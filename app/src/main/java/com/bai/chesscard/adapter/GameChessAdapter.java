package com.bai.chesscard.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bai.chesscard.R;
import com.bai.chesscard.bean.Bean_ChessList;
import com.bai.chesscard.utils.ConstentNew;

import java.util.List;

/**
 * Created by Administrator on 2016/12/2.
 */

public class GameChessAdapter extends BaseRecyAdapter {
    private int[] chessRes = new int[]{R.mipmap.chess_one, R.mipmap.chess_two, R.mipmap.chess_three, R.mipmap.chess_four, R.mipmap.chess_five, R.mipmap.chess_six, R.mipmap.chess_seven,
            R.mipmap.chess_eight, R.mipmap.chess_nine};

    public GameChessAdapter(List dataList) {
        super(dataList);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewHolder mHolder = (ViewHolder) holder;
        Bean_ChessList.Chess chess = (Bean_ChessList.Chess) dataList.get(position);

        if (position == dataList.size() - 1) {
            mHolder.getView(R.id.img_chess).setVisibility(View.VISIBLE);
            mHolder.setImage(R.id.img_chess, chessRes[ConstentNew.CHESSLIST[position]]);
        } else {
            mHolder.setImage(R.id.img_chess, R.mipmap.game_chess);
            if (chess.isVisiable)
                mHolder.getView(R.id.img_chess).setVisibility(View.VISIBLE);
            else mHolder.getView(R.id.img_chess).setVisibility(View.INVISIBLE);
        }
    }

    public int getInvisableCount() {
        int count = 0;
        for (int i = 0; i < dataList.size(); i++) {
            Bean_ChessList.Chess chess = (Bean_ChessList.Chess) dataList.get(i);
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
