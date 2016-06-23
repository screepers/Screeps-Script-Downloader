package com.frederikam.screepsscriptdownloader.gui;

import com.mashape.unirest.http.Unirest;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class MainApp extends Application {

    private static MainApp app;
    private static Stage stage;
    
    public static MainApp getApp(){
        return app;
    }
    
    public static Stage getMainStage(){
        return stage;
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        app = this;
        this.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        
        stage.setTitle("Script Downloader");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        Unirest.shutdown();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
