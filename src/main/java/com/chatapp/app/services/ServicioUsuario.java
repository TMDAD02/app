package com.chatapp.app.services;

import com.chatapp.app.model.Usuario;
import com.chatapp.app.repository.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioUsuario {

    @Autowired
    private  RepositorioUsuario repUsuario;
/*
    @Transactional
    public void registrar(String nombre, String contrasena, String email) throws Exception {
        Usuario usuario = new Usuario(id, contrasena, Rol.USUARIO);
        if (!repUsuario.existsById(usuario.getId())) {
            repUsuario.save(usuario);
        } else {
            throw new Exception();
        }
    }

    public String iniciarSesion(String email, String contrasena) throws Exception{
        Optional<Usuario> usuario = repUsuario.findUsuarioByEmail(email);
       // if (usuario.get().getContrasena().equals(contrasena)) {
            return usuario.get().getId();
        }
       // else {
       //     throw new Exception();
       // }
   // }
*/

    public Usuario obtenerUsuario(String nombre) throws Exception {
        Optional<Usuario> usuario = repUsuario.findByNombre(nombre);
        return usuario.get();
    }

    public boolean existeUsuario(String nombre) {
        return repUsuario.existsByNombre(nombre);
    }


    public List<String> obtenerTodosUsuarios(String miUsuario) {
        Iterable<Usuario> iterableUsuarios = repUsuario.findByNombreNot(miUsuario);
        List<String> listaUsuarios = new ArrayList<>();
        for (Usuario u : iterableUsuarios) {
           listaUsuarios.add(u.getNombre());
        }

        return listaUsuarios;
    }
}
