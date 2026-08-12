package br.ufpe.cin.focuszone.ui.tarefas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import br.ufpe.cin.focuszone.R;
import br.ufpe.cin.focuszone.domain.Tarefa;

public class TarefasAdapter extends ListAdapter<Tarefa, TarefaViewHolder> {

    public interface OnTarefaClickListener {
        void onTarefaClick(Tarefa tarefa);
    }

    private static final int VIEW_TYPE_PENDENTE = 0;
    private static final int VIEW_TYPE_CONCLUIDA = 1;

    private static final DiffUtil.ItemCallback<Tarefa> DIFF_CALLBACK = new DiffUtil.ItemCallback<Tarefa>() {
        @Override
        public boolean areItemsTheSame(@NonNull Tarefa oldItem, @NonNull Tarefa newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Tarefa oldItem, @NonNull Tarefa newItem) {
            return oldItem.getTitulo().equals(newItem.getTitulo())
                    && oldItem.isConcluida() == newItem.isConcluida();
        }
    };

    private final OnTarefaClickListener listener;

    public TarefasAdapter(OnTarefaClickListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).isConcluida() ? VIEW_TYPE_CONCLUIDA : VIEW_TYPE_PENDENTE;
    }

    @NonNull
    @Override
    public TarefaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tarefa, parent, false);
        if (viewType == VIEW_TYPE_CONCLUIDA) {
            return new ConcluidaViewHolder(itemView);
        }
        return new PendenteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TarefaViewHolder holder, int position) {
        holder.bind(getItem(position), listener);
    }
}
