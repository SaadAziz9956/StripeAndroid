package com.example.stripeintegration.data.network

import com.example.stripeintegration.domain.network.KtorResponse
import com.example.stripeintegration.utils.Constants
import com.example.stripeintegration.utils.Constants.AUTHORIZATION
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlin.text.get

class KtorResponseImpl(
    private val client: HttpClient
) : KtorResponse {

    override suspend fun createCustomer(): String {
        return client.request("https://api.stripe.com/v1/customers") {
            method = HttpMethod.Get
            header("Authorization", AUTHORIZATION)
        }
    }

    override suspend fun getKey(customerKey: String): String {
        return client.request("https://api.stripe.com/v1/ephemeral_keys") {
            method = HttpMethod.Post
            header("Authorization", AUTHORIZATION)
            header("Stripe-Version", "2022-08-01")
            parameter("customer", customerKey)
        }
    }

    override suspend fun getClient(customerKey: String): String {
        return client.request("https://api.stripe.com/v1/payment_intents") {
            method = HttpMethod.Post
            header("Authorization", AUTHORIZATION)
            parameter("customer", customerKey)
            parameter("amount", "1000")
            parameter("currency", "usd")
            parameter("automatic_payment_methods[enabled]", "true")
        }
    }

}