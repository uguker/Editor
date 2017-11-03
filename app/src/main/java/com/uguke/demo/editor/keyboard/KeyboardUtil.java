package com.uguke.demo.editor.keyboard;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public final class KeyboardUtil {

    private KeyboardUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 功能描述：动态显示软键盘
     * <p>在manifest.xml中activity中设置</p>
     * <p>android:windowSoftInputMode="adjustPan"</p>
     * @param act 活动
     */
    public static void showSoftInput(Activity act) {
        View view = act.getCurrentFocus();
        if (view == null)
            view = new View(act);
        InputMethodManager imm = (InputMethodManager) act
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null)
            return;
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }
    
    /**
     * 功能描述：动态显示软键盘
     * @param view 视图
     */
    public static void showSoftInput(View view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null)
            return;

        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 功能描述：动态隐藏软键盘
     * @param act 活动
     */
    public static void hideSoftInput(Activity act) {
        View view = act.getCurrentFocus();
        if (view == null)
            view = new View(act);
        InputMethodManager imm = (InputMethodManager) act
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null)
            return;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 功能描述：动态隐藏软键盘
     * @param view 视图
     */
    public static void hideSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null)
            return;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

//    /**
//     * 功能描述：切换键盘显示与否状态
//     */
//    public static void toggleSoftInput() {
//        InputMethodManager imm = (InputMethodManager) Tools.getContext()
//                .getSystemService(Context.INPUT_METHOD_SERVICE);
//        if (imm == null)
//            return;
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
//    }

    public static boolean isSoftShowing(Activity act) {
        //获取当前屏幕内容的高度
        int screenHeight = act.getWindow().getDecorView().getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        act.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);

        return screenHeight - rect.bottom - getBarHeight(act) != 0;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static int getBarHeight(Activity act) {
        DisplayMetrics metrics = new DisplayMetrics();
        //这个方法获取可能不是真实屏幕的高度
        act.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        //获取当前屏幕的真实高度
        act.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        } else {
            return 0;
        }
    }

}
