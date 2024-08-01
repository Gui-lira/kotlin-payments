package org.study.project.kotlinpayments.controller.payment

import kotlinx.coroutines.flow.Flow
import org.study.project.kotlinpayments.api.dto.PaymentDTO
import java.time.Instant
import java.util.UUID

interface PaymentHandler {
    suspend fun createPayment(
        userId: UUID,
        amount: Long,
    )

    suspend fun findAllByUserId(userId: UUID): Flow<PaymentDTO>

    suspend fun findByUserIdAndDate(
        userId: UUID,
        date: Instant,
    ): Flow<PaymentDTO>
}
