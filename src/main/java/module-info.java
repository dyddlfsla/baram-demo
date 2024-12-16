module org.example.baramdemo {

  requires javafx.fxml;
  requires org.controlsfx.controls;
  requires org.kordamp.bootstrapfx.core;
  requires com.github.kwhat.jnativehook;
  requires opencv;
  requires sikulixapi;
  requires jlayer;

  opens org.example.baramdemo to javafx.fxml;
  opens org.example.baramdemo.controller to javafx.fxml;

  exports org.example.baramdemo.controller;
  exports org.example.baramdemo;
}