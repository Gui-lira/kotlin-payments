package org.study.project.kotlinpayments.api.request

import java.util.UUID

data class PaymentRequest(
    val amount: Long,
    val userId: UUID,
)
