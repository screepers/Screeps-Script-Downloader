package com.frederikam.screepsscriptdownloader.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class PopupException extends RuntimeException {

    public PopupException() {
        super("An error occurred");
    }

    public PopupException(String msg) {
        super(msg);
    }
    
    public PopupException(Throwable thrwbl) {
        super(thrwbl);
    }

    public void displayPopup(AlertType at) {
        Alert alert = new Alert(at);
        alert.setTitle("Error Dialog");
        alert.setContentText(getMessage());

        alert.show();
    }

}
