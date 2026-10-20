import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JogadorDAOJdbc implements JogadorDAO {

  private static final String COLUNAS =
    "id, apelido, horas_semana, partidas_semana, gasto_mensal, taxa_vitoria, perfil";

  private final Connection conexao;

  public JogadorDAOJdbc(Connection conexao) {
    this.conexao = conexao;
  }

  @Override
  public List<Jogador> listarTodos() {
    String sql = "SELECT " + COLUNAS + " FROM jogador ORDER BY id";
    List<Jogador> jogadores = new ArrayList<>();
    try (PreparedStatement stmt = conexao.prepareStatement(sql);
      ResultSet rs = stmt.executeQuery()) {
      while (rs.next()) {
        jogadores.add(montarJogador(rs));
      }
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao listar jogadores: " + e.getMessage(), e);
    }
    return jogadores;
  }

  @Override
  public Optional<Jogador> buscarPorId(int id) {
    String sql = "SELECT " + COLUNAS + " FROM jogador WHERE id = ?";
    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
      stmt.setInt(1, id);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return Optional.of(montarJogador(rs));
        }
        return Optional.empty();
      }
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao buscar jogador por id: " + e.getMessage(), e);
    }
  }

  @Override
  public List<Jogador> listarPorPerfil(Perfil perfil) {
    String sql = "SELECT " + COLUNAS + " FROM jogador WHERE perfil = ? ORDER BY id";
    List<Jogador> jogadores = new ArrayList<>();
    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
      stmt.setString(1, perfil.name());
      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          jogadores.add(montarJogador(rs));
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao listar jogadores por perfil: " + e.getMessage(), e);
    }
    return jogadores;
  }

  /** Converte a linha atual do ResultSet em um Jogador. */
  private Jogador montarJogador(ResultSet rs) throws SQLException {
    return new Jogador(
      rs.getInt("id"),
      rs.getString("apelido"),
      rs.getDouble("horas_semana"),
      rs.getInt("partidas_semana"),
      rs.getDouble("gasto_mensal"),
      rs.getDouble("taxa_vitoria"),
      Perfil.valueOf(rs.getString("perfil"))
    );
  }
}
