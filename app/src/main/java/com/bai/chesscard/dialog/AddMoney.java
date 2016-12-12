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
 * Created by Administrator on 2016/12/12.
 */

public class AddMoney extends BasePopupwind {
    private View view;
    private EditText edtMoney;
    private int countMoney = 0;

    public AddMoney(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.add_money_pop, null);
        edtMoney = (EditText) view.findViewById(R.id.edt_money);
        view.findViewById(R.id.img_confirm).setOnClickListener(this);
        view.findViewById(R.id.img_add).setOnClickListener(this);
        this.setContentView(view);
        this.setOutsideTouchable(true);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.img_confirm:
                String money1 = edtMoney.getText().toString();
                if (TextUtils.isEmpty(money1)){
                    dismiss();
                    return;
                }else   countMoney = Integer.parseInt(money1.trim());
                if (countMoney > AppPrefrence.getAmount(context)) {
                    Tools.toastMsgCenter(context, "账户余额不足,请充值");
                    return;
                }
                AppPrefrence.setAmount(context, AppPrefrence.getAmount(context) - countMoney);
                Bundle bundle = new Bundle();
                bundle.putInt("money", countMoney);
                if (popInterfacer != null)
                    popInterfacer.OnConfirm(flag, bundle);
                dismiss();
                break;
            case R.id.img_add:
                String money = edtMoney.getText().toString();
                if (TextUtils.isEmpty(money)) {
                    countMoney += 100;
                } else {
                    countMoney = Integer.parseInt(money.trim());
                    countMoney += 100;
                }
                edtMoney.setText(countMoney + "");
                break;
        }
    }
}
