package br.ufpe.cin.focuszone.data.repository;

import android.content.Context;

import br.ufpe.cin.focuszone.data.prefs.ConfiguracaoStore;

public class ConfiguracaoRepository {

    private final ConfiguracaoStore store;

    public ConfiguracaoRepository(Context context) {
        store = new ConfiguracaoStore(context);
    }

    public int getDuracaoFocoMinutos() {
        return store.getDuracaoFocoMinutos();
    }

    public void salvarDuracaoFocoMinutos(int minutos) {
        store.salvarDuracaoFocoMinutos(minutos);
    }

    public int getDuracaoPausaMinutos() {
        return store.getDuracaoPausaMinutos();
    }

    public void salvarDuracaoPausaMinutos(int minutos) {
        store.salvarDuracaoPausaMinutos(minutos);
    }

    public int getTotalCiclos() {
        return store.getTotalCiclos();
    }

    public void salvarTotalCiclos(int ciclos) {
        store.salvarTotalCiclos(ciclos);
    }
}