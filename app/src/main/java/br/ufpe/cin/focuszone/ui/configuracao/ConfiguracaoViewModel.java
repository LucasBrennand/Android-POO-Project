package br.ufpe.cin.focuszone.ui.configuracao;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import br.ufpe.cin.focuszone.data.repository.ConfiguracaoRepository;

public class ConfiguracaoViewModel extends ViewModel {

    private final ConfiguracaoRepository repository;
    private final MutableLiveData<Integer> duracaoFocoMinutos = new MutableLiveData<>();

    public ConfiguracaoViewModel(@NonNull ConfiguracaoRepository repository) {
        this.repository = repository;
        duracaoFocoMinutos.setValue(repository.getDuracaoFocoMinutos());
    }

    public LiveData<Integer> getDuracaoFocoMinutos() {
        return duracaoFocoMinutos;
    }

    public void salvarDuracao(int minutos) {
        repository.salvarDuracaoFocoMinutos(minutos);
        duracaoFocoMinutos.setValue(minutos);
    }
}
