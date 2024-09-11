import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ContaBancariaTest {

    private ContaBancaria contaOrigem;
    private ContaBancaria contaDestino;

    @BeforeEach
    public void setup() {
        contaOrigem = new ContaBancaria("João Silva", 500.0, "123456");
        contaDestino = new ContaBancaria("Maria Souza", 300.0, "654321");
        
        ContaBancaria.criarConta(contaOrigem);
        ContaBancaria.criarConta(contaDestino);
    }

    @Test
    public void testCriarConta() {
        ContaBancaria novaConta = new ContaBancaria("Pedro Oliveira", 1000.0, "789101");
        ContaBancaria contaCriada = ContaBancaria.criarConta(novaConta);
        
        assertNotNull(contaCriada, "A conta criada não deve ser nula.");
        assertEquals("Pedro Oliveira", contaCriada.getTitular(), "O titular da conta deve ser Pedro Oliveira.");
        assertEquals(1000.0, contaCriada.getSaldo(), "O saldo inicial deve ser 1000.0.");
    }

    @Test
    public void testEfetuarLoginSucesso() {
        boolean loginSucesso = ContaBancaria.efetuarLogin(contaOrigem.getNumeroConta(), contaOrigem.getTitular());
        assertTrue(loginSucesso, "O login deve ser realizado com sucesso.");
    }

    @Test
    public void testAdicionarSaldo() {
        ContaBancaria.adicionarSaldo(contaOrigem.getNumeroConta(), 200.0);
        ContaBancaria contaAtualizada = ContaBancaria.consultarConta(contaOrigem.getNumeroConta(), contaOrigem.getTitular());
        
        assertNotNull(contaAtualizada, "A conta atualizada não deve ser nula.");
        assertEquals(700.0, contaAtualizada.getSaldo(), "O saldo deve ser atualizado para 700.0 após a adição de 200.0.");
    }

    @Test
    public void testTransferirSaldo() {
        ContaBancaria.transferirSaldo(contaOrigem.getNumeroConta(), contaDestino.getNumeroConta(), 100.0);
        
        ContaBancaria contaOrigemAtualizada = ContaBancaria.consultarConta(contaOrigem.getNumeroConta(), contaOrigem.getTitular());
        ContaBancaria contaDestinoAtualizada = ContaBancaria.consultarConta(contaDestino.getNumeroConta(), contaDestino.getTitular());
        
        assertNotNull(contaOrigemAtualizada, "A conta de origem atualizada não deve ser nula.");
        assertNotNull(contaDestinoAtualizada, "A conta de destino atualizada não deve ser nula.");
        
        assertEquals(400.0, contaOrigemAtualizada.getSaldo(), "O saldo da conta de origem deve ser reduzido para 400.0.");
        assertEquals(400.0, contaDestinoAtualizada.getSaldo(), "O saldo da conta de destino deve ser aumentado para 400.0.");
    }
}
