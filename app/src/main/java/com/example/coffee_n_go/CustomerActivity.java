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
import com.google.firebase.database.Query;
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
    Button book;
    List<String> products = new ArrayList<>();
    List<String>productsId=new ArrayList<>();
    ArrayList<String> prodOrder = new ArrayList<>();
    ArrayList<String> prodOrderId = new ArrayList<>();
    List<String> productsWithPrice = new ArrayList<>();
    List<String> prices = new ArrayList<>();
    double sum=0;
    boolean arrIsChecked[];
    boolean TakeAway=false;
    double diff=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        book = findViewById(R.id.BookBtn);
        Drink = findViewById(R.id.DrinksBtn);
        nameET = findViewById(R.id.NameFillByUser);
        phone = findViewById(R.id.PhoneFillByUser);
        myDataBase = FirebaseDatabase.getInstance();
        RootRef = myDataBase.getReference();
        RootRef = myDataBase.getReference("Products");
        Intent intent =getIntent();
        TakeAway=intent.getBooleanExtra("TakeAway",false);
        if(!TakeAway)
            diff=2;
        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                products.clear();
                productsId.clear();
                prices.clear();
                productsWithPrice.clear();
                p = dataSnapshot.getValue(Product.class);
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    p = (ds.getValue(Product.class));
                    int stock = Integer.parseInt(p.getStocks());
                    if (stock > 0) {
                        products.add(p.getName());
                        productsId.add(p.getId());
                        productsWithPrice.add(p.getName()+"\t"+p.getPrice());
                        prices.add(p.getPrice()+"");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        Drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(CustomerActivity.this);
                mBuilder.setTitle("Menu:");
                arrIsChecked = new boolean[productsWithPrice.size()];
                String[] arr = new String[productsWithPrice.size()];
                for (int i = 0; i < productsWithPrice.size(); i++) {
                    arr[i] = productsWithPrice.get(i);
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
                                        prodOrderId.add(productsId.get(j));
                                        sum+=Double.parseDouble(prices.get(j));
                                    }
                                } else {
                                    if (prodOrder.contains(products.get(j))) {
                                        prodOrder.remove(products.get(j));
                                        prodOrderId.remove(productsId.get(j));
                                        sum-=Double.parseDouble(prices.get(j));
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
                        prodOrderId.clear();
                        Drink.setText(prodOrder.toString());
                        sum=0;
                        productsWithPrice.clear();
                        prices.clear();
                    }
                });
                mBuilder.show();
            }
        });
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RootRef.child("Orders");
                String UserName = nameET.getText().toString();
                String Userphone = phone.getText().toString();
                String Orderid = RootRef.push().getKey();
                Order o = new Order(UserName, Userphone,TakeAway, prodOrder, Orderid,sum);
                FirebaseDatabase.getInstance().getReference("Orders").child(Orderid).setValue(o, complitionListener);
            }
        });
        Drink.setText(prodOrder.toString());
    }

    DatabaseReference.CompletionListener complitionListener = new DatabaseReference.CompletionListener() {
        @Override
        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
            if (databaseError != null) {
                Toast.makeText(CustomerActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
            } else {
                for(String str : prodOrderId){
                    RootRef.getDatabase().getReference("Products");
                    Query updateQuantity=RootRef.child(str);
                    updateQuantity.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            p=dataSnapshot.getValue(Product.class);
                            String Pname=p.getName();
                            String Pid=p.getId();
                            String Pquant="" +(Integer.parseInt(p.getStocks())-1);
                            String Pprice=p.getPrice()+"";
                            p=new Product(Pid,Pname,Pprice,Pquant);
                            RootRef.child(Pid).setValue(p);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                Intent intent =new Intent(CustomerActivity.this, Pop.class);
                intent.putExtra("sum",sum);
                startActivity(intent);
                prodOrderId.clear();
                prodOrder.clear();
                products.clear();
                productsId.clear();
                Drink.setText(prodOrder.toString());
                sum=0;
            }
        }
    };
}
