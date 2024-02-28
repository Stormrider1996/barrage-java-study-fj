package com.setronica.eventing.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfiguration {
    private static final String topicExchangeName = "spring-boot-exchange";

    private static final String queueName = "spring-boot";
    private static final String paymentQueueName = "payment-notifications"; // New queue

    @Bean
    public Queue queue() {
        return new Queue(queueName);
    }

    @Bean
    public Queue paymentQueue() { // New queue
        return new Queue(paymentQueueName);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");
    }

    @Bean
    public Binding paymentBinding(Queue paymentQueue, TopicExchange exchange) { // New binding
        return BindingBuilder.bind(paymentQueue).to(exchange).with("foo.bar.#");
    }
}