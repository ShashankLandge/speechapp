package com.example.speechapp;

public interface PopupCallback {
    void onInputEntered(String inputText, int words_per_minute, float accuracy);
}
