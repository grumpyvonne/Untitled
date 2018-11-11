package Model;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class Collectibles {

    private int count;
    private Image appleCollectable;

    public Collectibles() {
        this.count = 0;
        appleCollectable = new Image("images/coll.png", 30, 30, true, true);
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

    public Image getAppleCollectable() {
        return appleCollectable;
    }
}
