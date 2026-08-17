package br.ufpe.cin.focuszone.ui.timer;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import br.ufpe.cin.focuszone.data.repository.ConfiguracaoRepository;
import br.ufpe.cin.focuszone.service.TimerService;
import br.ufpe.cin.focuszone.service.TimerStateHolder;

public class TimerViewModel extends AndroidViewModel {

    private final TimerStateHolder stateHolder = TimerStateHolder.getInstance();
    private final ConfiguracaoRepository configuracaoRepository;

    public TimerViewModel(@NonNull Application application) {
        super(application);
        configuracaoRepository = new ConfiguracaoRepository(application);
    }

    public LiveData<Long> getTempoRestanteMillis() {
        return stateHolder.getTempoRestanteMillis();
    }

    public LiveData<Boolean> getEmAndamento() {
        return stateHolder.getEmAndamento();
    }

    public LiveData<String> getNomeTarefa(){
        return stateHolder.getNomeTarefa();
    }

    //Criado o metodo para pegar a duração total escolhida
    public long getDuracaoTotalMillis() {
        return configuracaoRepository.getDuracaoFocoMinutos() * 60_000L;
    }

    public void sincronizarDuracaoConfigurada() {
        Boolean andamento = stateHolder.getEmAndamento().getValue();
        Long tempoRestante = stateHolder.getTempoRestanteMillis().getValue();
        // Agora quando o app abre novamente e tempo estiver pausado, o timer não reseta
        if ((andamento == null || !andamento) && (tempoRestante == null || tempoRestante == 0L)) {
            stateHolder.atualizarTempoRestante(getDuracaoTotalMillis());
        }
    }

    public void iniciarContagem(String nomeTarefa) {
        Intent intent = new Intent(getApplication(), TimerService.class);
        intent.setAction(TimerService.ACTION_INICIAR);
        intent.putExtra(TimerService.EXTRA_NOME_TAREFA, nomeTarefa);
        ContextCompat.startForegroundService(getApplication(), intent);
    }

    public void pausarContagem() {
        Intent intent = new Intent(getApplication(), TimerService.class);
        intent.setAction(TimerService.ACTION_PAUSAR);
        getApplication().startService(intent);
    }

    public void cancelarContagem() {
        Intent intent = new Intent(getApplication(), TimerService.class);
        intent.setAction(TimerService.ACTION_CANCELAR);
        getApplication().startService(intent);
    }
}