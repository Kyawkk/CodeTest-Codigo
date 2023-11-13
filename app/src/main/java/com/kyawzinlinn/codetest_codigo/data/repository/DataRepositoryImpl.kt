package com.kyawzinlinn.codetest_codigo.data.repository

import android.content.Context
import com.kyawzinlinn.codetest_codigo.data.local.ALLERGIES_JSON
import com.kyawzinlinn.codetest_codigo.data.local.DIETS_JSON
import com.kyawzinlinn.codetest_codigo.data.local.HEALTHCONCERN_JSON
import com.kyawzinlinn.codetest_codigo.data.local.JsonDataSource
import com.kyawzinlinn.codetest_codigo.domain.model.Allergy
import com.kyawzinlinn.codetest_codigo.domain.model.Diet
import com.kyawzinlinn.codetest_codigo.domain.model.HealthConcern

class DataRepositoryImpl(private val context: Context) : DataRepository {
    override suspend fun getAllergies(): List<Allergy> = JsonDataSource.getJsonData<Allergy>(context, ALLERGIES_JSON,Allergy::class.java)

    override suspend fun getDiets(): List<Diet> = JsonDataSource.getJsonData<Diet>(context, DIETS_JSON, Diet::class.java)

    override suspend fun getHealthConcerns(): List<HealthConcern> = JsonDataSource.getJsonData<HealthConcern>(context, HEALTHCONCERN_JSON, HealthConcern::class.java)
}