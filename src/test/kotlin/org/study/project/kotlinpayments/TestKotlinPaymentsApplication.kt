package org.study.project.kotlinpayments

import org.springframework.boot.fromApplication
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.boot.with
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.utility.DockerImageName

@TestConfiguration(proxyBeanMethods = false)
class TestKotlinPaymentsApplication {
    @Bean
    @ServiceConnection
    fun mongoDbContainer(): MongoDBContainer {
        val container = MongoDBContainer(DockerImageName.parse("mongo:latest"))
        return container
    }
}

fun main(args: Array<String>) {
    fromApplication<KotlinPaymentsApplication>().with(TestKotlinPaymentsApplication::class).run(*args)
}
