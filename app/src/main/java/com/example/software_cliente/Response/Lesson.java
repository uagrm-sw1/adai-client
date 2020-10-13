package com.example.software_cliente.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lesson {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("titulo")
    @Expose
    private String title;

    @SerializedName("duracion")
    @Expose
    private int duration;

    @SerializedName("materia_id")
    @Expose
    private int subjectId;

    @SerializedName("curso_id")
    @Expose
    private int courseId;

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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
