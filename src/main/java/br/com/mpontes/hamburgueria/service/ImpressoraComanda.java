package br.com.mpontes.hamburgueria.service;

import br.com.mpontes.hamburgueria.dao.ConfiguracaoDAO;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ImpressoraComanda {

    public static void imprimir(
            int pedidoId,
            String cliente,
            String tipo,
            String itens,
            double total
    ) throws Exception {

        ConfiguracaoDAO configuracaoDAO =
                new ConfiguracaoDAO();

        String impressora =
                configuracaoDAO.buscar("impressora.cozinha");

        if (impressora == null ||
                impressora.isBlank()) {

            throw new RuntimeException(
                    "Impressora da cozinha não configurada."
            );
        }

        StringBuilder comando =
                new StringBuilder();

        // Inicializa a impressora
        comando.append("\u001B\u0040");

        // Fonte padrão maior
        comando.append("\u001D\u0021\u0001");

        // Centraliza
        comando.append("\u001B\u0061\u0001");

        // Negrito ligado
        comando.append("\u001B\u0045\u0001");

        // Fonte maior 2x
        comando.append("\u001D\u0021\u0011");

        comando.append("MPONTES\n");
        comando.append("HAMBURGUERIA\n");

        // Volta para tamanho normal ampliado
        comando.append("\u001D\u0021\u0001");

        comando.append("================================\n");

        // Negrito desligado
        comando.append("\u001B\u0045\u0000");

        // Alinhamento à esquerda
        comando.append("\u001B\u0061\u0000");

        // Pedido em destaque
        comando.append("\u001B\u0045\u0001");

        comando.append(String.format(
                "PEDIDO: #%d\n",
                pedidoId
        ));

        comando.append("\u001B\u0045\u0000");

        comando.append(String.format(
                "CLIENTE: %s\n",
                cliente
        ));

        comando.append(String.format(
                "TIPO: %s\n",
                tipo
        ));

        comando.append("--------------------------------\n");

        comando.append(itens);

        comando.append("--------------------------------\n");

        // Total
        comando.append("\u001B\u0045\u0001");
        comando.append("\u001D\u0021\u0011");

        comando.append(String.format(
                "TOTAL: R$ %.2f\n",
                total
        ));

        comando.append("\u001D\u0021\u0001");

        comando.append("\n");

        // Status
        comando.append("\u001B\u0061\u0001");
        comando.append("================================\n");
        comando.append("\u001D\u0021\u0001");
        comando.append("PEDIDO FINALIZADO\n");
        comando.append("\u001B\u0045\u0000");

        // Avança papel
        comando.append("\n");
        comando.append("\n");
        comando.append("\n");
        comando.append("\n");

        // Corte
        comando.append("\u001D\u0056\u0000");

        byte[] dados =
                comando.toString()
                        .getBytes(StandardCharsets.UTF_8);

        Process processo =
                new ProcessBuilder(
                        "lp",
                        "-d",
                        impressora
                ).start();

        try (OutputStream entrada =
                     processo.getOutputStream()) {

            entrada.write(dados);
            entrada.flush();
        }

        int resultado =
                processo.waitFor();

        if (resultado != 0) {

            throw new RuntimeException(
                    "Erro ao enviar a comanda para a impressora: "
                            + impressora
            );
        }
    }
}
