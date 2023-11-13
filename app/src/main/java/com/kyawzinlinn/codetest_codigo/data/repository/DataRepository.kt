package com.kyawzinlinn.codetest_codigo.data.repository

import com.kyawzinlinn.codetest_codigo.domain.model.Diet
import com.kyawzinlinn.codetest_codigo.domain.model.Allergy
import com.kyawzinlinn.codetest_codigo.domain.model.HealthConcern

interface DataRepository {
    suspend fun getAllergies() : List<Allergy>
    suspend fun getDiets(): List<Diet>
    suspend fun getHealthConcerns(): List<HealthConcern>
}