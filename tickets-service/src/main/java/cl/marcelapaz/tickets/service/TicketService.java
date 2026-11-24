package cl.marcelapaz.tickets.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cl.marcelapaz.tickets.dto.TicketRequest;
import cl.marcelapaz.tickets.model.Ticket;
import cl.marcelapaz.tickets.repository.TicketRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketService {

    private static final List<String> ESTADOS = List.of("Abierto", "En Proceso", "Cerrado");

    private final TicketRepository repository;

    public List<Ticket> listarPorUsuario(Long usuarioId) {
        return repository.findByUsuarioIdOrderByFechaDesc(usuarioId);
    }

    public Ticket buscar(Long id) {
        return repository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket no encontrado"));
    }

    public Ticket crear(TicketRequest request) {
        Ticket ticket = new Ticket();
        copiarDatos(request, ticket);
        return repository.save(ticket);
    }

    public Ticket actualizar(Long id, TicketRequest request) {
        Ticket ticket = buscar(id);
        copiarDatos(request, ticket);
        return repository.save(ticket);
    }

    public Ticket avanzarEstado(Long id) {
        Ticket ticket = buscar(id);
        int siguiente = (ESTADOS.indexOf(ticket.getEstado()) + 1) % ESTADOS.size();
        ticket.setEstado(ESTADOS.get(siguiente));
        return repository.save(ticket);
    }

    public void eliminar(Long id) {
        repository.delete(buscar(id));
    }

    private void copiarDatos(TicketRequest request, Ticket ticket) {
        ticket.setUsuarioId(request.usuarioId());
        ticket.setTitulo(request.titulo().trim());
        ticket.setDescripcion(request.descripcion().trim());
        ticket.setSector(request.sector().trim());
        ticket.setUrgencia(request.urgencia());
    }
}