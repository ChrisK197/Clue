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
    private Room currentRoom = null;
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

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentSpace(int currentSpace) {
        this.currentSpace = currentSpace;
    }

    public int getCurrentSpace(){
        return currentSpace;
    }

    public void setCards(Card[] cards) {
        this.cards = cards;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public int getNumCards() {
        return numCards;
    }

    public String getCardsAsStringNL() {
        String s = "";
        for (Card c : cards) {
            s += c.getName();
            s += "\n";
        }
        return s;
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
    }
}
