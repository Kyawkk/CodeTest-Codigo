package com.kyawzinlinn.uidesign.model

import androidx.annotation.DrawableRes

data class Event(
    val title: String,
    val description: String,
    val status: String,
    @DrawableRes val icon: Int,
)
