package br.ufpe.cin.focuszone.ui.tarefas;

import android.graphics.Paint;
import android.view.View;

import androidx.annotation.NonNull;

import br.ufpe.cin.focuszone.domain.Tarefa;

public class ConcluidaViewHolder extends TarefaViewHolder {

    public ConcluidaViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    void bind(Tarefa tarefa, TarefasAdapter.OnTarefaClickListener listener) {
        tituloText.setText(tarefa.getTitulo());
        tituloText.setPaintFlags(tituloText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        itemView.setOnClickListener(v -> listener.onTarefaClick(tarefa));
    }
}
