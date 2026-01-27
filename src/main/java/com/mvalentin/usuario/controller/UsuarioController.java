package com.mvalentin.usuario.controller;

import com.mvalentin.usuario.business.UsuarioService;
import com.mvalentin.usuario.business.dto.UsuarioDto;
import com.mvalentin.usuario.infrastructure.entity.Usuario;
import com.mvalentin.usuario.infrastructure.repository.UsuarioRepository;
import com.mvalentin.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<UsuarioDto> salvaUsuario(@RequestBody UsuarioDto usuarioDto){
        return ResponseEntity.ok(usuarioService.salvaUsuario(usuarioDto));
    }

    // Criação  do método  de login

    @PostMapping("/login")
    public String login(@RequestBody UsuarioDto usuarioDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDto.getEmail(),
                        usuarioDto.getSenha())
        );
        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }


    @GetMapping
    public ResponseEntity<UsuarioDto> buscaUsuario(@RequestParam("email") String email){
        UsuarioDto dto = usuarioService.buscaUsuarioByEmail(email);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deletaUsuarToEmail(@PathVariable String email){
       usuarioService.deletaUsuarioByEmail(email);
       return ResponseEntity.noContent().build(); // Retorna 204 - Indica que a requisição foi processada com sucesso.
    }
}
