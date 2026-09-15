package br.com.mpontes.hamburgueria.dao;

import br.com.mpontes.hamburgueria.database.Database;
import br.com.mpontes.hamburgueria.model.ItemPedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class ItemPedidoDAO {

    // ==================================================
    // CADASTRAR ITEM DO PEDIDO
    // ==================================================

    public int cadastrar(ItemPedido item) {

        String sql = """
            INSERT INTO itens_pedido
            (pedido_id, produto_id, quantidade,
             preco_unitario, subtotal)
            VALUES (?, ?, ?, ?, ?)
            """;

        try (Connection conexao = Database.conectar();
             PreparedStatement stmt =
                     conexao.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS
                     )) {

            stmt.setInt(1, item.getPedidoId());
            stmt.setInt(2, item.getProdutoId());
            stmt.setInt(3, item.getQuantidade());
            stmt.setDouble(4, item.getPrecoUnitario());
            stmt.setDouble(5, item.getSubtotal());

            stmt.executeUpdate();

            try (var rs = stmt.getGeneratedKeys()) {

                if (rs.next()) {

                    int id = rs.getInt(1);

                    item.setId(id);

                    System.out.println(
                            "Item do pedido cadastrado: " + id
                    );

                    return id;
                }
            }

        } catch (Exception e) {

            System.err.println(
                    "Erro ao cadastrar item do pedido:"
            );

            e.printStackTrace();
        }

        return -1;
    }

    // ==================================================
    // EXCLUIR ITEM
    // ==================================================

    public void excluir(int id) {

        String sql = """
            DELETE FROM itens_pedido
            WHERE id = ?
            """;

        try (Connection conexao = Database.conectar();
             PreparedStatement stmt =
                     conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            stmt.executeUpdate();

        } catch (Exception e) {

            System.err.println(
                    "Erro ao excluir item do pedido:"
            );

            e.printStackTrace();
        }
    }
}
