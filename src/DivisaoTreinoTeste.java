import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Divisão reproduzível dos jogadores em conjuntos de treino e teste.
 * O conjunto de treino recebe 80% dos dados; o restante vai para teste.
 */
public final class DivisaoTreinoTeste {

  public static final long SEMENTE_PADRAO = 67; // SIX SEVEN HEITOR MUAHDAHSJD

  private final List<Jogador> treino;
  private final List<Jogador> teste;

  private DivisaoTreinoTeste(List<Jogador> treino, List<Jogador> teste) {
    this.treino = List.copyOf(treino);
    this.teste = List.copyOf(teste);
  }

  // Cria a divisão 80/20 usando a semente fixa padrão.
  public static DivisaoTreinoTeste dividida(List<Jogador> jogadores) {
    return dividida(jogadores, SEMENTE_PADRAO);
  }

  // Cria a divisão 80/20 usando a semente recebida.
  public static DivisaoTreinoTeste dividida(List<Jogador> jogadores, long semente) {
    assert jogadores != null;
    assert jogadores.size() >= 2;

    List<Jogador> embaralhados = new ArrayList<>(jogadores);
    Collections.shuffle(embaralhados, new Random(semente));

    int quantidadeTreino = embaralhados.size() * 8 / 10;
    quantidadeTreino = Math.max(1, Math.min(embaralhados.size() - 1, quantidadeTreino));

    List<Jogador> treino = embaralhados.subList(0, quantidadeTreino);
    List<Jogador> teste = embaralhados.subList(quantidadeTreino, embaralhados.size());
    return new DivisaoTreinoTeste(treino, teste);
  }

  public List<Jogador> getTreino() {
    return treino;
  }

  public List<Jogador> getTeste() {
    return teste;
  }
}
