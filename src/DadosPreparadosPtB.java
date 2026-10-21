import java.util.ArrayList;
import java.util.List;

/**
 * Partição e características normalizadas que são consumidas pelos
 * próximos passos da Parte B.
 */
public final class DadosPreparadosPtB {

  private final DivisaoTreinoTeste divisao;
  private final Normalizador normalizador;
  private final List<double[]> treinoNormalizado;
  private final List<double[]> testeNormalizado;

  // Prepara a divisão fixa 80/20 e ajusta o min-max exclusivamente no treino.
  public DadosPreparadosPtB(List<Jogador> jogadores) {
    assert jogadores != null;

    DivisaoTreinoTeste divisao = DivisaoTreinoTeste.dividida(jogadores);
    Normalizador normalizador = Normalizador.ajustado(divisao.getTreino());
    List<double[]> treinoNormalizado = normalizador.transformarTodos(divisao.getTreino());
    List<double[]> testeNormalizado = normalizador.transformarTodos(divisao.getTeste());

    this.divisao = divisao;
    this.normalizador = normalizador;
    this.treinoNormalizado = copiarMatriz(treinoNormalizado);
    this.testeNormalizado = copiarMatriz(testeNormalizado);
  }

  public DivisaoTreinoTeste getDivisao() {
    return divisao;
  }

  public Normalizador getNormalizador() {
    return normalizador;
  }

  public List<double[]> getTreinoNormalizado() {
    return copiarMatriz(treinoNormalizado);
  }

  public List<double[]> getTesteNormalizado() {
    return copiarMatriz(testeNormalizado);
  }

  private static List<double[]> copiarMatriz(List<double[]> matriz) {
    List<double[]> copia = new ArrayList<>();
    for (double[] vetor : matriz) {
      copia.add(vetor.clone());
    }

    return copia;
  }
}
