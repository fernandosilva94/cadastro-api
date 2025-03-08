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
            Usuario usuario = validarNovoUsuario(usuarioDTO);
            preencherDadosUsuario(usuario, usuarioDTO);

            Usuario usuarioSalvo = usuarioRepository.save(usuario);
            if (usuarioSalvo!= null) {
                return usuarioSalvo;
            } else {
                throw new Error("Erro ao cadastrar usuario");
            }
    }

    @Transactional
    public Usuario editar(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuarioEditado = getUsuarioById(id);

        if (usuarioDTO.getNivelAcesso() != null) {
            usuarioRepository.updateStatus(id, 'I');
            usuarioEditado = isAdmin(usuarioDTO);
        }
        preencherDadosUsuario(usuarioEditado, usuarioDTO);

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

    private Usuario validarNovoUsuario (UsuarioDTO usuarioDTO) {
            Usuario usuario;
        if (!usuarioRepository.existsByEmail(usuarioDTO.getEmail()) && !usuarioRepository.existsByDocumento(usuarioDTO.getDocumento())) {
            if (usuarioDTO.getNivelAcesso() != null) {
                usuario = this.isAdmin(usuarioDTO);
            } else {
                usuario = new Usuario();
            }
        }  else {
            throw new Error("Usuário já cadastrado.");
        }
        return usuario;
    }

    private void preencherDadosUsuario(Usuario usuario, UsuarioDTO usuarioDTO) {
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(usuarioDTO.getSenha());
        usuario.setDocumento(usuarioDTO.getDocumento());
        usuario.setStatus('A');
        usuario.setDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
    }

    public Usuario inativarUsuario(Long id) {
         usuarioRepository.updateStatus(id, 'I');
         return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado após inativação"));
    }
}
