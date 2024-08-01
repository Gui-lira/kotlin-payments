package org.study.project.kotlinpayments.messaging

import kotlinx.coroutines.channels.Channel
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.study.project.kotlinpayments.api.request.PaymentRequest

@Configuration
class ChannelProvider {
    val channel = Channel<PaymentRequest>()

    @Bean
    fun channel() = channel
}
