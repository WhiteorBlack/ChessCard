package com.bai.chesscard.adapter;

import android.support.v7.widget.RecyclerView;
import android.widget.BaseAdapter;

import com.bai.chesscard.R;

import java.util.List;

/**
 * Created by Administrator on 2016/12/2.
 */

public class AudienceAdapter extends BaseRecyAdapter {
    public AudienceAdapter(List dataList) {
        super(dataList);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewHolder mHolder= (ViewHolder) holder;

    }

    @Override
    public int getLayout() {
        return R.layout.audience_pop_item;
    }
}
