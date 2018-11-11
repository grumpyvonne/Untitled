package Model;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Character {
    private ImageView character;
    private Point2D gravity;
    private boolean canJump = true;

    public Character(int x, int y, int width, int height, String url) {
        setCharacter(createCharacter(x, y, width, height, url));
        gravity = new Point2D(0, 0);
//        canJump = true;
    }


    public ImageView createCharacter(int x, int y, int width, int height, String url) {
        this.character = new ImageView(url);
        this.character.setTranslateX(x);
        this.character.setTranslateY(y);
        this.character.setFitHeight(height);
        this.character.setFitWidth(width);
        this.character.setPreserveRatio(true);
        return character;
    }


    private void setCharacter(ImageView character) {
        this.character = character;
    }

    public ImageView getCharacter() {
        return this.character;
    }

    public void setCanJump(boolean isJumping) {
        this.canJump = isJumping;
    }

    public boolean getCanJump() {
        return this.canJump;
    }


    public void setGravity(Point2D gravity) {
        this.gravity = gravity;
    }

    public Point2D getGravity() {
        return gravity;
    }
}
