package com.example.coffee_n_go;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateNewWorker extends AppCompatActivity {
    private EditText workerEmail;
    private EditText workerPassword;
    private EditText workerName;
    private EditText workerPhone;
    private Button login;
    private FirebaseAuth fAuth;
    private String permissions="WORKER";
    private DatabaseReference myRef;
    private FirebaseDatabase myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_worker);

        workerEmail = findViewById(R.id.emailNewEt);
        workerPassword = findViewById(R.id.passwordNewEt);
        workerName = findViewById(R.id.NameNewEt);
        workerPhone = findViewById(R.id.phoneNewEt);
        login = findViewById(R.id.newWorkerBtn);
        fAuth = FirebaseAuth.getInstance();
        myDb = FirebaseDatabase.getInstance();
        myRef = myDb.getReference("Users");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = workerEmail.getText().toString().trim();
                final String password = workerPassword.getText().toString().trim();
                final String name = workerName.getText().toString().trim();
                final String phone = workerPhone.getText().toString().trim();

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
                if(TextUtils.isEmpty(name)){
                    workerName.setError("Name is Required");
                    return;
                }
                if(TextUtils.isEmpty(phone)){
                    workerPhone.setError("Phone is Required");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(CreateNewWorker.this,"Error ! "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        else{
                            FirebaseUser newWorker = FirebaseAuth.getInstance().getCurrentUser();
                            User newUser = new User(name,email,phone,newWorker.getUid(),permissions);
                            myRef.push().setValue(newUser, completionListener);
                            workerEmail.setText("");
                            workerPassword.setText("");
                            workerName.setText("");
                            workerPhone.setText("");
                        }
                    }
                });
            }
        });
    }
    DatabaseReference.CompletionListener completionListener = new DatabaseReference.CompletionListener() {
        @Override
        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
            if(databaseError != null){
                Toast.makeText(CreateNewWorker.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(CreateNewWorker.this,"Saved!!",Toast.LENGTH_LONG).show();
            }
        }
    };
}
