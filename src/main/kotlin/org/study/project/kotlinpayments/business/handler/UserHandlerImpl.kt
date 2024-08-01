package org.study.project.kotlinpayments.business.handler

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.stereotype.Component
import org.study.project.kotlinpayments.business.repository.UserRepository
import org.study.project.kotlinpayments.controller.user.UserHandler
import java.util.UUID

@Component
class UserHandlerImpl(
    private val userRepository: UserRepository,
) : UserHandler {
    override suspend fun create(document: String) = userRepository.save(document)

    override suspend fun findById(id: UUID) = userRepository.findById(id)?.document

    override suspend fun findByDocument(document: String) = userRepository.findByDocument(document)?.id

    override suspend fun deleteById(id: UUID) {
        CoroutineScope(Dispatchers.IO).launch {
            userRepository.deleteById(id)
        }
    }
}
