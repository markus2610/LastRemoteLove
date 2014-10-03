package com.meoyawn.remotelove.effect

/**
 * Created by adelnizamutdinov on 10/3/14
 */
trait Effect<T>

enum class Loading : Effect<Any> {
  START
  END
}

data class Success<T>(val t: T) : Effect<T>

data class Failure<T>(val e: Exception) : Effect<Any>