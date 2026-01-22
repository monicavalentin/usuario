package com.mvalentin.usuario.business.dto;

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
    private String senha;
    private List<TelefoneDto> telefones;
    private List<EnderecoDto> enderecos;
}

