package com.mvalentin.usuario.business;

import com.mvalentin.usuario.infrastructure.clients.ViaCepClient;
import com.mvalentin.usuario.infrastructure.clients.ViaCepDto;
import com.mvalentin.usuario.infrastructure.exceptions.IllegalArgumentsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ViaCepService {

    private final ViaCepClient viaCepClient;

    public ViaCepDto buscarDadosEndereco(String cep){

        return viaCepClient.buscaDadosEndereco(processarCep(cep));
    }

    // método para remover os espaços "95010 10" (espaço) - seguindo doc viaCep
    private String processarCep (String cep){
        String cepFormatado = cep.replace(" ", "" ).
                replace("-", "");

        // Verifica se o cep informado possui apenas número e se é cep não tenha o  tamanho  exatamente igual a 8 lança execeção
        if(!cepFormatado.matches("\\d+") || !Objects.equals(cepFormatado.length(),8)){
            throw new IllegalArgumentsException("Cep com caracteres inválidos");

        }
        return cepFormatado;

    }
}