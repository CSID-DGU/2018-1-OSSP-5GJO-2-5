package com.example.alex.dg_chatbot.UI.Main.chat.ScheduleModel;

/**
 * Created by alex on 2018. 5. 21..
 */

public class ScheduleModel {
    int id;
    String preDate;
    String postDate;
    String content;

    public ScheduleModel(int id, String preDate, String postDate, String content) {
        this.id = id;
        this.preDate = preDate;
        this.postDate = postDate;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPreDate() {
        return preDate;
    }

    public void setPreDate(String preDate) {
        this.preDate = preDate;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
