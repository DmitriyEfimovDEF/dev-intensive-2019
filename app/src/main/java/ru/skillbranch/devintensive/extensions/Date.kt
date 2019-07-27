package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs
import kotlin.math.absoluteValue

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String="HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when(units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }

    this.time = time
    return this
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY;

    fun plural(value: Int): String{
        return "$value ${getPluralForm(value, this)}"
    }
}

val Int.sec get() = this * SECOND
val Int.min get() = this * MINUTE
val Int.hour get() = this * HOUR
val Int.day get() = this * DAY

val Long.asMin get() = this.absoluteValue / MINUTE
val Long.asHour get() = this.absoluteValue / HOUR
val Long.asDay get() = this.absoluteValue / DAY

fun Date.humanizeDiff(date: Date = Date()): String {
    val diff = ((date.time + 200) / 1000 - (time + 200) / 1000) * 1000

    return if (diff >= 0) {
        when (diff) {
            in 0.sec..1.sec -> "только что"
            in 2.sec..45.sec -> "несколько секунд назад"
            in 46.sec..75.sec -> "минуту назад"
            in 76.sec..45.min -> "${minutesAsCounter(diff.asMin)} назад"
            in 46.min..75.min -> "час назад"
            in 76.min..22.hour -> "${hoursAsCounter(diff.asHour)} назад"
            in 23.hour..26.hour -> "день назад"
            in 27.hour..360.day -> "${daysAsCounter(diff.asDay)} назад"
            else -> "более года назад"
        }
    } else {
        when (diff) {
            in (-1).sec..0.sec -> "прямо сейчас"
            in (-45).sec..(-1).sec -> "через несколько секунд"
            in (-75).sec..(-45).sec -> "через минуту"
            in (-45).min..(-75).sec -> "через ${minutesAsCounter(diff.asMin)}"
            in (-75).min..(-45).min -> "через час"
            in (-22).hour..(-75).min -> "через ${hoursAsCounter(diff.asHour)}"
            in (-26).hour..(-22).hour -> "через день"
            in (-360).day..(-26).hour -> "через ${daysAsCounter(diff.asDay)}"
            else -> "более чем через год"
        }
    }
}

private fun minutesAsCounter(value: Long) = when (value.asCounter) {
    Counter.ONE -> "$value минуту"
    Counter.FEW -> "$value минуты"
    Counter.MANY -> "$value минут"
}

private fun hoursAsCounter(value: Long) = when (value.asCounter) {
    Counter.ONE -> "$value час"
    Counter.FEW -> "$value часа"
    Counter.MANY -> "$value часов"
}

private fun daysAsCounter(value: Long) = when (value.asCounter) {
    Counter.ONE -> "$value день"
    Counter.FEW -> "$value дня"
    Counter.MANY -> "$value дней"
}


enum class Counter(private val second: String, private val minute: String, private val hour: String, private val day: String){
    ONE("секунду", "минуту", "час", "день"),
    FEW("секунды", "минуты", "часа", "дня"),
    MANY("секунд","минут", "часов", "дней");

    fun get(unit: TimeUnits): String {
        return when(unit){
            TimeUnits.SECOND -> second
            TimeUnits.MINUTE -> minute
            TimeUnits.HOUR -> hour
            TimeUnits.DAY -> day
        }
    }
}

fun getPluralForm(amount: Int, units: TimeUnits): String {

    return when(val posAmount = abs(amount) % 100){
        1 -> Counter.ONE.get(units)
        in 2..4 -> Counter.FEW.get(units)
        0, in 5..19 -> Counter.MANY.get(units)
        else -> getPluralForm(posAmount % 10, units)
    }
}



val Long.asCounter
    get() = when {
        this % 100L in 5L..20L -> Counter.MANY
        this % 10L == 1L -> Counter.ONE
        this % 10L in 2L..4L -> Counter.FEW
        else -> Counter.MANY
    }




