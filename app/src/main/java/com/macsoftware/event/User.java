package com.macsoftware.event;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.macsoftware.event.api.ListItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class User extends AppCompatActivity {

    List<ListItem> itemList;
    EditText SearchText;
    Button Go;
    ListView listView;
    ArrayList<String> veriListesi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        veriListesi = new ArrayList<>();

        CollectionReference veriKoleksiyonu = db.collection("veriler");
        itemList = new ArrayList<>();

        SearchText = findViewById(R.id.SearchEdit);
        listView = findViewById(R.id.ListViewUser);
        Go = findViewById(R.id.go);

        Go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Firestore'dan tüm verileri al
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference itemsRef = db.collection("veriler");

                itemsRef.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        itemList.clear(); // Önceki verileri temizle

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> veri = document.getData();
                            String veri1 = (String) veri.get("veri1");
                            String veri2 = (String) veri.get("veri2");
                            Timestamp tarihTimestamp = (Timestamp) veri.get("tarih");
                            Date tarih = tarihTimestamp.toDate();

                            itemList.add(new ListItem(veri1, veri2));

                        }

                        // Ardından, bu itemList'i kullanarak arama işlemini başlatın
                        performSearch();
                    } else {
                        // Hata durumunda işlem yapın
                    }
                });
            }
        });



        veriKoleksiyonu.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        for (QueryDocumentSnapshot document : querySnapshot) {
                            Map<String, Object> veri = document.getData();
                            String veri1 = (String) veri.get("veri1");
                            String veri2 = (String) veri.get("veri2");
                            Timestamp tarihTimestamp = (Timestamp) veri.get("tarih");
                            Date tarih = tarihTimestamp.toDate();

                            itemList.add(new ListItem(veri1, veri2));

                            veriListesi.add(veri1 + " - " + veri2 + " - " + tarih.toString());
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, veriListesi);
                        listView.setAdapter(adapter);

                        // ListView öğelerine tıklama özelliğini ekleyin
                        listView.setOnItemClickListener((parent, view, position, id) -> {
                            String link = itemList.get(position).getLink();
                            loadWebView(link);
                        });
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Veri okuma hatası
                    }
                });
    }
    private void loadWebView(String url) {
        Intent intent = new Intent(this, WebExmp.class);
        intent.putExtra("url", url);
        startActivityForResult(intent, 1);
    }

    private void performSearch() {
        // Kullanıcının girdiği arama terimini alın (örneğin, bir EditText kullanabilirsiniz)
        String searchTerm = SearchText.getText().toString();
        veriListesi = new ArrayList<>();

        // Verileri ara ve uygun sonuçları yeni bir ArrayList'e ekleyin
        ArrayList<ListItem> searchResults = new ArrayList<>();
        for (ListItem item : itemList) {
            if (item.getText().contains(searchTerm)) {
                searchResults.add((item));
                veriListesi.add(item.getText() + " - " + item.getLink());

            }
        }

        itemList.clear();
        itemList = searchResults;
        // Arama sonuçlarını yeni bir ArrayAdapter ile ListView'e ekleyin
        ArrayAdapter<String> searchAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1,
                veriListesi);
        listView.setAdapter(searchAdapter);
    }
}