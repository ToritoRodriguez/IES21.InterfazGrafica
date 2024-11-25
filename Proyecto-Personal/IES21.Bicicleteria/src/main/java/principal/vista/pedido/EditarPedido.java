/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package principal.vista.pedido;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import modelo.pedido.Pedido;
import modelo.producto.Producto;
import modelo.vendedor.Vendedor;
import modelo.cliente.Cliente;
import modelo.pedido.DetallePedido;
import repositorio.dao.pedido.PedidoDaoImpl;
import repositorio.dao.producto.ProductoDaoImpl;
import repositorio.dao.vendedor.VendedorDaoImpl;
import repositorio.dao.cliente.ClienteDaoImpl;
import java.util.List;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import modelo.pedido.EstadoPedido;

/**
 *
 * @author rodri
 */
public class EditarPedido extends javax.swing.JFrame {

    private JTextField busquedaTextField;
    private JTable tablaPedidos;
    private JTable tablaDetalles;
    private Pedido pedidoActual;

    public EditarPedido() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Editar Pedido");
        setSize(1000, 600);
        setLayout(new BorderLayout());

        // Panel de búsqueda con botón
        JPanel filtrosPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        filtrosPanel.setBorder(BorderFactory.createTitledBorder("Buscar Pedido"));

        JLabel busquedaLabel = new JLabel("Buscar por código de pedido:");
        busquedaTextField = new JTextField();
        JButton buscarButton = new JButton("Buscar");
        buscarButton.addActionListener(this::buscarPedido);

        // Se agrega el campo de texto y el botón de búsqueda al panel
        filtrosPanel.add(busquedaLabel);
        filtrosPanel.add(busquedaTextField);
        filtrosPanel.add(buscarButton);

        // Tabla de pedidos
        tablaPedidos = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Fecha", "Vendedor", "Cliente", "Código Pedido", "Estado", "Total Pedido"}
        ));

        // Usamos ListSelectionListener para manejar la selección de filas
        tablaPedidos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int filaSeleccionada = tablaPedidos.getSelectedRow();
                if (filaSeleccionada != -1) {
                    String codigoPedido = (String) tablaPedidos.getValueAt(filaSeleccionada, 3); // Columna "Código Pedido"
                    System.out.println("Código Pedido seleccionado: " + codigoPedido);
                    cargarDetallesPedido(codigoPedido); // Llamamos al método para cargar los detalles
                }
            }
        });

        JScrollPane tablaPedidosScroll = new JScrollPane(tablaPedidos);

        // Tabla de detalles de pedido
        tablaDetalles = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Código Pedido", "Nombre Producto", "Cantidad", "Precio", "Descuento", "Total"}
        ));
        JScrollPane tablaDetallesScroll = new JScrollPane(tablaDetalles);

        // Botones para cambiar el estado
        JButton cambiarEstadoButton = new JButton("Cambiar Estado");
        cambiarEstadoButton.addActionListener(e -> cambiarEstadoPedido());

        // Botones para cambiar el vendedor y cliente
        JButton cambiarVendedorButton = new JButton("Cambiar Vendedor");
        cambiarVendedorButton.addActionListener(e -> cambiarVendedor());

        JButton cambiarClienteButton = new JButton("Cambiar Cliente");
        cambiarClienteButton.addActionListener(e -> cambiarCliente());

        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        botonesPanel.add(cambiarEstadoButton);
        botonesPanel.add(cambiarVendedorButton);
        botonesPanel.add(cambiarClienteButton);

        // Agregar componentes al JFrame
        JPanel panelCentro = new JPanel(new GridLayout(2, 1));
        panelCentro.add(tablaPedidosScroll);
        panelCentro.add(tablaDetallesScroll);

        add(filtrosPanel, BorderLayout.NORTH);
        add(panelCentro, BorderLayout.CENTER);
        add(botonesPanel, BorderLayout.SOUTH);
    }

    private void actualizarTablaPedidos(List<Pedido> pedidos) {
        DefaultTableModel modeloPedidos = (DefaultTableModel) tablaPedidos.getModel();
        modeloPedidos.setRowCount(0);

        for (Pedido pedido : pedidos) {
            String nombreVendedor = pedido.getVendedor() != null ? pedido.getVendedor().getNombre() + " " + pedido.getVendedor().getApellido() : "No disponible";
            String nombreCliente = pedido.getCliente() != null ? pedido.getCliente().getNombre() + " " + pedido.getCliente().getApellido() : "No disponible";

            modeloPedidos.addRow(new Object[]{
                pedido.getFecha(),
                nombreVendedor,
                nombreCliente,
                pedido.getCodigo(),
                pedido.getEstado(),
                pedido.getTotalPedido()
            });
        }
    }

    private void cargarDetallesPedido(String codigoPedido) {
        PedidoDaoImpl pedidoDao = new PedidoDaoImpl();
        List<DetallePedido> detalles = pedidoDao.obtenerDetallesPorPedido(codigoPedido);

        if (detalles != null && !detalles.isEmpty()) {
            DefaultTableModel modeloDetalles = (DefaultTableModel) tablaDetalles.getModel();
            modeloDetalles.setRowCount(0);  // Limpiar la tabla de detalles antes de agregar los nuevos

            for (DetallePedido detalle : detalles) {
                Producto producto = detalle.getProducto();
                modeloDetalles.addRow(new Object[]{
                    codigoPedido,
                    producto.getNombre(),
                    detalle.getCantidad(),
                    producto.getPrecio(),
                    detalle.getDescuento(),
                    detalle.getTotal()
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró detalles para el pedido con código " + codigoPedido, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarPedido(ActionEvent event) {
        String codigoPedido = busquedaTextField.getText().trim();
        if (codigoPedido.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese un código de pedido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        PedidoDaoImpl pedidoDao = new PedidoDaoImpl();
        List<Pedido> pedidos = pedidoDao.obtenerPedidos(codigoPedido, "", "", "", "", "", "");

        if (!pedidos.isEmpty()) {
            pedidoActual = pedidos.get(0);
            actualizarTablaPedidos(pedidos);
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró el pedido con el código ingresado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cambiarEstadoPedido() {
        if (pedidoActual == null) {
            JOptionPane.showMessageDialog(this, "No hay un pedido seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] estados = {"PENDIENTE", "PREPARACION", "ENTREGADO"};
        String estadoActual = (String) JOptionPane.showInputDialog(this, "Seleccionar nuevo estado:", "Cambiar Estado",
                JOptionPane.QUESTION_MESSAGE, null, estados, pedidoActual.getEstado().toString());

        if (estadoActual != null && !estadoActual.isEmpty()) {
            try {
                EstadoPedido nuevoEstado = EstadoPedido.valueOf(estadoActual);
                pedidoActual.setEstado(nuevoEstado);

                PedidoDaoImpl pedidoDao = new PedidoDaoImpl();
                pedidoDao.modificarPedido(pedidoActual.getCodigo(), pedidoActual);

                JOptionPane.showMessageDialog(this, "Estado del pedido actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                actualizarTablaPedidos(List.of(pedidoActual));
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Estado inválido seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cambiarVendedor() {
        if (pedidoActual == null) {
            JOptionPane.showMessageDialog(this, "No hay un pedido seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        VendedorDaoImpl vendedorDao = new VendedorDaoImpl();
        List<Vendedor> vendedores = vendedorDao.getVendedores("", "", "", "");

        // Crear un JComboBox para seleccionar el vendedor con nombre y apellido
        JComboBox<Vendedor> comboVendedores = new JComboBox<>(vendedores.toArray(new Vendedor[0]));

        // Personalizar la visualización de los elementos en el JComboBox para mostrar solo el nombre y apellido
        comboVendedores.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Vendedor) {
                    Vendedor vendedor = (Vendedor) value;
                    label.setText(vendedor.getNombre() + " " + vendedor.getApellido());  // Solo mostrar nombre y apellido
                }
                return label;
            }
        });

        int option = JOptionPane.showConfirmDialog(this, comboVendedores, "Seleccionar nuevo vendedor", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            Vendedor vendedorSeleccionado = (Vendedor) comboVendedores.getSelectedItem();
            if (vendedorSeleccionado != null) {
                pedidoActual.setVendedor(vendedorSeleccionado);

                PedidoDaoImpl pedidoDao = new PedidoDaoImpl();
                pedidoDao.modificarPedido(pedidoActual.getCodigo(), pedidoActual);

                JOptionPane.showMessageDialog(this, "Vendedor del pedido actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void cambiarCliente() {
        if (pedidoActual == null) {
            JOptionPane.showMessageDialog(this, "No hay un pedido seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ClienteDaoImpl clienteDao = new ClienteDaoImpl();
        List<Cliente> clientes = clienteDao.getClientes("", "", "");

        // Crear un JComboBox para seleccionar el cliente con nombre y apellido
        JComboBox<Cliente> comboClientes = new JComboBox<>(clientes.toArray(new Cliente[0]));

        // Personalizar la visualización de los elementos en el JComboBox para mostrar solo el nombre y apellido
        comboClientes.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Cliente) {
                    Cliente cliente = (Cliente) value;
                    label.setText(cliente.getNombre() + " " + cliente.getApellido());  // Solo mostrar nombre y apellido
                }
                return label;
            }
        });

        int option = JOptionPane.showConfirmDialog(this, comboClientes, "Seleccionar nuevo cliente", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            Cliente clienteSeleccionado = (Cliente) comboClientes.getSelectedItem();
            if (clienteSeleccionado != null) {
                pedidoActual.setCliente(clienteSeleccionado);

                PedidoDaoImpl pedidoDao = new PedidoDaoImpl();
                pedidoDao.modificarPedido(pedidoActual.getCodigo(), pedidoActual);

                JOptionPane.showMessageDialog(this, "Cliente del pedido actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EditarPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditarPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditarPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditarPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditarPedido().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
