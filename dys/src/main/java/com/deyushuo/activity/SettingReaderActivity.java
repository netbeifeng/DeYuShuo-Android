package com.deyushuo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.dys.deyushuo.R;

public class SettingReaderActivity extends Activity {
    private TextView tv ;
    private TextView tv_con ;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.setting_reader_main);

            tv = (TextView) findViewById(R.id.setting_titel);
            tv_con = (TextView) findViewById(R.id.setting_context);
            Intent intent =getIntent();

            String titel = intent.getStringExtra("titel");
            String context = intent.getStringExtra("context");

            tv.setText(titel);
            tv_con.setText(context);
        }
    }

