package br.com.catalogo.util;

import br.com.catalogo.exception.DAOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    
    private static final String URL = "jdbc:h2:~/catalogo-web/catalogodb";

    public static Connection getConnection() {
        try {
            Class.forName("org.h2.Driver");
            String url = System.getProperty("catalogo.db.url", URL);
            return DriverManager.getConnection(url, "sa", "");
        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException("Erro ao conectar no banco", e);
        }
    }
}
