package com.example.speechapp;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class PopupController {

    @FXML
    private TextField inputTextField;

    @FXML
    private Label descriptionLabel;
    @FXML
    private Label transcribedText;
    @FXML
    private Label timerLabel;

    @FXML
    private Button recordButton;
    @FXML
    private Button stopButton;
    @FXML
    private Button closeButton;

    private int words_per_minute;
    private float accuracy;
    private TargetDataLine line;
    private PopupCallback callback;
    private long startTimeMillis; // Variable to store start time
    String phrase;

    public void initialize() {
        // Initially disable both buttons
        recordButton.setDisable(true);
        stopButton.setDisable(true);

        // Add listener to inputTextField
        inputTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Enable buttons when there's text in the input field
            boolean hasText = !newValue.trim().isEmpty();
            recordButton.setDisable(!hasText);
            stopButton.setDisable(!hasText);
        });

        // Populate the label with the desired text when the scene is loaded
        phrase = PhraseGenerator.getRandomPhrase();
        descriptionLabel.setText(phrase);
        words_per_minute = 0;
        accuracy = 0;
    }

    public void setCallback(PopupCallback callback) {
        this.callback = callback;
    }

    @FXML
    private void handleClose() {
        closePopup();
    }

    @FXML
    private void handleRecord() {
        System.out.println("Record button was pressed");
        recordButton.setDisable(true);
        startTimeMillis = System.currentTimeMillis(); // Store start time
        updateTimer(); // Update timer label
        timerLabel.setVisible(true);

        // Start recording
        AudioFormat format = new AudioFormat(44100, 16, 2, true, true);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

        if (!AudioSystem.isLineSupported(info)) {
            System.err.println("Line not supported");
            return;
        }

        try {
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();

            // Create a new thread for capturing audio
            Thread captureThread = new Thread(() -> {
                AudioInputStream ais = new AudioInputStream(line);

                // Specify the file to save the recorded audio
                File audioFile = new File("audio.wav");

                try {
                    AudioSystem.write(ais, AudioFileFormat.Type.WAVE, audioFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            captureThread.start();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleStop() throws IOException {
        System.out.println("Stop button was pressed");
        stopButton.setDisable(true);
        System.out.println("Button was disabled");
        timerLabel.setVisible(false);

        // Stop recording
        if (line != null) {
            line.stop();
            line.close();
        }

        // Calculate elapsed time
        long elapsedTimeMillis = System.currentTimeMillis() - startTimeMillis;
        int elapsedTimeSeconds = (int) (elapsedTimeMillis / 1000);

        String inputText = inputTextField.getText();
        System.out.println("Given for transcription");
        String transcription = App.startTranscribing();
        int noOfTranscribedWords = 25;

        words_per_minute = (noOfTranscribedWords / elapsedTimeSeconds) * 60;
        accuracy= StringChecker.accuracyScore(phrase, transcription);

        transcribedText.setText(transcription);
        if (callback != null) {
            callback.onInputEntered(inputText, words_per_minute, accuracy);
        }
    }

    private void updateTimer() {
        // Update timer label every second
        Thread timerThread = new Thread(() -> {
            while (!stopButton.isDisabled()) {
                long elapsedTimeMillis = System.currentTimeMillis() - startTimeMillis;
                int elapsedTimeSeconds = (int) (elapsedTimeMillis / 1000);
                int seconds = elapsedTimeSeconds % 60;
                int minutes = (elapsedTimeSeconds / 60) % 60;
                int hours = elapsedTimeSeconds / 3600;

                String formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);

                // Update timer label on JavaFX application thread
                Platform.runLater(() -> timerLabel.setText(formattedTime));

                try {
                    Thread.sleep(1000); // Update every second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        timerThread.setDaemon(true); // Daemonize the thread to allow the application to exit
        timerThread.start();
    }


    private void closePopup() {
        // Code to close the popup window
        // For example:
        inputTextField.getScene().getWindow().hide();
    }
}

