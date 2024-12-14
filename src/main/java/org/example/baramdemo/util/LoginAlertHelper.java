package org.example.baramdemo.util;

import javafx.scene.control.Alert;

public class LoginAlertHelper {

  public static Alert createErrorAlert() {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("login failed");
    alert.setHeaderText(null);
    alert.setContentText("아이디 또는 비밀번호가 틀렸습니다.");
    return alert;
  }

}
