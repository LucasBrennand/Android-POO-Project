package br.ufpe.cin.focuszone.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.ufpe.cin.focuszone.domain.Tarefa;

@Dao
public interface TarefaDao {

    @Insert
    void inserir(Tarefa tarefa);

    @Update
    void atualizar(Tarefa tarefa);

    @Delete
    void remover(Tarefa tarefa);

    @Query("SELECT * FROM tarefas ORDER BY id ASC")
    LiveData<List<Tarefa>> listarTodas();

    //Metodo que filtra as tarefas com o texto inserido
    //SQLlite usada || inves de +
    //:titulo para encontrar em qualquer posição
    @Query("SELECT * FROM tarefas WHERE titulo LIKE '%' || :titulo || '%'")
    LiveData<List<Tarefa>> buscarPorTitulo(String titulo);
}
