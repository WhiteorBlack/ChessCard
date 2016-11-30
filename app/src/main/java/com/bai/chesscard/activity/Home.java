package com.bai.chesscard.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bai.chesscard.BaseActivity;
import com.bai.chesscard.MainActivity;
import com.bai.chesscard.R;
import com.bai.chesscard.async.PostTools;
import com.bai.chesscard.bean.BaseBean;
import com.bai.chesscard.bean.Bean_Avatar;
import com.bai.chesscard.dialog.EditNamePop;
import com.bai.chesscard.dialog.HelpPop;
import com.bai.chesscard.dialog.PersonalPop;
import com.bai.chesscard.dialog.SettingPop;
import com.bai.chesscard.interfacer.PopInterfacer;
import com.bai.chesscard.interfacer.PostCallBack;
import com.bai.chesscard.utils.AppPrefrence;
import com.bai.chesscard.utils.CommonUntilities;
import com.bai.chesscard.utils.Tools;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TResult;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/8.
 */

public class Home extends TakePhotoActivity implements PopInterfacer {
    @BindView(R.id.txt_notify)
    TextView txtNotify;
    @BindView(R.id.ll_notify_content)
    LinearLayout llNotifyContent;
    @BindView(R.id.txt_pre_limit)
    TextView txtPreLimit;
    @BindView(R.id.fl_pre_room)
    FrameLayout flPreRoom;
    @BindView(R.id.txt_mid_limit)
    TextView txtMidLimit;
    @BindView(R.id.fl_mid_room)
    FrameLayout flMidRoom;
    @BindView(R.id.txt_hig_limit)
    TextView txtHigLimit;
    @BindView(R.id.fl_hig_room)
    FrameLayout flHigRoom;
    @BindView(R.id.txt_online_count)
    TextView txtOnlineCount;
    @BindView(R.id.img_start)
    ImageView imgStart;
    @BindView(R.id.img_user_photo)
    ImageView imgUserPhoto;
    @BindView(R.id.txt_user_no)
    TextView txtUserNo;
    @BindView(R.id.txt_user_name)
    TextView txtUserName;
    @BindView(R.id.txt_user_money)
    TextView txtUserMoney;
    @BindView(R.id.txt_help)
    TextView txtHelp;
    @BindView(R.id.txt_setting)
    TextView txtSetting;

    private HelpPop helpPop;
    private SettingPop settingPop;
    private PersonalPop personalPop;
    private Context context;
    private String picPath;
    private EditNamePop editNamePop;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivty_home);
        ButterKnife.bind(this);
        context = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        txtUserName.setText("昵称: " + AppPrefrence.getUserName(context));
        txtUserNo.setText("编号: " + AppPrefrence.getUserNo(context));
        txtUserMoney.setText(AppPrefrence.getAmount(context) + "");
    }

    @OnClick({R.id.fl_pre_room, R.id.fl_mid_room, R.id.fl_hig_room, R.id.img_start, R.id.img_user_photo, R.id.txt_help, R.id.txt_setting})
    public void cardClick(View view) {
        switch (view.getId()) {
            case R.id.fl_pre_room:
                startActivityForResult(new Intent(context, TableList.class).putExtra("type", "0"), 0);
                break;
            case R.id.fl_mid_room:
                startActivityForResult(new Intent(context, TableList.class).putExtra("type", "1"), 1);
                break;
            case R.id.fl_hig_room:
                startActivityForResult(new Intent(context, TableList.class).putExtra("type", "2"), 2);
                break;
            case R.id.img_start:
                break;
            case R.id.img_user_photo:
                if (personalPop == null)
                    personalPop = new PersonalPop(context);
                personalPop.setPhoto(AppPrefrence.getAvatar(context));
                personalPop.showPop(txtHelp);
                personalPop.setPopInterfacer(this, 0);
                break;
            case R.id.txt_help:
                if (helpPop == null)
                    helpPop = new HelpPop(context);
                helpPop.showPop(txtHelp);
                helpPop.setPopInterfacer(this, 1);
                break;
            case R.id.txt_setting:
                if (settingPop == null)
                    settingPop = new SettingPop(context);
                settingPop.showPop(txtHelp);
                settingPop.setPopInterfacer(this, 2);
                break;
        }
    }

    @Override
    public void OnDismiss(int flag) {
        switch (flag) {
            case 0:
                personalPop = null;
                break;
            case 1:
                helpPop = null;
                break;
            case 2:
                settingPop = null;
                break;
            case 3:
                editNamePop = null;
                break;
        }
    }

    @Override
    public void OnConfirm(int flag, Bundle bundle) {
        switch (flag) {
            case 0:
                if (bundle == null)
                    return;
                if (bundle.getInt("type", -1) == 0) {
                    //更改昵称
                    if (editNamePop == null)
                        editNamePop = new EditNamePop(context);
                    editNamePop.setPopInterfacer(this, 3);
                    editNamePop.showPop(txtHelp);
                }
                if (bundle.getInt("type") == 1) {
                    //更改账号
                    logout();
                    startActivity(new Intent(context, MainActivity.class));
                    personalPop.dismiss();
                    finish();
                }
                if (bundle.getInt("type") == 2) { //更改头像
                    final File file = new File(Environment.getExternalStorageDirectory(), "/chessCard/" + System.currentTimeMillis() + ".jpg");
                    if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
                    final Uri imageUri = Uri.fromFile(file);
                    CharSequence[] items = {"手机相册", "手机拍照"};
                    final TakePhoto takePhoto = getTakePhoto();
                    new AlertDialog.Builder(this).setTitle("选择照片").setCancelable(true).setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    takePhoto.onPickFromGallery();
                                    break;
                                case 1:
                                    takePhoto.onPickFromCapture(imageUri);
                                    break;
                                case 2:
                                    dialog.dismiss();
                                    break;
                            }
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
                }
                break;
            case 3:
                if (bundle == null)
                    return;
                if (personalPop != null)
                    personalPop.setName(bundle.getString("name"));
                txtUserName.setText(bundle.getString("name"));
                break;
        }
    }

    private void logout() {
    }

    @Override
    public void OnCancle(int flag) {

    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        picPath = result.getImage().getPath();
        if (!TextUtils.isEmpty(picPath))
            uploadPic();

    }

    private void uploadPic() {
        Map<String, String> params = new HashMap<>();
        params.put("picture", Tools.convertIconToString(picPath));
        params.put("token", AppPrefrence.getToken(context));
        PostTools.postData(CommonUntilities.MAIN_URL + "uploadpic", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response)) {
                    Tools.toastMsgCenter(context, R.string.no_network);
                    return;
                }
                Bean_Avatar baseBean = new Gson().fromJson(response, Bean_Avatar.class);
                if (baseBean.status) {
                    if (personalPop != null)
                        personalPop.setPhoto(picPath);
                    Glide.with(context).load(picPath).into(imgUserPhoto);
                    AppPrefrence.setAvatar(context, CommonUntilities.PIC_URL + baseBean.data);
                }
                Tools.toastMsgCenter(context, baseBean.msg);
            }
        });
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }
}
