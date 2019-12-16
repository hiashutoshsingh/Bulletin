package com.news.khabar.ashu.utils

import android.os.Build
import androidx.annotation.RequiresApi
import org.ocpsoft.prettytime.PrettyTime
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object CommonMethods {

    @JvmStatic
    @RequiresApi(api = Build.VERSION_CODES.N)
    fun getTimeByTimeStamp(stringDate: String?): String? {
        val p = PrettyTime()
        var isTime: String? = null
        try {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
            val date = sdf.parse(stringDate)
            isTime = p.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return isTime
    }

    @JvmStatic
    val country: String
        get() {
            val locale = Locale.getDefault()
            val country = locale.country
            return country.toLowerCase()
        }
}