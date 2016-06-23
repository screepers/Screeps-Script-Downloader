package com.frederikam.screepsscriptdownloader.gui;

import com.frederikam.screepsscriptdownloader.util.Downloader;
import com.frederikam.screepsscriptdownloader.util.PopupException;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;

public class FXMLController implements Initializable {

    @FXML
    private TextField emailInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private TextField directoryInput;
    @FXML
    private Button downloadButton;
    @FXML
    private Button browseButton;
    @FXML
    private Text label;

    @FXML
    private void openDirPicker(ActionEvent event) {
        DirectoryChooser picker = new DirectoryChooser();
        picker.setTitle("Open Resource File");
        File file = picker.showDialog(MainApp.getMainStage());
        if(file != null){
            directoryInput.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void onDownloadClicked(ActionEvent event) {
        setControlsDisable(true);
        try{
            Downloader.download(emailInput.getText(), passwordInput.getText(), new File(directoryInput.getText()), this);
        } catch(Exception ex){
            if(ex instanceof PopupException){
                ((PopupException)ex).displayPopup(AlertType.ERROR);
            } else {
                new PopupException(ex).displayPopup(AlertType.ERROR);
            }
            ex.printStackTrace();
            setLabelText("Download failed.");
        }
        setControlsDisable(false);
    }
    
    private void setControlsDisable(boolean bool){
        emailInput.setDisable(bool);
        passwordInput.setDisable(bool);
        directoryInput.setDisable(bool);
        downloadButton.setDisable(bool);
        browseButton.setDisable(bool);
    }
    
    public void setLabelText(String text){
        label.setText(text);
        label.setLayoutX(141 - label.getBoundsInParent().getWidth()/2);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    
}
