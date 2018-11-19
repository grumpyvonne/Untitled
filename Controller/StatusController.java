package Controller;

import Model.Collectibles;
import Model.Lives;
import Model.Score;
import javafx.scene.image.Image;

public class StatusController {
    private Lives lives;
    private Collectibles collectibles;
    private Score score;

    public StatusController() {
        lives = new Lives();
        collectibles = new Collectibles();
        score = new Score();
    }

    public int getScore(){
        return this.score.getScore();
    }

    public void setScore(int newScore){
        this.score.setScore(newScore);
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
        return this.collectibles.getAppleCollectible();
    }
}

