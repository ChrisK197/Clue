import java.util.ArrayList;

public class Tile {
    private int x;
    private int y;
    private ArrayList<Player> players;

    public Tile (int x, int y) {
        this.x = x;
        this.y = y;
        players = new ArrayList<>();
    }

    public void addPlayer(Player p) {
        players.add(p);
    }

    public void removePlayer(Player p) {
        players.remove(p);
    }
}
