public class Player {
    private Card[] cards;
    private String name;
    private int num;
    private String currentRoom = null;
    public Player(String name, int num, int numOfCards){
        this.name = name;
        this.num = num;
        cards = new Card[numOfCards];
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
