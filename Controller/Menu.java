package Controller;

import View.MainView;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Menu {
    private MainView mainView;
    private Pane mainPane;
    private Stage mainStage;
    private Scene mainScene;

    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }

    public MainView getMainView() {
        return mainView;
    }

    public void setMainPane(Pane mainPane) {
        this.mainPane = mainPane;
    }

    public Pane getMainPane() {
        return mainPane;
    }

    public Scene getMainScene() {
        return mainScene;
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }
}
