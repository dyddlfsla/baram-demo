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
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
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

  private NativeKeyListener keyListener; // 필드로 NativeKeyListener 추가

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

  // 글로벌 키보드 초기화
  private void initializeGlobalKeyListener() {
    try {
      GlobalScreen.registerNativeHook();
      keyListener = createKeyListener();
      GlobalScreen.addNativeKeyListener(keyListener);
    } catch (Exception e) {
      handleError("failed to initialize keyListener", e);
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
      getQuestOfKing();

    });
  }

  private void getQuestOfKing() {
    moveMouseToTarget(new Point(0, 0));
    Point targetPoint = findImageOnScreen(imageOfKing);

    if (targetPoint == null) {
      showInfoAlert("Can't find image on the screen");
      return;
    }

    moveMouseToTarget(targetPoint);

    mouseClick();
    sleep(200);
    pressEnterKey();
    sleep(200);
    pressEnterKey();
    sleep(200);
    pressEnterKey();
    sleep(200);
    pressEnterKey();
    sleep(200);
    pressArrowDownKey();
    sleep(250);
    pressEnterKey();
    sleep(250);
    pressEnterKey();
    sleep(250);
    pressEnterKey();
    sleep(200);
    pressSKey();
    sleep(200);
    pressPageDownKey();
    sleep(200);
    pressPageDownKey();
    sleep(200);
  }

  private boolean isQuestRight() {

    return false;
  }

  public static void sleep(int milliseconds) {
    try {
      Thread.sleep(milliseconds);  // 지정된 시간(ms)만큼 대기
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  // 마우스를 클릭하는 메소드
  public void mouseClick() {
    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK); // 왼쪽 버튼 클릭
    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
  }

  // 엔터 키를 누르는 메소드
  public void pressEnterKey() {
    robot.keyPress(KeyEvent.VK_ENTER);  // 엔터 키 누르기
    robot.keyRelease(KeyEvent.VK_ENTER); // 엔터 키 떼기
  }

  // 아래 방향키를 누르는 메소드
  public void pressArrowDownKey() {
    robot.keyPress(KeyEvent.VK_DOWN);  // 아래 방향키 누르기
    robot.keyRelease(KeyEvent.VK_DOWN); // 아래 방향키 떼기
  }

  // 'S' 키를 누르는 메소드
  public void pressSKey() {
    robot.keyPress(KeyEvent.VK_S);  // 'S' 키 누르기
    robot.keyRelease(KeyEvent.VK_S); // 'S' 키 떼기
  }

  // Page Down 키를 누르는 메소드
  public void pressPageDownKey() {
    robot.keyPress(KeyEvent.VK_PAGE_DOWN);  // Page Down 키 누르기
    robot.keyRelease(KeyEvent.VK_PAGE_DOWN); // Page Down 키 떼기
  }

  // F1 키가 눌렸을 때 수행될 내용
  private boolean isF1KeyPressed(NativeKeyEvent e) {
    return e.getKeyCode() == NativeKeyEvent.VC_F1;
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

      int steps = 30; // 이동 구간 수 (숫자가 클수록 더 부드럽고 천천히 이동)
      int delay = 10; // 각 구간을 이동한 후 기다리는 시간 (밀리초)

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
        sleep(delay);
      }
    } catch (AWTException e) {
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

  // 3. 이미지를 찾을 전체 화면을 스크린 샷
  public BufferedImage captureScreen() {
    Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
    return robot.createScreenCapture(screenRect);
  }

  // 4. 스크린샷 화면도 mat 형식으로 변환
  public Mat captureScreenAsMat() {
    return bufferedImageToMat(captureScreen());
  }

  // 5. 두 이미지를 매칭
  public Point getLocBetweenImages(Mat screenShot, Mat imageView) {
    // 결과 행렬 (템플릿 매칭 결과를 저장)
    Mat matchResult = new Mat();

    // 템플릿 매칭 수행 (이 방법은 TM_CCOEFF_NORMED를 사용하여 상관 계수를 계산)
    Imgproc.matchTemplate(screenShot, imageView, matchResult, Imgproc.TM_CCOEFF_NORMED);

    // 결과 행렬에서 최댓값과 최댓값의 위치를 찾아 템플릿의 일치 부분 찾기
    Core.MinMaxLocResult mmr = Core.minMaxLoc(matchResult);

    double threshold = 0.8; // 임계값을 원하는 값으로 설정 (0.0 ~ 1.0)

    if ((mmr.maxVal >= threshold)) {
      // x, y 값을 사용하여 java.awt.Point 생성
      return new Point((int) mmr.maxLoc.x, (int) mmr.maxLoc.y);
    }

    // 최댓값의 위치를 반환
    return null;
  }

  private Mat imageViewToMat(ImageView imageView) {
    Image image = imageView.getImage();  // 예시로 imageOfKing 사용
    return bufferedImageToMat(convertImageToBufferedImage(image));

  }

  private Point findImageOnScreen(ImageView template) {
    // Step 1: ImageView 에서 이미지를 얻고 Mat 으로 변환
    Mat imageViewMat = imageViewToMat(template);

    // Step 3: 화면의 스크린샷을 Mat 형식으로 캡처
    Mat screenShot = captureScreenAsMat();

    // Step 4: 이미지를 비교하여 일치하는 부분의 좌표를 찾기
    return getLocBetweenImages(screenShot, imageViewMat);
  }

}
