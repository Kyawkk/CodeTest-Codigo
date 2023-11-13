package com.kyawzinlinn.codetest_codigo.domain.model

data class Question(
    val id: Int,
    val question: String,
    val options: List<String>,
    val answerIndex: Int,
)
