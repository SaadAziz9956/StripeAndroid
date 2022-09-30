package com.example.stripeintegration.data.usecase

import android.widget.Toast
import com.example.stripeintegration.domain.usecase.Parser
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KeyJSON @Inject constructor() : Parser<String> {
    override suspend fun parse(response: String): List<String> {

        val jsonObject = JSONObject(response)
        val key = jsonObject.getString("id")
        val list = mutableListOf<String>()

        list.add(key)

        return list.ifEmpty {
            emptyList()
        }

    }
}