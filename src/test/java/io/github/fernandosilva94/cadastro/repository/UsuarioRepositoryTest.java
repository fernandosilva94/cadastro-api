package io.github.fernandosilva94.cadastro.repository;

import io.github.fernandosilva94.cadastro.model.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@SpringBootTest
public class UsuarioRepositoryTest {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Test
    public void salvarTest() {
        Usuario usuario = new Usuario();

        usuario.setNome("Nome teste");
        usuario.setEmail("email@teste.com");
        usuario.setSenha("123456");
        usuario.setDocumento("12345678919");
        usuario.setStatus('A');
        usuario.setDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        var usuarioSalvo = usuarioRepository.save(usuario);
        System.out.println(usuarioSalvo);
    }
}
