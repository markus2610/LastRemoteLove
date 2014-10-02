package com.meoyawn.remotelove.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.FrameLayout;
import butterknife.InjectView;
import com.meoyawn.remotelove.R;
import org.jetbrains.annotations.NotNull;

/**
 * Created by adelnizamutdinov on 10/3/14
 */
public class UsernameViewBase extends FrameLayout {
  @InjectView(R.id.username) @NotNull EditText username;

  public UsernameViewBase(Context context, AttributeSet attrs) {
    super(context, attrs);
  }
}
