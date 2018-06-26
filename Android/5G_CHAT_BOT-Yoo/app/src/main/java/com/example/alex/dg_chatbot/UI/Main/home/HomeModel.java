package com.example.alex.dg_chatbot.UI.Main.home;

import android.widget.ImageView;

/**
 * Created by alex on 2018. 5. 22..
 */

public class HomeModel {
    int img;
    int title;
    String url;

    public HomeModel(int img, int title, String url) {
        this.img = img;
        this.title = title;
        this.url = url;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
