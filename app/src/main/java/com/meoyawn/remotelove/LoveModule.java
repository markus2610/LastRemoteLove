package com.meoyawn.remotelove;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meoyawn.remotelove.api.ApiPackage;
import com.meoyawn.remotelove.api.LastFm;
import com.meoyawn.remotelove.api.Preferences;
import com.meoyawn.remotelove.api.model.Session;
import com.meoyawn.remotelove.api.model.Status;
import com.meoyawn.remotelove.api.model.Track;
import com.meoyawn.remotelove.effect.Effect;
import com.meoyawn.remotelove.fragment.LoginFragment;
import com.meoyawn.remotelove.fragment.LoveFragment;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import dagger.Module;
import dagger.Provides;
import de.devland.esperandro.Esperandro;
import de.devland.esperandro.serialization.JacksonSerializer;
import java.io.File;
import java.io.IOException;
import javax.inject.Singleton;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.JacksonConverter;
import rx.subjects.ReplaySubject;
import rx.subjects.Subject;
import timber.log.Timber;

/**
 * Created by adelnizamutdinov on 10/3/14
 */
@Module(injects = {
    LoginFragment.class,
    LoveFragment.class,
    LoveActivity.class
},
        library = true)
public class LoveModule {
  final Application application;

  public LoveModule(Application application) {this.application = application;}

  @Provides Context provideContext() { return application; }

  @Provides @Singleton ObjectMapper provideGson() {
    return new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
  }

  @Provides File provideCacheDir(Context context) {
    return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
        ? context.getExternalCacheDir()
        : context.getCacheDir();
  }

  @Provides @Singleton OkHttpClient provideOkHttpClient(File cacheDir) {
    OkHttpClient okHttpClient = new OkHttpClient();
    try {
      okHttpClient.setCache(new Cache(cacheDir, 20 * 1024 * 1024));
    } catch (IOException ignored) {
    }
    return okHttpClient;
  }

  @Provides @Singleton Picasso providePicasso(Context context, OkHttpClient okHttpClient) {
    return new Picasso.Builder(context)
        .downloader(new OkHttpDownloader(okHttpClient))
        .build();
  }

  @Provides @Singleton LastFm provideLastFm(OkHttpClient client, ObjectMapper objectMapper) {
    return new RestAdapter.Builder()
        .setClient(new OkClient(client))
        .setConverter(new JacksonConverter(objectMapper))
        .setEndpoint(ApiPackage.getSERVER())
        .setLogLevel(RestAdapter.LogLevel.FULL)
        .setLog(new RestAdapter.Log() {
          @Override public void log(String message) {
            Timber.d(message);
          }
        })
        .setRequestInterceptor(new RequestInterceptor() {
          @Override public void intercept(RequestFacade request) {
            request.addQueryParam("format", "json");
          }
        })
        .build()
        .create(LastFm.class);
  }

  @Provides @Singleton Preferences providePreferences(Context context, ObjectMapper objectMapper) {
    Esperandro.setSerializer(new JacksonSerializer(objectMapper));
    return Esperandro.getPreferences(Preferences.class, context);
  }

  @Provides @Singleton Subject<Effect<Session>, Effect<Session>> provideLoginFragmentSubject() {
    return ReplaySubject.createWithSize(2);
  }

  @Provides @Singleton Subject<Effect<Status>, Effect<Status>> provideStatusSubject() {
    return ReplaySubject.createWithSize(2);
  }

  @Provides @Singleton Subject<Effect<Track>, Effect<Track>> provideTracksSubject() {
    return ReplaySubject.createWithSize(2);
  }
}
