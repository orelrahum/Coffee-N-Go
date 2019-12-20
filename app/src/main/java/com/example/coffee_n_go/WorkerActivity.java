package com.example.coffee_n_go;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WorkerActivity extends AppCompatActivity {
    DatabaseReference myRef;
    FirebaseDatabase myDb;
    Button choose;
    Button remove;
    TextView Cname;
    TextView Cphone;
    Order o;
    TextView prods;
    TextView price;
    ArrayList<Order> orders = new ArrayList<>();
    Order myOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker);

        choose = findViewById(R.id.ChooseWorkerBtn);
        remove = findViewById(R.id.RemoveOrderBtn);
        Cname = findViewById(R.id.CustomerNameEt);
        Cphone = findViewById(R.id.CustomerPhoneEt);
        prods = findViewById(R.id.ProdEt);
        price = findViewById(R.id.CustomerPriceEt);
        myDb = FirebaseDatabase.getInstance();
        myRef = myDb.getReference("Orders");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orders.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    o = (ds.getValue(Order.class));
                    orders.add(o);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(WorkerActivity.this);
                builder.setTitle("Orders:");
                String[] arr = new String[orders.size()];
                for (int i = 0; i < arr.length; i++) {
                    arr[i] = orders.get(i).toString();
                }
                builder.setSingleChoiceItems( arr, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        myOrder=orders.get(i);
                        choose.setText(myOrder.toString());
                        Cname.setText(myOrder.getCustomerName());
                        Cphone.setText(myOrder.getCustomerPhone());
                        prods.setText(myOrder.getProducts().toString());
                        price.setText(myOrder.getPrice()+"");
                    }
                });
                builder.show();
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.getDatabase().getReference("Orders/");
                Query deleteQuerey = myRef.child(myOrder.getOrderID());
                deleteQuerey.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds:dataSnapshot.getChildren()){
                            ds.getRef().removeValue();
                        }
                        Toast.makeText(WorkerActivity.this,"Deleted",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(WorkerActivity.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
