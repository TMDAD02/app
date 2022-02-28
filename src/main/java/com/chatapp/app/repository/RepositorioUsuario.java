package com.chatapp.app.repository;

import com.chatapp.app.model.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositorioUsuario extends CrudRepository<Usuario, String> {
    Optional<Usuario> findByNombre(String nombre);
    Iterable<Usuario> findByNombreNot(String miUsuario);
    boolean existsByNombre(String nombre);
}
