package io.github.fernandosilva94.cadastro.service;

import io.github.fernandosilva94.cadastro.dto.UsuarioDTO;
import io.github.fernandosilva94.cadastro.model.Administrador;
import io.github.fernandosilva94.cadastro.model.Usuario;
import io.github.fernandosilva94.cadastro.model.enums.NivelAcesso;
import io.github.fernandosilva94.cadastro.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class UsuarioService {
    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario salvar(UsuarioDTO usuarioDTO) {
        if (!usuarioRepository.existsByEmail(usuarioDTO.getEmail()) && !usuarioRepository.existsByDocumento(usuarioDTO.getDocumento())) {
            Usuario usuario;

            if (usuarioDTO.getNivelAcesso() != null) {
                usuario = this.isAdmin(usuarioDTO);
                } else {
                usuario = new Usuario();
            }
            usuario.setNome(usuarioDTO.getNome());
            usuario.setEmail(usuarioDTO.getEmail());
            usuario.setSenha(usuarioDTO.getSenha());
            usuario.setDocumento(usuarioDTO.getDocumento());
            usuario.setStatus('A');
            usuario.setDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));

            Usuario usuarioSalvo = usuarioRepository.save(usuario);
            if (usuarioSalvo!= null) {
                return usuarioSalvo;
            } else {
                throw new Error("Erro ao cadastrar usuario");
            }
        } else {
            throw new Error("Usuário já cadastrado.");
        }
    }

    @Transactional
    public Usuario editar(Long id, UsuarioDTO usuarioDTO) {

        Usuario usuarioEditado = this.getUsuarioById(id);

        usuarioRepository.updateStatus(id, 'I');

        if (usuarioDTO.getNivelAcesso() != null) {
            usuarioEditado = this.isAdmin(usuarioDTO);
        }

        usuarioEditado.setNome(usuarioDTO.getNome());
        usuarioEditado.setEmail(usuarioDTO.getEmail());
        usuarioEditado.setSenha(usuarioDTO.getSenha());
        usuarioEditado.setDocumento(usuarioDTO.getDocumento());
        usuarioEditado.setStatus('A');
        usuarioEditado.setDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));

        return usuarioRepository.save(usuarioEditado);
    }

    public Usuario getUsuarioById(Long id) {
        Usuario usuarioSelecionado = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return usuarioSelecionado;
    }

    public Usuario isAdmin(UsuarioDTO usuarioDTO) {
        Usuario usuario;
        Administrador admin = new Administrador();
        if (usuarioDTO.getNivelAcesso().equals(NivelAcesso.GERENTE.toString()) || usuarioDTO.getNivelAcesso().equals(NivelAcesso.DIRETOR.toString())) {
            admin.setNivelAcesso(usuarioDTO.getNivelAcesso());
            usuario = admin;
        } else {
            throw new Error("Nivel de acesso não encontrado");
        }
        return usuario;
    }

    public Usuario inativarUsuario(Long id) {
         usuarioRepository.updateStatus(id, 'I');
         return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado após inativação"));
    }
}
