package com.example.stripeintegration.data.repository

import android.app.Application
import android.widget.Toast
import com.example.stripeintegration.data.model.Response
import com.example.stripeintegration.data.usecase.ClientJSON
import com.example.stripeintegration.data.usecase.CustomerJSON
import com.example.stripeintegration.data.usecase.KeyJSON
import com.example.stripeintegration.domain.network.KtorResponse
import com.example.stripeintegration.domain.repository.MainRepository
import com.example.stripeintegration.utils.DataState
import io.ktor.client.features.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepositoryImpl
@Inject
constructor(
    private val api: KtorResponse,
    private val customerJson: CustomerJSON,
    private val keyJSON: KeyJSON,
    private val clientJSON: ClientJSON,
    private val context: Application
) : MainRepository {

    override suspend fun createCustomer(): Flow<DataState<Response>> {
        return channelFlow {
            send(DataState.Loading(true))
            try {
                val response = api.createCustomer()
                val customerID = customerJson.parse(response)
                Toast.makeText(context, customerID[0], Toast.LENGTH_SHORT).show()
                val key = withContext(Dispatchers.Default) {
                    getKey(customerID[0])
                }
                val client = withContext(Dispatchers.Default) {
                        getClient(customerID[0])
                    }
                if (!key.contains("Error:") && !client.contains("Error:")) {
                    send(DataState.Loading(false))
                    send(
                        DataState.Success(
                            Response(
                                key = key, clientSecret = client, customerId = customerID[0]
                            )
                        )
                    )
                } else {
                    send(DataState.Loading(false))
                    send(DataState.Error(key))
                    Timber.d("Exception : $key")
                }
            } catch (e: Exception) {
                send(DataState.Loading(false))
                send(DataState.Error("Network error"))
                Timber.d("Exception : ${e.message}")
            } catch (e: RedirectResponseException) {
                send(DataState.Loading(false))
                send(DataState.Error("Network error"))
                Timber.d("Exception : ${e.message}")
            } catch (e: ClientRequestException) {
                send(DataState.Loading(false))
                send(DataState.Error("Network error"))
                Timber.d("Exception : ${e.message}")
            } catch (e: ServerResponseException) {
                send(DataState.Loading(false))
                send(DataState.Error("Network error"))
                Timber.d("Exception : ${e.message}")
            }
        }
    }

    override suspend fun getKey(customerID: String): String {
        return try {
            val response = api.getKey(customerID)
            keyJSON.parse(response)[0]
        } catch (e: Exception) {
            Timber.d("Key Exception : ${e.message}")
            Toast.makeText(context, "Key Exception : ${e.message}", Toast.LENGTH_SHORT).show()
            "Error: ${e.message}"
        } catch (e: RedirectResponseException) {
            Timber.d("Key Exception : ${e.message}")
            Toast.makeText(context, "Key Exception : ${e.message}", Toast.LENGTH_SHORT).show()
            "Error: ${e.message}"
        } catch (e: ClientRequestException) {
            Timber.d("Key Exception : ${e.message}")
            Toast.makeText(context, "Key Exception : ${e.message}", Toast.LENGTH_SHORT).show()
            "Error: ${e.message}"
        } catch (e: ServerResponseException) {
            Timber.d("Key Exception : ${e.message}")
            Toast.makeText(context, "Key Exception : ${e.message}", Toast.LENGTH_SHORT).show()
            "Error: ${e.message}"
        }
    }

    override suspend fun getClient(customerID: String): String {
        return try {
            val response = api.getClient(customerID)
            clientJSON.parse(response)[0]
        } catch (e: Exception) {
            Timber.d("Client Exception : ${e.message}")
            Toast.makeText(context, "Key Exception : ${e.message}", Toast.LENGTH_SHORT).show()
            "Error: ${e.message}"
        } catch (e: RedirectResponseException) {
            Timber.d("Client Exception : ${e.message}")
            Toast.makeText(context, "Key Exception : ${e.message}", Toast.LENGTH_SHORT).show()
            "Error: ${e.message}"
        } catch (e: ClientRequestException) {
            Timber.d("Client Exception : ${e.message}")
            Toast.makeText(context, "Key Exception : ${e.message}", Toast.LENGTH_SHORT).show()
            "Error: ${e.message}"
        } catch (e: ServerResponseException) {
            Timber.d("Client Exception : ${e.message}")
            Toast.makeText(context, "Key Exception : ${e.message}", Toast.LENGTH_SHORT).show()
            "Error: ${e.message}"
        }
    }

}