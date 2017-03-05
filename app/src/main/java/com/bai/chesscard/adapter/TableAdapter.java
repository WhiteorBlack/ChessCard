package com.bai.chesscard.adapter;/**
 * Created by Administrator on 2016/11/8.
 */

import android.support.v7.widget.RecyclerView;

import com.bai.chesscard.R;
import com.bai.chesscard.bean.Bean_Table;

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
        Bean_Table.Table table = (Bean_Table.Table) dataList.get(position);
        ViewHolder mholder = (ViewHolder) holder;
        mholder.setText(R.id.txt_online_count, table.lookcount + " 人在线");
        mholder.setText(R.id.txt_limit,"最低限制");
        mholder.setImage(R.id.img_status,R.mipmap.img_wait);
    }

    @Override
    public int getLayout() {
        return R.layout.table_list_item;
    }
}
