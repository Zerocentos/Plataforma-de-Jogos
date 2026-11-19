import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/** Relatório de avaliação do K-Means para a Parte C. */
public final class RelatorioPtC {

    private static final int MAX_K = 6;
    private static final int EXECUCOES_POR_K = 10;
    private static final int K_RELATORIO = 3;

    private RelatorioPtC() { }

    public static void imprimir(List<Jogador> jogadores) {
        if (jogadores == null || jogadores.isEmpty()) {
            throw new IllegalArgumentException("A Parte C precisa de jogadores para agrupar.");
        }

        System.out.println();
        System.out.println("Parte C - K-Means");
        System.out.println("-".repeat(24));

        Normalizador normalizador = Normalizador.ajustado(jogadores);

        Map<Jogador, Ponto> dadosNormalizados = converterValores(
            jogadores, normalizador.transformarTodos(jogadores));

        Map<Jogador, Ponto> dadosOriginais = converterValores(
            jogadores, obterValoresOriginais(jogadores));

        double[] inerciaNormalizada = calcularInercias(dadosNormalizados, 88L);
        double[] inerciaOriginal = calcularInercias(dadosOriginais, 88L);

        System.out.println("Inércia por K (menor valor entre " + EXECUCOES_POR_K
            + " inicializações, em cada escala):");

        for (int i = 0; i < inerciaNormalizada.length; i++) {
            System.out.printf("K=%d | normalizados: %.4f | escala original: %.4f%n",
                i + 1, inerciaNormalizada[i], inerciaOriginal[i]);
        }

        salvarGraficoCotovelo(inerciaNormalizada, inerciaOriginal);

        Resultado agrupamentoNormalizado = melhorAgrupamento(
            K_RELATORIO, dadosNormalizados, 88L + K_RELATORIO);

        Resultado agrupamentoOriginal = melhorAgrupamento(
            K_RELATORIO, dadosOriginais, 88L + K_RELATORIO);

        imprimirResultado("K=3 — dados normalizados", jogadores,
            agrupamentoNormalizado, normalizador, true);

        imprimirResultado("K=3 — dados sem normalização", jogadores,
            agrupamentoOriginal, normalizador, false);
    }

    private static double[] calcularInercias(Map<Jogador, Ponto> dados, long sementeBase) {
        double[][] execucoes = new double[MAX_K][EXECUCOES_POR_K];

        for (int k = 1; k <= MAX_K; k++) {
            for (int tentativa = 0; tentativa < EXECUCOES_POR_K; tentativa++) {
                KMeans modelo = new KMeans(k, dados, sementeBase + k * 100L + tentativa);
                modelo.agrupar();
                execucoes[k - 1][tentativa] = modelo.calcularInercia();
            }
        }

        return MetodoCotovelo.menoresInercias(execucoes);
    }

    private static Resultado melhorAgrupamento(
        int k, Map<Jogador, Ponto> dados, long sementeBase) {
        Resultado melhor = null;
        for (int tentativa = 0; tentativa < EXECUCOES_POR_K; tentativa++) {
            KMeans modelo = new KMeans(k, dados, sementeBase + tentativa);
            Map<Jogador, Integer> grupos = modelo.agrupar();
            double inercia = modelo.calcularInercia();
            if (melhor == null || inercia < melhor.inercia) {
                melhor = new Resultado(grupos, modelo.getCentroides(), inercia);
            }
        }
        return melhor;
    }

    private static void imprimirResultado(String titulo, List<Jogador> jogadores,
        Resultado resultado, Normalizador normalizador, boolean converterCentroides) {
        System.out.println();
        System.out.println(titulo);
        System.out.printf(Locale.US, "Inércia: %.4f%n", resultado.inercia);

        int[] tamanhos = new int[K_RELATORIO];
        @SuppressWarnings("unchecked")
        EnumMap<Perfil, Integer>[] tabela = new EnumMap[K_RELATORIO];
        for (int grupo = 0; grupo < K_RELATORIO; grupo++) {
            tabela[grupo] = new EnumMap<>(Perfil.class);
            for (Perfil perfil : Perfil.values()) {
                tabela[grupo].put(perfil, 0);
            }
        }

        for (Jogador jogador : jogadores) {
            int grupo = resultado.grupoPorJogador.get(jogador);
            tamanhos[grupo]++;
            tabela[grupo].put(jogador.getPerfil(), tabela[grupo].get(jogador.getPerfil()) + 1);
        }

        System.out.println("Tamanho dos grupos:");
        for (int grupo = 0; grupo < K_RELATORIO; grupo++) {
            System.out.printf("  Grupo %d: %d jogadores%n", grupo, tamanhos[grupo]);
        }

        System.out.println("Centroides na escala original das características:");
        for (int grupo = 0; grupo < K_RELATORIO; grupo++) {
            Ponto centroide = resultado.centroides.get(grupo);
            System.out.printf("  Grupo %d: ", grupo);
            for (NomeCaracteristica caracteristica : NomeCaracteristica.values()) {
                double valor = centroide.getValor(caracteristica);
                if (converterCentroides) {
                    int indice = caracteristica.ordinal();
                    valor = normalizador.minimos()[indice]
                        + valor * (normalizador.maximos()[indice] - normalizador.minimos()[indice]);
                }
                System.out.printf(Locale.US, "%s=%.2f  ", caracteristica.getDescricao(), valor);
            }
            System.out.println();
        }

        System.out.println("Tabela de grupos por perfil real:");
        System.out.printf("  %-12s", "Grupo");
        for (Perfil perfil : Perfil.values()) {
            System.out.printf("%12s", perfil.name());
        }
        System.out.println();
        int corretos = 0;
        for (int grupo = 0; grupo < K_RELATORIO; grupo++) {
            System.out.printf("  %-12d", grupo);
            int maiorContagem = 0;
            for (Perfil perfil : Perfil.values()) {
                int quantidade = tabela[grupo].get(perfil);
                System.out.printf("%12d", quantidade);
                maiorContagem = Math.max(maiorContagem, quantidade);
            }
            corretos += maiorContagem;
            System.out.println();
        }
        double pureza = (double) corretos / jogadores.size();

        System.out.printf(Locale.US, "Pureza: %d/%d = %.4f (%.2f%%)%n",
            corretos, jogadores.size(), pureza, pureza * 100.0);
    }

    private static Map<Jogador, Ponto> converterValores(
        List<Jogador> jogadores, List<double[]> valores) {
        Map<Jogador, Ponto> resultado = new HashMap<>();
        NomeCaracteristica[] caracteristicas = NomeCaracteristica.values();

        for (int i = 0; i < jogadores.size(); i++) {
            Map<NomeCaracteristica, Double> coordenadas = new HashMap<>();

            for (int j = 0; j < caracteristicas.length; j++) {
                coordenadas.put(caracteristicas[j], valores.get(i)[j]);
            }

            resultado.put(jogadores.get(i), new Ponto(coordenadas));
        }

        return resultado;
    }

    private static List<double[]> obterValoresOriginais(List<Jogador> jogadores) {
        return jogadores.stream().map(Jogador::getCaracteristicas).toList();
    }

    private static void salvarGraficoCotovelo(double[] normalizados, double[] originais) {
        try {
            Path pasta = Path.of("saida");
            Files.createDirectories(pasta);
            Path arquivoNormalizado = pasta.resolve("cotovelo-normalizado.svg");
            Path arquivoOriginal = pasta.resolve("cotovelo-sem-normalizacao.svg");
            Files.writeString(arquivoNormalizado,
                criarSvg(normalizados, "Dados normalizados", "#1769aa"), StandardCharsets.UTF_8);
            Files.writeString(arquivoOriginal,
                criarSvg(originais, "Dados sem normalização", "#d1495b"), StandardCharsets.UTF_8);
            System.out.println("Gráfico normalizado salvo em: " + arquivoNormalizado.toAbsolutePath());
            System.out.println("Gráfico sem normalização salvo em: " + arquivoOriginal.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("Não foi possível salvar o gráfico do cotovelo: " + e.getMessage());
        }
    }

    private static String criarSvg(double[] valores, String titulo, String cor) {
        final int largura = 850;
        final int altura = 480;
        final int esquerda = 90;
        final int topo = 55;
        final int graficoLargura = 690;
        final int graficoAltura = 330;
        double maximo = 0.0;
        for (double valor : valores) maximo = Math.max(maximo, valor);
        if (maximo == 0.0) maximo = 1.0;

        StringBuilder svg = new StringBuilder();
        svg.append("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"").append(largura)
            .append("\" height=\"").append(altura).append("\" viewBox=\"0 0 ")
            .append(largura).append(' ').append(altura).append("\">")
            .append("<rect width=\"100%\" height=\"100%\" fill=\"white\"/>")
            .append("<text x=\"425\" y=\"28\" text-anchor=\"middle\" font-family=\"Arial\" font-size=\"20\">Método do cotovelo — ")
            .append(titulo).append("</text>")
            .append("<line x1=\"").append(esquerda).append("\" y1=\"").append(topo + graficoAltura)
            .append("\" x2=\"").append(esquerda + graficoLargura).append("\" y2=\"")
            .append(topo + graficoAltura).append("\" stroke=\"black\"/>")
            .append("<line x1=\"").append(esquerda).append("\" y1=\"").append(topo)
            .append("\" x2=\"").append(esquerda).append("\" y2=\"").append(topo + graficoAltura)
            .append("\" stroke=\"black\"/>")
            .append("<text x=\"425\" y=\"455\" text-anchor=\"middle\" font-family=\"Arial\">Número de grupos (K)</text>")
            .append("<text transform=\"translate(22 220) rotate(-90)\" text-anchor=\"middle\" font-family=\"Arial\">Inércia (soma dos quadrados)</text>");

        for (int tick = 0; tick <= 5; tick++) {
            int y = topo + graficoAltura - tick * graficoAltura / 5;
            double valor = maximo * tick / 5.0;
            svg.append("<line x1=\"").append(esquerda).append("\" y1=\"").append(y)
                .append("\" x2=\"").append(esquerda + graficoLargura).append("\" y2=\"")
                .append(y).append("\" stroke=\"#dddddd\"/>")
                .append("<text x=\"").append(esquerda - 10).append("\" y=\"").append(y + 4)
                .append("\" text-anchor=\"end\" font-family=\"Arial\" font-size=\"11\">")
                .append(String.format(Locale.US, "%.2f", valor)).append("</text>");
        }
        for (int i = 0; i < valores.length; i++) {
            int x = esquerda + i * graficoLargura / Math.max(1, valores.length - 1);
            svg.append("<text x=\"").append(x).append("\" y=\"").append(topo + graficoAltura + 20)
                .append("\" text-anchor=\"middle\" font-family=\"Arial\">K=").append(i + 1).append("</text>");
        }
        svg.append("<polyline fill=\"none\" stroke=\"").append(cor)
            .append("\" stroke-width=\"3\" points=\"");
        for (int i = 0; i < valores.length; i++) {
            int x = esquerda + i * graficoLargura / Math.max(1, valores.length - 1);
            int y = topo + graficoAltura - (int) Math.round(valores[i] / maximo * graficoAltura);
            svg.append(x).append(',').append(y).append(' ');
        }
        svg.append("\"/>");
        for (int i = 0; i < valores.length; i++) {
            int x = esquerda + i * graficoLargura / Math.max(1, valores.length - 1);
            int y = topo + graficoAltura - (int) Math.round(valores[i] / maximo * graficoAltura);
            svg.append("<circle cx=\"").append(x).append("\" cy=\"").append(y)
                .append("\" r=\"5\" fill=\"").append(cor).append("\"/>");
        }
        svg.append("</svg>");
        return svg.toString();
    }

    private static final class Resultado {
        private final Map<Jogador, Integer> grupoPorJogador;
        private final List<Ponto> centroides;
        private final double inercia;

        private Resultado(Map<Jogador, Integer> grupos, List<Ponto> centroides, double inercia) {
            this.grupoPorJogador = grupos;
            this.centroides = centroides;
            this.inercia = inercia;
        }
    }
}
