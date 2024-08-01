package org.study.project.kotlinpayments.common

import org.study.project.kotlinpayments.api.dto.PaymentDTO
import org.study.project.kotlinpayments.api.model.Payment

fun Payment.toDTO() = PaymentDTO(amount, createdAt)
