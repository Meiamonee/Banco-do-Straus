public class Main {
    public static void main(String[] args) {
        // Criando um Cliente com Conta Simples
        Conta contaSimples = new Conta("001", 1000.0f, "Pedro");
        Cliente cliente1 = new Cliente("12345678900", "Pedro", contaSimples, "pedro123", "senhaPedro123") {};
        System.out.println("Conta Simples criada para " + cliente1.getNome());

        // Realizando operações apenas se o login e a senha estiverem corretos
        cliente1.executarOperacao("pedro123", "senhaPedro123", () -> {
            cliente1.getConta().deposito(200.0f);
            cliente1.getConta().saque(150.0f);
        });

        // Criando um Cliente com Conta Corrente
        ContaCorrente contaCorrente = new ContaCorrente("002", 500.0f, "Ana", 300.0f);
        Cliente cliente2 = new Cliente("98765432100", "Ana", contaCorrente, "ana321", "senhaAna321") {};
        System.out.println("\nConta Corrente criada para " + cliente2.getNome());

        cliente2.executarOperacao("ana321", "senhaAna321", () -> {
            cliente2.getConta().deposito(200.0f);
            ((ContaCorrente) cliente2.getConta()).usarCredito(100.0f);
            ((ContaCorrente) cliente2.getConta()).pagarFatura(50.0f);
        });

        // Criando um Cliente com Conta Poupança
        ContaPoupanca contaPoupanca = new ContaPoupanca("003", 1000.0f, "Carlos", 5.0f);
        Cliente cliente3 = new Cliente("11122233344", "Carlos", contaPoupanca, "carlos111", "senhaCarlos111") {};
        System.out.println("\nConta Poupança criada para " + cliente3.getNome());

        cliente3.executarOperacao("carlos111", "senhaCarlos111", () -> {
            ((ContaPoupanca) cliente3.getConta()).atualizarSaldo();
            cliente3.getConta().saque(500.0f);
        });

        // Testando transferência entre contas com validação de login
        cliente1.executarOperacao("pedro123", "senhaPedro123", () -> {
            System.out.println("\nTransferência de Pedro (Conta Simples) para Ana (Conta Corrente)");
            cliente1.getConta().transferencia(cliente2.getConta(), 200.0f);
        });

        cliente2.executarOperacao("ana321", "senhaAna321", () -> {
            System.out.println("\nTransferência de Ana (Conta Corrente) para Carlos (Conta Poupança)");
            cliente2.getConta().transferencia(cliente3.getConta(), 50.0f);
        });
    }
}
