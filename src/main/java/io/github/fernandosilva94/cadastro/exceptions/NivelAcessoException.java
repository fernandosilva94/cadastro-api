package io.github.fernandosilva94.cadastro.exceptions;

public class NivelAcessoException extends RuntimeException {
    public NivelAcessoException(String message) {
        super(message);
    }
    public NivelAcessoException(String message, Throwable cause) {
        super(message, cause);
    }
}
