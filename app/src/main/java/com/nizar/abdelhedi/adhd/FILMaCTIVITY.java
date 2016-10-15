package com.nizar.abdelhedi.adhd;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.neurosky.thinkgear.TGDevice;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FilmActivity extends Activity {
    
    private static final String TAG = "HelloEEG";

    BluetoothAdapter            bluetoothAdapter;
    TGDevice                    tgDevice;
    final boolean               rawEnabled = true;

    TextView                    tv;
    private static final String TAG_ATTENTION = "attention";
    private static final String TAG_BLINK = "blink";
    private static final String TAG_MEDIATION = "meditation";
    private static final String TAG_USERID = "kidId";
    private static final String TAG_TIMESTAMP = "timestamp";
    private static final String TAG_DATAARRAY = "dataTable";
    List<Data> datas=new ArrayList<Data>();
    int lastAttention=100,lastMeditation=100,lastBlink=100;
    VideoView mVideoView;
    Boolean isPaused = false ;
    ImageView focusImage;

		
    /** Called when the activity is first created. */
    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.main );

        findByView();
        checkBluetooth_activerBlink();
        initialise_mVideo();
        //new  GetContacts(this,cvrtListToJson()).execute();

        doStuff(tv);



    } /* end onCreate() */

    private void initialise_mVideo() {
        String uriPath = "android.resource://"+getPackageName()+"/"+R.raw.test;
        Uri uri = Uri.parse(uriPath);
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(HelloEEGActivity.this,"size="+datas.size(),Toast.LENGTH_SHORT).show();
            }
        });
        mVideoView.setVideoURI(uri);
        mVideoView.requestFocus();
        mVideoView.start();
    }

    private JSONObject cvrtListToJson() {
        JSONObject alldata =new JSONObject();
        try {
            alldata.put(TAG_USERID,"580150d133747373318dfa2c");
            alldata.put(TAG_TIMESTAMP,154165465);
            JSONArray datasArrayJson=new JSONArray();
            for(int i=0;i<datas.size();i++){
                JSONObject dataJson=new JSONObject();
                dataJson.put(TAG_ATTENTION,datas.get(i).getAttention());
                dataJson.put(TAG_BLINK,datas.get(i).getBlink());
                dataJson.put(TAG_MEDIATION,datas.get(i).getMediation());
                datasArrayJson.put(dataJson);
            }
            alldata.put(TAG_DATAARRAY,datasArrayJson);
            return  alldata;
        }catch (Exception e){}
        return null;
    }

    private void checkBluetooth_activerBlink() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if( bluetoothAdapter == null ) {

            // Alert user that Bluetooth is not available
            Toast.makeText( this, "Bluetooth not available", Toast.LENGTH_LONG ).show();
            finish();
            return;

        } else {

            // Create the TGDevice
            tgDevice = new TGDevice( bluetoothAdapter, handler );
            tv.append( "Android SDK version: " + tgDevice.getVersion() + "\n" );
        }
    }

    private void findByView() {

        mVideoView = (VideoView)findViewById(R.id.videoview);
        tv=(TextView) findViewById(R.id.textView);
        focusImage=(ImageView) findViewById(R.id.focusImage);
        tv.setText( "" );
        tv.append( "Android version: " + Integer.valueOf(android.os.Build.VERSION.SDK) + "\n" );
        datas.add(new Data(5,6,5));
        datas.add(new Data(5,6,5));

    }

    @Override
    public void onStop() {
        super.onStop();
        tgDevice.close();
    }
    
    @Override
    public void onDestroy() {
    	tgDevice.close();
        super.onDestroy();
    }
    
    final Handler handler = new Handler() {
    	
        @Override
        public void handleMessage( Message msg ) {

            switch( msg.what ) {
                case TGDevice.MSG_STATE_CHANGE:
    
                    switch( msg.arg1 ) {
    	                case TGDevice.STATE_IDLE:
    	                    break;
    	                case TGDevice.STATE_ERR_BT_OFF:
    	                    tv.append( "Bluetooth is off.  Turn on Bluetooth and try again." );
    	                    break;
    	                case TGDevice.STATE_CONNECTING:       	
    	                	tv.append( "Connecting...\n" );
    	                	break;		                    
                        case TGDevice.STATE_ERR_NO_DEVICE:
                            tv.append( "No Bluetooth devices paired.  Pair your device and try again.\n" );
                            break;
    	                case TGDevice.STATE_NOT_FOUND:
    	                	tv.append( "Could not connect any of the paired BT devices.  Turn them on and try again.\n" );
    	                	break;
                        case TGDevice.STATE_CONNECTED:
                            tv.append( "Connected.\n" );
                            tgDevice.start();
                            break;
    	                case TGDevice.STATE_DISCONNECTED:
    	                	tv.append( "Disconnected.\n" );
                    } /* end switch on msg.arg1 */

                    break;
    
                case TGDevice.MSG_POOR_SIGNAL:
              //  	tv.append( "PoorSignal: " + msg.arg1 + "\n" );
                    break;
                
                case TGDevice.MSG_RAW_DATA:
                   // tv.append( "PoorSignal: " + msg.arg1 + "\n" );

                	/* Handle raw EEG/EKG data here */
                	break;
                
                case TGDevice.MSG_HEART_RATE:
            		//tv.append( "Heart rate: " + msg.arg1 + "\n" );
                    break;
                
                case TGDevice.MSG_ATTENTION:
                    lastAttention=msg.arg1;
                    if(lastAttention>60) {
                        isPaused=false;
                        mVideoView.start();
                        focusImage.setVisibility(View.GONE);

                    }
                    datas.add(new Data(lastAttention,lastBlink,lastMeditation));
                    tv.setText( "Attention: " + msg.arg1 + "\n" );
                    Log.d("mmmmmeasure","attention: "+lastAttention);
                    break;
                
                case TGDevice.MSG_MEDITATION:
                    tv.append( "Meditation: " + msg.arg1 + "\n" );
                    lastMeditation=msg.arg1;
                    break;

                case TGDevice.MSG_BLINK:
                	tv.append( "Blink: " + msg.arg1 + "\n" );
                    lastBlink=msg.arg1;
                    break;
    
                default:
                	break;
                	
        	} /* end switch on msg.what */



            if(!isPaused  && lastAttention < 60 ){
                mVideoView.pause();
                isPaused=true ;
                focusImage.setVisibility(View.VISIBLE);
                //tgDevice.sou
            }
        } /* end handleMessage() */


        
    }; /* end Handler */
    
    /**
     * This method is called when the user clicks on the "Connect" button.
     * 
     * @param view
     */
    public void doStuff( View view ) {

    	if( tgDevice.getState() != TGDevice.STATE_CONNECTING && tgDevice.getState() != TGDevice.STATE_CONNECTED ) {
    	    
    		tgDevice.connect( rawEnabled );
    	}
    	
    } /* end doStuff() */
    private class GetContacts extends AsyncTask<Void, Void, String> {

        Context context;
        ProgressDialog pDialog;
        private static final String url = "http://192.168.1.176:3001/day";


        JSONObject json;

        public GetContacts(Context context, JSONObject json){
            this.context=context;this.json=json;}
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
            // Dismiss the progress dialog
            final String result2=result;
            if(result!=null&&result.trim().equals("OK")){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context,result2,Toast.LENGTH_LONG).show();
                        // intent===>>>>>>>>>>>
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