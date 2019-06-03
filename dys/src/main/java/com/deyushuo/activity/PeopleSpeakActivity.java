package com.deyushuo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;


import com.android.tu.loadingdialog.LoadingDailog;
import com.deyushuo.adapter.PeopleRecyAdapter;
import com.deyushuo.base.ListItem;
import com.deyushuo.connect.MyDatabaseHelper_People;
import com.dys.deyushuo.R;
import com.deyushuo.connect.getZitate_People;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PeopleSpeakActivity extends Activity {
    private getZitate_People mgp;
    private RecyclerView recyclerView;
    private CardView cardView;
    private MyDatabaseHelper_People myDatabaseHelper_people;
    final List<ListItem> list = new ArrayList<>();
    LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(this);
    //LoadingDailog dialog;
    LoadingDailog dialog2;
    Runnable runnable = new Runnable(){
        @Override
        public void run() {
            /**
             * 要执行的操作
             */
            try {

                mgp = new getZitate_People();
                mgp.getZitate(list,"http://zitate.net");
            } catch (IOException e){
                e.printStackTrace();
            }

            // 执行完毕后给handler发送一个空消息
            handler.sendEmptyMessage(0);
        }
    };


    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {


            super.handleMessage(msg);
            /**
             * 处理UI
             */
            // 当收到消息时就会执行这个方法

            Log.i("In","Erfolg!");
            initDatas();
            Log.i("Aus","Erfolg!");
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        loadBuilder
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dialog2=loadBuilder.create();
        super.onCreate(savedInstanceState);

//        View decorView = getWindow().getDecorView();
//        int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
//        decorView.setSystemUiVisibility(option);


        setContentView(R.layout.peopel_recy_main);

        initView();

        myDatabaseHelper_people = new MyDatabaseHelper_People(this,"PeopleStore.db",null,1);
        myDatabaseHelper_people.getWritableDatabase();
        dialog2.show();
        new Thread(runnable).start();
        initDatas();

    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recy);
    }

    private void initDatas() {

        recyclerView.setLayoutManager(new LinearLayoutManager((PeopleSpeakActivity.this)));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        PeopleRecyAdapter peopleRecyAdapter = new PeopleRecyAdapter(this,list);
        recyclerView.setAdapter(peopleRecyAdapter);

        peopleRecyAdapter.setOnItemClickListener(new PeopleRecyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i("position:",position+"");

                String titel = list.get(position).getTitel();
                String date = list.get(position).getDate();
                String link = list.get(position).getLink();


                Intent intent = new Intent(PeopleSpeakActivity.this, PeopleReaderActivity.class);
                intent.putExtra("titel",titel);
                intent.putExtra("date",date);
                intent.putExtra("link",link);

                startActivity(intent);
            }
        });
        dialog2.hide();
    }

    public void refreshPage(View v) {
        dialog2.show();
        new Thread(runnable).start();
        initDatas();
    }

    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context,R.anim.layout_animation_fall_down);
        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();

    }

    public void backToMenu(View v){
        Intent intent = new Intent(PeopleSpeakActivity.this,CircleActivity.class);
        startActivity(intent);

    }

    public void collect_People(View v) {

    }


}
