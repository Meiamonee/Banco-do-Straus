public abstract class Cliente {
    private String cpf;
    private String nome;
    private Conta conta;
    private String login;
    private String senha;

    public Cliente(String cpf, String nome, Conta conta, String login, String senha) {
        this.cpf = cpf;
        this.nome = nome;
        this.conta = conta;
        this.login = login;
        this.senha = senha;
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

    public String getLogin() {
        return login;
    }

    public boolean validarSenha(String senha) {
        return this.senha.equals(senha);
    }

    public void alterarSenha(String novaSenha) {
        this.senha = novaSenha;
    }

    public void executarOperacao(String login, String senha, Runnable operacao) {
        if (this.login.equals(login) && validarSenha(senha)) {
            operacao.run();
        } else {
            System.out.println("Login ou senha incorretos para " + this.nome);
        }
    }
}
