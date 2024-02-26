package com.setronica.eventing.app;

import com.setronica.eventing.dto.SaleDto;
import com.setronica.eventing.exceptions.NotFoundException;
import com.setronica.eventing.exceptions.TicketOrderException;
import com.setronica.eventing.persistence.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class TicketOrderService {

    private final TicketOrderRepository ticketOrderRepository;
    private final EventScheduleRepository eventScheduleRepository;
    private final RabbitTemplate rabbitTemplate;
    private final EventRepository eventRepository;

    public TicketOrderService(TicketOrderRepository ticketOrderRepository, EventScheduleRepository eventScheduleRepository, RabbitTemplate rabbitTemplate, EventRepository eventRepository) {
        this.ticketOrderRepository = ticketOrderRepository;
        this.eventScheduleRepository = eventScheduleRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.eventRepository = eventRepository;
    }

    public List<TicketOrder> getAll() {
        return ticketOrderRepository.findAll();
    }

    public TicketOrder getById(Integer id) {
        return ticketOrderRepository.findById(id).orElseThrow(() -> new NotFoundException("Ticket order not found with id=" + id));
    }

    public TicketOrder createTicketOrder(TicketOrder ticketOrder) {
        try {
            ticketOrderRepository.createInsertTrigger();
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof SQLException sqlException) {
                if ("45000".equals(sqlException.getSQLState())) {
                    throw new TicketOrderException("Not enough seats available");
                }
            }
            throw e;
        }

        EventSchedule eventSchedule = this.eventScheduleRepository.getReferenceById(ticketOrder.getEventScheduleId());

        Integer seatsAmount = eventSchedule.getAvailableSeats() - ticketOrder.getAmount();

        return saveAndSend(ticketOrder, eventSchedule, seatsAmount);
    }

    public TicketOrder updateTicketOrder(TicketOrder updatedTicketOrder) {
       EventSchedule eventSchedule = this.eventScheduleRepository.getReferenceById(updatedTicketOrder.getEventScheduleId());

       Integer seatsAmount = eventSchedule.getAvailableSeats() + updatedTicketOrder.getAmount();

       return saveAndSend(updatedTicketOrder, eventSchedule, seatsAmount);
    }

    public void deleteTicketOrder(int id) {
        ticketOrderRepository.deleteById(id);
    }

    @Async("appThreadPool")
    public void asyncOperation() throws InterruptedException {
        Thread.sleep(5000);
        System.out.println("Done.");
    }

    public void sendMessage(SaleDto message) {
        rabbitTemplate.convertAndSend("spring-boot", message);
    }

    private TicketOrder saveAndSend(TicketOrder ticketOrder, EventSchedule eventSchedule, Integer seatsAmount) {
        eventSchedule.setAvailableSeats(seatsAmount);
        this.eventScheduleRepository.save(eventSchedule);

        Event event = eventRepository.getReferenceById(eventSchedule.getEventId());

        SaleDto message = new SaleDto(ticketOrder.getEmail(), event.getTitle(), ticketOrder.getAmount());

        sendMessage(message);
        return ticketOrderRepository.save(ticketOrder);
    }
}
