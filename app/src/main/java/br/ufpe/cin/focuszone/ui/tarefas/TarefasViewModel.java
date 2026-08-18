package br.ufpe.cin.focuszone.ui.tarefas;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import br.ufpe.cin.focuszone.data.repository.TarefaRepository;
import br.ufpe.cin.focuszone.domain.Tarefa;

public class TarefasViewModel extends ViewModel {

    private final TarefaRepository repository;
    private final LiveData<List<Tarefa>> tarefas;
    private final MutableLiveData<String> tituloBusca = new MutableLiveData<>("");

    public TarefasViewModel(@NonNull TarefaRepository repository) {
        this.repository = repository;
        //Utilizamos o Transformations.switchMap para que ele observe as alterações do titulo buscado, depois fazemos uma condição para mostrar o titulo que foi buscado ou todos os titulos se o input estiver vazio
        this.tarefas = Transformations.switchMap(tituloBusca, titulo -> {
            if (titulo == null || titulo.trim().isEmpty()){
                return repository.listarTodas();
            }
            else{
                return repository.buscarPorTitulo(titulo);
            }
        });
    }

    public LiveData<List<Tarefa>> getTarefas() {
        return tarefas;
    }

    public void alternarConcluida(Tarefa tarefa) {
        tarefa.setConcluida(!tarefa.isConcluida());
        new Thread(() -> repository.atualizar(tarefa)).start();
    }

    //Esse metodo é pra definir o titulo que vamos buscar
    public void setTituloBusca(String titulo){
        tituloBusca.setValue(titulo);
    }

    public void remover(Tarefa tarefa) {
        new Thread(() -> repository.remover(tarefa)).start();
    }

    public void adicionar(String titulo) {
        new Thread(() -> repository.inserir(new Tarefa(titulo))).start();
    }
}
