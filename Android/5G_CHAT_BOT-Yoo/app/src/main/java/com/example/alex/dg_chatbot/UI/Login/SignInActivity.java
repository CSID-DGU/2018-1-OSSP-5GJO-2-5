package com.example.alex.dg_chatbot.UI.Login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.alex.dg_chatbot.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignInActivity extends RootActivity {
    private EditText etUserId;
    private EditText etUserPwd;
    private LinearLayout llSignIn;
    private LinearLayout llBack;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        etUserId = findViewById(R.id.etUserId);
        etUserPwd = findViewById(R.id.etUserPwd);
        llSignIn = findViewById(R.id.llSignIn);
        llBack = findViewById(R.id.llBack);

        firebaseAuth = FirebaseAuth.getInstance();

        llSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailSignIn(etUserId.getText().toString(), etUserPwd.getText().toString());
            }
        });

        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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

    //이메일 비번으로 로그인
    //유효성 검사 후 로그인 할 지 안 할지 결
    public void emailSignIn(String email, String pwd){
        showPD();

        firebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignInActivity.this, "이메일 로그인 성공", Toast.LENGTH_LONG).show();
                    startSelectActivity();
                }else{
                    Toast.makeText(SignInActivity.this, "이메일 로그인 실패", Toast.LENGTH_LONG).show();
                }
                stopPD();
            }
        });

        if(!isVaild()) return;


    }
    public void startSelectActivity(){
        Intent intent = new Intent(this, SelectFavoriteActivity.class);
        startActivity(intent);
    }

}
