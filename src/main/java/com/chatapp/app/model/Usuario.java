package com.chatapp.app.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "rol")
    @Enumerated(EnumType.STRING)
    private Rol rol;

    //@ManyToMany(mappedBy = "usuarios")
    //private Collection<Grupo> grupos;


    public Usuario(long id, String nombre, Rol rol) {
        this.id = id;
        this.nombre = nombre;
        this.rol = rol;
    }

    public Usuario() { }


    public String getNombre() {
        return nombre;
    }

    public long getId() {
        return id;
    }

}
