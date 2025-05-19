package com.example.appsectorsalud

import android.util.Log
import com.rabbitmq.client.*
import kotlinx.coroutines.*
import java.nio.charset.StandardCharsets

object RabbitMQConsumer {
    private const val QUEUE_NAME = "Cliente/Servidor"
    private const val EXCHANGE_NAME = "Cliente/Servidor"
    private const val ROUTING_KEY = "Expediente"

    fun iniciarConsumo(onMensajeRecibido: (String) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val factory = ConnectionFactory().apply {
                    host = "10.0.2.2"
                    port = 5672
                    username = "guest"
                    password = "guest"
                }

                val connection = factory.newConnection()
                val channel = connection.createChannel()

                channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT, true)
                channel.queueDeclare(QUEUE_NAME, true, false, false, null)
                channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY)

                val deliverCallback = DeliverCallback { _, delivery ->
                    val mensaje = String(delivery.body, StandardCharsets.UTF_8)
                    Log.d("RabbitMQ", "Mensaje recibido: $mensaje")
                    onMensajeRecibido(mensaje)
                }

                channel.basicConsume(QUEUE_NAME, true, deliverCallback) { consumerTag ->
                    Log.d("RabbitMQ", "Cancelado: $consumerTag")
                }

            } catch (e: Exception) {
                Log.e("RabbitMQ", "Error al consumir mensajes", e)
            }
        }
    }

}