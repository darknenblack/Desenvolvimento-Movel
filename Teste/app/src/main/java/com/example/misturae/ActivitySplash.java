package com.example.misturae;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

public class ActivitySplash extends AppCompatActivity implements Runnable{

    private ProgressBar progressBar;
    private Thread thread;
    private Handler handler;
    private int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abertura);

        progressBar = (ProgressBar)findViewById(R.id.progres_abertura);


        handler = new Handler();
        thread = new Thread(this);
        thread.start();

    }

    @Override
    public void run() {

        i = 1;
        try{
            while(i<=100){
                thread.sleep(10);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        i++;
                        progressBar.setProgress(i);
                    }
                });
            }
            finish();
            startActivity(new Intent(getBaseContext(), ActivityMain.class));

        }catch (InterruptedException e){

        }

    }
}