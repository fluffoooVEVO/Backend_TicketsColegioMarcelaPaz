package cl.marcelapaz.tickets.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long usuarioId;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(nullable = false, length = 1000)
    private String descripcion;

    @Column(nullable = false, length = 100)
    private String sector;

    @Column(nullable = false, length = 10)
    private String urgencia;

    @Column(nullable = false, length = 20)
    private String estado;

    @Column(nullable = false)
    private Long fecha;

    @PrePersist
    void alCrear() {
        if (estado == null) estado = "Abierto";
        if (fecha == null) fecha = System.currentTimeMillis();
    }
}