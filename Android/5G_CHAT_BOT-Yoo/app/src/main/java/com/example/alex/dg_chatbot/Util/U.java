package com.example.alex.dg_chatbot.Util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.example.alex.dg_chatbot.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by alex on 2018. 4. 20..
 */

public class U {
    private static final U ourInstance = new U();

    public static U getInstance() {
        return ourInstance;
    }

    private U() {
    }

    public void showYesNoPopup(Context context, String title, String msg,
                               String cName, SweetAlertDialog.OnSweetClickListener cEvent,
                               String oName, SweetAlertDialog.OnSweetClickListener oEvent) {
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(title)
                .setContentText(msg)
                .setConfirmText(cName)
                .setConfirmClickListener(cEvent)
                .setCancelText(oName)
                .setCancelClickListener(oEvent)
                .show();
    }

    public void showWarningPopup(Context context, String title, String msg) {
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(title)
                .setContentText(msg)
                .show();
    }

    public void showSuccessPopup(Context context, String title, String msg) {
        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(title)
                .setContentText(msg)
                .show();
    }


    public void showErrorPopup(Context context, String title, String msg) {
        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(title)
                .setContentText(msg)
                .show();

    }

    public void showNormalPopup(Context context, String title, String msg) {
        new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText(title)
                .setContentText(msg)
                .show();
    }

//    public SweetAlertDialog showLoading(Context context, String msg, String color) {
//        SweetAlertDialog progressDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
//        progressDialog.getProgressHelper().setBarColor(Color.parseColor(color));
//        progressDialog.setTitleText(msg)
//                .setCancelable(false);
//        progressDialog.show();
//        return progressDialog;
//    }

    public void Log(String content){
        Log.i("LOG", content);
    }

    public void eclassLog(String content){
        Log.i("eclass", content);
    }
    public void mainLog(String content){
        Log.i("main", content);
    }
    public void resultLog(String content){
        Log.i("result", content);
    }

    public void sharedPreferencesLog(String content){
        Log.i("sharedPreferences", content);
    }

}
