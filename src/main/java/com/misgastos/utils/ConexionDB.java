package com.misgastos.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.io.File;

public class ConexionDB {

    private static final String NOMBRE_DB = "gastos.db";
    
    private static String getDatabasePath() {
        String homeDir = System.getProperty("user.home");
        return homeDir + File.separator + NOMBRE_DB;
    }

    public static Connection getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            
            String url = "jdbc:sqlite:" + getDatabasePath();
            
            Connection conn = DriverManager.getConnection(url);
            
            crearTablaSiNoExiste(conn);
            
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void crearTablaSiNoExiste(Connection conn) {
        String sqlUsuario = "CREATE TABLE IF NOT EXISTS usuario ("
                          + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                          + "nombre_usuario TEXT NOT NULL UNIQUE,"
                          + "correo TEXT NOT NULL UNIQUE,"
                          + "clave TEXT NOT NULL,"
                          + "ingreso_mensual REAL DEFAULT 0.0"
                          + ");";

        String sqlGastoMensual = "CREATE TABLE IF NOT EXISTS gasto_mensual ("
                               + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                               + "id_usuario INTEGER NOT NULL,"
                               + "descripcion TEXT NOT NULL,"
                               + "monto REAL NOT NULL,"
                               + "tipo_gasto TEXT,"
                               + "mes TEXT NOT NULL,"
                               + "anio INTEGER NOT NULL,"
                               + "FOREIGN KEY (id_usuario) REFERENCES usuario(id)"
                               + ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sqlUsuario);
            stmt.execute(sqlGastoMensual);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}