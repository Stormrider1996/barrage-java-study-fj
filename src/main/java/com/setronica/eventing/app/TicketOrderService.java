package com.setronica.eventing.app;

import com.setronica.eventing.exceptions.NotFoundException;
import com.setronica.eventing.exceptions.TicketOrderException;
import com.setronica.eventing.persistence.EventSchedule;
import com.setronica.eventing.persistence.EventScheduleRepository;
import com.setronica.eventing.persistence.TicketOrder;
import com.setronica.eventing.persistence.TicketOrderRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Service
public class TicketOrderService {

    private final TicketOrderRepository ticketOrderRepository;
    private final EventScheduleRepository eventScheduleRepository;

    public TicketOrderService(TicketOrderRepository ticketOrderRepository, EventScheduleRepository eventScheduleRepository) {
        this.ticketOrderRepository = ticketOrderRepository;
        this.eventScheduleRepository = eventScheduleRepository;
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

        eventSchedule.setAvailableSeats(seatsAmount);
        this.eventScheduleRepository.save(eventSchedule);

        return ticketOrderRepository.save(ticketOrder);
    }

    public TicketOrder updateTicketOrder(TicketOrder updatedTicketOrder, Integer id) {
       TicketOrder existingTicketOrder = getById(id);

       if (Objects.equals(updatedTicketOrder.getStatus(), "REFUND")) {
           EventSchedule eventSchedule = this.eventScheduleRepository.getReferenceById(updatedTicketOrder.getEventScheduleId());

           Integer seatsAmount = eventSchedule.getAvailableSeats() + updatedTicketOrder.getAmount();

           eventSchedule.setAvailableSeats(seatsAmount);
           this.eventScheduleRepository.save(eventSchedule);
       }

       existingTicketOrder = updatedTicketOrder;

       return ticketOrderRepository.save(existingTicketOrder);
    }

    public void deleteTicketOrder(int id) {
        ticketOrderRepository.deleteById(id);
    }
}
