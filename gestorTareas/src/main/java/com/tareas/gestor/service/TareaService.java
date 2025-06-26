package com.tareas.gestor.service;

import com.tareas.gestor.model.Tarea;
import com.tareas.gestor.model.Usuario;
import com.tareas.gestor.repository.TareaRepository;
import com.tareas.gestor.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TareaService {

    // inyectamos
    @Autowired
    private TareaRepository tareaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /*
     * crear una tarea y asigna correctamente el usuario a la base de datos
     */
    public Tarea crearTarea(Tarea tarea){
        // obtenemos el id del usuario
        Long idUsuario = tarea.getUsuario().getId();

        // buscamos el usuario
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new RuntimeException("USUARIO NO ENCONTRADO"));

        // asignamos el usuario a la tarea
        tarea.setUsuario(usuario);

        return tareaRepository.save(tarea);
    }

    // devuelve todas las tareas existentes
    public List<Tarea> tareas(){
        return tareaRepository.findAll();
    }

    /*
    * buscar una tarea por su id
    */
    public Tarea buscarTareaPorId(Long id){
        return tareaRepository.findById(id).orElseThrow(() -> new RuntimeException("tarea no encontrada!"));
    }

    /*
    * eliminar una tarea
    * */
    public void eliminarTarea(Long id) {
        if (!tareaRepository.existsById(id)) {
            throw new RuntimeException("Tarea no encontrada");
        }
        tareaRepository.deleteById(id);
    }

    /**
     * marcar una tarea como completada
     */
    public Tarea marcarTareaCompletada(Long id){
        // buscamos la tarea
        Tarea tarea = tareaRepository.findById(id).orElseThrow(() -> new RuntimeException("tarea no encontrada"));

        tarea.setCompletada(!tarea.isCompletada()); // alternamos la tarea completada
        return tareaRepository.save(tarea);// guardamos los cambios y retornamos
    }
}
