package com.bai.chesscard.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.bai.chesscard.R;
import com.bai.chesscard.async.PostTools;
import com.bai.chesscard.bean.BaseBean;
import com.bai.chesscard.interfacer.PostCallBack;
import com.bai.chesscard.utils.AppPrefrence;
import com.bai.chesscard.utils.CommonUntilities;
import com.bai.chesscard.utils.Tools;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/11.
 */

public class EditNamePop extends BasePopupwind {
    private View view;
    private EditText edtName;

    public EditNamePop(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.edit_name_pop, null);
        edtName = (EditText) view.findViewById(R.id.edt_name);
        edtName.setText(AppPrefrence.getUserName(context));
        edtName.setFocusable(true);
        view.findViewById(R.id.img_cancle).setOnClickListener(this);
        view.findViewById(R.id.img_confirm).setOnClickListener(this);
        this.setContentView(view);
        this.setFocusable(true);
    }

    public void setName(String name) {
        edtName.setText(name);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.img_cancle:
                dismiss();
                break;
            case R.id.img_confirm:
                String name = edtName.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    Tools.toastMsg(context, "请输入用户昵称");
                    return;
                }
                editName(name);
                break;
        }
    }

    private void editName(final String name) {
        Map<String, String> params = new HashMap<>();
        params.put("nick_name", name);
        params.put("token", AppPrefrence.getToken(context));
        PostTools.postData(CommonUntilities.MAIN_URL + "edituser", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response)) {
                    Tools.toastMsgCenter(context, R.string.no_network);
                    return;
                }
                BaseBean baseBean = new Gson().fromJson(response, BaseBean.class);
                if (baseBean.status) {
                    Bundle bundle = new Bundle();
                    bundle.putString("name", name);
                    if (popInterfacer != null)
                        popInterfacer.OnConfirm(flag, bundle);
                    dismiss();
                } else Tools.toastMsgCenter(context, baseBean.msg);
            }
        });
    }
}
