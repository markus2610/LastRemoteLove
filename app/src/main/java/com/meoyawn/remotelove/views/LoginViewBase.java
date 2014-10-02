package com.meoyawn.remotelove.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.meoyawn.remotelove.R;

import org.jetbrains.annotations.NotNull;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by adelnizamutdinov on 10/1/14
 */
public class LoginViewBase extends FrameLayout {
    @InjectView(R.id.username) @NotNull EditText username;
    @InjectView(R.id.password) @NotNull EditText password;

    public LoginViewBase(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);
    }
}
