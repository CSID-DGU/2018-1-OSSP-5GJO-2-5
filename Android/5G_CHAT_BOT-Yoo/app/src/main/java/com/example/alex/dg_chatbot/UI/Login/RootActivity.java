package com.example.alex.dg_chatbot.UI.Login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by alex on 2018. 5. 7..
 */

public class RootActivity extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    ProgressDialog pd;
    public void showPD(){
        if( pd == null){
            pd = new ProgressDialog(this);
            pd.setCancelable(false); //임의 취소 불가
            pd.setMessage(".. loading ..");
        }
        pd.show();
    }

    public void stopPD(){
        if( pd != null && pd.isShowing()){
            pd.dismiss();
            //pd = null;
        }
    }

    //현재 로그인 한 유저의 fb 유저 정보
    public FirebaseUser getUser(){ //그때 그때 뽑는다
        return FirebaseAuth.getInstance().getCurrentUser();
    }
}
