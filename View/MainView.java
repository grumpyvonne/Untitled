package View;

import Controller.Menu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainView {
    private static final int HEIGHT = 720;
    private static final int WIDTH = 1280;
    private Pane mainPane;
    private Scene mainScene;
    private Stage mainStage;
    private Menu menu;


    public MainView() {
        mainPane = new Pane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        menu = new Menu();
        initialize();
    }

    public void initialize() {
        createButton();
    }

    public void createButton() {
        Button startButton = new Button("Start");
        startButton.setLayoutY(360);
        startButton.setLayoutX(640);
        mainPane.getChildren().add(startButton);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("clicked");
                GameView gameView = new GameView();
//                gameView.start();
                mainStage.close();
            }
        });
    }

    public Stage getMainStage() {
        return this.mainStage;
    }

    public Menu getMenu() {
        return this.menu;
    }
}


