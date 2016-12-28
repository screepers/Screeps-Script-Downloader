package com.frederikam.screepsscriptdownloader.util;

import com.frederikam.screepsscriptdownloader.gui.FXMLController;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;
import org.json.JSONObject;

public class Downloader {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.]+@[\\w.]+\\.[\\w.]+$");

    public static void download(String email, String password, File outputDir, FXMLController controller) {
        controller.setLabelText("Downloading...");
        if (!EMAIL_PATTERN.matcher(email).find()) {
            throw new PopupException("Please enter a valid email.");
        }

        if (password.equals("")) {
            throw new PopupException("Please enter a password.");
        }

        if (outputDir.isFile()) {
            throw new PopupException("The selected directory is a file.");
        }

        try {
            //Connect to the API
            HttpResponse<JsonNode> response = Unirest.get("https://screeps.com/api/user/code").basicAuth(email, password).asJson();
            switch (response.getStatus()) {
                case 200:
                    JSONObject json = response.getBody().getObject();
                    System.out.println("Downloaded branch " + json.getString("branch"));

                    outputDir.mkdirs();
                    int completed = 0;

                    //Handle the modules
                    JSONObject mods = json.getJSONObject("modules");
                    for (String key : mods.keySet()) {
                        controller.setLabelText("Processing file " + completed + 1 + " of " + mods.length() + "...");
                        if (!mods.isNull(key)) {
                            File file = new File(outputDir, key + ".js");
                            FileWriter fw = new FileWriter(file);
                            fw.write(mods.getString(key));
                            fw.close();
                            completed++;
                        }
                    }
                    controller.setLabelText("Downloaded " + completed + " files from branch " + json.getString("branch") + ".");
                    break;
                case 401:
                    throw new PopupException("Invalid credentials.");
                default:
                    throw new PopupException("API responded with code " + response.getStatus() + ": " + response.getStatusText());
            }
        } catch (UnirestException ex) {
            throw new PopupException(ex);
        } catch (IOException ex) {
            throw new PopupException("Failed to write to disk: " + ex.getLocalizedMessage());
        }
    }

}
