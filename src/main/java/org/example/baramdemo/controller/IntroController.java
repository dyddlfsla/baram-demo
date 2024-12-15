package org.example.baramdemo.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import org.example.baramdemo.util.StageManager;

public class IntroController {

  private static final String LOG_IN_FXML_PATH = "/org/example/baramdemo/log-in-view.fxml";

  // "시작하기" 버튼 클릭 시 호출될 메소드
  @FXML
  private void handleStartBtn() {
    loadScene();
  }

  private void loadScene() {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(LOG_IN_FXML_PATH));
      Scene loginScene = new Scene(fxmlLoader.load());
      StageManager.getStage().setScene(loginScene);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}