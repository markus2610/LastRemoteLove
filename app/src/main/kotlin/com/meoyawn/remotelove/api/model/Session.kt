package com.meoyawn.remotelove.api.model

/**
 * Created by adelnizamutdinov on 10/3/14
 */
data class Session(val name: String, val key: String)
data class SessionHolder(val session: Session)