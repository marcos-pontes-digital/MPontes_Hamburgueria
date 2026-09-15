package br.com.mpontes.hamburgueria.dao;

import br.com.mpontes.hamburgueria.database.Database;
import br.com.mpontes.hamburgueria.model.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    // 1. Cadastrar produto
    public void cadastrar(Produto produto) {

        String sql = """
            INSERT INTO produtos
            (nome, descricao, categoria, preco, ativo)
            VALUES (?, ?, ?, ?, ?)
            """;

        try (Connection conexao = Database.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setString(3, produto.getCategoria());
            stmt.setDouble(4, produto.getPreco());
            stmt.setBoolean(5, produto.isAtivo());

            stmt.executeUpdate();

            System.out.println("Produto cadastrado com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao cadastrar produto:");
            e.printStackTrace();
        }
    }

    // 2. Listar produtos
    public List<Produto> listar() {

        List<Produto> produtos = new ArrayList<>();

        String sql = """
            SELECT id, nome, descricao, categoria, preco, ativo
            FROM produtos
            ORDER BY nome
            """;

        try (Connection conexao = Database.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Produto produto = new Produto(
                    rs.getString("nome"),
                    rs.getString("descricao"),
                    rs.getString("categoria"),
                    rs.getDouble("preco"),
                    rs.getBoolean("ativo")
                );

                produto.setId(rs.getInt("id"));

                produtos.add(produto);
            }

        } catch (Exception e) {
            System.err.println("Erro ao listar produtos:");
            e.printStackTrace();
        }

        return produtos;
    }

    // 3. Buscar produto por ID
    public Produto buscarPorId(int id) {

        String sql = """
            SELECT id, nome, descricao, categoria, preco, ativo
            FROM produtos
            WHERE id = ?
            """;

        try (Connection conexao = Database.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    Produto produto = new Produto(
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getString("categoria"),
                        rs.getDouble("preco"),
                        rs.getBoolean("ativo")
                    );

                    produto.setId(rs.getInt("id"));

                    return produto;
                }
            }

        } catch (Exception e) {
            System.err.println("Erro ao buscar produto:");
            e.printStackTrace();
        }

        return null;
    }

    // 4. Alterar produto
    public void alterar(Produto produto) {

        String sql = """
            UPDATE produtos
            SET nome = ?,
                descricao = ?,
                categoria = ?,
                preco = ?,
                ativo = ?
            WHERE id = ?
            """;

        try (Connection conexao = Database.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setString(3, produto.getCategoria());
            stmt.setDouble(4, produto.getPreco());
            stmt.setBoolean(5, produto.isAtivo());
            stmt.setInt(6, produto.getId());

            stmt.executeUpdate();

            System.out.println("Produto alterado com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao alterar produto:");
            e.printStackTrace();
        }
    }

    // 5. Ativar/desativar produto
    public void alterarStatus(int id, boolean ativo) {

        String sql = """
            UPDATE produtos
            SET ativo = ?
            WHERE id = ?
            """;

        try (Connection conexao = Database.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setBoolean(1, ativo);
            stmt.setInt(2, id);

            stmt.executeUpdate();

            System.out.println(
                ativo
                ? "Produto ativado com sucesso!"
                : "Produto desativado com sucesso!"
            );

        } catch (Exception e) {
            System.err.println("Erro ao alterar status do produto:");
            e.printStackTrace();
        }
    }

    // 6. Excluir produto
    public void excluir(int id) {

        String sql = """
            DELETE FROM produtos
            WHERE id = ?
            """;

        try (Connection conexao = Database.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            stmt.executeUpdate();

            System.out.println("Produto excluído com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao excluir produto:");
            e.printStackTrace();
        }
    }
}
