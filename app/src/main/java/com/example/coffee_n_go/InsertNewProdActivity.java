package com.example.coffee_n_go;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
    Product.types selectedType;
    TextView productsHave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_new_prod);
        insert=findViewById(R.id.InsertNewBtn);
        name=findViewById(R.id.InsProdNameEt);
        price=findViewById(R.id.InsProdPriceEt);
        quant=findViewById(R.id.InsProdQuanEt);
        chooseType=findViewById(R.id.typesBtn);
        productsHave=findViewById(R.id.productsHaveTv);
        final Product.types[] possibleValues = Product.types.values();
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
                String ProductName = name.getText().toString();
                String ProductPrice = price.getText().toString();
                String ProductQuant = quant.getText().toString();
                String ProductID = myRef.push().getKey();
                if (products.contains(p.getName()))
                    Toast.makeText(InsertNewProdActivity.this, "This product is already in!!", Toast.LENGTH_LONG).show();
                if (TextUtils.isEmpty(ProductName)) {
                    name.setError("You must enter name!");
                    return;
                }
                if (TextUtils.isEmpty(ProductPrice)) {
                    price.setError("You must enter price!");
                    return;
                }
                if (TextUtils.isEmpty(ProductQuant)) {
                    quant.setError("You must enter quantity!");
                    return;
                }
                String type = chooseType.getText().toString();
                if (TextUtils.isEmpty(type)|| !type.equals("CHOOSE TYPE")) {
                    quant.setError("You must select type!");
                    return;
                }
                Product.types Type = Product.types.valueOf(type);
                Product p = new Product(ProductID, ProductName, Double.parseDouble(ProductPrice), Integer.parseInt(ProductQuant), Type);
                myRef.child(ProductID).setValue(p, completionListener);
            }
        });
        chooseType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(InsertNewProdActivity.this);
                builder.setTitle("Types:");
                String[]arr=new String[possibleValues.length];
                for(int i=0;i<arr.length;i++){
                    arr[i]=possibleValues[i].toString();
                }
                builder.setSingleChoiceItems(arr, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selectedType = possibleValues[i];
                        chooseType.setText(selectedType.toString());
                    }
                });
                builder.show();
        };
    });
    }

    private void showData(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            p = (ds.getValue(Product.class));
            products.add(p);
        }
        productsHave.setText("The products that we have is : "+products.toString());
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
