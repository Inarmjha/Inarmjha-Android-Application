package com.codon.minorproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/*This activity is used to show the received notification from the operating system and provides an option to close the app or to reply to the inarmjha*/
public class ViewImage extends Activity {
private Button send,ignore;
    private EditText inform;
    private Socket client2;
    public String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        //inform=(EditText)findViewById(R.id.editText2);
        ignore=(Button)findViewById(R.id.button5);
        ignore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        send=(Button)findViewById(R.id.button3);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent p=new Intent(ViewImage.this,TCPCommunication.class);
                startActivity(p);
            }
        });


        Log.d("a11","a11");
        Bundle ex = getIntent().getExtras();
        Log.d("b11","b11");
        Intent intent = getIntent();
        Log.d("c11","c11");
        //Bitmap bitmap = (Bitmap) intent.getParcelableExtra("bitmapbytes");
        byte[] bytes = getIntent().getByteArrayExtra("bitmapbytes");
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        Log.d("d11","d11");
        ImageView result = (ImageView)findViewById(R.id.imageView);
        Log.d("e11","e11");
        result.setImageBitmap(bmp);
      /*  send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            int portNumber = 7002;
                            String serverName = "192.168.0.9";

                            try {
                                client2 = new Socket(serverName, portNumber);
                                System.out.println("Connecting to " + serverName + " on port " + portNumber);

                                BufferedReader br = new BufferedReader(new InputStreamReader(client2.getInputStream()));

                                PrintWriter pw = new PrintWriter(client2.getOutputStream(), true);
                                pw.println(inform.getText().toString());

Log.d("11111111111111111111111","1111111111111111111111111111111111111");
                                while ((str = br.readLine()) != null) {
                                    Log.d("22222222222222222222222","222222222222222222222");
                                    System.out.println(str);
                                    showToast(str);

                                    Log.d("333333333333333","33333333333333333333");



                                    pw.println("bye");

                                    if (str.equals("bye"))
                                        break;
                                }

                                br.close();
                                pw.close();
                                client2.close();
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


        });*/
Log.d("f11","f11");

    }

    private void showToast(String str) {
        Toast.makeText(this,str,Toast.LENGTH_LONG).show();

    }
}
