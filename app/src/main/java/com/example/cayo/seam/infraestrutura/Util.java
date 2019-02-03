package com.example.cayo.seam.infraestrutura;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
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

    public static void notificar(Context contexto, String titulo, String mensagem)
    {
        int NOTIFICATION_ID = 234;

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

        gerenteNotificacao.notify(NOTIFICATION_ID++, construtor.build());
    }

    public static void exibirPopup(final ClipboardManager gerenteClipboard, Context contexto, final String titulo, final String mensagem)
    {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(contexto);
        dialogo.setTitle(titulo);
        dialogo.setIcon(R.drawable.common_google_signin_btn_text_light_normal_background);


        final Animation in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(3000);

        final Animation out = new AlphaAnimation(1.0f, 0.0f);
        out.setDuration(3000);

        final TextView caixaTexto = new TextView(contexto);
        caixaTexto.setText(mensagem);
        caixaTexto.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AnimationSet as = new AnimationSet(true);
                as.addAnimation(out);
                in.setStartOffset(3000);
                as.addAnimation(in);
                caixaTexto.startAnimation(out);

                ClipData clip = ClipData.newPlainText("label", caixaTexto.getText().toString());
                gerenteClipboard.setPrimaryClip(clip);
            }
        });

        dialogo.setView(caixaTexto);
        dialogo.setPositiveButton("Fechar", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialoginterface, int i)
            {
                dialoginterface.dismiss();
            }
        }).show();
    }
}