package Model;

import javafx.scene.Node;
import javafx.scene.image.ImageView;

public class Shot {
    private ImageView shot;

    public ImageView createShot(int x, int y, int width, int height, String url){
        shot = new ImageView(url);
        shot.setTranslateX(x);
        shot.setTranslateY(y);
        shot.setFitHeight(height);
        shot.setFitWidth(width);
        shot.setPreserveRatio(true);
        return shot;
    }

    public void moveRight(Node shot, int speed){
        shot.setTranslateX(shot.getTranslateX() + speed);
    }

    public void moveLeft(Node shot, int speed){
        shot.setTranslateX(shot.getTranslateX() - speed);
    }

}
