package com.mvalentin.usuario.business;

import com.mvalentin.usuario.business.converter.UsuarioConverter;
import com.mvalentin.usuario.business.dto.UsuarioDto;
import com.mvalentin.usuario.infrastructure.entity.Usuario;
import com.mvalentin.usuario.infrastructure.exceptions.ConflictException;
import com.mvalentin.usuario.infrastructure.exceptions.ResourcesNotFoundException;
import com.mvalentin.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder; /*Cripitorgrafoar senha*/

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

    public Usuario buscaUsuarioByEmail(String email){
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourcesNotFoundException("Email não cadastrado" + email));
    }

    public void deletaUsuarioByEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }
}
