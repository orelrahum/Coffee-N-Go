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
import java.util.List;

public class CustomerActivity extends AppCompatActivity {
    DatabaseReference RootRef;
    EditText nameET;
    EditText phone;
    Product p;
    FirebaseDatabase myDataBase;
    Button Drink;
    Button TakeBtn;
    List<String> products = new ArrayList<>();
    ArrayList<String> prodOrder = new ArrayList<>();
    boolean arrIsChecked[];

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
        RootRef = myDataBase.getReference("Products");
        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                p = dataSnapshot.getValue(Product.class);
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        Drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(CustomerActivity.this);
                mBuilder.setTitle("Drinks:");
                arrIsChecked = new boolean[products.size()];
                String[] arr = new String[products.size()];
                for (int i = 0; i < products.size(); i++) {
                    arr[i] = products.get(i);
                }
                mBuilder.setMultiChoiceItems(arr, arrIsChecked, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which, boolean isChecked) {
                        arrIsChecked[which] = isChecked;
                        String currentItem = products.get(which);
                        Toast.makeText(CustomerActivity.this, currentItem + " " + isChecked, Toast.LENGTH_SHORT).show();
                    }
                });
                mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (int j = 0; j < arrIsChecked.length; j++) {
                            {
                                if (arrIsChecked[j]) {
                                    if (!prodOrder.contains(products.get(j))) {
                                        prodOrder.add(products.get(j));
                                    }
                                } else {
                                    if (prodOrder.contains(products.get(j))) {
                                        prodOrder.remove(products.get(j));
                                    }
                                }
                            }
                        }
                        Drink.setText(prodOrder.toString());
                    }
                });
                mBuilder.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (boolean bol : arrIsChecked) bol = false;
                        prodOrder.clear();
                        Drink.setText(prodOrder.toString());
                    }
                });
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
                Order o = new Order(UserName, Userphone, prodOrder, Orderid);
                FirebaseDatabase.getInstance().getReference("Orders").child(Orderid).setValue(o, complitionListener);
            }
        });
        Drink.setText(prodOrder.toString());
    }

    private void showData(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            p = (ds.getValue(Product.class));
            int stock = Integer.parseInt(p.getStocks());
            if (stock > 0)
                products.add(p.getName());
        }

    }

    DatabaseReference.CompletionListener complitionListener = new DatabaseReference.CompletionListener() {
        @Override
        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
            if (databaseError != null) {
                Toast.makeText(CustomerActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
            } else {
                startActivity(new Intent(CustomerActivity.this, Pop.class));
            }
        }
    };
}
