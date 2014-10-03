package com.meoyawn.remotelove;

import com.meoyawn.remotelove.api.LastFm;
import com.meoyawn.remotelove.api.Preferences;
import com.meoyawn.remotelove.fragment.RxFragment;
import dagger.Lazy;
import javax.inject.Inject;
import org.jetbrains.annotations.NotNull;

/**
 * Created by adelnizamutdinov on 10/3/14
 */
public class LoveFragmentBase extends RxFragment {
  protected @Inject @NotNull Lazy<LastFm>      lastFmLazy;
  protected @Inject @NotNull Lazy<Preferences> preferencesLazy;
}
