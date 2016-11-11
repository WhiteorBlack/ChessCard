package com.bai.chesscard.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.bai.chesscard.R;
import com.bai.chesscard.utils.AppPrefrence;
import com.bai.chesscard.utils.Tools;

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
        view.findViewById(R.id.img_cancle).setOnClickListener(this);
        view.findViewById(R.id.img_confirm).setOnClickListener(this);
        this.setContentView(view);
    }

    public void setName(String name){
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
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                if (popInterfacer != null)
                    popInterfacer.OnConfirm(flag, bundle);
                break;
        }
    }
}
