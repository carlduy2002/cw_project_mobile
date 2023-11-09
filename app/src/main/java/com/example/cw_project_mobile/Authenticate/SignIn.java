package com.example.cw_project_mobile.Authenticate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cw_project_mobile.Main.BottomActivity;
import com.example.cw_project_mobile.R;

public class SignIn extends AppCompatActivity {

    private Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btnSignIn = findViewById(R.id.SignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, BottomActivity.class);
                startActivity(intent);
            }
        });
    }
}