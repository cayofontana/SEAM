package com.example.cayo.seam;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cayo.seam.infraestrutura.Util;

import java.util.List;

public class NotificacaoAdapter extends ArrayAdapter<NotificacaoVisao>
{
    private Context contexto;
    private List<NotificacaoVisao> notificacoes;

    public NotificacaoAdapter(Context contexto, List<NotificacaoVisao> notificacoes)
    {
        super(contexto, R.layout.linha_lista, notificacoes);
        this.contexto = contexto;
        this.notificacoes = notificacoes;
    }

    @Override
    public View getView(final int indice, View visaoConversao, ViewGroup elementoPai)
    {
        LayoutInflater preenchedorLeiaute = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View linhavisao = preenchedorLeiaute.inflate(R.layout.linha_lista, elementoPai, false);

        TextView titulo = (TextView) linhavisao.findViewById(R.id.titulo);
        TextView mensagem = (TextView) linhavisao.findViewById(R.id.mensagem);
        ImageView icone = (ImageView) linhavisao.findViewById(R.id.icone);

        final NotificacaoVisao notificacaoVisao = notificacoes.get(indice);
        titulo.setText(notificacaoVisao.getTitulo());
        mensagem.setText(notificacaoVisao.getMensagem() + " " + notificacaoVisao.getData() + ".");
        icone.setImageResource(R.drawable.icone);

        if (!notificacaoVisao.estaVisualizada())
        {
            int color = Color.argb(127, 63, 255, 63);
            linhavisao.setBackgroundColor(color);
        }

        linhavisao.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                notificacaoVisao.getNotificacao().setVisualizou(1);
                notificacaoVisao.atualizar(contexto);
                int color = Color.argb(255, 48, 48, 48);
                v.setBackgroundColor(color);
                Util.cancelarNotificacao(contexto);
            }
        });

        return (linhavisao);
    }
}