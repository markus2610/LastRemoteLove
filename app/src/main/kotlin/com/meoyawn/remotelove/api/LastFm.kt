package com.meoyawn.remotelove.api

import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.codec.binary.Hex

/**
 * Created by adelnizamutdinov on 10/3/14
 */

public val SERVER: String = "https://ws.audioscrobbler.com/2.0"
public val GET_RECENT_TRACKS: String = "user.getRecentTracks"
public val GET_MOBILE_SESSION: String = "auth.getMobileSession"

fun md5(string: String): String = String(Hex.encodeHex(DigestUtils.md5(string)) as CharArray)

fun apiSig(apiKey: String,
           method: String,
           password: String,
           username: String,
           secret: String): String =
    md5("api_key${apiKey}method${method}password${password}username${username}$secret")
