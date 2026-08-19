package br.ufpe.cin.focuszone.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.time.Instant;
import java.util.List;

import br.ufpe.cin.focuszone.domain.Sessao;

@Dao
public interface SessaoDao {

    @Insert
    void inserir(Sessao sessao);

    @Query("SELECT * FROM sessoes ORDER BY iniciadaEm DESC")
    List<Sessao> listarTodas();

    @Query("SELECT * FROM sessoes WHERE iniciadaEm >= :inicioDoDia ORDER BY iniciadaEm DESC")
    List<Sessao> listarDeHoje(Instant inicioDoDia);

    // Filtra o historico baseado nos ultimos 7 dias
    // :inicioDaSemana pega todos valores desde esse dia até hoje
    @Query("SELECT * FROM sessoes WHERE iniciadaEm >= :inicioDaSemana ORDER BY iniciadaEm DESC")
    List<Sessao> listaDaSemana(Instant inicioDaSemana);
}
