package io.github.fernandosilva94.cadastro.service;

import io.github.fernandosilva94.cadastro.dto.UsuarioDTO;
import io.github.fernandosilva94.cadastro.exceptions.*;
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
        try {
            Usuario usuario = validarNovoUsuario(usuarioDTO);
            preencherDadosUsuario(usuario, usuarioDTO);

            Usuario usuarioSalvo = usuarioRepository.save(usuario);
            if (usuarioSalvo!= null) {
                return usuarioSalvo;
            } else {
                throw new UsuarioCadastroException("Erro ao cadastrar usuario");
            }
        } catch (Exception e) {
            throw new UsuarioCadastroException("Erro ao salvar usuario no banco de dados", e);
        }
    }

    @Transactional
    public Usuario editar(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuarioEditado;
        try {
            usuarioEditado = getUsuarioById(id);
            if (usuarioDTO.getNivelAcesso() != null) {
                usuarioRepository.updateStatus(id, 'I');
                usuarioEditado = isAdmin(usuarioDTO);
            }
            preencherDadosUsuario(usuarioEditado, usuarioDTO);
        } catch (Exception e) {
            throw new UsuarioEditadoException("Erro ao editar usuário.", e);
        }
        return usuarioRepository.save(usuarioEditado);
    }

    public Usuario getUsuarioById(Long id) {
        Usuario usuarioSelecionado;
        try {
            usuarioSelecionado = usuarioRepository.findById(id)
                    .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));
            } catch (Exception e) {
                throw new UsuarioNaoEncontradoException("Erro ao buscar usuario", e);
            }
        return usuarioSelecionado;
    }

    public Usuario inativarUsuario(Long id) {
        Usuario inativarUsuario = getUsuarioById(id);
        try {
            usuarioRepository.updateStatus(inativarUsuario.getId(), 'I');
        } catch (Exception e) {
            throw new InativarUsuarioException("Erro ao inativar usuario", e);
        }
         return inativarUsuario;
    }

    public Usuario isAdmin(UsuarioDTO usuarioDTO) {
        Usuario usuario;
        Administrador admin = new Administrador();
        try {
            if (usuarioDTO.getNivelAcesso().equals(NivelAcesso.GERENTE.toString()) || usuarioDTO.getNivelAcesso().equals(NivelAcesso.DIRETOR.toString())) {
                admin.setNivelAcesso(usuarioDTO.getNivelAcesso());
                usuario = admin;
            } else {
                throw new NivelAcessoException("Nivel de acesso não encontrado");
            }
        } catch (Exception e) {
            throw new NivelAcessoException("Erro ao cadastrar administrador.", e);
        }
        return usuario;
    }

    private Usuario validarNovoUsuario (UsuarioDTO usuarioDTO) {
        Usuario usuario;
        try {
            if (!usuarioRepository.existsByEmail(usuarioDTO.getEmail()) && !usuarioRepository.existsByDocumento(usuarioDTO.getDocumento())) {
                if (usuarioDTO.getNivelAcesso() != null) {
                    usuario = this.isAdmin(usuarioDTO);
                } else {
                    usuario = new Usuario();
                }
            }  else {
                throw new UsuarioJaCadastradoException("Usuário já cadastrado.");
            }
        } catch (Exception e) {
            throw new UsuarioCadastroException("Erro ao validar dados do usuário.", e);
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

}
