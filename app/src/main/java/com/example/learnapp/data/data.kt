package com.example.learnapp.data
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class data(
    val name: String="",
    val anat: String="",
    val info: String="",
    val documentId: String = "",
): Parcelable
