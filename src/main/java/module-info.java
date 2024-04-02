module com.example.speechapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires assemblyai.java;
    requires json.simple;

    opens com.example.speechapp to javafx.fxml;
    exports com.example.speechapp;
}