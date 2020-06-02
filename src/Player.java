import javafx.scene.image.Image;

import javafx.scene.image.ImageView;

public class Player {
    private Card[] cards;
    private ImageView icon;
    private String name;
    private int num;
    private String currentRoom = null;
    private int numCards = 0;
    public Player(Image image, String name, int num){
        icon = new ImageView(image);
        this.name = name;
        this.num = num;
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

    public void setCards(Card[] cards) {
        this.cards = cards;
    }

    public void setCurrentRoom(String currentRoom) {
        this.currentRoom = currentRoom;
    }
}
