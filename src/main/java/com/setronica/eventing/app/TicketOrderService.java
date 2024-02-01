package com.setronica.eventing.app;

import com.setronica.eventing.exceptions.NotFoundException;
import com.setronica.eventing.persistence.TicketOrder;
import com.setronica.eventing.persistence.TicketOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketOrderService {

    private final TicketOrderRepository ticketOrderRepository;

    public TicketOrderService(TicketOrderRepository ticketOrderRepository) {
        this.ticketOrderRepository = ticketOrderRepository;
    }

    public List<TicketOrder> getAll() {
        return ticketOrderRepository.findAll();
    }

    public TicketOrder getById(Integer id) {
        return ticketOrderRepository.findById(id).orElseThrow(() -> new NotFoundException("Ticket order not found with id=" + id));
    }

    public TicketOrder createTicketOrder(TicketOrder ticketOrder) {
        return ticketOrderRepository.save(ticketOrder);
    }

    public TicketOrder updateTicketOrder(TicketOrder updatedTicketOrder, Integer id) {
       TicketOrder existingTicketOrder = getById(id);
       existingTicketOrder = updatedTicketOrder;

       return ticketOrderRepository.save(existingTicketOrder);
    }

    public void deleteTicketOrder(int id) {
        ticketOrderRepository.deleteById(id);
    }
}
