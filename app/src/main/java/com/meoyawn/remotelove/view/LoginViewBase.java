package com.meoyawn.remotelove.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import butterknife.InjectView;
import com.example.flow.screenswitcher.FrameScreenSwitcherView;
import com.meoyawn.remotelove.R;
import flow.Parcer;
import javax.inject.Inject;
import org.jetbrains.annotations.NotNull;

/**
 * Created by adelnizamutdinov on 10/1/14
 */
public class LoginViewBase extends LinearLayout {
  @Inject @NotNull                           Parcer<Object>          parcer;

  public LoginViewBase(Context context, AttributeSet attrs) { super(context, attrs); }
}
