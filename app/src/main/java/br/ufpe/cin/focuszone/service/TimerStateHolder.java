package br.ufpe.cin.focuszone.service;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class TimerStateHolder {

    //Vai representar o estado do ciclo atual
    public static final int TIPO_FOCO = 1;
    public static final int TIPO_PAUSA_CURTA = 2;

    private static TimerStateHolder instancia;

    private final MutableLiveData<Long> tempoRestanteMillis = new MutableLiveData<>(0L);
    private final MutableLiveData<Boolean> emAndamento = new MutableLiveData<>(false);
    private final MutableLiveData<String> nomeTarefa = new MutableLiveData<>("");
    private final MutableLiveData<Integer> tipoCicloAtual = new MutableLiveData<>(TIPO_FOCO);
    private final MutableLiveData<Integer> cicloAtual = new MutableLiveData<>(1);

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

    public LiveData<Integer> getTipoCicloAtual() {
        return tipoCicloAtual;
    }

    public LiveData<Integer> getCicloAtual() {
        return cicloAtual;
    }

    public void atualizarNomeTarefa(String nome) {
        nomeTarefa.postValue(nome);
    }

    public void atualizarTempoRestante(long millis) {
        tempoRestanteMillis.postValue(millis);
    }

    public void atualizarEmAndamento(boolean andamento) {
        emAndamento.postValue(andamento);
    }

    public void atualizarTipoCiclo(int tipo) {
        tipoCicloAtual.postValue(tipo);
    }

    public void atualizarCicloAtual(int ciclo) {
        cicloAtual.postValue(ciclo);
    }
}