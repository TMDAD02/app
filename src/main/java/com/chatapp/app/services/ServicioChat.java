package com.chatapp.app.services;

import com.chatapp.app.model.Grupo;
import com.chatapp.app.model.Mensaje;
import com.chatapp.app.model.Usuario;
import com.chatapp.app.repository.RepositorioGrupo;
import com.chatapp.app.repository.RepositorioMensaje;
import com.chatapp.app.repository.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioChat {
    private static final int LONGITUD_MAXIMA = 500;

    @Autowired
    private RepositorioMensaje repositorioMensaje;

    @Autowired
    private  RepositorioUsuario repositorioUsuario;

    @Autowired
    private RepositorioGrupo repositorioGrupo;


    @Transactional
    public void guardarMensaje(String fuente, String destino, String contenido, boolean leido) throws Exception {
        Optional<Usuario> ufuente = repositorioUsuario.findByNombre(fuente);
        Optional<Usuario> udestino = repositorioUsuario.findByNombre(destino);
        Optional<Grupo> destinoGrupo = repositorioGrupo.findByNombre(destino);
        System.out.println(ufuente.toString());
        System.out.println(udestino.toString());
        System.out.println(destinoGrupo.toString());


        if (ufuente.isPresent() && udestino.isPresent()) {
            Mensaje m = new Mensaje(ufuente.get(), udestino.get(), new Date(), contenido, leido);
            if (m.getContenido().length() < LONGITUD_MAXIMA) {
                repositorioMensaje.save(m);
                return;
            }

        }else if( ufuente.isPresent() && destinoGrupo.isPresent() ){
            System.out.println("3455");
            Mensaje m = new Mensaje(ufuente.get(), destinoGrupo.get(), new Date(), contenido, true); //mirar si poner true o false en mesnaje leido duda
            System.out.println("insideeeeeeee");
            if (m.getContenido().length() < LONGITUD_MAXIMA) {
                repositorioMensaje.save(m);
                return;
            }
        }

        throw new Exception();
    }

    public List<Mensaje> obtenerMensajes(String fuente, String destino) throws Exception {
        Optional<Usuario> uFuente = repositorioUsuario.findByNombre(fuente);
        Optional<Usuario> uDestino = repositorioUsuario.findByNombre(destino);
        Optional<Grupo> destinoGrupo = repositorioGrupo.findByNombre(destino);
        System.out.println(destinoGrupo);
        if (uFuente.isPresent() && uDestino.isPresent()) {
            Iterable<Mensaje> mensajes = repositorioMensaje.findByFuenteIdAndDestinoIdOr(uFuente.get(), uDestino.get());
            List<Mensaje> listaMensajes = new ArrayList<>();
            for (Mensaje m : mensajes) {
                listaMensajes.add(m);
            }
            return listaMensajes;
        }else if(destinoGrupo.isPresent()){
            System.out.println("paso1");
            Iterable<Mensaje> mensajes = repositorioMensaje.findByLeidoAndDestinogrupoId(true, destinoGrupo.get().getId());
            System.out.println(mensajes);
            System.out.println("paso2");

            List<Mensaje> listaMensajes = new ArrayList<>();
            for (Mensaje m : mensajes) {
                System.out.println(m);
                listaMensajes.add(m);
            }
            System.out.println(listaMensajes);
            return listaMensajes;
        }

        throw new Exception();
    }


    public List<Mensaje> obtenerMensajesNoLeidos(String usuario) throws Exception {
        Optional<Usuario> u = repositorioUsuario.findByNombre(usuario);

        if(u.isPresent()) {
            Iterable<Mensaje> mensajes = repositorioMensaje.findByLeidoAndDestinoId(false, u.get().getId());
            List<Mensaje> listaMensajes = new ArrayList<>();
            for (Mensaje m : mensajes) {
                listaMensajes.add(m);
                m.setLeido();
                repositorioMensaje.save(m);
            }
            return listaMensajes;
        }
        throw new Exception();
    }
}
