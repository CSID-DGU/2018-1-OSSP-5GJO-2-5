package com.example.alex.dg_chatbot.EclassCrawl;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.alex.dg_chatbot.R;
import com.example.alex.dg_chatbot.Util.U;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class JsoupActivity extends AppCompatActivity {

    private static final String PORTAL = "https://portal.dongguk.edu/member/login/login.do?sso=ok";
    private static final String PORTAL_MAIN = "https://portal.dongguk.edu/main/main.do";

    private static final String PORTAL_LOGIN = "https://sso.dongguk.edu:9443/sso/pmi-sso-login-uid-password2.html";

    private static final String ECLASS_LOGIN = "https://eclass.dongguk.edu/User.do?cmd=loginUser";

    private final String USER_AGENT = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8";

    private static final String ID = "2011112325";
    private static final String PWD = "dinermint1@#";

    org.jsoup.nodes.Document doc = null;
    org.jsoup.nodes.Document doc2 = null;
    org.jsoup.nodes.Document doc3 = null;

    private Map<String, String> data = new HashMap<>();
    private Map<String, String> loginTryCookie = new HashMap<>();
    private Map<String, String> loginSucCookie = new HashMap<>();
    private org.jsoup.Connection.Response loginPostResponse;
    private org.jsoup.Connection.Response loginPageResponse;

    private org.jsoup.Connection.Response eclassResponse;

    private Button btPortal;
    private TextView tvResult;

    private String resultText = "";

    private org.jsoup.nodes.Document loginPageDocument;
    private org.jsoup.nodes.Document loginSucDocument;

    public void portalLogin() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    //기본 로그인 페이지 쿠키 값 설정
                    @Override
                    public void run() {
                        try {
                            loginPageResponse = Jsoup.connect(PORTAL)
                                    .timeout(3000)
                                    .headers(getPortalHeaders())
                                    .method(org.jsoup.Connection.Method.GET)
                                    .execute();

                            Log.i("jsoupAct", loginPageResponse.toString());
                            loginTryCookie = loginPageResponse.cookies();
                            Log.i("jsoupAct", loginTryCookie.toString());
                            loginPageDocument = loginPageResponse.parse();
                            Log.i("jsoupAct", loginPageDocument.toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                //로그인 성공 후 쿠키 값 가져오기
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            loginPostResponse = Jsoup.connect(PORTAL)
                                    .timeout(3000)
                                    .headers(getPortalHeaders())
                                    .cookies(loginTryCookie)
                                    .data(data)
                                    .method(org.jsoup.Connection.Method.POST)
                                    .execute();
                            Log.i("jsoupPost", loginPostResponse.toString());
                            Log.i("jsoupPost", String.valueOf(loginPostResponse.statusCode()));
                            Log.i("jsoupPost", String.valueOf(loginPostResponse.cookies()));

                            loginSucCookie = loginPostResponse.cookies();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            org.jsoup.Connection.Response mainPortalResponse = Jsoup.connect(PORTAL_MAIN)
                                    .cookies(loginSucCookie)
                                    .data(data)
                                    .headers(getPortalMainHeaders())
                                    .method(org.jsoup.Connection.Method.POST)
                                    .execute();

                            Log.i("jsoupSuc", String.valueOf(mainPortalResponse.parse()));

                            Log.i("jsoupSuc", String.valueOf(loginSucCookie));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
            }
        });
    }

    public Map<String, String> getPortalHeaders(){
        Map<String, String> headers = new HashMap<>();

        headers.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        headers.put("Accept-Encoding","gzip, deflate, br");
        headers.put("Accept-Language","ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");
        headers.put("Referer","https://portal.dongguk.edu/");
        headers.put("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36");

        return headers;
    }

    public Map<String, String> getPortalMainHeaders(){
        Map<String, String> headers = new HashMap<>();

        headers.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        headers.put("Accept-Encoding","gzip, deflate, br");
        headers.put("Accept-Language","ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");
        headers.put("Referer","https://portal.dongguk.edu/member/login/login.do?sso=ok");
        headers.put("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36");
        headers.put("Upgrade-Insecure-Requests","1");
        return headers;
    }

    public Map<String, String> getEclassHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        headers.put("Accept-Encoding", "gzip, deflate, br");
        headers.put("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Origin", "https://eclass.dongguk.edu");
        headers.put("Referer", "https://eclass.dongguk.edu/");
        headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36");
        return headers;
    }

    public void eclassLogin() {

        new AsyncTask<Void, Void, String>(){
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    eclassResponse = Jsoup.connect(ECLASS_LOGIN)
                            .followRedirects(true)
                            .data("userDTO.userId", ID)
                            .data("userDTO,password", PWD)
                            .method(org.jsoup.Connection.Method.POST)
                            .timeout(15000)
                            .execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                try {
                    doc = Jsoup.connect("https://eclass.dongguk.edu/Schedule.do?cmd=viewLessonSchedule").followRedirects(true).cookies(eclassResponse.cookies()).followRedirects(true).method(org.jsoup.Connection.Method.POST).timeout(15000).post();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                U.getInstance().eclassLog(String.valueOf(eclassResponse.cookies()));
                U.getInstance().eclassLog(String.valueOf(eclassResponse.statusCode()));
                U.getInstance().eclassLog(String.valueOf(eclassResponse.statusMessage()));

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            U.getInstance().eclassLog(String.valueOf(eclassResponse.parse().text()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();





            }
        }.execute(new Void[0]);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsoup);

        data.put("userId", ID);
        data.put("userPwd", PWD);

        btPortal = findViewById(R.id.btPortal);
        tvResult = findViewById(R.id.tvResult);


//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                eclassLogin();
//
//            }
//        });
        portalLogin();



    }
}
