package com.example.coffee_n_go;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateNewWorker extends AppCompatActivity {
    private EditText workerEmail;
    private EditText workerPassword;
    private Button login;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_worker);

        workerEmail = findViewById(R.id.emailNewEt);
        workerPassword = findViewById(R.id.passwordNewEt);
        login = findViewById(R.id.newWorkerBtn);
        fAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = workerEmail.getText().toString().trim();
                String password = workerPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    workerEmail.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    workerPassword.setError("Password is Required");
                    return;
                }
                if(password.length() < 6){
                    workerPassword.setError("Password Must be >= 6 Characters");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(CreateNewWorker.this,"Error ! "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(CreateNewWorker.this,"User Created",Toast.LENGTH_SHORT).show();
                            workerEmail.setText("");
                            workerPassword.setText("");

                        }
                    }
                });
            }
        });
    }
}
