package com.chatapp.app.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "trending")
public class Trending {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "palabra", unique = true)
    private String palabra;

    @Column(name = "contador")
    private int contador;

    public Trending(String palabra, int contador) {
        this.palabra = palabra;
        this.contador = contador;
    }

    public Trending() { }
}
