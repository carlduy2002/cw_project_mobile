package com.example.cw_project_mobile.Authenticate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cw_project_mobile.Object.Users;
import com.example.cw_project_mobile.Query.SqlQuery;
import com.example.cw_project_mobile.R;

import java.util.ArrayList;

public class SignUp extends AppCompatActivity {

    private Button btnSignUp;
    private EditText username, email, password, confirmPassword, address;
    private String u_name = "", u_email = "", u_password = "", u_confrimPasword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnSignUp = findViewById(R.id.SignUp);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        address = findViewById(R.id.address);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateUsers() == true){
                    SqlQuery sql = new SqlQuery();
                    HashPassword hashPassword = new HashPassword();
                    String hashPwd = hashPassword.hashPassword(u_password);

                    //get current max id
                    int maxID = sql.selectUserMaxID();

                    //insert user
                    sql.insertUser(u_name, u_email, hashPwd, address.getText().toString());

                    //get new max id
                    int newID = sql.selectUserMaxID();

                    if(newID > maxID){
                        Toast("Sign Up succeed");

                        Intent intent = new Intent(SignUp.this, SignIn.class);
                        startActivity(intent);
                    }
                    else {
                        Toast("Sign Up failed");
                    }
                }
            }
        });
    }

    public boolean validateUsers(){
        u_name = username.getText().toString();
        u_email = email.getText().toString();
        u_password = password.getText().toString();
        u_confrimPasword = confirmPassword.getText().toString();
        String regexEmail = "^([a-zA-z0-9]+@gmail+\\.[a-zA-Z]{2,})$";
        String existUsername = "";
        String existEmail = "";

        SqlQuery sql = new SqlQuery();

        ArrayList<Users> usersInfor;
        usersInfor = sql.selectUserInfor();

        for (Users i : usersInfor){
            existUsername = i.getUsername();
            existEmail = i.getEmail();
            break;
        }

        if(u_name.matches("") && u_email.matches("") && u_password.matches("") && u_confrimPasword.matches("")){
            Toast("Please, enter all fields");
            return false;
        }
        if(u_name.matches("")){
            Toast("Please, enter username field");
            return false;
        }
        if(u_name.matches(existUsername)){
            Toast("Username already exist");
            return false;
        }
        if(u_email.matches("")){
            Toast("Please, enter email field");
            return false;
        }
        if(!u_email.matches(regexEmail)){
            Toast("Email already invalid");
            return false;
        }
        if(u_email.matches(existEmail)){
            Toast("Email is exist");
            return false;
        }
        if(u_password.matches("")){
            Toast("Please, enter password field");
            return false;
        }
        if(u_password.length() < 8){
            Toast("Please, enter password more than 8 characters");
            return false;
        }
        if(u_confrimPasword.matches("")){
            Toast("Please, enter confirm password field");
            return false;
        }
        if(!u_confrimPasword.equals(u_password)){
            Toast("Password and Confirm Password id not match");
            return false;
        }

        return true;
    }

    public void Toast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}