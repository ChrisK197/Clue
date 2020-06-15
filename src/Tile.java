
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;

public class Tile {
    private double x;
    private double y;
    private int num;
    private ArrayList<Player> players;
    private ImageView highlighted = new ImageView(new Image("highlighted_tile.png"));
    public static ArrayList<Tile> highlightedTiles = new ArrayList<>();
    //I think this variable is not necessary.



    public Tile (double x, double y, int num) {
        this.x = x;
        this.y = y;
        this.num = num;
        highlighted.setX(x);
        highlighted.setY(y);
        highlighted.setFitWidth(24);
        highlighted.setFitHeight(24);
        highlighted.setVisible(false);
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

    public void highlight(){
        highlighted.setVisible(true);
    }

    public void unhighlight(){
        highlighted.setVisible(false);
    }

    public ImageView getImageView() {
        return highlighted;
    }

    public boolean isHighlighted () {
        return highlighted.isVisible();
    }


    public String toString(){
        return "Tile " + num + ": (" + x + ", " + y + ")";
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
