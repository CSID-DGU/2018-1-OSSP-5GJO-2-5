package com.example.alex.dg_chatbot.UI.Main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alex.dg_chatbot.R;
import com.example.alex.dg_chatbot.UI.Login.RootActivity;
import com.example.alex.dg_chatbot.UI.Main.chat.ChatFragment;
import com.example.alex.dg_chatbot.UI.Main.home.HomeFragment;
import com.example.alex.dg_chatbot.UI.Main.settings.SettingFragment;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends RootActivity {

    private Map<String, Boolean> favMap = new HashMap<>();

    private TextView mTextMessage;
    private ImageView ivHomeChar;

    private BottomNavigationItemView home, chat, settings;

    private android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @SuppressLint("RestrictedApi")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home: //메인 홈 화면
                    home.setIcon(getResources().getDrawable(R.drawable.home));
                    chat.setIcon(getResources().getDrawable(R.drawable.nc_chat));
                    settings.setIcon(getResources().getDrawable(R.drawable.nc_setting));
                    fragmentManager.beginTransaction().replace(R.id.flContent, new HomeFragment()).commit();
                    Log.i("navi","home selected");
                    return true;

                case R.id.navigation_chat: //챗봇 화면
                    home.setIcon(getResources().getDrawable(R.drawable.nc_home));
                    chat.setIcon(getResources().getDrawable(R.drawable.chat));
                    settings.setIcon(getResources().getDrawable(R.drawable.nc_setting));
                    fragmentManager.beginTransaction().replace(R.id.flContent, new ChatFragment()).commit();
                    Log.i("navi","chat selected");
                    return true;

                case R.id.navigation_setting: //세팅 화면
                    home.setIcon(getResources().getDrawable(R.drawable.nc_home));
                    chat.setIcon(getResources().getDrawable(R.drawable.nc_chat));
                    settings.setIcon(getResources().getDrawable(R.drawable.settings));
                    fragmentManager.beginTransaction().replace(R.id.flContent, new SettingFragment()).commit();
                    Log.i("navi", "setting selected");
                    return true;
            }
            return false;
        }
    };

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        home = findViewById(R.id.navigation_home);
        chat = findViewById(R.id.navigation_chat);
        settings = findViewById(R.id.navigation_setting);

        home.setIcon(getResources().getDrawable(R.drawable.home));
        chat.setIcon(getResources().getDrawable(R.drawable.nc_chat));
        settings.setIcon(getResources().getDrawable(R.drawable.nc_setting));

        ivHomeChar = findViewById(R.id.ivHomeChar);
        favMap = (HashMap)getIntent().getSerializableExtra("favMap");

        switchToHome();



    }

    public Map<String, Boolean> getFav(){
        return favMap;
    }

    public void switchToHome(){
        fragmentManager.beginTransaction().replace(R.id.flContent, new HomeFragment()).commitNow();
    }

    public void switchToChat(){
        fragmentManager.beginTransaction().replace(R.id.flContent, new HomeFragment()).commitNow();
    }

    public void switchToSetting(){
        fragmentManager.beginTransaction().replace(R.id.flContent, new HomeFragment()).commitNow();
    }

    public void switchToFrament(int itemId){
        switch (itemId) {
            case R.id.navigation_home:
                Log.i("navi","home method selected");
                fragmentManager.beginTransaction()
                        .replace(R.id.flContent, new HomeFragment()).commitNow();

            case R.id.navigation_chat:
                Log.i("navi","chat method selected");
                fragmentManager.beginTransaction()
                        .replace(R.id.flContent, new ChatFragment()).commitNow();

            case R.id.navigation_setting:
                Log.i("navi","setting method selected");
                fragmentManager.beginTransaction()
                        .replace(R.id.flContent, new SettingFragment()).commitNow();
        }
    }



}
