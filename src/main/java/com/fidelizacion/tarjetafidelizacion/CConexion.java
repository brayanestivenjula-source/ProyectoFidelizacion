package com.fidelizacion.tarjetafidelizacion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CConexion {
    // Variables con los datos de tu XAMPP
    private static final String USUARIO = "root";       
    private static final String CONTRASENA = "";        
    private static final String BD = "bd_fidelizacion"; 
    private static final String URL = "jdbc:mysql://localhost:3306/" + BD;

    // Método para conectar
    public static Connection obtenerConexion() {
        Connection conectar = null;
        try {
            // Carga el Driver de MySQL en memoria
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Intenta realizar la conexión
            conectar = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            System.out.println("¡Conexión ESTABLECIDA con éxito a XAMPP!");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        return conectar;
    }
}

