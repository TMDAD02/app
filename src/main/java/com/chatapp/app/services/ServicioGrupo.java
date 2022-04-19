package com.chatapp.app.services;

import com.chatapp.app.model.Grupo;
import com.chatapp.app.model.Usuario;
import com.chatapp.app.model.UsuarioGrupo;
import com.chatapp.app.repository.RepositorioGrupo;
import com.chatapp.app.repository.RepositorioUsuario;
import com.chatapp.app.repository.RepositorioUsuarioGrupo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioGrupo {

    @Autowired
    private  RepositorioGrupo repGroup;

    @Autowired
    private RepositorioUsuarioGrupo repUsuarioGroup;

    @Autowired
    private RepositorioUsuario repUsuario;

    //@Autowired
    //private RepositorioUsuario_Grupo repUsuarioGroup;

    public boolean existeGrupo(String nombre) {
        return repGroup.existsByNombre(nombre);
    }


    public List<String> obtenerTodosGrupos(String miUsuario) {
        Iterable<Grupo> iterableGrupos = repGroup.findByNombreNot(miUsuario);


        List<String> listaGrupos = new ArrayList<>();
        for (Grupo u : iterableGrupos) {
            listaGrupos.add(u.getNombre());
        }
        return listaGrupos;
    }

    public List<String> obtenerMisGrupos(String miUsuario) {
        Optional<Usuario> usuario = repUsuario.findByNombre(miUsuario);
        Iterable<UsuarioGrupo> iterableUsuariosGrupos = repUsuarioGroup.findAllByIdUsuario(usuario.get().getId());
        System.out.println(usuario.get().getNombre());

        List<String> listaGruposFromUsuario = new ArrayList<>();
        for (UsuarioGrupo u : iterableUsuariosGrupos) {
            long idGrupo = u.getIdGrupo();
            Optional<Grupo> grupo = repGroup.findById(idGrupo);
            if (grupo.isPresent()){
                listaGruposFromUsuario.add(grupo.get().getNombre());
            }else{
                System.out.println("el grupo no existe");
            }

        }
        return listaGruposFromUsuario;
    }

    public void crearGrupo(String miUsuario, String nombreGrupo) {

        long idGrupo = repGroup.count() + 1;
        Grupo grupo= new Grupo(idGrupo, nombreGrupo, miUsuario);
        repGroup.save(grupo);//creaacion de grupos

        anadirUsuarioGrupo(miUsuario, miUsuario, nombreGrupo);

    }

    public void anadirUsuarioGrupo(String creador, String usuarioo, String nombreGrupo){
        Optional<Grupo> grupo = repGroup.findByNombre(nombreGrupo);
        Optional<Usuario> usuario = repUsuario.findByNombre(usuarioo);
        if(grupo.get().getCreador().equals(creador)){
            System.out.println("Eres el creador del grupo asi que puedes alamacenar");
            long idUsuarioGrupo = repUsuarioGroup.count()+1;
            UsuarioGrupo usuariogrupo = new UsuarioGrupo(idUsuarioGrupo, usuario.get().getId(), grupo.get().getId());
            repUsuarioGroup.save(usuariogrupo);
        }else{
            System.out.println("No se puede alamacenar porque la persona no es la creadora del grupo");
        }


    }

    public void eliminarGrupo(String miUsuario, String nombreGrupo) {
        Optional<Grupo> grupo = repGroup.findByNombre(nombreGrupo);
        //grupo.get()
        //long contador = repGroup.count();
        //Grupo grupo= new Grupo(contador+1, nombreGrupo,"basic");
        //repGroup.save(grupo);//creaacion de grupos
        if(grupo.isPresent()){
            repGroup.delete(grupo.get());
        }else{
            System.out.println("no cant removeeeeeeeeee");
        }


    }
}
