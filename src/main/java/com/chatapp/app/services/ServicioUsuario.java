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

    public boolean existeUsuario(String nombre) {
        return  repUsuario.existsByNombre(nombre);
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
