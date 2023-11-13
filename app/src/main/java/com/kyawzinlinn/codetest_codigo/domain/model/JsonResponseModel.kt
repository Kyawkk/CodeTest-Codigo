package com.kyawzinlinn.codetest_codigo.domain.model

import java.io.Serializable

data class JsonResponseModel<T>(
    val data: List<T>
): Serializable
