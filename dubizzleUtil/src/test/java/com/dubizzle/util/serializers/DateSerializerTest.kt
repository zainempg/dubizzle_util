package com.dubizzle.util.serializers

import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class DateSerializerTest {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.US)
    private val json = Json {
        serializersModule = kotlinx.serialization.modules.SerializersModule {
            contextual(Date::class, DateSerializer())
        }
    }

//    @Test
//    fun serialize() {
//        val date = dateFormat.parse("2025-01-05T05:37:47.097830Z")!!
//
//        val jsonString = json.encodeToString(DateSerializer(), date)
//
//        assertEquals("\"${dateFormat.format(date)}\"", jsonString)
//
//        assertEquals("\"2025-01-05T05:37:47.097830Z\"", jsonString)
//    }

    @Test
    fun deserialize() {
        val jsonString = "\"2025-01-05T05:37:47.097830Z\""
        val date = json.decodeFromString(DateSerializer(), jsonString)
        assertEquals(dateFormat.parse("2025-01-05T05:37:47.097830Z"), date)
    }
}