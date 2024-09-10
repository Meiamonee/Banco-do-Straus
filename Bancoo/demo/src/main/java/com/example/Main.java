package com.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Menu inicial para criar conta ou efetuar login2
            System.out.println("\n=== Sistema Bancário ===");
            System.out.println("1. Criar nova conta");
            System.out.println("2. Efetuar login");
            System.out.println("3. Encerrar o programa");
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
                    System.out.println("Conta criada com sucesso! Você será redirecionado para o menu principal.");
                    continue; // Voltar ao menu principal após criar a conta

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
                        continue; // Voltar ao menu principal se o login falhar
                    } else {
                        System.out.println("Login realizado com sucesso! Bem-vindo, " + contaCriada.getTitular() + "!");
                    }
                    break;

                case 3:
                    // Encerrar o programa
                    System.out.println("Encerrando o programa...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opção inválida.");
                    continue; // Voltar ao menu principal
            }

            // Menu de operações após logar na conta
            while (true) {
                System.out.println("\n=== Operações Disponíveis ===");
                System.out.println("1. Mostrar Saldo");
                System.out.println("2. Adicionar Saldo");
                System.out.println("3. Transferir Saldo");
                System.out.println("4. Excluir Conta");
                System.out.println("5. Sair da Conta");
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
                        break;
                    case 5:
                        // Sair da conta e voltar ao menu principal
                        System.out.println("Saindo da conta...");
                        break; // Quebra o loop interno e volta para o menu principal
                    default:
                        System.out.println("Opção inválida.");
                        break;
                }

                if (opcao == 5 || opcao == 4) {
                    break; // Voltar ao menu principal após sair da conta ou excluir
                }
            }
        }
    }
}
