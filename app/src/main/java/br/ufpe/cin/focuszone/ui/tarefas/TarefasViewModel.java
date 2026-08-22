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
    private final MutableLiveData<Boolean> tarefaAdicionada = new MutableLiveData<>(false);

    public TarefasViewModel(@NonNull TarefaRepository repository) {
        this.repository = repository;
        //Utilizamos o Transformations.switchMap para que ele observe as alterações do título buscado, depois fazemos uma condição para mostrar o título que foi buscado ou todos os títulos se o input estiver vazio
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

    public LiveData<Boolean> getTarefaAdicionada(){
        return tarefaAdicionada;
    }

    //Quando a tarefa for consumida, execute esse método
    public void tarefaAdicionada(){
        tarefaAdicionada.setValue(false);
    }

    public void alternarConcluida(Tarefa tarefa) {
        new Thread(() -> {
            Tarefa atualizada = new Tarefa(tarefa.getTitulo());
            atualizada.setId(tarefa.getId());
            atualizada.setConcluida(!tarefa.isConcluida());

            repository.atualizar(atualizada);
        }).start();
    }

    //Esse metodo é para definir o título que vamos buscar
    public void setTituloBusca(String titulo){
        tituloBusca.setValue(titulo);
    }

    public void remover(Tarefa tarefa) {
        new Thread(() -> repository.remover(tarefa)).start();
    }

    public void adicionar(String titulo) {
        new Thread(() -> {
            repository.inserir(new Tarefa(titulo));
            tarefaAdicionada.postValue(true);
        }).start();
    }
}
