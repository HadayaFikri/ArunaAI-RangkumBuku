package edu.unikom.tubesuas.data

data class FaqItem(
    val question: String,
    val answer: String,
    var isExpanded: Boolean = false
)