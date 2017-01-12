package com.bai.chesscard.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.bai.chesscard.R;
import com.bai.chesscard.adapter.AudienceAdapter;
import com.bai.chesscard.adapter.BaseRecyAdapter;
import com.bai.chesscard.async.PostTools;
import com.bai.chesscard.bean.Bean_Audience;
import com.bai.chesscard.interfacer.PostCallBack;
import com.bai.chesscard.presenter.GamePresenterNew;
import com.bai.chesscard.utils.CommonUntilities;
import com.bai.chesscard.widget.xrecycleview.XRecyclerView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/9.
 */

public class AudiencelPop extends BasePopupwind implements XRecyclerView.LoadingListener {
    private View view;
    private XRecyclerView recyclerView;
    private List<Bean_Audience.Audience> audienceList;
    private AudienceAdapter audienceAdapter;
    private GamePresenterNew gamePresenter;
    private int pageIndex=1,pageSize=20;
    private String id;

    public AudiencelPop(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        audienceList = new ArrayList();
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.audience_pop, null);
        recyclerView = (XRecyclerView) view.findViewById(R.id.recy_audience);
        recyclerView.setLoadingMoreEnabled(true);
        recyclerView.setPullRefreshEnabled(false);
        recyclerView.setLoadingListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        audienceAdapter = new AudienceAdapter(audienceList);
        recyclerView.setAdapter(audienceAdapter);
        audienceAdapter.setOnItemClickListener(new BaseRecyAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                if (gamePresenter != null)
                    gamePresenter.showUserInfo(audienceList.get(position-1).userinfo);
            }

            @Override
            public void onItemLongClickListener(View view, int position) {

            }
        });
        this.setContentView(view);
        this.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setAnimationStyle(R.style.audi_anim);
    }

    public void setId(String id){
        this.id=id;
    }

    public void setPresenter(GamePresenterNew gamePresenter) {
        this.gamePresenter = gamePresenter;
    }

    @Override
    public void showPop(View parent) {
        this.showAtLocation(parent,Gravity.LEFT,0,0);
        getAudiunce();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        pageIndex++;
        getAudiunce();
    }

    private void getAudiunce() {
        Map<String,String> params=new HashMap<>();
        params.put("table_id",id);
        params.put("Pageindex",pageIndex+"");
        params.put("Pagesize",pageSize+"");
        PostTools.postData(CommonUntilities.MAIN_URL+"userlist",params,new PostCallBack(){
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response))
                    return;
                if (pageIndex==1)
                    audienceList.clear();
                Bean_Audience beanAudience=new Gson().fromJson(response,Bean_Audience.class);
                if (beanAudience.status){
                    if (beanAudience.data!=null&&beanAudience.data.size()>0){
                        audienceList.addAll(beanAudience.data);
                        audienceAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}
