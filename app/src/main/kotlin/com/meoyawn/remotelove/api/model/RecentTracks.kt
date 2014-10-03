package com.meoyawn.remotelove.api.model

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by adelnizamutdinov on 10/3/14
 */
data class Image(JsonProperty("#text") val url: String, val size: String)
data class Artist(val name: String, val image: Array<Image>)
data class Attr(val nowplaying: Boolean)
data class Track(val artist: Artist,
                 val loved: Boolean,
                 val name: String,
                 val url: String,
                 val image: Array<Image>,
                 JsonProperty("@attr") val attr: Attr?)
data class RecentTracks(val track: Array<Track>)
data class RecentTracksHolder(val recenttracks: RecentTracks)
