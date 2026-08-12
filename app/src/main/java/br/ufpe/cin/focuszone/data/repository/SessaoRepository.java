package br.ufpe.cin.focuszone.data.repository;

import android.content.Context;

import java.time.Instant;
import java.util.List;

import androidx.annotation.WorkerThread;

import br.ufpe.cin.focuszone.data.local.FocusZoneDatabase;
import br.ufpe.cin.focuszone.data.local.SessaoDao;
import br.ufpe.cin.focuszone.domain.Sessao;

public class SessaoRepository {

    private final SessaoDao dao;

    public SessaoRepository(Context context) {
        dao = FocusZoneDatabase.getInstance(context).sessaoDao();
    }

    @WorkerThread
    public List<Sessao> listarTodas() {
        return dao.listarTodas();
    }

    @WorkerThread
    public List<Sessao> listarDeHoje(Instant inicioDoDia) {
        return dao.listarDeHoje(inicioDoDia);
    }

    @WorkerThread
    public void inserir(Sessao sessao) {
        dao.inserir(sessao);
    }
}
