import javafx.scene.Scene;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;

public class Player {
    private Card[] cards;
    private ImageView icon;
    private String name;
    private int num;
    private String currentRoom = null;
    private int currentSpace;
    private int numCards = 0;

    private String musicFile = "diceRoll.mp3";
    private Media sound = new Media(new File(musicFile).toURI().toString());
    private MediaPlayer mediaPlayer = new MediaPlayer(sound);

    public Player(Image image, String name, int num, int currSpace){
        icon = new ImageView(image);
        this.name = name;
        this.num = num;
        currentSpace = currSpace;
        //Players 1 and 2 will always have one more card than Players 3 and 4
        if(num == 1 || num == 2)
            cards = new Card[5];
        else
            cards = new Card[4];
    }

    public void addCard(Card c){
        cards[numCards++] = c;
    }

    public ImageView getImageView() {
        return icon;
    }

    public void setImageView(ImageView icon) {
        this.icon = icon;
    }

    public Card[] getCards() {
        return cards;
    }

    public String getName() {
        return name;
    }

    public int getNum() {
        return num;
    }

    public String getCurrentRoom() {
        return currentRoom;
    }
    public int getCurrentSpace(){
        return currentSpace;
    }

    public void setCards(Card[] cards) {
        this.cards = cards;
    }

    public void setCurrentRoom(String currentRoom) {
        this.currentRoom = currentRoom;
    }

    public int getNumCards() {
        return numCards;
    }

    protected int move(){
        int roll = (int)(Math.random()*6)+1;
        int roll2 = (int)(Math.random()*6)+1;
        Stage stage = new Stage();
        stage.setTitle("Roll");
        Pane p = new Pane();
        Text t = new Text(115, 250, "Player " + num + " rolled a " + roll + " and a " + roll2);
        t.setFont(new Font(26));
        p.getChildren().add(t);
        Scene ss = new Scene(p, 500, 500);
        stage.setScene(ss);
        stage.show();

        roll += roll2; //Total roll value

        mediaPlayer.setVolume(100);
        mediaPlayer.play();

        return roll;

        //int moves_done = 0;

        //Move function - TODO

        //Use BFS to find the squares (roll) distance or less away
        //Highlight those squares
        //Check for a mouse click
        //Move

        /*while (moves_done < roll) {
        }*/
        //Return to main
    }
}
