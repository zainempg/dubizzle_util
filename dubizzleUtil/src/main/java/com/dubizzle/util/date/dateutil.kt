package com.dubizzle.util.date

import android.content.Context
import com.dubizzle.util.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun getFormattedPassedTime(
    context:Context,
    date: Date,
    strDesiredDateFormat: String,
    currentLanguage: String
): String {
    val currentDate = Date(System.currentTimeMillis())

    val days = ((currentDate.time - date.time) / (1000 * 60 * 60 * 24)).toInt()
    val months = days / 30
    val years = months / 12

    if (years >= 1 || months >= 1 || days >= 6) {
        // return normal date
        val sdf = SimpleDateFormat(strDesiredDateFormat, Locale.getDefault())
        return sdf.format(date)
    }

    return when {
        days > 1 -> context.getString(R.string.str_days_ago, days.toString())
        days == 1 -> context.getString(R.string.str_yesterday)
        else -> {
            val seconds = ((currentDate.time - date.time) / 1000).toInt()
            val minutes = (seconds / 60).toInt()
            val hours = (minutes / 60).toInt()

            when {
                currentLanguage == "ar" && hours > 10 -> context.getString(
                    R.string.str_more_than_ten_hours,
                    hours.toString()
                )

                currentLanguage == "ar" && hours in 3..10 -> context.getString(
                    R.string.str_three_to_ten_hours,
                    hours.toString()
                )

                currentLanguage == "ar" && hours == 2 -> context.getString(R.string.str_two_hours_ago)
                hours > 1 -> context.getString(R.string.str_hours_ago, hours.toString())
                hours == 1 -> context.getString(R.string.str_one_hour_ago)
                minutes > 1 -> context.getString(R.string.str_minutes_ago, minutes.toString())
                else -> context.getString(R.string.str_just_now)
            }
        }
    }
}


