package br.com.mpontes.hamburgueria.dao;

import br.com.mpontes.hamburgueria.database.Database;
import br.com.mpontes.hamburgueria.model.Pedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class PedidoDAO {

    // ==================================================
    // CADASTRAR PEDIDO
    // ==================================================

    public int cadastrar(Pedido pedido) {

        String sql = """
            INSERT INTO pedidos
            (
                data_hora,
                cliente,
                tipo,
                status,
                total,
                mesa,
                atendente,
                terminal
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection conexao = Database.conectar();
             PreparedStatement stmt =
                     conexao.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS
                     )) {

            stmt.setString(1, pedido.getDataHora());
            stmt.setString(2, pedido.getCliente());
            stmt.setString(3, pedido.getTipo());
            stmt.setString(4, pedido.getStatus());
            stmt.setDouble(5, pedido.getTotal());

            stmt.setString(6, pedido.getMesa());
            stmt.setString(7, pedido.getAtendente());
            stmt.setString(8, pedido.getTerminal());

            stmt.executeUpdate();

            try (ResultSet rs =
                         stmt.getGeneratedKeys()) {

                if (rs.next()) {

                    int id = rs.getInt(1);

                    pedido.setId(id);

                    System.out.println(
                            "Pedido cadastrado: " + id
                    );

                    return id;
                }
            }

        } catch (Exception e) {

            System.err.println(
                    "Erro ao cadastrar pedido:"
            );

            e.printStackTrace();
        }

        return -1;
    }

    // ==================================================
    // ATUALIZAR TOTAL
    // ==================================================

    public void atualizarTotal(
            int pedidoId,
            double total) {

        String sql = """
            UPDATE pedidos
            SET total = ?
            WHERE id = ?
            """;

        try (Connection conexao = Database.conectar();
             PreparedStatement stmt =
                     conexao.prepareStatement(sql)) {

            stmt.setDouble(1, total);
            stmt.setInt(2, pedidoId);

            stmt.executeUpdate();

        } catch (Exception e) {

            System.err.println(
                    "Erro ao atualizar total:"
            );

            e.printStackTrace();
        }
    }

    // ==================================================
    // ATUALIZAR STATUS
    // ==================================================

    public void atualizarStatus(
            int pedidoId,
            String status) {

        String sql = """
            UPDATE pedidos
            SET status = ?
            WHERE id = ?
            """;

        try (Connection conexao = Database.conectar();
             PreparedStatement stmt =
                     conexao.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, pedidoId);

            stmt.executeUpdate();

        } catch (Exception e) {

            System.err.println(
                    "Erro ao atualizar status:"
            );

            e.printStackTrace();
        }
    }
}
