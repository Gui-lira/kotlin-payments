package org.study.project.kotlinpayments

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.reactive.server.WebTestClient
import org.study.project.kotlinpayments.api.dto.PaymentDTO
import org.study.project.kotlinpayments.business.repository.PaymentRepository
import org.study.project.kotlinpayments.business.repository.UserRepository
import org.study.project.kotlinpayments.common.toDTO
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import java.util.UUID

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
)
@Testcontainers
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class BaseIT {
    companion object {
        @ServiceConnection
        @Container
        val mongoDbContainer =
            MongoDBContainer(DockerImageName.parse("mongo:latest"))
                .apply { withReuse(true) }
    }

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var paymentRepository: PaymentRepository

    @Test
    fun `Should create user`() =
        runBlocking {
            val document = UUID.randomUUID().toString()
            val response =
                webTestClient
                    .post()
                    .uri("/user/create?document=$document")
                    .exchange()
                    .expectStatus()
                    .isOk
                    .expectBody(UUID::class.java)
                    .returnResult()

            val savedUser = response.responseBody?.let { userRepository.findById(it) }

            Assertions.assertAll(
                Executable { Assertions.assertNotNull(response.responseBody) },
                Executable { Assertions.assertNotNull(savedUser) },
                Executable { Assertions.assertEquals(document, savedUser?.document) },
            )
        }

    @Test
    fun `Should find user by id`() =
        runBlocking {
            val document = UUID.randomUUID().toString()
            val userId = userRepository.save(document)

            val response =
                webTestClient
                    .get()
                    .uri("/user/id/$userId")
                    .exchange()
                    .expectStatus()
                    .isOk
                    .expectBody(String::class.java)
                    .returnResult()

            Assertions.assertEquals(document, response.responseBody)
        }

    @Test
    fun `Should delete user by id`() =
        runBlocking {
            val document = UUID.randomUUID().toString()
            val userId = userRepository.save(document)

            webTestClient
                .delete()
                .uri("/user/$userId")
                .exchange()
                .expectStatus()
                .isOk
            delay(1000)
            val deletedUser = userRepository.findById(userId)

            Assertions.assertNull(deletedUser)
        }

    @Test
    fun `Should get payments by user id`() =
        runBlocking {
            val userId = UUID.randomUUID()
            flow {
                var total = 0
                do {
                    val paymentId = paymentRepository.createPayment(userId, 100)
                    emit(paymentId)
                    total += 1
                } while (total < 5)
            }
            val payments = paymentRepository.findAllByUserId(userId).map { it.toDTO() }.toList()

            val response =
                webTestClient
                    .get()
                    .uri("/payment/$userId")
                    .exchange()
                    .expectStatus()
                    .isOk
                    .expectBodyList(PaymentDTO::class.java)
                    .returnResult()

            Assertions.assertEquals(payments, response.responseBody)
        }

    @Test
    fun `Should create payment`() =
        runBlocking {
            val userId = UUID.randomUUID()
            val value = 100L

            webTestClient
                .post()
                .uri("/payment/create?userId=$userId&amount=$value")
                .exchange()
                .expectStatus()
                .isOk

            delay(1000)
            val savedPayment = paymentRepository.findAllByUserId(userId).firstOrNull()

            Assertions.assertAll(
                Executable { Assertions.assertNotNull(savedPayment) },
                Executable { Assertions.assertEquals(userId, savedPayment?.userId) },
                Executable { Assertions.assertEquals(value, savedPayment?.amount) },
            )
        }
}
