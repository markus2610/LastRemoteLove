package com.meoyawn.remotelove.api

import retrofit.http.GET
import rx.Observable
import com.meoyawn.remotelove.api.model.RecentTracksHolder
import retrofit.http.Query
import retrofit.http.POST
import retrofit.http.FormUrlEncoded
import retrofit.http.Field
import com.meoyawn.remotelove.api.model.SessionHolder
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.codec.binary.Hex

/**
 * Created by adelnizamutdinov on 10/3/14
 */
val SERVER = "https://ws.audioscrobbler.com/2.0"
val GET_MOBILE_SESSION = "auth.getMobileSession"
val GET_RECENT_TRACKS = "user.getRecentTracks"

fun md5(string: String): String = String(Hex.encodeHex(DigestUtils.md5(string)) as CharArray)

fun apiSig(apiKey: String, method: String, password: String, username: String): String =
    md5("api_key${apiKey}method${method}password${password}username${username}")

trait LastFm {
  FormUrlEncoded
  POST("/?method=$GET_MOBILE_SESSION")
  fun getMobileSession(Field("password") password: String,
                       Field("username") username: String,
                       Field("api_key") apiKey: String,
                       Field("api_sig") apiSig: String): Observable<SessionHolder>

  GET("/?method=$GET_RECENT_TRACKS&nowplaying=true&extended=1&limit=1")
  fun getRecentTracks(Query("user") user: String,
                      Query("api_key") apiKey: String): Observable<RecentTracksHolder>
}
