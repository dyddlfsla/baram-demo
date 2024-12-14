package org.example.baramdemo.controller;

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

  private static final LoginService loginService = new LoginService();
  private boolean isLoginSuccess;

  @FXML
  private void handleLoginBtn() {
    isLoginSuccess = loginService.execute(inputId, inputPw);

    if (!isLoginSuccess) {
      LoginAlertHelper.createErrorAlert().showAndWait();
    }
  }

}
