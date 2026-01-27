package com.mvalentin.usuario.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDto {

    private String nome;
    private String email;
    // Impede que a senha seja exposta em respostas JSON (Serialização),
    // mas permite que seja recebida em requisições de cadastro (Desserialização).
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senha;
    private List<TelefoneDto> telefones;
    private List<EnderecoDto> enderecos;
}

