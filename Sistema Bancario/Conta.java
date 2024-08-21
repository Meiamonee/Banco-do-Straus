public class Conta {
    private String numeroConta;
    private float saldo;
    private String titular;

    public Conta(String numeroConta, float saldo, String titular){
        this.titular = titular;
        this.saldo = saldo;
        this.numeroConta = numeroConta;
    }

    public void deposito(float valor){

    }

    public void saque(float valor){

    }

    public void transferencia(Conta destino, float valor){
        
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
