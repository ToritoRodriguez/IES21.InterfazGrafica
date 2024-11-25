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
import java.time.LocalDate;
import java.time.ZoneId;
import modelo.pedido.EstadoPedido;


/**
 *
 * @author rodri
 */

public class AltaPedido extends javax.swing.JFrame {

    private JTextField nombreVendedorField, apellidoVendedorField, nombreClienteField, apellidoClienteField;
    private JTextField nombreProductoField, cantidadField, descuentoField;
    private JComboBox<Producto> productosComboBox;
    private JButton agregarProductoButton, guardarPedidoButton;
    private JTextArea detallePedidoArea;

    private double totalPedido = 0;
    private List<DetallePedido> detalles = new ArrayList<>(); 

    public AltaPedido() {
        setTitle("Alta Pedido");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(10, 2, 10, 10));

        add(new JLabel("Nombre Vendedor:"));
        nombreVendedorField = new JTextField();
        add(nombreVendedorField);
        add(new JLabel("Apellido Vendedor:"));
        apellidoVendedorField = new JTextField();
        add(apellidoVendedorField);

        add(new JLabel("Nombre Cliente:"));
        nombreClienteField = new JTextField();
        add(nombreClienteField);
        add(new JLabel("Apellido Cliente:"));
        apellidoClienteField = new JTextField();
        add(apellidoClienteField);

        add(new JLabel("Producto:"));
        productosComboBox = new JComboBox<>(); 
        add(productosComboBox);

        add(new JLabel("Cantidad:"));
        cantidadField = new JTextField();
        add(cantidadField);

        add(new JLabel("Descuento (%):"));
        descuentoField = new JTextField();
        add(descuentoField);

        agregarProductoButton = new JButton("Agregar Producto");
        agregarProductoButton.addActionListener(new AgregarProductoListener());
        add(agregarProductoButton);

        add(new JLabel("Detalle del Pedido:"));
        detallePedidoArea = new JTextArea(5, 40);
        detallePedidoArea.setEditable(false);
        add(new JScrollPane(detallePedidoArea));

        guardarPedidoButton = new JButton("Guardar Pedido");
        guardarPedidoButton.addActionListener(new GuardarPedidoListener());
        add(guardarPedidoButton);

        cargarProductos();
    }

    private void cargarProductos() {
        try {
            List<Producto> productos = new ProductoDaoImpl().getProductosComboBox();
            for (Producto producto : productos) {
                productosComboBox.addItem(producto);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar productos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private class AgregarProductoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String nombreVendedor = nombreVendedorField.getText();
                String apellidoVendedor = apellidoVendedorField.getText();
                Vendedor vendedor = new VendedorDaoImpl().obtenerVendedor(null, nombreVendedor, apellidoVendedor);

                if (vendedor == null) {
                    JOptionPane.showMessageDialog(null, "Vendedor no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String nombreCliente = nombreClienteField.getText();
                String apellidoCliente = apellidoClienteField.getText();
                Cliente cliente = new ClienteDaoImpl().obtenerCliente(null, nombreCliente, apellidoCliente);

                if (cliente == null) {
                    JOptionPane.showMessageDialog(null, "Cliente no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Producto producto = (Producto) productosComboBox.getSelectedItem();
                if (producto == null) {
                    JOptionPane.showMessageDialog(null, "Producto no seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int cantidad = Integer.parseInt(cantidadField.getText());

                int descuento = 0;  
                try {
                    descuento = descuentoField.getText().isEmpty() ? 0 : Integer.parseInt(descuentoField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Descuento no válido. Se aplicará 0%", "Error", JOptionPane.ERROR_MESSAGE);
                }

                double totalProducto = (producto.getPrecio() * cantidad) - (producto.getPrecio() * cantidad * descuento / 100);

                detallePedidoArea.append("Producto: " + producto.getNombre() + " | Cantidad: " + cantidad + " | Precio: " + producto.getPrecio() + " | Descuento: " + descuento + "% | Total: " + totalProducto + "\n");

                totalPedido += totalProducto;

                DetallePedido detallePedido = new DetallePedido(
                        producto.getCodigo(), 
                        generarCodigoDetalle(), 
                        producto, 
                        cantidad, 
                        producto.getPrecio(), 
                        descuento,
                        totalProducto 
                );
                detalles.add(detallePedido);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al agregar producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private String generarCodigoDetalle() {
            return "D" + System.currentTimeMillis();  
        }
    }

    private class GuardarPedidoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String nombreVendedor = nombreVendedorField.getText();
                String apellidoVendedor = apellidoVendedorField.getText();
                Vendedor vendedor = new VendedorDaoImpl().obtenerVendedor(null, nombreVendedor, apellidoVendedor);
                if (vendedor == null) {
                    JOptionPane.showMessageDialog(null, "Vendedor no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String nombreCliente = nombreClienteField.getText();
                String apellidoCliente = apellidoClienteField.getText();
                Cliente cliente = new ClienteDaoImpl().obtenerCliente(null, nombreCliente, apellidoCliente);
                if (cliente == null) {
                    JOptionPane.showMessageDialog(null, "Cliente no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (detalles.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No se han agregado productos al pedido.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                LocalDate fechaActual = LocalDate.now();
                Pedido pedido = new Pedido(fechaActual, vendedor, cliente, EstadoPedido.PREPARACION, totalPedido, detalles);
                PedidoDaoImpl pedidoDao = new PedidoDaoImpl();
                pedidoDao.insertarNuevoPedido(pedido);

                JOptionPane.showMessageDialog(null, "Pedido registrado exitosamente.");
                limpiarCampos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al guardar el pedido: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void limpiarCampos() {
            nombreVendedorField.setText("");
            apellidoVendedorField.setText("");
            nombreClienteField.setText("");
            apellidoClienteField.setText("");
            productosComboBox.setSelectedIndex(0); 
            cantidadField.setText("");
            descuentoField.setText("");
            detallePedidoArea.setText("");
            totalPedido = 0;
            detalles.clear(); 
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
            java.util.logging.Logger.getLogger(AltaPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AltaPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AltaPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AltaPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AltaPedido().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
