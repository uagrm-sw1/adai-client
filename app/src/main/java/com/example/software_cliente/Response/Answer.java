package com.example.software_cliente.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Answer {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("contenido")
    @Expose
    private String content;

    @SerializedName("esCorrecto")
    @Expose
    private boolean isCorrect;

    @SerializedName("ejercicio_id")
    @Expose
    private int exerciseId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }
}
