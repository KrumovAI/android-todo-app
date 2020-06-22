package utils

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    private var dateFormat: String = "dd.MM.yyyy"
    private var timeFormat: String = "HH:mm"

    private var dateFormatter = SimpleDateFormat(dateFormat)
    private var timeFormatter = SimpleDateFormat(timeFormat)
    private var dateTimeFormatter = SimpleDateFormat("$dateFormat $timeFormat")

    fun createDate(year: Int, month: Int, day: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)

        return calendar.time
    }

    fun stringifyDate(date: Date?): String {
        if (date == null) {
            return ""
        }

        return this.dateFormatter.format(date)
    }

    fun stringifyDateTime(date: Date?): String {
        if (date == null) {
            return ""
        }

        return this.dateTimeFormatter.format(date)
    }

    fun parse(dateString: String?, timeString: String?): Date? {
        var dateTimeString: String? = dateString

        if (dateString.isNullOrBlank()) {
            return null
        }

        if (!timeString.isNullOrBlank()) {
            dateTimeString += " $timeString"
        }

        return try {
            this.dateTimeFormatter.parse(dateTimeString)
        } catch (e: Exception) {
            null
        }
    }

    fun parse(dateTimeString: String?): Date? {
        if (dateTimeString.isNullOrBlank()) {
            return null
        }

        return try {
            this.dateTimeFormatter.parse(dateTimeString)
        } catch (e: Exception) {
            null
        }
    }
}