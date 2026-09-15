package br.com.mpontes.hamburgueria.view;

import br.com.mpontes.hamburgueria.dao.ProdutoDAO;
import br.com.mpontes.hamburgueria.model.Produto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaProdutos extends JFrame {

    private JTextField txtNome;
    private JTextField txtDescricao;
    private JTextField txtCategoria;
    private JTextField txtPreco;

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    private ProdutoDAO produtoDAO;

    private int produtoSelecionadoId = -1;

    public TelaProdutos() {

        produtoDAO = new ProdutoDAO();

        setTitle("MPontes Hamburgueria - Produtos");
        setSize(950, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        criarInterface();
        carregarProdutos();
    }

    private void criarInterface() {

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));

        painelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        // ==========================
        // TÍTULO
        // ==========================

        JLabel titulo = new JLabel("Cadastro de Produtos");

        titulo.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        painelPrincipal.add(
                titulo,
                BorderLayout.NORTH
        );

        // ==========================
        // FORMULÁRIO
        // ==========================

        JPanel painelFormulario = new JPanel(
                new GridLayout(4, 2, 10, 10)
        );

        txtNome = new JTextField();
        txtDescricao = new JTextField();
        txtCategoria = new JTextField();
        txtPreco = new JTextField();

        painelFormulario.add(new JLabel("Nome:"));
        painelFormulario.add(txtNome);

        painelFormulario.add(new JLabel("Descrição:"));
        painelFormulario.add(txtDescricao);

        painelFormulario.add(new JLabel("Categoria:"));
        painelFormulario.add(txtCategoria);

        painelFormulario.add(new JLabel("Preço:"));
        painelFormulario.add(txtPreco);

        // ==========================
        // BOTÕES
        // ==========================

        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnAlterar = new JButton("Alterar");
        JButton btnStatus = new JButton("Ativar/Desativar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnLimpar = new JButton("Limpar");

        JPanel painelBotoes = new JPanel();

        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnAlterar);
        painelBotoes.add(btnStatus);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnLimpar);

        JPanel painelSuperior = new JPanel(
                new BorderLayout(10, 10)
        );

        painelSuperior.add(
                painelFormulario,
                BorderLayout.CENTER
        );

        painelSuperior.add(
                painelBotoes,
                BorderLayout.SOUTH
        );

        // ==========================
        // TABELA
        // ==========================

        modeloTabela = new DefaultTableModel(
                new Object[]{
                        "ID",
                        "Nome",
                        "Categoria",
                        "Preço",
                        "Status"
                }, 0
        ) {

            @Override
            public boolean isCellEditable(
                    int row,
                    int column
            ) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela);

        tabela.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        tabela.setRowHeight(25);

        JScrollPane scrollTabela =
                new JScrollPane(tabela);

        JPanel painelCentro =
                new JPanel(new BorderLayout(10, 10));

        painelCentro.add(
                painelSuperior,
                BorderLayout.NORTH
        );

        painelCentro.add(
                scrollTabela,
                BorderLayout.CENTER
        );

        painelPrincipal.add(
                painelCentro,
                BorderLayout.CENTER
        );

        setContentPane(painelPrincipal);

        // ==========================
        // EVENTOS
        // ==========================

        btnCadastrar.addActionListener(
                e -> cadastrarProduto()
        );

        btnAlterar.addActionListener(
                e -> alterarProduto()
        );

        btnStatus.addActionListener(
                e -> alterarStatus()
        );

        btnExcluir.addActionListener(
                e -> excluirProduto()
        );

        btnLimpar.addActionListener(
                e -> limparCampos()
        );

        tabela.getSelectionModel()
                .addListSelectionListener(
                        e -> selecionarProduto()
                );
    }

    // ==================================================
    // CADASTRAR
    // ==================================================

    private void cadastrarProduto() {

        String nome = txtNome.getText().trim();
        String descricao = txtDescricao.getText().trim();
        String categoria = txtCategoria.getText().trim();
        String precoTexto = txtPreco.getText().trim();

        if (nome.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Informe o nome do produto."
            );

            txtNome.requestFocus();
            return;
        }

        if (categoria.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Informe a categoria."
            );

            txtCategoria.requestFocus();
            return;
        }

        if (precoTexto.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Informe o preço."
            );

            txtPreco.requestFocus();
            return;
        }

        try {

            double preco = Double.parseDouble(
                    precoTexto.replace(",", ".")
            );

            if (preco <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "O preço deve ser maior que zero."
                );

                return;
            }

            Produto produto = new Produto(
                    nome,
                    descricao,
                    categoria,
                    preco,
                    true
            );

            produtoDAO.cadastrar(produto);

            JOptionPane.showMessageDialog(
                    this,
                    "Produto cadastrado com sucesso!"
            );

            limparCampos();
            carregarProdutos();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Preço inválido.\n\nExemplo: 29,90"
            );

            txtPreco.requestFocus();
        }
    }

    // ==================================================
    // ALTERAR
    // ==================================================

    private void alterarProduto() {

        if (produtoSelecionadoId == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecione um produto na tabela."
            );

            return;
        }

        String nome = txtNome.getText().trim();
        String descricao = txtDescricao.getText().trim();
        String categoria = txtCategoria.getText().trim();
        String precoTexto = txtPreco.getText().trim();

        if (nome.isEmpty()
                || categoria.isEmpty()
                || precoTexto.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Preencha nome, categoria e preço."
            );

            return;
        }

        try {

            double preco = Double.parseDouble(
                    precoTexto.replace(",", ".")
            );

            Produto produto =
                    produtoDAO.buscarPorId(
                            produtoSelecionadoId
                    );

            if (produto == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Produto não encontrado."
                );

                return;
            }

            produto.setNome(nome);
            produto.setDescricao(descricao);
            produto.setCategoria(categoria);
            produto.setPreco(preco);

            produtoDAO.alterar(produto);

            JOptionPane.showMessageDialog(
                    this,
                    "Produto alterado com sucesso!"
            );

            limparCampos();
            carregarProdutos();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Preço inválido."
            );
        }
    }

    // ==================================================
    // ATIVAR / DESATIVAR
    // ==================================================

    private void alterarStatus() {

        if (produtoSelecionadoId == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecione um produto na tabela."
            );

            return;
        }

        Produto produto =
                produtoDAO.buscarPorId(
                        produtoSelecionadoId
                );

        if (produto == null) {
            return;
        }

        boolean novoStatus =
                !produto.isAtivo();

        produtoDAO.alterarStatus(
                produtoSelecionadoId,
                novoStatus
        );

        carregarProdutos();
        limparCampos();

        JOptionPane.showMessageDialog(
                this,
                novoStatus
                        ? "Produto ativado!"
                        : "Produto desativado!"
        );
    }

    // ==================================================
    // EXCLUIR
    // ==================================================

    private void excluirProduto() {

        if (produtoSelecionadoId == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecione um produto na tabela."
            );

            return;
        }

        Produto produto =
                produtoDAO.buscarPorId(
                        produtoSelecionadoId
                );

        if (produto == null) {
            return;
        }

        int resposta =
                JOptionPane.showConfirmDialog(
                        this,
                        "Deseja realmente excluir o produto?\n\n"
                                + produto.getNome(),
                        "Confirmar exclusão",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (resposta ==
                JOptionPane.YES_OPTION) {

            produtoDAO.excluir(
                    produtoSelecionadoId
            );

            carregarProdutos();
            limparCampos();

            JOptionPane.showMessageDialog(
                    this,
                    "Produto excluído!"
            );
        }
    }

    // ==================================================
    // SELECIONAR PRODUTO
    // ==================================================

    private void selecionarProduto() {

        int linha =
                tabela.getSelectedRow();

        if (linha == -1) {
            return;
        }

        produtoSelecionadoId =
                (int) modeloTabela.getValueAt(
                        linha,
                        0
                );

        Produto produto =
                produtoDAO.buscarPorId(
                        produtoSelecionadoId
                );

        if (produto != null) {

            txtNome.setText(
                    produto.getNome()
            );

            txtDescricao.setText(
                    produto.getDescricao()
            );

            txtCategoria.setText(
                    produto.getCategoria()
            );

            txtPreco.setText(
                    String.format(
                            "%.2f",
                            produto.getPreco()
                    )
            );
        }
    }

    // ==================================================
    // CARREGAR PRODUTOS
    // ==================================================

    private void carregarProdutos() {

        modeloTabela.setRowCount(0);

        for (Produto produto :
                produtoDAO.listar()) {

            modeloTabela.addRow(
                    new Object[]{
                            produto.getId(),
                            produto.getNome(),
                            produto.getCategoria(),
                            String.format(
                                    "R$ %.2f",
                                    produto.getPreco()
                            ),
                            produto.isAtivo()
                                    ? "ATIVO"
                                    : "INATIVO"
                    }
            );
        }
    }

    // ==================================================
    // LIMPAR
    // ==================================================

    private void limparCampos() {

        txtNome.setText("");
        txtDescricao.setText("");
        txtCategoria.setText("");
        txtPreco.setText("");

        produtoSelecionadoId = -1;

        tabela.clearSelection();

        txtNome.requestFocus();
    }
}
