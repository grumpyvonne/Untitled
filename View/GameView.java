package View;

import Controller.BehaviorController;
import Controller.DataController;
import Controller.StatusController;
import Model.Character;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class GameView {
    private Pane mainPane;
    private Pane uiRoot;
    private Pane gameEntitiesRoot;
    private Pane shotsPane;
    private Pane enemiesPane;
    private Stage gameStage;
    private Scene gameScene;
    private Node background;
    private DataController dataController;
    private StatusController statusController;
    private BehaviorController behaviorController;
    private Character character;
    private UiView uiView;
    private HashMap<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>();
    private double time = 0;


    public GameView() {
        character = new Character(0, 620, 40, 40, "images/Object_11.png");
        statusController = new StatusController();
        dataController = new DataController(statusController, character);
        behaviorController = new BehaviorController(dataController);
        mainPane = new Pane();
        gameScene = new Scene(mainPane, 1280, 720);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
        uiView = new UiView(this.statusController);
        uiRoot = uiView.getUiRoot();
        gameEntitiesRoot = new Pane();
        shotsPane = new Pane();
        enemiesPane = new Pane();
        gameStage.hide();
        setBackground();
        mainPane.getChildren().addAll(background, gameEntitiesRoot, uiRoot);
        initialize();
        gameStage.show();
    }

    public void initialize() {
        dataController.createCollectiblesOnTrees();
        initializeEntities();
        checkLayout();
        createGameLoop();
    }

    private void keysListeners() {
        gameScene.setOnKeyPressed(keyEvent -> {
            if (!keys.containsKey(keyEvent.getCode()))
                keys.put(keyEvent.getCode(), true);
        });
        gameScene.setOnKeyReleased(keyEvent -> keys.remove(keyEvent.getCode()));
//        gameScene.setOnKeyReleased(keyEvent -> keys.put(keyEvent.getCode(), false));
    }

    private boolean keyIsPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }

    private boolean checkIfPressed(KeyCode keyCode) {
        Boolean isActive = keys.get(keyCode);
        if (isActive != null && isActive) {
            keys.put(keyCode, false);
            return true;
        } else {
            return false;
        }
    }

    private void initializeEntities() {
        gameEntitiesRoot.getChildren().addAll(dataController.getPlatforms());
        gameEntitiesRoot.getChildren().addAll(dataController.getWaterPlatforms());
        gameEntitiesRoot.getChildren().addAll(dataController.getTrees());
        gameEntitiesRoot.getChildren().addAll(dataController.getInitialCollectiblesList());
        enemiesPane.getChildren().addAll(dataController.getEnemies());
        gameEntitiesRoot.getChildren().addAll(enemiesPane);
        gameEntitiesRoot.getChildren().addAll(shotsPane);
        gameEntitiesRoot.getChildren().add(this.character.getCharacter());
    }

    private void checkLayout() {
        behaviorController.getCharacter().translateXProperty().addListener((obs, old, newValue) -> {
            int offsetX = newValue.intValue();
            if (offsetX > 640 && offsetX < dataController.getLevelWidth() - 640) {
                gameEntitiesRoot.setLayoutX(-(offsetX - 640));
            }
        });
        behaviorController.getCharacter().translateYProperty().addListener((obs, old, newValue) -> {
            int offsetY = newValue.intValue();
            if (offsetY < 360 && offsetY > dataController.getLevelHeight() - 360) {
                gameEntitiesRoot.setLayoutY(360 - offsetY);
            }
        });
    }

    private void createGameLoop() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                keysListeners();
                updateCharacterMovement();
                checkCollisions();
                checkCharacterInteraction();
                createEnemy();
                if (checkDeath()) {
                    uiView.removeLife();
                    if (statusController.getLivesCount() > 0)
                        behaviorController.getCharacter().setTranslateX(0);
                    behaviorController.getCharacter().setTranslateY(620);
                    gameEntitiesRoot.setLayoutX(0);
                } else if (statusController.getLivesCount() == 0) {
                    this.stop();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("You died");
                    alert.setHeaderText(null);
                    alert.show();
                    gameStage.close();
                }
            }
        };
        timer.start();
    }

    private void updateCharacterMovement() {
        if (keyIsPressed(KeyCode.D) && behaviorController.getCharacter().getTranslateX() + 40 <= dataController.getLevelWidth() - 5) {
            behaviorController.moveX(5, behaviorController.getCharacter());
        }
        if (keyIsPressed(KeyCode.A) && behaviorController.getCharacter().getTranslateX() >= 5) {
            behaviorController.moveX(-5, behaviorController.getCharacter());
        }
        if (keyIsPressed(KeyCode.SPACE) && behaviorController.getCharacter().getTranslateY() >= 5) {
            behaviorController.jumpCharacter();
        }

        if (behaviorController.getCharacterGravity().getY() < 10) {
            Point2D gravity = behaviorController.getCharacterGravity();
            gravity = gravity.add(0, 1);
            behaviorController.setCharacterGravity(gravity);
        }

        behaviorController.moveCharacterY((int) behaviorController.getCharacterGravity().getY());
    }

    private void checkCharacterInteraction() {
        checkForShooting();
    }

    private void createEnemy() {
        time += 0.016;
        if (time > 10) {
            Node singleEnemy = dataController.createEnemy(700, 280);
            enemiesPane.getChildren().addAll(singleEnemy);
        }
        if (time > 10) {
            time = 0;
        }
    }

    private void checkForShooting() {
        if (checkIfPressed(KeyCode.F)) {
            if (statusController.getCollectiblesCount() > 0) {
                uiView.removeCollectible();
                ImageView shotUi = dataController.createShot();
                dataController.addToShotsList(shotUi);
                shotsPane.getChildren().add(shotUi);
            }
        }
        for (Node shot : dataController.getShotsList()) {
            dataController.getShot().moveRight(shot, 3);
            checkCollisionShot();
            checkCollisionShotEnemy(shot);
        }
    }

    private void checkCollisionShot() {
        for (Node platform : dataController.getPlatforms()) {
            shotsPane.getChildren().removeIf(someShot -> someShot.getBoundsInParent().intersects(platform.getBoundsInParent()));
//            List<Node> newShotsList = dataController.getShotsList();
//            newShotsList.removeIf(someShot -> someShot.getBoundsInParent().intersects(platform.getBoundsInParent()));
//            dataController.setShotsList(newShotsList);
        }
        for (Node tree : dataController.getTrees()) {
            shotsPane.getChildren().removeIf(someShot -> someShot.getBoundsInParent().intersects(tree.getBoundsInParent()));
        }
    }

    private void checkCollisionShotEnemy(Node shot) {
        for (Iterator<Node> iterator = enemiesPane.getChildren().iterator(); iterator.hasNext(); ) {
            Node pigeon = iterator.next();
            if (pigeon.getBoundsInParent().intersects(shot.getBoundsInParent())) {
                iterator.remove();
                shotsPane.getChildren().remove(shot);
            }
        }
    }

    private void checkCollisions() {
        if (dataController.checkIntersectTree() && checkIfPressed(KeyCode.ENTER)) {
            uiView.addLife();
        }
        if (dataController.checkIntersectCollect() && checkIfPressed(KeyCode.E)) {
            uiView.addCollectible();
            gameEntitiesRoot.getChildren().remove(dataController.getCollectibleFromCollision());
            List<Node> newCollectiblesList = dataController.getInitialCollectiblesList();
            newCollectiblesList.remove(dataController.getCollectibleFromCollision());
            dataController.setInitialCollectiblesList(newCollectiblesList);
        }

    }

    private boolean checkDeath() {
        if (dataController.checkForFalling()) {
            keys.keySet().forEach(key -> keys.put(key, false));
            return true;
        } else return false;
    }

    public void setBackground() {
        Image backImage = new Image("images/Background.png", 1280, 720, false, false);
        this.background = new ImageView(backImage);
    }

}
