package br.ufpe.cin.focuszone.ui.tarefas;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import br.ufpe.cin.focuszone.data.repository.TarefaRepository;

public class TarefasViewModelFactory implements ViewModelProvider.Factory {

    private final TarefaRepository repository;

    public TarefasViewModelFactory(TarefaRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new TarefasViewModel(repository);
    }
}
