package Model;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Collectibles {

    private int count;
    private Image appleCollectible;

    public Collectibles() {
        this.count = 0;
        appleCollectible = new Image("images/coll.png", 30, 30, true, true);
    }


    public Node createCollectible(int x, int y, Image image) {
        ImageView collectible = new ImageView(image);
        collectible.setTranslateX(x);
        collectible.setTranslateY(y);
        return collectible;
    }


    public void setCount(int newCount) {
        this.count = newCount;
    }

    public int getCount() {
        return this.count;
    }

    public Image getAppleCollectible() {
        return appleCollectible;
    }
}
