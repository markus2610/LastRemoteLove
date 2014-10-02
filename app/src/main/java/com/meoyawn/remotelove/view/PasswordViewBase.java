package com.meoyawn.remotelove.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import butterknife.InjectView;
import com.meoyawn.remotelove.R;
import com.meoyawn.remotelove.widget.RxFrameLayout;
import org.jetbrains.annotations.NotNull;

/**
 * Created by adelnizamutdinov on 10/3/14
 */
public class PasswordViewBase extends RxFrameLayout {
  @InjectView(R.id.password) @NotNull EditText password;

  public PasswordViewBase(Context context, AttributeSet attrs) {
    super(context, attrs);
  }
}
