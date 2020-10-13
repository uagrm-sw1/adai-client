package com.example.software_cliente.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExerciseExam {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("ejercicio_id")
    @Expose
    private int exerciseId;

    @SerializedName("examen_id")
    @Expose
    private int examId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }
}
