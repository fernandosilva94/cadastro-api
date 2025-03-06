package io.github.fernandosilva94.cadastro.service;

import io.github.fernandosilva94.cadastro.dto.UsuarioDTO;
import io.github.fernandosilva94.cadastro.model.Usuario;
import io.github.fernandosilva94.cadastro.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario salvar(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(usuarioDTO.getSenha());
        usuario.setDocumento(usuarioDTO.getDocumento());
        usuario.setStatus('A');

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        if (usuarioSalvo!= null) {
            return usuarioSalvo;
        } else {
            throw new Error("Erro ao cadastrar usuario");
        }

        //return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario editar(Long id, UsuarioDTO usuarioDTO) {

        Usuario usuarioEditado = this.getUsuarioById(id);


        usuarioEditado.setNome(usuarioDTO.getNome());
        usuarioEditado.setEmail(usuarioDTO.getEmail());
        usuarioEditado.setSenha(usuarioDTO.getSenha());
        usuarioEditado.setDocumento(usuarioDTO.getDocumento());
        usuarioEditado.setStatus('A');

        return usuarioRepository.save(usuarioEditado);
    }

    public Usuario getUsuarioById(Long id) {
        Usuario usuarioSelecionado = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return usuarioSelecionado;
    }
}
