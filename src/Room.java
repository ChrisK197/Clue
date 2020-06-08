public class Room extends Tile{
    private String name;

    public Room(int x, int y, String name){
        super(x,y);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
