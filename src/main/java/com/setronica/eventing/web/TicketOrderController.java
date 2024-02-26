package com.setronica.eventing.web;

import com.setronica.eventing.app.TicketOrderService;
import com.setronica.eventing.dto.TicketOrderDto;
import com.setronica.eventing.mapper.TicketOrderMapper;
import com.setronica.eventing.persistence.TicketOrder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("event/api/v1/ticket-orders")
@Tag(name = "Ticket Order Controller")
public class TicketOrderController {

    private final TicketOrderService ticketOrderService;

    private final TicketOrderMapper ticketOrderMapper;

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(TicketOrderController.class);

    public TicketOrderController(TicketOrderService ticketOrderService, TicketOrderMapper ticketOrderMapper) {
        this.ticketOrderService = ticketOrderService;
        this.ticketOrderMapper = ticketOrderMapper;
    }

    @Operation(summary = "Get all ticket orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the ticket orders"),
            @ApiResponse(responseCode = "404", description = "Ticket orders not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public List<TicketOrder> findAll() {
        return ticketOrderService.getAll();
    }

    @Operation(summary = "Get ticket order by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the ticket order"),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
            @ApiResponse(responseCode = "404", description = "Ticket order not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public TicketOrderDto getById(@PathVariable("id") Integer id) {
        TicketOrder entity = ticketOrderService.getById(id);
        return ticketOrderMapper.mapToDto(entity);
    }

    @Operation(summary = "Create a new ticket order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ticket order created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("")
    public TicketOrderDto createTicketOrder(@RequestBody TicketOrderDto dto) {
        TicketOrder order = ticketOrderMapper.mapToTicketOrder(dto);
        TicketOrder createdTicketOrder = ticketOrderService.createTicketOrder(order);
        return ticketOrderMapper.mapToDto(createdTicketOrder);
    }

    @Operation(summary = "Update an existing ticket order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket order updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Ticket order not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("")
    public TicketOrderDto updateTicketOrder(@RequestBody TicketOrderDto dto) {
        TicketOrder order = ticketOrderMapper.mapToTicketOrder(dto);
        TicketOrder updatedTicketOrder = ticketOrderService.updateTicketOrder(order);
        return ticketOrderMapper.mapToDto(updatedTicketOrder);
    }

    @Operation(summary = "Delete a ticket order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket order deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Ticket order not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicketOrder(@PathVariable("id") int id) {
        ticketOrderService.deleteTicketOrder(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/async")
    public void asyncOp() throws InterruptedException {
        ticketOrderService.asyncOperation();
    }
}
