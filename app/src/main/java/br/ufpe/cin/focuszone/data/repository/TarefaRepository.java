package br.ufpe.cin.focuszone.data.repository;

import android.content.Context;

import java.util.List;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

import br.ufpe.cin.focuszone.data.local.FocusZoneDatabase;
import br.ufpe.cin.focuszone.data.local.TarefaDao;
import br.ufpe.cin.focuszone.domain.Tarefa;

public class TarefaRepository {

    private final TarefaDao dao;

    public TarefaRepository(Context context) {
        dao = FocusZoneDatabase.getInstance(context).tarefaDao();
    }

    public LiveData<List<Tarefa>> listarTodas() {
        return dao.listarTodas();
    }

    //Acionando o método do dao de filtrar
    public LiveData<List<Tarefa>> buscarPorTitulo(String titulo){
        return dao.buscarPorTitulo(titulo);
    }

    @WorkerThread
    public void inserir(Tarefa tarefa) {
        dao.inserir(tarefa);
    }

    @WorkerThread
    public void atualizar(Tarefa tarefa) {
        dao.atualizar(tarefa);
    }

    @WorkerThread
    public void remover(Tarefa tarefa) {
        dao.remover(tarefa);
    }

    @WorkerThread
    public int verificarTituloExiste(String titulo){
        return dao.verificarTituloExiste(titulo);
    }
}
