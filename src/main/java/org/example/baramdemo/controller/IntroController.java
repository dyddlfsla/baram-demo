package org.example.baramdemo.controller;

import java.io.IOException;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class IntroController {

  private Stage stage; // Stage 를 저장할 변수

  // "시작하기" 버튼 클릭 시 호출될 메소드
  @FXML
  private void handleStartBtn() {
    try {
      // 두 번째 화면 로드
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/baramdemo/log-in-view.fxml"));
      VBox tutorialView = fxmlLoader.load();
      Scene tutorialScene = new Scene(tutorialView);
      tutorialScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/org/example/baramdemo/style.css")).toExternalForm());

      LoginController controller = fxmlLoader.getController();
      controller.setStage(stage); // 여기서 primaryStage 를 전달

      // 현재 Stage 에 새로운 Scene 을 설정
      stage.setScene(tutorialScene);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Stage 를 외부에서 전달받는 메소드
  public void setStage(Stage stage) {
    this.stage = stage;
  }

}