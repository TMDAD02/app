package com.chatapp.app.rabbit;

import com.chatapp.app.model.Mensaje;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class RespuestaFactory {
    private static final String RESULTADO_RESPUESTA_NOMBRE = "RESULTADO_PETICION";
    private static final String PARAMETROS_NOMBRE = "PARAMETROS";

    public static String crearRespuestaLogin(boolean correcto, String id, String rol) throws JSONException {
        JSONObject jsonRespuesta = new JSONObject();
        if (correcto) {
            jsonRespuesta.put(RESULTADO_RESPUESTA_NOMBRE, "LOGIN_CORRECTO");
            JSONObject parametros = new JSONObject();
            parametros.put("id", id);
            parametros.put("rol", rol);
            jsonRespuesta.put(PARAMETROS_NOMBRE, parametros);
        } else {
            jsonRespuesta.put(RESULTADO_RESPUESTA_NOMBRE, "LOGIN_INCORRECTO");
        }

        return jsonRespuesta.toString();
    }

    public static String crearRespuestaRegistro(boolean correcto) throws JSONException {
        JSONObject jsonRespuesta = new JSONObject();
        if (correcto) {
            jsonRespuesta.put(RESULTADO_RESPUESTA_NOMBRE, "REGISTRO_CORRECTO");
        } else {
            jsonRespuesta.put(RESULTADO_RESPUESTA_NOMBRE, "REGISTRO_INCORRECTO");
        }

        return jsonRespuesta.toString();
    }

    public static String crearRespuestaVerificarUsuario(boolean correcto) throws JSONException {
        JSONObject jsonRespuesta = new JSONObject();

        if (correcto) {
            jsonRespuesta.put(RESULTADO_RESPUESTA_NOMBRE, "USUARIO_EXISTE");
        } else {
            jsonRespuesta.put(RESULTADO_RESPUESTA_NOMBRE, "USUARIO_NO_EXISTE");
        }

        return jsonRespuesta.toString();
    }

    public static String crearRespuestaObtenerTodosUsuarios(boolean correcto, List<String> usuarios) throws JSONException {
        JSONObject jsonRespuesta = new JSONObject();

        if (correcto) {
            jsonRespuesta.put(RESULTADO_RESPUESTA_NOMBRE, "OBTENER_TODOS_USUARIOS_CORRECTO");
            JSONObject parametros = new JSONObject();
            parametros.put("listaUsuarios", new JSONArray(usuarios));
            jsonRespuesta.put(PARAMETROS_NOMBRE, parametros);
        } else {
            jsonRespuesta.put(RESULTADO_RESPUESTA_NOMBRE, "OBTENER_TODOS_USUARIOS_INCORRECTO");
        }

        return jsonRespuesta.toString();
    }

    public static String crearRespuestaObtenerTodosGrupos(boolean correcto, List<String> grupos) throws JSONException {
        JSONObject jsonRespuesta = new JSONObject();

        if (correcto) {
            jsonRespuesta.put(RESULTADO_RESPUESTA_NOMBRE, "OBTENER_TODOS_GRUPOS_CORRECTO");
            JSONObject parametros = new JSONObject();
            parametros.put("listaGrupos", new JSONArray(grupos));
            jsonRespuesta.put(PARAMETROS_NOMBRE, parametros);
        } else {
            jsonRespuesta.put(RESULTADO_RESPUESTA_NOMBRE, "OBTENER_TODOS_GRUPOS_INCORRECTO");
        }

        return jsonRespuesta.toString();
    }

    public static String crearRespuestaObtenerMensajes(boolean correcto, List<JSONObject> mensajes) throws JSONException {
        JSONObject jsonRespuesta = new JSONObject();

        if (correcto) {
            jsonRespuesta.put(RESULTADO_RESPUESTA_NOMBRE, "OBTENER_MENSAJES_CORRECTO");
            JSONObject parametros = new JSONObject();
            parametros.put("listaMensajes", new JSONArray(mensajes));
            jsonRespuesta.put(PARAMETROS_NOMBRE, parametros);
        } else {
            jsonRespuesta.put(RESULTADO_RESPUESTA_NOMBRE, "OBTENER_MENSAJES_INCORRECTO");
        }

        return jsonRespuesta.toString();
    }

    public static String crearRespuestaCrearGrupo(boolean correcto) throws JSONException {
        JSONObject jsonRespuesta = new JSONObject();

        if (correcto) {
            jsonRespuesta.put(RESULTADO_RESPUESTA_NOMBRE, "GRUPO_CREADO_CORRECTO");
            JSONObject parametros = new JSONObject();
            //parametros.put("listaMensajes", new JSONArray(mensajes));
            jsonRespuesta.put(PARAMETROS_NOMBRE, parametros);
        } else {
            jsonRespuesta.put(RESULTADO_RESPUESTA_NOMBRE, "GRUPO_CREADO_INCORRECTO");
        }

        return jsonRespuesta.toString();
    }

    public static String crearRespuestaEliminarGrupo(boolean correcto) throws JSONException {
        JSONObject jsonRespuesta = new JSONObject();

        if (correcto) {
            jsonRespuesta.put(RESULTADO_RESPUESTA_NOMBRE, "GRUPO_ELIMINADO_CORRECTO");
            JSONObject parametros = new JSONObject();
            //parametros.put("listaMensajes", new JSONArray(mensajes));
            jsonRespuesta.put(PARAMETROS_NOMBRE, parametros);
        } else {
            jsonRespuesta.put(RESULTADO_RESPUESTA_NOMBRE, "GRUPO_ELIMINADO_INCORRECTO");
        }

        return jsonRespuesta.toString();
    }
    public static String crearRespuestaAnadirUsuarioGrupo(boolean correcto) throws JSONException {
            JSONObject jsonRespuesta = new JSONObject();

            if (correcto) {
                jsonRespuesta.put(RESULTADO_RESPUESTA_NOMBRE, "ANADIR_USUARIO_GRUPO_CORRECTO");
                JSONObject parametros = new JSONObject();
                //parametros.put("listaMensajes", new JSONArray(mensajes));
                jsonRespuesta.put(PARAMETROS_NOMBRE, parametros);
            } else {
                jsonRespuesta.put(RESULTADO_RESPUESTA_NOMBRE, "ANADIR_USUARIO_GRUPO_INCORRECTO");
            }

            return jsonRespuesta.toString();
        }
    public static String crearRespuestaObtenerMensajesNoLeidos(boolean correcto, List<JSONObject> mensajes) throws JSONException {
        JSONObject jsonRespuesta = new JSONObject();

        if (correcto) {
            jsonRespuesta.put(RESULTADO_RESPUESTA_NOMBRE, "OBTENER_MENSAJES_NO_LEIDOS_CORRECTO");
            JSONObject parametros = new JSONObject();
            parametros.put("listaMensajes", new JSONArray(mensajes));
            jsonRespuesta.put(PARAMETROS_NOMBRE, parametros);
        } else {
            jsonRespuesta.put(RESULTADO_RESPUESTA_NOMBRE, "OBTENER_MENSAJES_NO_LEIDOS_INCORRECTO");
        }

        return jsonRespuesta.toString();
    }
}
