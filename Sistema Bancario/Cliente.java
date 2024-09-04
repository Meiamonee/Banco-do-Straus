package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Cliente {
    private String cpf;
    private String nome;
    private Conta conta;
    private String login;
    private String senha;

    public Cliente(String cpf, String nome, Conta conta, String login, String senha) {
        this.cpf = cpf;
        this.nome = nome;
        this.conta = conta;
        this.login = login;
        this.senha = senha;
    }

    public Conta getConta() {
        return conta;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getLogin() {
        return login;
    }

    public boolean validarSenha(String senha) {
        return this.senha.equals(senha);
    }

    public static Cliente login(String login, String senha) {
        String query = "SELECT * FROM clientes WHERE login = ? AND senha = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, login);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String cpf = rs.getString("cpf");
                String nome = rs.getString("nome");
                String numeroConta = rs.getString("numero_conta");
                float saldo = rs.getFloat("saldo");

                Conta conta;
                if (rs.getFloat("limite_credito") > 0) {
                    conta = new ContaCorrente(numeroConta, saldo, nome, rs.getFloat("limite_credito"));
                } else if (rs.getFloat("taxa_rendimento") > 0) {
                    conta = new ContaPoupanca(numeroConta, saldo, nome, rs.getFloat("taxa_rendimento"));
                } else {
                    conta = new Conta(numeroConta, saldo, nome);
                }

                return new Cliente(cpf, nome, conta, login, senha) {};
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void executarOperacao(String login, String senha, Runnable operacao) {
        if (this.login.equals(login) && validarSenha(senha)) {
            operacao.run();
        } else {
            System.out.println("Login ou senha incorretos para " + this.nome);
        }
    }
}
