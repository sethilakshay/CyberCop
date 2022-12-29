
/**
 * 95-712 Homework 3
 * Name: Lakshay Sethi
 * Andrew ID: lsethi
 */


package hw3;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


@SuppressWarnings("serial")
public class DataException extends RuntimeException {
    /*
     * String variable to output the message in Alert
     */
    String message;
    DataException(String message){
        this.message = message;
    }
    
    /**
     * To show the alert prompt
     */
    void showAlert() {
        Alert dataError = new Alert(AlertType.ERROR);
        dataError.setTitle("Data Error");
        dataError.setHeaderText("Error");
        dataError.setContentText(message);
        dataError.showAndWait();    
    }
}
