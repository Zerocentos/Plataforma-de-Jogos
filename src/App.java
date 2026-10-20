import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class App {
  public static void main(String[] args) {
    try (Connection conexao = Conexao.abrir()) {
      JogadorDAO jogadorDAO = new JogadorDAOJdbc(conexao);
      List<Jogador> jogadores = jogadorDAO.listarTodos();
      RelatorioPtA.imprimir(conexao, jogadores);
    } catch (SQLException | IllegalStateException e) {
      System.err.println("Não foi possível executar a Parte A: " + e.getMessage());
      System.exit(1);
    }
  }
}
