package com.cxl.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements InputMethodLayout.onKeyboardsChangeListener {

    private View scrollView;

    private View loginButton;

    private int duration = 0;
    private InputMethodLayout methodLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scrollView = findViewById(R.id.scrollview);
        loginButton = findViewById(R.id.login_button);
        methodLayout = new InputMethodLayout(this);
        methodLayout.setOnkeyboarddStateListener(this);
    }

    @Override
    public void onKeyBoardStateChange(int state, int keyboardHeight, int displayHeight) {
        if (scrollView != null) {
            int[] myLocation = new int[2];
            loginButton.getLocationInWindow(myLocation);
            if (state == InputMethodLayout.KEYBOARD_STATE_SHOW) {
                duration = (myLocation[1] + loginButton.getHeight()) - displayHeight;
                if (duration <= 0) {
                    duration = 0;
                }
                if (duration > 0) {
                    scrollView.scrollBy(0, duration);
                }
            } else if (state == InputMethodLayout.KEYBOARD_STATE_HIDE) {
                scrollView.scrollTo(0, 0);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (methodLayout != null) {
            methodLayout.OnDestory();
            methodLayout = null;
        }
    }
}
