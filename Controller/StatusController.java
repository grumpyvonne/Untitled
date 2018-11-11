package Controller;

import Model.Collectibles;
import Model.Lives;
import javafx.scene.image.Image;

public class StatusController {
    private Lives lives;
    private Collectibles collectibles;

    public StatusController() {
        lives = new Lives();
        collectibles = new Collectibles();
    }

    public int getCollectiblesCount() {
        return this.collectibles.getCount();
    }

    public void setCollectiblesCount(int num){
        this.collectibles.setCount(num);
    }

    public int getLivesCount() {
        return this.lives.getLivesCount();
    }

    public void setLivesCount(int num) {
        this.lives.setLivesCount(num);
    }

    public Image getCollectibleImageA(){
        return this.collectibles.getAppleCollectable();
    }
}

