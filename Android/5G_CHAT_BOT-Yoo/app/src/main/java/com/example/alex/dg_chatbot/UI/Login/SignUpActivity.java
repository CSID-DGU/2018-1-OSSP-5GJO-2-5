package com.example.alex.dg_chatbot.UI.Login;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.alex.dg_chatbot.R;
import com.example.alex.dg_chatbot.Util.U;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends RootActivity {

    private EditText etUserNewID;
    private EditText etUserNewPwd;
    private EditText etUserNewPwdCheck;
    private CheckBox cbAgree;
    private LinearLayout llBack;
    private LinearLayout llSignUp;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener != null){
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    //sign in
                }else{
                    //sign out
                }
            }
        };

        firebaseAuth = FirebaseAuth.getInstance();
        etUserNewID = findViewById(R.id.etUserNewID);
        etUserNewPwd = findViewById(R.id.etUserNewPwd);
        etUserNewPwdCheck = findViewById(R.id.etUserNewPwdCheck);
        cbAgree = findViewById(R.id.cbAgree);
        llBack = findViewById(R.id.llBack);
        llSignUp = findViewById(R.id.llSignUp);

        llSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cbAgree.isChecked() && isPwdEquals()){
                    emailSignUp();
                }else if(!cbAgree.isChecked()){
                    Toast.makeText(SignUpActivity.this, "약관에 동의해주세요.", Toast.LENGTH_LONG).show();
                    return;
                }else if(!isPwdEquals()){
                    U.getInstance().showErrorPopup(SignUpActivity.this, "비밀번호 확인", "비밀번호가 일치하지 않습니다");
                }
            }
        });

        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public boolean isPwdEquals(){
        if(etUserNewPwd.getText().toString()
                .equals(etUserNewPwdCheck.getText().toString()))
            return true;
        else return false;
    }

    public void emailSignUp(){
        String email = this.etUserNewID.getText().toString();
        String pwd = this.etUserNewPwd.getText().toString();
        showPD();
        firebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.i("signup","success? : " + task.isSuccessful());

                if(!task.isSuccessful())
                    Toast.makeText(SignUpActivity.this, "failed", Toast.LENGTH_LONG).show();
                else {
                    Toast.makeText(SignUpActivity.this, "success", Toast.LENGTH_LONG).show();
                    finish();
                }


                stopPD();
            }

        });
    }
}
