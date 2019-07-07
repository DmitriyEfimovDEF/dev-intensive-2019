package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = SECOND * 60
const val HOUR = MINUTE * 60
const val DAY = HOUR * 24

fun Date.format(pattern: String="HH:mm:ss dd:MM:yy"):String{
    val dateFormate = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormate.format(this)
}
fun Date.add(value: Int, units: TimeUnits): Date {
    var time = this.time
    time += when (units) {
        TimeUnits.SECONDS-> value * SECOND
        TimeUnits.MINUTES -> value * MINUTE
        TimeUnits.HOURS -> value * HOUR
        TimeUnits.DAYS -> value * DAY
    }
    this.time = time

    return this
}

enum class TimeUnits {
    SECONDS,
    MINUTES,
    HOURS,
    DAYS

}
fun Date.humanizeDiff(date:Date = Date()): String {

    return "date"
}