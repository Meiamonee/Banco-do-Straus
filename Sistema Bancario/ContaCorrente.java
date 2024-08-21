public class ContaCorrente extends Conta {

    private float limiteCredito;

    public ContaCorrente(String numeroConta, float saldo, String titular) {
        super(numeroConta, saldo, titular);
        //TODO Auto-generated constructor stub
    }

    public void usarCredito(float valor){

    }

    public void pagarFatura(float valor){

    }

    public float getLimiteCredito() {
        return limiteCredito;
    }
    
}
