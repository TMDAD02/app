package com.chatapp.app.repository;

import com.chatapp.app.model.Grupo;
import com.chatapp.app.model.Trending;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.MapKeyColumn;
import java.util.Optional;

@Repository
public interface RepositorioTrending extends CrudRepository<Trending, String>{
    @Modifying
    @Query(
            value = "truncate table trending",
            nativeQuery = true
    )
    void truncate();


}
