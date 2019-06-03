package com.deyushuo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.deyushuo.connect.getPepaWurtz;
import com.dys.deyushuo.R;

import java.io.IOException;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class LifeVideoerActivity extends Activity {
    private TextView date_tv ;
    private TextView titel_tv;
    private TextView subtitel_tv;
    private String titel;
    private String link;

    private  String imgLink;
    private String videoURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.life_videoer_main);
        date_tv = (TextView) findViewById(R.id.life_date_v);
        titel_tv = (TextView) findViewById(R.id.life_title_v);
        subtitel_tv = (TextView) findViewById(R.id.life_sub_titel_v);

        Intent intent =getIntent();
        imgLink = intent.getStringExtra("imglink");
        titel = intent.getStringExtra("titel");
        link = intent.getStringExtra("link");
        String date = intent.getStringExtra("date");

        titel_tv.setText(titel);
        date_tv.setText(date);
        subtitel_tv.setText("《粉红猪小妹》（英语：Peppa Pig）是一套英国学前电视动画系列，Astley Baker Davis创作、导演和制作，并由E1 kids发行。2004年5月31日首播，共播出4季，暂停2年后，于2015年2月14日再次播出新一季。本动画共于180个地区播放。本动画获得2005年英国电影和电视艺术学院最佳学前动画奖和2005年安锡国际动画影展最佳电视动画奖。另外，主角佩佩的配音员哈莉·贝特获得2011年英国电影和电视艺术学院最佳儿童演出奖。");


        if(isNetworkAvailable(this)){
            new Thread(runnable).start();

            Log.e("aaa","!!!!!!!!!!");
        } else {
            Toast.makeText(this,"请检查网络",Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    public void endActivtiy_L(View v){
        finish();
    }

    public void share (View v){

            Intent textIntent = new Intent(Intent.ACTION_SEND);
            textIntent.setType("text/plain");
            textIntent.putExtra(Intent.EXTRA_TEXT, titel+":"+link);
            startActivity(Intent.createChooser(textIntent, "分享给你的好友"));

    }

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

    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {


            super.handleMessage(msg);
            /**
             * 处理UI
             */
            // 当收到消息时就会执行这个方法
            JzvdStd jzvdStd = (JzvdStd) findViewById(R.id.jc_video_player_standard);
            jzvdStd.setUp(videoURL
                    , titel , Jzvd.SCREEN_WINDOW_NORMAL);
            //jzvdStd.thumbImageView.setImageURI(Uri.parse("https://static.dedic.cn/VideoPool/f3c598fe-245a-11e9-88bd-00505686c5e6/index.png?stamp=1549192273454"));
            Glide.with(LifeVideoerActivity.this)
                    .load(imgLink)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(jzvdStd.thumbImageView);

        }
    };

    Runnable runnable = new Runnable(){
        @Override
        public void run() {
            /**
             * 要执行的操作
             */
            try {
                getPepaWurtz getPepaWurtzurtz = new getPepaWurtz();
                videoURL = getPepaWurtzurtz.getVideoURL(link);

            } catch (IOException e){
                e.printStackTrace();
            }

            // 执行完毕后给handler发送一个空消息
            handler.sendEmptyMessage(0);
        }
    };
}
