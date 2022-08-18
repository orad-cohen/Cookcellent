package com.project.cookcellent;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;



import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import android.widget.EditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignupActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    EditText Email;
    EditText Password;
    EditText Name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        Email =findViewById(R.id.eSignEmail);
        Password =findViewById(R.id.eSignPassword);
        Name =findViewById(R.id.eSignPassword);


    }


    public void Submit(View view) {

        //get sign up details from view
        String email = ((EditText) findViewById(R.id.eSignEmail)).getText().toString();
        String password = ((EditText) findViewById(R.id.eSignPassword)).getText().toString();
        String name = ((EditText) findViewById(R.id.eSignName)).getText().toString();
        //sign up using firebase
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {

                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser User = mAuth.getCurrentUser();
                        Intent intent = new Intent(SignupActivity.this,SearchActivity.class);
                        this.startActivity(intent);
                    }
                });
    }}
