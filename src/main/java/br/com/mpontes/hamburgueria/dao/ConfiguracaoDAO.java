package br.com.mpontes.hamburgueria.dao;

import br.com.mpontes.hamburgueria.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConfiguracaoDAO {

    public String buscar(String chave) {

        String sql = """
            SELECT valor
            FROM configuracoes
            WHERE chave = ?
            """;

        try (Connection conexao = Database.conectar();
             PreparedStatement stmt =
                     conexao.prepareStatement(sql)) {

            stmt.setString(1, chave);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return rs.getString("valor");
                }
            }

        } catch (Exception e) {

            System.err.println(
                    "Erro ao buscar configuração: " + chave
            );

            e.printStackTrace();
        }

        return null;
    }
}
