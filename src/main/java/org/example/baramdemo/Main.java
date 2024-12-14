package org.example.baramdemo;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.baramdemo.controller.IntroController;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) throws IOException {

    // FXML 파일을 로드하고 Scene 을 설정
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("intro-view.fxml"));
    Scene scene = new Scene(fxmlLoader.load());

    // HelloController 에 stage 전달
    IntroController controller = fxmlLoader.getController();
    controller.setStage(primaryStage); // 여기서 primaryStage 를 전달

    // primaryStage 설정
    Image windowIcon = new Image(getClass().getResourceAsStream("../../../static/img/baram_windows_icon.png"));
    primaryStage.getIcons().add(windowIcon);
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
