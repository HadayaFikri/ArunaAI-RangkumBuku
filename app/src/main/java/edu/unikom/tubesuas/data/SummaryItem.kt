package edu.unikom.tubesuas.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class SummaryItem(
    val prompt: String,
    val response: String,
    val timestamp: Long
): Parcelable
