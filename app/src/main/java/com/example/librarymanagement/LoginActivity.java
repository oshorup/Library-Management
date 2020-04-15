package com.example.librarymanagement;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_email)
    EditText loginEmail;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.login_button)
    Button loginButton;
    @BindView(R.id.forgotPassword)
    TextView forgotPassword;
    @BindView(R.id.loginTosignUp)
    TextView loginTosignUp;

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        ButterKnife.bind(this);

        sharedPreferences = getSharedPreferences("LIBRARYMANAGERDATA", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);




    }


    @OnClick({R.id.login_button, R.id.forgotPassword, R.id.loginTosignUp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                progressDialog.setMessage("Please wait.....");
                progressDialog.show();

                String  email = loginEmail.getText().toString();
                String password = loginPassword.getText().toString();
                if(!email.equalsIgnoreCase(""))
                {
                 if(!password.equalsIgnoreCase(""))
                 {
                  loginUser(email,password);
                 }
                 else {
                     Toast.makeText(LoginActivity.this,"Password can't be blank",Toast.LENGTH_SHORT).show();
                 }
                }
                else {
                    Toast.makeText(LoginActivity.this,"Email can't be blank",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.forgotPassword:
                 String emaill = loginEmail.getText().toString();
                 if(!emaill.equalsIgnoreCase(""))
                 {
                     firebaseAuth.sendPasswordResetEmail(emaill).addOnCompleteListener(new OnCompleteListener<Void>() {
                         @Override
                         public void onComplete(@NonNull Task<Void> task) {
                             if(task.isSuccessful())
                             {
                                 Toast.makeText(LoginActivity.this,"Please check your email for reset link",Toast.LENGTH_SHORT).show();
                             }
                             else {
                                 Toast.makeText(LoginActivity.this,"Error in sending reset link",Toast.LENGTH_SHORT).show();

                             }
                         }
                     });
                 }
                 else {
                     Toast.makeText(LoginActivity.this,"Email can't be blank",Toast.LENGTH_SHORT).show();
                 }
                break;
            case R.id.loginTosignUp:
                Intent j = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(j);
                finish();
                break;
        }
    }

    public void loginUser(String email, String password) {

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();

                if(task.isSuccessful())
                {
                    editor.putBoolean("LoginStatus",true);
                    editor.commit();

                    Toast.makeText(LoginActivity.this,"Logged in successfully",Toast.LENGTH_SHORT).show();
                    Intent ktoMain = new Intent(LoginActivity.this,BooksAvailable_COE.class);
                    startActivity(ktoMain);
                    finish();
                }
                else {
                    editor.putBoolean("LoginStatus",false);
                    editor.commit();

                    String errormsg = task.getException().getMessage();
                    Toast.makeText(LoginActivity.this,"Either email or password is invalid"+errormsg,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
