public class ContaPoupanca extends Conta{
    
    private float taxaRendimento;

    public ContaPoupanca(String numeroConta, float saldo, String titular) {
        super(numeroConta, saldo, titular);
        //TODO Auto-generated constructor stub
    }

    public void atualizarSaldo(){

    }

    public float getTaxaRendimento() {
        return taxaRendimento;
    }
}
