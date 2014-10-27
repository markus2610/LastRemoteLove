package com.meoyawn.remotelove.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.InjectView;
import com.meoyawn.remotelove.R;
import com.meoyawn.remotelove.api.LastFm;
import com.meoyawn.remotelove.api.Preferences;
import com.meoyawn.remotelove.api.model.Status;
import com.meoyawn.remotelove.api.model.Track;
import com.meoyawn.remotelove.effect.Effect;
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
  @Inject @NotNull Lazy<LastFm>                            lastFmLazy;
  @Inject @NotNull Lazy<Picasso>                           picassoLazy;
  @Inject @NotNull Lazy<Preferences>                       preferencesLazy;
  @Inject @NotNull Subject<Effect<Status>, Effect<Status>> statusSubject;
  @Inject @NotNull Subject<Effect<Track>, Effect<Track>>   tracksSubject;

  @InjectView(R.id.album_image) @Nullable ImageView   albumImage;
  @InjectView(R.id.artist) @Nullable      TextView    artist;
  @InjectView(R.id.title) @Nullable       TextView    title;
  @InjectView(R.id.love) @Nullable        ImageButton love;
  @InjectView(R.id.cover_frame) @Nullable ViewGroup   coverFrame;
  @InjectView(R.id.more) @Nullable        View        more;
  @InjectView(R.id.mainFrame) @Nullable   FrameLayout mainFrame;
  @InjectView(R.id.progress) @Nullable    ProgressBar progressBar;

  @Nullable @Override public View onCreateView(@NotNull LayoutInflater inflater,
                                               ViewGroup container,
                                               Bundle savedInstanceState) {
    return inflater.inflate(R.layout.love, container, false);
  }
}
