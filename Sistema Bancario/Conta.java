public class Conta {
    private String numeroConta;
    private float saldo;
    private String titular;

    public Conta(String numeroConta, float saldo, String titular) {
        this.titular = titular;
        this.saldo = saldo;
        this.numeroConta = numeroConta;
    }

    public void deposito(float valor) {
        if (valor > 0) {
            saldo += valor;
            System.out.println("Depósito de R$ " + valor + " realizado. Novo saldo: R$ " + saldo);
        }
    }

    public void saque(float valor) {
        if (valor > 0 && valor <= saldo) {
            saldo -= valor;
            System.out.println("Saque de R$ " + valor + " realizado. Novo saldo: R$ " + saldo);
        } else {
            System.out.println("Saque não realizado. Saldo insuficiente.");
        }
    }

    public void transferencia(Conta destino, float valor) {
        if (valor > 0 && valor <= saldo) {
            saque(valor);
            destino.deposito(valor);
            System.out.println("Transferência de R$ " + valor + " realizada para a conta " + destino.getNumeroConta());
        } else {
            System.out.println("Transferência não realizada. Saldo insuficiente.");
        }
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public float getSaldo() {
        return saldo;
    }

    public String getTitular() {
        return titular;
    }
}