package com.cxl.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ScrollView;

public class MainActivity extends AppCompatActivity implements InputMethodLayout.onKeyboardsChangeListener{

    private ScrollView scrollView;

    private View loginButton;

    private int duration = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scrollView = (ScrollView) findViewById(R.id.scrollview);
        loginButton = findViewById(R.id.login_button);
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
}
