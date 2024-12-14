package org.example.baramdemo.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.example.baramdemo.service.LoginService;

public class LoginController {

  @FXML
  private TextField inputId; // id
  @FXML
  private PasswordField inputPw; // pw
  @FXML
  private Text errorMsg; // 로그인 에러 메시지
  private static final LoginService loginService = new LoginService();
  private boolean loginResult;

  @FXML
  private void handleLoginBtn() {
    loginResult = loginService.isLoginSuccess(inputId, inputPw);

    if (loginResult) {
      errorMsg.setText("login success");
    } else {
      errorMsg.setText("login failed");
    }
  }

}
