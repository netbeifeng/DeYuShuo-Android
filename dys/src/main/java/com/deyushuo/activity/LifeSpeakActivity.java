package com.deyushuo.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import android.widget.Toast;


import com.android.tu.loadingdialog.LoadingDailog;

import com.deyushuo.adapter.LifeRecyAdapter;
import com.deyushuo.base.ListItem;
import com.dys.deyushuo.R;
import com.deyushuo.connect.getLife_LeichtNachrichten;
import com.deyushuo.connect.getPepaWurtz;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.deyushuo.connect.MyDatabaseHelper_Life;

public class LifeSpeakActivity extends Activity {
    private MyDatabaseHelper_Life myDatabaseHelper_life;
    private RecyclerView recyclerView;

    final List<ListItem> list = new ArrayList<>();
    //final List<ListItem> list_pre_page = new ArrayList<>();
    LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(this);
    //LoadingDailog dialog;
    LoadingDailog dialog2;
    String cur_Page = "https://www.nachrichtenleicht.de/nachrichten.2005.de.html";
    int cur_Page_Num = 1;
    boolean isNews = true;
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


        setContentView(R.layout.life_recy_main);

        initView();
        myDatabaseHelper_life = new MyDatabaseHelper_Life(this,"LifeStore.db",null,1);
        myDatabaseHelper_life.getWritableDatabase();


        dialog2.show();
        if(isNetworkAvailable(this)){
            new Thread(runnable_Nachricht).start();

            Log.e("aaa","!!!!!!!!!!");
        } else {
            Toast.makeText(this,"请检查网络",Toast.LENGTH_LONG).show();
        }

    }

    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {


            super.handleMessage(msg);
            /**
             * 处理UI
             */
            // 当收到消息时就会执行这个方法
            dialog2.hide();
            Log.i("In","Erfolg!");
            initDatas();
            Log.i("Aus","Erfolg!");
        }
    };

    Runnable runnable_pepa = new Runnable() {
        @Override
        public void run() {
            try {
                isNews = false;
                getPepaWurtz getPepaWurtz = new getPepaWurtz();
                getPepaWurtz.getList(list);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            handler.sendEmptyMessage(0);
        }
    };


    Runnable runnable_Nachricht = new Runnable(){
        @Override
        public void run() {
            /**
             * 要执行的操作
             */
            try {
            isNews = true;
             getLife_LeichtNachrichten getLifeLeichtNachrichten = new getLife_LeichtNachrichten();
             getLifeLeichtNachrichten.getNew(list,"https://www.nachrichtenleicht.de/nachrichten.2005.de.html");

            } catch (IOException e){
                e.printStackTrace();
            }

            // 执行完毕后给handler发送一个空消息
            handler.sendEmptyMessage(0);
        }
    };

    Runnable runnable_Sport = new Runnable(){
        @Override
        public void run() {
            /**
             * 要执行的操作
             */
            try {
                isNews = true;
                getLife_LeichtNachrichten getLifeLeichtNachrichten = new getLife_LeichtNachrichten();
                getLifeLeichtNachrichten.getNew(list,"https://www.nachrichtenleicht.de/sport.2004.de.html");
                cur_Page = "https://www.nachrichtenleicht.de/sport.2004.de.html";
            } catch (IOException e){
                e.printStackTrace();
            }

            // 执行完毕后给handler发送一个空消息
            handler.sendEmptyMessage(0);
        }
    };

    Runnable runnable_Kultur = new Runnable(){
        @Override
        public void run() {
            /**
             * 要执行的操作
             */
            try {
                isNews = true;
                getLife_LeichtNachrichten getLifeLeichtNachrichten = new getLife_LeichtNachrichten();
                getLifeLeichtNachrichten.getNew(list,"https://www.nachrichtenleicht.de/kultur.2006.de.html");
                cur_Page = "https://www.nachrichtenleicht.de/kultur.2006.de.html";
            } catch (IOException e){
                e.printStackTrace();
            }

            // 执行完毕后给handler发送一个空消息
            handler.sendEmptyMessage(0);
        }
    };

    Runnable runnable_Vermischtes = new Runnable(){
        @Override
        public void run() {
            /**
             * 要执行的操作
             */
            try {
                isNews = true;
                getLife_LeichtNachrichten getLifeLeichtNachrichten = new getLife_LeichtNachrichten();
                getLifeLeichtNachrichten.getNew(list,"https://www.nachrichtenleicht.de/vermischtes.2007.de.html");
                cur_Page = "https://www.nachrichtenleicht.de/vermischtes.2007.de.html";
            } catch (IOException e){
                e.printStackTrace();
            }

            // 执行完毕后给handler发送一个空消息
            handler.sendEmptyMessage(0);
        }
    };

    Runnable runnable_More = new Runnable(){
        @Override
        public void run() {
            /**
             * 要执行的操作
             */
            try {
                dialog2.show();
                getLife_LeichtNachrichten getLifeLeichtNachrichten = new getLife_LeichtNachrichten();
                getLifeLeichtNachrichten.getMorePages(list,cur_Page,cur_Page_Num);

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

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recy);
    }

    private void initDatas() {



        recyclerView.setLayoutManager(new LinearLayoutManager((LifeSpeakActivity.this)));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        LifeRecyAdapter lifeRecyAdapter = new LifeRecyAdapter(this,list);
        recyclerView.setAdapter(lifeRecyAdapter);

        lifeRecyAdapter.setOnItemClickListener(new LifeRecyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i("position:",position+"");
                if(isNews) {
                    String titel = list.get(position).getTitel();
                    String date = list.get(position).getDate();
                    String link = list.get(position).getLink();

                    Intent intent = new Intent(LifeSpeakActivity.this, LifeReaderActivity.class);
                    intent.putExtra("titel",titel);
                    intent.putExtra("date",date);
                    intent.putExtra("link",link);

                    startActivity(intent);
                } else {
                    String titel = list.get(position).getTitel();
                    String date = list.get(position).getDate();
                    String link = list.get(position).getLink();
                    String imgLink = list.get(position).getImageLink();

                    Intent intent = new Intent(LifeSpeakActivity.this, LifeVideoerActivity.class);
                    intent.putExtra("titel",titel);
                    intent.putExtra("date",date);
                    intent.putExtra("imglink",imgLink);
                    intent.putExtra("link",link);
                    startActivity(intent);
                }

            }
        });
    }

    public void biggg(View v) {
        TextView title = (TextView) findViewById(R.id.title);
        TextView source = (TextView) findViewById(R.id.source);
        title.setText("成功！");
        source.setText("每日德语听力");
    }


    public void next_Page(View v){
        Log.i("1","FFFFFFF");

        cur_Page_Num++;
        dialog2.show();
        list.clear();

        new Thread(runnable_More).start();
//        recyclerView.setLayoutManager(new LinearLayoutManager((LifeSpeakActivity.this)));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        LifeRecyAdapter lifeRecyAdapter = new LifeRecyAdapter(this,list);
//        recyclerView.setAdapter(lifeRecyAdapter);
//        runLayoutAnimation(recyclerView);
//
//
//        lifeRecyAdapter.setOnItemClickListener(new LifeRecyAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                Log.i("position:",position+"");
//
//                String titel = list.get(position).getTitel();
//                String date = list.get(position).getDate();
//
//                Intent intent = new Intent(LifeSpeakActivity.this, LifeReaderActivity.class);
//                intent.putExtra("titel",titel);
//                intent.putExtra("date",date);
//
//                startActivity(intent);
//            }
//        });

    }



    public void pre_Page(View v) {
        if(cur_Page_Num<=1) {
            Toast.makeText(LifeSpeakActivity.this,"到底了哦！",Toast.LENGTH_LONG).show();
        } else {
            cur_Page_Num--;
            dialog2.show();
            list.clear();

            new Thread(runnable_More).start();
//            recyclerView.setLayoutManager(new LinearLayoutManager((LifeSpeakActivity.this)));
//            recyclerView.setItemAnimator(new DefaultItemAnimator());
//            LifeRecyAdapter lifeRecyAdapter = new LifeRecyAdapter(this,list_pre_page);
//            recyclerView.setAdapter(lifeRecyAdapter);
//            runLayoutAnimation(recyclerView);
//            lifeRecyAdapter.setOnItemClickListener(new LifeRecyAdapter.OnItemClickListener() {
//                @Override
//                public void onItemClick(View view, int position) {
//                    Log.i("position:",position+"");
//
//                    String titel = list_pre_page.get(position).getTitel();
//                    String date = list_pre_page.get(position).getDate();
//
//                    Intent intent = new Intent(LifeSpeakActivity.this, LifeReaderActivity.class);
//                    intent.putExtra("titel",titel);
//                    intent.putExtra("date",date);
//
//                    startActivity(intent);
//                }
//            });
        }

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
        Intent intent = new Intent(LifeSpeakActivity.this,CircleActivity.class);
        startActivity(intent);

    }

    public void changeThema(View v){
        final Context mContext = this;
        final String[] menuItems = new String[]{"A1-A2","B1-B2"};

        dialog2=loadBuilder.create();
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setItems(menuItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, final int i) {
                Toast.makeText(mContext,menuItems[i],Toast.LENGTH_SHORT).show();

                final String[] mItems2 = new String[]{"Nachrichten","Kultur","Vermischtes","Sport","Pepa Wutz","短语"};
                final String[] mItems3 = new String[]{"Top-Thema","Sprachbar","Langsam gesprochene Nachrichten","Wort der Woche","Video-Thema","Alltagsdeutsch"};

                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(mContext);
                if(i==0)
                    alertDialog2.setItems(mItems2, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(mContext,mItems2[which],Toast.LENGTH_SHORT).show();

                            if(isNetworkAvailable(LifeSpeakActivity.this)){

                                switch (which) {
                                    case 0 : new Thread(runnable_Nachricht).start(); dialog2.show();break;
                                    case 1 : new Thread(runnable_Kultur).start(); dialog2.show();break;
                                    case 2 : new Thread(runnable_Vermischtes).start(); dialog2.show();break;
                                    case 3 : new Thread(runnable_Sport).start(); dialog2.show(); break;
                                    case 4 : new Thread(runnable_pepa).start(); dialog2.show(); break;
                                    case 5 : {Intent intent = new Intent(LifeSpeakActivity.this,LifeSpeakActivity_B2.class);intent.putExtra("type","a2");startActivity(intent);break;}
                                }
                                Log.e("aaa","!!!!!!!!!!");

                            } else {
                                Toast.makeText(LifeSpeakActivity.this,"请检查网络",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                else if (i==1){
                    Intent intent = new Intent(LifeSpeakActivity.this,LifeSpeakActivity_B2.class);
                    intent.putExtra("type","b1");
                    startActivity(intent);
                }
//                    alertDialog2.setItems(mItems3, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            Toast.makeText(mContext,mItems3[which],Toast.LENGTH_SHORT).show();
//                        }
//                    });
                alertDialog2.show();
            }
        });
        alertDialog.show();
    }



}
