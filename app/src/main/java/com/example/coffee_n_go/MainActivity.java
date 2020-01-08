package com.example.coffee_n_go;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.*;


public class MainActivity extends AppCompatActivity {
    private Button button_worker;
    private Button button_CEO;
    private Button button_customer;
    public Map<String, Object> user = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_worker = (Button) findViewById(R.id.worker);
        button_worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, WorkerLogin.class);
                i.putExtra("dest","worker");
                startActivity(i);
            }
        });
        button_customer= (Button) findViewById(R.id.customer);
        button_CEO= (Button) findViewById(R.id.CEO);
        button_CEO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,WorkerLogin.class);
                intent.putExtra("dest","ceo");
                startActivity(intent);
            }
        });


        button_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CustomerOptions.class);
                startActivity(i);
            }
        });

    }
}
