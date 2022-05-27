package com.chatapp.app.repository;

import com.chatapp.app.model.Grupo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RepositorioGrupo extends CrudRepository<Grupo, String>{
    Optional<Grupo> findByNombre(String nombre);
    Iterable<Grupo> findByNombreNot(String miUsuario);
    boolean existsByNombre(String nombre);

}
