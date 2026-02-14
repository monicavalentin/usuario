package com.mvalentin.usuario.controller;

import com.mvalentin.usuario.business.UsuarioService;
import com.mvalentin.usuario.business.dto.EnderecoDto;
import com.mvalentin.usuario.business.dto.TelefoneDto;
import com.mvalentin.usuario.business.dto.UsuarioDto;
import com.mvalentin.usuario.infrastructure.repository.EnderecoRepository;
import com.mvalentin.usuario.infrastructure.repository.TelefoneRepository;
import com.mvalentin.usuario.infrastructure.repository.UsuarioRepository;
import com.mvalentin.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;


    // Método simples com retorno de status code 200
   /* @PostMapping
    public ResponseEntity<UsuarioDto> salvaUsuario(@RequestBody UsuarioDto usuarioDto){
        return ResponseEntity.ok(usuarioService.salvaUsuario(usuarioDto));
    }*/

    // Método  com retorno de status code 201 created que é o correto para salvar usuário

    @PostMapping
    public ResponseEntity<UsuarioDto> salvaUsuario(@RequestBody UsuarioDto usuarioDto) {
        // 1. CHAMA O SERVICE: É aqui que a mágica (validação, senha, save) acontece
        UsuarioDto usuarioSalvo = usuarioService.salvaUsuario(usuarioDto);

        // 2. RETORNA A RESPOSTA: Com o objeto que o Service processou e salvou
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
    }
    @PostMapping("/endereco")
    public  ResponseEntity<EnderecoDto> cadastraEndereco(@RequestBody EnderecoDto enderecoDto,
                                                         @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.cadastraEndereco(token,enderecoDto));
    }

    @PostMapping("/telefone")
    public  ResponseEntity<TelefoneDto> cadastraTelefone(@RequestBody TelefoneDto telefoneDto,
                                                         @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.cadastraTelefone(token,telefoneDto));
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
    @PutMapping
    public ResponseEntity<UsuarioDto> atualizaDadoUsuario(@RequestBody UsuarioDto dto,
                                                          @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.atualizaDadosUsuario(token,dto));
    }

    @PutMapping("/endereco")
    public ResponseEntity<EnderecoDto> atualizaEndereco(@RequestBody EnderecoDto enderecoDto,
                                                        @RequestParam("id") Long id){
        return ResponseEntity.ok(usuarioService.atualizaDadosEndereco(id,enderecoDto));
    }

    @PutMapping("/telefone")
    public ResponseEntity<TelefoneDto> atualizaTelefone(@RequestBody TelefoneDto telefoneDto,
                                                        @RequestParam("id") Long id){
        return ResponseEntity.ok(usuarioService.atualizaDadosTelefone(id,telefoneDto));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deletaUsuarPorEmail(@PathVariable String email){
       usuarioService.deletaUsuarioByEmail(email);
       return ResponseEntity.noContent().build(); // Retorna 204 - Indica que a requisição foi processada com sucesso.
    }

}