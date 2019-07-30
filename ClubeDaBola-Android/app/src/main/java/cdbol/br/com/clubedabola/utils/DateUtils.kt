package cdbol.br.com.clubedabola.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {

    fun convertHourStartText(hourOfDay: Int, minute: Int, text: String) : String{

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

        strHrsToShow.append(text)
        strHrsToShow.append(hourOfDay)
        strHrsToShow.append(" : ")
        strHrsToShow.append(min)

        return strHrsToShow.toString()
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


    fun convertDateText(year: Int, month: Int, dayOfMonth: Int) : String{

        val dataFormatada = StringBuilder()
        dataFormatada.append(dayOfMonth)
        dataFormatada.append(" de ")
        dataFormatada.append(convertMountText(month))
        dataFormatada.append(" de ")
        dataFormatada.append(year)

         return dataFormatada.toString()

    }

    fun convertDateNumeric(year: Int, month: Int, dayOfMonth: Int) : String{

        val dataFormatada = StringBuilder()
        dataFormatada.append(dayOfMonth)
        dataFormatada.append("/")
        dataFormatada.append(convertMountZero(month))
        dataFormatada.append("/")
        dataFormatada.append(year)

        return dataFormatada.toString()

    }

    fun convertMountText(month: Int): String {

        val monthString = when (month) {
            0 -> "Janeiro"
            1 -> "Fevereiro"
            2 -> "Março"
            3 -> "Abril"
            4 -> "Maio"
            5 -> "Junho"
            6 -> "Julho"
            7 -> "Agosto"
            8 -> "Setembro"
            9 -> "Outubro"
            10 -> "Novembro"
            11 -> "Dezembro"
            else -> "Invalid month"
        }


        return monthString
    }

    fun toApiDate(date: Date) : String {
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(date)
    }

    fun convertMountZero(month: Int): String {

        return when (month) {
            0 -> "01"
            1 -> "02"
            2 -> "03"
            3 -> "04"
            4 -> "05"
            5 -> "06"
            6 -> "07"
            7 -> "08"
            8 -> "09"
            9 -> "10"
            10 -> "11"
            11 -> "12"
            else -> "Invalid month"
        }

    }
}