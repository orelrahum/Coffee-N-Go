package com.example.coffee_n_go;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UpdateProductDetails extends AppCompatActivity {
    DatabaseReference myRef;
    FirebaseDatabase myDb;
    Button choose;
    Button update;
    Product p;
    EditText name;
    EditText price;
    EditText quantity;
    String prod;
    ArrayList<String> products = new ArrayList<>();
    ArrayList<String>productsId=new ArrayList<>();
    String prodId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product_details);

        choose = findViewById(R.id.ChooseUpdateBtn);
        update = findViewById(R.id.UpdateBtn);
        name = findViewById(R.id.UpdateNameEt);
        price = findViewById(R.id.UpdatePriceEt);
        quantity = findViewById(R.id.UpdateQuantEt);
        myDb = FirebaseDatabase.getInstance();
        myRef = myDb.getReference("Products");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    p = (ds.getValue(Product.class));
                    productsId.add(p.getId());
                    products.add(p.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProductDetails.this);
                builder.setTitle("Products:");
                String[]arr=new String[products.size()];
                for(int i=0;i<arr.length;i++){
                    arr[i]=products.get(i);
                }
                builder.setSingleChoiceItems(arr, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        prod=products.get(i);
                        prodId=productsId.get(i);
                        choose.setText(prod);
                    }
                });
                builder.show();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.child("Orders");
                String newName=name.getText().toString();
                String newPrice=price.getText().toString();
                String newQant=quantity.getText().toString();
                p=new Product(prodId,newName,newPrice,newQant);
                FirebaseDatabase.getInstance().getReference("Products").child(prodId).setValue(p, complitionListener);
            }
        });
    }
    DatabaseReference.CompletionListener complitionListener = new DatabaseReference.CompletionListener() {
        @Override
        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
            if (databaseError != null) {
                Toast.makeText(UpdateProductDetails.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(UpdateProductDetails.this,"Updated!!",Toast.LENGTH_LONG).show();
            }
        }
    };
}