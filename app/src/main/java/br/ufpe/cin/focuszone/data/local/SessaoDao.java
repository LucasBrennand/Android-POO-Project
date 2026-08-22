package br.ufpe.cin.focuszone.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.time.Instant;
import java.util.List;

import br.ufpe.cin.focuszone.domain.Sessao;

@Dao
public interface SessaoDao {
    //Essa variável vai contar o total de sessões da semana até hoje
    @Query("SELECT COUNT(*) FROM sessoes WHERE iniciadaEm >= :inicioDaSemana")
    int countSessoesSemana(Instant inicioDaSemana);

    @Insert
    void inserir(Sessao sessao);

    @Query("SELECT * FROM sessoes ORDER BY iniciadaEm DESC")
    List<Sessao> listarTodas();

    @Query("SELECT * FROM sessoes WHERE iniciadaEm >= :inicioDoDia ORDER BY iniciadaEm DESC")
    List<Sessao> listarDeHoje(Instant inicioDoDia);

    //Vai ser usado para remover as tarefas do histórico
    //Vamos remover a sessão através do seu ID
    @Query("DELETE FROM sessoes WHERE id = :id")
    void remover(long id);

    // Filtra o historico baseado nos últimos 7 dias
    // :inicioDaSemana pega todos valores desde esse dia até hoje
    @Query("SELECT * FROM sessoes WHERE iniciadaEm >= :inicioDaSemana ORDER BY iniciadaEm DESC")
    List<Sessao> listaDaSemana(Instant inicioDaSemana);

}
