package com.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Menu inicial para criar conta ou efetuar login
        System.out.println("=== Sistema Bancário ===");
        System.out.println("1. Criar nova conta");
        System.out.println("2. Efetuar login");
        System.out.print("Escolha uma opção: ");
        int escolhaInicial = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha

        ContaBancaria contaCriada = null;

        switch (escolhaInicial) {
            case 1:
                // Criar uma nova conta
                System.out.println("=== Criar Nova Conta ===");
                System.out.print("Digite o nome do titular: ");
                String titular = scanner.nextLine();
                System.out.print("Digite o saldo inicial: ");
                double saldo = scanner.nextDouble();
                scanner.nextLine(); // Consumir a quebra de linha
                System.out.print("Digite o número da conta: ");
                String numeroConta = scanner.nextLine();

                // Criar o objeto ContaBancaria e salvar no banco de dados
                contaCriada = new ContaBancaria(titular, saldo, numeroConta);
                ContaBancaria.criarConta(contaCriada);
                System.out.println("Conta criada com sucesso!");
                break;

            case 2:
                // Efetuar login na conta existente
                System.out.println("=== Login na Conta ===");
                System.out.print("Digite o número da conta: ");
                String numeroContaLogin = scanner.nextLine();
                System.out.print("Digite o titular da conta: ");
                String titularLogin = scanner.nextLine();

                // Verificar se a conta existe no banco de dados
                contaCriada = ContaBancaria.consultarConta(numeroContaLogin, titularLogin);

                // Verificar se o login foi bem-sucedido
                if (contaCriada == null) {
                    System.out.println("Erro: Número da conta ou titular incorretos.");
                    return; // Encerrar se o login falhar
                } else {
                    System.out.println("Login realizado com sucesso! Bem-vindo, " + contaCriada.getTitular() + "!");
                }
                break;

            default:
                System.out.println("Opção inválida.");
                return; // Encerrar o programa
        }

        // Menu de operações após criar ou logar na conta
        while (true) {
            System.out.println("\n=== Operações Disponíveis ===");
            System.out.println("1. Mostrar Saldo");
            System.out.println("2. Adicionar Saldo");
            System.out.println("3. Transferir Saldo");
            System.out.println("4. Excluir Conta");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    // Mostrar saldo da conta
                    System.out.println("Seu saldo atual é: R$ " + contaCriada.getSaldo());
                    break;
                case 2:
                    // Adicionar saldo à conta
                    System.out.print("Digite o valor a adicionar: ");
                    double valorAdicionar = scanner.nextDouble();
                    ContaBancaria.adicionarSaldo(contaCriada.getNumeroConta(), valorAdicionar);
                    break;
                case 3:
                    // Transferir saldo para outra conta
                    System.out.print("Digite o número da conta de destino: ");
                    String numeroContaDestino = scanner.nextLine();
                    System.out.print("Digite o valor a transferir: ");
                    double valorTransferir = scanner.nextDouble();
                    ContaBancaria.transferirSaldo(contaCriada.getNumeroConta(), numeroContaDestino, valorTransferir);
                    break;
                case 4:
                    // Excluir a conta
                    ContaBancaria.excluirConta(contaCriada.getNumeroConta());
                    return; // Encerrar após exclusão da conta
                case 5:
                    System.out.println("Saindo...");
                    return; // Encerrar o programa
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }
}
