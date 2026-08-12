package br.ufpe.cin.focuszone.ui.configuracao;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import br.ufpe.cin.focuszone.data.repository.ConfiguracaoRepository;

public class ConfiguracaoViewModelFactory implements ViewModelProvider.Factory {

    private final ConfiguracaoRepository repository;

    public ConfiguracaoViewModelFactory(ConfiguracaoRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ConfiguracaoViewModel(repository);
    }
}
