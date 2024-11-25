package com.example.learnapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Question(
    val question: String = "", // текст вопроса
    val answer: List<String> = listOf(), // список ответов
    val yes: String = "" // правильный ответ
) : Parcelable

