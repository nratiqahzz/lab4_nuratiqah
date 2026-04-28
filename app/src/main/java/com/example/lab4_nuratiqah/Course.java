package com.example.lab4_nuratiqah;
public class Course {

    String courseId;
    String courseName;

    // Empty constructor (WAJIB untuk Firebase)
    public Course() {
    }

    public Course(String courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }
}