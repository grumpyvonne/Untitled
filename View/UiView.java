package View;

import Controller.StatusController;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class UiView {
    private Pane uiRoot;
    private Pane uiLives;
    private Image lifeImage;
    private ImageView[] livesArr;
    private StatusController statusController;
    private Text textCollectible;
    private Text textScore;

    public UiView(StatusController statusController) {
        this.statusController = statusController;
        uiRoot = new Pane();
        uiLives = new Pane();
        uiRoot.getChildren().add(uiLives);
        initializeLives();
        initializeCollectibles();
        initializeKillsScore();
    }

    private void initializeLives() {
        lifeImage = new Image("images/heart.png", 30, 30, true, true);
        livesArr = new ImageView[3];
        for (int livesCount = 0; livesCount < statusController.getLivesCount(); livesCount++) {
            livesArr[livesCount] = new ImageView(lifeImage);
            livesArr[livesCount].setLayoutX(10 + livesCount * lifeImage.getWidth());
            livesArr[livesCount].setLayoutY(10);
            this.uiLives.getChildren().add(livesArr[livesCount]);
        }
    }

    private void initializeCollectibles(){
        ImageView collectible = new ImageView(statusController.getCollectibleImageA());
        collectible.setLayoutX(500);
        collectible.setLayoutY(10);
        this.uiRoot.getChildren().add(collectible);
        textCollectible = new Text();
        textCollectible.setText(Integer.toString( statusController.getCollectiblesCount()));
        textCollectible.setFont(Font.font("Verdana", 20));
        textCollectible.setTranslateX(540);
        textCollectible.setTranslateY(30);
        this.uiRoot.getChildren().add(textCollectible);
    }

    private void initializeKillsScore(){
        Image killsImage = new Image("images/pigeon.png", 40, 40, true, true);
        ImageView kills = new ImageView(killsImage);
        kills.setLayoutX(1000);
        kills.setLayoutY(10);
        this.uiRoot.getChildren().add(kills);
        textScore = new Text();
        textScore.setText(Integer.toString(statusController.getScore()));
        textScore.setFont(Font.font("Verdana", 20));
        textScore.setTranslateX(1050);
        textScore.setTranslateY(30);
        this.uiRoot.getChildren().add(textScore);
    }

    public void addKillScore(){
        statusController.setScore(statusController.getScore() + 10);
        updateScore();
    }

    public void updateScore(){
        textScore.setText(Integer.toString(statusController.getScore()));
    }


    public void addCollectible(){
            statusController.setCollectiblesCount(statusController.getCollectiblesCount() + 1);
            textCollectible.setText(Integer.toString(statusController.getCollectiblesCount()));
    }

    public void removeCollectible(){
        if(statusController.getCollectiblesCount() > 0){
            statusController.setCollectiblesCount(statusController.getCollectiblesCount() - 1);
            textCollectible.setText(Integer.toString(statusController.getCollectiblesCount()));
        }
    }


    public void addLife() {
        if(statusController.getLivesCount() < 3) {
            statusController.setLivesCount(statusController.getLivesCount() + 1);
            ImageView newLife = new ImageView(lifeImage);
            newLife.setLayoutX(10 + (statusController.getLivesCount() - 1) * lifeImage.getWidth());
            newLife.setLayoutY(10);
            livesArr[livesArr.length - 1] = new ImageView(lifeImage);
            this.uiLives.getChildren().add(newLife);
        }
    }

    public void removeLife(){
        if(statusController.getLivesCount() > 0){
            statusController.setLivesCount(statusController.getLivesCount() - 1);
            this.uiLives.getChildren().remove(uiLives.getChildren().size() - 1);
        }
    }

    public Pane getUiRoot() {
        return uiRoot;
    }

}
