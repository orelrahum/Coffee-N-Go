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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class UpdateProductDetails extends AppCompatActivity {
    DatabaseReference myRef;
    FirebaseDatabase myDb;
    Button choose;
    Button update;
    Product p;
    Product temp;
    EditText name;
    EditText price;
    EditText quantity;
    TextView aboutEnd;
    String Pname;
    double Pprice;
    int Pquant;
    ArrayList<Product> products = new ArrayList<>();
    String prodId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product_details);
        aboutEnd=findViewById(R.id.aboutToEndET);
//        try {
            Intent intent =getIntent();
            String s=intent.getStringExtra("extra");
            aboutEnd.setText(s);
//        }catch (Exception e){}
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
                products.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    p = (ds.getValue(Product.class));
                    products.add(p);
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
                    arr[i]=products.get(i).getName();
                }
                builder.setSingleChoiceItems(arr, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Pname=products.get(i).getName();
                        prodId=products.get(i).getId();
                        choose.setText(Pname);
                        myRef.getDatabase().getReference("Products");
                        Query updateValues=myRef.child(prodId);
                        updateValues.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                p=dataSnapshot.getValue(Product.class);
                                Pname=p.getName();
                                Pprice=p.getPrice();
                                Pquant=p.getStocks();
                                temp=new Product(prodId,Pname,Pprice,Pquant);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
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
                if(newName.equals(""))newName=temp.getName();
                if(newPrice.equals(""))newPrice=temp.getPrice()+"";
                if(newQant.equals(""))newQant=temp.getStocks()+"";
                p=new Product(prodId,newName,Double.parseDouble(newPrice),Integer.parseInt(newQant));
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
                Pname="";
                choose.setText(Pname);
            }
        }
    };
}
