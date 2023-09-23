package com.macsoftware.event.adminconnect;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.macsoftware.event.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Next extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        //RealTime Data BASE
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("veritabani_yolu");
        ListView ListView1 = findViewById(R.id.ListViewDataBase);

        //normal DataBase
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference veriKoleksiyonu = db.collection("veri");
        DocumentReference belgeReferansi = veriKoleksiyonu.document();

        // Eklemek istediğiniz verileri bir Map veya özel bir sınıf kullanarak oluşturun
        Map<String, Object> veri = new HashMap<>();
        veri.put("Link", "Test/lINK");
        veri.put("Event Name", "mIT");

        // Belgeye veriyi ekle
        belgeReferansi.set(veri)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Veri başarıyla eklendi
                        Toast.makeText(getApplicationContext(), "Veri başarıyla yazıldı Normal DataBase", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Veri ekleme hatası
                        Toast.makeText(getApplicationContext(), "Veri yazma hatası: Normal DataBase ", Toast.LENGTH_SHORT).show();

                    }
                });
        // Yeni bir veri oluşturun

        // DatabaseReference üzerinden veriyi ekleyin

        databaseReference.push().setValue("test","1", new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError == null) {
                    // Veri başarıyla yazıldı
                    Toast.makeText(getApplicationContext(), "Veri başarıyla yazıldı", Toast.LENGTH_SHORT).show();
                } else {
                    // Veri yazma hatası oluştu
                    Toast.makeText(getApplicationContext(), "Veri yazma hatası: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Veri değiştiğinde buraya düşer
                // dataSnapshot içindeki verileri işleyin

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Veri okuma işlemi iptal edildiğinde buraya düşer
            }
        });

        //CollectionReference veriKoleksiyonu1 = db.collection("veri");
        veriKoleksiyonu.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        ArrayList<String> verilerListesi = new ArrayList<>();

                        for (QueryDocumentSnapshot document : querySnapshot) {
                            Map<String, Object> veri = document.getData();
                            String baslik = (String) veri.get("Link");
                            String icerik = (String) veri.get("Event Name");
                            // Veriyi kullanabilirsiniz, örneğin veriyi bir liste ekleyebilirsiniz
                            verilerListesi.add(baslik + " - " + icerik);
                        }

                        // Verileri ListView'e veya başka bir liste görünümüne ekleyin
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, verilerListesi);
                        ListView1.setAdapter(adapter); // listView, listeyi göstermek istediğiniz liste görünümüdür
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Veri okuma hatası
                    }
                });
    }
}