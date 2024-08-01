package org.study.project.kotlinpayments.business.repository

import kotlinx.coroutines.flow.Flow
import org.study.project.kotlinpayments.api.model.Payment
import java.time.Instant
import java.util.UUID

interface PaymentRepository {
    suspend fun createPayment(
        userId: UUID,
        amount: Long,
    ): UUID

    suspend fun findAllByUserId(userId: UUID): Flow<Payment>

    suspend fun findByUserIdAndDate(
        userId: UUID,
        date: Instant,
    ): Flow<Payment>
}
