package com.example.alex.dg_chatbot.UI.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.alex.dg_chatbot.R;
import com.example.alex.dg_chatbot.UI.Main.MainActivity;
import com.example.alex.dg_chatbot.Util.ResultCode;
import com.example.alex.dg_chatbot.Util.U;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SelectFavoriteActivity extends RootActivity {

    private LinearLayout llStart;
    private ResultCode resultCode;

    private FrameLayout flNews, flFood, flLib, flSche,
            flJob, flVol, flMap, flTimetable, flMan;

    private FrameLayout flNewsCheck, flFoodCheck, flLibCheck, flScheCheck,
            flJobCheck, flVolCheck, flMapCheck, flTimetableCheck, flManCheck;

    private boolean newsChecked = false, foodChecked = false, libChecked = false, scheChecked = false,
            jobChecked = false, volChecked = false, mapChecked = false, timetableChecked = false, manChecked = false;

    private Map<String, Boolean> favMap = new HashMap<>();

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor spEditor;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_favorite);

        llStart = findViewById(R.id.llStart);

        favMap.put("news", false);
        favMap.put("food", false);
        favMap.put("lib", false);
        favMap.put("sche", false);
        favMap.put("job", false);
        favMap.put("vol", false);
        favMap.put("map", false);
        favMap.put("timetable", false);
        favMap.put("man", false);

        sharedPreferences = getSharedPreferences("fav", MODE_PRIVATE);
        spEditor = sharedPreferences.edit();

        spEditor.putBoolean("news", false);
        spEditor.putBoolean("food", false);
        spEditor.putBoolean("lib", false);
        spEditor.putBoolean("sche", false);
        spEditor.putBoolean("job", false);
        spEditor.putBoolean("vol", false);
        spEditor.putBoolean("map", false);
        spEditor.putBoolean("timetable", false);
        spEditor.putBoolean("man", false);

        spEditor.commit();

        flNews = findViewById(R.id.flNews);
        flFood = findViewById(R.id.flFood);
        flLib = findViewById(R.id.flLib);
        flSche = findViewById(R.id.flSche);
        flJob = findViewById(R.id.flJob);
        flVol = findViewById(R.id.flVol);
        flMap = findViewById(R.id.flMap);
        flTimetable = findViewById(R.id.flTimetable);
        flMan = findViewById(R.id.flMan);

        flNewsCheck = findViewById(R.id.flNewsCheck);
        flFoodCheck = findViewById(R.id.flFoodCheck);
        flLibCheck = findViewById(R.id.flLibCheck);
        flScheCheck = findViewById(R.id.flScheCheck);
        flJobCheck = findViewById(R.id.flJobCheck);
        flVolCheck = findViewById(R.id.flVolCheck);
        flMapCheck = findViewById(R.id.flMapCheck);
        flTimetableCheck = findViewById(R.id.flTimetableCheck);
        flManCheck = findViewById(R.id.flManCheck);

        flNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!favMap.get("news")) {
                    flNewsCheck.setVisibility(View.VISIBLE);
                    favMap.put("news", true);
                    spEditor.putBoolean("news", true);
                } else {
                    flNewsCheck.setVisibility(View.GONE);
                    favMap.put("news", false);
                    spEditor.putBoolean("news", false);
                }
                spEditor.commit();
            }
        });

        flLib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!favMap.get("lib")) {
                    flLibCheck.setVisibility(View.VISIBLE);
                    favMap.put("lib", true);
                    spEditor.putBoolean("lib", true);
                } else {
                    flLibCheck.setVisibility(View.GONE);
                    favMap.put("lib", false);
                    spEditor.putBoolean("lib", false);
                }
                spEditor.commit();
            }
        });

        flFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!favMap.get("food")) {
                    flFoodCheck.setVisibility(View.VISIBLE);
                    favMap.put("food", true);
                    spEditor.putBoolean("food", true);
                } else {
                    flFoodCheck.setVisibility(View.GONE);
                    favMap.put("food", true);
                    spEditor.putBoolean("food", false);
                }
                spEditor.commit();
            }
        });

        flSche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!favMap.get("sche")) {
                    flScheCheck.setVisibility(View.VISIBLE);
                    favMap.put("sche", true);
                    spEditor.putBoolean("sche", true);
                } else {
                    flScheCheck.setVisibility(View.GONE);
                    favMap.put("sche", false);
                    spEditor.putBoolean("sche", false);
                }
                spEditor.commit();
            }
        });

        flJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!favMap.get("job")) {
                    flJobCheck.setVisibility(View.VISIBLE);
                    favMap.put("job", true);
                    spEditor.putBoolean("job", true);
                } else {
                    flJobCheck.setVisibility(View.GONE);
                    favMap.put("job", false);
                    spEditor.putBoolean("job", false);
                }
                spEditor.commit();
            }
        });

        flVol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!favMap.get("vol")) {
                    flVolCheck.setVisibility(View.VISIBLE);
                    favMap.put("vol", true);
                    spEditor.putBoolean("vol", true);
                } else {
                    flVolCheck.setVisibility(View.GONE);
                    favMap.put("vol", false);
                    spEditor.putBoolean("vol", false);
                }
                spEditor.commit();
            }
        });

        flMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!favMap.get("map")) {
                    flMapCheck.setVisibility(View.VISIBLE);
                    favMap.put("map", true);
                    spEditor.putBoolean("map", true);
                } else {
                    flMapCheck.setVisibility(View.GONE);
                    favMap.put("map", false);
                    spEditor.putBoolean("map", true);
                }
                spEditor.commit();
            }
        });

        flTimetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!favMap.get("timetable")) {
                    flTimetableCheck.setVisibility(View.VISIBLE);
                    favMap.put("timetable", true);
                    spEditor.putBoolean("timetable", true);
                } else {
                    flTimetableCheck.setVisibility(View.GONE);
                    favMap.put("timetable", false);
                    spEditor.putBoolean("timetable", false);
                }
                spEditor.commit();
            }
        });

        flMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!favMap.get("man")) {
                    flManCheck.setVisibility(View.VISIBLE);
                    favMap.put("man", true);
                    spEditor.putBoolean("man", true);
                } else {
                    flManCheck.setVisibility(View.GONE);
                    favMap.put("man", false);
                    spEditor.putBoolean("man", false);
                }
                spEditor.commit();
            }
        });

        llStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                U.getInstance().showYesNoPopup(
                        SelectFavoriteActivity.this,
                        "선택 완료",
                        "선택을 완료하시겠습니까?",
                        "확인",
                        new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                Intent intent = new Intent(SelectFavoriteActivity.this, MainActivity.class);
                                intent.putExtra("favMap", (Serializable) favMap);
                                startActivity(intent);
                            }
                        },
                        "취소",
                        new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        }
                );
            }
        });
    }

    public boolean isAllEmpty(Map<String, Boolean> map) {
        if (favMap.get("news") == false && favMap.get("food") == false && favMap.get("lib") == false
                && favMap.get("sche") == false && favMap.get("job") == false && favMap.get("vol") == false
                && favMap.get("map") == false && favMap.get("timetable") == false && favMap.get("man") == false) {
            return false;
        } else return true;
    }

}
