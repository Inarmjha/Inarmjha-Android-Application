package com.codon.minorproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity {
private Button ignore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ignore=(Button)findViewById(R.id.button4);
        ignore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               
            }
        });
    }
}
