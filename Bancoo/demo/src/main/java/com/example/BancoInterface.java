package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BancoInterface {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JTextField loginNumeroContaField;
    private JTextField loginTitularField;
    private ContaBancaria contaAtual;

    public BancoInterface() {
        frame = new JFrame("Sistema Bancário");
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Painel de login
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BorderLayout());

        // Adicionar imagem no topo
        JPanel imagePanel = new JPanel();
        JLabel imageLabel = new JLabel();
        try {
            // Carregar a imagem PNG
            Image img = ImageIO.read(new File("C:\\Users\\Pedro\\OneDrive\\Área de Trabalho\\Bancoo\\demo\\src\\main\\java\\com\\example\\banco-do-brasil-200x150.jpg")); // Substitua pelo caminho da sua imagem
            ImageIcon icon = new ImageIcon(img.getScaledInstance(150, 100, Image.SCALE_SMOOTH));
            imageLabel.setIcon(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imagePanel.add(imageLabel);
        loginPanel.add(imagePanel, BorderLayout.NORTH);

        // Painel de campos e botões
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Definir margens entre os componentes

        // Label e campo para o número da conta
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Número da Conta:"), gbc);

        loginNumeroContaField = new JTextField();
        loginNumeroContaField.setPreferredSize(new Dimension(150, 25)); // Definindo tamanho menor para o campo de texto
        gbc.gridx = 1;
        formPanel.add(loginNumeroContaField, gbc);

        // Label e campo para o titular
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Titular:"), gbc);

        loginTitularField = new JTextField();
        loginTitularField.setPreferredSize(new Dimension(150, 25)); // Definindo tamanho menor para o campo de texto
        gbc.gridx = 1;
        formPanel.add(loginTitularField, gbc);

        // Botões de Login e Criar Conta
        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100, 25)); // Definindo tamanho menor para o botão
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(loginButton, gbc);

        JButton criarContaButton = new JButton("Criar Conta");
        criarContaButton.setPreferredSize(new Dimension(100, 25)); // Definindo tamanho menor para o botão
        gbc.gridx = 1;
        formPanel.add(criarContaButton, gbc);

        loginPanel.add(formPanel, BorderLayout.CENTER);

        // Painel de operações
        JPanel operacoesPanel = new JPanel();
        operacoesPanel.setLayout(new GridBagLayout());

        // Botões de operações com tamanho reduzido
        JButton saldoButton = new JButton("Mostrar Saldo");
        saldoButton.setPreferredSize(new Dimension(150, 30)); // Tamanho menor para o botão
        JButton adicionarSaldoButton = new JButton("Adicionar Saldo");
        adicionarSaldoButton.setPreferredSize(new Dimension(150, 30)); // Tamanho menor para o botão
        JButton transferirButton = new JButton("Transferir Saldo");
        transferirButton.setPreferredSize(new Dimension(150, 30)); // Tamanho menor para o botão
        JButton excluirContaButton = new JButton("Excluir Conta");
        excluirContaButton.setPreferredSize(new Dimension(150, 30)); // Tamanho menor para o botão
        JButton sairButton = new JButton("Sair da Conta");
        sairButton.setPreferredSize(new Dimension(150, 30)); // Tamanho menor para o botão

        // Organizando botões no painel de operações
        gbc.gridx = 0;
        gbc.gridy = 0;
        operacoesPanel.add(saldoButton, gbc);

        gbc.gridy = 1;
        operacoesPanel.add(adicionarSaldoButton, gbc);

        gbc.gridy = 2;
        operacoesPanel.add(transferirButton, gbc);

        gbc.gridy = 3;
        operacoesPanel.add(excluirContaButton, gbc);

        gbc.gridy = 4;
        operacoesPanel.add(sairButton, gbc);

        // Adicionando painéis ao CardLayout
        mainPanel.add(loginPanel, "Login");
        mainPanel.add(operacoesPanel, "Operações");

        // Ação do botão de login
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        // Ação do botão de criar conta (aqui você pode adicionar a lógica para criação de conta)
        criarContaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Código para criar uma nova conta
            }
        });

        // Ação do botão "Mostrar Saldo"
        saldoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (contaAtual != null) {
                    JOptionPane.showMessageDialog(frame, "Seu saldo atual é: R$ " + contaAtual.getSaldo());
                }
            }
        });

        // Ação do botão "Adicionar Saldo"
        adicionarSaldoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String valor = JOptionPane.showInputDialog(frame, "Digite o valor a adicionar:");
                if (valor != null && contaAtual != null) {
                    double valorAdicionar = Double.parseDouble(valor);
                    ContaBancaria.adicionarSaldo(contaAtual.getNumeroConta(), valorAdicionar);
                    JOptionPane.showMessageDialog(frame, "Saldo adicionado com sucesso!");
                }
            }
        });

        // Ação do botão "Transferir Saldo"
        transferirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String contaDestino = JOptionPane.showInputDialog(frame, "Digite o número da conta de destino:");
                String valor = JOptionPane.showInputDialog(frame, "Digite o valor a transferir:");
                if (contaDestino != null && valor != null && contaAtual != null) {
                    double valorTransferir = Double.parseDouble(valor);
                    ContaBancaria.transferirSaldo(contaAtual.getNumeroConta(), contaDestino, valorTransferir);
                }
            }
        });

        // Ação do botão "Excluir Conta"
        excluirContaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (contaAtual != null) {
                    ContaBancaria.excluirConta(contaAtual.getNumeroConta());
                    JOptionPane.showMessageDialog(frame, "Conta excluída com sucesso!");
                    cardLayout.show(mainPanel, "Login");
                }
            }
        });

        // Ação do botão "Sair da Conta"
        sairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contaAtual = null;
                cardLayout.show(mainPanel, "Login");
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(mainPanel);
        frame.setSize(400, 300);
        frame.setVisible(true);
    }

    // Método para realizar o login
    private void login() {
        String numeroConta = loginNumeroContaField.getText();
        String titular = loginTitularField.getText();

        contaAtual = ContaBancaria.consultarConta(numeroConta, titular);
        if (contaAtual != null) {
            JOptionPane.showMessageDialog(frame, "Login realizado com sucesso!");
            cardLayout.show(mainPanel, "Operações");
        } else {
            JOptionPane.showMessageDialog(frame, "Erro: Número da conta ou titular incorretos.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BancoInterface());
    }
}
