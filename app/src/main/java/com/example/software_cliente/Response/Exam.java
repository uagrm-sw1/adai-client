package com.example.software_cliente.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Exam {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("nombre")
    @Expose
    private String title;

    @SerializedName("cantidad_ejercicio")
    @Expose
    private int amountExercise;

    @SerializedName("tema_id")
    @Expose
    private int lessonId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAmountExercise() {
        return amountExercise;
    }

    public void setAmountExercise(int amountExercise) {
        this.amountExercise = amountExercise;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }
}
