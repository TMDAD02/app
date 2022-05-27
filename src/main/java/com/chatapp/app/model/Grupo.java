package com.chatapp.app.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "grupos")
public class Grupo {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "nombre", unique = true)
    private String nombre;

    @ManyToOne()
    @JoinColumn(name = "creador")
    private Usuario creador;

    @ManyToMany(fetch = FetchType.EAGER,
                cascade = {CascadeType.PERSIST})
    @JoinTable(
            name = "usuarios_grupos",
            joinColumns = @JoinColumn(name = "id_grupo"),
            inverseJoinColumns = @JoinColumn(name = "id_usuario")
    )
    private Set<Usuario> usuarios = new HashSet<>();

    public Grupo(String nombre, Usuario creador) {
        this.nombre = nombre;
        this.creador = creador;
    }

    public Grupo() { }

    public long getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }
    public Usuario getCreador() { return creador; }
    public Set<Usuario> getColeccionUsuarios() { return usuarios; }
    public void setColeccionUsuarios(Set<Usuario> usuarios) { this.usuarios = usuarios; }

    public void aniadirUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
        usuario.getGrupos().add(this);
    }

    public void eliminarUsuario(Usuario usuario) {
        usuarios.removeIf(u -> u.getNombre().equals(usuario.getNombre()));
        usuario.getGrupos().remove(this);
    }
}
