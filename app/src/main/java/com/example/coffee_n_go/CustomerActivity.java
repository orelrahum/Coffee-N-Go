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

public class CustomerActivity extends AppCompatActivity {
    DatabaseReference RootRef;
    EditText nameET;
    EditText phone;
    String DrinkFromDb;
    String[] Drinks_items;
    Product p;
    FirebaseDatabase myDataBase;
    Button Drink;
    Button TakeBtn;
    ArrayList<String>products=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        TakeBtn = findViewById(R.id.TakeBtn);
        Drink = findViewById(R.id.DrinksBtn);
        nameET = findViewById(R.id.NameFillByUser);
        phone = findViewById(R.id.PhoneFillByUser);
        myDataBase = FirebaseDatabase.getInstance();
        RootRef = myDataBase.getReference();
        Drinks_items = getResources().getStringArray(R.array.Coffie_Drinks);
        RootRef=myDataBase.getReference("Products");
        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                p = dataSnapshot.getValue(Product.class);
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                startActivity(new Intent(CustomerActivity.this,Pop.class));
            }
        });
        Drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(CustomerActivity.this);
                mBuilder.setTitle("Drinks:");
                String[] arr = new String [products.size()];
                for(int i=0;i<products.size();i++){
                    arr[i]=products.get(i);
                }
                mBuilder.setSingleChoiceItems(arr, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DrinkFromDb = products.get(i);
                        Drink.setText(DrinkFromDb);
                        dialogInterface.dismiss();
                    }
                });


                mBuilder.create();
                mBuilder.show();
            }

        });

        TakeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RootRef.child("Orders");
                String UserName = nameET.getText().toString();
                String Userphone = phone.getText().toString();
                String Orderid = RootRef.push().getKey();
                Order o = new Order(UserName, Userphone, DrinkFromDb, Orderid);
                FirebaseDatabase.getInstance().getReference("Orders").child(Orderid).setValue(o,complitionListener);
            }
        });
    }

    private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            p=(ds.getValue(Product.class));
            products.add(p.getName());
        }

    }

    DatabaseReference.CompletionListener complitionListener = new DatabaseReference.CompletionListener() {
        @Override
        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
            if(databaseError != null){
                Toast.makeText(CustomerActivity.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
            else{
                startActivity(new Intent(CustomerActivity.this,Pop.class));
            }
        }
    };
    }
