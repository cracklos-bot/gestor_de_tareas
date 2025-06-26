package com.tareas.gestor.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String descripcion;

    private boolean completada;

    @ManyToOne // muchas tareas tienen un usuario
    @JoinColumn(name = "usuario_id") // referencia de la columna usuario_id en la tabla tareas
    @JsonBackReference // marca el lado inverso, que no se serializa en JSON
    private Usuario usuario;
}
