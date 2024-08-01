package org.study.project.kotlinpayments.api.dto

import java.time.Instant

data class PaymentDTO(
    val amount: Long,
    val createdAt: Instant,
)
