package br.ufpe.cin.focuszone.ui.tarefas;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.ufpe.cin.focuszone.domain.Tarefa;

public abstract class TarefaViewHolder extends RecyclerView.ViewHolder {

    protected final TextView tituloText;

    public TarefaViewHolder(@NonNull View itemView) {
        super(itemView);
        tituloText = (TextView) itemView;
    }

    abstract void bind(Tarefa tarefa, TarefasAdapter.OnTarefaClickListener listener);
}
