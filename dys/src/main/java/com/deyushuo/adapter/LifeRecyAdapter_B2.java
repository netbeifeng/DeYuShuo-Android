package com.deyushuo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deyushuo.base.Life_B2_Item;
import com.dys.deyushuo.R;
import java.util.List;

public class LifeRecyAdapter_B2 extends RecyclerView.Adapter<LifeRecyAdapter_B2.ViewHolder> {

    private List<Life_B2_Item> list;
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    public LifeRecyAdapter_B2(Context context, List<Life_B2_Item> list) {
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
        holder.tv_context.setText(list.get(position).getContext().substring(0,0));
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

        public TextView tv_title;
        public TextView tv_context;
        public ViewHolder(View itemView) {
            super(itemView);

            tv_title = (TextView) itemView.findViewById(R.id.title);
            tv_context = (TextView) itemView.findViewById(R.id.source);
        }
    }



}
