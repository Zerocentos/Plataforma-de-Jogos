import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class Conexao {
  private static final String URL =
    "jdbc:h2:mem:plataforma_jogos;DB_CLOSE_DELAY=-1";

  private Conexao() {}

  public static Connection abrir() throws SQLException {
    Connection conexao = DriverManager.getConnection(URL);
    inicializarBanco(conexao);
    return conexao;
  }

  private static void inicializarBanco(Connection conexao) throws SQLException {
    try {
      for (String linha : Files.readAllLines(Path.of("jogadores.sql"))) {
        String comando = linha.trim();
        if (!comando.isEmpty() && !comando.startsWith("--")) {
          try (Statement stmt = conexao.createStatement()) {
            stmt.execute(comando);
          }
        }
      }
    } catch (IOException e) {
      throw new SQLException("Não foi possível ler jogadores.sql.", e);
    }
  }
}
