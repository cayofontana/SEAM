package com.example.cayo.seam;

import android.content.Context;

import com.example.cayo.seam.infraestrutura.Util;
import com.example.cayo.seam.modelo.Notificacao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotificacaoVisao
{
    private String titulo;
    private String mensagem;
    private String data;
    private int icone;

    private Notificacao notificacao;

    public NotificacaoVisao(String titulo, String mensagem, String data, int icone)
    {
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.data = data;
        this.icone = icone;
    }

    public NotificacaoVisao(Notificacao notificacao, String titulo, String mensagem, String data, int icone)
    {
        this(titulo, mensagem, data, icone);
        this.notificacao = notificacao;
    }

    public String getTitulo()
    {
        return (titulo);
    }

    public void setTitulo(String titulo)
    {
        this.titulo = titulo;
    }

    public String getMensagem()
    {
        return (mensagem);
    }

    public void setMensagem(String mensagem)
    {
        this.mensagem = mensagem;
    }

    public String getData()
    {
        return (data);
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getIcone()
    {
        return (icone);
    }

    public void setIcone(int icone)
    {
        this.icone = icone;
    }

    public boolean estaVisualizada()
    {
        return (notificacao.getVisualizou() == 1 ? true : false);
    }

    public int atualizar(Context contexto)
    {
        return (notificacao.atualizar(contexto));
    }

    public Notificacao getNotificacao()
    {
        return (notificacao);
    }

    public static List<NotificacaoVisao> listar(Context contexto)
    {
        List<NotificacaoVisao> notificacoesVisao = new ArrayList<NotificacaoVisao>();

        for (Notificacao notificacao : Notificacao.listar(contexto))
            notificacoesVisao.add(new NotificacaoVisao(notificacao,"Evento " + notificacao.getId() + ": Possível Invasão", "Sensores notificaram presença de objeto em ", Util.alterarFormatoData(notificacao.getData(), "dd/MM/yyyy hh:mm:ss"), 1));

        return (notificacoesVisao);
    }
}