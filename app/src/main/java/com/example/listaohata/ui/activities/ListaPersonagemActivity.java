package com.example.listaohata.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

    @Override /*começo da criação*/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);/*Iniciar toda a parte do android*/
        setContentView(R.layout.activity_lista_personagem);
        setTitle(TITULO_APPBAR_LISTA_PERSONAGENS);

        ConfiguraFabNovoPersonagem();
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
        super.onResume();//Salvando par não dar back e apagar


        ListView listadePersonagem = findViewById(R.id.activity_main_lista_personagem);
        final List<Personagem> personagens = dao.todos();
        listaDePersonagens(listadePersonagem, personagens);

        ConfiguraItenPorClique(listadePersonagem, personagens);
    }

    private void ConfiguraItenPorClique(ListView listadePersonagem, List<Personagem> personagens) {
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

    private void listaDePersonagens(ListView listadePersonagem, List<Personagem> personagens) {
        listadePersonagem.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, personagens));
    }
}
