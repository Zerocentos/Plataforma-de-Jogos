import java.util.ArrayList;
import java.util.List;

// Normalizador min-max ajustado a partir de um conjunto de jogadores.
public final class Normalizador {

  private final double[] minimos;
  private final double[] maximos;

  private Normalizador(double[] minimos, double[] maximos) {
    this.minimos = minimos;
    this.maximos = maximos;
  }

  /**
   * Calcula mínimo e máximo de cada característica somente com os dados
   * recebidos. Na Parte B, passe aqui apenas os jogadores de treino.
   */
  public static Normalizador ajustado(List<Jogador> jogadoresDeTreino) {
    assert jogadoresDeTreino != null;
    assert !jogadoresDeTreino.isEmpty();

    double[] primeiro = jogadoresDeTreino.get(0).getCaracteristicas();
    double[] minimos = primeiro.clone();
    double[] maximos = primeiro.clone();

    for (int i = 1; i < jogadoresDeTreino.size(); i++) {
      double[] caracteristicas = jogadoresDeTreino.get(i).getCaracteristicas();
      for (int j = 0; j < caracteristicas.length; j++) {
        minimos[j] = Math.min(minimos[j], caracteristicas[j]);
        maximos[j] = Math.max(maximos[j], caracteristicas[j]);
      }
    }

    return new Normalizador(minimos, maximos);
  }

  // Normaliza as características de um jogador usando os limites ajustados.
  public double[] transformar(Jogador jogador) {
    assert jogador != null;
    return transformar(jogador.getCaracteristicas());
  }

  // Normaliza um vetor na ordem das características de Jogador.
  public double[] transformar(double[] caracteristicas) {
    assert caracteristicas != null;
    assert caracteristicas.length == minimos.length;

    double[] normalizadas = new double[caracteristicas.length];
    for (int i = 0; i < caracteristicas.length; i++) {
      double intervalo = maximos[i] - minimos[i];
      // Uma característica constante no treino não contém variação para escalar.
      normalizadas[i] = intervalo == 0.0
          ? 0.0
          : (caracteristicas[i] - minimos[i]) / intervalo;
    }

    return normalizadas;
  }

  // Devolve os vetores normalizados.
  public List<double[]> transformarTodos(List<Jogador> jogadores) {
    assert jogadores != null;

    List<double[]> resultado = new ArrayList<>();
    for (Jogador jogador : jogadores) {
      resultado.add(transformar(jogador));
    }

    return resultado;
  }

  public double[] minimos() { return minimos.clone(); }

  public double[] maximos() { return maximos.clone(); }
}
