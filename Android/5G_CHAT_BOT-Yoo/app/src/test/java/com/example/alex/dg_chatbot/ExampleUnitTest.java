package com.example.alex.dg_chatbot;

import android.os.AsyncTask;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sendbird.android.shadow.com.google.gson.Gson;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static org.hamcrest.CoreMatchers.is;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private final String portalUrl = "http://portal.dongguk.edu/member/login/login.do?sso=ok/";
    private final String mainUrl = "https://portal.dongguk.edu/main/main.do";
    private WebViewClient wvc = new WebViewClient();
    private WebView wv;
    @Before
    public void setUp(){


    }
    @Test
    public void addition_isCorrect() throws Exception {
        String userId = "2011112325";
        String userPwd = "dinermint1@#";
        ObjectMapper objectMapper = new ObjectMapper();
        String exampleJson = "{\"key : \"value\"}";
        String json = objectMapper.writeValueAsString(exampleJson);
        new HttpUtil().execute(json);

        //쿠키 사용 x
        HttpConnection.Response responseDGPortal = (HttpConnection.Response) Jsoup
                .connect(portalUrl)
                .data("userId", userId)
                .data("userPwd",userPwd)
                .method(Connection.Method.POST)
                .execute();

        org.jsoup.nodes.Document doc = responseDGPortal.parse();
        System.out.println("TEXT : " + doc.text());


        //쿠키 사용
        android.webkit.CookieManager cookieManager = android.webkit.CookieManager.getInstance();
        String cookie = cookieManager.getCookie(portalUrl);
        HttpConnection.Response resWithCookie = (HttpConnection.Response) Jsoup
                .connect(mainUrl)
                .data("userId",userId)
                .data("userPwd",userPwd)
                .cookie("Cookie",cookie)
                .execute();

    }

    public class HttpUtil extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String url = portalUrl;
            try {
                URL obj = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type","application/json");
                byte[] outputInBytes = strings[0].getBytes("UTF-8");
                OutputStream os = conn.getOutputStream();
                os.write(outputInBytes);
                os.close();

                int retCode = conn.getResponseCode();
                InputStream is = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuffer response = new StringBuffer();
                while((line = br.readLine()) != null){
                    response.append(line);
                    response.append('\r');
                }
                br.close();

                String res = response.toString();


            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }
    }





}