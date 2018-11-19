package Model;

import javafx.scene.Node;
import javafx.scene.image.ImageView;

public class Enemy {

    private ImageView enemy;
    private int count;
    public Enemy(){
        this.count = 0;
    }

    public ImageView createEnemy(int x, int y, int width, int height, String url){
        enemy = new ImageView(url);
        enemy.setTranslateX(x);
        enemy.setTranslateY(y);
        enemy.setFitHeight(height);
        enemy.setFitWidth(width);
        enemy.setPreserveRatio(true);
        setCount(getCount() + 1);
        return enemy;
    }

    public void moveDown(Node node, int speed){
        node.setTranslateY(node.getTranslateY() + speed);
    }

    public void stop(Node node){
        node.setTranslateX(node.getTranslateX());
    }

    public void setCount(int newCount){
        this.count = newCount;
    }
    public int getCount(){
        return this.count;
    }
}
