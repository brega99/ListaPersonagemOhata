package com.example.listaohata.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listaohata.R;
import com.example.listaohata.dao.PersonagemDao;
import com.example.listaohata.model.Personagem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.example.listaohata.ui.activities.ConstantesActivities.CHAVE_PERSONAGEM;
import static com.example.listaohata.ui.activities.ConstantesActivities.TITULO_APPBAR_LISTA_PERSONAGENS;

public class ListaPersonagemActivity extends AppCompatActivity {

    private final PersonagemDao dao = new PersonagemDao();
    private ArrayAdapter<Personagem> adapter;

    @Override /*começo da criação*/
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);/*Iniciar toda a parte do android*/
        setContentView(R.layout.activity_lista_personagem);
        setTitle(TITULO_APPBAR_LISTA_PERSONAGENS);
        ConfiguraFabNovoPersonagem();
        configuraLista();
        new AlertDialog.Builder(this)
                .setTitle("Removendo Personagem")
                .setMessage("Tem certeza que deseja remover?")
                .setPositiveButton("Sim", null)
                .setNegativeButton("Não", null)
                .show();
    }

    private void ConfiguraFabNovoPersonagem() {
        FloatingActionButton BtNovoPersonagem = findViewById(R.id.fab_add);
        BtNovoPersonagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AbreFormulario();
            }
        });
    }

    private void AbreFormulario() {
        startActivity(new Intent(ListaPersonagemActivity.this, FormularioPersonagemActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();//Salvando para não dar back e apagar
        atualizaPersonagem();
    }

    private void atualizaPersonagem() {
        adapter.clear();
        adapter.addAll(dao.todos());
    }

    private void remove(Personagem personagem) {
        dao.remove(personagem);
        adapter.remove(personagem);
    }

    @Override//apertar botão abre janela
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.activity_lista_personagem_menu, menu);
    }

    @Override//permite selecionar item e dar retorno para o adapter
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return configuraMenu(item);
    }

    //Remover item da lista
    private boolean configuraMenu(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.activity_lista_personagem_menu_remover) {
            new AlertDialog.Builder(this)
                    .setTitle("Removendo Personagem")
                    .setMessage("Tem certeza que deseja remover?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                            Personagem personagemEscolhido = adapter.getItem(menuInfo.position);
                            remove(personagemEscolhido);
                        }
                    })
                    .setNegativeButton("Não", null)
                    .show();
        }
        return super.onContextItemSelected(item);
    }


    private void configuraLista() {
        ListView listadePersonagem = findViewById(R.id.activity_main_lista_personagem);
        listaDePersonagens(listadePersonagem);
        ConfiguraItenPorClique(listadePersonagem);
        registerForContextMenu(listadePersonagem);
    }

    private void ConfiguraItenPorClique(ListView listadePersonagem) {
        listadePersonagem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                     @Override
                                                     public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                                                         Personagem personagenEscolhido = (Personagem) adapterView.getItemAtPosition(posicao);

                                                         AbreFormularioEditar(personagenEscolhido);

                                                     }
                                                 }
        );
    }

    private void AbreFormularioEditar(Personagem personagenEscolhido) {
        Intent vaiParaOfotmulario = new Intent(ListaPersonagemActivity.this, FormularioPersonagemActivity.class);
        vaiParaOfotmulario.putExtra(CHAVE_PERSONAGEM, personagenEscolhido);
        startActivity(vaiParaOfotmulario);
    }

    private void listaDePersonagens(ListView listadePersonagem) {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listadePersonagem.setAdapter(adapter);
    }
}
