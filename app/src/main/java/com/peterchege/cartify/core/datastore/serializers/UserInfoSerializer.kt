package com.peterchege.cartify.core.datastore.serializers

import androidx.datastore.core.Serializer
import com.peterchege.cartify.domain.models.User
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Suppress("BlockingMethodInNonBlockingContext")
object UserInfoSerializer : Serializer<User?> {

    override val defaultValue: User?
        get() = null

    override suspend fun readFrom(input: InputStream): User? {
        return try {
            Json.decodeFromString(
                deserializer = User.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: User?, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = User.serializer(),
                value = t ?: User(
                    _id =  "",
                    fullname =  "",
                    email =  "",
                    phoneNumber =  "",
                    password =  "",
                    address =  ""
                )
            ).encodeToByteArray()
        )
    }
}