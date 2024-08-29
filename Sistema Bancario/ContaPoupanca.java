public class ContaPoupanca extends Conta {
    private float taxaRendimento;

    public ContaPoupanca(String numeroConta, float saldo, String titular, float taxaRendimento) {
        super(numeroConta, saldo, titular);
        this.taxaRendimento = taxaRendimento;
    }

    public void atualizarSaldo() {
        float rendimento = getSaldo() * (taxaRendimento / 100);
        deposito(rendimento);
        System.out.println("Saldo atualizado com rendimento de R$ " + rendimento + ". Novo saldo: R$ " + getSaldo());
    }

    public float getTaxaRendimento() {
        return taxaRendimento;
    }
}