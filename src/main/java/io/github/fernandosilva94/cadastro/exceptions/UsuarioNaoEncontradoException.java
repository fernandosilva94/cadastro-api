package io.github.fernandosilva94.cadastro.exceptions;

public class UsuarioNaoEncontradoException extends RuntimeException {

    public UsuarioNaoEncontradoException(String message) {
        super(message);
    }
    public UsuarioNaoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }
}
