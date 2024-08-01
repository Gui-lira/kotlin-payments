package org.study.project.kotlinpayments.database.configuration

import com.mongodb.reactivestreams.client.MongoClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.ReactiveMongoTemplate

@Configuration
class MongoTemplateConfiguration(
    private val mongoClient: MongoClient,
) {
    @Bean
    fun mongoTemplate() = ReactiveMongoTemplate(mongoClient, "kotlin-payments")
}
