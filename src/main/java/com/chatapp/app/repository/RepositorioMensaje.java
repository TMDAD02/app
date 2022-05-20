package com.chatapp.app.repository;

import com.chatapp.app.model.Mensaje;
import com.chatapp.app.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioMensaje extends CrudRepository<Mensaje, String> {
    Iterable<Mensaje> findByLeidoAndDestinoId(boolean leido, long destino);
    Iterable<Mensaje> findByLeidoAndDestinogrupoId(boolean leido, long destino);

    @Query("select m from Mensaje m where (m.fuente = ?1 and m.destino = ?2) or (m.fuente = ?2 and m.destino = ?1)")
    Iterable<Mensaje> findByFuenteIdAndDestinoIdOr(Usuario fuente, Usuario destino);

    Iterable<Mensaje> findByDestinogrupoId(long id);
}