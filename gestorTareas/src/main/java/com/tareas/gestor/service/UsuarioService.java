package com.tareas.gestor.service;

import com.tareas.gestor.model.Usuario;
import com.tareas.gestor.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /*
    * crear un usuario
    */
    public Usuario crearUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    /*
    * listar todos los usuarios
    */
    public List<Usuario> usuarios(){
        return usuarioRepository.findAll();
    }

    /*
    * buscar un usuario por su id
    * */
    public Usuario buscarUsuarioPorId(Long id){
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("usuario no encontrado!"));
    }

    /*
    * eliminar a un usuario
    * */
    public void eliminarUsuario(Long id){
        if (!usuarioRepository.existsById(id)){
            throw new RuntimeException("usuario no encontrado!");
        }
        usuarioRepository.deleteById(id);
    }
}
