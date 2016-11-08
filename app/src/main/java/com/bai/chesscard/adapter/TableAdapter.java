package com.bai.chesscard.adapter;/**
 * Created by Administrator on 2016/11/8.
 */

import android.support.v7.widget.RecyclerView;

import com.bai.chesscard.R;

import java.util.List;

/**
 * author:${白曌勇} on 2016/11/8
 * TODO:
 */
public class TableAdapter extends BaseRecyAdapter {
    public TableAdapter(List dataList) {
        super(dataList);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getLayout() {
        return R.layout.table_list_item;
    }
}
