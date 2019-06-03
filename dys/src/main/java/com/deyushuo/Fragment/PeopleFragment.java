package com.deyushuo.Fragment;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deyushuo.adapter.MyAdapter;
import com.deyushuo.base.CollectItem;
import com.deyushuo.activity.PeopleReaderActivity;
import com.deyushuo.connect.MyDatabaseHelper_People;
import com.dys.deyushuo.R;

import java.util.ArrayList;
import java.util.List;

public class PeopleFragment extends Fragment {

    final List<CollectItem> collectItems = new ArrayList<>();
    RecyclerView recyclerView;
    private ArrayList<String> arrayList_tite = new ArrayList<>();
    private ArrayList<String> arrayList_sour = new ArrayList<>();
    private ArrayList<String> arrayList_time = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        View chatView = inflater.inflate(R.layout.fragment_people, container,false);
        initView(chatView);
        return chatView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        initData();
        Log.d("TAG/PEOPLE", "onAttach");
    }


    public void initView(View v) {

        recyclerView = (RecyclerView) v.findViewById(R.id.recy_collect_fr_peo);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.LayoutManager layout = new GridLayoutManager(getActivity(),2);//网格布局，每行为3
        recyclerView.setLayoutManager(layout);
        recyclerView.setHasFixedSize(true);//适配器内容改变，不会改变RecyclerView的大小
        MyAdapter adapter = new MyAdapter(collectItems);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i("position:",position+"");

                String titel = arrayList_tite.get(position);
                String context = arrayList_sour.get(position);

                Intent intent = new Intent(getContext(), PeopleReaderActivity.class);
                intent.putExtra("titel",titel);
                intent.putExtra("date",context);
                Log.e("AAA",context);

                startActivity(intent);
            }
        });
    }

    public void initData() {
        getData();
        String[] strTitle = (String[]) arrayList_tite.toArray(new String[0]);
        String[] srcTitle = (String[]) arrayList_sour.toArray(new String[0]);
        String[] times = (String[]) arrayList_time.toArray(new String[0]);
       // String[] strTitle = {"这是一段测试文字", "This is a test text", "هذا هو نص الاختبار", "これはテストテキストです", "Dies ist ein Testtext", "這是一段測試文字", "Это тестовый текст", "Ceci est un texte de test", "이것은 테스트 텍스트입니다", "זהו טקסט בדיקה"};
//        String[] strTitle = {"慵懒慢时光，爵士中文", "【华语】曾经的你已杳无音信", "粤语歌中的华语镜像", "凡走过必留下痕迹", "摇滚Live也可以温柔到骨子里", "人生中的舍得与难舍"};
        for(int i=0;i<strTitle.length;i++){
            CollectItem item=new CollectItem();
            item.setTitle(strTitle[i]);
            item.setContext(srcTitle[i]);
            item.setTime("收藏时间:"+times[i]);
            collectItems.add(item);
        }
    }

    public void getData() {
        MyDatabaseHelper_People myDatabaseHelper_people = new MyDatabaseHelper_People(getContext(),"PeopleStore.db",null,1);
        SQLiteDatabase db = myDatabaseHelper_people.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM PEOPLE",null);
        while (cursor.moveToNext()) {
            String titel = cursor.getString(0);
            String source = cursor.getString(1);
            String time = cursor.getString(2);
            arrayList_tite.add(titel);
            arrayList_sour.add(source);
            arrayList_time.add(time);
        }
    }

}

