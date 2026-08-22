package br.ufpe.cin.focuszone.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class ConfiguracaoStore {

    private static final String PREFS_NAME = "focuszone_config";
    private static final String KEY_DURACAO_FOCO = "duracaoFocoMinutos";
    private static final String KEY_DURACAO_PAUSA = "duracaoPausaMinutos";
    private static final String KEY_TOTAL_CICLOS = "totalCiclos";

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

    public int getDuracaoPausaMinutos() {
        return preferences.getInt(KEY_DURACAO_PAUSA, 5);
    }

    public void salvarDuracaoPausaMinutos(int minutos) {
        preferences.edit()
                .putInt(KEY_DURACAO_PAUSA, minutos)
                .apply();
    }

    public int getTotalCiclos() {
        return preferences.getInt(KEY_TOTAL_CICLOS, 4);
    }

    public void salvarTotalCiclos(int ciclos) {
        preferences.edit()
                .putInt(KEY_TOTAL_CICLOS, ciclos)
                .apply();
    }
}
