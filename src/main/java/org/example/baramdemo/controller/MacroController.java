package org.example.baramdemo.controller;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.robot.Robot;

public class MacroController {

  private NativeKeyListener keyListener; // 필드로 NativeKeyListener 추가

  public MacroController() {
    initializeGlobalKeyListener();
  }

  // 글로벌 키보드 초기화
  private void initializeGlobalKeyListener() {
    try {
      GlobalScreen.registerNativeHook();
      keyListener = createKeyListener();
      GlobalScreen.addNativeKeyListener(keyListener);
    } catch (Exception e) {
      handleError("키보드 리스너 초기화 실패", e);
    }
  }

  // 키 입력을 처리하는 리스너 생성
  private NativeKeyListener createKeyListener() {
    return new NativeKeyListener() {
      @Override
      public void nativeKeyPressed(NativeKeyEvent e) {
        if (isF1KeyPressed(e)) {
          handleF1KeyPress();
        }
      }

      @Override
      public void nativeKeyReleased(NativeKeyEvent e) {
        // 추가적인 키 해제 처리 로직이 필요하면 구현
      }

      @Override
      public void nativeKeyTyped(NativeKeyEvent e) {
        // 추가적인 문자 키 입력 처리 로직이 필요하면 구현
      }
    };
  }

  // F1 키가 눌렸을 때 수행될 내용
  private void handleF1KeyPress() {
    Platform.runLater(() -> {
      moveMouse();
      showInfoAlert("F1 키가 눌렸습니다. 마우스를 이동합니다.");
      playSystemBeep();
    });
  }

  // F1 키가 눌렸을 때 수행될 내용
  private boolean isF1KeyPressed(NativeKeyEvent e) {
    return e.getKeyCode() == NativeKeyEvent.VC_F1;
  }

  // 마우스 이동
  public void moveMouse() {
    try {
      Robot robot = new Robot();
      Point currentPosition = MouseInfo.getPointerInfo().getLocation();
      int newX = currentPosition.x + 10; // 10px 이동
      int newY = currentPosition.y + 10;
      robot.mouseMove(newX, newY);
    } catch (Exception e) {  // AWTException 처리 없이 모든 예외를 Exception 으로 처리
      handleError("마우스 이동 실패", e);
    }
  }

  // 시스템 알림음
  private void playSystemBeep() {
    Toolkit.getDefaultToolkit().beep(); // 시스템 기본 알림음을 재생
  }

  // UI 스레드에서 알림창 생성
  private void showInfoAlert(String message) {
    Platform.runLater(() -> {
      Alert alert = new Alert(AlertType.INFORMATION);
      alert.setTitle("정보");
      alert.setHeaderText(null);
      alert.setContentText(message);
      alert.showAndWait();
    });
  }

  /**
   * 에러 발생 시 알림을 띄움
   */
  private void handleError(String message, Exception e) {
    Platform.runLater(() -> {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("에러");
      alert.setHeaderText(message);
      alert.setContentText(e.getMessage());
      alert.showAndWait();
    });
  }
}
