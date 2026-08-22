package br.ufpe.cin.focuszone.ui.configuracao;

import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import br.ufpe.cin.focuszone.R;
import br.ufpe.cin.focuszone.data.repository.ConfiguracaoRepository;

public class ConfiguracaoActivity extends AppCompatActivity {

    private ConfiguracaoViewModel viewModel;
    private SeekBar duracaoFocoSeekBar;
    private SeekBar duracaoPausaSeekBar;
    private Spinner ciclosSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);

        ConfiguracaoRepository repository = new ConfiguracaoRepository(this);
        viewModel = new ViewModelProvider(this, new ConfiguracaoViewModelFactory(repository))
                .get(ConfiguracaoViewModel.class);

        duracaoFocoSeekBar = findViewById(R.id.duracaoFocoSeekBar);
        duracaoPausaSeekBar = findViewById(R.id.duracaoPausaSeekBar);
        ciclosSpinner = findViewById(R.id.ciclosSpinner);

        viewModel.getDuracaoFocoMinutos().observe(this, duracaoFocoSeekBar::setProgress);
        viewModel.getDuracaoPausaMinutos().observe(this, duracaoPausaSeekBar::setProgress);

        viewModel.getTotalCiclos().observe(this, total -> {
            if (total != null && ciclosSpinner.getAdapter() != null) {
                int index = Math.max(0, Math.min(total - 1, ciclosSpinner.getAdapter().getCount() - 1));
                ciclosSpinner.setSelection(index);
            }
        });

        Button salvarButton = findViewById(R.id.salvarConfiguracaoButton);
        salvarButton.setOnClickListener(v -> {
            int foco = Math.max(1, duracaoFocoSeekBar.getProgress());
            int pausa = Math.max(1, duracaoPausaSeekBar.getProgress());
            int ciclos;
            try {
                String selecionado = ciclosSpinner.getSelectedItem().toString();
                ciclos = Integer.parseInt(selecionado);
            } catch (Exception e) {
                ciclos = ciclosSpinner.getSelectedItemPosition() + 1;
            }

            viewModel.salvarConfiguracoes(foco, pausa, ciclos);
            finish();
        });
    }
}