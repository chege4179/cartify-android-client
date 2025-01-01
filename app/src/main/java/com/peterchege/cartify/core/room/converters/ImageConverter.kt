package com.peterchege.cartify.core.room.converters

import androidx.room.TypeConverter
import com.peterchege.cartify.domain.models.Image
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val json = Json {
    encodeDefaults = true
    ignoreUnknownKeys = true
}

class ImageConverter {

    @TypeConverter
    fun toImageList(imageListString: String): List<Image> {
        return json.decodeFromString<List<Image>>(imageListString)
    }

    @TypeConverter
    fun toImageString(imageList: List<Image>): String {
        return json.encodeToString(imageList)

    }


}