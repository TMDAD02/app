package com.chatapp.app.model;

import javax.persistence.*;

@Entity
@Table(name = "usuariosgrupos")
public class UsuarioGrupo {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "idusuario")
    private long idUsuario;

    @Column(name = "idgrupo")
    private long idGrupo;

    public UsuarioGrupo(long id, long id_usuario, long id_grupo) {
        this.id = id;
        this.idUsuario = id_usuario;
        this.idGrupo = id_grupo;
    }

    public UsuarioGrupo() { }


    public long getId() {
        return id;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public long getIdGrupo() {
        return idGrupo;
    }
}
