package com.deyushuo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deyushuo.base.CollectItem;
import com.dys.deyushuo.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<CollectItem> data;
    private OnItemClickListener mOnItemClickListener;
    public MyAdapter(List<CollectItem> data) {
        this.data = data;
    }
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //绑定布局
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_collect, null);
        //创建ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder viewHolder, int i) {
        viewHolder.title.setText(data.get(i).getTitle());
        viewHolder.context.setText(data.get(i).getContext());
        viewHolder.time.setText(data.get(i).getTime());
        final int positions = i;
        if (mOnItemClickListener!=null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    int pos = viewHolder.getAdapterPosition();
                    mOnItemClickListener.onItemClick(viewHolder.itemView,pos);
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return data.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView context;
        public TextView time;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            title = (TextView) itemLayoutView.findViewById(R.id.title_collect);
            context=(TextView)itemLayoutView.findViewById(R.id.context_collect);
            time = (TextView) itemLayoutView.findViewById(R.id.addTime);
        }
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
