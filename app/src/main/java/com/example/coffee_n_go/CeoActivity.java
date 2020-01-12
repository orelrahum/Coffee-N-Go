package com.example.coffee_n_go;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class CeoActivity extends AppCompatActivity {
    Button chnagePricesBtn;
    Button newProdBtn;
    Button removeProdBtn;
    Button insertNewWorker;
    Button insertNewCeo;
    ArrayList<String>almostEnd=new ArrayList<>();
    DatabaseReference myRef;
    FirebaseDatabase myDb;
    int minStackToAlert=15;
    TextView welcome;
    String userName="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ceo);


        chnagePricesBtn=findViewById(R.id.changePricesBtn);
        newProdBtn=findViewById(R.id.insertProductsBtn);
        removeProdBtn=findViewById(R.id.RemoveProductBtn);
        insertNewWorker=findViewById(R.id.CreateNewWorkerBtn);
        insertNewCeo = findViewById(R.id.CreateNewCeoBtn);
        welcome = findViewById(R.id.managerWelcomeTv);
        Intent intent =getIntent();
        userName=intent.getStringExtra("name");
        welcome.setText(welcome.getText()+userName);

        myDb=FirebaseDatabase.getInstance();
        myRef=myDb.getReference("Products");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Product p = (ds.getValue(Product.class));
                    if(p.getStocks()<minStackToAlert)
                        almostEnd.add(p.getName());
                }
                if(almostEnd.size()>0){
                    Intent intent = new Intent(CeoActivity.this,UpdateProductDetails.class);
                    String s="This products about to end : ";
                    for(String str :almostEnd)s+=str+",";
                    s=s.substring(0,s.length()-1);
                    intent.putExtra("extra",s);
                    PendingIntent pendingIntent = PendingIntent.getActivity(CeoActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                    NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                        String channelId = "MY_CHANNEL_ID";
                        NotificationChannel channel = new NotificationChannel(channelId,"channel 1",NotificationManager.IMPORTANCE_DEFAULT);
                        channel.setDescription("Ceo channel");
                        notificationManager.createNotificationChannel(channel);
                        NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(),channelId);
                        Notification notification = builder.setContentIntent(pendingIntent)
                                .setSmallIcon(R.drawable.ic_message)
                                .setWhen(System.currentTimeMillis())
                                .setAutoCancel(true)
                                .setContentTitle("the inventory about to end")
                                .setContentText(almostEnd.toString()).build();
                        builder.setChannelId(channelId);
                        notificationManager.notify(1,notification);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });



        newProdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CeoActivity.this, InsertNewProdActivity.class);
                startActivity(i);
            }
        });
        chnagePricesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CeoActivity.this,UpdateProductDetails.class);
                startActivity(intent);
            }
        });
        removeProdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CeoActivity.this,DeleteProduct.class);
                startActivity(intent);
            }
        });
        insertNewWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CeoActivity.this,CreateNewWorker.class);
                intent.putExtra("permission","WORKER");
                startActivity(intent);
            }
        });
        insertNewCeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CeoActivity.this,CreateNewWorker.class);
                intent.putExtra("permission","MANAGER");
                startActivity(intent);
            }
        });
    }
}
