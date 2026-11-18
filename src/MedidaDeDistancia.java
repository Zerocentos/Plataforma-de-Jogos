/**
 * Estratégia para calcular a distância entre duas pessoas jogadoras.
 * O KNN depende desta interface, permitindo trocar a métrica sem alterar
 * sua implementação.
 */
public interface MedidaDeDistancia {

  /**
   * Calcula a distância entre dois vetores de características.
   * Os vetores devem possuir o mesmo tamanho.
   */
  double calcular(double[] a, double[] b);
}
