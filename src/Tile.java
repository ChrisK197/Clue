
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;

public class Tile {
    private int x;
    private int y;
    private int num;
    private ArrayList<Player> players;
    private ImageView highlighted = new ImageView(new Image("highlighted_tile.png"));
    public static ArrayList<Tile> highlightedTiles = new ArrayList<>();

    public Tile (int x, int y, int num) {
        this.x = x;
        this.y = y;
        this.num = num;
        highlighted.setX(x);
        highlighted.setY(y);
        highlighted.setFitWidth(24);
        highlighted.setFitHeight(24);
        highlighted.setOnMouseClicked(e->{
            highlighted.setVisible(false);
            System.out.println(highlightedTiles);
            for(int i = highlightedTiles.size() - 1; i >= 0; i--){
                if(!highlightedTiles.get(i).equals(this))
                    highlightedTiles.remove(i).unhighlight();
            }
            System.out.println(highlightedTiles);
        });
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
    public String toString(){
        return "Tile " + num + ": (" + x + ", " + y + ")";
    }
}
