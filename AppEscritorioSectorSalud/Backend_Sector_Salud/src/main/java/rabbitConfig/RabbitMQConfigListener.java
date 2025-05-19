/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rabbitConfig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author jl4ma
 */
@Configuration
public class RabbitMQConfigListener {
   
    // Nombres de exchanges
    public static final String EXCHANGE_NAME = "solicitudes";
    public static final String CLIENTE_SERVIDOR_QUEUE = "appmovil";
    public static final String EXPEDIENTE_ROUTING_KEY = "expediente";

    // Declaración de la cola
    @Bean
    public Queue clienteServidorQueue() {
        return new Queue(CLIENTE_SERVIDOR_QUEUE, true); // true significa que la cola es durable
    }

    // Declaración del exchange
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    // Declaración de la vinculación entre la cola y el exchange con la routing key
    @Bean
    public Binding binding(Queue clienteServidorQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(clienteServidorQueue).to(topicExchange).with(EXPEDIENTE_ROUTING_KEY);
    }
} 

