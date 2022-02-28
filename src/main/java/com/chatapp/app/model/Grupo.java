package com.chatapp.app.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "grupos")
public class Grupo {

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "usuarios_grupos",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_grupo")
    )
    private Collection<Usuario> usuarios;



    @Id
    @GeneratedValue
    private long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "tipo")
    private String tipo;

    public Grupo(long id, String nombre, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public Grupo() { }

    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() { return tipo; }


}
