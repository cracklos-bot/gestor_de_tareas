package com.tareas.gestor.controller;

import com.tareas.gestor.model.Usuario;
import com.tareas.gestor.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // indica que es un controlador rest, devuelve datos, no lista
@RequestMapping("/api/usuarios") // ruta base para todos los endpoints de este controlador
public class UsuarioControler {

    // inyectamos una instancia del repositorio de usuarios - ya no!
    // inyectamos el servicio
    @Autowired
    private UsuarioService usuarioService;

    // listar todos los usuarios
    @GetMapping
    public List<Usuario> usuarios(){
        return usuarioService.usuarios();
    }

    // creamos un usuario
    // requestBody para deserializar el cuerpo de una solicitud http y convertirla en obj java
    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario usuario){
        return usuarioService.crearUsuario(usuario);
    }

    // buscar un usuario por ID
    @GetMapping("/{id}")
    public Usuario buscarUsuarioPorId(@PathVariable Long id){
        return usuarioService.buscarUsuarioPorId(id);
    }

    // actualizar un usuario por ID
    @PutMapping("/{id}")
    public Usuario actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario){
        // buscamos al usuario
        Usuario existente = usuarioService.buscarUsuarioPorId(id);
        // validamos si el usuario se encontró
        if (existente != null){
            existente.setNombre(usuario.getNombre());
            existente.setCorreo(usuario.getCorreo());
            return usuarioService.crearUsuario(existente);
        }
        return null;
    }

    // elimina un usuario por su ID
    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable Long id){
        usuarioService.eliminarUsuario(id);
    }
}
