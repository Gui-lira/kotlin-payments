package org.study.project.kotlinpayments.business.repository

import org.study.project.kotlinpayments.api.model.User
import java.util.UUID

interface UserRepository {
    suspend fun save(document: String): UUID

    suspend fun findById(id: UUID): User?

    suspend fun findByDocument(document: String): User?

    suspend fun deleteById(id: UUID)
}
