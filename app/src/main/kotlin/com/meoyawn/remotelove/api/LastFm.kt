package com.meoyawn.remotelove.api

import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.codec.binary.Hex

/**
 * Created by adelnizamutdinov on 10/3/14
 */

val API_KEY = "9e2f8abdca9a2833a339a0502d97c8bf"
val API_SECRET = "335d2bc1ecf5c4ac3c5eb453b511fe5d"
val SERVER: String = "https://ws.audioscrobbler.com/2.0"
val GET_RECENT_TRACKS: String = "user.getRecentTracks"
val GET_MOBILE_SESSION: String = "auth.getMobileSession"

fun md5(string: String): String = String(Hex.encodeHex(DigestUtils.md5(string)) as CharArray)

fun apiSig(apiKey: String,
           method: String,
           password: String,
           username: String,
           secret: String): String =
    md5("api_key${apiKey}method${method}password${password}username${username}$secret")
