package br.ufpe.cin.focuszone.service;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import java.time.Instant;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import br.ufpe.cin.focuszone.R;
import br.ufpe.cin.focuszone.data.repository.ConfiguracaoRepository;
import br.ufpe.cin.focuszone.data.repository.SessaoRepository;
import br.ufpe.cin.focuszone.domain.Sessao;
import br.ufpe.cin.focuszone.ui.timer.TimerActivity;

public class TimerService extends Service {

    public static final String ACTION_INICIAR = "br.ufpe.cin.focuszone.action.INICIAR";
    public static final String ACTION_PAUSAR = "br.ufpe.cin.focuszone.action.PAUSAR";
    public static final String ACTION_CANCELAR = "br.ufpe.cin.focuszone.action.CANCELAR";
    public static final String EXTRA_NOME_TAREFA = "nome_tarefa";
    public static final String ACTION_BROADCAST_PAUSAR = "br.ufpe.cin.focuszone.broadcast.PAUSAR";
    private static final String CHANNEL_ID = "focus_zone_timer";
    private static final int NOTIFICATION_ID = 2;
    private static final int NOTIFICATION_ID_FIM_FOCO = 1;
    private final TimerStateHolder stateHolder = TimerStateHolder.getInstance();
    private ConfiguracaoRepository configuracaoRepository;
    private SessaoRepository sessaoRepository;
    private CountDownTimer timerFoco;
    private String nomeTarefaAtual;
    private TimerActionReceiver actionReceiver;
    private Long tempoRestanteAtual; //Pega o tempo que falta
    private int tipoCicloAtual = TimerStateHolder.TIPO_FOCO;
    private int cicloAtual = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        configuracaoRepository = new ConfiguracaoRepository(this);
        sessaoRepository = new SessaoRepository(this);
        criarCanalDeNotificacao();

        actionReceiver = new TimerActionReceiver();
        IntentFilter filter = new IntentFilter(ACTION_BROADCAST_PAUSAR);
        ContextCompat.registerReceiver(this, actionReceiver, filter, ContextCompat.RECEIVER_NOT_EXPORTED);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null || intent.getAction() == null) {
            return START_NOT_STICKY;
        }
        switch (intent.getAction()) {
            case ACTION_INICIAR:
                nomeTarefaAtual = intent.getStringExtra(EXTRA_NOME_TAREFA);
                iniciarContagem();
                break;
            case ACTION_PAUSAR:
                pausarContagem();
                break;
            case ACTION_CANCELAR:
                cancelarContagem();
                break;
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(actionReceiver);
        if (timerFoco != null) {
            timerFoco.cancel();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // TODO: sobrescreva onTaskRemoved(Intent rootIntent) — o callback que o Service
    // recebe quando o usuário remove o app dos Recents (não só sai pelo Home) — e
    // registre um Log.d confirmando que o TimerService continua rodando. Só tem
    // efeito depois de declarar android:stopWithTask="false" no <service> do
    // AndroidManifest.xml (tem outro // TODO lá). Ver exercicios/aula-08/enunciado.md.

    @Override
    public void onTaskRemoved(Intent rootIntent){
        super.onTaskRemoved(rootIntent);
        android.util.Log.d("TimerService", "Task removida dos Recents — TimerService continua rodando");
    }

    private void iniciarContagem() {
        if (timerFoco != null) {
            timerFoco.cancel();
        }

        if (nomeTarefaAtual != null && !nomeTarefaAtual.trim().isEmpty()){
            stateHolder.atualizarNomeTarefa(nomeTarefaAtual);
        }

        this.tempoRestanteAtual = stateHolder.getTempoRestanteMillis().getValue();
        //Se o tempo que falta for null ou 0, declaramos 0 segundos faltando, se não pegue o valor dos segundos que faltam
        if (this.tempoRestanteAtual == null){
            this.tempoRestanteAtual = 0L;
        }
        long duracaoMinutos = (tipoCicloAtual == TimerStateHolder.TIPO_FOCO) ? configuracaoRepository.getDuracaoFocoMinutos() : configuracaoRepository.getDuracaoPausaMinutos();
        long duracaoMillis = duracaoMinutos * 60_000L;
        if (tempoRestanteAtual > 0 && tempoRestanteAtual < duracaoMillis){
            duracaoMillis = tempoRestanteAtual;
        }
        startForeground(NOTIFICATION_ID,
                construirNotificacao(getString(R.string.notif_foco_andamento, nomeTarefaAtual)));

        timerFoco = new CountDownTimer(duracaoMillis, 1_000) {
            @Override
            public void onTick(long millisUntilFinished) {
                stateHolder.atualizarTempoRestante(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                stateHolder.atualizarTempoRestante(0L);
                // Altera o ciclo
                avancarProximoCiclo();
            }
        };
        stateHolder.atualizarEmAndamento(true);
        timerFoco.start();
    }

    private void avancarProximoCiclo() {
        int totalCiclosConfigurados = configuracaoRepository.getTotalCiclos();
        if (tipoCicloAtual == TimerStateHolder.TIPO_FOCO) {
            registrarSessao(true);
            notificarFimDeFoco();
            // Muda para Pausa Curta pra manter o ciclo atual
            tipoCicloAtual = TimerStateHolder.TIPO_PAUSA_CURTA;
            stateHolder.atualizarTempoRestante(configuracaoRepository.getDuracaoPausaMinutos() * 60_000L);
            iniciarContagem();
        } else {
            // Se Estavae em pausa, incrementa o ciclo
            cicloAtual++;
            if (cicloAtual <= totalCiclosConfigurados) {
                tipoCicloAtual = TimerStateHolder.TIPO_FOCO;
                stateHolder.atualizarTempoRestante(configuracaoRepository.getDuracaoFocoMinutos() * 60_000L);
                iniciarContagem();
            } else {
                resetarEstadoCompleto();
            }
        }
    }

    private void resetarEstadoCompleto() {
        tipoCicloAtual = TimerStateHolder.TIPO_FOCO;
        cicloAtual = 1;
        stateHolder.atualizarTipoCiclo(TimerStateHolder.TIPO_FOCO);
        stateHolder.atualizarCicloAtual(1);
        stateHolder.atualizarEmAndamento(false);
        stateHolder.atualizarTempoRestante(configuracaoRepository.getDuracaoFocoMinutos() * 60_000L);
        stateHolder.atualizarNomeTarefa("");
        stopForeground(STOP_FOREGROUND_REMOVE);
        stopSelf();
    }

    private void pausarContagem() {
        if (timerFoco != null) {
            timerFoco.cancel();
            timerFoco = null;
        }
        stateHolder.atualizarEmAndamento(false);
        atualizarNotificacao(getString(R.string.notif_foco_pausado));
    }

    private void cancelarContagem() {
        if (timerFoco != null) {
            timerFoco.cancel();
            timerFoco = null;
        }
        stateHolder.atualizarEmAndamento(false);
        stateHolder.atualizarTempoRestante(configuracaoRepository.getDuracaoFocoMinutos() * 60_000L);
        stateHolder.atualizarNomeTarefa("");
        stopForeground(STOP_FOREGROUND_REMOVE);
        stopSelf();
    }

    private void registrarSessao(boolean concluida) {
        String tituloTarefa = nomeTarefaAtual;
        int duracao = configuracaoRepository.getDuracaoFocoMinutos();
        new Thread(() -> {
            Sessao sessao = new Sessao(tituloTarefa, duracao, Instant.now(), concluida);
            sessaoRepository.inserir(sessao);
        }).start();
    }

    private void criarCanalDeNotificacao() {
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID, getString(R.string.canal_timer_nome), NotificationManager.IMPORTANCE_LOW);
        channel.setDescription(getString(R.string.canal_timer_descricao));
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);
    }

    private Notification construirNotificacao(String texto) {
        Intent abrirAppIntent = new Intent(this, TimerActivity.class);
        PendingIntent contentPendingIntent = PendingIntent.getActivity(
                this, 0, abrirAppIntent, PendingIntent.FLAG_IMMUTABLE);

        Intent pausarIntent = new Intent(ACTION_BROADCAST_PAUSAR);
        PendingIntent pausarPendingIntent = PendingIntent.getBroadcast(
                this, 0, pausarIntent, PendingIntent.FLAG_IMMUTABLE);

        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(getString(R.string.titulo_notificacao_foco))
                .setContentText(texto)
                .setContentIntent(contentPendingIntent)
                .setOngoing(true)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .addAction(0, getString(R.string.acao_pausar), pausarPendingIntent)
                .build();
    }

    private void atualizarNotificacao(String texto) {
        NotificationManagerCompat.from(this)
                .notify(NOTIFICATION_ID, construirNotificacao(texto));
    }

    private void notificarFimDeFoco() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(getString(R.string.titulo_notificacao_pausa))
                .setContentText(getString(R.string.msg_notificacao_pausa))
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                == PackageManager.PERMISSION_GRANTED) {
            NotificationManagerCompat.from(this).notify(NOTIFICATION_ID_FIM_FOCO, builder.build());
        }
    }

}
