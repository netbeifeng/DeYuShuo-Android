package com.deyushuo.activity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.dys.deyushuo.R;


public class MainActivity extends Activity
{
    private TransitionDrawable transitionDrawable;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starter);
        ImageView im =  findViewById(R.id.start);
        setHideAnimation(im,4000);
        ImageView iv = findViewById(R.id.logo);
        Drawable sw = getResources().getDrawable(R.drawable.deyushuo_sw);
        Drawable c = getResources().getDrawable(R.drawable.deyushuo_c);
        TransitionDrawable td = new TransitionDrawable(new Drawable[]{c, sw});
        iv.setBackgroundDrawable(td);
        td.startTransition(4000);
//        Intent intent = new Intent(this, CircleActivity.class);
//        startActivity(intent);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,
                        CircleActivity.class);
                startActivity(intent);
            }

        }, 4000);

//        getListView().setAdapter(
//                new ArrayAdapter<String>(this,
//                        android.R.layout.simple_list_item_1, new String[] {
//                        "测试", "方案一" }));

    }

//    @Override
//    protected void onListItemClick(ListView l, View v, int position, long id)
//    {
//        Intent intent = null;
//        if (position == 0)
//        {
//            intent = new Intent(this, CCBActivity.class);
//        } else
//        {
//            intent = new Intent(this, CircleActivity.class);
//        }
//        startActivity(intent);
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item)
//    {
//        switch (item.getItemId())
//        {
//            case R.id.action_settings:
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_VIEW);
//                intent.setData(Uri
//                        .parse("http://blog.csdn.net/lmj623565791?viewmode=contents"));
//                startActivity(intent);
//                return true;
//            default:
//                return false;
//        }
//    }


    private AlphaAnimation mHideAnimation	= null;
    private AlphaAnimation mShowAnimation	= null;

    /**
     * View渐隐动画效果
     */
    private void setHideAnimation( View view, int duration ){
        if( null == view || duration < 0 ){
            return;
        }
        if( null != mHideAnimation ){
            mHideAnimation.cancel( );
        }
        mHideAnimation = new AlphaAnimation(1.0f, 0.0f);
        mHideAnimation.setDuration( duration );
        mHideAnimation.setFillAfter( true );
        view.startAnimation( mHideAnimation );
    }

    /**
     * View渐现动画效果
     */
    private void setShowAnimation( View view, int duration ){
        if( null == view || duration < 0 ){
            return;
        }
        if( null != mShowAnimation ){
            mShowAnimation.cancel( );
        }
        mShowAnimation = new AlphaAnimation(0.0f, 1.0f);
        mShowAnimation.setDuration( duration );
        mShowAnimation.setFillAfter( true );
        view.startAnimation( mShowAnimation );
    }


}
