package org.example.baramdemo.service;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.baramdemo.member.Admin;

public class LoginService {

  private String inputId;
  private String inputPw;
  private boolean loginResult;
  private final Admin admin = Admin.getInstance();

  public boolean execute(TextField inputId, PasswordField inputPw) {
    initialize(inputId, inputPw);
    checkIdAndPw();
    return loginResult;
  }

  private void initialize(TextField inputId, PasswordField inputPw) {
    this.inputId = getTextId(inputId);
    this.inputPw = getTextPassword(inputPw);
  }

  private void checkIdAndPw() {
    loginResult = admin.getId().equals(inputId) && admin.getPassword().equals(inputPw);
  }

  private String getTextId(TextField inputId) {
    return inputId.getText();
  }

  private String getTextPassword(PasswordField inputPw) {
    return inputPw.getText();
  }
}
