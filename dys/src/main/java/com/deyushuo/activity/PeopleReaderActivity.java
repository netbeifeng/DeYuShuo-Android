package com.deyushuo.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.deyushuo.connect.MyDatabaseHelper_People;
import com.deyushuo.connect.getZitate_People;
import com.dys.deyushuo.R;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PeopleReaderActivity extends Activity {
    private MyDatabaseHelper_People myDatabaseHelper_people;
    private getZitate_People getZitate_people;
    private TextView titel_tv;
    private TextView source_tv;
    private TextView tran;
   // private String temp;
//    Runnable run = new Runnable() {
//        @Override
//        public void run()  {
//            getZitate_people = new getZitate_People();
//            try {
//                tran.setText(getZitate_people.getTran(temp));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.people_reader_main);
        titel_tv =(TextView) findViewById(R.id.a_title);
        tran = (TextView) findViewById(R.id.translate);
        source_tv= (TextView) findViewById(R.id.a_source);
        Intent intent =getIntent();
        myDatabaseHelper_people = new MyDatabaseHelper_People(this,"PeopleStore.db",null,1);
        String titel = intent.getStringExtra("titel");
        String source = intent.getStringExtra("date");
        //temp = titel;
        //new Thread(run).start();
        titel_tv.setText(titel);
        source_tv.setText(source);

    }

    public void endActivtiy_P(View v){
        finish();
    }

    public void toLink_P(View v){
        Uri uri = Uri.parse("https://www.google.com/");
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }

    public void collect_People(View v) {
        SQLiteDatabase db = myDatabaseHelper_people.getWritableDatabase();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        db.execSQL("insert into " + "PEOPLE" + " (titel, source,time) values ('"+titel_tv.getText()+"','"+source_tv.getText()+"','"+dateFormat.format(date)+"')");
        Toast.makeText(this,"收藏成功~",Toast.LENGTH_LONG).show();
    }
}
