package Controller;

import Model.Character;
import Model.Enemy;
import Model.Shot;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.util.Duration;

public class BehaviorController {
    private DataController dataController;
    private Character character;
    private Enemy enemy;
    private Shot shot;
    private boolean movingLeft = false;
    private boolean movingRight = false;

    public void setMovingLeft(boolean isMoving) {
        movingLeft = isMoving;
    }

    public void setMovingRight(boolean isMoving) {
        movingRight = isMoving;
    }

    public boolean getMovingLeft() {
        return movingLeft;
    }

    public boolean getMovingRight() {
        return movingRight;
    }

    public BehaviorController(DataController dataController) {
        this.dataController = dataController;
        this.character = dataController.getCharacterClass();
        this.shot = dataController.getShotClass();
        this.enemy = dataController.getEnemyClass();
    }

    public void moveShotLeft(Node singleShot, int speed) {
        shot.moveLeft(singleShot, speed);
    }

    public void moveShotRight(Node singleShot, int speed) {
        shot.moveRight(singleShot, speed);
    }

    public void moveX(int value, Node node) {
        boolean movingToTheRight = value > 0;
        for (int pixels = 0; pixels < Math.abs(value); pixels++) {
            for (Node platform : dataController.getPlatforms()) {
                if (node.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    if (movingToTheRight) {
                        if (node.getTranslateX() + 40 == platform.getTranslateX()) {
                            return;
                        }
                    } else {
                        if (node.getTranslateX() == platform.getTranslateX() + 60) {
                            return;
                        }
                    }
                }
            }
            node.setTranslateX(node.getTranslateX() + (movingToTheRight ? 1 : -1));
        }
    }

    public void chase(Node node) {
        if (Math.abs(character.getCharacter().getTranslateX() - node.getTranslateX()) < 700 && Math.abs(character.getCharacter().getTranslateY() - node.getTranslateY()) < 700) {
            Timeline timeline = new Timeline();
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.setAutoReverse(true);
            KeyValue kvx = new KeyValue(node.translateXProperty(), character.getCharacter().getTranslateX());
            KeyValue kvy = new KeyValue(node.translateYProperty(), character.getCharacter().getTranslateY());
            KeyFrame kf = new KeyFrame(Duration.seconds(1), kvx, kvy);
            timeline.getKeyFrames().add(kf);
            timeline.play();
        }
    }


    public void moveCharacterY(int value) {
        boolean movingDown = value > 0;
        for (int pixels = 0; pixels < Math.abs(value); pixels++) {
            for (Node platform : dataController.getPlatforms()) {
                if (character.getCharacter().getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    if (movingDown) {
                        if (character.getCharacter().getTranslateY() + 40 == platform.getTranslateY()) {
                            character.getCharacter().setTranslateY(character.getCharacter().getTranslateY() - 1);
                            setCanJump(true);
                            return;
                        }
                    } else {
                        if (character.getCharacter().getTranslateY() == platform.getTranslateY() + 60) {
                            return;
                        }
                    }
                }
            }
            character.getCharacter().setTranslateY(character.getCharacter().getTranslateY() + (movingDown ? 1 : -1));
        }
    }

    public void jumpCharacter() {
        if (getCanJump()) {
            setCharacterGravity(getCharacterGravity().add(0, -27));
            setCanJump(false);
        }
    }

    public void setCanJump(boolean bool) {
        character.setCanJump(bool);
    }

    public boolean getCanJump() {
        return character.getCanJump();
    }

    public void setCharacterGravity(Point2D velocity) {
        character.setGravity(velocity);
    }

    public Point2D getCharacterGravity() {
        return character.getGravity();
    }

    public Node getCharacter() {
        return character.getCharacter();
    }
}
