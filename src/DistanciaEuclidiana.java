/** Implementa a distância euclidiana entre dois vetores. */
public final class DistanciaEuclidiana implements MedidaDeDistancia {

  @Override
  public double calcular(double[] a, double[] b) {
    validarVetores(a, b);

    double soma = 0.0;
    for (int i = 0; i < a.length; i++) {
      double diferenca = a[i] - b[i];
      soma += diferenca * diferenca;
    }

    return Math.sqrt(soma);
  }

  private static void validarVetores(double[] a, double[] b) {
    if (a == null || b == null) {
      throw new IllegalArgumentException("Os vetores não podem ser nulos.");
    }
    if (a.length != b.length) {
      throw new IllegalArgumentException(
          "Os vetores devem possuir a mesma quantidade de características.");
    }
  }
}
