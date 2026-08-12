package br.ufpe.cin.focuszone.ui.historico;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.ufpe.cin.focuszone.R;
import br.ufpe.cin.focuszone.domain.Sessao;

public class SessaoAdapter extends RecyclerView.Adapter<SessaoViewHolder> {

    private final List<Sessao> sessoes;

    public SessaoAdapter(List<Sessao> sessoes) {
        this.sessoes = sessoes;
    }

    @NonNull
    @Override
    public SessaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sessao, parent, false);
        return new SessaoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SessaoViewHolder holder, int position) {
        holder.bind(sessoes.get(position));
    }

    @Override
    public int getItemCount() {
        return sessoes.size();
    }
}
