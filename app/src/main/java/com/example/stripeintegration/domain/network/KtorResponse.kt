package com.example.stripeintegration.domain.network

import com.example.stripeintegration.data.network.KtorResponseImpl
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*

interface KtorResponse {

    suspend fun createCustomer(): String

    suspend fun getKey(customerKey: String): String

    suspend fun getClient(customerKey: String): String

    companion object {
        fun create(): KtorResponse {
            return KtorResponseImpl(
                client = HttpClient(Android) {
                    install(Logging) {
                        level = LogLevel.ALL
                    }
                    install(JsonFeature) {
                        serializer = KotlinxSerializer()
                    }
                }
            )
        }
    }
}