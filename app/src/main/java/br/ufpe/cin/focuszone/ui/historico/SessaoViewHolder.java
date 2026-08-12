package br.ufpe.cin.focuszone.ui.historico;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.ufpe.cin.focuszone.domain.Sessao;

public class SessaoViewHolder extends RecyclerView.ViewHolder {

    private final TextView resumoText;

    public SessaoViewHolder(@NonNull View itemView) {
        super(itemView);
        resumoText = (TextView) itemView;
    }

    void bind(Sessao sessao) {
        String resumo = sessao.getTarefaTitulo() + " — " + sessao.getDuracaoMinutos() + " min";
        resumoText.setText(resumo);
    }
}
