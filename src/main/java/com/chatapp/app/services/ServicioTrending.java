package com.chatapp.app.services;

import com.chatapp.app.model.Trending;
import com.chatapp.app.model.Usuario;
import com.chatapp.app.repository.RepositorioGrupo;
import com.chatapp.app.repository.RepositorioTrending;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ServicioTrending {
    @Autowired
    private RepositorioTrending repositorioTrending;

    @Transactional
    public void actualizarTrending(JSONObject listaTrending) throws JSONException {
        repositorioTrending.truncate();
        Iterator keys = listaTrending.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            int value = listaTrending.getInt(key);
            Trending t  = new Trending(key, value);
            repositorioTrending.save(t);
        }
    }

}
