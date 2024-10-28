package com.example.myapplication.retrofitPacket

import android.util.Log
import com.example.myapplication.dto.User
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.text.NumberFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoUnit
import java.util.Locale

data class ProjectDetail(
    val projectId: Int,
    val goalAmount: Int,
    var currentAmount: Int,
    val title: String,
    val contents: String,
    val startDate: String,
    val endDate: String,
    val perPrice: Int,
    val thumbnail: String,
    val user: UserPacket,
    val category: CategoryPacket,
    val numOfSupport: Int,
    val numOfFavorite: Int
) : Serializable {
    fun calculateDday(): String {
        return try {
            Log.d("dateParsing5", "${endDate}")
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            val endLocalDate = LocalDateTime.parse(endDate, formatter).toLocalDate()

            "${ChronoUnit.DAYS.between(LocalDate.now(), endLocalDate)}일 남음"
        } catch (e: DateTimeParseException) {
            Log.e("DateParsing", "Failed to parse date: $endDate", e)
            "날짜 형식 오류"
        }
    }

    fun progress(): Int {
        val progressPercentage: Int = ((currentAmount.toDouble() / goalAmount) * 100).toInt()
        return progressPercentage
    }

    fun percent(): String {
        return progress().toString() + "% 달성"
    }

    fun formattedAmount(): String {
        val numberFormat = NumberFormat.getNumberInstance(Locale.KOREA)
        return numberFormat.format(currentAmount)
    }
}