package Controller;

import View.GameView;
import View.UiView;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CollisionController {
    private DataController dataController;
    private BehaviorController behaviorController;
    private StatusController statusController;
    private GameView gameView;
    private UiView uiView;
    public CollisionController(DataController dataController, BehaviorController behaviorController, StatusController statusController, GameView gameView, UiView uiView){
        this.dataController = dataController;
        this.behaviorController = behaviorController;
        this.statusController = statusController;
        this.gameView = gameView;
        this.uiView = uiView;
    }
    public boolean checkIntersectCollect() {
        boolean isColliding = false;
        for (Node collectible : dataController.getInitialCollectiblesList()) {
            if (dataController.getCharacterClass().getCharacter().getBoundsInParent().intersects(collectible.getBoundsInParent())) {
                isColliding = true;
                dataController.setNodeFromCollision(collectible);
            }
        }
        return isColliding;
    }

    public boolean checkIntersectTree() {
        boolean isColliding = false;
        for (Node tree : dataController.getTrees()) {
            if (dataController.getCharacterClass().getCharacter().getBoundsInParent().intersects(tree.getBoundsInParent()))
                isColliding = true;
        }
        return isColliding;
    }
    public void checkCollisions() {
        if (checkIntersectTree() && gameView.checkIfPressed(KeyCode.ENTER)) {
            uiView.addLife();
        }
        if (checkIntersectCollect() && gameView.checkIfPressed(KeyCode.E)) {
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

        for (Node tree : dataController.getTrees()) {
            if (shot.getBoundsInParent().intersects(tree.getBoundsInParent())) {
                gameView.shotsPane.getChildren().remove(shot);
                HashMap<Node, String> newHashMap = dataController.getShotsMap();
                newHashMap.remove(shot);
                dataController.setShotsMap(newHashMap);
            }
        }
        for (Iterator<Node> iterator = gameView.enemiesPane.getChildren().iterator(); iterator.hasNext(); ) {
            Node pigeon = iterator.next();
            if (pigeon.getBoundsInParent().intersects(shot.getBoundsInParent())) {
                iterator.remove();
                gameView.shotsPane.getChildren().remove(shot);
                HashMap<Node, String> newHashMap = dataController.getShotsMap();
                newHashMap.remove(shot);
                dataController.setShotsMap(newHashMap);
            }
        }
    }

    public void checkForShooting() {
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
        if (gameView.checkIfPressed(KeyCode.C)) {
            System.out.println("map " + dataController.getShotsMap());
        }

        for (Map.Entry<Node, String> shot : dataController.getShotsMap().entrySet()) {
            if (shot.getValue() == "right") {
                behaviorController.moveShotRight(shot.getKey(), 6);
            } else if (shot.getValue() == "left") {
                behaviorController.moveShotLeft(shot.getKey(), 6);
            }
        }

        for(Iterator<Node> iterator = dataController.getShotsMap().keySet().iterator(); iterator.hasNext();){
            Node shot = iterator.next();
            checkCollisionShot(shot);
        }
    }

    public void checkCharacterInteraction() {
        checkForShooting();
    }
}
