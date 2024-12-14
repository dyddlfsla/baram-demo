package org.example.baramdemo.controller;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.baramdemo.service.LoginService;
import org.example.baramdemo.util.LoginAlertHelper;

public class LoginController {

  @FXML
  private TextField inputId; // id
  @FXML
  private PasswordField inputPw; // pw

  private final Desktop desktop = Desktop.getDesktop();
  private static final LoginService loginService = new LoginService();
  private boolean isLoginSuccess;

  @FXML
  private void handleLoginBtn() {
    isLoginSuccess = loginService.execute(inputId, inputPw);

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
}
