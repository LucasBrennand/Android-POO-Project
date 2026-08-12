package br.ufpe.cin.focuszone.service;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class TimerStateHolder {

    private static TimerStateHolder instancia;

    private final MutableLiveData<Long> tempoRestanteMillis = new MutableLiveData<>(0L);
    private final MutableLiveData<Boolean> emAndamento = new MutableLiveData<>(false);

    private TimerStateHolder() {
    }

    public static synchronized TimerStateHolder getInstance() {
        if (instancia == null) {
            instancia = new TimerStateHolder();
        }
        return instancia;
    }

    public LiveData<Long> getTempoRestanteMillis() {
        return tempoRestanteMillis;
    }

    public LiveData<Boolean> getEmAndamento() {
        return emAndamento;
    }

    public void atualizarTempoRestante(long millis) {
        tempoRestanteMillis.setValue(millis);
    }

    public void atualizarEmAndamento(boolean andamento) {
        emAndamento.setValue(andamento);
    }
}
