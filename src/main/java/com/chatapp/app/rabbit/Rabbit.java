package com.chatapp.app.rabbit;

import com.chatapp.app.model.Mensaje;
import com.chatapp.app.model.Usuario;
import com.chatapp.app.services.ServicioChat;
import com.chatapp.app.services.ServicioUsuario;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Rabbit {
    public static final String COLA_PETICIONES = "peticiones";
    public static final String PARAMETROS_NOMBRE = "PARAMETROS";

    @Autowired
    private ServicioUsuario servicioUsuario;

    @Autowired
    private ServicioChat servicioChat;

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
            case "OBTENER_MENSAJES": return tratarObtenerMensajes(mensaje.getJSONObject(PARAMETROS_NOMBRE));
            case "OBTENER_MENSAJES_NO_LEIDOS": return tratarObtenerMensajesNoLeidos(mensaje.getJSONObject(PARAMETROS_NOMBRE));
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

    private String tratarObtenerMensajes(JSONObject parametros) throws JSONException {
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

    String tratarGuardarMensaje(JSONObject parametros) throws JSONException {
        String fuente = parametros.getString("fuente");
        String destino = parametros.getString("destino");
        String contenido = parametros.getString("contenido");
        boolean leido = parametros.getBoolean("leido");
        try {
            servicioChat.guardarMensaje(fuente, destino, contenido, leido);
            return RespuestaFactory.crearRespuestaVerificarUsuario(true);
        } catch (Exception e) { // No necesitamos responder, devolvemos lo que sea...
            return RespuestaFactory.crearRespuestaVerificarUsuario(false);
        }
    }

    String tratarValidarUsuario(JSONObject parametros) throws JSONException {
        try {
            servicioUsuario.existeUsuario(parametros.getString("nombre"));
            return RespuestaFactory.crearRespuestaVerificarUsuario(true);
        } catch (Exception e) {
            return RespuestaFactory.crearRespuestaVerificarUsuario(false);
        }

    }
/*
    String tratarRegistro(JSONObject parametros) throws JSONException {
        try {
            servicioUsuario.registrar(parametros.getString("nombre"), parametros.getString("contrasenaCifrada"),
                    parametros.getString("email"));
            return RespuestaFactory.crearRespuestaRegistro(true);

       } catch (Exception e) {
            return RespuestaFactory.crearRespuestaRegistro(false);
        }
    }

    private String tratarLogin(JSONObject parametros) throws JSONException {
        try {
            String id = servicioUsuario.iniciarSesion(parametros.getString("email"), parametros.getString("contrasenaCifrada"));
            return RespuestaFactory.crearRespuestaLogin(true, id, "USER");
        } catch (Exception e) {
            return RespuestaFactory.crearRespuestaLogin(false, null, null);
        }
    }*/

}
