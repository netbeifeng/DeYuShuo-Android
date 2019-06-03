package com.deyushuo.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tu.loadingdialog.LoadingDailog;
import com.deyushuo.adapter.BookRecyAdapter;
import com.deyushuo.base.Life_B2_Item;
import com.dys.deyushuo.R;
import com.deyushuo.connect.get_Life_B2;


import com.deyushuo.connect.MyDatabaseHelper_Book;

import java.util.ArrayList;
import java.util.List;

public class BookSpeakActivity extends Activity {
    LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(this);
    LoadingDailog dialog;
    private RecyclerView recyclerView;
    public final List<Life_B2_Item> list = new ArrayList<>();
    public boolean isOne = true;
    public get_Life_B2 gb2 = new get_Life_B2();
    private MyDatabaseHelper_Book myDatabaseHelper_book;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        loadBuilder
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dialog=loadBuilder.create();
        setContentView(R.layout.book_recy_main);
        ImageView pre = (ImageView)findViewById(R.id.last_page_book);
        ImageView nex = (ImageView)findViewById(R.id.next_page_book);
        pre.setVisibility(View.INVISIBLE);
        nex.setVisibility(View.INVISIBLE);

        gb2.createData_Book1(list);
        initView();
        myDatabaseHelper_book = new MyDatabaseHelper_Book(this,"BookStore.db",null,1);
        myDatabaseHelper_book.getWritableDatabase();
    }
    public void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recy_book);

        recyclerView.setLayoutManager(new LinearLayoutManager((BookSpeakActivity.this)));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        BookRecyAdapter bookRecyAdapter = new BookRecyAdapter(this,list);
        recyclerView.setAdapter(bookRecyAdapter);
        bookRecyAdapter.setOnItemClickListener(new BookRecyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i("position:",position+"");

                String titel = list.get(position).getTitel();
                String date = list.get(position).getContext();

                Intent intent = new Intent(BookSpeakActivity.this, BookReaderActivity.class);
                intent.putExtra("titel",titel);
                intent.putExtra("date",date);

                startActivity(intent);
            }
        });
    }







    public void biggg(View v) {
        TextView title = (TextView) findViewById(R.id.title);
        TextView source = (TextView) findViewById(R.id.source);
        title.setText("成功！");
        source.setText("每日德语听力");
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
        Intent intent = new Intent(BookSpeakActivity.this,CircleActivity.class);
        startActivity(intent);

    }


    public void changeLevel_Book(View view) {

        final Context mContext = this;
        final String[] menuItems = new String[]{"StudienWeg1","StudienWeg2","StudienWeg3"};
        dialog=loadBuilder.create();
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setItems(menuItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, final int i) {
                Toast.makeText(mContext,menuItems[i],Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(mContext);
                if(i==0) {
                    loading1(view);
                } else if (i==1){
                    loading2(view);
                } else if (i==2) {
                    loading3(view);
                }
            }
    });
        alertDialog.show();
    }

    public void loading1(View v) {
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                /**
                 * 延时执行的代码
                 */
                dialog.hide();
                gb2.createData_Book1(list);
                initView();
                Toast.makeText(BookSpeakActivity.this,"加载成功",Toast.LENGTH_LONG).show();
            }
        },3000); // 延时1秒
    }
    public void loading2(View v) {
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                /**
                 * 延时执行的代码
                 */
                dialog.hide();
                gb2.createData_Book2(list);
                initView();
                Toast.makeText(BookSpeakActivity.this,"加载成功",Toast.LENGTH_LONG).show();
            }
        },3000); // 延时1秒
    }

    public void loading3(View v) {
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                /**
                 * 延时执行的代码
                 */
                dialog.hide();
                gb2.createData_Book3(list);
                initView();
                Toast.makeText(BookSpeakActivity.this,"加载成功",Toast.LENGTH_LONG).show();
            }
        },3000); // 延时1秒
    }
}
