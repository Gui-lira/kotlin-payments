package org.study.project.kotlinpayments.messaging

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.stereotype.Component
import org.study.project.kotlinpayments.api.request.PaymentRequest
import org.study.project.kotlinpayments.business.repository.PaymentRepository

@Component
class ListenerImpl(
    private val paymentRepository: PaymentRepository,
) : Listener {
    override suspend fun receive(paymentRequest: PaymentRequest) {
        CoroutineScope(Dispatchers.IO).launch {
            paymentRepository.createPayment(paymentRequest.userId, paymentRequest.amount)
        }
    }
}
