package com.example.coffee_n_go;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class WorkerActivity extends AppCompatActivity {
    DatabaseReference myRef;
    FirebaseDatabase myDb;
    Order o;
    ArrayList<Order> orders = new ArrayList<>();
    ListView listView;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker);


        listView=(ListView)findViewById(R.id.listview);
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,orders);
        listView.setAdapter(arrayAdapter);
        myDb = FirebaseDatabase.getInstance();
        myRef = myDb.getReference("Orders");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orders.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    o = (ds.getValue(Order.class));
                    if (o.getStatus().equals("not served")) {
                        orders.add(o);
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                myRef.child("Orders");
                orders.get(i).setStatus("served");
                FirebaseDatabase.getInstance().getReference("Orders").child(orders.get(i).getOrderID()).setValue(orders.get(i));
                Toast.makeText(WorkerActivity.this,orders.get(i).toString()+" deleted",Toast.LENGTH_SHORT).show();
                arrayAdapter.notifyDataSetChanged();
            }
        });
    }
}
