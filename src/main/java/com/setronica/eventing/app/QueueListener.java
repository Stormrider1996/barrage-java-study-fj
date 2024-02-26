package com.setronica.eventing.app;

import com.setronica.eventing.dto.TicketOrderDto;
import com.setronica.eventing.mapper.TicketOrderMapper;
import com.setronica.eventing.persistence.TicketOrder;
import com.setronica.eventing.web.TicketOrderController;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class QueueListener {

    @RabbitListener(queues = "payment-notifications")
    public void handleMessage(TicketOrderDto ticketOrderDto) {
        if ("AUTHORIZED".equals(ticketOrderDto.getStatus())) {
            // do something if the status is authorized
        } else if ("FAILED".equals(ticketOrderDto.getStatus())) {
            // do something if the status is failed
        }
    }
}