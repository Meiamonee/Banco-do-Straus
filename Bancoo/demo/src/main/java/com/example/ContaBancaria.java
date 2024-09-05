package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContaBancaria {
    private int id;
    private String titular;
    private double saldo;
    private String numeroConta;

    // Construtores
    public ContaBancaria(String titular, double saldo, String numeroConta) {
        this.titular = titular;
        this.saldo = saldo;
        this.numeroConta = numeroConta;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitular() { return titular; }
    public void setTitular(String titular) { this.titular = titular; }
    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }
    public String getNumeroConta() { return numeroConta; }
    public void setNumeroConta(String numeroConta) { this.numeroConta = numeroConta; }

    // Método para criar uma nova conta e retornar o objeto criado
    public static ContaBancaria criarConta(ContaBancaria conta) {
        String insertSql = "INSERT INTO conta_bancaria (titular, saldo, numero_conta) VALUES (?, ?, ?)";
        String selectSql = "SELECT * FROM conta_bancaria WHERE numero_conta = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement insertStmt = conn.prepareStatement(insertSql);
             PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {

            // Inserindo os dados na tabela
            insertStmt.setString(1, conta.getTitular());
            insertStmt.setDouble(2, conta.getSaldo());
            insertStmt.setString(3, conta.getNumeroConta());
            insertStmt.executeUpdate();
            System.out.println("Conta criada com sucesso!");

            // Recuperar a conta recém-criada para confirmação
            selectStmt.setString(1, conta.getNumeroConta());
            try (ResultSet rs = selectStmt.executeQuery()) {
                if (rs.next()) {
                    ContaBancaria contaCriada = new ContaBancaria(
                            rs.getString("titular"),
                            rs.getDouble("saldo"),
                            rs.getString("numero_conta")
                    );
                    contaCriada.setId(rs.getInt("id"));
                    System.out.println("Verificação: Conta inserida com sucesso no banco de dados.");
                    return contaCriada;
                } else {
                    System.out.println("Erro: A conta não foi encontrada no banco de dados após a inserção.");
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao criar ou verificar conta: " + e.getMessage());
        }
        return null;
    }

    // Método para login na conta usando número da conta e titular como autenticação
    public static boolean efetuarLogin(String numeroConta, String titular) {
        String sql = "SELECT * FROM conta_bancaria WHERE numero_conta = ? AND titular = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, numeroConta);
            stmt.setString(2, titular);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Login realizado com sucesso! Bem-vindo, " + rs.getString("titular") + "!");
                    return true;
                } else {
                    System.out.println("Erro: Número da conta ou titular incorretos.");
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao efetuar login: " + e.getMessage());
        }
        return false;
    }

    // Método para adicionar saldo à conta
    public static void adicionarSaldo(String numeroConta, double valor) {
        String updateSql = "UPDATE conta_bancaria SET saldo = saldo + ? WHERE numero_conta = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateSql)) {

            stmt.setDouble(1, valor);
            stmt.setString(2, numeroConta);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Saldo adicionado com sucesso!");
            } else {
                System.out.println("Erro: Conta não encontrada.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao adicionar saldo: " + e.getMessage());
        }
    }

    // Método para transferir saldo de uma conta para outra
    public static void transferirSaldo(String numeroContaOrigem, String numeroContaDestino, double valor) {
        String selectSql = "SELECT saldo FROM conta_bancaria WHERE numero_conta = ?";
        String updateOrigemSql = "UPDATE conta_bancaria SET saldo = saldo - ? WHERE numero_conta = ?";
        String updateDestinoSql = "UPDATE conta_bancaria SET saldo = saldo + ? WHERE numero_conta = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); // Iniciando transação

            // Verificar saldo da conta de origem
            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                selectStmt.setString(1, numeroContaOrigem);
                try (ResultSet rs = selectStmt.executeQuery()) {
                    if (rs.next() && rs.getDouble("saldo") >= valor) {
                        // Diminuir saldo da conta de origem
                        try (PreparedStatement updateOrigemStmt = conn.prepareStatement(updateOrigemSql)) {
                            updateOrigemStmt.setDouble(1, valor);
                            updateOrigemStmt.setString(2, numeroContaOrigem);
                            updateOrigemStmt.executeUpdate();
                        }

                        // Aumentar saldo da conta de destino
                        try (PreparedStatement updateDestinoStmt = conn.prepareStatement(updateDestinoSql)) {
                            updateDestinoStmt.setDouble(1, valor);
                            updateDestinoStmt.setString(2, numeroContaDestino);
                            updateDestinoStmt.executeUpdate();
                        }

                        conn.commit(); // Confirmar transação
                        System.out.println("Transferência realizada com sucesso!");
                    } else {
                        System.out.println("Erro: Saldo insuficiente ou conta não encontrada.");
                    }
                }
            } catch (SQLException e) {
                conn.rollback(); // Reverter transação em caso de erro
                System.err.println("Erro na transferência: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados para transferência: " + e.getMessage());
        }
    }

    // Método para excluir uma conta
    public static void excluirConta(String numeroConta) {
        String sql = "DELETE FROM conta_bancaria WHERE numero_conta = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, numeroConta);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Conta excluída com sucesso!");
            } else {
                System.out.println("Erro: Conta não encontrada.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao excluir conta: " + e.getMessage());
        }
    }
    
    // Método para consultar uma conta existente no banco de dados
public static ContaBancaria consultarConta(String numeroConta, String titular) {
    String sql = "SELECT * FROM conta_bancaria WHERE numero_conta = ? AND titular = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, numeroConta);
        stmt.setString(2, titular);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                // Criar o objeto ContaBancaria com os dados do banco
                ContaBancaria conta = new ContaBancaria(
                        rs.getString("titular"),
                        rs.getDouble("saldo"),
                        rs.getString("numero_conta")
                );
                conta.setId(rs.getInt("id")); // Definir o ID da conta
                return conta; // Retornar a conta encontrada
            }
        }

    } catch (SQLException e) {
        System.err.println("Erro ao consultar conta: " + e.getMessage());
    }
    return null; // Retornar null se a conta não for encontrada
}

}
