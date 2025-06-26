package com.tareas.gestor.controller;

import com.tareas.gestor.model.Tarea;
import com.tareas.gestor.service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@RestController //Este controlador también es REST
@RequestMapping("/api/tareas") // Ruta base: /api/tareas
public class TareaControler {

    @Autowired //Inyección del servicio
    private TareaService tareaService;

    @GetMapping //devuelve todas las tareas
    public List<Tarea> listar() {
        return tareaService.tareas();
    }

    @PostMapping //crea una nueva tarea
    public Tarea crear(@RequestBody Tarea tarea) {
        return tareaService.crearTarea(tarea);
    }

    @GetMapping("/{id}") //devuelve una tarea por su ID
    public Tarea buscar(@PathVariable Long id) {
        return tareaService.buscarTareaPorId(id);
    }

    @PutMapping("/{id}") //actualiza los datos de una tarea
    public Tarea actualizar(@PathVariable Long id, @RequestBody Tarea datos) {
        Tarea tarea = tareaService.buscarTareaPorId(id);
        if (tarea != null) {
            tarea.setTitulo(datos.getTitulo());
            tarea.setDescripcion(datos.getDescripcion());
            tarea.setCompletada(datos.isCompletada());
            return tareaService.crearTarea(tarea);
        }
        return null;
    }

    @DeleteMapping("/{id}") //elimina una tarea por ID
    public void eliminar(@PathVariable Long id) {
        tareaService.eliminarTarea(id);
    }

    @PutMapping("/{id}/completar") // completar una tarea como completada/no completada
    public ResponseEntity<Tarea> completarTarea(@PathVariable Long id) {
        Tarea tareaActualizada = tareaService.marcarTareaCompletada(id);
        return ResponseEntity.ok(tareaActualizada);
    }


}
