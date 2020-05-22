package com.example.stapemotion.model;

import com.google.gson.annotations.SerializedName;

public class Emotion {

    @SerializedName("date_created")
    private String date_created;

    @SerializedName("emotion")
    private int emotion;

    @SerializedName("id")
    private String id;

    public Emotion() {
    }

    public Emotion(String date_created, int emotion, String id) {
        this.date_created = date_created;
        this.emotion = emotion;
        this.id = id;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public int getEmotion() {
        return emotion;
    }

    public void setEmotion(int emotion) {
        this.emotion = emotion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
