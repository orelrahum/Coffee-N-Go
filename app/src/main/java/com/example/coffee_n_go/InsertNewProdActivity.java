package com.example.coffee_n_go;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InsertNewProdActivity extends AppCompatActivity {


    DatabaseReference myRef;
    FirebaseDatabase myDb;
    EditText name;
    EditText price;
    EditText quant;
    Button insert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_new_prod);
        insert=findViewById(R.id.InsertNewBtn);
        name=findViewById(R.id.InsProdNameEt);
        price=findViewById(R.id.InsProdPriceEt);
        quant=findViewById(R.id.InsProdQuanEt);
        myDb=FirebaseDatabase.getInstance();
        myRef=myDb.getReference("Products");



        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ProductName=name.getText().toString();
                String ProductPrice=price.getText().toString();
                String ProductQuant=quant.getText().toString();
                String ProductID=myRef.push().getKey();
                Product p = new Product(ProductID,ProductName,ProductPrice,ProductQuant);
                myRef.child(ProductID).setValue(p,completionListener);
            }
        });
    }
    DatabaseReference.CompletionListener completionListener = new DatabaseReference.CompletionListener() {
        @Override
        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
           if(databaseError != null){
               Toast.makeText(InsertNewProdActivity.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
           }
           else{
                Toast.makeText(InsertNewProdActivity.this,"Saved!!",Toast.LENGTH_LONG).show();
           }
        }
    };
}