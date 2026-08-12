package br.ufpe.cin.focuszone.domain;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tarefas")
public class Tarefa {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private final String titulo;
    private boolean concluida;

    public Tarefa(String titulo) {
        this.titulo = titulo;
        this.concluida = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }
}
