package com.macsoftware.event.adminconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.macsoftware.event.R;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class add extends AppCompatActivity {

    EditText edittexteventname, edittextlink;
    CalendarView calendar;
    Button AddButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        edittexteventname = findViewById(R.id.editTextText);
        edittextlink = findViewById(R.id.editTextText2);
        CalendarView calendarView = findViewById(R.id.calendarView);



        AddButton = findViewById(R.id.ButtonAddEvent);
        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String veri1 = edittexteventname.getText().toString();
                String veri2 = edittextlink.getText().toString();
                long secilenTarih = calendarView.getDate(); // Takvimden tarihi al
                Map<String, Object> veri = new HashMap<>();
                veri.put("veri1", veri1);
                veri.put("veri2", veri2);
                veri.put("tarih", new Timestamp(new Date(secilenTarih)));

                CollectionReference veriKoleksiyonu = db.collection("veriler");
                DocumentReference belgeReferansi = veriKoleksiyonu.document();

                belgeReferansi.set(veri)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(add.this, "", Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), "Succ", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Not Succ: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}