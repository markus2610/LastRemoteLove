package com.meoyawn.remotelove;

import android.app.Activity;
import butterknife.InjectView;
import com.example.flow.screenswitcher.FrameScreenSwitcherView;
import flow.Parcer;
import javax.inject.Inject;
import org.jetbrains.annotations.NotNull;

public class LoveActivityBase extends Activity {
  @Inject @NotNull                     Parcer<Object>          parcer;
  @InjectView(R.id.container) @NotNull FrameScreenSwitcherView container;
}
