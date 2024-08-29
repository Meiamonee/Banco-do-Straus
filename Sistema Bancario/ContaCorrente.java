public class ContaCorrente extends Conta {
    private float limiteCredito;

    public ContaCorrente(String numeroConta, float saldo, String titular, float limiteCredito) {
        super(numeroConta, saldo, titular);
        this.limiteCredito = limiteCredito;
    }

    public void usarCredito(float valor) {
        if (valor > 0 && valor <= limiteCredito) {
            deposito(valor);
            limiteCredito -= valor;
            System.out.println("Uso de crédito de R$ " + valor + " realizado. Crédito restante: R$ " + limiteCredito);
        } else {
            System.out.println("Uso de crédito não permitido. Limite de crédito insuficiente.");
        }
    }

    public void pagarFatura(float valor) {
        if (valor > 0 && valor <= getSaldo()) {
            saque(valor);
            limiteCredito += valor;
            System.out.println("Fatura de R$ " + valor + " paga. Novo limite de crédito: R$ " + limiteCredito);
        } else {
            System.out.println("Pagamento da fatura não realizado. Saldo insuficiente.");
        }
    }

    public float getLimiteCredito() {
        return limiteCredito;
    }
}