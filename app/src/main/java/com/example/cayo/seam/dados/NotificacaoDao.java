package com.example.cayo.seam.dados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cayo.seam.dados.NotificacaoContrato.NotificacaoVerbete;
import com.example.cayo.seam.modelo.Notificacao;

import java.util.ArrayList;
import java.util.List;

public class NotificacaoDao
{
    private Context contexto;
    private SQLiteDatabase bancoDeDados;

    public NotificacaoDao(Context contexto)
    {
        this.contexto = contexto;
        DBHelper dbHelper = new DBHelper(contexto);
        bancoDeDados = dbHelper.getWritableDatabase();
    }

    public List<Notificacao> listar()
    {
        List<Notificacao> notificacoes = null;

        try
        {
            Cursor cursor = bancoDeDados.query(NotificacaoVerbete.TABELA, new String[] {NotificacaoVerbete.COLUNA_ID, NotificacaoVerbete.COLUNA_VALOR_DISTANCIA, NotificacaoVerbete.COLUNA_DATA, NotificacaoVerbete.COLUNA_VISUALIZOU}, null, null, null, null, NotificacaoVerbete.COLUNA_ID + " DESC", null);

            notificacoes = new ArrayList<Notificacao>();

            if (cursor.getCount() > 0)
            {
                cursor.moveToFirst();
                do
                {
                    Notificacao notificacao = new Notificacao(cursor.getLong(0), cursor.getDouble(1), cursor.getString(2), cursor.getInt(3));
                    notificacoes.add(notificacao);
                }
                while (cursor.moveToNext());

                if (cursor != null && !cursor.isClosed())
                    cursor.close();
            }
        }
        catch (Exception excecao)
        {
            // TRATAR EXCEÇÃO (CRIAR CLASSE)
        }

        return (notificacoes);
    }

    public long inserir(Notificacao notificacao)
    {
        ContentValues valores = new ContentValues();
        valores.put(NotificacaoVerbete.COLUNA_ID, notificacao.getId());
        valores.put(NotificacaoVerbete.COLUNA_VALOR_DISTANCIA, notificacao.getDistanciaMedia());
        valores.put(NotificacaoVerbete.COLUNA_DATA, notificacao.getData());
        valores.put(NotificacaoVerbete.COLUNA_VISUALIZOU, notificacao.getVisualizou());

        return (bancoDeDados.insert(NotificacaoVerbete.TABELA, null, valores));
    }

    public int atualizar(Notificacao notificacao)
    {
        ContentValues valores = new ContentValues();
        valores.put(NotificacaoVerbete.COLUNA_VISUALIZOU, notificacao.getVisualizou());
        String filtro = NotificacaoVerbete.COLUNA_ID + " = ?";
        String[] argumentosFiltro = { Long.toString(notificacao.getId()) };
        return (bancoDeDados.update(NotificacaoVerbete.TABELA, valores, filtro, argumentosFiltro));
    }

    public void excluirTodos()
    {
        bancoDeDados.delete(NotificacaoVerbete.TABELA, null, null);
    }
}