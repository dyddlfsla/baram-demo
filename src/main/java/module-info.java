module org.example.baramdemo {
  requires javafx.fxml;

  requires org.controlsfx.controls;
  requires org.kordamp.bootstrapfx.core;

  opens org.example.baramdemo to javafx.fxml;
  exports org.example.baramdemo;
  exports org.example.baramdemo.controller;
  opens org.example.baramdemo.controller to javafx.fxml;
}