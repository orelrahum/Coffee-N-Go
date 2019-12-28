package com.example.coffee_n_go;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CustomerOptions extends AppCompatActivity {
    Button seat;
    Button take;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_options);

        seat = findViewById(R.id.SeatOptionBtn);
        take = findViewById(R.id.TakeOptionBtn);
        take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(CustomerOptions.this,CustomerActivity.class);
                intent.putExtra("TakeAway",true);
                startActivity(intent);
            }
        });
        seat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(CustomerOptions.this,CustomerActivity.class);
                intent.putExtra("TakeAway",false);
                startActivity(intent);
            }
        });
    }
}
