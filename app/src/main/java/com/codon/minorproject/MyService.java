package com.codon.minorproject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.v4.app.NotificationCompat;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.Socket;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import static com.codon.minorproject.R.id.image;

public class MyService extends Service {

    /* Background service which listens to os and pop ups a notification in notification bar whenever os send a message to the app*/
    private static final String TAG = "MyService";
    private boolean isRunning  = false;
    private Looper looper;
    private Socket client1;
    private MyServiceHandler myServiceHandler;
    private static final long UPDATE_INTERVAL = 1000*60*60*24;
    /* our service runs upto above mention time , we have to restart the service frequently*/

    private Timer timer = new Timer();

    @Override
    public void onCreate() {

        /*create a thread which runs in background thread not on main thread*/
        HandlerThread handlerthread = new HandlerThread("MyThread", Process.THREAD_PRIORITY_BACKGROUND);
        handlerthread.start();

        looper = handlerthread.getLooper();

        myServiceHandler = new MyServiceHandler(looper);
        isRunning = true;
        // we shedule task "showNotification" to run everyday.
        timer.scheduleAtFixedRate(
                new TimerTask() {
                    public void run() {
                        try {
                            int portNumber = 7004;
                            String serverName = "192.168.0.102";
                            String str;
                            try {
                                while (true) {
                                    client1 = new Socket(serverName, portNumber);
                                    System.out.println("Connecting to " + serverName + " on port " + portNumber);

                                    InputStream in=client1.getInputStream();
                                     while(in == null) {};
                                    BufferedReader br=new BufferedReader(new InputStreamReader((in)));
                                    StringBuilder sb=new StringBuilder();

                                    String line;
                                    while (((line=br.readLine())!=null)){sb.append(line);}
                                    JSONObject json=new JSONObject(sb.toString());
                                    //String leaf_name=json.getString("id");
                                    String mat_string=json.getString("img");
                                  //FileOutputStream byteBuffer = new FileOutputStream();

                          /*our image is transfered to app in Base64 Encoded format,we have to decode it*/

                                    byte[] raw_data = Base64.decode(mat_string, Base64.DEFAULT);
                                    Bitmap img = BitmapFactory.decodeByteArray(raw_data, 0, raw_data.length);
                                   // img.compress(Bitmap.CompressFormat.PNG, 100, byteBuffer);


                                    System.out.println(img);


                                    // imageView.setImageBitmap(bitmap);

                                    Log.d("xx", "xx");
                                    //System.out.println(client1.getInputStream());
                                    //InputStream in=client1.getInputStream();
                                    //Bitmap bitmap = BitmapFactory.decodeStream(in);
                                    Log.d("xx1", "xx1");
                                    //System.out.println(bitmap);
                                    Log.d("xx2", "xx2");

                                    showNotification(img);

                                    Log.d("xx3", "xx3");

                                    //   pw.println("bye");

                                    //  if (str.equals("bye"))
                                    //    break;



                                    br.close();
                                    // pw.close();
                                    client1.close();
                                }
                            } catch (IOException e) {
                                Log.d("msg30", "message30");
                                e.printStackTrace();
                            } catch (Exception e) {
                                Log.d("msg40", "message40");
                                e.printStackTrace();
                            }


                        }catch(Exception t){}
                    }
                },
                0,
                UPDATE_INTERVAL);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        //If service is killed while starting, it restarts.*/
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {

    }
    private final class MyServiceHandler extends Handler {
        public MyServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            synchronized (this) {
                for (int i = 0; i < 10; i++) {
                    try {
                        Log.i(TAG, "MyService running...");
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        Log.i(TAG, e.getMessage());
                    }
                    if(!isRunning){
                        break;
                    }
                }
            }
            //stops the service for the start id.
            stopSelfResult(msg.arg1);
        }
    }
    public void showNotification(Bitmap bitmap) {


        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.inarmja)
                        .setContentTitle("INARMJA")
                        .setContentText("Do u know this person");
        Intent notificationIntent = new Intent(this, ViewImage.class);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bytes = stream.toByteArray();
        notificationIntent.putExtra("bitmapbytes",bytes);



        Log.d("11","11");
       // notificationIntent.putExtra("image",bitmap);
        Log.d("22","22");
        //System.out.println(bitmap);
        Log.d("33","33");

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());





    }

}

