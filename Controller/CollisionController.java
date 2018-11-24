package Controller;

import View.GameView;
import View.UiView;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;

import java.util.*;

public class CollisionController {
    private DataController dataController;
    private BehaviorController behaviorController;
    private StatusController statusController;
    private GameView gameView;
    private UiView uiView;
    private double collisionTime = 0;
    private Node collidedPigeon;

    public CollisionController(DataController dataController, BehaviorController behaviorController, StatusController statusController, GameView gameView, UiView uiView) {
        this.dataController = dataController;
        this.behaviorController = behaviorController;
        this.statusController = statusController;
        this.gameView = gameView;
        this.uiView = uiView;
    }

    public boolean checkIntersectCollectible() {
        boolean isColliding = false;
        for (Node collectible : dataController.getInitialCollectiblesList()) {
            if (dataController.getCharacterClass().getCharacter().getBoundsInParent().intersects(collectible.getBoundsInParent())) {
                isColliding = true;
                dataController.setNodeFromCollision(collectible);
            }
        }
        return isColliding;
    }

    public void checkCollectingResources() {
        if (statusController.getScore() >= 100 && gameView.checkIfPressed(KeyCode.ENTER) && statusController.getLivesCount() < 3) {
            uiView.addLife();
            statusController.setScore(statusController.getScore() - 100);
            uiView.updateScore();
        }
        if (checkIntersectCollectible() && gameView.checkIfPressed(KeyCode.E)) {
            uiView.addCollectible();
            gameView.gameEntitiesRoot.getChildren().remove(dataController.getCollectibleFromCollision());
            List<Node> newCollectiblesList = dataController.getInitialCollectiblesList();
            newCollectiblesList.remove(dataController.getCollectibleFromCollision());
            dataController.setInitialCollectiblesList(newCollectiblesList);
        }
    }

    public void checkCollisionShot(Node shot) {
        for (Node platform : dataController.getPlatforms()) {
            if (shot.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                gameView.shotsPane.getChildren().remove(shot);
                HashMap<Node, String> newHashMap = dataController.getShotsMap();
                newHashMap.remove(shot);
                dataController.setShotsMap(newHashMap);
            }
        }

        Collection<Node> enemiesToRemove = new ArrayList<Node>();
        for (Node pigeon : gameView.enemiesPane.getChildren()) {
            if (pigeon.getBoundsInParent().intersects(shot.getBoundsInParent())) {
                HashMap<Node, String> newShotsHashMap = dataController.getShotsMap();
                gameView.shotsPane.getChildren().remove(shot);
                newShotsHashMap.remove(shot);
                dataController.setShotsMap(newShotsHashMap);
                enemiesToRemove.add(pigeon);
                dataController.getEnemyClass().setCount(dataController.getEnemyClass().getCount() - 1);
                List<Node> newEnemyList = dataController.getEnemiesList();
                newEnemyList.remove(pigeon);
                dataController.setEnemiesList(newEnemyList);
                uiView.addKillScore();
            }

        }
        if (checkCharacterEnemyCollision() && gameView.checkIfPressed(KeyCode.G)) {
            enemiesToRemove.add(collidedPigeon);
            dataController.getEnemyClass().setCount(dataController.getEnemyClass().getCount() - 1);
            List<Node> newEnemyList = dataController.getEnemiesList();
            newEnemyList.remove(collidedPigeon);
            dataController.setEnemiesList(newEnemyList);
            uiView.addKillScore();
        }
        gameView.enemiesPane.getChildren().removeAll(enemiesToRemove);
    }

    public void checkBoundaries(Node shot) {
        if (shot.getTranslateX() <= 0 || shot.getTranslateX() >= dataController.getLevelWidth()) {
            gameView.shotsPane.getChildren().remove(shot);
            HashMap<Node, String> newHashMap = dataController.getShotsMap();
            newHashMap.remove(shot);
            dataController.setShotsMap(newHashMap);
        }
    }

    public void checkForShooting() {
        for (Iterator<Node> iterator = dataController.getShotsMap().keySet().iterator(); iterator.hasNext(); ) {
            Node shot = iterator.next();
            checkCollisionShot(shot);
            checkBoundaries(shot);
        }
        if (gameView.checkIfPressed(KeyCode.F)) {
            if (statusController.getCollectiblesCount() > 0) {
                uiView.removeCollectible();
                Node shotUi = dataController.createShot();
                gameView.shotsPane.getChildren().add(shotUi);
                if (behaviorController.getMovingLeft()) {
                    dataController.addToShotsMap(shotUi, "left");
                } else if (behaviorController.getMovingRight()) {
                    dataController.addToShotsMap(shotUi, "right");
                }
            }
        }

        for (Map.Entry<Node, String> shot : dataController.getShotsMap().entrySet()) {
            if (shot.getValue() == "right") {
                behaviorController.moveShotRight(shot.getKey(), 6);
            } else if (shot.getValue() == "left") {
                behaviorController.moveShotLeft(shot.getKey(), 6);
            }
        }


    }

    public boolean checkCharacterEnemyCollision() {
        boolean isColliding = false;
        for (Node pigeon : dataController.getEnemiesList()) {
            behaviorController.chase(pigeon);
            if (dataController.getCharacterClass().getCharacter().getBoundsInParent().intersects(pigeon.getBoundsInParent())) {
                collisionTime += 0.016;
                isColliding = true;
                collidedPigeon = pigeon;
            }
        }
        return isColliding;
    }

    public boolean checkForFalling() {
        boolean drowned = false;
        for (Node water : dataController.getWaterPlatforms()) {
            if (dataController.getCharacterClass().getCharacter().getBoundsInParent().intersects(water.getBoundsInParent())) {
                dataController.getCharacterClass().setGravity(new Point2D(0, 0));
                drowned = true;
            }
        }
        return drowned;
    }

    public void checkCharacterInteraction() {
        checkForShooting();
    }

    public double getCollisionTime() {
        return collisionTime;
    }

    public void nullifyCollisionTime() {
        this.collisionTime = 0;
    }
}
