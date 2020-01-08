package com.example.coffee_n_go;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WorkerLogin extends AppCompatActivity {

    private EditText mEmailField;
    private EditText mPasswordField;
    private Button mLoginBtn;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private String dest;
    private FirebaseDatabase myDataBase;
    private DatabaseReference RootRef;
    private ArrayList<User> users = new ArrayList<>();
    private String Uid;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_login);

        mEmailField = findViewById(R.id.emailField);
        mPasswordField = findViewById(R.id.passwordField);
        mLoginBtn = findViewById(R.id.CustomerLoginBtn);
        Intent intent = getIntent();
        dest = intent.getStringExtra("dest");
        Toast.makeText(WorkerLogin.this, dest, Toast.LENGTH_LONG).show();
        myDataBase = FirebaseDatabase.getInstance();
        RootRef = myDataBase.getReference("Users");
        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    user = ds.getValue(User.class);
                    users.add(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        mAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    if (dest.equals("worker")) {
                        Intent intent = new Intent(WorkerLogin.this, WorkerActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else if(dest.equals("ceo")) {
                        mLoginBtn.setText(users.toString());
                        for (User user : users) {
                            if (user.getAuthID().equals(Uid)) {
                                if (user.getPermissions().equals("MANAGER")) {
                                    Intent intent = new Intent(WorkerLogin.this, CeoActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(WorkerLogin.this, "You don't have permissions!!", Toast.LENGTH_LONG).show();
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        };
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignIn();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }
    private void startSignIn() {
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(WorkerLogin.this, "Fields are empty.", Toast.LENGTH_LONG).show();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(WorkerLogin.this, "Sign In Problem", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}