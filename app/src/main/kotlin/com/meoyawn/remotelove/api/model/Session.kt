package com.meoyawn.remotelove.api.model

/**
 * Created by adelnizamutdinov on 10/3/14
 */
data class Session(var name: String = "", var key: String = "")
data class SessionHolder(val session: Session = Session())