package com.deyushuo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.deyushuo.base.Nachrichtenleicht_Artikel_Item;
import com.deyushuo.connect.getLife_LeichtNachrichten;
import com.dys.deyushuo.R;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.deyushuo.connect.MyDatabaseHelper_Life;
public class LifeReaderActivity extends Activity {
    private MyDatabaseHelper_Life myDatabaseHelper_life;
    private TextView titel_tv;
    private TextView date_tv;
    private TextView sub_Titel_tv;
    private TextView context_tv;
    private TextView bedeutung_tv;
    private String link;
    private Nachrichtenleicht_Artikel_Item detail;

    private String audio_Link;
    private ImageButton audio_bt;
    final MediaPlayer mediaPlayer = new MediaPlayer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.life_reader_main);

        titel_tv =(TextView) findViewById(R.id.life_title);
        date_tv = (TextView) findViewById(R.id.life_date);
        sub_Titel_tv = (TextView) findViewById(R.id.life_sub_titel);
        context_tv =(TextView) findViewById(R.id.life_context);
        bedeutung_tv = (TextView) findViewById(R.id.bedeutung);
        audio_bt = (ImageButton) findViewById(R.id.audio_link_life);
        myDatabaseHelper_life = new MyDatabaseHelper_Life(this,"LifeStore.db",null,1);

        Intent intent =getIntent();
        link = intent.getStringExtra("link");
        String titel = intent.getStringExtra("titel");
        String date = intent.getStringExtra("date");

        titel_tv.setText(titel);
        date_tv.setText(date);

        if(isNetworkAvailable(this)){
            new Thread(runnable).start();

            Log.e("aaa","!!!!!!!!!!");
        } else {
            Toast.makeText(this,"请检查网络",Toast.LENGTH_LONG).show();
        }




    }

    public void endActivtiy_L(View v){
        finish();
    }
    public void toLink_L(View v){
        Intent textIntent = new Intent(Intent.ACTION_SEND);
        textIntent.setType("text/plain");
        textIntent.putExtra(Intent.EXTRA_TEXT, "文字版: https://www.nachrichtenleicht.de/"+link+"\n音频: "+audio_Link);
        startActivity(Intent.createChooser(textIntent, "分享这篇新闻给你的好友"));
    }



    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {


            super.handleMessage(msg);
            /**
             * 处理UI
             */
            // 当收到消息时就会执行这个方法
            sub_Titel_tv.setText(detail.getSub_Titel());
            context_tv.setText(detail.getMain_Context());
            List<Map<String,String>> mapList = detail.getDiscrption();
            audio_Link = detail.getAudio_Link();
            String bedeutungen =  "";
            for(int i=0; i < mapList.size(); i++){
                bedeutungen = bedeutungen + ("<font color='#006064'; size:150%><big>"+ mapList.get(i).keySet()+"</big></font> <br><font size:200%>"+ mapList.get(i).values())+"</font><br><br>";
            }
            bedeutung_tv.setText(Html.fromHtml(bedeutungen));

            mediaPlayer_init();

        }
    };

    Runnable runnable = new Runnable(){
        @Override
        public void run() {
            /**
             * 要执行的操作
             */
            try {

                getLife_LeichtNachrichten getLifeLeichtNachrichten = new getLife_LeichtNachrichten();
                detail = getLifeLeichtNachrichten.getDetails(link);
            } catch (IOException e){
                e.printStackTrace();
            }

            // 执行完毕后给handler发送一个空消息
            handler.sendEmptyMessage(0);
        }
    };

    public boolean isNetworkAvailable(Activity activity)
    {
        Context context = activity.getApplicationContext();
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null)
            return false;
        else
        {   // 获取所有NetworkInfo对象
            NetworkInfo[] networkInfo = cm.getAllNetworkInfo();
            if (networkInfo != null && networkInfo.length > 0)
            {
                for (int i = 0; i < networkInfo.length; i++)
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;  // 存在可用的网络连接
            }
        }
        return false;
    }

    public void play_Sound_life(View v){
        if((int)audio_bt.getTag()==R.drawable.play) {
            mediaPlayer.start();
            audio_bt.setBackgroundResource(R.drawable.pause);
            audio_bt.setTag(R.drawable.pause);
        }

        else if((int)audio_bt.getTag()==R.drawable.pause){
            mediaPlayer.pause();
            audio_bt.setBackgroundResource(R.drawable.play);
            audio_bt.setTag(R.drawable.play);
        }

    }

    public void mediaPlayer_init(){
        try {
            mediaPlayer.setDataSource(audio_Link);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    // 4 开始播放
                    audio_bt.setBackgroundResource(R.drawable.play);
                    audio_bt.setTag(R.drawable.play);
                }
            });
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void collect_Life(View v) {
        SQLiteDatabase db = myDatabaseHelper_life.getWritableDatabase();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
       db.execSQL("insert into " + "LIFE" + " (titel,link,date,time) values ('"+titel_tv.getText()+"','"+link+"','"+date_tv.getText()+"','"+dateFormat.format(date)+"')");
        Toast.makeText(this,"收藏成功~",Toast.LENGTH_LONG).show();
    }
}
