package com.example.coffee_n_go;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class DeleteProduct extends AppCompatActivity {
    DatabaseReference myRef;
    FirebaseDatabase myDb;
    String prodId;
    ArrayList<String>products=new ArrayList<>();
    ArrayList<String>productsId=new ArrayList<>();
    String prod;
    Product p;
    Button choose;
    Button del;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_product);
        choose = findViewById(R.id.ChooseDeleteBtn);
        del=findViewById(R.id.DeleteBtn);
        myDb = FirebaseDatabase.getInstance();
        myRef = myDb.getReference("Products");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    p = (ds.getValue(Product.class));
                    productsId.add(p.getId());
                    products.add(p.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteProduct.this);
                builder.setTitle("Products:");
                String[]arr=new String[products.size()];
                for(int i=0;i<arr.length;i++){
                    arr[i]=products.get(i);
                }
                builder.setSingleChoiceItems(arr, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        prod=products.get(i);
                        prodId=productsId.get(i);
                        choose.setText(prod);
                    }
                });
                builder.show();
            }
        });
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.getDatabase().getReference("Products/");
                Query deleteQuery = myRef.child(prodId);
                deleteQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds: dataSnapshot.getChildren()){
                            ds.getRef().removeValue();
                        }
                        Toast.makeText(DeleteProduct.this,"Deleted",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(DeleteProduct.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
