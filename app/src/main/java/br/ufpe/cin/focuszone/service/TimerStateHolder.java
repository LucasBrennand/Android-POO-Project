package br.ufpe.cin.focuszone.service;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class TimerStateHolder {

    private static TimerStateHolder instancia;

    private final MutableLiveData<Long> tempoRestanteMillis = new MutableLiveData<>(0L);
    private final MutableLiveData<Boolean> emAndamento = new MutableLiveData<>(false);
    private MutableLiveData<String> nomeTarefa = new MutableLiveData<>(""); //Isso vai ser pra guardar o nome da tarefa quando fecha o app

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

    public LiveData<String> getNomeTarefa() {
        return nomeTarefa;
    }

    public void atualizarNomeTarefa(String nome){
        nomeTarefa.setValue(nome);
    }

    public static TimerStateHolder getInstancia() {
        return instancia;
    }

    public static void setInstancia(TimerStateHolder instancia) {
        TimerStateHolder.instancia = instancia;
    }

    public void atualizarTempoRestante(long millis) {
        tempoRestanteMillis.setValue(millis);
    }

    public void atualizarEmAndamento(boolean andamento) {
        emAndamento.setValue(andamento);
    }
}
