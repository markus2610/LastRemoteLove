package com.meoyawn.remotelove.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import butterknife.InjectView;
import com.dd.CircularProgressButton;
import com.meoyawn.remotelove.R;
import com.meoyawn.remotelove.api.LastFm;
import com.meoyawn.remotelove.api.Preferences;
import com.meoyawn.remotelove.api.model.Session;
import com.meoyawn.remotelove.effect.Effect;
import dagger.Lazy;
import javax.inject.Inject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rx.subjects.Subject;

/**
 * Created by adelnizamutdinov on 10/3/14
 */
public class LoginFragmentBase extends RxFragment {
  @Inject @NotNull Lazy<LastFm>                              lastFmLazy;
  @Inject @NotNull Lazy<Preferences>                         preferencesLazy;
  @Inject @NotNull Subject<Effect<Session>, Effect<Session>> subject;

  @InjectView(R.id.username) @NotNull        EditText               username;
  @InjectView(R.id.password) @NotNull        EditText               password;
  @InjectView(R.id.submit_progress) @NotNull CircularProgressButton submit;

  @Nullable @Override public View onCreateView(@NotNull LayoutInflater inflater,
                                               ViewGroup container,
                                               Bundle savedInstanceState) {
    return inflater.inflate(R.layout.login, container, false);
  }
}
