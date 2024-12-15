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
import org.example.baramdemo.service.LoginService;
import org.example.baramdemo.util.LoginAlertHelper;
import org.example.baramdemo.util.StageManager;

public class LoginController {

  @FXML
  private TextField inputId; // id
  @FXML
  private PasswordField inputPw; // pw
  private static final String MACRO_FXML_PATH = "/org/example/baramdemo/macro-view.fxml";
  private static final String JOIN_WEB_PAGE_URL = "https://www.naver.com";
  private static final String RESTORE_WEB_PAGE_URL = "https://www.google.com";

  private Desktop desktop = Desktop.getDesktop();
  private static final LoginService loginService = new LoginService();
  private boolean isLoginSuccess;

  @FXML
  private void handleLoginBtn() {
    isLoginSuccess = loginService.execute(inputId, inputPw);

    if (isLoginSuccess) {
      loadScene();
    }

    if (!isLoginSuccess) {
      LoginAlertHelper.createErrorAlert().showAndWait();
    }
  }

  @FXML
  private void handleJoinBtn() {
    goToWebPage(JOIN_WEB_PAGE_URL);
  }

  @FXML
  private void handleRestoreBtn() {
    goToWebPage(RESTORE_WEB_PAGE_URL);
  }

  private void loadScene() {
    try {

      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(MACRO_FXML_PATH));
      Scene macroScene = new Scene(fxmlLoader.load());
      StageManager.getStage().setScene(macroScene);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void goToWebPage(String webPageUrl) {
    try {
      desktop.browse(new URI(webPageUrl));
    } catch (IOException | URISyntaxException e) {
      e.printStackTrace();
    }
  }
}
