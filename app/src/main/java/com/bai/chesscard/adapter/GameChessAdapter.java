package com.bai.chesscard.adapter;

import android.support.v7.widget.RecyclerView;

import com.bai.chesscard.R;

import java.util.List;

/**
 * Created by Administrator on 2016/12/2.
 */

public class GameChessAdapter extends BaseRecyAdapter {
    public GameChessAdapter(List dataList) {
        super(dataList);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getLayout() {
        return R.layout.game_chess;
    }
}
