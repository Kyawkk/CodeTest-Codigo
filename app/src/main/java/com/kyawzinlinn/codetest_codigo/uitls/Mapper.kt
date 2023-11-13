package com.kyawzinlinn.codetest_codigo.uitls

import android.util.Log
import com.kyawzinlinn.codetest_codigo.domain.model.Allergy
import com.kyawzinlinn.codetest_codigo.domain.model.Diet
import com.kyawzinlinn.codetest_codigo.domain.model.HealthConcern
import org.json.JSONArray
import org.json.JSONObject

fun JSONArray.toDiet(): List<Diet> {
    val data = mutableListOf<Diet>()
    for (i in 0 until this.length()) {
        val jsonObject = this.getJSONObject(i)
        data.add(
            Diet(
                jsonObject.getInt("id"),
                jsonObject.getString("name"),
                jsonObject.getString("tool_tip")
            )
        )
    }
    return data
}

fun JSONArray.toAllergy(): List<Allergy> {
    val data = mutableListOf<Allergy>()
    for (i in 0 until this.length()) {
        val jsonObject = this.getJSONObject(i)
        data.add(
            Allergy(
                jsonObject.getInt("id"),
                jsonObject.getString("name")
            )
        )
    }
    return data
}

fun JSONArray.toHealthConcern(): List<HealthConcern> {
    val data = mutableListOf<HealthConcern>()
    for (i in 0 until this.length()) {
        val jsonObject = this.getJSONObject(i)
        data.add(
            HealthConcern(
                jsonObject.getInt("id"),
                jsonObject.getString("name")
            )
        )
    }
    Log.d("TAG", "toHealthConcern: $data")
    return data
}