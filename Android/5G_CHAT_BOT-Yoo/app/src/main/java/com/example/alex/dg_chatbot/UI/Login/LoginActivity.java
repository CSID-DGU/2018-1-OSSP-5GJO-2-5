package com.example.alex.dg_chatbot.UI.Login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.alex.dg_chatbot.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends RootActivity {

    private FrameLayout flAnonyLogin;
    private FrameLayout flEmailSignUp;
    private FrameLayout flEmailLogin;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        flAnonyLogin = findViewById(R.id.flAnonyLogin);
        flEmailSignUp = findViewById(R.id.flEmailSignUp);
        flEmailLogin = findViewById(R.id.flEmailLogin);

        firebaseAuth = FirebaseAuth.getInstance();

        flEmailSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        flAnonyLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                anonymousSignUp();
            }
        });

        flEmailLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
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

                    Toast.makeText(LoginActivity.this, "익명 계정 생성 성공", Toast.LENGTH_LONG).show();
                    startSelectActivity();
                }else{
                    Toast.makeText(LoginActivity.this, "익명 계정 생성 실패", Toast.LENGTH_LONG).show();
                }
                stopPD();
            }
        });
    }

    public void startSelectActivity(){
        Intent intent = new Intent(LoginActivity.this, SelectFavoriteActivity.class);
        startActivity(intent);
    }
}
