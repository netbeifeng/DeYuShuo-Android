package com.deyushuo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dys.deyushuo.R;

public class LifeReaderActivtiy_B2 extends Activity {
    private TextView context_tv;
    private TextView titel_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.life_reader_main_b2);
        titel_tv =(TextView) findViewById(R.id.life_title_b2);
        context_tv =(TextView) findViewById(R.id.life_context_b2);

        Intent intent =getIntent();

        String titel = intent.getStringExtra("titel");
        String date = intent.getStringExtra("date");

        titel_tv.setText(titel);
        context_tv.setText(date);
    }

    public void endActivtiy_L_b2(View v){
        finish();
    }
    public void toLink_L_b2(View v){
        Intent textIntent = new Intent(Intent.ACTION_SEND);
        textIntent.setType("text/plain");
        textIntent.putExtra(Intent.EXTRA_TEXT, "Test");
        startActivity(Intent.createChooser(textIntent, "分享给你的好友"));
    }
}
