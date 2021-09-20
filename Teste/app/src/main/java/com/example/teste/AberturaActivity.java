package com.example.teste;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

public class AberturaActivity extends AppCompatActivity implements Runnable{

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
                thread.sleep(25);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        i++;
                        progressBar.setProgress(i);
                    }
                });
            }
            finish();
            startActivity(new Intent(getBaseContext(),MainActivity.class));

        }catch (InterruptedException e){

        }

    }
}