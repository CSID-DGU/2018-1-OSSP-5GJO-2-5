package com.example.alex.dg_chatbot.EclassCrawl;

import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by alex on 2018. 5. 17..
 */

public class MySocketFactory extends org.apache.http.conn.ssl.SSLSocketFactory{
    private static final String PORTAL = "https://portal.dongguk.edu/member/login/login.do?sso=ok";
    private static final String ID = "2011112325";
    private static final String PWD = "dienrmint1@#";

    SSLContext sslContext;

    public MySocketFactory(KeyStore trustStore) throws NoSuchAlgorithmException, UnrecoverableKeyException, KeyStoreException, KeyManagementException {
        super(trustStore);
        sslContext = SSLContext.getInstance("TLS");
        TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };

        sslContext.init(null, new TrustManager[]{trustManager},null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
    }

    @Override
    public Socket createSocket(Socket sock, String host, int port, boolean autoClose) throws IOException {
        return sslContext.getSocketFactory().createSocket(sock, host, port, autoClose);
    }

    @Override
    public Socket createSocket() throws IOException {
        return sslContext.getSocketFactory().createSocket();
    }
}
