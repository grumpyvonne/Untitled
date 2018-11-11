package Controller;

import Model.Character;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class BehaviorController {
    private DataController dataController;
    private Character character;

    public BehaviorController(DataController dataController){
        this.dataController = dataController;
        this.character = dataController.getCharacter();
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

    public void moveY(int value, Node node) {
        boolean movingDown = value > 0;
        for (int pixels = 0; pixels < Math.abs(value); pixels++) {
            for (Node platform : dataController.getPlatforms()) {
                if (node.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    if (movingDown) {
                        if (node.getTranslateY() + 40 == platform.getTranslateY()) {
                            node.setTranslateY(node.getTranslateY() - 1);
                            return;
                        }
                    } else {
                        if (node.getTranslateY() == platform.getTranslateY() + 60) {
                            return;
                        }
                    }
                }
            }
            node.setTranslateY(node.getTranslateY() + (movingDown ? 1 : -1));
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

    public void setCanJump(boolean bool){
        character.setCanJump(bool);
    }

    public boolean getCanJump(){
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