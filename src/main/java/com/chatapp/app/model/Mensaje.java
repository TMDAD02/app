package com.chatapp.app.model;

import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "mensajes")
public class Mensaje {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Usuario fuente;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Usuario destino;

    @ManyToOne(optional = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Grupo destinogrupo;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "contenido")
    private String contenido;

    @Column(name = "leido")
    private boolean leido;



    public Mensaje(Usuario fuente, Usuario destino, Date fecha, String contenido, boolean leido) {
        this.fuente = fuente;
        this.destino = destino;
        this.fecha = fecha;
        this.contenido = contenido;
        this.leido = leido;
    }

    public Mensaje(Usuario fuente, Grupo destino, Date fecha, String contenido, boolean leido) {
        this.fuente = fuente;
        this.destino = new Usuario();
        this.destinogrupo = destino;
        this.fecha = fecha;
        this.contenido = contenido;
        this.leido = leido;
    }

    public Mensaje() {}

    public String getContenido() {
        return contenido;
    }

    public void setLeido() {
        this.leido = true;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("fuente", this.fuente.getNombre());
        jsonObject.put("destino", this.destino.getNombre());
        jsonObject.put("fecha", this.fecha);
        jsonObject.put("contenido", this.contenido);
        jsonObject.put("leido", this.leido);

        return jsonObject;
    }

}
