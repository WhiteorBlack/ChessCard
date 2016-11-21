package com.bai.chesscard.widget;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/11/21.
 */

public class CenterToast extends Toast {
    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link Application}
     *                or {@link Activity} object.
     */
    public CenterToast(Context context) {
        super(context);
    }

}
