package com.mvalentin.usuario.business.converter;

import com.mvalentin.usuario.business.dto.EnderecoDto;
import com.mvalentin.usuario.business.dto.TelefoneDto;
import com.mvalentin.usuario.business.dto.UsuarioDto;
import com.mvalentin.usuario.infrastructure.entity.Endereco;
import com.mvalentin.usuario.infrastructure.entity.Telefone;
import com.mvalentin.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component  // esta classe é um componente a mais da aplicação, mas não tem regras de negócio
public class UsuarioConverter {

    // ##############################################################//
    // ### Seção 1: CONVERSÃO DE DTO PARA OBJETO DE DOMÍNIO (Entity)#//
    // #############################################################//


    public Usuario toUsuario(UsuarioDto usuarioDto) {
        return Usuario.builder()
                .nome(usuarioDto.getNome())
                .email(usuarioDto.getEmail())
                .senha(usuarioDto.getSenha())
                .enderecos(toListEndereco(usuarioDto.getEnderecos()))
                .telefones(toListTelefone(usuarioDto.getTelefones()))
                .build();
    }

    /**
     * * Converte uma lista de "enderecoDtos"  para uma lista "enderecoEntity".
     * Utiliza o fluxo de Streams para mapear cada item individualmente.
     */
    public List<Endereco> toListEndereco(List<EnderecoDto> enderecosDtos) {
        if (enderecosDtos == null) return Collections.emptyList(); //Se a lista for nula, retorne uma lista vazia
        return enderecosDtos.stream().map(this::toEndereco).toList();
    }

    /**
     * Converte um único objeto EnderecoDto para um objeto Endereco.
     * Usa o padrão Builder para criar o objeto de forma clara e segura.
     */
    public Endereco toEndereco(EnderecoDto enderecoDto) {
        return Endereco.builder()
                .rua(enderecoDto.getRua())
                .numero(enderecoDto.getNumero())
                .cidade(enderecoDto.getCidade())
                .complemento(enderecoDto.getComplemento())
                .cep(enderecoDto.getCep())
                .estado(enderecoDto.getEstado())
                .build();
    }

    /**
     * * Converte uma lista de "telefoneDtos"  para uma lista "telefoneEntity".
     * Utiliza o fluxo de Streams para mapear cada item individualmente.
     */

    public List<Telefone> toListTelefone(List<TelefoneDto> telefoneDtos) {
        if (telefoneDtos == null) return Collections.emptyList(); //Se a lista for nula, retorne uma lista vazia
        return telefoneDtos.stream().map(this::toTelefone).toList();
    }

    public Telefone toTelefone(TelefoneDto telefoneDto) {
        return Telefone.builder()
                .ddd(telefoneDto.getDdd())
                .numero(telefoneDto.getNumero())
                .build();
    }

    // #############################################################
    //         ### Seção 2: CONVERSÃO ENTITY PARA DTO ###
    // #############################################################

    public UsuarioDto toUsuarioDto(Usuario usuario) {
        return UsuarioDto.builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .enderecos(toListEnderecoDto(usuario.getEnderecos()))
                .telefones(toListTelefoneDto(usuario.getTelefones()))
                .build();

    }

    /**
     * Recebe uma lista de entidades endereço e transforma em uma lista de enderecoDto.
     */
    public List<EnderecoDto> toListEnderecoDto(List<Endereco> enderecos) {
        if (enderecos == null) return Collections.emptyList(); //Se a lista for nula, retorne uma lista vazia
        return enderecos.stream().map(this::toEnderecoDto).toList();
    }

    /**
     * Converte uma única entidade Endereco para EnderecoDto.
     */
    public EnderecoDto toEnderecoDto(Endereco endereco) {
        return EnderecoDto.builder()
                .id(endereco.getId())
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .cidade(endereco.getCidade())
                .complemento(endereco.getComplemento())
                .cep(endereco.getCep())
                .estado(endereco.getEstado())
                .build();
    }

    /**
     * Recebe uma lista de entidades Telefone e transforma em uma lista de TelefoneDTO.
     */

    public List<TelefoneDto> toListTelefoneDto(List<Telefone> telefones) {
        if (telefones == null) return Collections.emptyList(); //Se a lista for nula, retorne uma lista vazia
        return telefones.stream().map(this::toTelefoneDto).toList();
    }

    /**
     * Converte uma única entidade Telefone para TelefoneDTO.
     */
    public TelefoneDto toTelefoneDto(Telefone telefone) {
        return TelefoneDto.builder()
                .ddd(telefone.getDdd())
                .numero(telefone.getNumero())
                .build();
    }

    // ############################################################################
    //         ### Seção 3: Faz as comprações dos campos antes de atualiar     ###
    // ############################################################################

    public Usuario updateUsuario(UsuarioDto usuarioDto, Usuario usuario) {
        return Usuario.builder()
                .nome(usuarioDto.getNome() != null ? usuarioDto.getNome() : usuario.getNome())
                .id(usuario.getId())// como o Id dificilmente irá alterar então eu vou pegar direto da entity
                .senha(usuarioDto.getSenha() != null ? usuarioDto.getSenha() : usuario.getSenha())
                .email(usuarioDto.getEmail() != null ? usuarioDto.getEmail() : usuario.getEmail())
                .enderecos(usuario.getEnderecos()) // aqui vou pegar direto da entity pq não vamos alterar nesse método endereco e telefone
                .telefones(usuario.getTelefones()) // aqui vou pegar direto da entity pq não vamos alterar nesse método endereco e telefone
                .build();
    }

    public Endereco updateEndereco(EnderecoDto enderecoDto, Endereco endereco) {
        return Endereco.builder()
                .id(endereco.getId())
                .rua(enderecoDto.getRua() != null ? enderecoDto.getRua() : endereco.getRua())
                .numero(enderecoDto.getNumero() != null ? enderecoDto.getNumero() : endereco.getNumero())
                .cidade(enderecoDto.getCidade() != null ? enderecoDto.getCidade() : endereco.getCidade())
                .complemento(enderecoDto.getComplemento() != null ? enderecoDto.getComplemento() : endereco.getComplemento())
                .cep(enderecoDto.getCep() != null ? enderecoDto.getCep() : endereco.getCep())
                .estado(enderecoDto.getEstado() != null ? enderecoDto.getEstado() : endereco.getEstado())
                .build();
    }

    public Telefone updateTelefone(TelefoneDto telefoneDto, Telefone telefone) {
        return Telefone.builder()
                .id(telefone.getId())
                .ddd(telefoneDto.getDdd() != null ? telefoneDto.getDdd() : telefone.getDdd())
                .numero(telefoneDto.getNumero() != null ? telefoneDto.getNumero() : telefone.getNumero())
                .build();
    }

    // ##############################################################################
    //         ### Seção 4 -  Conversões de Endereço e Telefone de Dto para Entity  ###
    // ###############################################################################

    public Endereco toEnderecoEntity(EnderecoDto enderecoDto, Long idUsuario) {
        return Endereco.builder()
                .rua(enderecoDto.getRua())
                .numero(enderecoDto.getNumero())
                .cidade(enderecoDto.getCidade())
                .complemento(enderecoDto.getComplemento())
                .cep(enderecoDto.getCep())
                .estado(enderecoDto.getEstado())
                .usuario_id(idUsuario)
                .build();
    }

    public Telefone toTelefoneEntity(TelefoneDto telefoneDto, Long idUsuario ){
        return Telefone.builder()
                .ddd(telefoneDto.getDdd())
                .numero(telefoneDto.getNumero())
                .usuario_id(idUsuario)
                .build();
    }
}
