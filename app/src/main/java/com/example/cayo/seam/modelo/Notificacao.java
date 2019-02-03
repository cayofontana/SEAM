package com.example.cayo.seam.modelo;

import android.content.Context;
import android.util.Log;

import com.example.cayo.seam.dados.NotificacaoDao;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Notificacao
{
    private long id;
    private double distanciaMedia;
    private String data;
    private int visualizou;

    public Notificacao(long id, double distanciaMedia, String data)
    {
        this.id = id;
        this.distanciaMedia = distanciaMedia;
        this.data = data;
        visualizou = 0;
    }

    public Notificacao(long id, double distanciaMedia, String data, int visualizou)
    {
        this(id, distanciaMedia, data);     
        this.visualizou = visualizou;
    }

    public Notificacao(JSONObject objetoJSON) throws JSONException
    {
        this(objetoJSON.getLong("id"), objetoJSON.getDouble("valor"), objetoJSON.getString("datahora"));
    }

    public long getId()
    {
        return (id);
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public double getDistanciaMedia()
    {
        return (distanciaMedia);
    }

    public void setDistanciaMedia(double distanciaMedia)
    {
        this.distanciaMedia = distanciaMedia;
    }

    public String getData()
    {
        return (data);
    }

    public void setData(String data)
    {
        this.data = data;
    }

    public int getVisualizou()
    {
        return (visualizou);
    }

    public void setVisualizou(int visualizou)
    {
        this.visualizou = visualizou;
    }

    public int atualizar(Context contexto)
    {
        NotificacaoDao notificacaoDao = new NotificacaoDao(contexto);
        return (notificacaoDao.atualizar(this));
    }

    public long salvar(Context contexto)
    {
        NotificacaoDao notificacaoDao = new NotificacaoDao(contexto);
        return (notificacaoDao.inserir(this));
    }

    public void imprimir()
    {
        Log.d("ID: ",  Long.toString(id));
        Log.d("Distância Média: ",  Double.toString(distanciaMedia));
        Log.d("Data: ",  data);
    }

    public static List<Notificacao> listar(Context contexto)
    {
        NotificacaoDao notificacaoDao = new NotificacaoDao(contexto);

        return (notificacaoDao.listar());
    }
}