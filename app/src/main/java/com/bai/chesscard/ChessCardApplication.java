package com.bai.chesscard;

import android.app.Application;
import android.app.IntentService;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;

import com.bai.chesscard.service.BackgroundService;
import com.bai.chesscard.utils.AppPrefrence;
import com.tencent.TIMManager;

/**
 * Created by Administrator on 2016/11/3.
 */

public class ChessCardApplication extends Application {
    private static ChessCardApplication instance;

    public static ChessCardApplication getInstance() {
        return instance;
    }

    private Intent backgroundService;

    private SoundPool soundPool;
    private int background;
    private int btnSound;
    private int diceSound;
    private int goldSound;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        TIMManager.getInstance().init(this);
        if (soundPool == null)
            soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        background = soundPool.load(this, R.raw.background_mic, 1);
        btnSound = soundPool.load(this, R.raw.btn_mic, 1);
        diceSound = soundPool.load(this, R.raw.dice_mic, 1);
        goldSound = soundPool.load(this, R.raw.gold_mic, 1);
    }

    /**
     * 播放背景音乐
     */
    public void playBack() {
        if (AppPrefrence.getIsBack(this)) {
            if (backgroundService == null)
                backgroundService = new Intent(this, BackgroundService.class);
            startService(backgroundService);
        }
    }

    public void stopBack() {
        if (backgroundService == null)
            backgroundService = new Intent(this, BackgroundService.class);
        stopService(backgroundService);
    }

    public void resumeBack() {
        soundPool.resume(background);
    }

    public void pauseBack() {

    }

    /**
     * 播放按钮音效
     */
    public void playBtnSound() {
        if (AppPrefrence.getIsNotify(this))
            soundPool.play(btnSound, 1, 1, 1, 0, 1);
    }

    /**
     * 播放摇色子音效
     */
    public void playDiceSound() {
        if (AppPrefrence.getIsNotify(this))
            soundPool.play(diceSound, 1, 1, 1, -1, 1);
    }

    /**
     * 播放押注音效
     */
    public void playGoldSound() {
        if (AppPrefrence.getIsNotify(this))
            soundPool.play(goldSound, 1, 1, 1, 0, 1);
    }

    public void stopDiecSound() {
        soundPool.pause(diceSound);
    }
}
