package com.setronica.eventing.web;

import com.setronica.eventing.app.TicketOrderService;
import com.setronica.eventing.dto.TicketOrderDto;
import com.setronica.eventing.mapper.TicketOrderMapper;
import com.setronica.eventing.persistence.TicketOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("event/api/v1/ticket-orders")
public class TicketOrderController {

    private final TicketOrderService ticketOrderService;

    private final TicketOrderMapper ticketOrderMapper;

    public TicketOrderController(TicketOrderService ticketOrderService, TicketOrderMapper ticketOrderMapper) {
        this.ticketOrderService = ticketOrderService;
        this.ticketOrderMapper = ticketOrderMapper;
    }

    @GetMapping
    public List<TicketOrder> findAll() {
        return ticketOrderService.getAll();
    }

    @GetMapping("/{id}")
    public TicketOrderDto getById(@PathVariable("id") Integer id) {
        TicketOrder entity = ticketOrderService.getById(id);
        return ticketOrderMapper.mapToDto(entity);
    }

    @PostMapping("")
    public TicketOrderDto createTicketOrder(@RequestBody TicketOrderDto dto) {
        TicketOrder order = ticketOrderMapper.mapToTicketOrder(dto);
        TicketOrder createdTicketOrder = ticketOrderService.createTicketOrder(order);
        return ticketOrderMapper.mapToDto(createdTicketOrder);
    }

    @PutMapping("/{id}")
    public TicketOrderDto updateTicketOrder(@RequestBody TicketOrderDto dto, @PathVariable("id") Integer id) {
        TicketOrder order = ticketOrderMapper.mapToTicketOrder(dto);
        TicketOrder updatedTicketOrder = ticketOrderService.updateTicketOrder(order, id);
        return ticketOrderMapper.mapToDto(updatedTicketOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicketOrder(@PathVariable("id") int id) {
        ticketOrderService.deleteTicketOrder(id);
        return ResponseEntity.ok().build();
    }
}
