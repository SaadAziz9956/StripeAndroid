package com.example.stripeintegration.domain.repository

import com.example.stripeintegration.data.model.Response
import com.example.stripeintegration.utils.DataState
import kotlinx.coroutines.flow.Flow


interface MainRepository {

    suspend fun createCustomer(): Flow<DataState<Response>>

    suspend fun getKey(customerID: String): String

    suspend fun getClient(customerID: String): String

}