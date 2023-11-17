package com.example.cw_project_mobile.Authenticate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cw_project_mobile.DataConnect.DatabaseConnect;
import com.example.cw_project_mobile.Hike.AddFragment;
import com.example.cw_project_mobile.Main.BottomActivity;
import com.example.cw_project_mobile.Object.Users;
import com.example.cw_project_mobile.Query.SqlQuery;
import com.example.cw_project_mobile.R;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLData;
import java.sql.Statement;
import java.util.ArrayList;

public class SignIn extends AppCompatActivity{

    private Button btnSignIn;
    private EditText username, password;
    private ArrayList<Users> lstUsers;
    private String user_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btnSignIn = findViewById(R.id.SignIn);
        lstUsers = new ArrayList<>();

        username = findViewById(R.id.usernameSignIn);
        password = findViewById(R.id.passwordSignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateLogin() == true){
                    SqlQuery sql = new SqlQuery();
                    HashPassword hashPassword = new HashPassword();
                    String hashPwd = hashPassword.hashPassword(password.getText().toString().trim());

                    lstUsers = sql.selectAllUsers(username.getText().toString().trim(), hashPwd);
                    if(lstUsers.size() > 0){
                        Toast("Login succeed");

                        for(Users i : lstUsers){
                            user_id = String.valueOf(i.getId());
                            break;
                        }

                        Intent intent = new Intent(SignIn.this, BottomActivity.class);
                        intent.putExtra("user_id", user_id);
                        intent.putParcelableArrayListExtra("lstUsers", lstUsers);
                        startActivity(intent);
                    }
                    else {
                        Toast("Username or Password is incorrectly");
                    }

                }
            }
        });
    }

    public boolean validateLogin(){
        if(username.getText().toString().matches("") && password.getText().toString().matches("")){
            Toast("Please, enter all fields");
            return false;
        }
        if(username.getText().toString().matches("")){
            Toast("Please, enter username field");
            return false;
        }
        if(password.getText().toString().matches("")){
            Toast("Please, enter password field");
            return false;
        }

        return true;
    }

    public void Toast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}