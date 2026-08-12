package br.ufpe.cin.focuszone.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class ConfiguracaoStore {

    private static final String PREFS_NAME = "focuszone_config";
    private static final String KEY_DURACAO_FOCO = "duracaoFocoMinutos";

    private final SharedPreferences preferences;

    public ConfiguracaoStore(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public int getDuracaoFocoMinutos() {
        return preferences.getInt(KEY_DURACAO_FOCO, 25);
    }

    public void salvarDuracaoFocoMinutos(int minutos) {
        preferences.edit()
                .putInt(KEY_DURACAO_FOCO, minutos)
                .apply();
    }
}
