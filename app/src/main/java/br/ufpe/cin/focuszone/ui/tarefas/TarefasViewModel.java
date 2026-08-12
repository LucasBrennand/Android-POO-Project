package br.ufpe.cin.focuszone.ui.tarefas;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import br.ufpe.cin.focuszone.data.repository.TarefaRepository;
import br.ufpe.cin.focuszone.domain.Tarefa;

public class TarefasViewModel extends ViewModel {

    private final TarefaRepository repository;
    private final LiveData<List<Tarefa>> tarefas;

    public TarefasViewModel(@NonNull TarefaRepository repository) {
        this.repository = repository;
        this.tarefas = repository.listarTodas();
    }

    public LiveData<List<Tarefa>> getTarefas() {
        return tarefas;
    }

    public void alternarConcluida(Tarefa tarefa) {
        tarefa.setConcluida(!tarefa.isConcluida());
        new Thread(() -> repository.atualizar(tarefa)).start();
    }

    public void remover(Tarefa tarefa) {
        new Thread(() -> repository.remover(tarefa)).start();
    }

    public void adicionar(String titulo) {
        new Thread(() -> repository.inserir(new Tarefa(titulo))).start();
    }
}
