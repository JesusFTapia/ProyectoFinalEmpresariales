package com.example.appsectorsalud

import com.rabbitmq.client.ConnectionFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object RabbitMQSender {

    private const val QUEUE_NAME = "appmovil"
    private const val EXCHANGE_NAME = "solicitudes"
    private const val ROUTING_KEY = "expediente"

    suspend fun enviarMensaje(mensaje: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val factory = ConnectionFactory().apply {
                    host = "10.0.2.2"
                    port = 5672
                    username = "guest"
                    password = "guest"
                }

                factory.newConnection().use { connection ->
                    connection.createChannel().use { channel ->
                        channel.exchangeDeclare(EXCHANGE_NAME, "direct", true)
                        channel.queueDeclare(QUEUE_NAME, true, false, false, null)
                        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY)
                        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, mensaje.toByteArray())
                        println("Mensaje enviado: $mensaje")
                    }
                }
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }
}