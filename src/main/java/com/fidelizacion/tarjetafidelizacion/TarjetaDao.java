package com.fidelizacion.tarjetafidelizacion;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class TarjetaDao {

    public void registrarCliente(String cedula, String nombreCliente) {
        // Usamos exactamente los nombres limpios de tu tabla
        String sql = "INSERT INTO tarjetas (numero_tarjeta, nombre_cliente, puntos) VALUES (?, ?, 0)";
        
        try (Connection con = CConexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, cedula);
            ps.setString(2, nombreCliente);
            ps.executeUpdate();
            
            System.out.println("¡Cliente registrado con éxito en XAMPP!");
            
        } catch (Exception e) {
            System.out.println("Error al registrar el cliente: " + e.getMessage());
        }
    }
    // Método para sumar puntos a una tarjeta existente
    public boolean sumarPuntos(String numeroTarjeta, int puntosASumar) {
        String sql = "UPDATE tarjetas SET puntos = puntos + ? WHERE numero_tarjeta = ?";
        
        try (Connection con = CConexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, puntosASumar);
            ps.setString(2, numeroTarjeta);
            int filasAfectadas = ps.executeUpdate();
            
            return filasAfectadas > 0; // Devuelve true si encontró la tarjeta y sumó
            
        } catch (Exception e) {
            System.out.println("Error al sumar puntos: " + e.getMessage());
            return false;
        }
    }
    // Método para traer todos los clientes y mostrarlos en la tabla
    public javax.swing.table.DefaultTableModel obtenerClientesTabla() {
        String[] columnas = {"ID", "Nro Tarjeta", "Nombre", "Puntos"};
        javax.swing.table.DefaultTableModel modelo = new javax.swing.table.DefaultTableModel(null, columnas);
        String sql = "SELECT * FROM tarjetas";
        
        try (Connection con = CConexion.obtenerConexion();
             java.sql.Statement st = con.createStatement();
             java.sql.ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("id"),
                    rs.getString("numero_tarjeta"),
                    rs.getString("nombre_cliente"),
                    rs.getInt("puntos")
                };
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            System.out.println("Error al listar clientes: " + e.getMessage());
        }
        return modelo;
    }
    // Método para buscar clientes por nombre o tarjeta
    public javax.swing.table.DefaultTableModel buscarClientes(String criterio) {
        String[] columnas = {"ID", "Nro Tarjeta", "Nombre", "Puntos"};
        javax.swing.table.DefaultTableModel modelo = new javax.swing.table.DefaultTableModel(null, columnas);
        String sql = "SELECT * FROM tarjetas WHERE numero_tarjeta LIKE ? OR nombre_cliente LIKE ?";
        
        try (Connection con = CConexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, "%" + criterio + "%");
            ps.setString(2, "%" + criterio + "%");
            
            java.sql.ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("id"),
                    rs.getString("numero_tarjeta"),
                    rs.getString("nombre_cliente"),
                    rs.getInt("puntos")
                };
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            System.out.println("Error al buscar cliente: " + e.getMessage());
        }
        return modelo;
    }

    // Método para eliminar un cliente por número de tarjeta
    public boolean eliminarCliente(String numeroTarjeta) {
        String sql = "DELETE FROM tarjetas WHERE numero_tarjeta = ?";
        
        try (Connection con = CConexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, numeroTarjeta);
            int filasAfectadas = ps.executeUpdate();
            
            return filasAfectadas > 0;
            
        } catch (Exception e) {
            System.out.println("Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }
}