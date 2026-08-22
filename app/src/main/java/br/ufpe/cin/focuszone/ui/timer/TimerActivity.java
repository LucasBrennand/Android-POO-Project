package br.ufpe.cin.focuszone.ui.timer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import br.ufpe.cin.focuszone.R;
import br.ufpe.cin.focuszone.service.TimerStateHolder;
import br.ufpe.cin.focuszone.ui.configuracao.ConfiguracaoActivity;
import br.ufpe.cin.focuszone.ui.historico.HistoricoActivity;
import br.ufpe.cin.focuszone.ui.tarefas.TarefasActivity;

public class TimerActivity extends AppCompatActivity {

    private TimerViewModel viewModel;

    private TextView tempoDisplay;
    private EditText nomeTarefaInput;
    private Button iniciarButton;
    private Button cancelarButton;
    private boolean andamento;
    private long tempoRestanteAtual;

    private ActivityResultLauncher<String> permissaoNotificacaoLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(TimerViewModel.class);

        tempoDisplay = findViewById(R.id.tempoDisplay);
        nomeTarefaInput = findViewById(R.id.nomeTarefaInput);
        iniciarButton = findViewById(R.id.iniciarButton);
        cancelarButton = findViewById(R.id.cancelarButton);
        Button configuracaoButton = findViewById(R.id.configuracaoButton);
        Button historicoButton = findViewById(R.id.historicoButton);
        Button tarefasButton = findViewById(R.id.tarefasButton);

        permissaoNotificacaoLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                concedida -> { }
        );
        pedirPermissaoNotificacaoSeNecessario();

        viewModel.getTempoRestanteMillis().observe(this, this::atualizarTempoDisplay);

        viewModel.getEmAndamento().observe(this, andamento -> {
            this.andamento = andamento;
            cancelarButton.setEnabled(andamento);
            if(andamento){
                iniciarButton.setText(R.string.acao_pausar);
            }
            else{
                if (tempoRestanteAtual > 0 && tempoRestanteAtual < viewModel.getDuracaoTotalMillis()) {
                    iniciarButton.setText(R.string.acao_retomar);
                } else {
                    iniciarButton.setText(R.string.btn_iniciar);
                }
            }
            this.andamento = andamento;
        });

        //Isso vai ser usado para armazenar a tarefa
        viewModel.getNomeTarefa().observe(this, nome -> {
            if (nome != null && !nome.isEmpty()){
                nomeTarefaInput.setText(nome);
            }
        });

        //Inverteu a ordem para checar primeiro se está em andamento
        iniciarButton.setOnClickListener(v -> {
            if (andamento) {
                // Se já estiver rodando, pausa
                viewModel.pausarContagem();
            } else {
                // Se está parado ou pausado, valida o nome antes de iniciar ou retomar
                String nome = nomeTarefaInput.getText().toString().trim();
                if (nome.isEmpty()) {
                    Snackbar.make(v, R.string.msg_informe_tarefa, Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(v, getString(R.string.msg_iniciando_foco, nome), Snackbar.LENGTH_LONG).show();
                    viewModel.iniciarContagem(nome);
                }
            }
        });

        //Alert para confirmar e cancelar o timer
        cancelarButton.setOnClickListener(v -> {
//            viewModel.cancelarContagem();
            new AlertDialog.Builder(TimerActivity.this)
                    .setTitle(R.string.btn_cancelar_foco)
                    .setPositiveButton(R.string.btn_confirmar, (dialog, which) -> {
                        viewModel.cancelarContagem();
                    })
                    .setNegativeButton(R.string.btn_cancelar, (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .setOnCancelListener(dialog -> {
                        dialog.dismiss();
                    }).show();
        });

        configuracaoButton.setOnClickListener(v ->
                startActivity(new Intent(this, ConfiguracaoActivity.class)));

        historicoButton.setOnClickListener(v ->
                startActivity(new Intent(this, HistoricoActivity.class)));

        tarefasButton.setOnClickListener(v ->
                startActivity(new Intent(this, TarefasActivity.class)));

        //Atualiza o interface
        viewModel.getTipoCicloAtual().observe(this, tipoCiclo -> {
            if (tipoCiclo == TimerStateHolder.TIPO_PAUSA_CURTA) {
                nomeTarefaInput.setEnabled(false);
            } else {
                nomeTarefaInput.setEnabled(true);
            }
        });
        viewModel.getCicloAtual().observe(this, ciclo -> {
            int totalCiclos = new br.ufpe.cin.focuszone.data.repository.ConfiguracaoRepository(this).getTotalCiclos();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.sincronizarDuracaoConfigurada();
    }

    private void atualizarTempoDisplay(long millisRestantes) {
        long segundosTotais = millisRestantes / 1000;
        long minutos = segundosTotais / 60;
        long segundos = segundosTotais % 60;
        this.tempoRestanteAtual = millisRestantes; //salva o milisegundos faltando na variavel
        tempoDisplay.setText(String.format(Locale.getDefault(), "%02d:%02d", minutos, segundos));
    }

    private void pedirPermissaoNotificacaoSeNecessario() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
                && ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                        != PackageManager.PERMISSION_GRANTED) {
            permissaoNotificacaoLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
        }
    }
}
