/** Implementa a distância Manhattan entre dois vetores. */
public final class DistanciaManhattan implements MedidaDeDistancia {

  @Override
  public double calcular(double[] a, double[] b) {
    validarVetores(a, b);

    double soma = 0.0;
    for (int i = 0; i < a.length; i++) {
      soma += Math.abs(a[i] - b[i]);
    }

    return soma;
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
