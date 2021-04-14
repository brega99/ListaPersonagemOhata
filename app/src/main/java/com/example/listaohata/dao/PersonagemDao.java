package com.example.listaohata.dao;

import com.example.listaohata.model.Personagem;

import java.util.ArrayList;
import java.util.List;

public class PersonagemDao {

    private final static List<Personagem> personagens = new ArrayList<>();
    private static int contadorDeId = 1;

    public void salva(Personagem personagemSalvo)//metodo para salvar dados do personagem
    {
        personagemSalvo.setId(contadorDeId);
        personagens.add(personagemSalvo);
        AtualizaId();
    }

    private void AtualizaId() {
        contadorDeId++;
    }

    public void editar(Personagem personagem)//metoro para poder editar dados do personagem
    {
        Personagem personagemEscolhido = BuscaPersonagenId(personagem);
        if (personagemEscolhido != null) {
            int posicaoPersonagem = personagens.indexOf(personagemEscolhido);
            personagens.set(posicaoPersonagem, personagem);
        }
    }

    private Personagem BuscaPersonagenId(Personagem personagem) {

        for (Personagem p : personagens) {
            if (p.getId() == personagem.getId()) {
                return p;
            }
        }
        return null;
    }

    public List<Personagem> todos() {
        return new ArrayList<>(personagens);
    }
}
