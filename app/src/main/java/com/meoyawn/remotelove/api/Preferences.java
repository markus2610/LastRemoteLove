package com.meoyawn.remotelove.api;

import com.meoyawn.remotelove.api.model.Session;
import de.devland.esperandro.SharedPreferenceActions;
import de.devland.esperandro.annotations.SharedPreferences;
import org.jetbrains.annotations.Nullable;

/**
 * Created by adelnizamutdinov on 10/3/14
 */
public @SharedPreferences interface Preferences extends SharedPreferenceActions {
  @Nullable Session session();

  void session(@Nullable Session session);
}
