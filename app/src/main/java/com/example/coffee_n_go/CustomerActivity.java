package com.example.coffee_n_go;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerActivity extends AppCompatActivity {
    DatabaseReference RootRef;
    EditText name;
    EditText phone;
    static String DrinkFromList;
    String[] Drinks_items;
    FirebaseDatabase myDataBase;
    Button Drink;
    Button TakeBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        TakeBtn = findViewById(R.id.TakeBtn);
        Drink = findViewById(R.id.DrinksBtn);
        name = findViewById(R.id.NameFillByUser);
        phone = findViewById(R.id.PhoneFillByUser);
        myDataBase = FirebaseDatabase.getInstance();
        RootRef = myDataBase.getReference("Orders");

        Drinks_items = getResources().getStringArray(R.array.Coffie_Drinks);
        Drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(CustomerActivity.this);
                mBuilder.setTitle("Drinks:");

                mBuilder.setSingleChoiceItems(Drinks_items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DrinkFromList = Drinks_items[i];
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

                RootRef.child("Orders")
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                String UserName = name.getText().toString().trim();
                                String Userphone = phone.getText().toString().trim();
                                String Orderid = RootRef.push().getKey();
                                Order o = new Order(UserName, Userphone, DrinkFromList, Orderid);
                                FirebaseDatabase.getInstance().getReference("Orders").child(Orderid).setValue(o);
                                RootRef.push().setValue(Orderid);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }

                        });

            }


        });


    }
}
