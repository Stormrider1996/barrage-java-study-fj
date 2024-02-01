package com.setronica.eventing.persistence;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

@Component
public interface TicketOrderRepository extends JpaRepository<TicketOrder, Integer> {

    @Modifying
    @Transactional
    @Query(value = "create trigger check_available_seats before insert on ticket_order for each row begin select available_seats into @seats from event_schedule where id = new.event_schedule_id; if new.amount > @seats then signal sqlstate '45000' set message_text = 'Not enough seats available'; end if; end", nativeQuery = true)
    void createInsertTrigger();
}
