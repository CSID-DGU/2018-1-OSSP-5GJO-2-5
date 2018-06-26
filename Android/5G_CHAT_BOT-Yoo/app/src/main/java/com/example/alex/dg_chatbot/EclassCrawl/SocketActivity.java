package com.example.alex.dg_chatbot.EclassCrawl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.ProviderTestCase2;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.alex.dg_chatbot.R;

import org.apache.http.HttpConnection;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ClientConnectionRequest;
import org.apache.http.conn.ManagedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.Key;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

public class SocketActivity extends AppCompatActivity {

    private static final String PORTAL = "https://portal.dongguk.edu/member/login/login.do?sso=ok";

    private final String USER_AGENT = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8";

    private static final String ID = "2011112325";
    private static final String PWD = "dinermint1@#";

    private Button btPost;
    private Button btPortal;
    private TextView tvResult;
    private URL pureUrl = new URL(PORTAL);
    private Document document;
    private Map<String, String> loginCookie;
    private Map<String, String> sessionCookie;
//    private JsoupTest jsoupTest;

    private EditText etUdrimsID;
    private EditText etUdrimsPwd;

    private Connection.Response loginPageResponse;




    public SocketActivity() throws MalformedURLException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        HttpsURLConnection httpsURLConnection = null;
        HttpURLConnection connection = null;

        tvResult = findViewById(R.id.tvResult);

        etUdrimsID = findViewById(R.id.etUdrimsID);
        etUdrimsPwd = findViewById(R.id.etUdrimsPwd);

        btPost = findViewById(R.id.btPost);
        btPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            HttpUrlConnectionExample hc = new HttpUrlConnectionExample();
                            hc.connectWithAgent();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });

        btPortal = findViewById(R.id.btPortal);
        btPortal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = etUdrimsID.getText().toString();
                String pwd = etUdrimsPwd.getText().toString();

                //deprecate later
                id = ID;
                pwd = PWD;

                final Map<String, String> data = new HashMap<>();
                data.put("userId",id);
                data.put("userPwd", pwd);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
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
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                loginCookie = loginPageResponse.cookies();
                                try {
                                    document = loginPageResponse.parse();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();


                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Connection.Response response = null;
                                try {
                                    response = Jsoup.connect(PORTAL)
                                            .userAgent(USER_AGENT)
                                            .timeout(3000)
                                            .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                                            .header("Accept-Encoding", "gzip, deflate, br")
                                            .header("Accpet-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                                            .header("Referer","https://portal.dongguk.edu/")
                                            .header("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36")
                                            .header("Upgrade-Insecure-Requests","1")
                                            .cookies(loginCookie)
                                            .data(data)
                                            .method(Connection.Method.POST)
                                            .execute();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                sessionCookie = response.cookies();
                                tvResult.setText(sessionCookie.toString());
                            }
                        }).start();

                    }
                });
            }
        });







//        btPost.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Thread thread = new Thread(){
//                    @Override
//                    public void run() {
//                        try {
//                            HttpClient httpClient = getHttpClient();
//                            URI url = new URI(PORTAL);
//
//                            HttpPost httpPost = new HttpPost();
//                            httpPost.setURI(url);
//
//                            List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>(2);
//                            nameValuePairs.add(new BasicNameValuePair("userId", ID));
//                            nameValuePairs.add(new BasicNameValuePair("userPwd", PWD));
//
//                            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//
//                            HttpResponse response = httpClient.execute(httpPost);
//                            String responseString = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
//
//                            Log.i("socketAct", responseString);
//                        } catch (UnrecoverableKeyException e) {
//                            e.printStackTrace();
//                        } catch (NoSuchAlgorithmException e) {
//                            e.printStackTrace();
//                        } catch (KeyStoreException e) {
//                            e.printStackTrace();
//                        } catch (KeyManagementException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        } catch (CertificateException e) {
//                            e.printStackTrace();
//                        } catch (URISyntaxException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                };
//
//                thread.start();
//            }
//        });
    }

    public static HttpClient getHttpClient() throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException, CertificateException {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            MySocketFactory mySocketFactory = new MySocketFactory(trustStore);
            mySocketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", mySocketFactory, 443));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
            return new DefaultHttpClient(ccm, params);

        }catch (Exception e){
            return new DefaultHttpClient();
        }
    }

}
