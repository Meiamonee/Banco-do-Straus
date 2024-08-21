/**
 * Cliente
 */
public abstract class Cliente {
    private String cpf;
    private String nome;
    private Conta conta;

    public Cliente(String cpf, String nome, Conta conta){
        this.cpf = cpf;
        this.nome = nome;
        this.conta = conta;
    }

    public Conta getConta() {
        return conta;
    }

    public String getCpf() {
        return cpf;
    }
    
    public String getNome() {
        return nome;
    }
    
}