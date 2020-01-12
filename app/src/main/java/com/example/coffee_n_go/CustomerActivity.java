package com.example.coffee_n_go;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
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

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CustomerActivity extends AppCompatActivity {
    DatabaseReference RootRef;
    EditText nameET;
    EditText phone;
    Product p;
    FirebaseDatabase myDataBase;
    Button Drink;
    Button Food;
    Button Snack;
    Button book;
    List<Product> products = new ArrayList<>();
    ArrayList<Product> productsOrder = new ArrayList<>();
    ArrayList<Product>drinksOrder=new ArrayList<>();
    ArrayList<Product>foodsOrder=new ArrayList<>();
    ArrayList<Product>snacksOrder=new ArrayList<>();
    double sum = 0;
    boolean arrIsChecked[];
    boolean TakeAway = false;
    double diff = 0;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        book = findViewById(R.id.BookBtn);
        Drink = findViewById(R.id.DrinksBtn);
        Snack = findViewById(R.id.snacksBtn);
        Food = findViewById(R.id.foodsBtn);
        nameET = findViewById(R.id.NameFillByUser);
        phone = findViewById(R.id.PhoneFillByUser);
        myDataBase = FirebaseDatabase.getInstance();
        RootRef = myDataBase.getReference();
        RootRef = myDataBase.getReference("Products");
        Intent intent = getIntent();
        TakeAway = intent.getBooleanExtra("TakeAway", false);
        if (!TakeAway)
            diff = 2;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                products.clear();
                p = dataSnapshot.getValue(Product.class);
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    p = (ds.getValue(Product.class));
                    int stock = p.getStocks();
                    if (stock > 0) {
                        products.add(p);
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
                mBuilder.setTitle("Drinks:");
                final ArrayList<Product>drinks=new ArrayList<>();
                for(Product product : products){
                    if(product.getType().equals(Product.types.Drinks))
                        drinks.add(product);
                }
                arrIsChecked = new boolean[drinks.size()];
                String[] arr = new String[drinks.size()];
                for (int i = 0; i < drinks.size(); i++) {
                    arr[i] = drinks.get(i).getName() + "\t" + (drinks.get(i).getPrice() + diff);
                }
                mBuilder.setMultiChoiceItems(arr, arrIsChecked, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which, boolean isChecked) {
                        arrIsChecked[which] = isChecked;
                        String currentItem = drinks.get(which).getName();
                        Toast.makeText(CustomerActivity.this, currentItem + " " + isChecked, Toast.LENGTH_SHORT).show();
                    }
                });
                mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (int j = 0; j < arrIsChecked.length; j++) {
                            {
                                if (arrIsChecked[j]) {
                                    if (!productsOrder.contains(drinks.get(j))) {
                                        productsOrder.add(drinks.get(j));
                                        drinksOrder.add(drinks.get(j));
                                        sum += drinks.get(j).getPrice() + diff;
                                    }
                                } else {
                                    if (productsOrder.contains(drinks.get(j))) {
                                        productsOrder.remove(drinks.get(j));
                                        drinksOrder.remove(drinks.get(j));
                                        sum -= drinks.get(j).getPrice() + diff;
                                    }
                                }
                            }
                        }
                        Drink.setText(drinksOrder.toString());
                    }
                });
                mBuilder.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (boolean bol : arrIsChecked) bol = false;
                        productsOrder.removeAll(drinksOrder);
                        drinksOrder.clear();
                        Drink.setText("Choose");
                        sum = 0;
                    }
                });
                AlertDialog dialog = mBuilder.create();
                dialog.show();
                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.parseColor("#000000"));
                dialog.getButton(DialogInterface.BUTTON_NEUTRAL).setTextColor(Color.parseColor("#000000"));

            }
        });
        Food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(CustomerActivity.this);
                mBuilder.setTitle("Foods:");
                final ArrayList<Product>foods=new ArrayList<>();
                for(Product product : products){
                    if(product.getType().equals(Product.types.Food))
                        foods.add(product);
                }
                arrIsChecked = new boolean[foods.size()];
                String[] arr = new String[foods.size()];
                for (int i = 0; i < foods.size(); i++) {
                    arr[i] = foods.get(i).getName() + "\t" + (foods.get(i).getPrice() + diff);
                }
                mBuilder.setMultiChoiceItems(arr, arrIsChecked, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which, boolean isChecked) {
                        arrIsChecked[which] = isChecked;
                        String currentItem = foods.get(which).getName();
                        Toast.makeText(CustomerActivity.this, currentItem + " " + isChecked, Toast.LENGTH_SHORT).show();
                    }
                });
                mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (int j = 0; j < arrIsChecked.length; j++) {
                            {
                                if (arrIsChecked[j]) {
                                    if (!productsOrder.contains(foods.get(j))) {
                                        productsOrder.add(foods.get(j));
                                        foodsOrder.add(foods.get(j));
                                        sum += foods.get(j).getPrice() + diff;
                                    }
                                } else {
                                    if (productsOrder.contains(foods.get(j))) {
                                        productsOrder.remove(foods.get(j));
                                        foodsOrder.remove(foods.get(j));
                                        sum -= foods.get(j).getPrice() + diff;

                                    }
                                }
                            }
                        }
                        Food.setText(foodsOrder.toString());
                    }
                });
                mBuilder.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (boolean bol : arrIsChecked) bol = false;
                        productsOrder.removeAll(foodsOrder);
                        foodsOrder.clear();
                        Food.setText("Choose");
                        sum = 0;
                    }
                });
                AlertDialog dialog = mBuilder.create();
                dialog.show();
                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.parseColor("#000000"));
                dialog.getButton(DialogInterface.BUTTON_NEUTRAL).setTextColor(Color.parseColor("#000000"));
            }
        });
        Snack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(CustomerActivity.this);
                mBuilder.setTitle("Snacks:");
                final ArrayList<Product>snacks=new ArrayList<>();
                for(Product product : products){
                    if(product.getType().equals(Product.types.Snacks))
                        snacks.add(product);
                }
                arrIsChecked = new boolean[snacks.size()];
                String[] arr = new String[snacks.size()];
                for (int i = 0; i < snacks.size(); i++) {
                    arr[i] = snacks.get(i).getName() + "\t" + (snacks.get(i).getPrice() + diff);
                }
                mBuilder.setMultiChoiceItems(arr, arrIsChecked, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which, boolean isChecked) {
                        arrIsChecked[which] = isChecked;
                        String currentItem = snacks.get(which).getName();
                        Toast.makeText(CustomerActivity.this, currentItem + " " + isChecked, Toast.LENGTH_SHORT).show();
                    }
                });
                mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (int j = 0; j < arrIsChecked.length; j++) {
                            {
                                if (arrIsChecked[j]) {
                                    if (!productsOrder.contains(snacks.get(j))) {
                                        productsOrder.add(snacks.get(j));
                                        snacksOrder.add(snacks.get(j));
                                        sum += snacks.get(j).getPrice() + diff;
                                    }
                                } else {
                                    if (productsOrder.contains(snacks.get(j))) {
                                        productsOrder.remove(snacks.get(j));
                                        snacksOrder.remove(snacks.get(j));
                                        sum -= snacks.get(j).getPrice() + diff;

                                    }
                                }
                            }
                        }
                        Snack.setText(snacksOrder.toString());
                    }
                });
                mBuilder.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (boolean bol : arrIsChecked) bol = false;
                        productsOrder.removeAll(snacksOrder);
                        snacksOrder.clear();
                        Snack.setText("Choose");
                        sum = 0;
                    }
                });
                AlertDialog dialog = mBuilder.create();
                dialog.show();
                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.parseColor("#000000"));
                dialog.getButton(DialogInterface.BUTTON_NEUTRAL).setTextColor(Color.parseColor("#000000"));

            }
        });
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RootRef.child("Orders");
                String UserName = nameET.getText().toString();
                String Userphone = phone.getText().toString();
                String Orderid = RootRef.push().getKey();
                if(TextUtils.isEmpty(UserName)){
                    nameET.setError("Name is Required");
                    return;
                }
                if(TextUtils.isEmpty(Userphone)){
                    phone.setError("Phone is Required");
                    return;
                }
                if (productsOrder.size() == 0)
                    Toast.makeText(CustomerActivity.this, "Please Select at list one product!!", Toast.LENGTH_SHORT).show();
                else {
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy, HH:mm");
                    String date = df.format(Calendar.getInstance().getTime());
                    userName=UserName;
                    Order o = new Order(UserName, Userphone, TakeAway, productsOrder, Orderid, sum, "not served",date);
                    FirebaseDatabase.getInstance().getReference("Orders").child(Orderid).setValue(o, complitionListener);
                }
            }
        });
    }

    DatabaseReference.CompletionListener complitionListener = new DatabaseReference.CompletionListener() {
        @Override
        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
            if (databaseError != null) {
                Toast.makeText(CustomerActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
            } else {
                for (Product product : productsOrder) {
                    String str = product.getId();
                    RootRef.getDatabase().getReference("Products");
                    Query updateQuantity = RootRef.child(str);
                    updateQuantity.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            p = dataSnapshot.getValue(Product.class);
                            String Pname = p.getName();
                            String Pid = p.getId();
                            int Pquant = p.getStocks() - 1;
                            double Pprice = p.getPrice();
                            p = new Product(Pid, Pname, Pprice, Pquant,p.getType());
                            RootRef.child(Pid).setValue(p);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                Intent intent = new Intent(CustomerActivity.this, Pop.class);
                intent.putExtra("sum", sum);
                intent.putExtra("name",userName);
                startActivity(intent);
                products.clear();
                productsOrder.clear();
                drinksOrder.clear();
                foodsOrder.clear();
                snacksOrder.clear();
                nameET.setText("");
                phone.setText("");
                Drink.setText("Choose");
                Food.setText("Choose");
                Snack.setText("Choose");
                sum = 0;
                userName="";
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.customermenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.takeMenu) {
            Toast.makeText(CustomerActivity.this, "Take", Toast.LENGTH_SHORT).show();
            Intent intent =new Intent(CustomerActivity.this,CustomerActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("TakeAway",true);
            startActivity(intent);
        }
        else if (id == R.id.seatMenu) {
            Intent intent =new Intent(CustomerActivity.this,CustomerActivity.class);
            intent.putExtra("TakeAway",false);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            Toast.makeText(CustomerActivity.this, "Seat", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.clearMenu) {
            nameET.setText("");
            phone.setText("");
            productsOrder.clear();
            Drink.setText("Choose");
            Food.setText("Choose");
            Snack.setText("Choose");
            snacksOrder.clear();
            foodsOrder.clear();
            drinksOrder.clear();
            Toast.makeText(CustomerActivity.this, "Clear", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}