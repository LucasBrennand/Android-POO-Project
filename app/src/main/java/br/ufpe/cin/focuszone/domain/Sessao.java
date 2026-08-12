package br.ufpe.cin.focuszone.domain;

import java.time.Instant;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sessoes")
public class Sessao {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private final String tarefaTitulo;
    private final int duracaoMinutos;
    private final Instant iniciadaEm;
    private final boolean concluida;

    public Sessao(String tarefaTitulo, int duracaoMinutos, Instant iniciadaEm, boolean concluida) {
        this.tarefaTitulo = tarefaTitulo;
        this.duracaoMinutos = duracaoMinutos;
        this.iniciadaEm = iniciadaEm;
        this.concluida = concluida;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTarefaTitulo() {
        return tarefaTitulo;
    }

    public int getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public Instant getIniciadaEm() {
        return iniciadaEm;
    }

    public boolean isConcluida() {
        return concluida;
    }
}
