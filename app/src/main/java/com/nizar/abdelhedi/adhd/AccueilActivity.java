package com.nizar.abdelhedi.adhd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AccueilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

    }

    public void filmActivityCall(View view) {
        Intent intent = new Intent(this,FilmsList.class);
        startActivity(intent);
    }
}
