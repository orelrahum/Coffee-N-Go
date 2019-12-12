package com.example.coffee_n_go;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;

public class CeoActivity extends AppCompatActivity {
    Button chnagePricesBtn;
    Button newProdBtn;
    Button removeProdBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ceo);
        chnagePricesBtn=findViewById(R.id.changePricesBtn);
        newProdBtn=findViewById(R.id.insertProductsBtn);
        removeProdBtn=findViewById(R.id.RemoveProductBtn);




        newProdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CeoActivity.this, InsertNewProdActivity.class);
                startActivity(i);
            }
        });
    }
}
