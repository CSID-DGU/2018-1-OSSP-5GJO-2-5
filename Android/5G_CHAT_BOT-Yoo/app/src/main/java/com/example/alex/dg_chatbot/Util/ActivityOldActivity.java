package com.example.alex.dg_chatbot.Util;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alex.dg_chatbot.R;
import com.example.alex.dg_chatbot.UI.Login.RootActivity;
import com.example.alex.dg_chatbot.UI.Login.SelectFavoriteActivity;
import com.example.alex.dg_chatbot.UI.Login.SignUpActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Firebase를 이용한 로그인 처리
 * -익명 인증
 * -이메일, 비번 인증
 * -익명 인증의 이메일, 비번 계정으로 전환 처리
 */

public class ActivityOldActivity extends RootActivity {

    private ResultCode resultCode;

    private EditText etUserId;
    private EditText etUserPwd;
    private Button btEmailLogin;
    private Button btAnonymousLogin;
    private Button btSignUp;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onStart() {
        super.onStart();
        if(getUser() != null){
            Toast.makeText(this, "로그인 되었습니다.", Toast.LENGTH_LONG).show();
            startSelectActivity();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        etUserId = (EditText) findViewById(R.id.etUserId);
        etUserPwd = (EditText) findViewById(R.id.etUserPwd);
        btEmailLogin = (Button) findViewById(R.id.btEmailLogin);
        btAnonymousLogin = (Button) findViewById(R.id.btAnonymousLogin);
        btSignUp = (Button) findViewById(R.id.btSignUp);

        btEmailLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               emailSignIn(etUserId.getText().toString(), etUserPwd.getText().toString());
            }
        });

        btAnonymousLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               anonymousSignUp();
            }
        });

        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityOldActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    public void startSelectActivity(){
        Intent intent = new Intent(this, SelectFavoriteActivity.class);
        intent.putExtra("where", resultCode.getLOGIN_ACTIVITY());
        startActivity(intent);
    }

    public void anonymousSignUp(){
        showPD();
        firebaseAuth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //성공 -> 회원 정보 획득
                    FirebaseUser user = getUser();
                    if(user != null){
                        Log.i("Login", user.getUid());
//                        Log.i("Login", user.getEmail());
                    }

                    Toast.makeText(ActivityOldActivity.this, "익명 계정 생성 성공", Toast.LENGTH_LONG).show();
                    startSelectActivity();
                }else{
                    Toast.makeText(ActivityOldActivity.this, "익명 계정 생성 실패", Toast.LENGTH_LONG).show();
                }
                stopPD();
            }
        });
    }


    //이메일 비번으로 로그인
    //유효성 검사 후 로그인 할 지 안 할지 결
    public void emailSignIn(String email, String pwd){
        showPD();

        firebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ActivityOldActivity.this, "이메일 로그인 성공", Toast.LENGTH_LONG).show();
                    startSelectActivity();
                }else{
                    Toast.makeText(ActivityOldActivity.this, "이메일 로그인 실패", Toast.LENGTH_LONG).show();
                }
                stopPD();
            }
        });

        if(!isVaild()) return;


    }

    //유효성 검사
   public boolean isVaild() {
        String email = this.etUserId.getText().toString();
        String pwd = this.etUserPwd.getText().toString();
        if(TextUtils.isEmpty(email)) {
            this.etUserId.setError("이메일을 입력하세요");
            return false;
        }
        if(TextUtils.isEmpty(pwd)) {
            this.etUserPwd.setError("비밀번호를 입력하세요");
            return false;
        }
        return true;
    }

    public void emailSignUp(){
        String email = this.etUserId.getText().toString();
        String pwd = this.etUserPwd.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            getUser().sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {

                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()) {
                                                Toast.makeText(ActivityOldActivity.this, "valid email", Toast.LENGTH_LONG).show();
                                                startSelectActivity();
                                            }
                                            else Toast.makeText(ActivityOldActivity.this, "invalid email", Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }else{
                            Toast.makeText(ActivityOldActivity.this, "계정생성 실패", Toast.LENGTH_LONG).show();
                        }
                        stopPD();
                    }
                });
    }

}
