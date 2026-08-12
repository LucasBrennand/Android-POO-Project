package br.ufpe.cin.focuszone.ui.configuracao;

import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import br.ufpe.cin.focuszone.R;
import br.ufpe.cin.focuszone.data.repository.ConfiguracaoRepository;

public class ConfiguracaoActivity extends AppCompatActivity {

    private ConfiguracaoViewModel viewModel;
    private SeekBar duracaoFocoSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);

        ConfiguracaoRepository repository = new ConfiguracaoRepository(this);
        viewModel = new ViewModelProvider(this, new ConfiguracaoViewModelFactory(repository))
                .get(ConfiguracaoViewModel.class);

        duracaoFocoSeekBar = findViewById(R.id.duracaoFocoSeekBar);
        viewModel.getDuracaoFocoMinutos().observe(this, duracaoFocoSeekBar::setProgress);

        Button salvarButton = findViewById(R.id.salvarConfiguracaoButton);
        salvarButton.setOnClickListener(v -> {
            viewModel.salvarDuracao(duracaoFocoSeekBar.getProgress());
            finish();
        });
    }
}
