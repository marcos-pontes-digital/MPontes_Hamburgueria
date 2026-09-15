package br.com.mpontes.hamburgueria.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static final String URL = "jdbc:sqlite:database/hamburgueria.db";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
