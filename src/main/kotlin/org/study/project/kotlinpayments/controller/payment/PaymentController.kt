package org.study.project.kotlinpayments.controller.payment

import kotlinx.coroutines.flow.toList
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.server.EntityResponse
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait
import org.study.project.kotlinpayments.api.dto.PaymentDTO
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.*

@RestController
@RequestMapping("/payment")
class PaymentController(
    private val paymentHandler: PaymentHandler
) {
    @PostMapping("/create")
    suspend fun createPayment(
        @RequestParam userId: UUID,
        @RequestParam amount: Long,
    ): ServerResponse {
        paymentHandler.createPayment(userId, amount)
        return ServerResponse.accepted().buildAndAwait()
    }

    @GetMapping("/{userId}")
    suspend fun findAllByUserId(
        @PathVariable userId: UUID,
        @RequestParam(required = false) date: LocalDate? = null,
    ): ResponseEntity<List<PaymentDTO>> {
        val payments =
            date?.let {
                paymentHandler.findByUserIdAndDate(userId, it.atStartOfDay().toInstant(ZoneOffset.UTC))
            } ?: paymentHandler.findAllByUserId(userId)
        return ResponseEntity.ok(payments.toList())
    }
}
