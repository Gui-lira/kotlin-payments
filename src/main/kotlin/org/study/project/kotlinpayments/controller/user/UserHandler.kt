package org.study.project.kotlinpayments.controller.user

import java.util.UUID

interface UserHandler {
    suspend fun create(document: String): UUID

    suspend fun findById(id: UUID): String?

    suspend fun findByDocument(document: String): UUID?

    suspend fun deleteById(id: UUID)
}
