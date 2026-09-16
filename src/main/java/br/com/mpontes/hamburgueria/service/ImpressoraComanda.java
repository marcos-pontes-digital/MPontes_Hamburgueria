package br.com.mpontes.hamburgueria.service;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ImpressoraComanda {

    private static final String IMPRESSORA =
            "EPSON_TM_T88V_USB";

    public static void imprimir(
            int pedidoId,
            String cliente,
            String tipo,
            String itens,
            double total
    ) throws Exception {

        StringBuilder comando =
                new StringBuilder();

        /*
         * ================================
         * INICIALIZA A IMPRESSORA
         * ================================
         */
        comando.append("\u001B\u0040");

        /*
         * ================================
         * FONTE PADRÃO MAIOR
         *
         * GS ! 0x01
         * Aumenta a altura da fonte.
         * ================================
         */
        comando.append("\u001D\u0021\u0001");

        /*
         * ================================
         * CENTRALIZA
         * ================================
         */
        comando.append("\u001B\u0061\u0001");

        /*
         * ================================
         * CABEÇALHO
         * ================================
         */

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

        /*
         * ================================
         * ALINHAMENTO À ESQUERDA
         * ================================
         */
        comando.append("\u001B\u0061\u0000");

        /*
         * ================================
         * DADOS DO PEDIDO
         * ================================
         */

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

        /*
         * ================================
         * ITENS
         * ================================
         */

        comando.append(itens);

        comando.append("--------------------------------\n");

        /*
         * ================================
         * TOTAL
         * ================================
         */

        // Negrito
        comando.append("\u001B\u0045\u0001");

        // Fonte 2x
        comando.append("\u001D\u0021\u0011");

        comando.append(String.format(
                "TOTAL: R$ %.2f\n",
                total
        ));

        // Volta para fonte normal ampliada
        comando.append("\u001D\u0021\u0001");

        comando.append("\n");

        /*
         * ================================
         * STATUS
         * ================================
         */

        comando.append("\u001B\u0061\u0001");

        comando.append("================================\n");

        comando.append("\u001D\u0021\u0001");

        comando.append("PEDIDO FINALIZADO\n");

        comando.append("\u001B\u0045\u0000");

        /*
         * ================================
         * AVANÇA O PAPEL
         * ================================
         */

        comando.append("\n");
        comando.append("\n");
        comando.append("\n");
        comando.append("\n");

        /*
         * ================================
         * CORTE DE PAPEL
         * ================================
         */
        comando.append("\u001D\u0056\u0000");

        /*
         * ================================
         * CONVERTE PARA BYTES
         * ================================
         */
        byte[] dados =
                comando.toString()
                        .getBytes(StandardCharsets.UTF_8);

        /*
         * ================================
         * ENVIA PARA O CUPS
         * ================================
         */
        Process processo =
                new ProcessBuilder(
                        "lp",
                        "-d",
                        IMPRESSORA
                ).start();

        try (OutputStream entrada =
                     processo.getOutputStream()) {

            entrada.write(dados);
            entrada.flush();
        }

        /*
         * ================================
         * AGUARDA O CUPS
         * ================================
         */
        int resultado =
                processo.waitFor();

        if (resultado != 0) {

            throw new RuntimeException(
                    "Erro ao enviar a comanda para a impressora."
            );
        }
    }
}
