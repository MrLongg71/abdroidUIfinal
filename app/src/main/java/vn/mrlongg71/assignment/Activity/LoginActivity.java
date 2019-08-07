package vn.mrlongg71.assignment.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

import vn.mrlongg71.assignment.Model.User;
import vn.mrlongg71.assignment.R;
import vn.mrlongg71.assignment.View.CustomToast;
import vn.mrlongg71.assignment.View.Show_Hide_Dialog;

public class LoginActivity extends AppCompatActivity {

    EditText edtUser, edtPass;
    Button btnSignIn, btnSignUp;
    FirebaseAuth firebaseAuth;
    DatabaseReference mDatabase;
    ProgressDialog progressDialog;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        anhxa();
        eventLogin();

        eventRegisterNew();


    }

    private void eventRegisterNew() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(LoginActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_dialog_register);
                RegisterUser(dialog);
                dialog.show();
            }
        });


    }

    private void RegisterUser(final Dialog dialog) {
        final EditText edtUserNew, edtPassNew, edtEmailNew;
        Button btnSignUp;
        edtUserNew = dialog.findViewById(R.id.edtUserNew);
        edtPassNew = dialog.findViewById(R.id.edtPassNew);
        edtEmailNew = dialog.findViewById(R.id.edtEmailNew);
        btnSignUp = dialog.findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user = edtUserNew.getText().toString();
                final String email = edtEmailNew.getText().toString();
                final String pass = edtPassNew.getText().toString();
                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
                    Toast.makeText(LoginActivity.this, "Thông tin bắt buộc!", Toast.LENGTH_SHORT).show();
                } else if (pass.length() < 6) {
                    Toast.makeText(LoginActivity.this, "Mật khẩu tối đa 6 kí tự!", Toast.LENGTH_SHORT).show();
                } else if (!checkemail(email)) {
                    Toast.makeText(LoginActivity.this, "Email không đúng định dạng!", Toast.LENGTH_SHORT).show();

                } else {
                    Show_Hide_Dialog.showProgressDialogWithTitle("Vui lòng chờ...", progressDialog);
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                userID = firebaseAuth.getCurrentUser().getUid();
                                User users = new User(userID, user, email, "");
                                mDatabase.child("users").child(userID).setValue(users);
                                Show_Hide_Dialog.hideProgressDialogWithTitle(progressDialog);
                                CustomToast.makeText(getApplicationContext(), "Đăng ký thành công!", CustomToast.SUCCESS,CustomToast.LENGTH_LONG, false).show();
                                dialog.dismiss();
                            } else {
                                Show_Hide_Dialog.hideProgressDialogWithTitle(progressDialog);
                                CustomToast.makeText(getApplicationContext(), "Đăng ký thất bại: " + task.getException(), CustomToast.ERROR,CustomToast.LENGTH_LONG, false).show();
                            }
                        }
                    });
                }

            }
        });


    }

    private void eventLogin() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtUser.getText().toString();
                String pass = edtPass.getText().toString();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
                    CustomToast.makeText(getApplicationContext(), "Thông tin bắt buộc!", CustomToast.LENGTH_LONG, CustomToast.ERROR,false).show();
                } else if (!checkemail(email)) {
                    CustomToast.makeText(getApplicationContext(), "Email không đúng định dạng!", CustomToast.LENGTH_LONG, CustomToast.ERROR,false).show();
                } else {
                    Show_Hide_Dialog.showProgressDialogWithTitle("Vui lòng chờ hihi...", progressDialog);
                    firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            String userID = firebaseAuth.getCurrentUser().getUid();
                            if (task.isSuccessful()) {
                                Show_Hide_Dialog.hideProgressDialogWithTitle(progressDialog);

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("userID", userID); //userID
                                CustomToast.makeText(getApplicationContext(), "Đăng nhập thành công! Xin chào "  , CustomToast.LENGTH_LONG, CustomToast.SUCCESS,false).show();
                                startActivity(intent);
                                finish();
                            } else {
                                Show_Hide_Dialog.hideProgressDialogWithTitle(progressDialog);
                                CustomToast.makeText(getApplicationContext(), "Sai tài khoản hoặc mật khẩu!", CustomToast.LENGTH_LONG, CustomToast.ERROR,false).show();
                            }
                        }
                    });
                }
            }
        });


    }
    //check email
    private boolean checkemail(String email) {
        Pattern Email = Pattern.compile("[a-zA-Z0-9+._%-+]{1,256}" +
                "@" +
                "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                "(" +
                "." +
                "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                ")+");
        return Email.matcher(email).matches();
    }


    private void anhxa() {
        edtUser = findViewById(R.id.edtUser);
        edtPass = findViewById(R.id.edtPass);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp_New);
        firebaseAuth = firebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        edtUser.setText("abc@gmail.com");
        edtPass.setText("123456");
    }

}
