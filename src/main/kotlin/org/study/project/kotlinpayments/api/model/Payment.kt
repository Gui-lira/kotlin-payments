package org.study.project.kotlinpayments.api.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.util.UUID

@Document
data class Payment(
    @Id
    val id: UUID = UUID.randomUUID(),
    val userId: UUID,
    val amount: Long,
    val createdAt: Instant = Instant.now(),
)
