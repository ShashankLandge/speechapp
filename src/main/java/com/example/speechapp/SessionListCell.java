package com.example.speechapp;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

public class SessionListCell extends ListCell<String> {
    private final HBox content;
    private final Button deleteButton;
    private final ObservableList<String> sessionList;

    public SessionListCell(ObservableList<String> sessionList) {
        this.sessionList = sessionList;
        content = new HBox();
        deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> {
            String sessionString = getItem();
            if (sessionString != null) {
                sessionList.remove(sessionString);
            }
        });
        content.getChildren().add(deleteButton);

        // Apply dark theme only to the delete button
        deleteButton.setStyle("-fx-background-color: #4b8bbe; -fx-text-fill: white;");
        // Add padding to the content
        content.setStyle("-fx-padding: 5px;");
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            setText(item);
            setGraphic(content);
        }
    }
}
