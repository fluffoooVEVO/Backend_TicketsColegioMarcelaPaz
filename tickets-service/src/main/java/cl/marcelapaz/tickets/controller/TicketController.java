package cl.marcelapaz.tickets.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.marcelapaz.tickets.dto.TicketRequest;
import cl.marcelapaz.tickets.model.Ticket;
import cl.marcelapaz.tickets.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService service;

    @GetMapping
    public List<Ticket> listar(@RequestParam Long usuarioId) {
        return service.listarPorUsuario(usuarioId);
    }

    @GetMapping("/{id}")
    public Ticket buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Ticket crear(@Valid @RequestBody TicketRequest request) {
        return service.crear(request);
    }

    @PutMapping("/{id}")
    public Ticket actualizar(@PathVariable Long id, @Valid @RequestBody TicketRequest request) {
        return service.actualizar(id, request);
    }

    @PatchMapping("/{id}/estado")
    public Ticket avanzarEstado(@PathVariable Long id) {
        return service.avanzarEstado(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}