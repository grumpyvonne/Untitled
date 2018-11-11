package Controller;

import Model.*;
import Model.Character;
import javafx.scene.Node;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DataController {
    private LevelData levelData;
    private StatusController statusController;
    private List<Node> enemies;
    private List<Node> platforms;
    private List<Node> trees;
    private List<Node> waterPlatforms;
    private List<Node> initialCollectiblesList;
    private Character character;
    private Collectibles collectibles;
    private Shot shot;
    private List<Node> shots;
    private Enemy enemy;
    private Node collectible;

    public DataController(StatusController statusController, Character character) {
        this.statusController = statusController;
        Image enemyImage = new Image("images/Object_10.png");
        initialCollectiblesList = new ArrayList<>();
        levelData = new LevelData();
        collectibles = new Collectibles();
        shots = new ArrayList<>();
        shot = new Shot();
        enemy = new Enemy();
        enemies = new ArrayList<>();
        levelData.initializeContent();
        setPlatforms(levelData.getPlatforms());
        setTrees(levelData.getTrees());
        setWaterPlatforms(levelData.getWaterPlatforms());
        this.character = character;
        int randomX = ThreadLocalRandom.current().nextInt(levelData.getLevelWidth() + 1);
        int randomY = ThreadLocalRandom.current().nextInt(levelData.getLevelHeight() + 1);
        System.out.println(levelData.getLevelHeight());
        System.out.println("X - " + randomX + "; Y - " + randomY);
    }

    public ImageView createEnemy(){
        int randomX = ThreadLocalRandom.current().nextInt(levelData.getLevelWidth() + 1);
        int randomY = ThreadLocalRandom.current().nextInt(levelData.getLevelHeight() + 1);
        ImageView singleEnemy = enemy.createEnemy(randomX, randomY, 80, 80, "images/pigeon.png");
        enemies.add(singleEnemy);
        return singleEnemy;
    }

    public ImageView createEnemy(int x, int y){
        ImageView singleEnemy = enemy.createEnemy(x, y, 80, 80, "images/pigeon.png");
        enemies.add(singleEnemy);
        return singleEnemy;
    }

    public List<Node> getEnemies() {
        return enemies;
    }

    public void createCollectiblesOnTrees() {
        for (Node tree : trees) {
            initialCollectiblesList.add(collectibles.createCollectible((int)tree.getTranslateX() + 40, (int) tree.getTranslateY() + 20, statusController.getCollectibleImageA()));
            initialCollectiblesList.add(collectibles.createCollectible((int)tree.getTranslateX() + 80, (int) tree.getTranslateY() + 40, statusController.getCollectibleImageA()));
        }
    }

    public ImageView createShot(){
        ImageView singleShot = shot.createShot((int) character.getCharacter().getTranslateX(), (int) character.getCharacter().getTranslateY(), 30, 30, "images/coll.png");
        shots.add(singleShot);
        return singleShot;
    }

    public void addToShotsList(Node shot){
        shots.add(shot);
    }

    public List<Node> getShotsList(){
        return shots;
    }

    public void setShotsList(List<Node> shotsList){
        this.shots = shotsList;
    }

    public Shot getShot() {
        return shot;
    }

    public boolean checkIntersectTree() {
        boolean isColliding = false;
        for (Node tree : trees) {
            if (character.getCharacter().getBoundsInParent().intersects(tree.getBoundsInParent()))
                isColliding = true;
        }
        return isColliding;
    }

    public boolean checkForFalling() {
        boolean drowned = false;
        for (Node water : waterPlatforms) {
            if (character.getCharacter().getBoundsInParent().intersects(water.getBoundsInParent())) {
                System.out.println("Drowned");
                character.setGravity(new Point2D(0, 0));
                drowned = true;
            }
        }
        return drowned;
    }

    public boolean checkIntersectCollect() {
        boolean isColliding = false;
        for (Node collectible : initialCollectiblesList) {
            if (character.getCharacter().getBoundsInParent().intersects(collectible.getBoundsInParent())) {
                isColliding = true;
                setNodeFromCollision(collectible);
            }
        }
        return isColliding;
    }


    private void setNodeFromCollision(Node collectible) {
        this.collectible = collectible;
    }

    public Node getCollectibleFromCollision() {
        return collectible;
    }

    public List<Node> getInitialCollectiblesList() {
        return initialCollectiblesList;
    }

    private void setPlatforms(List<Node> platforms) {
        this.platforms = platforms;
    }

    private void setTrees(List<Node> trees) {
        this.trees = trees;
    }

    public void setInitialCollectiblesList(List<Node> initialCollectiblesList) {
        this.initialCollectiblesList = initialCollectiblesList;
    }

    private void setWaterPlatforms(List<Node> waterPlatforms) {
        this.waterPlatforms = waterPlatforms;
    }


    public List<Node> getTrees() {
        return trees;
    }

    public List<Node> getPlatforms() {
        return platforms;
    }

    public List<Node> getWaterPlatforms() {
        return waterPlatforms;
    }

    public int getLevelWidth() {
        return levelData.getLevelWidth();
    }

    public int getLevelHeight() {
        return levelData.getLevelHeight();
    }

    public Character getCharacter() {
        return character;
    }
}
