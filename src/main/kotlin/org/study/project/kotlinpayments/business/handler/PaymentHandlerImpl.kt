package org.study.project.kotlinpayments.business.handler

import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Component
import org.study.project.kotlinpayments.api.request.PaymentRequest
import org.study.project.kotlinpayments.business.repository.PaymentRepository
import org.study.project.kotlinpayments.common.toDTO
import org.study.project.kotlinpayments.controller.payment.PaymentHandler
import org.study.project.kotlinpayments.messaging.Producer
import java.time.Instant
import java.util.UUID

@Component
class PaymentHandlerImpl(
    private val paymentRepository: PaymentRepository,
    private val producer: Producer,
) : PaymentHandler {
    override suspend fun createPayment(
        userId: UUID,
        amount: Long,
    ) {
        producer.send(PaymentRequest(amount, userId))
    }

    override suspend fun findAllByUserId(userId: UUID) =
        paymentRepository
            .findAllByUserId(userId)
            .map { it.toDTO() }

    override suspend fun findByUserIdAndDate(
        userId: UUID,
        date: Instant,
    ) = paymentRepository
        .findByUserIdAndDate(userId, date)
        .map { it.toDTO() }
}
