package com.example.cayo.seam;

import android.content.BroadcastReceiver;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.cayo.seam.infraestrutura.Util;
import com.example.cayo.seam.servico.NotificacaoServico;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private ListView lvwNotificacoes;
    private LinearLayout painelListaVazia;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvwNotificacoes = (ListView) findViewById(R.id.lvwNotificacoes);
        painelListaVazia = (LinearLayout) findViewById(R.id.painelListaVazia);

        exibirNotificacoes();

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("seam"))
        {
            Intent intent = new Intent(MainActivity.this, NotificacaoServico.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(".MainActivity");
        MainActivityReceiver receiver = new MainActivityReceiver();
        registerReceiver(receiver, intentFilter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.seam_menu, menu);

        return (true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.token:
                Util.exibirDialogo((ClipboardManager) getSystemService(CLIPBOARD_SERVICE), this, FirebaseInstanceId.getInstance().getToken());
                return (true);
            default:
                return (super.onOptionsItemSelected(item));
        }
    }

    private void exibirNotificacoes()
    {
        painelListaVazia.setVisibility(View.INVISIBLE);
        lvwNotificacoes.setVisibility(View.INVISIBLE);

        List<NotificacaoVisao> notificacoesVisao = NotificacaoVisao.listar(this);

        if (notificacoesVisao.isEmpty())
            painelListaVazia.setVisibility(View.VISIBLE);
        else {
            lvwNotificacoes.setVisibility(View.VISIBLE);
            lvwNotificacoes.setAdapter(new NotificacaoAdapter(this, notificacoesVisao));
        }
    }

    private class MainActivityReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            Bundle extras = intent.getExtras();
            Long idNotificacao = extras.getLong("seam");
            exibirNotificacoes();
            intent.removeExtra("seam");
        }
    }
}