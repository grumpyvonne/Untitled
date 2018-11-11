package Model;

import javafx.scene.Node;
import javafx.scene.image.ImageView;

public class Enemy {

    private ImageView enemy;

    public ImageView createEnemy(int x, int y, int width, int height, String url){
        enemy = new ImageView(url);
        enemy.setTranslateX(x);
        enemy.setTranslateY(y);
        enemy.setFitHeight(height);
        enemy.setFitWidth(width);
        enemy.setPreserveRatio(true);
        return enemy;
    }

    public void moveDown(Node node, int speed){
        node.setTranslateY(node.getTranslateY() + speed);
    }
}
