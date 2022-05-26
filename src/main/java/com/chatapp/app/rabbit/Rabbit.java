package com.chatapp.app.rabbit;

import com.chatapp.app.model.Mensaje;
import com.chatapp.app.model.Usuario;
import com.chatapp.app.services.ServicioChat;
import com.chatapp.app.services.ServicioTrending;
import com.chatapp.app.services.ServicioUsuario;
import com.chatapp.app.services.ServicioGrupo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class Rabbit {
    public static final String COLA_PETICIONES = "peticiones";
    public static final String PARAMETROS_NOMBRE = "PARAMETROS";

    @Autowired
    private ServicioUsuario servicioUsuario;

    @Autowired
    private ServicioGrupo servicioGrupo;

    @Autowired
    private ServicioChat servicioChat;

    @Autowired
    private ServicioTrending servicioTrending;

    @Bean
    public Queue colaPeticiones() {
        return new Queue(COLA_PETICIONES);
    }

    @RabbitListener(queues = COLA_PETICIONES)
    public String recibirMensaje(String jsonString) throws JSONException {
        JSONObject json = new JSONObject(jsonString);
        return tratarMensaje(json);
    }

    String tratarMensaje(JSONObject mensaje) throws JSONException {
        System.out.println(mensaje);
        switch (mensaje.getString("NOMBRE_COMANDO")){
            //case "REGISTRO": return tratarRegistro(mensaje.getJSONObject(PARAMETROS_NOMBRE));
            case "AUTENTICAR_USUARIO": return tratarValidarUsuario(mensaje.getJSONObject(PARAMETROS_NOMBRE));
            case "GUARDAR_MENSAJE": return tratarGuardarMensaje(mensaje.getJSONObject(PARAMETROS_NOMBRE));
            case "OBTENER_TODOS_USUARIOS": return tratarObtenerTodosUsuarios(mensaje.getJSONObject(PARAMETROS_NOMBRE));
            case "OBTENER_TODOS_GRUPOS": return tratarObtenerTodosGrupos(mensaje.getJSONObject(PARAMETROS_NOMBRE));
            //case "OBTENER_MENSAJES": return tratarObtenerMensajes(mensaje.getJSONObject(PARAMETROS_NOMBRE));
            case "OBTENER_MENSAJES_USUARIOS": return tratarObtenerMensajesUsuarios(mensaje.getJSONObject(PARAMETROS_NOMBRE));
            case "OBTENER_MENSAJES_GRUPOS": return tratarObtenerMensajesGrupos(mensaje.getJSONObject(PARAMETROS_NOMBRE));
            case "OBTENER_MENSAJES_NO_LEIDOS": return tratarObtenerMensajesNoLeidos(mensaje.getJSONObject(PARAMETROS_NOMBRE));
            case "CREAR_GRUPO": return tratarCrearGrupo(mensaje.getJSONObject(PARAMETROS_NOMBRE));
            case "ELIMINAR_GRUPO": return tratarEliminarGrupo(mensaje.getJSONObject(PARAMETROS_NOMBRE));
            case "ANADIR_USUARIO_GRUPO": return tratarAnadirUsuarioGrupo(mensaje.getJSONObject(PARAMETROS_NOMBRE));
            case "ELIMINAR_USUARIO_GRUPO": return tratarEliminarUsuarioGrupo(mensaje.getJSONObject(PARAMETROS_NOMBRE));
            case "ACTUALIZAR_TRENDING": return tratarActualizarTrending(mensaje.getJSONObject(PARAMETROS_NOMBRE));
            case "OBTENER_CHATS_ACTIVOS": return tratarObtenerChatsActivos();
        }

        return null;
    }



    private String tratarObtenerMensajesNoLeidos(JSONObject parametros) throws JSONException {
        String usuario = parametros.getString("usuario");
        try {
            List<Mensaje> mensajes = servicioChat.obtenerMensajesNoLeidos(usuario);
            List<JSONObject> jsonMensajes = new ArrayList<>();
            for (Mensaje m : mensajes) {
                jsonMensajes.add(m.toJSON());
            }
            return RespuestaFactory.crearRespuestaObtenerMensajesNoLeidos(true, jsonMensajes);
        } catch (Exception e) {
            return RespuestaFactory.crearRespuestaObtenerMensajesNoLeidos(false, null);
        }
    }

    /*private String tratarObtenerMensajes(JSONObject parametros) throws JSONException {
        try {
            String fuente = parametros.getString("fuente");
            String destino = parametros.getString("destino");
            List<Mensaje> mensajes = servicioChat.obtenerMensajes(fuente, destino);
            List<JSONObject> jsonMensajes = new ArrayList<>();
            for (Mensaje m : mensajes) {
                jsonMensajes.add(m.toJSON());
            }
            return RespuestaFactory.crearRespuestaObtenerMensajes(true, jsonMensajes);
        } catch (Exception e) {
            return RespuestaFactory.crearRespuestaObtenerMensajes(false, null);
        }
    }*/

    private String tratarObtenerMensajesUsuarios(JSONObject parametros) throws JSONException {
        try {
            String fuente = parametros.getString("fuente");
            String destino = parametros.getString("destino");
            List<Mensaje> mensajes = servicioChat.obtenerMensajesUsuarios(fuente, destino);
            List<JSONObject> jsonMensajes = new ArrayList<>();
            for (Mensaje m : mensajes) {
                jsonMensajes.add(m.toJSON());
            }
            return RespuestaFactory.crearRespuestaObtenerMensajesUsuarios(true, jsonMensajes);
        } catch (Exception e) {
            return RespuestaFactory.crearRespuestaObtenerMensajesUsuarios(false, null);
        }
    }

    private String tratarObtenerMensajesGrupos(JSONObject parametros) throws JSONException {
        try {

            String destino = parametros.getString("destino");
            List<Mensaje> mensajes = servicioChat.obtenerMensajesGrupos( destino);
            List<JSONObject> jsonMensajes = new ArrayList<>();
            for (Mensaje m : mensajes) {
                jsonMensajes.add(m.toJSON());
            }
            return RespuestaFactory.crearRespuestaObtenerMensajesGrupos(true, jsonMensajes);
        } catch (Exception e) {
            return RespuestaFactory.crearRespuestaObtenerMensajesGrupos(false, null);
        }
    }

    String tratarObtenerTodosUsuarios(JSONObject parametros) throws JSONException {
        try {
            String miUsuario = parametros.getString("miusuario");
            List<String> usuarios = servicioUsuario.obtenerTodosUsuarios(miUsuario);
            return RespuestaFactory.crearRespuestaObtenerTodosUsuarios(true, usuarios);
        } catch (Exception e) {
            return RespuestaFactory.crearRespuestaObtenerTodosUsuarios(false, null);
        }
    }

    String tratarObtenerTodosGrupos(JSONObject parametros) throws JSONException {
        try {
            String miUsuario = parametros.getString("miusuario");
            List<String> grupos = servicioGrupo.obtenerMisGrupos(miUsuario);
            return RespuestaFactory.crearRespuestaObtenerTodosGrupos(true, grupos);
        } catch (Exception e) {
            return RespuestaFactory.crearRespuestaObtenerTodosGrupos(false, null);
        }
    }

    private String tratarCrearGrupo(JSONObject parametros) throws JSONException {
        try {
            String miUsuario = parametros.getString("miusuario");
            String nombreGrupo = parametros.getString("nombregrupo");
            servicioGrupo.crearGrupo(miUsuario, nombreGrupo);
            return RespuestaFactory.crearRespuestaCrearGrupo(true );
        } catch (Exception e) {
            return RespuestaFactory.crearRespuestaCrearGrupo(false);
        }
    }
    private String tratarEliminarGrupo(JSONObject parametros) throws JSONException {
        try {
            String miUsuario = parametros.getString("miusuario");
            String nombreGrupo = parametros.getString("nombregrupo");
            servicioGrupo.eliminarGrupo(miUsuario, nombreGrupo);
            return RespuestaFactory.crearRespuestaEliminarGrupo(true );
        } catch (Exception e) {
            return RespuestaFactory.crearRespuestaEliminarGrupo(false);
        }
    }

    private String tratarAnadirUsuarioGrupo(JSONObject parametros) throws JSONException {
        try {
            String miUsuario = parametros.getString("miusuario");
            String nombreUsuario = parametros.getString("nombreusuario");
            String nombreGrupo = parametros.getString("nombregrupo");
            servicioGrupo.anadirUsuarioGrupo(miUsuario, nombreUsuario, nombreGrupo);
            return RespuestaFactory.crearRespuestaAnadirUsuarioGrupo(true );
        } catch (Exception e) {
            return RespuestaFactory.crearRespuestaAnadirUsuarioGrupo(false);
        }
    }

    private String tratarEliminarUsuarioGrupo(JSONObject parametros) throws JSONException {
        try {
            String miUsuario = parametros.getString("miusuario");
            String nombreUsuario = parametros.getString("nombreusuario");
            String nombreGrupo = parametros.getString("nombregrupo");
            servicioGrupo.eliminarUsuarioGrupo(miUsuario, nombreUsuario, nombreGrupo);
            return RespuestaFactory.crearRespuestaEliminarUsuarioGrupo(true );
        } catch (Exception e) {
            return RespuestaFactory.crearRespuestaEliminarUsuarioGrupo(false);
        }
    }

    String tratarGuardarMensaje(JSONObject parametros) throws JSONException {
        String fuente = parametros.getString("fuente");
        String destino = parametros.getString("destino");
        String contenido = parametros.getString("contenido");
        boolean leido = parametros.getBoolean("leido");
        try {
            servicioChat.guardarMensaje(fuente, destino, contenido, leido);
            return RespuestaFactory.crearRespuestaVerificarUsuario(true);
        } catch (Exception e) {
            return RespuestaFactory.crearRespuestaVerificarUsuario(false);
        }
    }

    String tratarValidarUsuario(JSONObject parametros) throws JSONException {
        try {
            if (servicioUsuario.existeUsuario(parametros.getString("nombre"))) {
                return RespuestaFactory.crearRespuestaVerificarUsuario(true);
            } else {
                return RespuestaFactory.crearRespuestaVerificarUsuario(false);
            }

        } catch (Exception e) {
            return RespuestaFactory.crearRespuestaVerificarUsuario(false);
        }
    }

    String tratarActualizarTrending(JSONObject parametros) throws JSONException {
        JSONObject listaTrending = parametros.getJSONObject("listaTrending");
        try {
            servicioTrending.actualizarTrending(listaTrending);
            return RespuestaFactory.crearRespuestaActualizarTrending(true);
        } catch (Exception e) {
            return RespuestaFactory.crearRespuestaActualizarTrending(false);
        }
    }

    private String tratarObtenerChatsActivos() throws JSONException {

        try {
            long usuariosActivos = servicioChat.obtenerChatsActivos();
            return RespuestaFactory.crearRespuestaChatsActivos(true, usuariosActivos );
        } catch (Exception e) {
            return RespuestaFactory.crearRespuestaChatsActivos(false, -1);
        }
    }

}
