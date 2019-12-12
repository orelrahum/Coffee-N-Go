package com.example.coffee_n_go;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomerActivity extends AppCompatActivity {
    DatabaseReference RootRef;
    EditText name;
    EditText phone;
    String DrinkFromList;
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
                        Drink.setText(DrinkFromList);
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
                String UserName = name.getText().toString();
                String Userphone = phone.getText().toString();
                String Orderid = RootRef.push().getKey();
                Order o = new Order(UserName, Userphone, DrinkFromList, Orderid);
                FirebaseDatabase.getInstance().getReference("Orders").child(Orderid).setValue(o);
                startActivity(new Intent(CustomerActivity.this,Pop.class));
               // FirebaseDatabase.getInstance().getReference("Orders").child(Orderid).setValue(o,complitionListener);
//                DatabaseReference.CompletionListener complitionListener = new DatabaseReference.CompletionListener() {
//                    @Override
//                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
//                        if (databaseError != null)
//
//                    }
//                }
                }
        });

    }
}
