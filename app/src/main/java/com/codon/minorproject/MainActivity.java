package com.codon.minorproject;


        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.text.SpannableString;
        import android.text.style.ForegroundColorSpan;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;
        import android.widget.Toast;
/*Main activity of Inarmjha app which has options for starting and stopping background service,scan devices over network
* and send message to the Inarmjha OS*/
public class MainActivity extends Activity {
Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OnClickListener listener = new OnClickListener() {
            public void onClick(View view) {
                /*Intent is a built in android class used to shift between activities*/
                Intent intent = new Intent(MainActivity.this, MyService.class);
                switch (view.getId()) {
                    case R.id.service_start:
                        //starts service for the given Intent

                        startService(intent);
                        SpannableString spannableString = new SpannableString("Connected to INARMJHA");
                        spannableString.setSpan(
                                new ForegroundColorSpan(getResources().getColor(android.R.color.holo_blue_bright)),
                                0,
                                spannableString.length(),
                                0);
                        Toast.makeText(MainActivity.this, spannableString, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.service_stop:
                        //stops service for the given Intent
                        stopService(intent);
                        SpannableString spannableString1 = new SpannableString("Disconnected from INARMJHA");
                        spannableString1.setSpan(
                                new ForegroundColorSpan(getResources().getColor(android.R.color.holo_blue_bright)),
                                0,
                                spannableString1.length(),
                                0);
                        Toast.makeText(MainActivity.this, spannableString1, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.button2:
                        Intent i=new Intent(MainActivity.this,TCPCommunication.class);
                        startActivity(i);
                        break;

                    case R.id.button6:
                        Intent i1=new Intent(MainActivity.this,WiFiServiceDiscoveryActivity.class);
                        startActivity(i1);
                        SpannableString spannableString2 = new SpannableString("Scanning the network!");
                        spannableString2.setSpan(
                                new ForegroundColorSpan(getResources().getColor(android.R.color.holo_blue_bright)),
                                0,
                                spannableString2.length(),
                                0);
                        Toast.makeText(MainActivity.this, spannableString2, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        findViewById(R.id.service_start).setOnClickListener(listener);
        findViewById(R.id.service_stop).setOnClickListener(listener);
        findViewById(R.id.button2).setOnClickListener(listener);
        findViewById(R.id.button6).setOnClickListener(listener);
    }

}
