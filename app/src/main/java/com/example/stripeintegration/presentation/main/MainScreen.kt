package com.example.stripeintegration.presentation.main

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stripeintegration.data.model.Response
import com.example.stripeintegration.utils.Constants
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MainScreen(
    paymentSheet: PaymentSheet, viewModel: MainScreenViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    var loadingPayment by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(context) {
        viewModel.result
            .collectLatest { result ->
                loadingPayment = false
                PaymentConfiguration.init(context, Constants.PUBLISH_KEY)
                paymentFlow(paymentSheet, result)
            }
    }


    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Button(onClick = {
            loadingPayment = true
            viewModel.init()
        }) {
            Text(
                text = "Subscribe Now", fontSize = 16.sp, modifier = Modifier.padding(
                    horizontal = 20.dp, vertical = 15.dp
                )
            )
        }

        Spacer(modifier = Modifier.size(50.dp))

        if (loadingPayment) {
            CircularProgressIndicator()
        }

    }


}

fun paymentFlow(
    paymentSheet: PaymentSheet, response: Response?
) {
    response?.let {
        paymentSheet.presentWithPaymentIntent(
            it.clientSecret, PaymentSheet.Configuration(
                "ABC Company", PaymentSheet.CustomerConfiguration(
                    it.customerId, it.key
                )
            )
        )
    }
}
