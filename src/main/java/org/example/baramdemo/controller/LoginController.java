package org.example.baramdemo.controller;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.baramdemo.service.LoginService;
import org.example.baramdemo.util.LoginAlertHelper;

public class LoginController {

  @FXML
  private TextField inputId; // id
  @FXML
  private PasswordField inputPw; // pw
  private Stage stage;

  private final Desktop desktop = Desktop.getDesktop();
  private static final LoginService loginService = new LoginService();
  private boolean isLoginSuccess;

  @FXML
  private void handleLoginBtn() {
    isLoginSuccess = loginService.execute(inputId, inputPw);

    if (isLoginSuccess) {
      try {
        // 두 번째 화면 로드
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/baramdemo/macro-view.fxml"));
        VBox macroView = fxmlLoader.load();

        // 현재 Stage 에 새로운 Scene 을 설정
        stage.setScene(new Scene(macroView));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    if (!isLoginSuccess) {
      LoginAlertHelper.createErrorAlert().showAndWait();
    }

  }

  @FXML
  private void handleJoinBtn() {
    try {
      desktop.browse(new URI("https://www.naver.com"));
    } catch (IOException | URISyntaxException e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void handleRestoreBtn() {
    try {
      desktop.browse(new URI("https://www.google.com"));
    } catch (IOException | URISyntaxException e) {
      e.printStackTrace();
    }
  }

  // Stage 를 외부에서 전달받는 메소드
  public void setStage(Stage stage) {
    this.stage = stage;
  }
}
