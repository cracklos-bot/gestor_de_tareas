package com.tareas.gestor.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String correo;

    // un usuario tiene muchas tareas,la relación está mapeada por el campo usuario en Tarea
    // cascade: Si eliminas un usuario, se eliminan también sus tareas
    // orphanRemoval: Si quitas una tarea de la lista, se elimina de la BD.
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // marca el lado propietario de la relación (el que contiene la lista)
    private List<Tarea> tareas;

}
