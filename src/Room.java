public class Room extends Tile{
    private String name;

    public Room(int x, int y, int num, String name){
        super(x,y, num);
        this.name = name;
        //TODO give rooms a different x, y coordinate for each player
    }

    public String getName() {
        return name;
    }
}
