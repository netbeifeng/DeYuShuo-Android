package com.deyushuo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.deyushuo.base.ListItem;
import com.dys.deyushuo.R;

import java.util.List;

public class PeopleRecyAdapter extends RecyclerView.Adapter<PeopleRecyAdapter.ViewHolder> {
    private Context context;
    private List<ListItem> list;
    private PeopleRecyAdapter.OnItemClickListener mOnItemClickListener;
    public PeopleRecyAdapter(Context context,List<ListItem> list) {
        this.context=context;
        this.list=list;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.people_card_view_list,parent,false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //holder.iv_backgroud.setBackgroundResource(list.get(position).getImageId());
        Glide.with(context).load(list.get(position).getImageLink()).into(holder.iv_backgroud);
        holder.tv_source.setText(list.get(position).getDate());
        holder.tv_title.setText(list.get(position).getTitel());

        final int positions = position;
        if (mOnItemClickListener!=null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    int pos = holder.getAdapterPosition();
                    mOnItemClickListener.onItemClick(holder.itemView,pos);
                }
            });
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_backgroud;
        public TextView tv_title;
        public TextView tv_source;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_backgroud = (ImageView) itemView.findViewById(R.id.card_view_people);
            tv_title = (TextView) itemView.findViewById(R.id.title);
            tv_source = (TextView) itemView.findViewById(R.id.source);
        }
    }



}
