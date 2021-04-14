package com.example.listaohata.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.listaohata.dao.PersonagemDao;
import com.example.listaohata.model.Personagem;
import com.example.listaohata.R;

import static com.example.listaohata.ui.activities.ConstantesActivities.CHAVE_PERSONAGEM;
import static com.example.listaohata.ui.activities.ConstantesActivities.TITULO_APPBAR_EDITA_PERSONAGEM;
import static com.example.listaohata.ui.activities.ConstantesActivities.TITULO_APPBAR_NOVO_PERSONAGEM;

public class FormularioPersonagemActivity extends AppCompatActivity {


    private EditText campoNome;
    private EditText campoNacimento;
    private EditText campoAltura;

    private String nome;
    private String nascimento;
    private String altura;

    private final PersonagemDao dao = new PersonagemDao();
    private Personagem personagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_personagem);
        inicializacaoCampos();
        configuraBotaoSalvar();
        carregaPersonagen();

    }

    private void carregaPersonagen() {
        Intent dados = getIntent();
        if (dados.hasExtra(CHAVE_PERSONAGEM)) {
            setTitle(TITULO_APPBAR_EDITA_PERSONAGEM);
            personagen = (Personagem) dados.getSerializableExtra(CHAVE_PERSONAGEM);
            preencheCampos(personagen);
        } else {
            setTitle(TITULO_APPBAR_NOVO_PERSONAGEM);
            personagen = new Personagem();
        }
    }

    private void preencheCampos(Personagem personagen) {
        campoNome.setText(personagen.getNome());
        campoNacimento.setText(personagen.getNacimento());
        campoAltura.setText(personagen.getAltura());
    }

    private void configuraBotaoSalvar() {
        // Pegando  o botao de salvar, para por um listener de ações nele, no caso, salvar  informaçõa do personagem.
        Button btSalvar = findViewById(R.id.bt_salvar);
        btSalvar.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            finalizarFormulario();
                                        }
                                    }
        );
    }

    private void finalizarFormulario() {
        prencherPersonagen();
        if (personagen.IdValido()) {
            dao.editar(personagen);
            finish();
        } else {
            dao.salva(personagen);
        }
        finish();
    }

    private void prencherPersonagen() {


        nome = campoNome.getText().toString();
        nascimento = campoNacimento.getText().toString();
        altura = campoAltura.getText().toString();

        personagen.setNome(nome);
        personagen.setNacimento(nascimento);
        personagen.setAltura(altura);
    }

    private void inicializacaoCampos() {//Pegando os  ids referentes aos dados do personagem
        campoNome = findViewById(R.id.editTextText_Name);
        campoNacimento = findViewById(R.id.editText_Nascimento);
        campoAltura = findViewById(R.id.editText_Altura);
    }
}