package com.kyawzinlinn.codetest_codigo.data.local

import com.kyawzinlinn.codetest_codigo.domain.model.Question

object DataSource {
    val questions = listOf(
        Question(
            id = 0,
            question = "Is your daily exposure to sun is limited?",
            options = listOf("Yes","No"),
            answerIndex = 0
        ),
        Question(
            id = 1,
            question = "Do you current smoke (tobacco or marijuana)?",
            options = listOf("Yes","No"),
            answerIndex = 0
        ),
        Question(
            id = 2,
            question = "On average, how many alcoholic beverages do you have in a week?",
            options = listOf("0 - 1","2 - 5", "5+"),
            answerIndex = 0
        )
    )
}