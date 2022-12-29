
/**
 * 95-712 Homework 3
 * Name: Lakshay Sethi
 * Andrew ID: lsethi
 */

package hw3;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class ModifyCaseView extends CaseView {

    ModifyCaseView(String header) {
        super(header);
    }

    @Override
    Stage buildView() {
        
        stage.setTitle("Modify Case");
        updateButton.setText("Modify Case");
        Scene scene = new Scene(updateCaseGridPane, CASE_WIDTH, CASE_HEIGHT);
        stage.setScene(scene);
        
        return stage;
    }

}
