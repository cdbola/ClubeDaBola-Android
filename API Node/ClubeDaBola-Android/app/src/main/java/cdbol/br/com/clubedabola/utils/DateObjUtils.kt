package cdbol.br.com.clubedabola.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateObjUtils {

    fun toSimpleString(date: Date): String {
        val format = SimpleDateFormat("dd/MM/yyy")
        return format.format(date)
    }

    fun formatDateApi(stgDate: String): String{
        val format = SimpleDateFormat("dd/MM/yyyy")
        val date = format.parse(stgDate)
        format.applyPattern("yyyy-M-dd")
        return format.format(date)
    }

    fun formatDateServer(stgDate: String): String{
        val format = SimpleDateFormat("dd/MM/yyyy")
        val date = format.parse(stgDate)
        format.applyPattern("dd-M-yyyy")
        return format.format(date)
    }

    fun formatDate(stgDate: String): String{
        val format = SimpleDateFormat("dd-M-yyyy")
        val date = format.parse(stgDate)
        format.applyPattern("dd/MM/yyyy")
        return format.format(date)
    }


    fun toDateAPI(data: String): String {
        var data = data
        data = data.replace("/".toRegex(), "")
        return if (data.length < 8) {
            data.substring(0, 4) + "-" + data.substring(4, 6) + "-" + data.substring(6, 7)
        } else {
            data.substring(0, 4) + "-" + data.substring(4, 6) + "-" + data.substring(6, 8)
        }
    }


    @Throws(ParseException::class)
    fun stringToDate(date: String, format: String): Date {
        val simpleFormat = SimpleDateFormat(format, Locale.US)
        return simpleFormat.parse(date)
    }

    fun hourApi(hourOfDay: Int, minute: Int) : String{

        val datetime = Calendar.getInstance()
        datetime.set(Calendar.HOUR_OF_DAY, hourOfDay)
        datetime.set(Calendar.MINUTE, minute)

        var min = StringBuffer()


        if (minute < 10) {
            min.append(0)
            min.append(minute)
        } else {
            min.append(minute)
        }

        var strHrsToShow = StringBuilder()

        strHrsToShow.append(hourOfDay)
        strHrsToShow.append(":")
        strHrsToShow.append(min)

        return strHrsToShow.toString()
    }


    fun toCalendar(hourOfDay: Int, minute: Int) : Calendar {

        val datetime = Calendar.getInstance()
        datetime.set(Calendar.HOUR_OF_DAY, hourOfDay)
        datetime.set(Calendar.MINUTE, minute)
        return datetime
    }
}