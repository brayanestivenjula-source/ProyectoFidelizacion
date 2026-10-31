package com.fidelizacion.tarjetafidelizacion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormularioFidelizacion extends JFrame {

    private JTextField txtCedula, txtNombre, txtTarjetaPuntos, txtPuntosASumar, txtBuscar;
    private JButton btnRegistrar, btnSumarPuntos, btnActualizar, btnBuscar, btnEliminar;
    private JTable tablaClientes;
    private JScrollPane scrollTabla;
    private TarjetaDao tarjetaDao;

    public FormularioFidelizacion() {
        tarjetaDao = new TarjetaDao();

        setTitle("Sistema de Fidelización de Clientes");
        setSize(480, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // --- SECCIÓN 1: REGISTRAR CLIENTE ---
        JLabel lblTituloReg = new JLabel("--- Registrar Nuevo Cliente ---");
        lblTituloReg.setBounds(30, 15, 300, 20);
        add(lblTituloReg);

        JLabel lblCedula = new JLabel("Nro Tarjeta:");
        lblCedula.setBounds(30, 45, 130, 25);
        add(lblCedula);

        txtCedula = new JTextField();
        txtCedula.setBounds(160, 45, 240, 25);
        add(txtCedula);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(30, 80, 130, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(160, 80, 240, 25);
        add(txtNombre);

        btnRegistrar = new JButton("Registrar Cliente");
        btnRegistrar.setBounds(140, 115, 180, 30);
        add(btnRegistrar);

        // --- SECCIÓN 2: ACUMULAR PUNTOS ---
        JLabel lblTituloPuntos = new JLabel("--- Acumular Puntos ---");
        lblTituloPuntos.setBounds(30, 160, 300, 20);
        add(lblTituloPuntos);

        JLabel lblTarjPuntos = new JLabel("Nro Tarjeta:");
        lblTarjPuntos.setBounds(30, 190, 130, 25);
        add(lblTarjPuntos);

        txtTarjetaPuntos = new JTextField();
        txtTarjetaPuntos.setBounds(160, 190, 240, 25);
        add(txtTarjetaPuntos);

        JLabel lblCantPuntos = new JLabel("Puntos a sumar:");
        lblCantPuntos.setBounds(30, 225, 130, 25);
        add(lblCantPuntos);

        txtPuntosASumar = new JTextField();
        txtPuntosASumar.setBounds(160, 225, 240, 25);
        add(txtPuntosASumar);

        btnSumarPuntos = new JButton("Sumar Puntos");
        btnSumarPuntos.setBounds(140, 260, 180, 30);
        add(btnSumarPuntos);

        // --- SECCIÓN 3: BUSCADOR Y TABLA ---
        JLabel lblTituloTabla = new JLabel("--- Gestión de Clientes ---");
        lblTituloTabla.setBounds(30, 305, 300, 20);
        add(lblTituloTabla);

        JLabel lblBuscar = new JLabel("Buscar:");
        lblBuscar.setBounds(30, 335, 60, 25);
        add(lblBuscar);

        txtBuscar = new JTextField();
        txtBuscar.setBounds(90, 335, 180, 25);
        add(txtBuscar);

        btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(280, 335, 120, 25);
        add(btnBuscar);

        tablaClientes = new JTable();
        scrollTabla = new JScrollPane(tablaClientes);
        scrollTabla.setBounds(30, 375, 400, 160);
        add(scrollTabla);

        btnActualizar = new JButton("Ver Todos");
        btnActualizar.setBounds(60, 550, 150, 30);
        add(btnActualizar);

        btnEliminar = new JButton("Eliminar Seleccionado");
        btnEliminar.setBounds(230, 550, 180, 30);
        add(btnEliminar);

        cargarTabla();

        // --- ACCIONES DE LOS BOTONES ---
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cedula = txtCedula.getText();
                String nombre = txtNombre.getText();

                if (!cedula.isEmpty() && !nombre.isEmpty()) {
                    tarjetaDao.registrarCliente(cedula, nombre);
                    JOptionPane.showMessageDialog(null, "¡Cliente registrado con éxito!");
                    txtCedula.setText("");
                    txtNombre.setText("");
                    cargarTabla();
                } else {
                    JOptionPane.showMessageDialog(null, "Llena todos los campos de registro.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnSumarPuntos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tarjeta = txtTarjetaPuntos.getText();
                String puntosTexto = txtPuntosASumar.getText();

                if (!tarjeta.isEmpty() && !puntosTexto.isEmpty()) {
                    try {
                        int puntos = Integer.parseInt(puntosTexto);
                        boolean exito = tarjetaDao.sumarPuntos(tarjeta, puntos);
                        
                        if (exito) {
                            JOptionPane.showMessageDialog(null, "¡Puntos sumados correctamente!");
                            txtTarjetaPuntos.setText("");
                            txtPuntosASumar.setText("");
                            cargarTabla();
                        } else {
                            JOptionPane.showMessageDialog(null, "No se encontró esa tarjeta.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Los puntos deben ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Llena los campos para sumar puntos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String criterio = txtBuscar.getText();
                if (!criterio.isEmpty()) {
                    DefaultTableModel modelo = tarjetaDao.buscarClientes(criterio);
                    tablaClientes.setModel(modelo);
                } else {
                    cargarTabla();
                }
            }
        });

        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtBuscar.setText("");
                cargarTabla();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tablaClientes.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    String tarjeta = tablaClientes.getValueAt(filaSeleccionada, 1).toString();
                    int confirmar = JOptionPane.showConfirmDialog(null, 
                            "¿Seguro que deseas eliminar la tarjeta " + tarjeta + "?", 
                            "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                    
                    if (confirmar == JOptionPane.YES_OPTION) {
                        if (tarjetaDao.eliminarCliente(tarjeta)) {
                            JOptionPane.showMessageDialog(null, "Cliente eliminado con éxito.");
                            cargarTabla();
                        } else {
                            JOptionPane.showMessageDialog(null, "Error al eliminar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona primero una fila de la tabla.", "Atención", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    private void cargarTabla() {
        DefaultTableModel modelo = tarjetaDao.obtenerClientesTabla();
        tablaClientes.setModel(modelo);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FormularioFidelizacion().setVisible(true);
            }
        });
    }
}