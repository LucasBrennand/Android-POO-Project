package br.ufpe.cin.focuszone.ui.configuracao;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import br.ufpe.cin.focuszone.data.repository.ConfiguracaoRepository;

public class ConfiguracaoViewModel extends ViewModel {

    private final ConfiguracaoRepository repository;
    private final MutableLiveData<Integer> duracaoFocoMinutos = new MutableLiveData<>();
    private final MutableLiveData<Integer> duracaoPausaMinutos = new MutableLiveData<>();
    private final MutableLiveData<Integer> totalCiclos = new MutableLiveData<>();

    public ConfiguracaoViewModel(@NonNull ConfiguracaoRepository repository) {
        this.repository = repository;
        duracaoFocoMinutos.setValue(repository.getDuracaoFocoMinutos());
        duracaoPausaMinutos.setValue(repository.getDuracaoPausaMinutos());
        totalCiclos.setValue(repository.getTotalCiclos());
    }

    public LiveData<Integer> getDuracaoFocoMinutos() {
        return duracaoFocoMinutos;
    }

    public LiveData<Integer> getDuracaoPausaMinutos() {
        return duracaoPausaMinutos;
    }

    public LiveData<Integer> getTotalCiclos() {
        return totalCiclos;
    }

    public void salvarConfiguracoes(int focoMinutos, int pausaMinutos, int ciclos) {
        repository.salvarDuracaoFocoMinutos(focoMinutos);
        repository.salvarDuracaoPausaMinutos(pausaMinutos);
        repository.salvarTotalCiclos(ciclos);

        duracaoFocoMinutos.setValue(focoMinutos);
        duracaoPausaMinutos.setValue(pausaMinutos);
        totalCiclos.setValue(ciclos);
    }
}