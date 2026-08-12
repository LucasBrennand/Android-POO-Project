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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import br.ufpe.cin.focuszone.R;
import br.ufpe.cin.focuszone.ui.configuracao.ConfiguracaoActivity;
import br.ufpe.cin.focuszone.ui.historico.HistoricoActivity;
import br.ufpe.cin.focuszone.ui.tarefas.TarefasActivity;

public class TimerActivity extends AppCompatActivity {

    private TimerViewModel viewModel;

    private TextView tempoDisplay;
    private EditText nomeTarefaInput;
    private Button iniciarButton;
    private Button cancelarButton;

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
            iniciarButton.setEnabled(!andamento);
            cancelarButton.setEnabled(andamento);
        });

        iniciarButton.setOnClickListener(v -> {
            String nome = nomeTarefaInput.getText().toString().trim();
            if (nome.isEmpty()) {
                Snackbar.make(v, R.string.msg_informe_tarefa, Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(v, getString(R.string.msg_iniciando_foco, nome), Snackbar.LENGTH_LONG).show();
                viewModel.iniciarContagem(nome);
            }
        });

        cancelarButton.setOnClickListener(v -> viewModel.cancelarContagem());

        configuracaoButton.setOnClickListener(v ->
                startActivity(new Intent(this, ConfiguracaoActivity.class)));

        historicoButton.setOnClickListener(v ->
                startActivity(new Intent(this, HistoricoActivity.class)));

        tarefasButton.setOnClickListener(v ->
                startActivity(new Intent(this, TarefasActivity.class)));
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
