package com.meoyawn.remotelove;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meoyawn.remotelove.util.JacksonParcer;
import com.meoyawn.remotelove.view.LoginView;
import dagger.Module;
import dagger.Provides;
import flow.Parcer;
import javax.inject.Singleton;

/**
 * Created by adelnizamutdinov on 10/3/14
 */
@Module(injects = {
    LoveActivity.class, LoginView.class})
public class LoveModule {
  @Provides @Singleton ObjectMapper provideGson() { return new ObjectMapper(); }

  @Provides @Singleton Parcer<Object> provideParcer(ObjectMapper gson) {
    return new JacksonParcer<>(gson);
  }
}
