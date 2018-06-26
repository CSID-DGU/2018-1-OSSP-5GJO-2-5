package com.example.alex.dg_chatbot.UI.Main.chat.NoticeModel;

/**
 * Created by alex on 2018. 5. 21..
 */

public class NoticeModel {
    String id;
    String postUrl;
    String noticeType;
    String writer;
    String regDate;
    int hits;
    String title;
    String type;
    String content;


    public NoticeModel(String id, String postUrl, String noticeType, String writer, String regDate, int hits, String title, String type, String content) {
        this.id = id;
        this.postUrl = postUrl;
        this.noticeType = noticeType;
        this.writer = writer;
        this.regDate = regDate;
        this.hits = hits;
        this.title = title;
        this.type = type;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
