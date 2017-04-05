package com.codon.minorproject;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;



import static com.codon.minorproject.EnumsAndStatics.MessageTypes.MessageFromServer;

public class TCPCommunication extends Activity implements TCPListener{
    /*Used to establish connection between app and server,communication is done using sockts.Json objects are transferred between used and os*/

    private TCPCommunicator tcpClient;
    private ProgressDialog dialog;
    public static String currentUserName;
    private Handler UIHandler = new Handler();
    private boolean isFirstLoad=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcpcommunication);

        ConnectToServer();
      //  Toast.makeText(getApplicationContext(), "Connected to INARMJA", Toast.LENGTH_SHORT).show();
    }

    private void ConnectToServer() {
        /*method to connect to the server using TCP protocol over loacl area network*/
       // setupDialog();
        tcpClient = TCPCommunicator.getInstance();
        TCPCommunicator.addListener(this);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        tcpClient.init(settings.getString(EnumsAndStatics.SERVER_IP_PREF, "192.168.0.5"),
                Integer.parseInt(settings.getString(EnumsAndStatics.SERVER_PORT_PREF, "7002")));
        Log.d("SERVER_IP_PREF","SERVER_IP_PREF");
        Log.d("SERVER_PORT_PREF","SERVER_PORT_PREF");
    }



    private void setupDialog() {
        dialog = new ProgressDialog(this,ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Loading");
        dialog.setMessage("Please wait...");
        dialog.setIndeterminate(true);
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity3, menu);
        return true;
    }

    @Override
    protected void onStop()
    {
        super.onStop();

    }
    @Override
    protected void onResume()
    {
        super.onResume();
        setContentView(R.layout.activity_tcpcommunication);
        if(!isFirstLoad)
        {
            TCPCommunicator.closeStreams();
            ConnectToServer();
        }
        else
            isFirstLoad=false;
    }
    public void btnSendClick(View view)
    {Log.d("anc","anc");
       ConnectToServer();
        Log.d("123123","123123");
        JSONObject obj = new JSONObject();
        EditText txtName= (EditText)findViewById(R.id.txtUserName);
        if(txtName.getText().toString().length()==0)
        {
            Toast.makeText(this, "Please enter text", Toast.LENGTH_SHORT).show();
            return;
        }
        try
        {
            obj.put(EnumsAndStatics.MESSAGE_TYPE_FOR_JSON, EnumsAndStatics.MessageTypes.MessageFromClient);
            obj.put(EnumsAndStatics.MESSAGE_CONTENT_FOR_JSON, txtName.getText().toString());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        TCPCommunicator.writeToSocket(obj,UIHandler,this);
        //dialog.show();

    }

    @Override
    public void onTCPMessageRecieved(String message) {
        // TODO Auto-generated method stub
        final String theMessage=message;
        try {
             /*received json object is converted to string and displayed directly to user*/
            JSONObject obj = new JSONObject(message);
            String messageTypeString=obj.getString(EnumsAndStatics.MESSAGE_TYPE_FOR_JSON);
            Log.d("messageTypeString","messageTypeString");
            System.out.println(obj);
            EnumsAndStatics.MessageTypes messageType = EnumsAndStatics.getMessageTypeByString(messageTypeString);

            switch(messageType)
            {

                case MessageFromServer:
                {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            EditText editTextFromServer =(EditText)findViewById(R.id.editTextFromServer);
                            Log.d("hiiee","hiiee");
                            System.out.println(theMessage);
                            editTextFromServer.setText(theMessage);
                            TCPCommunicator.closeStreams();
                        }
                    });

                    break;
                }


            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void onTCPConnectionStatusChanged(boolean isConnectedNow) {
        // TODO Auto-generated method stub
        if(isConnectedNow)
        {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
//                    dialog.hide();
                 //   Toast.makeText(getApplicationContext(), "Connected to INARMJA", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
    public void btnSettingsClicked(View view)
    {
        Intent intent = new Intent(this,SettingsActivity.class);
        startActivity(intent);
    }

}

