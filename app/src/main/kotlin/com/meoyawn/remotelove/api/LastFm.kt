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
val TRACK_LOVE = "track.love"
val TRACK_UNLOVE = "track.unlove"

fun md5(string: String): String = String(Hex.encodeHex(DigestUtils.md5(string))!!)

fun apiSig(apiKey: String,
           password: String,
           username: String,
           secret: String): String =
    md5("api_key${apiKey}method${GET_MOBILE_SESSION}password${password}username${username}$secret")

fun apiSig(apiKey: String,
           artist: String,
           method: String,
           sk: String,
           track: String,
           secret: String): String =
    md5("api_key${apiKey}artist${artist}method${method}sk${sk}track${track}$secret")
