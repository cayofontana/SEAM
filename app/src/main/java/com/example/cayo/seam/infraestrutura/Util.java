package com.example.cayo.seam.infraestrutura;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.TextView;

import com.example.cayo.seam.MainActivity;
import com.example.cayo.seam.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util
{
    private Util() {}

    public static String alterarFormatoData(String strData, String formato)
    {
        String novaStrData = null;
        SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date data = null;
        try
        {
            data = formatoData.parse(strData);
            formatoData.applyPattern("dd/MM/yyyy hh:mm:ss");
            novaStrData = formatoData.format(data);
        }
        catch (ParseException excecao)
        {
            excecao.printStackTrace();
        }
        return (novaStrData);
    }

    public static void notificar(Context contexto, int id, String titulo, String mensagem)
    {
        NotificationManager gerenteNotificacao = (NotificationManager) contexto.getSystemService(Context.NOTIFICATION_SERVICE);

        String CHANNEL_ID = "seam_01";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            CharSequence nome = "seam";
            String descricao = "Canal App SEAM";
            int importancia = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel canalNotificacao = new NotificationChannel(CHANNEL_ID, nome, importancia);
            canalNotificacao.setDescription(descricao);
            canalNotificacao.enableLights(true);
            canalNotificacao.setLightColor(Color.RED);
            canalNotificacao.enableVibration(true);
            canalNotificacao.setVibrationPattern(new long[] { 100, 200, 300, 400, 500, 400, 300, 200, 400 });
            canalNotificacao.setShowBadge(false);
            gerenteNotificacao.createNotificationChannel(canalNotificacao);
        }

        NotificationCompat.Builder construtor = new NotificationCompat.Builder(contexto, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(titulo)
                .setContentText(mensagem)
                .setAutoCancel(true);

        Intent intent = new Intent(contexto, MainActivity.class);
        TaskStackBuilder pilhaTarefasConstrutor = TaskStackBuilder.create(contexto);
        pilhaTarefasConstrutor.addParentStack(MainActivity.class);
        pilhaTarefasConstrutor.addNextIntent(intent);
        PendingIntent resultPendingIntent = pilhaTarefasConstrutor.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        construtor.setContentIntent(resultPendingIntent);

        gerenteNotificacao.notify(id, construtor.build());
    }

    public static void cancelarNotificacao(Context contexto, int id)
    {
        String servicoNotificacao = Context.NOTIFICATION_SERVICE;
        NotificationManager gerenteNotificacao = (NotificationManager) contexto.getSystemService(servicoNotificacao);
        gerenteNotificacao.cancel(id);
    }

    public static void exibirDialogo(final ClipboardManager gerenteClipboard, Context contexto, final String mensagem)
    {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(contexto);
        dialogo.setIcon(R.drawable.common_google_signin_btn_text_light_normal_background);

        ViewGroup elementosVisao = ((Activity) contexto).findViewById(android.R.id.content);

        View dialogoVisao = LayoutInflater.from(contexto).inflate(R.layout.dialogo, elementosVisao, false);
        final TextView tvwMensagemCopiado = (TextView) dialogoVisao.findViewById(R.id.tvwMensagemCopiado);

        final Animation animacaoInicial = new AlphaAnimation(0.0f, 1.0f);
        animacaoInicial.setDuration(3000);
        final Animation animacaoFinal = new AlphaAnimation(1.0f, 0.0f);
        animacaoFinal.setDuration(3000);

        animacaoFinal.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation arg0) {
                tvwMensagemCopiado.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAnimationRepeat(Animation arg0) {
            }
            @Override
            public void onAnimationEnd(Animation arg0) {
                tvwMensagemCopiado.setVisibility(View.INVISIBLE);
            }
        });
        final TextView tvwToken = (TextView) dialogoVisao.findViewById(R.id.tvwToken);
        tvwToken.setText(mensagem);
        tvwToken.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AnimationSet as = new AnimationSet(true);
                as.addAnimation(animacaoFinal);
                animacaoInicial.setStartOffset(2000);
                as.addAnimation(animacaoInicial);
                tvwToken.startAnimation(animacaoFinal);

                ClipData clip = ClipData.newPlainText("token", tvwToken.getText().toString());
                gerenteClipboard.setPrimaryClip(clip);
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
        builder.setCancelable(false);
        builder.setView(dialogoVisao);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        Button btnFechar = (Button) dialogoVisao.findViewById(R.id.btnFechar);
        btnFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
}