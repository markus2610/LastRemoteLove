package com.meoyawn.remotelove;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.InjectView;
import com.meoyawn.remotelove.api.LastFm;
import com.meoyawn.remotelove.api.Preferences;
import com.meoyawn.remotelove.api.model.Track;
import com.meoyawn.remotelove.effect.Effect;
import com.meoyawn.remotelove.fragment.RxFragment;
import com.squareup.picasso.Picasso;
import dagger.Lazy;
import javax.inject.Inject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rx.subjects.Subject;

/**
 * Created by adelnizamutdinov on 10/3/14
 */
public class LoveFragmentBase extends RxFragment {
  protected @Inject @NotNull Lazy<LastFm>                          lastFmLazy;
  protected @Inject @NotNull Lazy<Picasso>                         picassoLazy;
  protected @Inject @NotNull Lazy<Preferences>                     preferencesLazy;
  protected @Inject @NotNull Subject<Effect<Track>, Effect<Track>> subject;

  protected @InjectView(R.id.album_image) @NotNull ImageView albumImage;
  protected @InjectView(R.id.artist) @NotNull      TextView  artist;
  protected @InjectView(R.id.title) @NotNull       TextView  title;

  @Nullable @Override public View onCreateView(@NotNull LayoutInflater inflater,
                                               ViewGroup container,
                                               Bundle savedInstanceState) {
    return inflater.inflate(R.layout.love, container, false);
  }
}
