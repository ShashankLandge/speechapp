package com.example.speechapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class MainController implements PopupCallback {

    @FXML
    private ListView<String> sessionListView;

    private ObservableList<String> sessionList = FXCollections.observableArrayList();

    ArrayList<Session> allSessions = new ArrayList<>();
    private static int id = 0;

    @FXML
    private void initialize() {
        sessionListView.setItems(sessionList);
        sessionListView.setCellFactory(param -> new SessionListCell(sessionList));
    }

    @FXML
    private void handleAdd() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Popup.fxml"));
            Parent root = loader.load();

            PopupController popupController = loader.getController();
            popupController.setCallback(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void printAllSessions(){
        sessionList.clear(); // Clear the list to avoid duplicates
        for(Session element: allSessions){
            sessionList.add(element.description + "    " +"A :" + element.accuracy + "    "+ "WPM: " + element.words_per_minute);
        }
    }

    @Override
    public void onInputEntered(String description, int words_per_minute, float accuracy) {
        Session temp = new Session(description, words_per_minute, accuracy, id++);

        allSessions.add(temp);
        printAllSessions();
    }
}
