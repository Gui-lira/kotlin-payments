package org.study.project.kotlinpayments.database.repository

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.flow
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Repository
import org.study.project.kotlinpayments.business.repository.PaymentRepository
import org.study.project.kotlinpayments.api.model.Payment
import java.time.Instant
import java.util.UUID

@Repository
class PaymentRepositoryImpl(
    private val databaseTemplate: ReactiveMongoTemplate,
) : PaymentRepository {
    override suspend fun createPayment(
        userId: UUID,
        amount: Long,
    ): UUID {
        val payment = Payment(userId = userId, amount = amount)
        databaseTemplate.save(payment).awaitSingle()
        return payment.id
    }

    override suspend fun findAllByUserId(userId: UUID) =
        databaseTemplate
            .query(Payment::class.java)
            .matching(Query(Payment::userId isEqualTo userId))
            .flow()

    override suspend fun findByUserIdAndDate(
        userId: UUID,
        date: Instant,
    ) = databaseTemplate
        .query(Payment::class.java)
        .matching(
            Query(Payment::userId isEqualTo userId)
                .addCriteria(Payment::createdAt isEqualTo date),
        ).flow()
}
