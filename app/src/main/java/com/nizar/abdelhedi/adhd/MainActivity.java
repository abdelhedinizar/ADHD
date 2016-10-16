package com.nizar.abdelhedi.adhd;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.util.ExceptionCatchingInputStream;
import com.dd.processbutton.iml.ActionProcessButton;

import org.json.JSONObject;

import static android.R.attr.button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_PASSWORD = "password";
    private static final String TAG_USERNAME = "pseudo";
    ActionProcessButton btnSignIn;
    TextView pseudo ;
    TextView password;
    ProgressDialog pDialogT;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pseudo= (TextView) findViewById(R.id.input_email);
        password= (TextView) findViewById(R.id.input_password);
        btnSignIn = (ActionProcessButton)  findViewById(R.id.btnSignIn);
        pDialogT = new ProgressDialog(this);
        pDialogT.setMessage("Please wait... :P");
        pDialogT.setCancelable(false);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View view) {
                /*try {
                    btnSignIn.setProgress(50);
                    //btnSignIn.setMode(ActionProcessButton.Mode.PROGRESS);
                   // Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
                btnSignIn.setProgress(100);


                if(cvrtListToJson() == null){
                    Toast.makeText(MainActivity.this , "input error"  , Toast.LENGTH_LONG).show();
                    return ;
                }else
                   new GetContacts(MainActivity.this, cvrtListToJson()).execute();
                /*{
                    try{
                        pDialogT.show();
                        Thread.sleep(1000);
                        pDialogT.dismiss();
                        Intent intent = new Intent(getApplicationContext()  , FilmActivity.class);
                        startActivity(intent);
                    }catch (Exception e){

                    }
                }*/
            }

        });






    }


    private JSONObject cvrtListToJson() {
        JSONObject alldata =new JSONObject();
        try {

            alldata.put(TAG_USERNAME,pseudo.getText().toString());
            alldata.put(TAG_PASSWORD,password.getText().toString());
            Log.i("djhfgjhdfg","hgjghgf");
            return  alldata;


        }catch (Exception e){

            Log.e("error" , "errorrrr" + e.getMessage());
        }
        return null;
    }





    public void signUp(View view) {
        Intent intent = new Intent(this,AccueilActivity.class);
        startActivity(intent);
    }











    private class GetContacts extends AsyncTask<Void, Void, String> {

        Context context;
        ProgressDialog pDialog;
        private static final String url = "https://whispering-garden-24422.herokuapp.com/kid/login";


        JSONObject json;

        public GetContacts(Context context, JSONObject json){
            this.context=context;
            this.json=json;}
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Please wait... :p");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler(json);

            // Making a request to url and getting response
            final String reponseServeur = sh.makeCall(url, 4*1000);

            return reponseServeur;
        }

        @Override
        protected void onPostExecute(String  result) {

            super.onPostExecute(result);

//            Dismiss the progress dialog
            pDialog.dismiss();
            final String result2=result;
            if(result!=null){

                Log.i("test ", "mriguel ");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context,result2,Toast.LENGTH_LONG).show();

                        PrefManager manager = new PrefManager(MainActivity.this);
                        manager.setUSER_Name(pseudo.getText().toString());
                        manager.setIsDoctor(true);
                        //  manager.setUserId("");



                        Intent intent = new Intent(getApplicationContext()  , AccueilActivity.class);
                        startActivity(intent);


                    }
                });

            }
            else  runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context,"error",Toast.LENGTH_LONG).show();
                }
            });
            if (pDialog.isShowing())
                pDialog.dismiss();
        }

    }
}

    /*ActionProcessButton btnSignIn;
    EditText userNameEditText;
    EditText passowrdEditText;
    TextInputLayout mEmailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNameEditText = (EditText)findViewById(R.id.input_email);
        passowrdEditText = (EditText)findViewById(R.id.input_password);
        mEmailView = (TextInputLayout)findViewById(R.id.input);


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

                if(userNameEditText.getText().toString().length()>4)
                {
                    btnSignIn.setProgress(100);
                    String user=userNameEditText.getText().toString();
                    String pass=passowrdEditText.getText().toString();

                    Intent intent = new Intent(MainActivity.this,AccueilActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                   mEmailView.setError("please sign up");


                }




            }
        });


    }

    public void signUp(View view) {
        Intent intent = new Intent(this,SignUp.class);
        startActivity(intent);
    }*/
