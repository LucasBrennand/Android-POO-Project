package br.ufpe.cin.focuszone.ui.historico;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import br.ufpe.cin.focuszone.R;
import br.ufpe.cin.focuszone.data.repository.SessaoRepository;
import br.ufpe.cin.focuszone.domain.Sessao;

public class HistoricoActivity extends AppCompatActivity {

    private final List<Sessao> sessoes = new ArrayList<>();

    private SessaoAdapter adapter;
    private SessaoRepository sessaoRepository;
    private TextView totalTarefasSemana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        RecyclerView historicoRecyclerView = findViewById(R.id.historicoRecyclerView);
        historicoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        historicoRecyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        adapter = new SessaoAdapter(sessoes);
        historicoRecyclerView.setAdapter(adapter);

        totalTarefasSemana = findViewById(R.id.totalTarefasNaSemana);

        sessaoRepository = new SessaoRepository(this);
        carregarSessoes(1);
        carregarTotalSemana();

        ItemTouchHelper.SimpleCallback swipeCallback = new ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getBindingAdapterPosition();

                if (position == RecyclerView.NO_POSITION || position >= sessoes.size()) {
                    return;
                }

                Sessao sessaoParaRemover = sessoes.get(position);

                //Implementado uma confirmação antes de remover a sessão
                new AlertDialog.Builder(HistoricoActivity.this)
                        .setTitle(R.string.titulo_confirmar_exclusao)
                        .setPositiveButton(R.string.btn_cancelar, (dialog, which) -> {
                            // Confirmação: remove do banco de dados e atualiza a lista
                            new Thread(() -> {
                                sessaoRepository.remover(sessaoParaRemover);

                                runOnUiThread(() -> {
                                    sessoes.remove(position);
                                    adapter.notifyItemRemoved(position);
                                    carregarTotalSemana();
                                });
                            }).start();
                        })
                        .setNegativeButton(R.string.btn_cancelar, (dialog, which) -> {
                            // Cancelamento: restaura o item na tela
                            adapter.notifyItemChanged(position);
                        })
                        .setOnCancelListener(dialog -> {
                            // Cancelamento: restaura o item na tela
                            adapter.notifyItemChanged(position);
                        })
                        .show();
            }
        };
        new ItemTouchHelper(swipeCallback).attachToRecyclerView(historicoRecyclerView);

        Button hojeButton = findViewById(R.id.hojeButton);
        Button todasButton = findViewById(R.id.todasButton);
        Button semanaButton = findViewById(R.id.ultimaSemanaButton);
        // Cada botão tem um número int e o carregarSessoes vai executar baseada nesse número
        // 1 - Hoje
        // 2 - Ultima Semana
        // 3 - Todos
        hojeButton.setOnClickListener(v -> carregarSessoes(1));
        semanaButton.setOnClickListener(v -> carregarSessoes(2));
        todasButton.setOnClickListener(v -> carregarSessoes(3));

        Button compartilharButton = findViewById(R.id.compartilharButton);
        compartilharButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.msg_compartilhar_sessao, 25));
            startActivity(Intent.createChooser(intent, getString(R.string.titulo_compartilhar)));
        });
    }

    //Ajustado o parametro para int
    private void carregarSessoes(int op) {
        new Thread(() -> {
            List<Sessao> sessoesDoBanco;

            if (op == 1){
                sessoesDoBanco = sessaoRepository.listarDeHoje(inicioDoDia());
            }
            else if (op == 2){
                sessoesDoBanco = sessaoRepository.listaDaSemana(inicioDaSemana());
            }
            else if (op == 3) {
                sessoesDoBanco = sessaoRepository.listarTodas();
            } else {
                sessoesDoBanco = null;
            }
            runOnUiThread(() -> {
                sessoes.clear();
                assert sessoesDoBanco != null;
                sessoes.addAll(sessoesDoBanco);
                adapter.notifyDataSetChanged();
            });
        }).start();
    }

    private Instant inicioDoDia() {
        return LocalDate.now()
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant();
    }

    //Metodo para pegar ultimo 7 dias
    private Instant inicioDaSemana(){
        return LocalDate.now().minusDays(7).atStartOfDay(ZoneId.systemDefault()).toInstant();
    }

    //Metodo para exibir a quantidade de tarefas na semana
    private void carregarTotalSemana(){
        new Thread(() -> {
            int total = sessaoRepository.countSessoesSemana(inicioDaSemana());
            runOnUiThread(() -> {
                if (totalTarefasSemana != null){
                    totalTarefasSemana.setText("Total de Tarefas esta semana: "+total);
                }
            });
        }).start();
    }
}
