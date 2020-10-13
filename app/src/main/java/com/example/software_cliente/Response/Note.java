package com.example.software_cliente.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Note {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("nota")
    @Expose
    private float note;

    @SerializedName("estudiante_id")
    @Expose
    private int studentId;

    @SerializedName("tema_id")
    @Expose
    private int lessonId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }
}
