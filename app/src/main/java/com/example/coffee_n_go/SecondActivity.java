package com.example.coffee_n_go;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {
    Button check;
    String DrinkFromList;
    String[] Drinks_items;
    boolean[] arr;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        check = findViewById(R.id.testBtn);
        textView=findViewById(R.id.textView);
        final ArrayList<Product> prodList = new ArrayList<Product>();

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drinks_items = getResources().getStringArray(R.array.Coffie_Drinks);
                        arr = new boolean[Drinks_items.length];
                        for (boolean a : arr) {
                            a = false;
                        }
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SecondActivity.this);
                        mBuilder.setMultiChoiceItems(Drinks_items, arr, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                                DrinkFromList = Drinks_items[i];
                                check.setText(DrinkFromList);
                                arr[i]=b;
                                Toast.makeText(getApplicationContext(),DrinkFromList+ " "+b,Toast.LENGTH_SHORT).show();
                            }
                        });
                        mBuilder.setCancelable(true);
                        mBuilder.setTitle("Drinks:");
                        mBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                      }
                    });
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        textView.setText("Your order is : \n");
                        for(int j=0;i<arr.length;j++){
                            boolean checked=arr[j];
                            if(checked) textView.setText(textView.getText()+Drinks_items[j]+"\n" );
                        }
                    }
                });
                AlertDialog dialog = mBuilder.create();
                dialog.show();
                    }

                });

            }

    }