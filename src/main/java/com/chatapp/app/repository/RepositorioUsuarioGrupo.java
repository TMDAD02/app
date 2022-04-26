package com.chatapp.app.repository;

import com.chatapp.app.model.Grupo;
import com.chatapp.app.model.UsuarioGrupo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositorioUsuarioGrupo extends CrudRepository<UsuarioGrupo, String>{
    Iterable<UsuarioGrupo> findAllById(long id);
    Iterable<UsuarioGrupo> findAllByIdUsuario(long id);
    Optional<UsuarioGrupo> findByIdGrupoAndIdUsuario(long idgrupo, long idusuario);
}
