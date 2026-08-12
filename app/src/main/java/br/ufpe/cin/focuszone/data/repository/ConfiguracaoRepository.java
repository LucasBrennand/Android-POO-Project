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
}
