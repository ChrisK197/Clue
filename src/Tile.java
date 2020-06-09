import java.util.ArrayList;

public class Tile {
    private int x;
    private int y;
    private int num;
    private ArrayList<Player> players;

    public Tile (int x, int y, int num) {
        this.x = x;
        this.y = y;
        this.num = num;
        players = new ArrayList<>();
    }

    public void addPlayer(Player p) {
        players.add(p);
    }

    public void removePlayer(Player p) {
        players.remove(p);
    }

    public int getNum(){
        return num;
    }
}
