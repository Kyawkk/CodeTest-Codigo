package com.kyawzinlinn.codetest_codigo.data.local

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.reflect.TypeToken
import com.kyawzinlinn.codetest_codigo.domain.model.Allergy
import com.kyawzinlinn.codetest_codigo.domain.model.Diet
import com.kyawzinlinn.codetest_codigo.domain.model.HealthConcern
import com.kyawzinlinn.codetest_codigo.domain.model.JsonResponseModel
import com.kyawzinlinn.codetest_codigo.uitls.toAllergy
import com.kyawzinlinn.codetest_codigo.uitls.toDiet
import com.kyawzinlinn.codetest_codigo.uitls.toHealthConcern
import org.json.JSONObject

const val ALLERGIES_JSON = "allergies.json"
const val DIETS_JSON = "diets.json"
const val HEALTHCONCERN_JSON = "healthconcern.json"

object JsonDataSource {
    private fun getJsonDataFromAsset(
        context: Context,
        fileName: String
    ) : String {
        var jsonString: String = ""

        try {
            jsonString = context.assets.open(fileName).bufferedReader().use {
                it.readText()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return jsonString
    }

    fun <T> getJsonData(context: Context, fileName: String, type: Class<T>) : List<T>{
        val jsonString = getJsonDataFromAsset(context,fileName)
        val jsonObject = JSONObject(jsonString)
        val jsonArray = jsonObject.getJSONArray("data")
        Log.d("TAG", "getJsonData: $type")
        return when (type) {
            Allergy::class.java -> jsonArray.toAllergy() as List<T>
            Diet::class.java -> jsonArray.toDiet() as List<T>
            HealthConcern::class.java -> jsonArray.toHealthConcern() as List<T>
            else -> emptyList()
        }
    }
}