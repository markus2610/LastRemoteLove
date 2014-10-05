package com.meoyawn.remotelove;

import android.app.Activity;
import com.meoyawn.remotelove.api.Preferences;
import dagger.Lazy;
import javax.inject.Inject;
import org.jetbrains.annotations.NotNull;

/**
 * Created by adelnizamutdinov on 10/5/14
 */
public class LoveActivityBase extends Activity {
  protected @Inject @NotNull Lazy<Preferences> preferencesLazy;
}
