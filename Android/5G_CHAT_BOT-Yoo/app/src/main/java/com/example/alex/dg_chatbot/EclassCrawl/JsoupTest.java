package com.example.alex.dg_chatbot.EclassCrawl;

import android.test.ProviderTestCase2;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;

/**
 * Created by alex on 2018. 5. 17..
 */

public class JsoupTest{
    final String PORTAL = "https://portal.dongguk.edu/member/login/login.do?sso=ok";
    final Connection.Response loginPageResponse;

    public JsoupTest() throws IOException {
        loginPageResponse = Jsoup.connect(PORTAL)
                .timeout(3000)
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Accpet-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                .header("Referer","https://portal.dongguk.edu/")
                .header("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36")
                .header("Upgrade-Insecure-Requests","1")
                .method(Connection.Method.GET)
                .execute();
    }

    public Map<String, String> getLoginCookies(){
        Map<String, String> loginCookies = loginPageResponse.cookies();
        return loginCookies;
    }

    public Document getDoc() throws IOException {
        return loginPageResponse.parse();
    }
}
