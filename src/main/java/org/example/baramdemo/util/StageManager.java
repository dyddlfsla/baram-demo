package org.example.baramdemo.util;

import javafx.stage.Stage;

public class StageManager {

  private Stage stage;
  private static final StageManager stageManager = new StageManager();

  private StageManager() {}

  public static StageManager getInstance() {
    return stageManager;
  }

  public static Stage getStage() {
    return stageManager.stage;
  }

  public void setStage(Stage stage) {
    this.stage = stage;
  }

}
