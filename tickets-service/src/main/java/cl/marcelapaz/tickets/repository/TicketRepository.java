package cl.marcelapaz.tickets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.marcelapaz.tickets.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByUsuarioIdOrderByFechaDesc(Long usuarioId);
}