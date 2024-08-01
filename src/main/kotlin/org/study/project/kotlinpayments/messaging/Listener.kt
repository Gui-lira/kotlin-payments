package org.study.project.kotlinpayments.messaging

import org.study.project.kotlinpayments.api.request.PaymentRequest

interface Listener {
    suspend fun receive(paymentRequest: PaymentRequest)
}
