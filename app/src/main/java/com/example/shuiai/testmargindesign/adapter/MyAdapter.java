package com.example.shuiai.testmargindesign.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shuiai.testmargindesign.R;

import java.util.List;

/**
 * @author shuiai@dianjia.io
 * @Company 杭州木瓜科技有限公司
 * @date 2016/9/7
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHoder> {
    private List<Integer> dataList;
    private Context context;

    public MyAdapter(Context context, List<Integer> dataList) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public MyAdapter.MyViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHoder myViewHoder = new MyViewHoder(LayoutInflater.from(context).inflate(R.layout.item_load, parent, false));

        return myViewHoder;
    }

    @Override
    public void onBindViewHolder(MyAdapter.MyViewHoder holder, int position) {
        holder.txt.setImageResource(dataList.get(position));

    }

    @Override
    public int getItemCount() {
        return (dataList == null || dataList.size() == 0) ? 0 : dataList.size();
    }

    public class MyViewHoder extends RecyclerView.ViewHolder {
        private ImageView txt;

        public MyViewHoder(View itemView) {
            super(itemView);
            txt = (ImageView) itemView.findViewById(R.id.txt);
        }
    }

}
