package br.ufpe.cin.focuszone.ui.historico;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import br.ufpe.cin.focuszone.R;
import br.ufpe.cin.focuszone.domain.Sessao;

public class SessaoViewHolder extends RecyclerView.ViewHolder {
    private final TextView duracaoSessaoText;
    private final TextView tituloSessaoText;
    private final TextView dataSessaoText;

    //Adicionamos um DateFormate para exibir a data e hora que foi criada a tarefa
    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());

    //Adicionado as informações da tarefa para o construtor para serem inicializados
    public SessaoViewHolder(@NonNull View itemView) {
        super(itemView);
        this.duracaoSessaoText = itemView.findViewById(R.id.duracaoSessaoText);
        this.tituloSessaoText = itemView.findViewById(R.id.tituloSessaoText);
        this.dataSessaoText = itemView.findViewById(R.id.dataSessaoText);
    }

    void bind(Sessao sessao) {
        tituloSessaoText.setText(sessao.getTarefaTitulo());
        duracaoSessaoText.setText(sessao.getDuracaoMinutos() + "minutos");
        //Se existir a tarefa, pegue a suas informaçoes
        if (sessao.getIniciadaEm() != null){
            Date dataInicio = Date.from(sessao.getIniciadaEm());
            String dataString = dateFormat.format(dataInicio);
            dataSessaoText.setText(dataString);
        }
        else{
            dataSessaoText.setText("");
        }
    }
}
