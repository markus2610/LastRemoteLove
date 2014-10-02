package com.meoyawn.remotelove.util

import android.os.Parcel
import android.os.Parcelable

import com.fasterxml.jackson.databind.ObjectMapper

import java.io.IOException
import java.io.StringWriter

import flow.Parcer

/**
 * Created by adel on 2/1/14
 */
class JacksonParcer<T>(val objectMapper: ObjectMapper) : Parcer<T> {
    override fun wrap(instance: T?): Parcelable? {
        try {
            val json = encode(instance)
            return Wrapper(json)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    override fun unwrap(parcelable: Parcelable?): T? {
        val wrapper = parcelable as Wrapper
        try {
            return decode(wrapper.json)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    throws(javaClass<IOException>())
    fun encode(instance: T?): String {
        val stringWriter = StringWriter()
        val generator = objectMapper.getFactory()?.createGenerator(stringWriter)

        try {
            generator?.writeStartObject()
            generator?.writeFieldName(instance.javaClass.getName())
            generator?.writeObject(instance)
            generator?.writeEndObject()

            return stringWriter.toString()
        } finally {
            generator?.close()
            stringWriter.close()
        }
    }

    throws(javaClass<IOException>())
    fun decode(json: String): T? {
        val parser = objectMapper.getFactory()?.createParser(json)

        try {
            parser?.nextToken()
            parser?.nextToken()
            parser?.nextToken()

            val clazz: Class<T> = Class.forName(parser?.getCurrentName()) as Class<T>
            return parser?.readValueAs(clazz)
        } catch (e: ClassNotFoundException) {
            throw RuntimeException(e)
        } finally {
            parser?.close()
        }
    }

    class Wrapper(val json: String) : Parcelable {
        override fun describeContents(): Int {
            return 0
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            out.writeString(json)
        }

        class object {
            val CREATOR: Parcelable.Creator<Wrapper> = object : Parcelable.Creator<Wrapper> {
                override fun createFromParcel(`in`: Parcel): Wrapper {
                    val json = `in`.readString()
                    return Wrapper(json)
                }

                override fun newArray(size: Int): Array<Wrapper> {
                    return arrayOfNulls<Wrapper>(size) as Array<Wrapper>
                }
            }
        }
    }
}