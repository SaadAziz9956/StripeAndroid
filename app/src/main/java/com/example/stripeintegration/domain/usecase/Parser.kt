package com.example.stripeintegration.domain.usecase

import java.io.InputStream

interface Parser<T> {
    suspend fun parse(response: String): List<T>
}