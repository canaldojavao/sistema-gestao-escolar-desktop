package br.com.escola.gestaoescolar.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactory {

    private static HikariDataSource dataSource;

    static {
        var config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/sistema_gestao_escolar");
        config.setUsername("root");
        config.setPassword("root");

        config.setMinimumIdle(10);
        config.setMaximumPoolSize(50);

        dataSource = new HikariDataSource(config);
    }

    public static Connection criaConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
