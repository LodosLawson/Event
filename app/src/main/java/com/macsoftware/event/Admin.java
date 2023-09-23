package com.macsoftware.event;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.macsoftware.event.adminconnect.Next;
import com.macsoftware.event.adminconnect.add;
import com.macsoftware.event.adminconnect.event;
import com.macsoftware.event.adminconnect.participsnts;
import com.macsoftware.event.adminconnect.qrconnect;

public class Admin extends AppCompatActivity {
    private static final String GOOGLE_FORM_URL = "https://forms.google.com/your-form-url-here";
    private WebView webView;

    private SharedPreferences sharedPreferences;
    private Button addButton;
    private Button eventButton;
    private Button participsntsButton;
    private Button qrconnectButton;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        // WebView'i tanımlayın
        webView = findViewById(R.id.webwiev);

        // WebView ayarlarını yapılandırın
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Eğer JavaScript gerekiyorsa

        // WebViewClient kullanarak sayfayı WebView içinde açın
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // Harici tarayıcılara yönlendirmeyi engelleyin
                if (url != null && (url.startsWith("http://") || url.startsWith("https://"))) {
                    view.loadUrl(url);
                    return true;
                } else {
                    // Harici tarayıcılara gitmeyi engelleyin ve mevcut WebView içinde açın
                    return false;
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // Sayfa yüklendikten sonra burada işlemler yapabilirsiniz
            }
        });

        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        if (clipboardManager.hasPrimaryClip()) {
            ClipData clipData = clipboardManager.getPrimaryClip();
            if (clipData != null && clipData.getItemCount() > 0) {
                ClipData.Item item = clipData.getItemAt(0); // Panoya en son kopyalanan veriyi alın
                String copiedText = item.getText().toString();

                // Kopyalanan metni kullanabilirsiniz
                if (copiedText != null && !copiedText.isEmpty()) {
                    // Örnek olarak, metni bir Toast mesajıyla gösterelim
                    Toast.makeText(this, "Panoya kopyalanan metin: " + copiedText, Toast.LENGTH_SHORT).show();
                }
            }
        }
        // Kullanıcıyı Google hesabına bağlanmaya yönlendirin (örneğin, Google OAuth 2.0 kimlik doğrulama işlemi ile)
        // Bu işlemi gerçekleştirmek için özel bir kod ve kimlik doğrulama akışı gerekebilir.

        // Google Form'un URL'sini yükleyin
        webView.loadUrl(GOOGLE_FORM_URL);

        addButton = findViewById(R.id.ButtonNext);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SharedPreferences'e verileri kaydet
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("link", "https://example.com");
                editor.putString("id", "123");
                editor.putString("name", "Örnek İsim");
                editor.apply();

                // Veriler kaydedildiğinde kullanıcıya bir geri bildirim verin (isteğe bağlı)
                 Toast.makeText(Admin.this, "Veriler kaydedildi", Toast.LENGTH_SHORT).show();
            }
        });


        addButton = findViewById(R.id.ButtonAdd);
        eventButton = findViewById(R.id.ButtonEvent);
        qrconnectButton = findViewById(R.id.ButtonQrConnect);
        participsntsButton = findViewById(R.id.ButtonParticipsnts);
        nextButton = findViewById(R.id.ButtonNext);

        // Tıklama işlevini oluşturun
        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.ButtonEvent){
                    FrameEvent();
                } else if (v.getId() == R.id.ButtonNext) {
                    FrameNext();
                } else if (v.getId() == R.id.ButtonParticipsnts) {
                    FrameParticipsnts();
                } else if (v.getId() == R.id.ButtonQrConnect) {
                    Frameqrconnect();
                } else if (v.getId() == R.id.ButtonAdd) {
                    FrameAdd();
                }


            }
        };

        // Her düğmeye tıklama işlevini atayın
        addButton.setOnClickListener(buttonClickListener);
        eventButton.setOnClickListener(buttonClickListener);
        qrconnectButton.setOnClickListener(buttonClickListener);
        participsntsButton.setOnClickListener(buttonClickListener);
        nextButton.setOnClickListener(buttonClickListener);

    }

    // Geri tuşuna basıldığında
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            // WebView geri gidebilirse, mevcut URL'yi alın
            String currentUrl = webView.getUrl();

            // Bu URL'yi kullanabilir veya işleyebilirsiniz
            if (currentUrl != null) {
                // URL'yi göstermek için bir Toast mesajı kullanalım
                Toast.makeText(Admin.this, "Mevcut Sayfa URL'si: " + currentUrl, Toast.LENGTH_SHORT).show();
            }

            // WebView'de geri git
            webView.goBack();
        } else {
            super.onBackPressed(); // Uygulamadan çık
        }
    }

    public void FrameNext(){
        // İkinci aktiviteye geçiş için Intent'i oluşturun
        Intent intent = new Intent(Admin.this, Next.class);

        // İkinci aktiviteyi başlatın
        startActivity(intent);
    }

    private void startQRCodeScanner() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        //integrator.setCaptureActivity(CustomScannerActivity.class); // İsteğe bağlı olarak özel bir tarayıcı ekleyebilirsiniz
        integrator.setOrientationLocked(false); // Ekran döndürme iznini kaldırır

        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() == null) {
                // QR kod taraması iptal edildi.
            } else {
                String qrData = result.getContents(); // QR kodun içeriği
                // QR kod verisini kullanabilir ve Firebase veritabanında kontrol edebilirsiniz.
                Uri uri = Uri.parse(qrData);
                loadUrlInWebView(String.valueOf(uri));
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void loadUrlInWebView(String url) {
        webView = findViewById(R.id.controlweb);
        webView.getSettings().setJavaScriptEnabled(true);

        // URL'yi WebView içinde aç
        webView.loadUrl(url);

        // WebView'deki sayfa yüklendiğinde geri dönmek için WebViewClient kullanın
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // WebView'deki bağlantıyı işleyin ve geri dönmeyin
                view.loadUrl(url);
                return true;
            }
        });
    }
    public void FrameAdd(){
        // İkinci aktiviteye geçiş için Intent'i oluşturun
        Intent intent = new Intent(Admin.this, add.class);

        // İkinci aktiviteyi başlatın
        startActivity(intent);
    }
    public void FrameParticipsnts(){
        // İkinci aktiviteye geçiş için Intent'i oluşturun
        Intent intent = new Intent(Admin.this, participsnts.class);

        // İkinci aktiviteyi başlatın
        startActivity(intent);
    }
    public void Frameqrconnect(){
        // İkinci aktiviteye geçiş için Intent'i oluşturun
        //Intent intent = new Intent(Admin.this, qrconnect.class);
        startQRCodeScanner();
        // İkinci aktiviteyi başlatın
        //startActivity(intent);
    }
    public void FrameEvent(){
        // İkinci aktiviteye geçiş için Intent'i oluşturun
        Intent intent = new Intent(Admin.this, event.class);

        // İkinci aktiviteyi başlatın
        startActivity(intent);
    }
}