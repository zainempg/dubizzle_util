package com.dubizzle.util.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * [KSerializer] implementation for [Date] objects.
 *
 * This class provides serialization and deserialization logic for [Date] objects
 * using a specific ISO 8601 date/time format.
 *
 * The date/time format used is "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", representing:
 *   - yyyy: Year (e.g., 2023)
 *   - MM: Month (01-12)
 *   - dd: Day of month (01-31)
 *   - 'T': Literal character 'T' separating date and time
 *   - HH: Hour (00-23)
 *   - mm: Minute (00-59)
 *   - ss: Second (00-59)
 *   - SSSSSS: Microseconds (000000-999999)
 *   - 'Z': Literal character 'Z' indicating UTC timezone
 *
 * Example of a formatted date string: "2025-01-05T05:37:47.097830Z"
 *
 * This serializer is intended for use with kotlinx.serialization.
 */
class DateSerializer : KSerializer<Date> {

    //   sample date "2025-01-05T05:37:47.097830Z"
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.US)
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Date) {
        val dateString = dateFormat.format(value)
        encoder.encodeString(dateString)
    }

    override fun deserialize(decoder: Decoder): Date {
        val dateString = decoder.decodeString()
        return dateFormat.parse(dateString)!!
    }
}