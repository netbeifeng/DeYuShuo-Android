package com.deyushuo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;

import com.deyushuo.connect.get_Life_B2;
import com.deyushuo.adapter.LifeRecyAdapter_B2;
import com.deyushuo.base.Life_B2_Item;
import com.dys.deyushuo.R;

import java.util.ArrayList;
import java.util.List;

public class LifeSpeakActivity_B2 extends Activity {
    final List<Life_B2_Item> list = new ArrayList<>();
    private RecyclerView recyclerView;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.life_recy_main);
        ImageView pre = (ImageView)findViewById(R.id.last_page_life);
        ImageView nex = (ImageView)findViewById(R.id.next_page_life);
        pre.setVisibility(View.INVISIBLE);
        nex.setVisibility(View.INVISIBLE);
        recyclerView = (RecyclerView) findViewById(R.id.recy);

        recyclerView.setLayoutManager(new LinearLayoutManager((LifeSpeakActivity_B2.this)));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        LifeRecyAdapter_B2 lifeRecyAdapter = new LifeRecyAdapter_B2(this,list);
        recyclerView.setAdapter(lifeRecyAdapter);

        String extra = getIntent().getStringExtra("type");
        get_Life_B2 gb2 = new get_Life_B2();
        if(extra.equals("b1")){
            gb2.createData(list);
        } else if(extra.equals("a2")){
            gb2.createData_A2(list);
        }


        lifeRecyAdapter.setOnItemClickListener(new LifeRecyAdapter_B2.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Log.i("position:",position+"");

            String titel = list.get(position).getTitel();
            String date = list.get(position).getContext();

            Intent intent = new Intent(LifeSpeakActivity_B2.this, LifeReaderActivtiy_B2.class);
            intent.putExtra("titel",titel);
            intent.putExtra("date",date);

            startActivity(intent);
        }
    });

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
        Intent intent = new Intent(LifeSpeakActivity_B2.this,CircleActivity.class);
        startActivity(intent);

    }

    public void changeThema(View v) {
        Intent intent = new Intent(LifeSpeakActivity_B2.this,LifeSpeakActivity.class);
        startActivity(intent);
    }

}
