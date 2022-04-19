package com.chatapp.app.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "grupos")
public class Grupo {

    /*@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "usuarios_grupos",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_grupo")
    )
    private Collection<Usuario> usuarios;*/



    @Id
    @GeneratedValue
    private long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "creador")
    private String creador;

    public Grupo(long id, String nombre, String creador) {
        this.id = id;
        this.nombre = nombre;
        this.creador = creador;
    }

    public Grupo() { }

    //crear dos contructorees de grupo


    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCreador() { return creador; }


}
