import edu.princeton.cs.algs4.BreadthFirstPaths;
import edu.princeton.cs.algs4.Graph;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class MainFile extends Application {
    //for continuing game and kicking out
    private boolean p1Out = false;
    private boolean p2Out = false;
    private boolean p3Out = false;
    private boolean p4Out = false;
    private boolean p5Out = false;
    private boolean p6Out = false;

    private boolean weaponAns = false;
    private boolean personAns = false;
    private boolean playerAns = false;
    private boolean weaponFinalAns = false;
    private boolean personFinalAns = false;
    private boolean roomFinalAns=false;
    //currGuess in order: player (who are asking) at 0, weapon at 1, person you are guessing at 2, room at 3
    private String[] currGuess = new String[4];
    // 0 is room, 1 is weapon, 2 is person
    private String[] finalGuess = new String[3];
    private Graph g;
    private Tile[] tiles;
    private Button diceRoll;
    private Player currentPlayer;
    private Player p1;
    private Player p2;
    private Player p3;
    private Player p4;
    private Player p5;
    private Player p6;
    private ArrayList<Card> envelope;
    private Pane mainPane;

    private int turnCounter = 0;
    private Label currPlayerText;

    public void start(Stage ps){
        mainPane = new Pane();
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

        p1 = new Player(new Image("green.png"), "Mr. Green", 1, 183);
        currentPlayer = p1;
        ImageView p1view = p1.getImageView();
        p1view.setFitWidth(20);
        p1view.setFitHeight(20);
        p1view.setX(255);
        p1view.setY(605);
        mainPane.getChildren().add(p1view);

        p2 = new Player(new Image("mustard.png"), "Colonel Mustard", 2, 196);
        ImageView p2view = p2.getImageView();
        p2view.setFitWidth(20);
        p2view.setFitHeight(20);
        p2view.setX(585);
        p2view.setY(200);
        mainPane.getChildren().add(p2view);

        p3 = new Player(new Image("scarlet.png"), "Miss Scarlet", 3, 194);
        ImageView p3view = p3.getImageView();
        p3view.setFitWidth(20);
        p3view.setFitHeight(20);
        p3view.setX(420);
        p3view.setY(35);
        mainPane.getChildren().add(p3view);

        p4 = new Player(new Image("plum.png"), "Professor Plum", 4, 195);
        ImageView p4view = p4.getImageView();
        p4view.setFitWidth(20);
        p4view.setFitHeight(20);
        p4view.setX(45);
        p4view.setY(150);
        mainPane.getChildren().add(p4view);

        p5 = new Player(new Image("peacock.png"), "Mrs. Peacock", 5, 197);
        ImageView p5view = p5.getImageView();
        p5view.setFitWidth(20);
        p5view.setFitHeight(20);
        p5view.setX(45);
        p5view.setY(700);
        mainPane.getChildren().add(p5view);

        p6 = new Player(new Image("white.png"), "Mrs. White", 6, 184);
        ImageView p6view = p6.getImageView();
        p6view.setFitWidth(20);
        p6view.setFitHeight(20);
        p6view.setX(420);
        p6view.setY(785);
        mainPane.getChildren().add(p6view);

        envelope = distributeCards(p1, p2, p3, p4, p5, p6);

        //We are using a graph.txt now
        try{createGraph();}
        catch (FileNotFoundException f){
            System.out.println("File not found");
        }
        /*
        These were temporary testing variables. I'm leaving them here in case I need them later
        Tile t = new Tile(100,100, 0);
        t.highlight();
        mainPane.getChildren().add(t.getImageView());
        Tile x = new Tile(200,10, 1);
        x.highlight();
        mainPane.getChildren().add(x.getImageView());
        Tile z = new Tile(250,130, 2);
        z.highlight();
        mainPane.getChildren().add(z.getImageView());
        Tile.highlightedTiles.add(t); Tile.highlightedTiles.add(x); Tile.highlightedTiles.add(z);*/

        //2D array to use in the for loop to file up tiles. (true means it is a space, false means it is not) it DOES NOT include the start spaces
        // [row][col]
        boolean[][] tileOrNot = new boolean[25][24];
        for(int row=0; row<25; row++){
            for(int col =0; col<24; col++){
                //study
                if(row<=3 && col<=6){
                    tileOrNot[row][col]=false;
                }
                //hall
                else if(row<=6 && col>=9 && col<=14){
                    tileOrNot[row][col]=false;
                }
                //lounge (and 1 extra space but that is fine)
                else if (row<=5 && col >=17){
                    tileOrNot[row][col]=false;
                }
                //library (plz check this idk if it is right)
                else if(row>=6 && row<=10 && (col <=5 || (col==6 && row==7) || (col==6 && row==8))){
                    tileOrNot[row][col]=false;
                }
                //box in the middle that says clue
                else if (row>=8 && row<=14 && col >=9 && col <=13){
                    tileOrNot[row][col]=false;
                }
                //dining room part 1
                else if(col >=16 && row>=9 && row<=14){
                    tileOrNot[row][col]=false;
                }
                //dining room part 2
                else if(row==15 && col>=19){
                    tileOrNot[row][col]=false;
                }
                //billiard room
                else if(row>=12 && row<=16 && col<=5){
                    tileOrNot[row][col]=false;
                }
                //little space right outside conservatory
                else if(col==5 && row==19){
                    tileOrNot[row][col]=true;
                }
                //conservatory
                else if(row>=19 && col <=5){
                    tileOrNot[row][col]=false;
                }
                //ballroom
                else if (row>=17 && col>=8 && col <=15){
                    tileOrNot[row][col]=false;
                }

                //kitchen
                else if(col>=18 && row>=18){
                    tileOrNot[row][col]=false;
                }
                //perimeter
                else if(col==0 || row==0 || col==23 || row==24){
                    tileOrNot[row][col]=false;
                }
                else{
                    tileOrNot[row][col]=true;
                }
                //perimeter fix (one space)
                tileOrNot[17][23]=true;
                //ballroom four spaces
                tileOrNot[23][8]=true;
                tileOrNot[23][9]=true;
                tileOrNot[23][15]=true;
                tileOrNot[23][14]=true;
                tileOrNot[5][19]=true;
                tileOrNot[0][7] = true;
                tileOrNot[5][19] = false;
                tileOrNot[23][6] = false;
                tileOrNot[23][17] = false;
                tileOrNot[9][6] = false;
            }
        }

        //Rooms are being made below
        Room study = new Room(17, 12, 191, "Study", "studyH.jpg");
        Room hall = new Room(238, -3, 192, "Hall", "hallH.jpg");
        Room lounge = new Room(425, 3, 193, "Lounge", "loungeH.png");
        Room library = new Room(18, 158, 190, "Library", "libraryH.png");
        Room diningRoom = new Room(398, 222, 189, "Dining Room", "diningH.png");
        Room billiardRoom = new Room(17, 300, 188, "Billiard Room", "billiardH.jpg");
        Room conservatory = new Room(14, 467, 185, "Conservatory", "conservatoryH.png");
        Room ballRoom = new Room(210, 410, 186, "Ballroom", "ballRoomH.png");
        Room kitchen = new Room(450, 440, 187, "Kitchen", "kitchenH.jpg");
        Room[] temparr = {study, hall, lounge, library, diningRoom, billiardRoom, conservatory, ballRoom, kitchen};
        for(Room r : temparr){
            mainPane.getChildren().add(r.getImageView());
            r.getImageView().setOnMouseClicked(e ->{
                diceRoll.setDisable(false);
                r.unhighlight();
                for(int i = Tile.highlightedTiles.size() - 1; i >= 0; i--) {
                    Tile.highlightedTiles.remove(i).unhighlight();
                }
                r.addPlayer(currentPlayer);
                currentPlayer.setCurrentRoom(r);
                guess(currentPlayer);
            });
            //r.highlight();
        }

        tiles = new Tile[198]; //we don't have a 0, we started at 1
        tiles[191] = study;
        tiles[192] = hall;
        tiles[193] = lounge;
        tiles[190] = library;
        tiles[189] = diningRoom;
        tiles[188] = billiardRoom;
        tiles[185] = conservatory;
        tiles[186] = ballRoom;
        tiles[187] = kitchen;
        int count = 1;
        int xval = 63-24; //starting
        int yval = 28; //starting
        for(int row = 0; row < tileOrNot.length; row++){
            for(int col = 0; col < tileOrNot[0].length; col++){
                if(tileOrNot[row][col]){
                    tiles[count] = new Tile(xval + 23.8*col,yval + 24*row, count);
                    //Moved setOnMouseClicked out of Tile so it can access the diceRoll
                    int finalCount = count;
                    tiles[count].getImageView().setOnMouseClicked(e->{
                        diceRoll.setDisable(false);
                        tiles[finalCount].getImageView().setVisible(false);
                        //stateHighlighted = true;
                        //System.out.println(Tile.highlightedTiles);
                        for(int i = Tile.highlightedTiles.size() - 1; i >= 0; i--) {
                            Tile.highlightedTiles.remove(i).unhighlight();
                        }
                        //System.out.println(Tile.highlightedTiles);
                        currentPlayer.getImageView().setX(tiles[finalCount].getX());
                        currentPlayer.getImageView().setY(tiles[finalCount].getY());
                        currentPlayer.setCurrentSpace(tiles[finalCount].getNum());
                        currentPlayer.setCurrentRoom(null);
                    });
                    mainPane.getChildren().add(tiles[count].getImageView());
                    //tiles[count].highlight();
                    count++;
                }
                //Rooms
                //study
                else if(row==2 && col==5){
                    study.setP1x(xval + 23.8*col);
                    study.setP1y(yval + 24*row);
                }
                else if(row==2 && col==4){
                    study.setP2x(xval + 23.8*col);
                    study.setP2y(yval + 24*row);
                }
                else if(row==2 && col==3){
                    study.setP3x(xval + 23.8*col);
                    study.setP3y(yval + 24*row);
                }
                else if(row==2 && col==2){
                    study.setP4x(xval + 23.8*col);
                    study.setP4y(yval + 24*row);
                }
                //hall
                else if(row==3 && col==13){
                    hall.setP1x(xval + 23.8*col);
                    hall.setP1y(yval + 24*row);
                }
                else if(row==3 && col==12){
                    hall.setP2x(xval + 23.8*col);
                    hall.setP2y(yval + 24*row);
                }
                else if(row==3 && col==11){
                    hall.setP3x(xval + 23.8*col);
                    hall.setP3y(yval + 24*row);
                }
                else if(row==3 && col==10){
                    hall.setP4x(xval + 23.8*col);
                    hall.setP4y(yval + 24*row);
                }
                //lounge
                else if(row==2 && col==22){
                    lounge.setP1x(xval + 23.8*col);
                    lounge.setP1y(yval + 24*row);
                }
                else if(row==2 && col==21){
                    lounge.setP2x(xval + 23.8*col);
                    lounge.setP2y(yval + 24*row);
                }
                else if(row==2 && col==20){
                    lounge.setP3x(xval + 23.8*col);
                    lounge.setP3y(yval + 24*row);
                }
                else if(row==2 && col==19){
                    lounge.setP4x(xval + 23.8*col);
                    lounge.setP4y(yval + 24*row);
                }
                //library
                else if(row==6 && col==5){
                    library.setP1x(xval + 23.8*col);
                    library.setP1y(yval + 24*row);
                }
                else if(row==6 && col==4){
                    library.setP2x(xval + 23.8*col);
                    library.setP2y(yval + 24*row);
                }
                else if(row==6 && col==3){
                    library.setP3x(xval + 23.8*col);
                    library.setP3y(yval + 24*row);
                }
                else if(row==6 && col==2){
                    library.setP4x(xval + 23.8*col);
                    library.setP4y(yval + 24*row);
                }
                //Dining Room
                else if(row==12 && col==22){
                    diningRoom.setP1x(xval + 23.8*col);
                    diningRoom.setP1y(yval + 24*row);
                }
                else if(row==12 && col==21){
                    diningRoom.setP2x(xval + 23.8*col);
                    diningRoom.setP2y(yval + 24*row);
                }
                else if(row==12 && col==20){
                    diningRoom.setP3x(xval + 23.8*col);
                    diningRoom.setP3y(yval + 24*row);
                }
                else if(row==12 && col==19){
                    diningRoom.setP4x(xval + 23.8*col);
                    diningRoom.setP4y(yval + 24*row);
                }
                //Billiard Room
                else if(row==14 && col==4){
                    billiardRoom.setP1x(xval + 23.8*col);
                    billiardRoom.setP1y(yval + 24*row);
                }
                else if(row==14 && col==3){
                    billiardRoom.setP2x(xval + 23.8*col);
                    billiardRoom.setP2y(yval + 24*row);
                }
                else if(row==14 && col==2){
                    billiardRoom.setP3x(xval + 23.8*col);
                    billiardRoom.setP3y(yval + 24*row);
                }
                else if(row==14 && col==1){
                    billiardRoom.setP4x(xval + 23.8*col);
                    billiardRoom.setP4y(yval + 24*row);
                }
                //Conservatory
                else if(row==21 && col==4){
                    conservatory.setP1x(xval + 23.8*col);
                    conservatory.setP1y(yval + 24*row);
                }
                else if(row==21 && col==3){
                    conservatory.setP2x(xval + 23.8*col);
                    conservatory.setP2y(yval + 24*row);
                }
                else if(row==21 && col==2){
                    conservatory.setP3x(xval + 23.8*col);
                    conservatory.setP3y(yval + 24*row);
                }
                else if(row==21 && col==1){
                    conservatory.setP4x(xval + 23.8*col);
                    conservatory.setP4y(yval + 24*row);
                }
                //Ballroom
                else if(row==20 && col==13){
                    ballRoom.setP1x(xval + 23.8*col);
                    ballRoom.setP1y(yval + 24*row);
                }
                else if(row==20 && col==12){
                    ballRoom.setP2x(xval + 23.8*col);
                    ballRoom.setP2y(yval + 24*row);
                }
                else if(row==20 && col==11){
                    ballRoom.setP3x(xval + 23.8*col);
                    ballRoom.setP3y(yval + 24*row);
                }
                else if(row==20 && col==10){
                    ballRoom.setP4x(xval + 23.8*col);
                    ballRoom.setP4y(yval + 24*row);
                }
                //kitchen
                else if(row==20 && col==22){
                    kitchen.setP1x(xval + 23.8*col);
                    kitchen.setP1y(yval + 24*row);
                }
                else if(row==20 && col==21){
                    kitchen.setP2x(xval + 23.8*col);
                    kitchen.setP2y(yval + 24*row);
                }
                else if(row==20 && col==20){
                    kitchen.setP3x(xval + 23.8*col);
                    kitchen.setP3y(yval + 24*row);
                }
                else if(row==20 && col==19){
                    kitchen.setP4x(xval + 23.8*col);
                    kitchen.setP4y(yval + 24*row);
                }
            }
        }

        diceRoll= new Button();
        diceRoll.setText("Roll");
        diceRoll.setPrefSize(50, 50);
        //This is just the basic move function setup, not anything that would work as of now, TO DO
        diceRoll.setOnAction(e->{
            if(p1Out && turnCounter==0){
                turnCounter=1;
            }
            if(p2Out && turnCounter==1){
                turnCounter=2;
            }
            if(p3Out && turnCounter==2){
                turnCounter=3;
            }
            if(p4Out && turnCounter==3){
                turnCounter=4;
            }
            if (p5Out && turnCounter==4) {
                turnCounter=5;
            }
            if (p6Out && turnCounter==5) {
                turnCounter=0;
            }
            //this is duplicated because if players 1 and 4 are out it could get weird.
            if(p1Out && turnCounter==0){
                turnCounter=1;
            }
            if(p2Out && turnCounter==1){
                turnCounter=2;
            }
            if(p3Out && turnCounter==2){
                turnCounter=3;
            }
            if(p4Out && turnCounter==3){
                turnCounter=4;
            }
            if (p5Out && turnCounter==4) {
                turnCounter=5;
            }
            if (p6Out && turnCounter==5) {
                turnCounter=0;
            }
            diceRoll.setDisable(true);
            if (turnCounter == 0) {
                currentPlayer = p1;
                currPlayerText.setText("Current Player: 1");
                int roll = p1.move();
                move(p1, roll);
                turnCounter++;
            }
            else if (turnCounter == 1) {
                currentPlayer = p2;
                currPlayerText.setText("Current Player: 2");
                int roll = p2.move();
                move(p2, roll);
                turnCounter++;
            }
            else if (turnCounter == 2) {
                currentPlayer = p3;
                currPlayerText.setText("Current Player: 3");
                int roll = p3.move();
                move(p3, roll);
                turnCounter++;
            }
            else if (turnCounter == 3) {
                currentPlayer = p4;
                currPlayerText.setText("Current Player: 4");
                int roll = p4.move();
                move(p4, roll);
                turnCounter++;
            }
            else if (turnCounter == 4) {
                currentPlayer = p5;
                currPlayerText.setText("Current Player: 5");
                int roll = p5.move();
                move(p5, roll);
                turnCounter++;
            }
            else {
                currentPlayer = p6;
                currPlayerText.setText("Current Player: 6");
                int roll = p6.move();
                move(p6, roll);
                turnCounter = 0;
            }
        });
        mainPane.getChildren().add(diceRoll);
        diceRoll.setLayoutX(287);
        diceRoll.setLayoutY(275);

        currPlayerText = new Label("Current Player: " + "1");
        mainPane.getChildren().add(currPlayerText);
        currPlayerText.setLayoutX(660);
        currPlayerText.setLayoutY(350);

        Button finalGuess = new Button("Final Guess");
        mainPane.getChildren().add(finalGuess);
        finalGuess.setLayoutX(660);
        finalGuess.setLayoutY(300);
        finalGuess.setOnAction(e->{
            finalGuess();
        });

        ps.setTitle("Clue");
        Scene scene = new Scene(mainPane, 800,650);
        //The dimensions fit the board almost exactly, so if you want to add a border to put things in just expand it
        ps.setScene(scene);
        ps.show();

        //guess(p1);
    }
    public void finalGuess(){
        Stage s = new Stage();
        s.setTitle("FINAL GUESS");
        Label weaponLabel= new Label("Pick a weapon.");
        Label personLabel= new Label("Pick a person");

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


        knife.setOnAction(e -> weaponChosen2(submit));
        gun.setOnAction(e-> weaponChosen2(submit));
        candlestick.setOnAction(e-> weaponChosen2(submit));
        leadPipe.setOnAction(e-> weaponChosen2(submit));
        wrench.setOnAction(e-> weaponChosen2(submit));
        rope.setOnAction(e-> weaponChosen2(submit));

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

        plum.setOnAction(e-> personChosen2(submit));
        green.setOnAction(e-> personChosen2(submit));
        mustard.setOnAction(e-> personChosen2(submit));
        scarlet.setOnAction(e-> personChosen2(submit));
        white.setOnAction(e-> personChosen2(submit));
        peacock.setOnAction(e-> personChosen2(submit));

        //{"Study", "Hall", "Lounge", "Dining Room", "Kitchen", "Ballroom", "Conservatory", "Billiard Room", "Library"};
        //rooms
        RadioButton study, hall, lounge, diningRoom, kitchen, ballroom, conservatory, billiardRoom, library;
        study =new RadioButton("Study");
        hall= new RadioButton("Hall");
        lounge= new RadioButton("Lounge");
        diningRoom= new RadioButton("Dining Room");
        kitchen = new RadioButton("Kitchen");
        ballroom = new RadioButton("Ballroom");
        conservatory = new RadioButton("Conservatory");
        billiardRoom=new RadioButton("Billiard Room");
        library=new RadioButton("Library");


        ToggleGroup rooms = new ToggleGroup();

        study.setToggleGroup(rooms);
        hall.setToggleGroup(rooms);
        lounge.setToggleGroup(rooms);
        diningRoom.setToggleGroup(rooms);
        kitchen.setToggleGroup(rooms);
        ballroom.setToggleGroup(rooms);
        conservatory.setToggleGroup(rooms);
        billiardRoom.setToggleGroup(rooms);
        library.setToggleGroup(rooms);

        study.setOnAction(e-> roomChosen(submit));
        hall.setOnAction(e-> roomChosen(submit));
        lounge.setOnAction(e-> roomChosen(submit));
        diningRoom.setOnAction(e-> roomChosen(submit));
        kitchen.setOnAction(e-> roomChosen(submit));
        ballroom.setOnAction(e-> roomChosen(submit));
        conservatory.setOnAction(e-> roomChosen(submit));
        billiardRoom.setOnAction(e-> roomChosen(submit));
        library.setOnAction(e-> roomChosen(submit));

        submit.setOnAction(e->{
            //System.out.println("Correct Answer: ");
            for(int i=0; i<3; i++){
                //System.out.println(envelope.get(i));
            }
            Stage s3 = new Stage();
            VBox v = new VBox();
            finalGuess[0] = ((RadioButton)(rooms.getSelectedToggle())).getText();
            finalGuess[1]=((RadioButton)(weapons.getSelectedToggle())).getText();
            finalGuess[2] = ((RadioButton)(people.getSelectedToggle())).getText();
            Label win = new Label(currentPlayer.getName() + " has won the game!\nThe answer was " + finalGuess[0] + ", " + finalGuess[1] + ", " + finalGuess[2]);
            Label lose = new Label(currentPlayer.getName() + " was wrong!");
            Label allLose = new Label("EVERYONE WAS WRONG");
            for(int i=0; i<3; i++){
                //System.out.println(finalGuess[i]);
            }
            //people, weapon, room
            if(finalGuess[0].equals((envelope.get(2)).getName()) && finalGuess[1].equals((envelope.get(1)).getName()) && finalGuess[2].equals((envelope.get(0)).getName())){
                v.getChildren().add(win);
            }
            else{
                v.getChildren().add(lose);
                if(currentPlayer.getNum()==1){
                    p1Out=true;
                    turnCounter++;
                    currentPlayer=p2;
                }
                else if (currentPlayer.getNum()==2){
                    p2Out=true;
                    turnCounter++;
                    currentPlayer=p3;
                }
                else if(currentPlayer.getNum()==3){
                    p3Out=true;
                    turnCounter++;
                    currentPlayer=p4;
                }
                else if(currentPlayer.getNum()==4){
                    p4Out=true;
                    turnCounter++;
                    currentPlayer=p5;
                }
                else if(currentPlayer.getNum()==5){
                    p5Out=true;
                    turnCounter++;
                    currentPlayer=p6;
                }
                else if (currentPlayer.getNum()==6){
                    p6Out=true;
                    turnCounter=0;
                    currentPlayer=p1;
                }
                if(p1Out && p2Out && p3Out && p4Out && p5Out && p6Out){
                    v.getChildren().remove(lose);
                    v.getChildren().add(allLose);
                    mainPane.getChildren().removeAll();
                }
            }
            Scene scenee = new Scene(v, 400, 250);
            s3.setScene(scenee);
            s3.show();
            s.close();
        });

        HBox mainLayout = new HBox(7);
        VBox roomLayout = new VBox(5);
        VBox weaponLayout= new VBox(5);
        VBox peopleLayout = new VBox(5);
        Label roomLabel = new Label("Pick a room");

        mainLayout.getChildren().addAll(roomLayout, weaponLayout, peopleLayout);
        roomLayout.getChildren().addAll(roomLabel, study, hall, lounge, diningRoom, kitchen, ballroom, conservatory, billiardRoom, library);
        weaponLayout.getChildren().addAll(weaponLabel, knife, gun, candlestick, leadPipe, wrench, rope, submit);
        peopleLayout.getChildren().addAll(personLabel, plum, green, mustard, scarlet, white, peacock);

        Scene scene1= new Scene(mainLayout, 400, 300);
        s.setScene(scene1);

        s.show();
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
        RadioButton player1, player2, player3, player4, player5, player6;
        player1 =new RadioButton("Player 1");
        player2= new RadioButton("Player 2");
        player3= new RadioButton("Player 3");
        player4= new RadioButton("Player 4");
        player5= new RadioButton("Player 5");
        player6= new RadioButton("Player 6");

        ToggleGroup player = new ToggleGroup();

        player1.setToggleGroup(player);
        player2.setToggleGroup(player);
        player3.setToggleGroup(player);
        player4.setToggleGroup(player);
        player5.setToggleGroup(player);
        player6.setToggleGroup(player);

        int pNum = p.getNum();
        if(pNum==1){
            player1.setDisable(true);
        }
        else if (pNum==2){
            player2.setDisable(true);
        }
        else if (pNum==3){
            player3.setDisable(true);
        }
        else if (pNum==4){
            player4.setDisable(true);
        }
        else if (pNum==5){
            player5.setDisable(true);
        }
        else if (pNum==6){
            player6.setDisable(true);
        }
        player1.setOnAction(e -> playerChosen(submit));
        player2.setOnAction(e-> playerChosen(submit));
        player3.setOnAction(e-> playerChosen(submit));
        player4.setOnAction(e-> playerChosen(submit));
        player5.setOnAction(e-> playerChosen(submit));
        player6.setOnAction(e-> playerChosen(submit));

        submit.setOnAction(e->{
            currGuess[0] = ((RadioButton)(player.getSelectedToggle())).getText();
            currGuess[1]=((RadioButton)(weapons.getSelectedToggle())).getText();
            currGuess[2] = ((RadioButton)(people.getSelectedToggle())).getText();
            currGuess[3] = p.getCurrentRoom().getName();
            for(int i=0; i<4; i++){
                //System.out.println(currGuess[i]);
            }
            guess2();
            stage.close();
        });

        Label turn = new Label(" Turn the computer\n to the player\n you are asking \n before you click submit.");
        HBox mainLayout = new HBox(7);
        VBox playerLayout = new VBox(5);
        VBox weaponLayout= new VBox(5);
        VBox peopleLayout = new VBox(5);

        mainLayout.getChildren().addAll(playerLayout, weaponLayout, peopleLayout);
        playerLayout.getChildren().addAll(playerLabel, player1, player2, player3, player4, player5, player6, turn);
        weaponLayout.getChildren().addAll(weaponLabel, knife, gun, candlestick, leadPipe, wrench, rope, submit, labelresponse);
        peopleLayout.getChildren().addAll(personLabel, plum, green, mustard, scarlet, white, peacock);

        Scene scene1= new Scene(mainLayout, 450, 300);
        stage.setScene(scene1);

        stage.show();
        //http://www.learningaboutelectronics.com/Articles/Multiple-choice-test-question-in-JavaFX.php
    }
    //used to see if the cards are right
    public void guess2(){
        Stage stage = new Stage();
        stage.setTitle("Guess 2");
        Button submit = new Button("Submit");
        submit.setDisable(true);
        //System.out.println("\n");
        int numOfCards=0;
        RadioButton weapon = null;
        RadioButton person = null;
        RadioButton room = null;
        ToggleGroup t = new ToggleGroup();
        if(currGuess[0].equals("Player 1")){
            for(int i=0; i<(p1.getCards()).length; i++){
                //System.out.println((p1.getCards())[i]);
            }
            for(int i=0; i<(p1.getCards()).length; i++){
                //weapon check
                if((((p1.getCards())[i]).getName()).equals(currGuess[1])){
                    numOfCards++;
                    weapon = new RadioButton(currGuess[1]);
                }
                //person
                else if((((p1.getCards())[i]).getName()).equals(currGuess[2])){
                    numOfCards++;
                    person = new RadioButton(currGuess[2]);
                }
                else if((((p1.getCards())[i]).getName()).equals(currGuess[3])){
                    numOfCards++;
                    room = new RadioButton(currGuess[3]);
                }
            }
        }
        else if(currGuess[0].equals("Player 2")){
            for(int i=0; i<(p2.getCards()).length; i++){
                //System.out.println((p2.getCards())[i]);
            }
            for(int i=0; i<(p2.getCards()).length; i++){
                //weapon check
                if((((p2.getCards())[i]).getName()).equals(currGuess[1])){
                    numOfCards++;
                    weapon = new RadioButton(currGuess[1]);
                }
                //person
                else if((((p2.getCards())[i]).getName()).equals(currGuess[2])){
                    numOfCards++;
                    person = new RadioButton(currGuess[2]);
                }
                else if((((p2.getCards())[i]).getName()).equals(currGuess[3])){
                    numOfCards++;
                    room = new RadioButton(currGuess[3]);
                }
            }
        }
        else if(currGuess[0].equals("Player 3")){
            for(int i=0; i<(p3.getCards()).length; i++){
                //System.out.println((p3.getCards())[i]);
            }
            for(int i=0; i<(p3.getCards()).length; i++){
                //weapon check
                if((((p3.getCards())[i]).getName()).equals(currGuess[1])){
                    numOfCards++;
                    weapon = new RadioButton(currGuess[1]);
                }
                //person
                else if((((p3.getCards())[i]).getName()).equals(currGuess[2])){
                    numOfCards++;
                    person = new RadioButton(currGuess[2]);
                }
                else if((((p3.getCards())[i]).getName()).equals(currGuess[3])){
                    numOfCards++;
                    room = new RadioButton(currGuess[3]);
                }
            }
        }
        else if(currGuess[0].equals("Player 4")){
            for(int i=0; i<(p4.getCards()).length; i++){
                //System.out.println((p3.getCards())[i]);
            }
            for(int i=0; i<(p4.getCards()).length; i++){
                //weapon check
                if((((p4.getCards())[i]).getName()).equals(currGuess[1])){
                    numOfCards++;
                    weapon = new RadioButton(currGuess[1]);
                }
                //person
                else if((((p4.getCards())[i]).getName()).equals(currGuess[2])){
                    numOfCards++;
                    person = new RadioButton(currGuess[2]);
                }
                else if((((p4.getCards())[i]).getName()).equals(currGuess[3])){
                    numOfCards++;
                    room = new RadioButton(currGuess[3]);
                }
            }
        }
        else if(currGuess[0].equals("Player 5")){
            for(int i=0; i<(p5.getCards()).length; i++){
                //System.out.println((p3.getCards())[i]);
            }
            for(int i=0; i<(p5.getCards()).length; i++){
                //weapon check
                if((((p5.getCards())[i]).getName()).equals(currGuess[1])){
                    numOfCards++;
                    weapon = new RadioButton(currGuess[1]);
                }
                //person
                else if((((p5.getCards())[i]).getName()).equals(currGuess[2])){
                    numOfCards++;
                    person = new RadioButton(currGuess[2]);
                }
                else if((((p5.getCards())[i]).getName()).equals(currGuess[3])){
                    numOfCards++;
                    room = new RadioButton(currGuess[3]);
                }
            }
        }
        else if(currGuess[0].equals("Player 6")){
            for(int i=0; i<(p6.getCards()).length; i++){
                //System.out.println((p4.getCards())[i]);
            }
            for(int i=0; i<(p6.getCards()).length; i++){
                //weapon check
                if((((p6.getCards())[i]).getName()).equals(currGuess[1])){
                    numOfCards++;
                    weapon = new RadioButton(currGuess[1]);
                }
                //person
                else if((((p6.getCards())[i]).getName()).equals(currGuess[2])){
                    numOfCards++;
                    person = new RadioButton(currGuess[2]);
                }
                else if((((p6.getCards())[i]).getName()).equals(currGuess[3])){
                    numOfCards++;
                    room = new RadioButton(currGuess[3]);
                }
            }
        }
        VBox pane = new VBox(5);
        if(numOfCards==0){
            pane.getChildren().add(new Label (currGuess[0] + " had none of the cards you guessed."));
        }
        //at least one button is not null
        else{
            Label prompt = new Label("What would you like to show?");
            pane.getChildren().add(prompt);
            Label end = new Label ("Turn the computer back to the player\nyou are showing after clicking submit");
            if(weapon != null){
                pane.getChildren().add(weapon);
                weapon.setToggleGroup(t);
                weapon.setOnAction(e->{
                    submit.setDisable(false);
                });
            }
            if(person!=null){
                pane.getChildren().add(person);
                person.setToggleGroup(t);
                person.setOnAction(e->{
                    submit.setDisable(false);
                });
            }
            if(room!=null){
                pane.getChildren().add(room);
                room.setToggleGroup(t);
                room.setOnAction(e->{
                    submit.setDisable(false);
                });
            }
            pane.getChildren().add(submit);
            pane.getChildren().add(end);
        }


        submit.setOnAction(e->{
            Stage s2 = new Stage();
            VBox pane2 = new VBox(5);
            Label l = new Label(currGuess[0] + " is showing you the " + ((RadioButton)(t.getSelectedToggle())).getText());
            pane2.getChildren().add(l);
            //for some reason does not put a new window with what the person chose. It puts a million errors
            Scene s = new Scene(pane2, 400, 250);
            s2.setScene(s);
            s2.show();
            stage.close();
        });
        Scene scene1= new Scene(pane, 400, 250);
        stage.setScene(scene1);

        stage.show();

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
    public void weaponChosen2(Button submit){
        weaponFinalAns=true;
        if(weaponFinalAns ==true && personFinalAns ==true && roomFinalAns==true){
            submit.setDisable(false);
        }
    }
    public void personChosen2(Button submit){
        personFinalAns=true;
        if(weaponFinalAns ==true && personFinalAns ==true && roomFinalAns==true){
            submit.setDisable(false);
        }
    }
    public void roomChosen(Button submit){
        roomFinalAns=true;
        if(weaponFinalAns ==true && personFinalAns ==true && roomFinalAns==true){
            submit.setDisable(false);
        }
    }

    private ArrayList<Card> distributeCards(Player p1, Player p2, Player p3, Player p4, Player p5, Player p6){ //21 cards total, 18 after the 3 are placed in the envelope
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
        Player[] p = {p1, p2, p3, p4, p5, p6};
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
            if(currplayer >= 6)
                currplayer = 0;
        }

        Button p1Cards = new Button("Player 1 Cards");
        mainPane.getChildren().add(p1Cards);
        p1Cards.setLayoutX(660);
        p1Cards.setLayoutY(0);
        p1Cards.setOnAction(e->{
            Stage stage = new Stage();
            stage.setTitle("Player 1's Hand");
            Pane pane = new Pane();
            Text t1 = new Text(25, 50, "Player 1's Cards:");
            Text t2 = new Text(25, 250, p1.getCardsAsStringNL());
            t1.setFont(new Font(22));
            t2.setFont(new Font(22));
            pane.getChildren().addAll(t1, t2);
            Scene ss = new Scene(pane, 250, 500);
            stage.setScene(ss);
            stage.show();
        });

        Button p2Cards = new Button("Player 2 Cards");
        mainPane.getChildren().add(p2Cards);
        p2Cards.setLayoutX(660);
        p2Cards.setLayoutY(50);
        p2Cards.setOnAction(e->{
            Stage stage = new Stage();
            stage.setTitle("Player 2's Hand");
            Pane pane = new Pane();
            Text t1 = new Text(25, 50, "Player 2's Cards:");
            Text t2 = new Text(25, 250, p2.getCardsAsStringNL());
            t1.setFont(new Font(22));
            t2.setFont(new Font(22));
            pane.getChildren().addAll(t1, t2);
            Scene ss = new Scene(pane, 250, 500);
            stage.setScene(ss);
            stage.show();
        });

        Button p3Cards = new Button("Player 3 Cards");
        mainPane.getChildren().add(p3Cards);
        p3Cards.setLayoutX(660);
        p3Cards.setLayoutY(100);
        p3Cards.setOnAction(e->{
            Stage stage = new Stage();
            stage.setTitle("Player 3's Hand");
            Pane pane = new Pane();
            Text t1 = new Text(25, 50, "Player 3's Cards:");
            Text t2 = new Text(25, 250, p3.getCardsAsStringNL());
            t1.setFont(new Font(22));
            t2.setFont(new Font(22));
            pane.getChildren().addAll(t1, t2);
            Scene ss = new Scene(pane, 250, 500);
            stage.setScene(ss);
            stage.show();
        });

        Button p4Cards = new Button("Player 4 Cards");
        mainPane.getChildren().add(p4Cards);
        p4Cards.setLayoutX(660);
        p4Cards.setLayoutY(150);
        p4Cards.setOnAction(e->{
            Stage stage = new Stage();
            stage.setTitle("Player 4's Hand");
            Pane pane = new Pane();
            Text t1 = new Text(25, 50, "Player 4's Cards:");
            Text t2 = new Text(25, 250, p4.getCardsAsStringNL());
            t1.setFont(new Font(22));
            t2.setFont(new Font(22));
            pane.getChildren().addAll(t1, t2);
            Scene ss = new Scene(pane, 250, 500);
            stage.setScene(ss);
            stage.show();
        });

        Button p5Cards = new Button("Player 5 Cards");
        mainPane.getChildren().add(p5Cards);
        p5Cards.setLayoutX(660);
        p5Cards.setLayoutY(200);
        p5Cards.setOnAction(e->{
            Stage stage = new Stage();
            stage.setTitle("Player 5's Hand");
            Pane pane = new Pane();
            Text t1 = new Text(25, 50, "Player 5's Cards:");
            Text t2 = new Text(25, 250, p5.getCardsAsStringNL());
            t1.setFont(new Font(22));
            t2.setFont(new Font(22));
            pane.getChildren().addAll(t1, t2);
            Scene ss = new Scene(pane, 250, 500);
            stage.setScene(ss);
            stage.show();
        });

        Button p6Cards = new Button("Player 6 Cards");
        mainPane.getChildren().add(p6Cards);
        p6Cards.setLayoutX(660);
        p6Cards.setLayoutY(250);
        p6Cards.setOnAction(e->{
            Stage stage = new Stage();
            stage.setTitle("Player 6's Hand");
            Pane pane = new Pane();
            Text t1 = new Text(25, 50, "Player 6's Cards:");
            Text t2 = new Text(25, 250, p6.getCardsAsStringNL());
            t1.setFont(new Font(22));
            t2.setFont(new Font(22));
            pane.getChildren().addAll(t1, t2);
            Scene ss = new Scene(pane, 250, 500);
            stage.setScene(ss);
            stage.show();
        });

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
    private void move(Player p, int roll){
        BreadthFirstPaths b = new BreadthFirstPaths(g, p.getCurrentSpace());
        ArrayList<Integer> nulls = new ArrayList<>();
        nulls.add(195); nulls.add(194); nulls.add(196); nulls.add(184); nulls.add(183); nulls.add(197);

        for(int i = 1; i < 198; i++){

            if(b.distTo(i) <= roll && b.distTo(i) % 2 == roll % 2 && i != p.getCurrentSpace()){
                if (!nulls.contains(i)) {
                    tiles[i].highlight();
                    Tile.highlightedTiles.add(tiles[i]);
                }
            }
        }
    }
}
