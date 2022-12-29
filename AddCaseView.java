
/**
 * 95-712 Homework 3
 * Name: Lakshay Sethi
 * Andrew ID: lsethi
 */

package hw3;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class AddCaseView extends CaseView {

    AddCaseView(String header) {
        super(header);
    }

    @Override
    Stage buildView() {
        stage.setTitle("Add Case");
        updateButton.setText("Add Case");
        Scene scene = new Scene(updateCaseGridPane, CASE_WIDTH, CASE_HEIGHT);
        stage.setScene(scene);
        
        return stage;
    }

}
