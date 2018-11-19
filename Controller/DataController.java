package Controller;

import Model.*;
import Model.Character;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class DataController {
    private LevelData levelData;
    private StatusController statusController;
    private List<Node> enemiesList;
    private List<Node> platforms;
    private List<Node> treesList;
    private HashMap<Node, Integer> treesHashMap;
    private List<Node> waterPlatforms;
    private List<Node> initialCollectiblesList;
    private List<Node> collectiblesList;
    private Character character;
    private Collectibles collectibles;
    private Shot shot;
    private Enemy enemy;
    private Node collectible;
    private HashMap<Node, String> shotsMap;
    private double time = 0;
    private int level = 0;
//    private boolean isUpdatedColl = false;

    public DataController(StatusController statusController, Character character) {
        this.statusController = statusController;
        shotsMap = new HashMap<Node, String>();
        initialCollectiblesList = new ArrayList<>();
        collectiblesList = new ArrayList<>();
        levelData = new LevelData();
        collectibles = new Collectibles();
        shot = new Shot();
        enemy = new Enemy();
        enemiesList = new ArrayList<>();
        levelData.initializeContent();
        this.treesHashMap = levelData.getTreesHashMap();
        setPlatforms(levelData.getPlatforms());
        setTreesList(levelData.getTreesList());
        setWaterPlatforms(levelData.getWaterPlatforms());
        this.character = character;
    }

    public List<Node> getEnemiesList() {
        return enemiesList;
    }

    public void setEnemiesList(List<Node> newList) {
        this.enemiesList = newList;
    }

    public void createCollectiblesOnTrees() {
        for (Map.Entry<Node, Integer> tree : treesHashMap.entrySet()) {
            if (tree.getValue() < 2) {
                initialCollectiblesList.add(collectibles.createCollectible((int) (tree.getKey().getTranslateX() + 40), (int) (tree.getKey().getTranslateY() + 20), statusController.getCollectibleImageA()));
                treesHashMap.put(tree.getKey(), tree.getValue() + 1);
                initialCollectiblesList.add(collectibles.createCollectible((int) (tree.getKey().getTranslateX() + 80), (int) (tree.getKey().getTranslateY() + 40), statusController.getCollectibleImageA()));
                treesHashMap.put(tree.getKey(), tree.getValue() + 1);
            }
        }
    }

    public boolean waitForCollectibles() {
        boolean isUpdatedColl = false;
        if (initialCollectiblesList.size() == 0) {
            time += 0.016;
            System.out.println(time);
            updateCollectibles();
            isUpdatedColl = true;
        }
        return isUpdatedColl;
    }

    public void updateCollectibles() {

        if (time > 3) {
            for (Node tree : treesList) {
                initialCollectiblesList.add(collectibles.createCollectible((int) (tree.getTranslateX() + 40), (int) (tree.getTranslateY() + 20), statusController.getCollectibleImageA()));
                initialCollectiblesList.add(collectibles.createCollectible((int) (tree.getTranslateX() + 80), (int) (tree.getTranslateY() + 40), statusController.getCollectibleImageA()));
                System.out.println("coll size - " + initialCollectiblesList.size());
            }
            time = 0;
        }
    }

//    public void updateCollectibles() {
//        for (Map.Entry<Node, Integer> tree : treesHashMap.entrySet()) {
//            if (tree.getValue() < 2) {
//                collectiblesList.add(collectibles.createCollectible((int) (tree.getKey().getTranslateX() + 40), (int) (tree.getKey().getTranslateY() + 20), statusController.getCollectibleImageA()));
//                treesHashMap.put(tree.getKey(), tree.getValue() + 1);
//                collectiblesList.add(collectibles.createCollectible((int) (tree.getKey().getTranslateX() + 80), (int) (tree.getKey().getTranslateY() + 40), statusController.getCollectibleImageA()));
//                treesHashMap.put(tree.getKey(), tree.getValue() + 1);
//            }
//        }
//    }

    public ImageView createShot() {
        ImageView singleShot = shot.createShot((int) character.getCharacter().getTranslateX(), (int) character.getCharacter().getTranslateY(), 30, 30, "images/coll.png");
        return singleShot;
    }

    public void createEnemy() {
        int randomX = ThreadLocalRandom.current().nextInt(levelData.getLevelWidth() + 1);
        int randomY = ThreadLocalRandom.current().nextInt(levelData.getLevelHeight() + 1);
        ImageView singleEnemy = enemy.createEnemy(randomX, randomY, 80, 80, "images/pigeon.png");
        enemiesList.add(singleEnemy);
    }

    public int checkLevel() {
        if (statusController.getCollectiblesCount() < 10)
            level = 2;
        else if(statusController.getCollectiblesCount() > 10)
            level = 4;
        else if (statusController.getCollectiblesCount() > 20)
            level = 5;
        else if (statusController.getCollectiblesCount() > 30)
            level = 6;
        else if (statusController.getCollectiblesCount() > 40)
            level = 7;
        else if (statusController.getCollectiblesCount() > 70)
            level = 8;
        else if (statusController.getCollectiblesCount() > 90)
            level = 10;
        return level;
    }

    public void addToShotsMap(Node shot, String turn) {
        shotsMap.put(shot, turn);
    }

    public HashMap<Node, String> getShotsMap() {
        return shotsMap;
    }

    public void setShotsMap(HashMap<Node, String> newHashMap) {
        this.shotsMap = new HashMap<Node, String>(newHashMap);
    }

    public void setNodeFromCollision(Node collectible) {
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

    private void setTreesList(List<Node> treesList) {
        this.treesList = treesList;
    }

    public void setInitialCollectiblesList(List<Node> initialCollectiblesList) {
        this.initialCollectiblesList = initialCollectiblesList;
    }

    private void setWaterPlatforms(List<Node> waterPlatforms) {
        this.waterPlatforms = waterPlatforms;
    }


    public List<Node> getTreesList() {
        return treesList;
    }

    public HashMap<Node, Integer> getTreesHashMap() {
        return treesHashMap;
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

    public Character getCharacterClass() {
        return character;
    }

    public Shot getShotClass() {
        return shot;
    }

    public Enemy getEnemyClass() {
        return enemy;
    }
}
