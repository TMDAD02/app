package com.chatapp.app.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @OneToMany(mappedBy = "creador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Grupo> creadores;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE
            }, mappedBy = "usuarios")
    private Set<Grupo> grupos  = new HashSet<>();


    public Usuario(String nombre, Rol rol) {
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

    public Set<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(Set<Grupo> grupos) {
        this.grupos = grupos;
    }

}
