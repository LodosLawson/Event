package com.macsoftware.event.api;

import android.os.AsyncTask;

import com.google.api.services.drive.Drive;
import com.macsoftware.event.adminconnect.participsnts;

import java.io.File;
import java.util.List;

public class driver extends AsyncTask<Void, Void, List<File>> {
    private Drive driveService;
    private participsnts activity;

    public driver(Drive driveService, participsnts activity) {
        this.driveService = driveService;
        this.activity = activity;
    }

    @Override
    protected List<File> doInBackground(Void... voids) {
        // Google Drive'dan dosyaları listeleyin
        return null; // Burada Google Drive API kullanarak dosya listeleme işlemlerini eklemeniz gerekecek
    }

    @Override
    protected void onPostExecute(List<File> files) {
        super.onPostExecute(files);
        if (files != null) {
            // Dosyaları ListView'da gösterme işlemi
        }
    }
}