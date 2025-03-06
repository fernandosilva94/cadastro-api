package io.github.fernandosilva94.cadastro.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UsuarioDTO {

    private Long id;
    @NotBlank(message = "Nome não pode ser nulo")
    private String nome;
    @NotBlank(message = "email não pode ser nulo")
    @Email(message = "Email deve ser válido")
    private String email;
    @NotBlank(message = "senha não pode ser nulo")
    private String senha;
    @NotBlank(message = "documento não pode ser nulo")
    private String documento;
    @NotBlank(message = "Status não pode ser nulo")
    @Pattern(regexp = "[AI]", message = "Status deve ser 'A' (Ativo) ou 'I' (Inativo)")
    private char status;

    private String nivelAcesso;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public String getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(String nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }
}
