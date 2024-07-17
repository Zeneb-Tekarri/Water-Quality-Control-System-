package com.example.watercontrolapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
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

public class ShowStatusActivity2 extends AppCompatActivity {

    FloatingActionButton back;
    DatabaseReference reference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_status2);
        reference = FirebaseDatabase.getInstance().getReference("system data");
        reference.child("1").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        Toast.makeText(ShowStatusActivity2.this, " read data successfully", Toast.LENGTH_LONG).show();
                        DataSnapshot dataSnapshot = task.getResult();
                        String phtx = String.valueOf(dataSnapshot.child("ph").getValue());
                        double phValue = Double.parseDouble(phtx);
                        String formattedph = String.format("%.2f", phValue);
                        String turbiditetx = String.valueOf(dataSnapshot.child("Turbidite").getValue());
                        double turbiditeValue = Double.parseDouble(turbiditetx);
                        String formattedturbidite = String.format("%.2f", turbiditeValue);
                        String tdstx = String.valueOf(dataSnapshot.child("tds").getValue());
                        double tdsValue = Double.parseDouble(tdstx);
                        String formattedtds = String.format("%.2f", tdsValue);
                        TextView ph = findViewById(R.id.phlevel);
                        ph.setText(formattedph);
                        TextView turbidite = findViewById(R.id.turbiditylevel);
                        turbidite.setText(formattedturbidite);
                        TextView tds = findViewById(R.id.tdslevel);
                        tds.setText(formattedtds);
                    } else {
                        Toast.makeText(ShowStatusActivity2.this, " data doesn't exist", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ShowStatusActivity2.this, "failed to read data", Toast.LENGTH_LONG).show();

                }
            }
        });

        back = findViewById(R.id.backarrow2);
        //sortir de l'application
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(ShowStatusActivity2.this, ShowStatusActivity.class);
                startActivity(k);
            }
        });

    }

}