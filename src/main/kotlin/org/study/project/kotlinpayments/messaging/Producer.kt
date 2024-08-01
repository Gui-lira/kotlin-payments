package org.study.project.kotlinpayments.messaging

import kotlinx.coroutines.channels.Channel
import org.springframework.stereotype.Component
import org.study.project.kotlinpayments.api.request.PaymentRequest

@Component
class Producer(
    private val channel: Channel<PaymentRequest>
) {
    suspend fun send(paymentRequest: PaymentRequest) = channel.send(paymentRequest)
}
