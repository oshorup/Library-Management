package com.example.librarymanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import DATA.UserInformation;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.signup_username)
    EditText signupUsername;
    @BindView(R.id.signup_email)
    EditText signupEmail;
    @BindView(R.id.signup_password)
    EditText signupPassword;
    @BindView(R.id.signup_button)
    Button signupButton;

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    DatabaseReference databaseReference;
    @BindView(R.id.signup_college_IDCARD_number)
    EditText CollegeIDCARDNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_screen);
        ButterKnife.bind(this);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        databaseReference = FirebaseDatabase.getInstance().getReference("USERS");


    }


    @OnClick(R.id.signup_button)
    public void onViewClicked() {

        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        String name = signupUsername.getText().toString();
        String email = signupEmail.getText().toString();
        String password = signupPassword.getText().toString();
        String  collegeId = CollegeIDCARDNumber.getText().toString();

        if (!name.equalsIgnoreCase("")) {
            if (!email.equalsIgnoreCase("")) {
                if (!password.equalsIgnoreCase("")) {
                    if(!collegeId.equalsIgnoreCase(""))
                    {
                        registerUser(name, email, password,collegeId);
                    }
                    else {
                        Toast.makeText(SignUpActivity.this, "College ID card number can't be blank", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(SignUpActivity.this, "Password can't be blank", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(SignUpActivity.this, "Email can't be blank", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(SignUpActivity.this, "Name can't be blank", Toast.LENGTH_SHORT).show();
        }
    }

    private void registerUser(String name, String email, String password,String collegeID) {


        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {


                    FirebaseUser curruser= firebaseAuth.getCurrentUser();
                    String uid = curruser.getUid();

                    UserInformation information = new UserInformation(name, email,collegeID );
                    databaseReference.child(uid).setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this, "User registered", Toast.LENGTH_SHORT).show();
                                Intent ktoLogin = new Intent(SignUpActivity.this, LoginActivity.class);
                                startActivity(ktoLogin);
                                finish();
                            }


                        }
                    });
                } else {
                    Toast.makeText(SignUpActivity.this, "not registered"+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
