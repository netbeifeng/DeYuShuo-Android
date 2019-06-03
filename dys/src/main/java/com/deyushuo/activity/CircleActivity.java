package com.deyushuo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.deyushuo.view.CircleMenuLayout;
import com.dys.deyushuo.R;
/**
 * <pre>
 * @author zhy
 * http://blog.csdn.net/lmj623565791/article/details/43131133
 * </pre>
 */
public class CircleActivity extends Activity
{

    private CircleMenuLayout mCircleMenuLayout;

    private String[] mItemTexts = new String[] { "书上说", "生活说","名人说" ,
            "个人中心 ", "设置" };
    private int[] mItemImgs = new int[] { R.drawable.book_icon,
            R.drawable.life_icon, R.drawable.people_icon,
            R.drawable.my_center, R.drawable.setting_icon };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //自已切换布局文件看效果
        setContentView(R.layout.activity_main02);
//		setContentView(R.layout.activity_main);

        mCircleMenuLayout = (CircleMenuLayout) findViewById(R.id.id_menulayout);
        mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);



        mCircleMenuLayout.setOnMenuItemClickListener(new CircleMenuLayout.OnMenuItemClickListener()
        {

            @Override
            public void itemClick(View view, int pos)
            {
                //Toast.makeText(CircleActivity.this, mItemTexts[pos],
                //        Toast.LENGTH_SHORT).show();
                Intent intent = null;
                Toast.makeText(CircleActivity.this,""+pos,Toast.LENGTH_SHORT).show();
                switch (pos) {
                    case 0: //书上说
                        intent = new Intent(CircleActivity.this,BookSpeakActivity.class); break;
                    case 1: //
                        intent = new Intent(CircleActivity.this,LifeSpeakActivity.class); break;
                    case 2: //
                        intent = new Intent(CircleActivity.this,PeopleSpeakActivity.class); break;
                    case 3:
                        intent = new Intent(CircleActivity.this,CollectionsCenter.class); break;
                    case 4:
                        intent = new Intent(CircleActivity.this, SettingActivity.class); break;
                    default:
                        break;
                }
                startActivity(intent);
            }

            @Override
            public void itemCenterClick(View view)
            {
                Toast.makeText(CircleActivity.this,
                        "you can do something just like ccb  ",
                        Toast.LENGTH_SHORT).show();

            }
        });

    }

}

