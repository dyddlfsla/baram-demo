package org.example.baramdemo;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.baramdemo.controller.IntroController;


public class Main extends Application {

  @Override
  public void start(Stage primaryStage) throws IOException {

    // FXML 파일을 로드하고 Scene 을 설정
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("introduce-view.fxml"));
    Scene scene = new Scene(fxmlLoader.load());

    // HelloController 에 stage 전달
    IntroController controller = fxmlLoader.getController();
    controller.setStage(primaryStage); // 여기서 primaryStage 를 전달

    // primaryStage 설정
    primaryStage.setTitle("The Kingdom of Winds Classic");
    primaryStage.setResizable(false);
    primaryStage.setScene(scene);

    // 창을 띄웁니다.
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}
