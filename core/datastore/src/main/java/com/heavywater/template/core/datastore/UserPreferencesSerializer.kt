package com.heavywater.template.core.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

object UserPreferencesSerializer : Serializer<UserPreferences> {

    override val defaultValue: UserPreferences = UserPreferences()

    override suspend fun readFrom(input: InputStream): UserPreferences =
        try {
            UserPreferences.ADAPTER.decode(input)
        } catch (e: IOException) {
            throw CorruptionException("Cannot read proto.", e)
        }

    override suspend fun writeTo(t: UserPreferences, output: OutputStream) {
        UserPreferences.ADAPTER.encode(output, t)
    }
}