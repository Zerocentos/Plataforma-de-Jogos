// Mantém, para cada K de 1 a 6, a menor inércia entre as execuções.
public final class MetodoCotovelo {

  private static final int QUANTIDADE_K = 6;

  public static double[] menoresInercias(double[][] inerciasPorK) {
    assert inerciasPorK.length == QUANTIDADE_K;

    double[] menores = new double[QUANTIDADE_K];
    for (int i = 0; i < QUANTIDADE_K; i++) {
      double menor = inerciasPorK[i][0];
      for (double inercia : inerciasPorK[i]) {
        if(inercia < menor) {
          menor = inercia;
        }
      }
      menores[i] = menor;
    }

    return menores;
  }

  private MetodoCotovelo() { }
}
