package com.example.watercontrolapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShowStatusActivity extends AppCompatActivity {
    Button refresh;
    FloatingActionButton out, forward;
    DatabaseReference reference;
    //this is about notification
    private static final String CHANNEL_ID = "channel_id";
    private static final int NOTIFICATION_ID = 1;

    //end
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_status);
        //this is about notification
        createNotificationChannel();
        //end
        reference = FirebaseDatabase.getInstance().getReference("system data");
        reference.child("1").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        Toast.makeText(ShowStatusActivity.this, " read data successfully", Toast.LENGTH_LONG).show();
                        DataSnapshot dataSnapshot = task.getResult();
                        String chloretx = String.valueOf(dataSnapshot.child("chlore").getValue());
                        double chloreValue = Double.parseDouble(chloretx);
                        String formattedChlore = String.format("%.2f", chloreValue);
                        String conductivitetx = String.valueOf(dataSnapshot.child("conductivite").getValue());
                        double conductivityValue = Double.parseDouble(conductivitetx);
                        String formattedConductivite = String.format("%.2f", conductivityValue);
                        String etattx = String.valueOf(dataSnapshot.child("status/prediction_result/0").getValue());
                        double etatvalue = Double.parseDouble(etattx);
                        TextView chlore = findViewById(R.id.phlevel);
                        chlore.setText(formattedChlore);
                        TextView conductivite = findViewById(R.id.turbiditylevel);
                        conductivite.setText(formattedConductivite);
                        TextView etat = findViewById(R.id.tdslevel);
                        String etatwatertx; // Declare the etatwatertx variable
                        if (etatvalue == 0) {
                            etatwatertx = "Water not potable"; // Set the string if etatvalue is 0
                        } else if (etatvalue == 1) {
                            etatwatertx = "Potable water"; // Set the string if etatvalue is 1
                        } else {
                            etatwatertx = "Unknown"; // Set a default value for other cases
                        }

                        etat.setText(etatwatertx);

                        //notification push
                        if (chloreValue > 0.05 ){
                            MyFirebaseMessagingService messagingService = new MyFirebaseMessagingService();
                            messagingService.showNotification("Warning", "Value exceeds desired level!");
                        }
                    } else {
                        Toast.makeText(ShowStatusActivity.this, " data doesn't exist", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ShowStatusActivity.this, "failed to read data", Toast.LENGTH_LONG).show();

                }
            }
        });

        out = findViewById(R.id.backarrow2);
        forward = findViewById(R.id.goforward);
        refresh = findViewById(R.id.refresh2);
        //update the results
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotification();
                Intent j = new Intent(ShowStatusActivity.this, ShowStatusActivity.class);
                startActivity(j);
            }
        });

        //sortir de l'application
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(ShowStatusActivity.this, MainActivity.class);
                startActivity(k);
            }
        });
        //go the second page
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Z = new Intent(ShowStatusActivity.this, ShowStatusActivity2.class);
                startActivity(Z);
            }
        });

    }

    //this is about notification
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "My Channel";
            String description = "My Channel Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void showNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_notifications_24)
                .setContentTitle("Refresh Notification")
                .setContentText("This is tha latest update in the system")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
    //end

}