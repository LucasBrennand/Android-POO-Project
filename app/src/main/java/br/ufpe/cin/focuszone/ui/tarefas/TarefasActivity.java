package br.ufpe.cin.focuszone.ui.tarefas;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import br.ufpe.cin.focuszone.R;
import br.ufpe.cin.focuszone.data.repository.TarefaRepository;
import br.ufpe.cin.focuszone.domain.Tarefa;

public class TarefasActivity extends AppCompatActivity {

    private TarefasViewModel viewModel;
    private TarefasAdapter adapter;
    private EditText buscaTitulo;
    private TextView tarefasProgressoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefas);

        TarefaRepository repository = new TarefaRepository(this);
        viewModel = new ViewModelProvider(this, new TarefasViewModelFactory(repository))
                .get(TarefasViewModel.class);

        RecyclerView tarefasRecyclerView = findViewById(R.id.tarefasRecyclerView);
        tarefasRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tarefasRecyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        adapter = new TarefasAdapter(this::onTarefaClicada);
        tarefasRecyclerView.setAdapter(adapter);

        viewModel.getTarefas().observe(this, adapter::submitList);

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
                Tarefa tarefa = adapter.getCurrentList().get(position);
                viewModel.remover(tarefa);
            }
        };
        new ItemTouchHelper(swipeCallback).attachToRecyclerView(tarefasRecyclerView);

        MaterialButton adicionarTarefaButton = findViewById(R.id.adicionarTarefaButton);
        adicionarTarefaButton.setOnClickListener(v -> {
            EditText input = new EditText(this);
            new AlertDialog.Builder(this)
                    .setTitle(R.string.titulo_nova_tarefa)
                    .setView(input)
                    .setPositiveButton(R.string.btn_adicionar, (dialog, which) -> {
                        String titulo = input.getText().toString().trim();
                        if (titulo.isEmpty()) {
                            Snackbar.make(v, R.string.msg_titulo_vazio, Snackbar.LENGTH_SHORT).show();
                        } else {
                            //Quando for adicionar uma nova tarefa, uma busca vai ser feito para checar se esse titulo já existe
                            new Thread(() -> {
                                int qtd = repository.verificarTituloExiste(titulo);
                                runOnUiThread(() -> {
                                    if (qtd > 0){
                                        Snackbar.make(v, R.string.tarefa_ja_existe, Snackbar.LENGTH_SHORT).show();
                                    }
                                    else{
                                        viewModel.adicionar(titulo);
                                    }
                                });
                            }).start();
                        }
                    })
                    .setNegativeButton(R.string.btn_cancelar, null)
                    .show();
        });

        buscaTitulo = findViewById(R.id.editTextInput);
        //Adcionamos o TextWatcher para criar metodo onTextChanged que vai capturar as alterações no input
        buscaTitulo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                viewModel.setTituloBusca(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        tarefasProgressoText = findViewById(R.id.tarefas_progresso);
        viewModel.getTarefas().observe(this, listaDeTarefas -> {
            adapter.submitList(listaDeTarefas);

            if (listaDeTarefas != null){
                int total = listaDeTarefas.size();
                int concluidas = 0;

                //Cada vez que uma tarefa for concluida, atualiza o progresso
                for (int i = 0; i < listaDeTarefas.size(); i++) {
                    if (listaDeTarefas.get(i).isConcluida()){
                        concluidas++;
                    }
                }
                String progresso = concluidas + " de " + total + " tarefas concluídas";
                tarefasProgressoText.setText(progresso);
            }
        });
    }

    private void onTarefaClicada(Tarefa tarefa) {
        viewModel.alternarConcluida(tarefa);
    }
}
