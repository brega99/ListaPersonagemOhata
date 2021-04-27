package com.example.listaohata.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listaohata.dao.PersonagemDao;
import com.example.listaohata.model.Personagem;
import com.example.listaohata.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import static com.example.listaohata.ui.activities.ConstantesActivities.CHAVE_PERSONAGEM;
import static com.example.listaohata.ui.activities.ConstantesActivities.TITULO_APPBAR_EDITA_PERSONAGEM;
import static com.example.listaohata.ui.activities.ConstantesActivities.TITULO_APPBAR_NOVO_PERSONAGEM;

public class FormularioPersonagemActivity extends AppCompatActivity {

//instanciando informações
    private EditText campoNome;
    private EditText campoNacimento;
    private EditText campoAltura;

    private String nome;
    private String nascimento;
    private String altura;

    private final PersonagemDao dao = new PersonagemDao();
    private Personagem personagen;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_formulario_personagem_menu_salvar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.activity_formulario_personagem_menu_salvar){
            finalizarFormulario();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_personagem);
        inicializacaoCampos();
        configuraBotaoSalvar();
        carregaPersonagen();

    }
//carrega dados dos personagens/itens
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
// Disponibiliza o preenchimento dos campos com a informação escolhida
    private void preencheCampos(Personagem personagen) {
        campoNome.setText(personagen.getNome());
        campoNacimento.setText(personagen.getNacimento());
        campoAltura.setText(personagen.getAltura());
    }
//Save das informações
    private void configuraBotaoSalvar() {
        // Pega o botão que salva as informções no personagem
        Button btSalvar = findViewById(R.id.bt_salvar);
        btSalvar.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            finalizarFormulario();
                                        }
                                    }
        );
    }
// variável de edição e salvamento das informções do personagem
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
// Permite o preenchimento das informações das personagens/itens
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

        SimpleMaskFormatter smfAltura = new SimpleMaskFormatter("N,NN");//criando objeto
        MaskTextWatcher mtwAltura = new MaskTextWatcher(campoAltura, smfAltura);
        campoAltura.addTextChangedListener(mtwAltura);//adiciona texto

        SimpleMaskFormatter smfNascimento = new SimpleMaskFormatter("NN,NN,NNNN");//criando objeto
        MaskTextWatcher mtwNascimento = new MaskTextWatcher(campoNacimento, smfNascimento);
        campoNacimento.addTextChangedListener(mtwNascimento);//adiciona texto

    }
}