package com.example.speechapp;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject; // for JSON.simple
import com.assemblyai.api.AssemblyAI;
import com.assemblyai.api.resources.transcripts.types.*;
import org.json.simple.JSONValue;

public class App {
    public static String startTranscribing() throws IOException {
        // API key obtained from AssemblyAI
        String apiKey = "6b14f602fac44142bb4cf894a579a9ac";
        // Path to the audio file to upload
        String filePath = "E:\\Java\\SpeechAPP\\audio.wav";

        // Create HTTP connection
        URL url = new URL("https://api.assemblyai.com/v2/upload");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", apiKey);
        connection.setRequestProperty("Content-Type", "application/octet-stream");
        connection.setDoOutput(true);

        // Read file content and write to connection output stream
        try (OutputStream outputStream = connection.getOutputStream();
             FileInputStream fileInputStream = new FileInputStream(filePath)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }

        // Get response code and response message
        int responseCode = connection.getResponseCode();
        String responseMessage = connection.getResponseMessage();

        System.out.println("Response Code: " + responseCode);
        System.out.println("Response Message: " + responseMessage);

        String uploadUrl = null;
        // If successful, read response body
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder responseBody = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBody.append(line);
                }
                System.out.println("Response Body: " + responseBody.toString());

                // Parse JSON response to extract upload URL
                JSONObject jsonResponse = (JSONObject) JSONValue.parse(responseBody.toString());
                uploadUrl = (String) jsonResponse.get("upload_url");
                System.out.println("Upload URL: " + uploadUrl);
            }
        }

        // Close connection
        connection.disconnect();

        if (uploadUrl != null) {
            AssemblyAI client = AssemblyAI.builder()
                    .apiKey(apiKey)
                    .build();

            Transcript transcript = client.transcripts().transcribe(uploadUrl);

            System.out.println(transcript.getText());
            return transcript.getText().orElse("");
        } else {
            return "Upload failed. No upload URL obtained.";
        }
    }
}
