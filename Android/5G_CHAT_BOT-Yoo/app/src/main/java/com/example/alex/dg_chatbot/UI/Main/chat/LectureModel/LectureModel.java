package com.example.alex.dg_chatbot.UI.Main.chat.LectureModel;

/**
 * Created by alex on 2018. 6. 21..
 */

public class LectureModel {
    String lectureName;
    String lectureProf;
    String lectureNum;
    String lectureTime;
    String lectureGrade;
    String lectureRoom;
    String lectureMajor;

    public LectureModel(String lectureName, String lectureProf, String lectureNum, String lectureTime, String lectureGrade, String lectureRoom, String lectureMajor) {
        this.lectureName = lectureName;
        this.lectureProf = lectureProf;
        this.lectureNum = lectureNum;
        this.lectureTime = lectureTime;
        this.lectureGrade = lectureGrade;
        this.lectureRoom = lectureRoom;
        this.lectureMajor = lectureMajor;
    }

    public String getLectureName() {
        return lectureName;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public String getLectureProf() {
        return lectureProf;
    }

    public void setLectureProf(String lectureProf) {
        this.lectureProf = lectureProf;
    }

    public String getLectureNum() {
        return lectureNum;
    }

    public void setLectureNum(String lectureNum) {
        this.lectureNum = lectureNum;
    }

    public String getLectureTime() {
        return lectureTime;
    }

    public void setLectureTime(String lectureTime) {
        this.lectureTime = lectureTime;
    }

    public String getLectureGrade() {
        return lectureGrade;
    }

    public void setLectureGrade(String lectureGrade) {
        this.lectureGrade = lectureGrade;
    }

    public String getLectureRoom() {
        return lectureRoom;
    }

    public void setLectureRoom(String lectureRoom) {
        this.lectureRoom = lectureRoom;
    }

    public String getLectureMajor() {
        return lectureMajor;
    }

    public void setLectureMajor(String lectureMajor) {
        this.lectureMajor = lectureMajor;
    }
}
