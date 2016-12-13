package com.bai.chesscard.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bai.chesscard.R;
import com.bai.chesscard.bean.Bean_ChessList;

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
        ViewHolder mHolder= (ViewHolder) holder;
        Bean_ChessList.Chess chess= (Bean_ChessList.Chess) dataList.get(position);
        if (chess.isVisiable)
            mHolder.getView(R.id.img_chess).setVisibility(View.VISIBLE);
        else mHolder.getView(R.id.img_chess).setVisibility(View.INVISIBLE);
    }

    public int getInvisableCount(){
        int count=0;
        for (int i = 0; i < dataList.size(); i++) {
            Bean_ChessList.Chess chess= (Bean_ChessList.Chess) dataList.get(i);
            if (chess.isVisiable)
                count++;
        }
        return count;
    }

    @Override
    public int getLayout() {
        return R.layout.game_chess;
    }
}
