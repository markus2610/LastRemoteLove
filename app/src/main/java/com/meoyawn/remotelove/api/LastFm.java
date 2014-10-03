package com.meoyawn.remotelove.api;

import com.meoyawn.remotelove.api.model.RecentTracksHolder;
import com.meoyawn.remotelove.api.model.SessionHolder;
import org.jetbrains.annotations.NotNull;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by adelnizamutdinov on 10/3/14
 */
public interface LastFm {
  @FormUrlEncoded
  @POST("/?method=auth.getMobileSession")
  Observable<SessionHolder> getMobileSession(@Field("password") @NotNull String password,
                                             @Field("username") @NotNull String username,
                                             @Field("api_key") @NotNull String apiKey,
                                             @Field("api_sig") @NotNull String apiSig);

  @GET("/?method=user.getRecentTracks&nowplaying=true&extended=1&limit=1")
  Observable<RecentTracksHolder> getRecentTracks(@Query("user") @NotNull String user,
                                                 @Query("api_key") @NotNull String apiKey);
}
