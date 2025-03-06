package io.github.fernandosilva94.cadastro.model.enums;

public enum Status {
    ATIVO('A'),
    INATIVO('I');

    private final char valor;

    Status(char valor) {
        this.valor = valor;
    }
    public char getValor() {
        return valor;
    }
    public static Status fromValor(char valor) {
        for (Status status : Status.values()) {
            if (status.getValor() == valor) {
                return status;
            }
        }
        throw new IllegalArgumentException("Valor inválido para Status: " + valor);
    }
}