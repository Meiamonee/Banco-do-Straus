package com.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
           
            System.out.println("\n=== Sistema Bancário ===");
            System.out.println("1. Criar nova conta");
            System.out.println("2. Efetuar login");
            System.out.println("3. Encerrar o programa");
            System.out.print("Escolha uma opção: ");
            int escolhaInicial = scanner.nextInt();
            scanner.nextLine(); 

            ContaBancaria contaCriada = null;

            switch (escolhaInicial) {
                case 1:
                    

                case 2:
                    
                    System.out.println("=== Login na Conta ===");
                    System.out.print("Digite o número da conta: ");
                    String numeroContaLogin = scanner.nextLine();
                    System.out.print("Digite o titular da conta: ");
                    String titularLogin = scanner.nextLine();

                    
                    contaCriada = ContaBancaria.consultarConta(numeroContaLogin, titularLogin);

                    
                    if (contaCriada == null) {
                        System.out.println("Erro: Número da conta ou titular incorretos.");
                        continue;
                    } else {
                        System.out.println("Login realizado com sucesso! Bem-vindo, " + contaCriada.getTitular() + "!");
                    }
                    break;

                case 3:
                   
                    System.out.println("Encerrando o programa...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opção inválida.");
                    continue;
            }

           
            while (true) {
                System.out.println("\n=== Operações Disponíveis ===");
                System.out.println("1. Mostrar Saldo");
                System.out.println("2. Adicionar Saldo");
                System.out.println("3. Transferir Saldo");
                System.out.println("4. Excluir Conta");
                System.out.println("5. Sair da Conta");
                System.out.print("Escolha uma opção: ");

                int opcao = scanner.nextInt();
                scanner.nextLine(); 

                switch (opcao) {
                    case 1:
                       
                        System.out.println("Seu saldo atual é: R$ " + contaCriada.getSaldo());
                        break;
                    case 2:
                       
                        System.out.print("Digite o valor a adicionar: ");
                        double valorAdicionar = scanner.nextDouble();
                        ContaBancaria.adicionarSaldo(contaCriada.getNumeroConta(), valorAdicionar);
                        break;
                    case 3:
                      
                        System.out.print("Digite o número da conta de destino: ");
                        String numeroContaDestino = scanner.nextLine();
                        System.out.print("Digite o valor a transferir: ");
                        double valorTransferir = scanner.nextDouble();
                        ContaBancaria.transferirSaldo(contaCriada.getNumeroConta(), numeroContaDestino, valorTransferir);
                        break;
                    case 4:
                       
                        ContaBancaria.excluirConta(contaCriada.getNumeroConta());
                        break;
                    case 5:
                        
                        System.out.println("Saindo da conta...");
                        break; 
                    default:
                        System.out.println("Opção inválida.");
                        break;
                }

                if (opcao == 5 || opcao == 4) {
                    break; 
                }
            }
        }
    }
}
