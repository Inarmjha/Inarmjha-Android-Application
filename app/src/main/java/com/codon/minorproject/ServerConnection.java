package com.codon.minorproject;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;

public class ServerConnection extends Activity {

    private Button b1;
    private EditText e1;
   // private TextView t1;
    protected static final int RESULT_SPEECH = 1;

    private ImageButton btnSpeak;
    private TextView txtText;

   // private PrintWriter printwriter;
    ObjectOutputStream oos = null;
    ObjectInputStream ois = null;
    private String message;
    private Socket client;
    public String str;

    private PrintWriter printwriter;
    private BufferedReader inFromUser;
    public  BufferedReader bufferedReader;
    Queue<String> myQueue = new LinkedList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_connection);





        b1=(Button)findViewById(R.id.button);

        e1=(EditText)findViewById(R.id.editText);

       // Intent serviceIntent = new Intent(this, NotifyService.class);
        //startService(serviceIntent);
        txtText = (TextView) findViewById(R.id.txtText);

        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(
                        RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

                try {
                    startActivityForResult(intent, RESULT_SPEECH);
                    txtText.setText("");
                } catch (ActivityNotFoundException a) {
                    Toast t = Toast.makeText(getApplicationContext(),
                            "Opps! Your device doesn't support Speech to Text",
                            Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            int portNumber = 7002;
                            String serverName = "192.168.0.9";

                            try {
                                client = new Socket(serverName, portNumber);
                                System.out.println("Connecting to " + serverName + " on port " + portNumber);

                                BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));

                                PrintWriter pw = new PrintWriter(client.getOutputStream(), true);
                                pw.println(e1.getText().toString());


                                while ((str = br.readLine()) != null) {
                                    System.out.println(str);



                                    pw.println("bye");

                                    if (str.equals("bye"))
                                        break;
                                }

                                br.close();
                                pw.close();
                                client.close();
                            } catch (IOException e) {
                                Log.d("msg30", "message30");
                                e.printStackTrace();
                            } catch (Exception e) {
                                Log.d("msg40", "message40");
                                e.printStackTrace();
                            }


                        }catch (Exception j){}}});
                thread.start();
            }
            }
            );


    }


        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SPEECH: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    txtText.setText(text.get(0));
                }
                break;
            }

        }
    }}
