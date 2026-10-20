import java.util.List;
import java.util.Optional;

public interface JogadorDAO {
  List<Jogador> listarTodos();

  Optional<Jogador> buscarPorId(int id);

  List<Jogador> listarPorPerfil(Perfil perfil);
}
