package com.bai.chesscard.activity;

import android.app.ProgressDialog;
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

import com.bai.chesscard.ChessCardApplication;
import com.bai.chesscard.MainActivity;
import com.bai.chesscard.R;
import com.bai.chesscard.async.PostTools;
import com.bai.chesscard.bean.BeanCharge;
import com.bai.chesscard.bean.Bean_Avatar;
import com.bai.chesscard.bean.Bean_Notify;
import com.bai.chesscard.bean.Bean_Room;
import com.bai.chesscard.dialog.EditNamePop;
import com.bai.chesscard.dialog.HelpPop;
import com.bai.chesscard.dialog.NotifyDetialPop;
import com.bai.chesscard.dialog.PersonalPop;
import com.bai.chesscard.dialog.SettingPop;
import com.bai.chesscard.interfacer.PopInterfacer;
import com.bai.chesscard.interfacer.PostCallBack;
import com.bai.chesscard.service.MessageEvent;
import com.bai.chesscard.utils.AppPrefrence;
import com.bai.chesscard.utils.CommonUntilities;
import com.bai.chesscard.utils.ConstentNew;
import com.bai.chesscard.utils.Tools;
import com.bai.chesscard.widget.BaseScollTextView;
import com.bai.chesscard.widget.ScrollTextView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.TResult;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMTextElem;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/8.
 */

public class Home extends TakePhotoActivity implements PopInterfacer, Observer {
    @BindView(R.id.txt_notify)
    ScrollTextView txtNotify;
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
    private List<String> notifyStringList;
    private List<Bean_Notify.Notify> notifyList;
    private List<Bean_Room.Room> roomList;
    private NotifyDetialPop notifyDetialPop;

    private int count = 0;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivty_home);
        ButterKnife.bind(this);
        context = this;
        initData();
        MessageEvent.getInstance().addObserver(this);
        ChessCardApplication.getInstance().playBack();
    }

    private void initData() {
        progressDialog = Tools.getDialog(context, "");
        progressDialog.setMessage("正在获取房间数据...");
        progressDialog.show();
        notifyList = new ArrayList<>();
        notifyStringList = new ArrayList<>();
        roomList = new ArrayList<>();
        txtNotify.setOnItemClickListener(new BaseScollTextView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                if (notifyDetialPop == null)
                    notifyDetialPop = new NotifyDetialPop(context);
                notifyDetialPop.setTitle(notifyList.get(position).title);
                notifyDetialPop.setContent(notifyList.get(position).content);
                notifyDetialPop.showPop(txtHelp);
                notifyDetialPop.setPopInterfacer(Home.this, 4);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getNotify();
        getRoomData();
    }

    /**
     * 获取房间信息
     */
    private void getRoomData() {
        PostTools.postData(CommonUntilities.MAIN_URL + "HouseList", null, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response)) {
                    Tools.toastMsgCenter(context, R.string.no_network);
                    return;
                }
                Bean_Room beanRoom = new Gson().fromJson(response, Bean_Room.class);
                if (beanRoom != null && beanRoom.id > 0 && beanRoom.result != null && beanRoom.result.size() > 0) {
                    roomList.clear();
                    roomList.addAll(beanRoom.result);
                    setData();
                }
            }

            @Override
            public void onAfter() {
                super.onAfter();
                progressDialog.dismiss();
            }
        });
    }

    private void setData() {
        for (int i = 0; i < roomList.size(); i++) {
            Bean_Room.Room room = roomList.get(i);
            if (i == 0) {
                txtPreLimit.setText("最低" + room.min_piont + "金币");
            }
            if (i == 1) {
                txtMidLimit.setText("最低" + room.min_piont + "金币");
            }
            if (i == 2) {
                txtHigLimit.setText("最低" + room.min_piont + "金币");
            }
            count += roomList.get(i).people_count;
        }
        txtOnlineCount.setText("当前在线人数: " + count + " 人");
    }


    /**
     * 获取通知
     */
    private void getNotify() {
        Map<String, String> params = new HashMap<>();
//        params.put("Type", "0");
        PostTools.postData(CommonUntilities.MAIN_URL + "NoticeList", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response)) {
                    Tools.toastMsgCenter(context, R.string.no_network);
                    return;
                }
                Bean_Notify bean_notify = new Gson().fromJson(response, Bean_Notify.class);
                if (bean_notify != null && bean_notify.result != null && bean_notify.result.size() > 0) {
                    notifyList.clear();
                    notifyStringList.clear();
                    notifyList.addAll(bean_notify.result);
                    for (int i = 0; i < notifyList.size(); i++) {
                        notifyStringList.add(notifyList.get(i).title);
                    }
                    txtNotify.setData(notifyStringList);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFinishing())
            return;
        Glide.with(this).load(AppPrefrence.getAvatar(context)).into(imgUserPhoto);
        txtUserName.setText("昵称: " + (TextUtils.isEmpty(AppPrefrence.getUserName(context)) ? AppPrefrence.getUserPhone(context) : AppPrefrence.getUserName(context)));
        txtUserNo.setText("编号: " + AppPrefrence.getUserNo(context));
        txtUserMoney.setText(AppPrefrence.getAmount(context) + "");
        ChessCardApplication.getInstance().playBack();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ChessCardApplication.getInstance().stopBack();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MessageEvent.getInstance().clear();
        TIMManager.getInstance().logout();
    }

    @OnClick({R.id.fl_pre_room, R.id.fl_mid_room, R.id.fl_hig_room, R.id.img_start, R.id.img_user_photo, R.id.txt_help, R.id.txt_setting})
    public void cardClick(View view) {
        ChessCardApplication.getInstance().playBtnSound();
        switch (view.getId()) {
            case R.id.fl_pre_room:
                if (roomList.size() == 0)
                    return;
                dealPoint(roomList.get(0).point_str);
                ConstentNew.BANKER_LIMIT_MONEY = roomList.get(0).sz_point;
                startActivityForResult(new Intent(context, TableList.class).putExtra("id", roomList.get(0).id).putExtra("point", roomList.get(0).min_piont), 0);
                break;
            case R.id.fl_mid_room:
                if (roomList.size() == 0)
                    return;
                dealPoint(roomList.get(1).point_str);
                ConstentNew.BANKER_LIMIT_MONEY = roomList.get(1).sz_point;
                startActivityForResult(new Intent(context, TableList.class).putExtra("id", roomList.get(1).id).putExtra("point", roomList.get(1).min_piont), 1);
                break;
            case R.id.fl_hig_room:
                if (roomList.size() < 2)
                    return;
                dealPoint(roomList.get(2).point_str);
                ConstentNew.BANKER_LIMIT_MONEY = roomList.get(2).sz_point;
                startActivityForResult(new Intent(context, TableList.class).putExtra("id", roomList.get(2).id).putExtra("point", roomList.get(2).min_piont), 2);
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

    private void dealPoint(String point_str) {
        if (TextUtils.isEmpty(point_str))
            return;
        String[] point = point_str.split(",");
        ConstentNew.LEFTPOINT = Integer.parseInt(point[0]);
        ConstentNew.MIDPOINT = Integer.parseInt(point[1]);
        ConstentNew.RIGHTPOINT = Integer.parseInt(point[2]);
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
            case 4:
                notifyDetialPop = null;
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
            case 2:
                if (bundle == null)
                    return;
                if (bundle.getBoolean("type")) {
                    ChessCardApplication.getInstance().playBack();
                } else ChessCardApplication.getInstance().stopBack();
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
        AppPrefrence.setAvatar(context, "");
        AppPrefrence.setIsLogin(context, false);
        AppPrefrence.setUserName(context, "");
        AppPrefrence.setUserNo(context, "");
        AppPrefrence.setUserPwd(context, "");
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
        params.put("userlogo", Tools.convertIconToString(picPath));
        params.put("token", AppPrefrence.getToken(context));
        PostTools.postData(CommonUntilities.MAIN_URL + "EditUserLogo", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response)) {
                    Tools.toastMsgCenter(context, R.string.no_network);
                    return;
                }
                Bean_Avatar baseBean = new Gson().fromJson(response, Bean_Avatar.class);
                if (baseBean.id == 1) {
                    if (personalPop != null)
                        personalPop.setPhoto(picPath);
                    Glide.with(context).load(picPath).into(imgUserPhoto);
                    AppPrefrence.setAvatar(context, baseBean.msg);

                    TIMFriendshipManager.getInstance().setFaceUrl(baseBean.msg, null);
                }else
                Tools.toastMsgCenter(context, baseBean.msg);
            }
        });
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof MessageEvent) {
            TIMMessage msg = (TIMMessage) data;
            if (msg != null && TextUtils.equals(msg.getConversation().getType().toString(), "C2C")) {
                for (int i = 0; i < msg.getElementCount(); i++) {

                    TIMTextElem elem = (TIMTextElem) msg.getElement(i);
                    String msgString = elem.getText().toString();
                    if (!TextUtils.isEmpty(msgString)) {
                        final BeanCharge beanCharge = new Gson().fromJson(msgString, BeanCharge.class);
                        if (beanCharge != null && beanCharge.type == 15) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AppPrefrence.setAmount(context, beanCharge.amount);
                                    txtUserMoney.setText(beanCharge.amount+"");
                                }
                            });
                        }
                    }


                }
            }
        }
    }


}
