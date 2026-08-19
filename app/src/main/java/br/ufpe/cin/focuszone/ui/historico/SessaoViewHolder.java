package br.ufpe.cin.focuszone.ui.historico;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import br.ufpe.cin.focuszone.R;
import br.ufpe.cin.focuszone.domain.Sessao;

public class SessaoViewHolder extends RecyclerView.ViewHolder {

    private final TextView resumoText;
    private final TextView duracaoSessaoText;
    private final TextView tituloSessaoText;
    private final TextView dataSessaoText;

    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    DateFormat timeFormat = new SimpleDateFormat("HH:mm z", Locale.getDefault());
    //Adicionado as informaçoes da tarefa para o construtor para serem inicializados
    public SessaoViewHolder(@NonNull View itemView) {
        super(itemView);
        resumoText = (TextView) itemView;
        this.duracaoSessaoText = itemView.findViewById(R.id.duracaoSessaoText);
        this.tituloSessaoText = itemView.findViewById(R.id.tituloSessaoText);
        this.dataSessaoText = itemView.findViewById(R.id.dataSessaoText);
    }

    void bind(Sessao sessao) {
        String resumo = duracaoSessaoText  + tituloSessaoText + dataSessaoText + " — " + sessao.getDuracaoMinutos() + " min";
        resumoText.setText(resumo);
    }
}
