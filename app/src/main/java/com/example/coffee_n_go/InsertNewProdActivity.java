package com.example.coffee_n_go;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

public class InsertNewProdActivity extends AppCompatActivity {


    DatabaseReference myRef;
    FirebaseDatabase myDb;
    EditText name;
    EditText price;
    EditText quant;
    Button insert;
    List<Product> products = new ArrayList<>();
    Product p;
    Button chooseType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_new_prod);
        insert=findViewById(R.id.InsertNewBtn);
        name=findViewById(R.id.InsProdNameEt);
        price=findViewById(R.id.InsProdPriceEt);
        quant=findViewById(R.id.InsProdQuanEt);
        chooseType=findViewById(R.id.typesBtn);
        myDb=FirebaseDatabase.getInstance();
        myRef=myDb.getReference("Products");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                p = dataSnapshot.getValue(Product.class);
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });



        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ProductName=name.getText().toString();
                String ProductPrice=price.getText().toString();
                String ProductQuant=quant.getText().toString();
                String ProductID=myRef.push().getKey();
                String type=chooseType.getText().toString();
                Product.types Type=Product.types.valueOf(type);
                Product p = new Product(ProductID,ProductName,Double.parseDouble(ProductPrice),Integer.parseInt(ProductQuant),Type);
                if(products.contains(p.getName()))
                    Toast.makeText(InsertNewProdActivity.this,"This product is already in!!",Toast.LENGTH_LONG).show();
                else
                    myRef.child(ProductID).setValue(p,completionListener);
            }
        });
    }

    private void showData(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            p = (ds.getValue(Product.class));
            products.add(p);
        }
    }

    DatabaseReference.CompletionListener completionListener = new DatabaseReference.CompletionListener() {
        @Override
        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
           if(databaseError != null){
               Toast.makeText(InsertNewProdActivity.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
           }
           else{
                Toast.makeText(InsertNewProdActivity.this,"Saved!!",Toast.LENGTH_LONG).show();
           }
        }
    };
}
