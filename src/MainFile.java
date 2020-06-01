import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainFile extends Application {
    private boolean weaponAns = false;
    private boolean personAns = false;

    public void start(Stage ps){
        Pane mainPane = new Pane();

        Image board = new Image("clueboard.jpg");
        ImageView boardImageView = new ImageView();
        boardImageView.setImage(board);
        boardImageView.setFitWidth(650);
        boardImageView.setFitHeight(650);
        mainPane.getChildren().add(boardImageView);

        ps.setTitle("Clue");
        Scene scene = new Scene(mainPane, 1000,1000); //I just put random numbers for now
        ps.setScene(scene);
        ps.show();
    }
    public void guess(){
        Stage stage = new Stage();
        stage.setTitle("Guess");


        Label labelfirst= new Label("Pick a weapon and a person. The room can only be the one you are in.");
        Label labelresponse= new Label();


        Button submit= new Button("Submit");

        RadioButton knife, gun, candlestick, leadPipe, wrench, rope;
        knife=new RadioButton("Knife");
        gun= new RadioButton("Gun");
        candlestick= new RadioButton("Candlestick");
        leadPipe= new RadioButton("Lead Pipe");
        wrench = new RadioButton("Wrench");
        rope = new RadioButton("Rope");


        ToggleGroup weapons= new ToggleGroup();

        knife.setToggleGroup(weapons);
        gun.setToggleGroup(weapons);
        candlestick.setToggleGroup(weapons);
        leadPipe.setToggleGroup(weapons);
        wrench.setToggleGroup(weapons);
        rope.setToggleGroup(weapons);

        submit.setDisable(true);

        knife.setOnAction(e -> weaponChosen(submit));
        gun.setOnAction(e-> weaponChosen(submit));
        candlestick.setOnAction(e-> weaponChosen(submit));
        leadPipe.setOnAction(e-> weaponChosen(submit));
        wrench.setOnAction(e-> weaponChosen(submit));
        rope.setOnAction(e-> weaponChosen(submit));


        HBox mainLayout = new HBox(20);
        VBox weaponLayout= new VBox(5);
        VBox peopleLayout = new VBox(5); //still need to do people multiple choice

        mainLayout.getChildren().addAll(weaponLayout, peopleLayout);
        weaponLayout.getChildren().addAll(labelfirst, knife, gun, candlestick, leadPipe, wrench, rope, submit, labelresponse);

        Scene scene1= new Scene(mainLayout, 400, 250);
        stage.setScene(scene1);

        stage.show();
        //http://www.learningaboutelectronics.com/Articles/Multiple-choice-test-question-in-JavaFX.php
        //add multiple choice bubbles and stuff here
    }
    public void weaponChosen(Button submit){
        weaponAns=true;
        if(weaponAns && personAns){
            submit.setDisable(false);
        }
    }

}
