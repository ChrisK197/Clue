import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Room extends Tile{
    private String name;
    private double p1x;
    private double p1y;
    private double p2x;
    private double p2y;
    private double p3x;
    private double p3y;
    private double p4x;
    private double p4y;
    private double p5x;
    private double p5y;
    private double p6x;
    private double p6y;
    private ImageView imageView;

    public Room(int x, int y, int num, String name, String im){
        super(x,y, num);
        this.name = name;
        imageView = new ImageView(new Image(im));
        imageView.setX(x);
        imageView.setY(y);
        imageView.setScaleX(.8305);
        imageView.setScaleY(.7855);
        imageView.setVisible(false);
    }

    @Override
    public void addPlayer(Player p){
        ImageView i = p.getImageView();
        p.setCurrentSpace(getNum());
        p.setCurrentRoom(null);
        if(p.getNum() == 1){
            i.setX(p1x); i.setY(p1y);
        }
        else if(p.getNum() == 2){
            i.setX(p2x); i.setY(p2y);
        }
        else if(p.getNum() == 3){
            i.setX(p3x); i.setY(p3y);
        }
        else if(p.getNum() == 4){
            i.setX(p4x); i.setY(p4y);
        }
        else if(p.getNum() == 5){
            i.setX(p5x); i.setY(p5y);
        }
        else if(p.getNum() == 6){
            i.setX(p6x); i.setY(p6y);
        }
    }

    @Override
    public ImageView getImageView() {
        return imageView;
    }
    @Override
    public void highlight(){
        imageView.setVisible(true);
    }
    @Override
    public void unhighlight(){
        imageView.setVisible(false);
    }

    @Override
    public boolean isHighlighted() {
        return imageView.isVisible();
    }

    public double getP1x() {
        return p1x;
    }

    public void setP1x(double p1x) {
        this.p1x = p1x;
    }

    public double getP1y() {
        return p1y;
    }

    public void setP1y(double p1y) {
        this.p1y = p1y;
    }

    public double getP2x() {
        return p2x;
    }

    public void setP2x(double p2x) {
        this.p2x = p2x;
    }

    public double getP2y() {
        return p2y;
    }

    public void setP2y(double p2y) {
        this.p2y = p2y;
    }

    public double getP3x() {
        return p3x;
    }

    public void setP3x(double p3x) {
        this.p3x = p3x;
    }

    public double getP3y() {
        return p3y;
    }

    public void setP3y(double p3y) {
        this.p3y = p3y;
    }

    public double getP4x() {
        return p4x;
    }

    public void setP4x(double p4x) {
        this.p4x = p4x;
    }

    public double getP4y() {
        return p4y;
    }

    public void setP4y(double p4y) {
        this.p4y = p4y;
    }

    public double getP5x() {
        return p5x;
    }

    public void setP5x(double p5x) {
        this.p5x = p5x;
    }

    public double getP5y() {
        return p5y;
    }

    public void setP5y(double p5y) {
        this.p5y = p5y;
    }

    public double getP6x() {
        return p6x;
    }

    public void setP6x(double p6x) {
        this.p6x = p6x;
    }

    public double getP6y() {
        return p6y;
    }

    public void setP6y(double p6y) {
        this.p6y = p6y;
    }

    public String getName() {
        return name;
    }
}
