package br.com.mpontes.hamburgueria;

import br.com.mpontes.hamburgueria.database.DatabaseSetup;
import br.com.mpontes.hamburgueria.view.TelaPedidos;
import br.com.mpontes.hamburgueria.view.TelaProdutos;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main() {

        setTitle("MPontes Hamburgueria");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        criarInterface();
    }

    private void criarInterface() {

        JPanel painel = new JPanel(
                new BorderLayout(20, 20)
        );

        painel.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 30, 30, 30
                )
        );

        JLabel titulo = new JLabel(
                "MPontes Hamburgueria",
                SwingConstants.CENTER
        );

        titulo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        painel.add(
                titulo,
                BorderLayout.NORTH
        );

        JPanel botoes = new JPanel(
                new GridLayout(1, 2, 20, 20)
        );

        JButton btnProdutos = new JButton(
                "Produtos"
        );

        btnProdutos.setFont(
                new Font("Arial", Font.BOLD, 18)
        );

        btnProdutos.addActionListener(e -> {

            TelaProdutos tela = new TelaProdutos();

            tela.setVisible(true);
        });

        JButton btnPedidos = new JButton(
                "Novo Pedido"
        );

        btnPedidos.setFont(
                new Font("Arial", Font.BOLD, 18)
        );

        btnPedidos.addActionListener(e -> {

            TelaPedidos tela = new TelaPedidos();

            tela.setVisible(true);
        });

        botoes.add(btnProdutos);
        botoes.add(btnPedidos);

        painel.add(
                botoes,
                BorderLayout.CENTER
        );

        JLabel rodape = new JLabel(
                "Sistema de Gestão - MPontes Digital",
                SwingConstants.CENTER
        );

        painel.add(
                rodape,
                BorderLayout.SOUTH
        );

        setContentPane(painel);
    }

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("     MPontes Hamburgueria");
        System.out.println("=================================");

        DatabaseSetup.criarTabelas();

        SwingUtilities.invokeLater(() -> {

            Main telaPrincipal = new Main();

            telaPrincipal.setVisible(true);
        });
    }
}
