package com.chatapp.app.services;

import com.chatapp.app.model.Grupo;
import com.chatapp.app.model.Usuario;
import com.chatapp.app.repository.RepositorioGrupo;
import com.chatapp.app.repository.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ServicioGrupo {

    @Autowired
    private  RepositorioGrupo repGrupo;

    @Autowired
    private RepositorioUsuario repUsuario;



    public boolean existeGrupo(String nombre) {
        return repGrupo.existsByNombre(nombre);
    }


    public List<String> obtenerTodosGrupos(String miUsuario) {
        Iterable<Grupo> iterableGrupos = repGrupo.findByNombreNot(miUsuario);


        List<String> listaGrupos = new ArrayList<>();
        for (Grupo u : iterableGrupos) {
            listaGrupos.add(u.getNombre());
        }
        return listaGrupos;
    }

    public List<String> obtenerMisGrupos(String miUsuario) throws Exception {
        Optional<Usuario> usuario = repUsuario.findByNombre(miUsuario);
        if (usuario.isPresent()) {
            List<String> listaGruposUsuario = new ArrayList<>();
            for (Grupo g : usuario.get().getGrupos()) {
                listaGruposUsuario.add(g.getNombre());
            }
            return listaGruposUsuario;
        }

        throw new Exception();
    }

    public void crearGrupo(String miUsuario, String nombreGrupo) throws Exception {
        Optional<Usuario> usuario = repUsuario.findByNombre(miUsuario);
        if (usuario.isPresent()) {
            Grupo grupo = new Grupo(nombreGrupo, usuario.get());
            repGrupo.save(grupo);
            anadirUsuarioGrupo(miUsuario, miUsuario, nombreGrupo);
        }

        throw new Exception();
    }

    public void anadirUsuarioGrupo(String usuarioCreador, String usuarioAnadir, String nombreGrupo) throws Exception {
        Optional<Grupo> grupo = repGrupo.findByNombre(nombreGrupo);
        Optional<Usuario> usuario = repUsuario.findByNombre(usuarioAnadir);
        Optional<Usuario> creador = repUsuario.findByNombre(usuarioCreador);
        if (grupo.isPresent() && usuario.isPresent() && creador.isPresent()) {
            if (creador.get().getId() == grupo.get().getCreador().getId()) {
                grupo.get().aniadirUsuario(usuario.get());
                repGrupo.save(grupo.get());
            }
        } else {
            throw new Exception();
        }
    }


    public void eliminarUsuarioGrupo(String miUsuario, String uEliminar, String nombreGrupo) throws Exception {
        Optional<Usuario> usuario = repUsuario.findByNombre(miUsuario);
        Optional<Usuario> usuarioEliminar = repUsuario.findByNombre(uEliminar);
        Optional<Grupo> grupo = repGrupo.findByNombre(nombreGrupo);
        if (usuario.isPresent() && grupo.isPresent() && usuarioEliminar.isPresent()) {
            Grupo g = grupo.get();
            Usuario u = usuario.get();
            Usuario ue = usuarioEliminar.get();
            if (g.getCreador().getId() == u.getId()) {
                g.eliminarUsuario(ue);
                repGrupo.save(g);
            }
        }
        throw new Exception();

    }

    public void eliminarGrupo(String miUsuario, String nombreGrupo) throws Exception {
        Optional<Grupo> grupo = repGrupo.findByNombre(nombreGrupo);
        Optional<Usuario> usuario = repUsuario.findByNombre(miUsuario);
        if(grupo.isPresent() && usuario.isPresent()) {
            Grupo g = grupo.get();
            Usuario u = usuario.get();
            if (g.getCreador().getId() == u.getId()) {
                g.getColeccionUsuarios().removeAll(g.getColeccionUsuarios());
                repGrupo.delete(g);
            } else {
                throw new Exception();
            }
            throw new Exception();
        }
    }


}

