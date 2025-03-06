package io.github.fernandosilva94.cadastro.repository;

import io.github.fernandosilva94.cadastro.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario getByDocumento(String documento);
}
