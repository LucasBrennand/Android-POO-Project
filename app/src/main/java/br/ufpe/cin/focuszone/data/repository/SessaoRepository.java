package br.ufpe.cin.focuszone.data.repository;

import android.content.Context;

import java.time.Instant;
import java.util.List;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

import br.ufpe.cin.focuszone.data.local.FocusZoneDatabase;
import br.ufpe.cin.focuszone.data.local.SessaoDao;
import br.ufpe.cin.focuszone.domain.Sessao;

public class SessaoRepository {

    private final SessaoDao dao;

    public SessaoRepository(Context context) {
        dao = FocusZoneDatabase.getInstance(context).sessaoDao();
    }


    //Todos os métodos criados no SessaoDao também vão ser implementados aqui
    @WorkerThread
    public List<Sessao> listarTodas() {
        return dao.listarTodas();
    }

    @WorkerThread
    public List<Sessao> listarDeHoje(Instant inicioDoDia) {
        return dao.listarDeHoje(inicioDoDia);
    }

    //Adicionado o método que vem do Dao
    @WorkerThread
    public List<Sessao> listaDaSemana(Instant inicioDaSemana){
        return dao.listaDaSemana(inicioDaSemana);
    }

    @WorkerThread
    public void inserir(Sessao sessao) {
        dao.inserir(sessao);
    }

    @WorkerThread
    public void remover(Sessao sessao){
        //Pegamos o ID de sessão para remover
        dao.remover(sessao.getId());
    }

    //Adicionado o método que vem do Dao
    @WorkerThread
    public int countSessoesSemana(Instant inicioDaSemana){
        return dao.countSessoesSemana(inicioDaSemana);
    }
}
