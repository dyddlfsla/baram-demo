package org.example.baramdemo;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.baramdemo.util.StageManager;

public class Main extends Application {

  private Stage stage;
  private static final String INTRO_FXML_PATH = "/org/example/baramdemo/intro-view.fxml";

  @Override
  public void start(Stage primaryStage) {
    initializePrimaryStage(primaryStage);
    createStageManager();
    loadScene();
  }

  public static void main(String[] args) {
    launch();
  }

  private void createStageManager() {
    StageManager.getInstance().setStage(stage);
  }

  private void loadScene() {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(INTRO_FXML_PATH));
    try {
      Scene introScene = new Scene(fxmlLoader.load());
      stage.setScene(introScene);
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void initializePrimaryStage(Stage primaryStage) {
    stage = primaryStage;
    stage.setTitle("The Kingdom of Winds Classic");
    Image iconImg = new Image(getClass().getResourceAsStream("../../../static/img/baram_windows_icon.png"));
    stage.getIcons().add(iconImg);
    stage.setResizable(false);
  }
}
