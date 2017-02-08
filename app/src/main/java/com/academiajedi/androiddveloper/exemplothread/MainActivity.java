package com.academiajedi.androiddveloper.exemplothread;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ImageView ivImageDown;
    private Button btnDownloadImage;
    public ProgressDialog loadingdialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivImageDown = (ImageView) findViewById(R.id.ivImageDown);

        btnDownloadImage = (Button) findViewById(R.id.btnDownloadImage);
        btnDownloadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baixarImage();
            }
        });

    }

    public void baixarImage(){


        loadingdialog = ProgressDialog.show(MainActivity.this,
                "","Baixando a imagem",true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    URL url = new URL("https://media.licdn.com/mpr/mpr/shrink_200_200/AAEAAQAAAAAAAAkZAAAAJDM1MDY0ZGM0LTZlOTMtNDVjNi05ZTRjLWEyZmNhZTEwNzg2Zg.png");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadingdialog.dismiss();
                            ivImageDown.setImageBitmap(bitmap);
                        }
                    });

                    Log.i("MainActivity", "Baixou a imagem");
                } catch (Exception e){
                    loadingdialog.dismiss();
                    Log.e("MainActivity", "Erro ao baixar imagem: "+e.getMessage());
                }
            }
        }).start();

    }
}
