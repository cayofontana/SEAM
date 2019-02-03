package com.example.cayo.seam.dados;

import android.provider.BaseColumns;

public final class NotificacaoContrato
{
    private NotificacaoContrato()
    {
    }

    public static class NotificacaoVerbete implements BaseColumns
    {
        public static final String TABELA = "DETECCAO";
        public static final String COLUNA_ID = "DETE_ID_DETECCAO";
        public static final String COLUNA_VALOR_DISTANCIA = "DETE_VL_DISTANCIA_MEDIA";
        public static final String COLUNA_DATA = "DETE_DT_DETECCAO";
        public static final String COLUNA_VISUALIZOU = "DETE_FL_VISUALIZOU";
    }
}
