package com.cxl.demo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;

public class InputMethodLayout implements OnGlobalLayoutListener {
    /**
     * 初始状态
     **/
    public static final byte KEYBOARD_STATE_INIT = -1;
    /**
     * 隐藏状态
     **/
    public static final byte KEYBOARD_STATE_HIDE = -2;
    /**
     * 打开状态
     **/
    public static final byte KEYBOARD_STATE_SHOW = -3;

    private onKeyboardsChangeListener keyboarddsChangeListener;// 键盘状�?监听

    View decodeView;

    Activity mActivity;

    ViewTreeObserver observer;

    Rect mRect;

    public InputMethodLayout(Activity mActivity) {
        this.mActivity = mActivity;
        mRect = new Rect();
        Window mWindow = mActivity.getWindow();
        if (mWindow != null) {
            decodeView = mWindow.getDecorView();
        }
        if (decodeView != null) {
            observer = decodeView.getViewTreeObserver();
        }
        if (observer != null) {
            observer.addOnGlobalLayoutListener(this);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void OnDestory() {
        if (observer != null) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    observer.removeOnGlobalLayoutListener(this);
                }
            } catch (Exception e) {

            }
        }
    }

    int previousKeyboardHeight = -1;

    @Override
    public void onGlobalLayout() {
        if (decodeView != null) {
            // 先获取显示的高度
            decodeView.getWindowVisibleDisplayFrame(mRect);
            int displayHeight = mRect.bottom;

            // 然后获取窗体的高
            int height = decodeView.getHeight();

            // 得到键盘的高
            int keyboardHeight = height - displayHeight;

            if (previousKeyboardHeight != keyboardHeight) {
                boolean hide = (double) displayHeight / height > 0.8;
                if (previousKeyboardHeight == -1) {
                    keyboardSateChange(KEYBOARD_STATE_INIT, keyboardHeight, displayHeight);
                } else if (hide) {
                    keyboardSateChange(KEYBOARD_STATE_HIDE, keyboardHeight, displayHeight);
                } else {
                    keyboardSateChange(KEYBOARD_STATE_SHOW, keyboardHeight, displayHeight);
                }
            }
            previousKeyboardHeight = height;
        }
    }

    /**
     * 切换软键盘状
     *
     * @param state
     */
    public void keyboardSateChange(int state, int keyboardHeight, int displayHeight) {
        if (keyboarddsChangeListener != null) {
            keyboarddsChangeListener.onKeyBoardStateChange(state, keyboardHeight, displayHeight);
        }
    }

    /**
     * 软键盘状态切换监听
     *
     * @author zihao
     */
    public interface onKeyboardsChangeListener {
        void onKeyBoardStateChange(int state, int keyboardHeight, int displayHeight);
    }

    /**
     * 设置软键盘状态监听
     *
     * @param listener
     */
    public void setOnkeyboarddStateListener(onKeyboardsChangeListener listener) {
        keyboarddsChangeListener = listener;
    }
}
