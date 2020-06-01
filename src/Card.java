public class Card {
    private String type;
    private String name;
    public Card(String name, String type){
        this.name = name;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String toString(){
        return name;
    }
}
