package io.github.fernandosilva94.cadastro.exceptions;

public class UsuarioCadastroException extends RuntimeException {

    public UsuarioCadastroException(String message) {
        super(message);
    }

    public UsuarioCadastroException(String message, Throwable cause) {
        super(message, cause);
    }
}
