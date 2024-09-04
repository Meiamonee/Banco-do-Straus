package com.example;

public class Main {
    public static void main(String[] args) {
        // Tentativa de login
        Cliente cliente = Cliente.login("pedro123", "senhaPedro123");
        if (cliente != null) {
            System.out.println("Login bem-sucedido para " + cliente.getNome());

            // Executando operações bancárias
            cliente.executarOperacao("pedro123", "senhaPedro123", () -> {
                cliente.getConta().deposito(200.0f);
                cliente.getConta().saque(150.0f);
            });

            // Exemplo de transferência (não inclui outra conta)
            // cliente.getConta().transferencia(outraConta, valor);
        } else {
            System.out.println("Falha no login. Verifique suas credenciais.");
        }
    }
}
