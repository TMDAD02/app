package com.chatapp.app.repository;

import com.chatapp.app.model.Grupo;
import com.chatapp.app.model.Usuario;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioGrupo extends CrudRepository<Grupo, String>{
    Optional<Grupo> findByNombre(String nombre);
    Iterable<Grupo> findByNombreNot(String miUsuario);
    boolean existsByNombre(String nombre);

    @Query(
            value =
                    "insert into usuarios_grupos (id_grupo, id_usuario) values (?1, ?2)",
            nativeQuery = true)
    void insertUserGroup(Long id_grupo, Long id_usuario);

    @Query(
            value =
                    "delete from usuarios_grupos where id_grupo = ?1 and id_usuario = ?2",
            nativeQuery = true)
    void removeUserGrupo(Long id_grupo, Long id_usuario);

    Optional<Grupo> findById(long id);

    //void delete(Optional<Grupo> grupo);
}
