package io.github.fernandosilva94.cadastro.exceptions;

public class UsuarioJaCadastradoException extends RuntimeException {

    public UsuarioJaCadastradoException(String message) {
        super(message);
    }
    public UsuarioJaCadastradoException(String message, Throwable cause) {
        super(message, cause);
    }
}
