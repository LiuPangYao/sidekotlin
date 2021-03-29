package com.slideproject.sidekotlin

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object SettingsSerializer : Serializer<Login> {
    override val defaultValue: Login = Login.getDefaultInstance()

    override fun readFrom(input: InputStream): Login {
        try {
            return Login.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override fun writeTo(
        t: Login,
        output: OutputStream
    ) = t.writeTo(output)
}