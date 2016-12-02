package com.bai.chesscard.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bai.chesscard.R;
import com.bai.chesscard.adapter.AudienceAdapter;
import com.bai.chesscard.adapter.BaseRecyAdapter;
import com.bai.chesscard.bean.Bean_TableDetial;
import com.bai.chesscard.presenter.GamePresenter;
import com.bai.chesscard.utils.AppPrefrence;
import com.bai.chesscard.utils.Tools;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */

public class AudiencelPop extends BasePopupwind {
    private View view;
    private RecyclerView recyclerView;
    private List<Bean_TableDetial.TableUser> audienceList;
    private AudienceAdapter audienceAdapter;
    private GamePresenter gamePresenter;

    public AudiencelPop(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        audienceList = new ArrayList();
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.audience_pop, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recy_audience);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        audienceAdapter = new AudienceAdapter(audienceList);
        recyclerView.setAdapter(audienceAdapter);
        audienceAdapter.setOnItemClickListener(new BaseRecyAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                if (gamePresenter != null)
                    gamePresenter.showUserInfo(audienceList.get(position));
            }

            @Override
            public void onItemLongClickListener(View view, int position) {

            }
        });
        this.setContentView(view);
        this.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setAnimationStyle(R.style.audi_anim);
    }

    public void setPresenter(GamePresenter gamePresenter) {
        this.gamePresenter = gamePresenter;
    }

    @Override
    public void showPop(View parent) {
        this.showAtLocation(parent,Gravity.LEFT,0,0);
        for (int i = 0; i < 20; i++) {
            audienceList.add(new Bean_TableDetial.TableUser());
        }
        audienceAdapter.notifyDataSetChanged();
    }
}
