import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainFile extends Application {
    public void start(Stage ps){
        Pane mainPane = new Pane();

        //Build onto pane here

        ps.setTitle("Clue");
        Scene scene = new Scene(mainPane, 1000,1000); //I just put random numbers for now
        ps.setScene(scene);
        ps.show();
    }
    public void guess(){
        Stage stage = new Stage();
        //http://www.learningaboutelectronics.com/Articles/Multiple-choice-test-question-in-JavaFX.php
        //add multiple choice bubbles and stuff here
    }

}
