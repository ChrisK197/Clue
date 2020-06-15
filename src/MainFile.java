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
    private Tile[] tiles;
    private Button diceRoll;
    private Player currentPlayer;

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

        Player p1 = new Player(new Image("green.png"), "Mr. Green", 1, 183);
        currentPlayer = p1;
        ImageView p1view = p1.getImageView();
        p1view.setFitWidth(20);
        p1view.setFitHeight(20);
        p1view.setX(255);
        p1view.setY(605);
        mainPane.getChildren().add(p1view);

        Player p2 = new Player(new Image("mustard.png"), "Colonel Mustard", 2, 196);
        ImageView p2view = p2.getImageView();
        p2view.setFitWidth(20);
        p2view.setFitHeight(20);
        p2view.setX(585);
        p2view.setY(200);
        mainPane.getChildren().add(p2view);

        Player p3 = new Player(new Image("scarlet.png"), "Miss Scarlet", 3, 194);
        ImageView p3view = p3.getImageView();
        p3view.setFitWidth(20);
        p3view.setFitHeight(20);
        p3view.setX(420);
        p3view.setY(35);
        mainPane.getChildren().add(p3view);

        Player p4 = new Player(new Image("plum.png"), "Professor Plum", 4, 195);
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

        //Rooms are being made below, the coordinates need to be changed later
        Room study = new Room(63-24, 28, 191, "Study");
        Room hall = new Room(351, 28, 192, "Hall");
        Room lounge = new Room(663, 28, 193, "Lounge");
        Room library = new Room(39, 168, 190, "Library");
        Room diningRoom = new Room(624, 252, 189, "Dining Room");
        Room billiardRoom = new Room(39, 336, 188, "Billiard Room");
        Room conservatory = new Room(39, 532, 185, "Conservatory");
        Room ballRoom = new Room(311, 476, 186, "Ballroom");
        Room kitchen = new Room(504, 702, 187, "Kitchen");

        tiles = new Tile[198]; //we don't have a 0, we started at 1
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
                        System.out.println(Tile.highlightedTiles);
                        for(int i = Tile.highlightedTiles.size() - 1; i >= 0; i--) {
                            Tile.highlightedTiles.remove(i).unhighlight();
                        }
                        System.out.println(Tile.highlightedTiles);
                        currentPlayer.getImageView().setX(tiles[finalCount].getX());
                        currentPlayer.getImageView().setY(tiles[finalCount].getY());
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
                    study.setP1x(xval + 23.8*col);
                    study.setP1y(yval + 24*row);
                }
                else if(row==3 && col==12){
                    study.setP2x(xval + 23.8*col);
                    study.setP2y(yval + 24*row);
                }
                else if(row==3 && col==11){
                    study.setP3x(xval + 23.8*col);
                    study.setP3y(yval + 24*row);
                }
                else if(row==3 && col==10){
                    study.setP4x(xval + 23.8*col);
                    study.setP4y(yval + 24*row);
                }
                //lounge
                else if(row==2 && col==22){
                    study.setP1x(xval + 23.8*col);
                    study.setP1y(yval + 24*row);
                }
                else if(row==2 && col==21){
                    study.setP2x(xval + 23.8*col);
                    study.setP2y(yval + 24*row);
                }
                else if(row==2 && col==20){
                    study.setP3x(xval + 23.8*col);
                    study.setP3y(yval + 24*row);
                }
                else if(row==2 && col==19){
                    study.setP4x(xval + 23.8*col);
                    study.setP4y(yval + 24*row);
                }
                //library
                else if(row==6 && col==5){
                    study.setP1x(xval + 23.8*col);
                    study.setP1y(yval + 24*row);
                }
                else if(row==6 && col==4){
                    study.setP2x(xval + 23.8*col);
                    study.setP2y(yval + 24*row);
                }
                else if(row==6 && col==3){
                    study.setP3x(xval + 23.8*col);
                    study.setP3y(yval + 24*row);
                }
                else if(row==6 && col==2){
                    study.setP4x(xval + 23.8*col);
                    study.setP4y(yval + 24*row);
                }
                //Dining Room
                else if(row==12 && col==22){
                    study.setP1x(xval + 23.8*col);
                    study.setP1y(yval + 24*row);
                }
                else if(row==12 && col==21){
                    study.setP2x(xval + 23.8*col);
                    study.setP2y(yval + 24*row);
                }
                else if(row==12 && col==20){
                    study.setP3x(xval + 23.8*col);
                    study.setP3y(yval + 24*row);
                }
                else if(row==12 && col==19){
                    study.setP4x(xval + 23.8*col);
                    study.setP4y(yval + 24*row);
                }
                //Billiard Room
                else if(row==14 && col==4){
                    study.setP1x(xval + 23.8*col);
                    study.setP1y(yval + 24*row);
                }
                else if(row==14 && col==3){
                    study.setP2x(xval + 23.8*col);
                    study.setP2y(yval + 24*row);
                }
                else if(row==14 && col==2){
                    study.setP3x(xval + 23.8*col);
                    study.setP3y(yval + 24*row);
                }
                else if(row==14 && col==1){
                    study.setP4x(xval + 23.8*col);
                    study.setP4y(yval + 24*row);
                }
                //Conservatory
                else if(row==21 && col==4){
                    study.setP1x(xval + 23.8*col);
                    study.setP1y(yval + 24*row);
                }
                else if(row==21 && col==3){
                    study.setP2x(xval + 23.8*col);
                    study.setP2y(yval + 24*row);
                }
                else if(row==21 && col==2){
                    study.setP3x(xval + 23.8*col);
                    study.setP3y(yval + 24*row);
                }
                else if(row==21 && col==1){
                    study.setP4x(xval + 23.8*col);
                    study.setP4y(yval + 24*row);
                }
                //Ballroom
                else if(row==20 && col==13){
                    study.setP1x(xval + 23.8*col);
                    study.setP1y(yval + 24*row);
                }
                else if(row==20 && col==12){
                    study.setP2x(xval + 23.8*col);
                    study.setP2y(yval + 24*row);
                }
                else if(row==20 && col==11){
                    study.setP3x(xval + 23.8*col);
                    study.setP3y(yval + 24*row);
                }
                else if(row==20 && col==10){
                    study.setP4x(xval + 23.8*col);
                    study.setP4y(yval + 24*row);
                }
                //kitchen
                else if(row==20 && col==22){
                    study.setP1x(xval + 23.8*col);
                    study.setP1y(yval + 24*row);
                }
                else if(row==20 && col==21){
                    study.setP2x(xval + 23.8*col);
                    study.setP2y(yval + 24*row);
                }
                else if(row==20 && col==20){
                    study.setP3x(xval + 23.8*col);
                    study.setP3y(yval + 24*row);
                }
                else if(row==20 && col==19){
                    study.setP4x(xval + 23.8*col);
                    study.setP4y(yval + 24*row);
                }
            }
        }


        diceRoll= new Button();
        diceRoll.setText("Roll");
        diceRoll.setPrefSize(50, 50);
        //This is just the basic move function setup, not anything that would work as of now, TO DO
        diceRoll.setOnAction(e->{
            diceRoll.setDisable(true);
            if (turnCounter == 0) {
                currentPlayer = p1;
                int roll = p1.move();
                move(p1, roll);
                turnCounter++;
            }
            else if (turnCounter == 1) {
                currentPlayer = p2;
                int roll = p2.move();
                move(p2, roll);
                turnCounter++;
            }
            else if (turnCounter == 2) {
                currentPlayer = p3;
                int roll = p3.move();
                move(p3, roll);
                turnCounter++;
            }
            else {
                currentPlayer = p4;
                int roll = p4.move();
                move(p4, roll);
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
    private void move(Player p, int roll){
        BreadthFirstPaths b = new BreadthFirstPaths(g, p.getCurrentSpace());

        for(int i = 1; i < 198; i++){

            if(b.distTo(i) <= roll && b.distTo(i) % 2 == roll % 2 && i != p.getCurrentSpace()){
                tiles[i].highlight();
                Tile.highlightedTiles.add(tiles[i]);
            }
        }
    }
}
