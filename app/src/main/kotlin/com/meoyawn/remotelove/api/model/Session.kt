package com.meoyawn.remotelove.api.model

/**
 * Created by adelnizamutdinov on 10/3/14
 */
data class Session {
  var name: String = ""
  var key: String = ""
}

data class LastFmResponse {
  var session: Session? = null
  var error: Int? = null
  var message: String? = null
}

data class Status {
  var status: String = ""
}