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

import java.util.ArrayList;

public class MainFile extends Application {
    private boolean weaponAns = false;
    private boolean personAns = false;

    public void start(Stage ps){
        Pane mainPane = new Pane();

        Image p1image = new Image("green.png");
        Player p1 = new Player(p1image, "Mr. Green", 1);
        ImageView p1view = p1.getImageView();
        p1view.setImage(p1image);
        p1view.setFitWidth(40);
        p1view.setFitHeight(40);
        p1view.setX(610);
        p1view.setY(570);
        mainPane.getChildren().add(p1view);

        Image p2image = new Image("mustard.png");
        Player p2 = new Player(p2image, "Colonel Mustard", 2);
        ImageView p2view = p2.getImageView();
        p2view.setImage(p2image);
        p2view.setFitWidth(40);
        p2view.setFitHeight(40);
        p2view.setX(610);
        p2view.setY(570);
        mainPane.getChildren().add(p2view);

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

    private ArrayList<Card> distributeCards(Player p1, Player p2, Player p3, Player p4){ //21 cards total, 18 after the 3 are placed in the envelope
        //Takes the player objects and returns the envelope
        String[] people = {"Professor Plum", "Mr. Green", "Colonel Mustard", "Miss Scarlet", "Mrs. White", "Mrs. Peacock"};
        String[] weapons = {"Gun", "Candlestick", "Rope", "Knife", "Lead Pipe", "Wrench"};
        String[] rooms = {"Study", "Hall", "Lounge", "Dining Room", "Kitchen", "Ballroom", "Conservatory", "Billiard Room", "Library"};
        ArrayList<Card> peopleCards = new ArrayList<>();
        ArrayList<Card> weaponCards = new ArrayList<>();
        ArrayList<Card> roomCards = new ArrayList<>();
        for(String s: people){
            peopleCards.add(new Card(s, "person"));
        }
        for(String s: weapons){
            weaponCards.add(new Card(s, "weapon"));
        }
        for(String s: rooms){
            roomCards.add(new Card(s, "room"));
        }
        //putting cards in envelope
        //The envelope will have the cards in the order, person, weapon, room
        ArrayList<Card> envelope = new ArrayList<>();
        envelope.add(peopleCards.remove((int)(Math.random() * peopleCards.size())));
        envelope.add(weaponCards.remove((int)(Math.random() * weaponCards.size())));
        envelope.add(roomCards.remove((int)(Math.random() * roomCards.size())));

        int total = peopleCards.size() + weaponCards.size() + roomCards.size();
        Player[] p = {p1, p2, p3, p4};
        int currplayer = 0; // Players 1 and 2 will always have one extra card
        ArrayList<Card> arr;
        while(peopleCards.size() != 0 || weaponCards.size() != 0 || roomCards.size() != 0){
            int num = (int)(Math.random() * total);
            arr = peopleCards;
            if(num >= peopleCards.size()){
                num -= peopleCards.size();
                arr = weaponCards;
                if(num >= weaponCards.size()){
                    num-= weaponCards.size();
                    arr = roomCards;
                }
            }
            p[currplayer].addCard(arr.remove(num));
            total = peopleCards.size() + weaponCards.size() + roomCards.size();
            currplayer++;
            if(currplayer >= 4)
                currplayer = 0;
        }

        return envelope;
    }
}
