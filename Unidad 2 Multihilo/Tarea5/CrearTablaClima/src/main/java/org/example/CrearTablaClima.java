package org.example;

import java.sql.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class CrearTablaClima {
    private static final String URL = "jdbc:mysql://localhost:3306/ut2";
    private static final String USER = "dam";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) {
        // Sentencia SQL para crear la tabla clientes (sin usar IF NOT EXISTS)
        String sql = "CREATE TABLE precipalgemesioctubre ("
                + "Provincia VARCHAR(32) NOT NULL, "
                + "Estacion VARCHAR(32) NOT NULL, "
                + "Fecha DATE NOT NULL,"
                + "Temperatura DECIMAL(6.2) NOT NULL,"
                + "Humedad DECIMAL(6.2) NOT NULL,"
                + "Precipitacion DECIMAL(6.2) NULL"
                + ")";

        // try-with-resources: la conexión y el Statement se cierran solos al salir del bloque
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {

            // Las sentencias DDL
            statement.execute(sql);
            System.out.println("¡Tabla 'precipalgemesioctubre' creada correctamente en la base de datos!");

        } catch (SQLException e) {
            // Gestión de errores SQL utilizando el metodo requerido
            muestraErrorSQL(e);
        } catch (Exception e) {
            // Captura de cualquier otra excepción general
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace(System.err);
        }
    }

    public static void muestraErrorSQL(SQLException e) {
        System.out.println("\n--- ERROR DE BASE DE DATOS (SQLException) ---");
        System.out.println("Mensaje de error: " + e.getMessage());
        System.out.println("Estado SQL (SQLState): " + e.getSQLState());
        System.out.println("Código de error del proveedor: " + e.getErrorCode());
        System.out.println("----------------------------------------------\n");
    }
}

