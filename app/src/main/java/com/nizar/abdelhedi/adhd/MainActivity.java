package com.nizar.abdelhedi.adhd;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.dd.processbutton.iml.ActionProcessButton;

import static android.R.attr.button;

public class MainActivity extends AppCompatActivity {

    ActionProcessButton btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnSignIn = (ActionProcessButton)  findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View view) {
                try {
                    btnSignIn.setProgress(50);
                    btnSignIn.setMode(ActionProcessButton.Mode.PROGRESS);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                btnSignIn.setProgress(100);

            }
        });


    }

    public void signUp(View view) {
        Intent intent = new Intent(this,SignUp.class);
        startActivity(intent);
    }
}
