package com.meoyawn.remotelove.api.model

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by adelnizamutdinov on 10/3/14
 */
data class Image {
  JsonProperty("#text") var url: String = ""
  var size: String = ""
}

data class Artist {
  var name: String = ""
  var image: Array<Image> = array()
}

data class Attr {
  var nowplaying: Boolean = false
}

data class Track {
  var artist: Artist = Artist()
  var loved: Int = 0
  var name: String = ""
  var url: String = ""
  var image: Array<Image> = array()
  JsonProperty("@attr") var attr: Attr? = null

  class object {
    val EMPTY = Track()
  }
}

data class RecentTracks {
  var track: Array<Track> = array()
}

data class RecentTracksHolder {
  var recenttracks: RecentTracks = RecentTracks()
}
