package com.chatapp.app.repository;

import com.chatapp.app.model.Grupo;
import com.chatapp.app.model.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RepositorioUsuario extends CrudRepository<Usuario, String> {
    Optional<Usuario> findByNombre(String nombre);
    Iterable<Usuario> findByNombreNot(String miUsuario);
    Set<Grupo> findGrupoByNombre(String miUsuario);
    boolean existsByNombre(String nombre);
}
