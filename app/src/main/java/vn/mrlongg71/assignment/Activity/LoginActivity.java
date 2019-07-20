package vn.mrlongg71.assignment.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import vn.mrlongg71.assignment.R;

public class LoginActivity extends AppCompatActivity {

    EditText edtUser, edtPass;
    Button btnSignIn, btnSignUp;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        anhxa();
        //eventLogin();
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
        eventRegisterNew();



    }

    private void eventRegisterNew() {


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(LoginActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_dialog_register);
                dialog.show();

            }
        });


    }

    private void eventLogin() {

    firebaseAuth = firebaseAuth.getInstance();
        final String email = edtUser.getText().toString();
        final String pass = edtPass.getText().toString();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)){
//                    Snackbar snackbar = Snackbar
//                            .make(v, "Welcome to AndroidHive", Snackbar.LENGTH_LONG);
//
//                    snackbar.show();
                    Toast.makeText(LoginActivity.this, "Thông tin bắt buộc!", Toast.LENGTH_SHORT).show();
                }else{
                    firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {



                        }
                    });
                }

            }
        });




    }

    private void anhxa() {


        edtUser = findViewById(R.id.edtUser);
        edtPass = findViewById(R.id.edtPass);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp_New);

    }

}
