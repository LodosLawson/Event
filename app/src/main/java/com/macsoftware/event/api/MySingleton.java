package com.macsoftware.event.api;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class MySingleton {
    private static MySingleton instance;
    private GoogleSignInAccount googleAccount; // Google hesabını saklamak için bir değişken

    private MySingleton() {
        // Özel kurucu metot: Sınıfın dışarıdan oluşturulmasını engeller
    }

    public static MySingleton getInstance() {
        if (instance == null) {
            instance = new MySingleton();
        }
        return instance;
    }

    public GoogleSignInAccount getGoogleAccount() {
        return googleAccount;
    }

    public void setGoogleAccount(GoogleSignInAccount googleAccount) {
        this.googleAccount = googleAccount;
    }
}