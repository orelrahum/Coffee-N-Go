package com.example.coffee_n_go;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;


public class Login extends AppCompatActivity {
    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private int counter=5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Name = findViewById(R.id.etName);
        Password = findViewById(R.id.etPassword);
        Info = findViewById(R.id.tvInfo);
        Login = findViewById(R.id.btnLogin);
        Info.setText("Nomber of attempts remaining: 5");
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Name.getText().toString(),Password.getText().toString());
            }
        });
    }

    private void validate(String userName, String userPassword){
        if(TextUtils.isEmpty(userName)){
            Name.setError("You must enter name!");
            return;
        }
        if(TextUtils.isEmpty(userPassword)){
            Password.setError("You must enter Password!");
            return;
        }
        if(userName.equals("Admin") && userPassword.equals("1234")){
            Intent intent = new Intent(this, CeoActivity.class);
            startActivity(intent);
        }
        else{
            counter--;
            Info.setText("Nomber of attempts remaining: "+ counter);
            if(counter==0){
                Login.setEnabled(false);
            }
        }
    }
}
