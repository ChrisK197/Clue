import edu.princeton.cs.algs4.Graph;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class MainFile extends Application {
    private boolean weaponAns = false;
    private boolean personAns = false;
    private boolean playerAns = false;
    //currGuess in order: player (who are asking) at 0, weapon at 1, person you are guessing at 2, room at 3
    private String[] currGuess = new String[4];
    private Graph g;

    private int turnCounter = 0;

    public void start(Stage ps){
        Pane mainPane = new Pane();
        //Prints coordinates of mouse click
        /*mainPane.setOnMousePressed(e->{
            System.out.println(e.getX() + ", " + e.getY());
        });*/

        Image board = new Image("clueboard.jpg");
        ImageView boardImageView = new ImageView();
        boardImageView.setImage(board);
        boardImageView.setFitWidth(650);
        boardImageView.setFitHeight(650);
        mainPane.getChildren().add(boardImageView);

        Player p1 = new Player(new Image("green.png"), "Mr. Green", 1);
        ImageView p1view = p1.getImageView();
        p1view.setFitWidth(20);
        p1view.setFitHeight(20);
        p1view.setX(255);
        p1view.setY(605);
        mainPane.getChildren().add(p1view);

        Player p2 = new Player(new Image("mustard.png"), "Colonel Mustard", 2);
        ImageView p2view = p2.getImageView();
        p2view.setFitWidth(20);
        p2view.setFitHeight(20);
        p2view.setX(585);
        p2view.setY(200);
        mainPane.getChildren().add(p2view);

        Player p3 = new Player(new Image("scarlet.png"), "Miss Scarlet", 3);
        ImageView p3view = p3.getImageView();
        p3view.setFitWidth(20);
        p3view.setFitHeight(20);
        p3view.setX(420);
        p3view.setY(35);
        mainPane.getChildren().add(p3view);

        Player p4 = new Player(new Image("plum.png"), "Professor Plum", 4);
        ImageView p4view = p4.getImageView();
        p4view.setFitWidth(20);
        p4view.setFitHeight(20);
        p4view.setX(45);
        p4view.setY(150);
        mainPane.getChildren().add(p4view);

        ArrayList<Card> envelope = distributeCards(p1, p2, p3, p4);

        //We are using a graph.txt now
        try{createGraph();}
        catch (FileNotFoundException f){
            System.out.println("File not found");
        }

        Tile[] tiles = new Tile[198]; //we don't have a 0, we started at 1


        Button diceRoll= new Button();
        diceRoll.setText("Roll");
        diceRoll.setPrefSize(50, 50);
        //This is just the basic move function setup, not anything that would work as of now, TO DO
        diceRoll.setOnAction(e->{
            if (turnCounter == 0) {
                p1.move();
                turnCounter++;
            }
            else if (turnCounter == 1) {
                p2.move();
                turnCounter++;
            }
            else if (turnCounter == 2) {
                p3.move();
                turnCounter++;
            }
            else {
                p4.move();
                turnCounter = 0;
            }
        });
        mainPane.getChildren().add(diceRoll);
        diceRoll.setLayoutX(287);
        diceRoll.setLayoutY(275);

        ps.setTitle("Clue");
        Scene scene = new Scene(mainPane, 650,650);
        //The dimensions fit the board almost exactly, so if you want to add a border to put things in just expand it
        ps.setScene(scene);
        ps.show();

        guess(p1);
    }
    public void guess(Player p){
        Stage stage = new Stage();
        stage.setTitle("Guess");

        Label playerLabel = new Label("Pick a player to ask.");
        Label weaponLabel= new Label("Pick a weapon.");
        Label personLabel= new Label("Pick a person");
        Label labelresponse= new Label();


        Button submit= new Button("Submit");
        submit.setDisable(true);

        //weapons
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


        knife.setOnAction(e -> weaponChosen(submit));
        gun.setOnAction(e-> weaponChosen(submit));
        candlestick.setOnAction(e-> weaponChosen(submit));
        leadPipe.setOnAction(e-> weaponChosen(submit));
        wrench.setOnAction(e-> weaponChosen(submit));
        rope.setOnAction(e-> weaponChosen(submit));

        //people
        RadioButton plum, green, mustard, scarlet, white, peacock;
        plum =new RadioButton("Professor Plum");
        green= new RadioButton("Mr. Green");
        mustard= new RadioButton("Colonel Mustard");
        scarlet= new RadioButton("Miss Scarlet");
        white = new RadioButton("Mrs. White");
        peacock = new RadioButton("Mrs. Peacock");


        ToggleGroup people = new ToggleGroup();

        plum.setToggleGroup(people);
        green.setToggleGroup(people);
        mustard.setToggleGroup(people);
        scarlet.setToggleGroup(people);
        white.setToggleGroup(people);
        peacock.setToggleGroup(people);

        plum.setOnAction(e-> personChosen(submit));
        green.setOnAction(e-> personChosen(submit));
        mustard.setOnAction(e-> personChosen(submit));
        scarlet.setOnAction(e-> personChosen(submit));
        white.setOnAction(e-> personChosen(submit));
        peacock.setOnAction(e-> personChosen(submit));

        //players
        RadioButton p1, p2, p3, p4;
        p1 =new RadioButton("Player 1");
        p2= new RadioButton("Player 2");
        p3= new RadioButton("Player 3");
        p4= new RadioButton("Player 4");

        ToggleGroup player = new ToggleGroup();

        p1.setToggleGroup(player);
        p2.setToggleGroup(player);
        p3.setToggleGroup(player);
        p4.setToggleGroup(player);

        int pNum = p.getNum();
        if(pNum==1){
            p1.setDisable(true);
        }
        else if (pNum==2){
            p2.setDisable(true);
        }
        else if (pNum==3){
            p3.setDisable(true);
        }
        else if (pNum==4){
            p4.setDisable(true);
        }
        p1.setOnAction(e -> playerChosen(submit));
        p2.setOnAction(e-> playerChosen(submit));
        p3.setOnAction(e-> playerChosen(submit));
        p4.setOnAction(e-> playerChosen(submit));

        submit.setOnAction(e->{
            currGuess[0] = ((RadioButton)(player.getSelectedToggle())).getText();
            currGuess[1]=((RadioButton)(weapons.getSelectedToggle())).getText();
            currGuess[2] = ((RadioButton)(people.getSelectedToggle())).getText();
            currGuess[3] = p.getCurrentRoom();
            for(int i=0; i<4; i++){
                System.out.println(currGuess[i]);
            }
            stage.close();
        });

        HBox mainLayout = new HBox(7);
        VBox playerLayout = new VBox(5);
        VBox weaponLayout= new VBox(5);
        VBox peopleLayout = new VBox(5);

        mainLayout.getChildren().addAll(playerLayout, weaponLayout, peopleLayout);
        playerLayout.getChildren().addAll(playerLabel, p1, p2, p3, p4);
        weaponLayout.getChildren().addAll(weaponLabel, knife, gun, candlestick, leadPipe, wrench, rope, submit, labelresponse);
        peopleLayout.getChildren().addAll(personLabel, plum, green, mustard, scarlet, white, peacock);

        Scene scene1= new Scene(mainLayout, 400, 250);
        stage.setScene(scene1);

        stage.show();
        //http://www.learningaboutelectronics.com/Articles/Multiple-choice-test-question-in-JavaFX.php
    }
    public void weaponChosen(Button submit){
        weaponAns=true;
        if(weaponAns==true && personAns==true && playerAns==true){
            submit.setDisable(false);
        }
    }
    public void personChosen(Button submit){
        personAns=true;
        if(weaponAns==true && personAns==true && playerAns ==true){
            submit.setDisable(false);
        }
    }
    public void playerChosen(Button submit){
        playerAns=true;
        if(weaponAns==true && personAns==true && playerAns ==true){
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

    private void createGraph() throws FileNotFoundException {
        g = new Graph(198);
        //g is a private global variable declared at the top
        Scanner in = new Scanner(new FileReader("graph.txt"));
        while(in.hasNextLine()){
            String s = in.nextLine();
            String[] a = s.split(" ");
            g.addEdge(Integer.parseInt(a[0]), Integer.parseInt(a[1]));
            g.addEdge(Integer.parseInt(a[1]), Integer.parseInt(a[0]));
        }
    }
}
