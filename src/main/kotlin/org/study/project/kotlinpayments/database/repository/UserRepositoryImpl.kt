package org.study.project.kotlinpayments.database.repository

import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.awaitOneOrNull
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Repository
import org.study.project.kotlinpayments.business.repository.UserRepository
import org.study.project.kotlinpayments.api.model.User
import java.util.UUID

@Repository
class UserRepositoryImpl(
    private val databaseTemplate: ReactiveMongoTemplate,
) : UserRepository {
    override suspend fun save(document: String): UUID {
        val user = User(document = document)
        databaseTemplate.save(user).awaitSingle()
        return user.id
    }

    override suspend fun findById(id: UUID) =
        databaseTemplate
            .findById(id, User::class.java)
            .awaitSingleOrNull()

    override suspend fun findByDocument(document: String) =
        databaseTemplate
            .query(User::class.java)
            .matching(Query(User::document isEqualTo document))
            .awaitOneOrNull()

    override suspend fun deleteById(id: UUID) {
        databaseTemplate
            .remove(Query(User::id isEqualTo id), User::class.java)
            .awaitSingle()
    }
}
