import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/** Exibe o total de jogadores, a quantidade e as médias por perfil,
 * e os jogadores com mais de 20 horas semanais e taxa de vitória acima de 55%.
 */
public final class RelatorioPtA {

  private RelatorioPtA() {}

  public static void imprimir(Connection conexao, List<Jogador> jogadores)
      throws SQLException {
    System.out.println("Jogadores carregados: " + jogadores.size());
    if (jogadores.size() != 150) {
      System.out.println("Aviso: esperava-se 150 registros na tabela jogador.");
    }

    String query1 = "SELECT perfil, COUNT(*) AS quantidade FROM jogador "
                      + "GROUP BY perfil ORDER BY perfil";
    String query2 = "SELECT perfil, AVG(horas_semana) AS media_horas_semana, "
                      + "AVG(partidas_semana) AS media_partidas_semana, "
                      + "AVG(gasto_mensal) AS media_gasto_mensal, "
                      + "AVG(taxa_vitoria) AS media_taxa_vitoria "
                      + "FROM jogador GROUP BY perfil ORDER BY perfil";
    String query3 = "SELECT apelido, perfil, taxa_vitoria FROM jogador "
                      + "WHERE horas_semana > 20 AND taxa_vitoria > 55 "
                      + "ORDER BY taxa_vitoria DESC, apelido";

    System.out.println();
    imprimirConsulta(conexao, "(a) Quantidade por perfil", query1);
    imprimirConsulta(conexao, "(b) Médias das características por perfil", query2);
    imprimirConsulta(conexao, "(c) Jogadores acima dos limites", query3);
  }

  private static void imprimirConsulta(Connection conexao, String titulo, String sql)
      throws SQLException {
    System.out.println(titulo);
    try (PreparedStatement stmt = conexao.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

      int colunas = rs.getMetaData().getColumnCount();
      while (rs.next()) {
        for (int coluna = 1; coluna <= colunas; coluna++) {
          if (coluna > 1) {
            System.out.print(" | ");
          }

          System.out.print(rs.getMetaData().getColumnLabel(coluna)
            + ": " + rs.getString(coluna));
        }
        System.out.println();
      }
      System.out.println();
    }
  }
}
