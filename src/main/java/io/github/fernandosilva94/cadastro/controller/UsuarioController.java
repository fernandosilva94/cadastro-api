package io.github.fernandosilva94.cadastro.controller;

import io.github.fernandosilva94.cadastro.dto.UsuarioDTO;
import io.github.fernandosilva94.cadastro.model.Usuario;
import io.github.fernandosilva94.cadastro.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuario")
public class UsuarioController {

    private UsuarioService usuarioService ;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/salvar")
    public ResponseEntity<Usuario> criarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        Usuario novoUsuario = usuarioService.salvar(usuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        Usuario getUsuario = usuarioService.getUsuarioById(id);
        return ResponseEntity.status(HttpStatus.OK).body(getUsuario);
    }
    @PutMapping("/editar/{id}")
    public ResponseEntity<Usuario> editarUsuario( @PathVariable Long id, @RequestBody @Valid UsuarioDTO usuarioDTO) {
        Usuario usuarioSelecionado = usuarioService.editar(id, usuarioDTO);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioSelecionado);
    }

}
