package com.example.cayo.seam.dados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.cayo.seam.dados.NotificacaoContrato.NotificacaoVerbete;

public class DBHelper extends SQLiteOpenHelper
{
    private static final int VERSAO_DATABASE = 5;
    private static final String NOME_DATABASE = "SEAM.db";

    private static final String SQL_CRIAR_TABELAS =
            "CREATE TABLE " + NotificacaoVerbete.TABELA +
                    " (" +
                    NotificacaoVerbete.COLUNA_ID + " INTEGER PRIMARY KEY," +
                    NotificacaoVerbete.COLUNA_VALOR_DISTANCIA + " REAL," +
                    NotificacaoVerbete.COLUNA_DATA + " TEXT," +
                    NotificacaoVerbete.COLUNA_VISUALIZOU + " INTEGER" +
                    ")";

    private static final String SQL_REMOVER_TABELAS =
            "DROP TABLE IF EXISTS " + NotificacaoVerbete.TABELA;

    DBHelper(Context contexto)
    {
        super(contexto, NOME_DATABASE, null, VERSAO_DATABASE);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(SQL_CRIAR_TABELAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_REMOVER_TABELAS);
        onCreate(db);
    }
}