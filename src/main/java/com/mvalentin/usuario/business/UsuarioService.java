package com.mvalentin.usuario.business;

import com.mvalentin.usuario.business.converter.UsuarioConverter;
import com.mvalentin.usuario.business.dto.UsuarioDto;
import com.mvalentin.usuario.infrastructure.entity.Usuario;
import com.mvalentin.usuario.infrastructure.exceptions.ConflictException;
import com.mvalentin.usuario.infrastructure.exceptions.ResourcesNotFoundException;
import com.mvalentin.usuario.infrastructure.repository.UsuarioRepository;
import com.mvalentin.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder; /*Cripitorgrafoar senha*/
    private final JwtUtil jwtUtil;

    public UsuarioDto salvaUsuario(UsuarioDto usuarioDto){
        emailExiste(usuarioDto.getEmail());
        usuarioDto.setSenha(passwordEncoder.encode(usuarioDto.getSenha()));
        Usuario usuario = usuarioConverter.toUsuario(usuarioDto);
        usuario = usuarioRepository.save(usuario);
        return usuarioConverter.toUsuarioDto(usuario);
    }

    public void emailExiste(String email){
        try {
            boolean existe = verificaEmailExistente(email);
            if(existe){
                throw new ConflictException("E-mail já cadastrado" + email);
            }
        }catch (ConflictException e){
            throw new ConflictException("E-mail já cadastrado"+ e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public UsuarioDto buscaUsuarioByEmail(String email){
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourcesNotFoundException("Email não cadastrado" + email));
                return usuarioConverter.toUsuarioDto(usuario);
    }

    public void deletaUsuarioByEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDto atualizaDadosUsuario(String token, UsuarioDto dto){
        // busca do e-mail do usuário através do token( tirar a obrigatoriedade de passar o email)
        String email  = jwtUtil.extrairEmailToken(token.substring(7));
        // Busca os dados do usuarío no BD
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(()
                -> new ResourcesNotFoundException("E-mail não localizado"));

        // Criptografia de senha
        dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null);

        // Mescla os dados que recebemos na request Dto com os dados do BD)
        Usuario usuario  = usuarioConverter.updateUsuario(dto,usuarioEntity);

        // Salvau os dados do usuario convertido e  depois converte  para usuario Dto

        return usuarioConverter.toUsuarioDto(usuarioRepository.save(usuario));
    }
}