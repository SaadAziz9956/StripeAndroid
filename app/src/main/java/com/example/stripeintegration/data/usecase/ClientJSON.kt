package com.example.stripeintegration.data.usecase

import com.example.stripeintegration.domain.usecase.Parser
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClientJSON @Inject constructor() : Parser<String> {

    override suspend fun parse(response: String): List<String> {

        val jsonObject = JSONObject(response)
        val clientSecret = jsonObject.getString("client_secret")
        val list = mutableListOf<String>()

        list.add(clientSecret)

        return list.ifEmpty {
            emptyList()
        }

    }
}