package com.example.coffee_n_go;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;


public class Pop extends Activity {
    TextView price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent =getIntent();
        double sum=intent.getDoubleExtra("sum",0.00);
        setContentView(R.layout.popwindow);
        price= findViewById(R.id.PricePop);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        price.setText(price.getText()+""+sum);
        getWindow().setLayout((int) (width),(int)(height));
    }
}
