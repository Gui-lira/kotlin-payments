package org.study.project.kotlinpayments.messaging

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.springframework.stereotype.Component
import org.study.project.kotlinpayments.api.request.PaymentRequest

@Component
class Broker(
    private val channel: Channel<PaymentRequest>,
    private val listener: Listener
) {
    init {
        CoroutineScope(Dispatchers.IO).launch {
            channel.receiveAsFlow().collect {
                listener.receive(it)
            }
        }
    }
}
