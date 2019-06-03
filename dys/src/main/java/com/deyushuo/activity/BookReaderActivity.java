package com.deyushuo.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.dys.deyushuo.R;
import com.deyushuo.connect.MyDatabaseHelper_Book;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BookReaderActivity extends Activity {
    private MyDatabaseHelper_Book myDatabaseHelper_book;
    private TextView titel_tv;
    private TextView context_tv;
    String temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_reader_main);
        titel_tv =(TextView) findViewById(R.id.a_title);
        context_tv= (TextView) findViewById(R.id.a_source);
        Intent intent =getIntent();
        myDatabaseHelper_book = new MyDatabaseHelper_Book(this,"BookStore.db",null,1);
        String titel = intent.getStringExtra("titel");
        String date = intent.getStringExtra("date");
        temp =date;
        titel_tv.setText(Html.fromHtml(titel));
        context_tv.setText(date);

        context_tv.setText(Html.fromHtml(date, new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {

                int id = Integer.parseInt(source);
                Drawable drawable = getResources().getDrawable(id, null);
                int w = drawable.getIntrinsicWidth();
                int h = drawable.getIntrinsicHeight();
                //对图片大小进行等比例放大 此处宽高可自行调整
                if (w < h && h > 0) {
                    float scale = (1580.0f / h);
                    w = (int) (scale * w);
                    h = (int) (scale * h);
                } else if (w > h && w > 0) {
                    float scale = (3300.0f / w);
                    w = (int) (scale * w);
                    h = (int) (scale * h);
                }

                drawable.setBounds(0, 0, w, h);
//                drawable.setBounds(0, 0, drawable.getIntrinsicWidth() ,
//                        drawable.getIntrinsicHeight());

                return drawable;
            }
        }, null));


    }

    public void endActivtiy_B(View v){
        finish();
    }
    public void toLink_B(View v){
        Intent textIntent = new Intent(Intent.ACTION_SEND);
        textIntent.setType("text/plain");
        textIntent.putExtra(Intent.EXTRA_TEXT, "deyushuo~~");
        startActivity(Intent.createChooser(textIntent, "分享给你的好友"));
    }

    public void collect_Book(View v){
        SQLiteDatabase db = myDatabaseHelper_book.getWritableDatabase();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        db.execSQL("insert into " + "Book" + " (titel, context,time) values ('"+titel_tv.getText()+"','"+temp.replaceAll("'","\"")+"','"+dateFormat.format(date)+"')");
        Log.e("SUC","INSERT BOOK/");
        Toast.makeText(this,"收藏成功~",Toast.LENGTH_LONG).show();
    }
}
