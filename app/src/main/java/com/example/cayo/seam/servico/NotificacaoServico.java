package com.example.cayo.seam.servico;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.cayo.seam.infraestrutura.Util;
import com.example.cayo.seam.modelo.Notificacao;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Random;

public class NotificacaoServico extends FirebaseMessagingService {
    private static final String TAG = "NotificacaoServico";

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
        // enviarTokenAoServidor(token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            Context contexto = getApplicationContext();

            try {
                Map<String, String> mapaDados = remoteMessage.getData();
                Notificacao notificacao = new Notificacao(new JSONObject(mapaDados));
                notificacao.salvar(getApplicationContext());
                registrarCallBack(notificacao, contexto);
            } catch (JSONException excecao) {
                Log.d("Exceção: ", excecao.toString());
            }
        }
    }

    private void enviarTokenAoServidor(String token) {
        FirebaseMessaging servicoMensageiroFirebase = FirebaseMessaging.getInstance();
        servicoMensageiroFirebase.send(new RemoteMessage.Builder("alarmsys-4c2f7@gcm.googleapis.com")
                .setMessageId(Integer.toString(new Random().nextInt()))
                .addData("message", token)
                .addData("action", "REGISTRAR_TOKEN")
                .build());
    }

    private void registrarCallBack(Notificacao notificacao, Context contexto) {
        Intent intent = new Intent();
        intent.putExtra("seam", notificacao.getId());
        intent.setAction(".MainActivity");
        sendBroadcast(intent);

        Util.notificar(contexto, (int) notificacao.getId(), "SEAM " + String.valueOf(notificacao.getId()), "Alerta em " + notificacao.getData());
    }
}