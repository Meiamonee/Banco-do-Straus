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

        
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BorderLayout());

       
        JPanel imagePanel = new JPanel();
        JLabel imageLabel = new JLabel();
        try {
           
            Image img = ImageIO.read(new File("C:\\Users\\Pedro\\OneDrive\\Área de Trabalho\\Bancoo\\demo\\src\\main\\java\\com\\example\\banco-do-brasil-200x150.jpg")); // Substitua pelo caminho da sua imagem
            ImageIcon icon = new ImageIcon(img.getScaledInstance(150, 100, Image.SCALE_SMOOTH));
            imageLabel.setIcon(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imagePanel.add(imageLabel);
        loginPanel.add(imagePanel, BorderLayout.NORTH);

        
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Número da Conta:"), gbc);

        loginNumeroContaField = new JTextField();
        loginNumeroContaField.setPreferredSize(new Dimension(150, 25)); 
        gbc.gridx = 1;
        formPanel.add(loginNumeroContaField, gbc);

   
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Titular:"), gbc);

        loginTitularField = new JTextField();
        loginTitularField.setPreferredSize(new Dimension(150, 25)); 
        gbc.gridx = 1;
        formPanel.add(loginTitularField, gbc);


        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100, 25));
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(loginButton, gbc);

        JButton criarContaButton = new JButton("Criar Conta");
        criarContaButton.setPreferredSize(new Dimension(100, 25)); 
        gbc.gridx = 1;
        formPanel.add(criarContaButton, gbc);

        loginPanel.add(formPanel, BorderLayout.CENTER);

      
        JPanel operacoesPanel = new JPanel();
        operacoesPanel.setLayout(new GridBagLayout());

        
        JButton saldoButton = new JButton("Mostrar Saldo");
        saldoButton.setPreferredSize(new Dimension(150, 30));
        JButton adicionarSaldoButton = new JButton("Adicionar Saldo");
        adicionarSaldoButton.setPreferredSize(new Dimension(150, 30)); 
        JButton transferirButton = new JButton("Transferir Saldo");
        transferirButton.setPreferredSize(new Dimension(150, 30)); 
        JButton excluirContaButton = new JButton("Excluir Conta");
        excluirContaButton.setPreferredSize(new Dimension(150, 30)); 
        JButton sairButton = new JButton("Sair da Conta");
        sairButton.setPreferredSize(new Dimension(150, 30)); 

   
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

    
        mainPanel.add(loginPanel, "Login");
        mainPanel.add(operacoesPanel, "Operações");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

    
     criarContaButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String numeroConta = JOptionPane.showInputDialog(frame, "Digite o número da nova conta:");
            String titular = JOptionPane.showInputDialog(frame, "Digite o nome do titular:");
    
            if (numeroConta != null && titular != null) {
                // Criar a nova conta
                ContaBancaria novaConta = new ContaBancaria(titular, 0.0, numeroConta);
                ContaBancaria contaCriada = ContaBancaria.criarConta(novaConta);
    
                if (contaCriada != null) {
                    JOptionPane.showMessageDialog(frame, "Conta criada com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Erro ao criar a conta.");
                }
            }
        }
    });
    


        
        saldoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (contaAtual != null) {
                    JOptionPane.showMessageDialog(frame, "Seu saldo atual é: R$ " + contaAtual.getSaldo());
                }
            }
        });

        
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
