package org.example.baramdemo.controller;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javazoom.jl.player.Player;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class MacroController {

  static {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // OpenCV 라이브러리 로드
  }

  private FileChooser fileChooser;
  private Robot robot;
  private boolean isRunning = false;
  private Thread macroThread = null;

  @FXML
  private ImageView imageOfKing;
  @FXML
  private ImageView imageOfMonster01;
  @FXML
  private ImageView imageOfMonster02;
  @FXML
  private ImageView imageOfMonster03;
  @FXML
  private ImageView imageOfMonster04;
  @FXML
  private ImageView imageOfMonster05;
  @FXML
  private TextField huntingGroundCharacter;
  @FXML
  private TextField kingGroundCharacter;

  public void initialize() {
    initializeGlobalKeyListener();
    initializeFileChooser();
    initializeRobot();
  }

  private void initializeRobot() {
    try {
      robot = new Robot();
    } catch (AWTException e) {
      throw new RuntimeException(e);
    }
  }

  private void initializeFileChooser() {
    // FileChooser 객체 생성
    fileChooser = new FileChooser();
    // 기본적으로 이미지 파일만 선택하도록 필터 설정
    ExtensionFilter imageFilter =
        new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif");
    fileChooser.getExtensionFilters().add(imageFilter);
  }

  // 키 입력을 처리하는 리스너 생성
  private void initializeGlobalKeyListener() {

    class KeyListenerHelper implements NativeKeyListener {
      @Override
      public void nativeKeyPressed(NativeKeyEvent keyEvent) {

        switch (keyEvent.getKeyCode()) {
          case NativeKeyEvent.VC_F1:
            handleF1KeyPress();
            break;
          case NativeKeyEvent.VC_F2:
            try {
              handleF2KeyPress();
            } catch (InterruptedException e) {
              handleInterruption();
            }
            break;
          case NativeKeyEvent.VC_F3:
            try {
              handleF3KeyPress();
            } catch (InterruptedException e) {
              handleInterruption();
            }
            break;
          case NativeKeyEvent.VC_F4:
            handleF4KeyPress();
            break;
          case NativeKeyEvent.VC_F5:
            try {
              handleF5KeyPress();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            break;
        }
      }

      @Override
      public void nativeKeyReleased(NativeKeyEvent e) {} // 키 release 이벤트

      @Override
      public void nativeKeyTyped(NativeKeyEvent e) {} // 키 Type 이벤트
    }

    try {
      GlobalScreen.registerNativeHook();
      GlobalScreen.addNativeKeyListener(new KeyListenerHelper());
    } catch (Exception e) {
      handleError("failed to initialize keyListener", e);
    }
  }

  private void handleF1KeyPress() {
    startQuestMacro();
  }

  private void handleF2KeyPress() throws InterruptedException {
    teleportToTarget(kingGroundCharacter.getText());
  }

  private void handleF3KeyPress() throws InterruptedException {
    teleportToTarget(huntingGroundCharacter.getText());
  }

  private void handleF4KeyPress() {
    stopQuestMacro();
  }

  private void handleF5KeyPress() throws InterruptedException {
  }

  private void teleportToTarget(String characterName) throws InterruptedException {
    pressUKey();
    pauseThread();
    pressAKey();
    pauseThread();
    pressNumber9Key();
    pauseThread();
    typeCharacterName(characterName);
    pauseThread(); // 텍스트 입력 후 잠깐 대기
    pressEnterKey();
  }

  private boolean isKingExisted() {
    moveMouseToTarget(new Point(0, 0));
    return findImageOnScreen(imageOfKing, "imageOfKing") != null;
  }

  private void getQuestOfKing() throws InterruptedException {
    moveMouseToTarget(new Point(0, 0));
    moveMouseToTarget(findImageOnScreen(imageOfKing, "imageOfKing"));
    mouseClick();
    pauseThread();
    pressEnterKey();
    pauseThread();
    pressEnterKey();
    pauseThread();
    pressEnterKey();
    pauseThread();
    pressEnterKey();
    pauseThread();
    pressArrowDownKey();
    pauseThread();
    pressEnterKey();
    pauseThread();
    pressEnterKey();
    pauseThread();
    pressEnterKey();
    pauseThread();
    pressSKey();
    pauseThread();
    pressPageDownKey();
    pauseThread();
    pressPageDownKey();
    pauseThread(150);
  }

private void startQuestMacro() {
    isRunning = true;

    macroThread = new Thread(() -> {
      System.out.println("Macro started");

      if (!isKingExisted()) {
        handleKingNotExisted();
        return;
      }

      while (true) {
        try {
          getQuestOfKing();
          System.out.println("Macro running..");

          if (isQuestRight()) {
            System.out.println("Quest is right..");
            teleportToTarget(huntingGroundCharacter.getText());
            playReadySound();
            System.out.println("Macro stopped");
            break; // 올바른 퀘스트가 있으면 텔레포트하고 종료

          } else {
            handleIncorrectQuest();
          }
          Thread.sleep(200);
        } catch (Exception e) {
          handleInterruption();
          break;
        }
      }
    });
    macroThread.start();
  }

  private void handleInterruption() {
    Thread.currentThread().interrupt();
    System.out.println("Macro interrupted during quest processing.");
  }

  private void handleIncorrectQuest() {
    System.out.println("Quest is not correct, trying again.");
  }

  private void handleKingNotExisted() {
      showInfoAlert("The King is NOT PRESENT.");
      System.out.println("Macro stopped");
  }

  private void stopQuestMacro() {
    if (!isRunning) {
      return;
    }

    isRunning = false;
    if (macroThread != null && macroThread.isAlive()) {
      macroThread.interrupt();  // 매크로 스레드를 중지
      System.out.println("macro stopped");
      showInfoAlert("매크로가 중지되었습니다.");
    }
  }

  private boolean isQuestRight() {

    Point targetPoint1 = findImageOnScreen(imageOfMonster01, "imageOfMonster01");
    Point targetPoint2 = findImageOnScreen(imageOfMonster02, "imageOfMonster02");
    Point targetPoint3 = findImageOnScreen(imageOfMonster03, "imageOfMonster03");
    Point targetPoint4 = findImageOnScreen(imageOfMonster04, "imageOfMonster04");
    Point targetPoint5 = findImageOnScreen(imageOfMonster05, "imageOfMonster05");

    return targetPoint1 != null
        || targetPoint2 != null
        || targetPoint3 != null
        || targetPoint4 != null
        || targetPoint5 != null;
  }

  public void pauseThread() throws InterruptedException {
    Random rand = new Random();
    // 평균 200ms, 표준편차 50ms의 정규 분포로 지연 시간을 계산
    int delay = (int) (rand.nextGaussian() * 50 + 200);
    // 지연 시간을 200ms ~ 400ms 범위로 제한
    delay = Math.max(200, Math.min(250, delay));
    Thread.sleep(delay);
  }

  public void pauseThread(int delay) throws InterruptedException {
    Thread.sleep(delay);
  }

  public void mouseClick() {
    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK); // 왼쪽 버튼 클릭
    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
  }

  public void pressEnterKey() {
    robot.keyPress(KeyEvent.VK_ENTER);  // 엔터 키 누르기
    robot.keyRelease(KeyEvent.VK_ENTER); // 엔터 키 떼기
  }

  public void pressArrowDownKey() {
    robot.keyPress(KeyEvent.VK_DOWN);  // 아래 방향키 누르기
    robot.keyRelease(KeyEvent.VK_DOWN); // 아래 방향키 떼기
  }

  public void pressSKey() {
    robot.keyPress(KeyEvent.VK_S);  // 'S' 키 누르기
    robot.keyRelease(KeyEvent.VK_S); // 'S' 키 떼기
  }

  public void pressPageDownKey() {
    robot.keyPress(KeyEvent.VK_PAGE_DOWN);  // Page Down 키 누르기
    robot.keyRelease(KeyEvent.VK_PAGE_DOWN); // Page Down 키 떼기
  }

  private void pressUKey() {
    robot.keyPress(KeyEvent.VK_U);  // U 키 누르기
    robot.keyRelease(KeyEvent.VK_U); // U 키 떼기
  }

  private void pressAKey() {
    robot.keyPress(KeyEvent.VK_A);  // A 키 누르기
    robot.keyRelease(KeyEvent.VK_A); // A 키 떼기
  }

  private void pressNumber9Key() {
    robot.keyPress(KeyEvent.VK_9);
    robot.keyRelease(KeyEvent.VK_9);
  }

  private void typeCharacterName(String name) {
//    for (char c : name.toCharArray()) {
//      int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
//      if (keyCode != KeyEvent.VK_UNDEFINED) {
//        robot.keyPress(keyCode);  // 해당 문자의 키 누르기
//        robot.keyRelease(keyCode); // 해당 문자의 키 떼기
//        try {
//          pauseThread(50);  // 키 간에 잠깐 대기 (너무 빠르게 타이핑하지 않도록)
//        } catch (InterruptedException e) {
//          throw new RuntimeException(e);
//        }
//      }
//    }
//    Screen screen = new Screen();
//    screen.type(name);
    nameToClipboard(name);
    pasteNameInClip();
  }

  private void pasteNameInClip() {
    robot.keyPress(KeyEvent.VK_CONTROL);
    robot.keyPress(KeyEvent.VK_V);
    robot.keyRelease(KeyEvent.VK_V);
    robot.keyRelease(KeyEvent.VK_CONTROL);
  }

  private void nameToClipboard(String name) {
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    clipboard.setContents(new StringSelection(name), null);
  }

  // 마우스 이동
  public void moveMouseToTarget(Point targetPoint) {
    try {
      Robot robot = new Robot();

      // 현재 마우스 위치 얻기
      Point currentPosition = MouseInfo.getPointerInfo().getLocation();

      int startX = currentPosition.x;
      int startY = currentPosition.y;
      int endX = targetPoint.x;
      int endY = targetPoint.y;

      int steps = 20; // 이동 구간 수 (숫자가 클수록 더 부드럽고 천천히 이동)
      int delay = 8; // 각 구간을 이동한 후 기다리는 시간 (밀리초)

      // 구간을 나누어 이동
      for (int i = 0; i <= steps; i++) {
        // 현재 구간의 비율을 계산 (0.0 ~ 1.0)
        double ratio = (double) i / steps;

        // 현재 위치에서 목표 위치로 비례 이동
        int x = (int) (startX + (endX - startX) * ratio);
        int y = (int) (startY + (endY - startY) * ratio);

        // 마우스 이동
        robot.mouseMove(x, y);
        // 각 구간을 이동 후 딜레이를 줘서 천천히 움직이게 함
        pauseThread(delay);
      }
    } catch (AWTException e) {
      handleError("마우스 이동 실패", e);
    } catch (InterruptedException e) {
      handleInterruption();
    }
  }

  private void playReadySound() throws Exception {
    Player player = new Player(getClass().getClassLoader().getResourceAsStream("static/mp3/ready_to_play.mp3"));
    player.play();
  }

  // UI 스레드에서 알림창 생성
  private void showInfoAlert(String message) {
    Platform.runLater(() -> {
      Alert alert = new Alert(AlertType.INFORMATION);
      alert.setTitle("information");
      alert.setHeaderText(null);
      alert.setContentText(message);
      alert.showAndWait();
    });
  }

  // 에러 발생 시 알림 띄움
  private void handleError(String message, Exception e) {
    Platform.runLater(() -> {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("에러");
      alert.setHeaderText(message);
      alert.setContentText(e.getMessage());
      alert.showAndWait();
    });
  }

  @FXML
  public void selectImageOfKing() {
    selectImage(imageOfKing);
  }

  private void selectImage(ImageView imageView) {
    File pickedFile = pickFile();

    if (pickedFile != null) {
      setImage(pickedFile, imageView);
    }
  }

  private File pickFile() {
    return fileChooser.showOpenDialog(imageOfKing.getScene().getWindow());
  }

  private void setImage(File pickedFile, ImageView imageView) {
    imageView.setImage(new Image(pickedFile.toURI().toString()));
  }

  @FXML
  public void selectImageOfMonster01() {
    selectImage(imageOfMonster01);
  }

  @FXML
  public void selectImageOfMonster02() {
    selectImage(imageOfMonster02);
  }

  @FXML
  public void selectImageOfMonster03() {
    selectImage(imageOfMonster03);
  }

  @FXML
  public void selectImageOfMonster04() {
    selectImage(imageOfMonster04);
  }

  @FXML
  public void selectImageOfMonster05() {
    selectImage(imageOfMonster05);
  }

  // 1. ImageView 를 BufferedImage 로 변환
  public BufferedImage convertImageToBufferedImage(Image image) {
    int width = (int) image.getWidth();
    int height = (int) image.getHeight();
    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    PixelReader pixelReader = image.getPixelReader();
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        // Get the color of each pixel
        javafx.scene.paint.Color fxColor = pixelReader.getColor(x, y);

        // Convert JavaFX color to AWT color
        int red = (int) (fxColor.getRed() * 255);   // Red channel
        int green = (int) (fxColor.getGreen() * 255); // Green channel
        int blue = (int) (fxColor.getBlue() * 255);  // Blue channel
        int alpha = (int) (fxColor.getOpacity() * 255); // Alpha channel

        // Create a 32-bit ARGB color (alpha << 24 | red << 16 | green << 8 | blue)
        int awtColor = (alpha << 24) | (red << 16) | (green << 8) | blue;
        bufferedImage.setRGB(x, y, awtColor);
      }
    }
    return bufferedImage;
  }

  // 2. bufferImage 를 opencv 의 mat 형식으로 변환
  public Mat bufferedImageToMat(BufferedImage image) {
    int width = image.getWidth();
    int height = image.getHeight();
    // CvType.CV_8UC3: 3채널(이미지에서 R, G, B)로 Mat 생성
    Mat mat = new Mat(height, width, CvType.CV_8UC3);

    // BufferedImage에서 픽셀 데이터를 가져오기
    int[] data = new int[width * height];
    image.getRGB(0, 0, width, height, data, 0, width);

    // Mat 객체에 데이터 복사
    byte[] byteData = new byte[width * height * 3];
    int index = 0;

    for (int i = 0; i < data.length; i++) {
      // 색상 정보를 ARGB로 가져옴
      int argb = data[i];
      // BGR로 변환
      byteData[index++] = (byte) ((argb >> 16) & 0xFF); // Blue
      byteData[index++] = (byte) ((argb >> 8) & 0xFF);  // Green
      byteData[index++] = (byte) (argb & 0xFF);         // Red
    }

    // Mat 객체에 RGB 데이터를 넣기 (BGR 순서)
    mat.put(0, 0, byteData);

    return mat;
  }

  // 3. 이미지를 검색할 대상 화면을 스크린샷으로 촬영
  public BufferedImage captureScreen() {
    Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
    return robot.createScreenCapture(screenRect);
  }

  // 4. 스크린샷 화면도 mat 형식으로 변환
  public Mat captureScreenAsMat() {
    return bufferedImageToMat(captureScreen());
  }

  // 5. 두 이미지를 비교하여 매칭되는 부분을 찾고 해당 위치의 좌표를 반환
  public Point getLocBetweenImages(Mat screenShot, Mat imageView, String imageName) {
    // 결과 행렬 (템플릿 매칭 결과를 저장)
    Mat matchResult = new Mat();
    // 템플릿 매칭 수행 (TM_CCOEFF_NORMED 를 사용하여 상관 계수를 계산)
    Imgproc.matchTemplate(screenShot, imageView, matchResult, Imgproc.TM_CCOEFF_NORMED);
    // 결과 행렬에서 최댓값과 최댓값의 위치를 찾아 템플릿의 일치 부분 찾기
    Core.MinMaxLocResult mmr = Core.minMaxLoc(matchResult);
    double threshold = 0.85; // 임계값을 원하는 값으로 설정 (0.0 ~ 1.0)

    System.out.println(imageName + ":" + mmr.maxVal);
    if ((mmr.maxVal >= threshold)) {
      // x, y 값을 사용하여 java.awt.Point 생성하고 최댓값의 위치를 반환
      return new Point((int) mmr.maxLoc.x, (int) mmr.maxLoc.y);
    }
    return null;
  }

  private Mat imageViewToMat(ImageView imageView) {
    Image image = imageView.getImage();
    return bufferedImageToMat(convertImageToBufferedImage(image));
  }

  private Point findImageOnScreen(ImageView imageView, String imageName) {
    // 이미지를 비교하고 일치하는 부분의 좌표를 찾기
    return getLocBetweenImages(captureScreenAsMat(), imageViewToMat(imageView), imageName);
  }

}
