package com.meoyawn.remotelove;

import com.fasterxml.jackson.databind.ObjectMapper;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by adelnizamutdinov on 10/3/14
 */
@Module(injects = {
    LoveActivity.class,
    LoginFragmentBase.class
},
        library = true)
public class LoveModule {
  @Provides @Singleton ObjectMapper provideGson() { return new ObjectMapper(); }
}
