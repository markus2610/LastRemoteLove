package com.meoyawn.remotelove;

import android.app.Application;
import dagger.ObjectGraph;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import timber.log.Timber;

/**
 * Created by adelnizamutdinov on 10/5/14
 */
public class App extends Application {
  protected final @NotNull ObjectGraph objectGraph = ObjectGraph.create(getModules().toArray());

  @Override public void onCreate() {
    super.onCreate();
    Timber.plant(new Timber.DebugTree());
  }

  protected @NotNull List<Object> getModules() {
    ArrayList<Object> objects = new ArrayList<>();
    objects.add(new LoveModule(this));
    return objects;
  }
}
