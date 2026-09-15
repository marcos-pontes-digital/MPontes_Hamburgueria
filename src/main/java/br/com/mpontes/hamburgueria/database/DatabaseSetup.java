package br.com.mpontes.hamburgueria.database;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseSetup {

    public static void criarTabelas() {

        String tabelaProdutos = """
            CREATE TABLE IF NOT EXISTS produtos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL,
                descricao TEXT,
                categoria TEXT NOT NULL,
                preco REAL NOT NULL,
                ativo INTEGER NOT NULL DEFAULT 1
            )
            """;

        String tabelaPedidos = """
            CREATE TABLE IF NOT EXISTS pedidos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                data_hora TEXT NOT NULL,
                cliente TEXT,
                tipo TEXT NOT NULL,
                status TEXT NOT NULL,
                total REAL NOT NULL DEFAULT 0
            )
            """;

        String tabelaItensPedido = """
            CREATE TABLE IF NOT EXISTS itens_pedido (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                pedido_id INTEGER NOT NULL,
                produto_id INTEGER NOT NULL,
                quantidade INTEGER NOT NULL,
                preco_unitario REAL NOT NULL,
                subtotal REAL NOT NULL,
                FOREIGN KEY (pedido_id) REFERENCES pedidos(id),
                FOREIGN KEY (produto_id) REFERENCES produtos(id)
            )
            """;

        String tabelaConfiguracoes = """
            CREATE TABLE IF NOT EXISTS configuracoes (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                chave TEXT NOT NULL UNIQUE,
                valor TEXT
            )
            """;

        try (Connection conexao = Database.conectar();
             Statement stmt = conexao.createStatement()) {

            stmt.execute(tabelaProdutos);
            stmt.execute(tabelaPedidos);
            stmt.execute(tabelaItensPedido);
            stmt.execute(tabelaConfiguracoes);

            System.out.println("Tabelas criadas com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao criar tabelas:");
            e.printStackTrace();
        }
    }
}
