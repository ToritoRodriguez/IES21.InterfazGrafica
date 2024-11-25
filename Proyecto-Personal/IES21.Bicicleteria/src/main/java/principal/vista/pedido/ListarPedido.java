package principal.vista.pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import modelo.pedido.Pedido;
import modelo.pedido.DetallePedido;
import repositorio.dao.pedido.PedidoDaoImpl;

/**
 *
 * @author rodri
 */

public class ListarPedido extends javax.swing.JFrame {

    private JTextField busquedaTextField, nombreVendedorField, apellidoVendedorField, nombreClienteField, apellidoClienteField, fechaDesdeField, fechaHastaField;
    private JTable tablaPedidos;
    private JTable tablaDetalles;

    public ListarPedido() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Listado de Pedidos");
        setSize(800, 600);
        setLayout(new BorderLayout());

        JPanel filtrosPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        filtrosPanel.setBorder(BorderFactory.createTitledBorder("Filtros de Búsqueda"));

        JLabel busquedaLabel = new JLabel("Buscar por código o nombre:");
        busquedaTextField = new JTextField();

        JLabel nombreVendedorLabel = new JLabel("Nombre Vendedor:");
        nombreVendedorField = new JTextField();
        JLabel apellidoVendedorLabel = new JLabel("Apellido Vendedor:");
        apellidoVendedorField = new JTextField();
        JLabel nombreClienteLabel = new JLabel("Nombre Cliente:");
        nombreClienteField = new JTextField();
        JLabel apellidoClienteLabel = new JLabel("Apellido Cliente:");
        apellidoClienteField = new JTextField();
        JLabel fechaDesdeLabel = new JLabel("Fecha Desde:");
        fechaDesdeField = new JTextField();
        JLabel fechaHastaLabel = new JLabel("Fecha Hasta:");
        fechaHastaField = new JTextField();

        filtrosPanel.add(busquedaLabel);
        filtrosPanel.add(busquedaTextField);
        filtrosPanel.add(nombreVendedorLabel);
        filtrosPanel.add(nombreVendedorField);
        filtrosPanel.add(apellidoVendedorLabel);
        filtrosPanel.add(apellidoVendedorField);
        filtrosPanel.add(nombreClienteLabel);
        filtrosPanel.add(nombreClienteField);
        filtrosPanel.add(apellidoClienteLabel);
        filtrosPanel.add(apellidoClienteField);
        filtrosPanel.add(fechaDesdeLabel);
        filtrosPanel.add(fechaDesdeField);
        filtrosPanel.add(fechaHastaLabel);
        filtrosPanel.add(fechaHastaField);

        JPanel buscarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton buscarButton = new JButton("Buscar");
        buscarButton.addActionListener(this::buscarPedidos);
        buscarButton.setPreferredSize(new Dimension(100, 30)); 
        buscarPanel.add(buscarButton);

        tablaPedidos = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Fecha", "Vendedor", "Cliente", "Código Pedido", "Estado", "Total Pedido"}
        ));

        JScrollPane tablaPedidosScroll = new JScrollPane(tablaPedidos);

        tablaDetalles = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Código Pedido", "Nombre Producto", "Cantidad", "Precio", "Descuento", "Total"}
        ));
        JScrollPane tablaDetallesScroll = new JScrollPane(tablaDetalles);

        JPanel panelBusqueda = new JPanel();
        panelBusqueda.setLayout(new BoxLayout(panelBusqueda, BoxLayout.Y_AXIS)); 
        panelBusqueda.add(filtrosPanel);
        panelBusqueda.add(buscarPanel);

        add(panelBusqueda, BorderLayout.NORTH); 
        add(tablaPedidosScroll, BorderLayout.CENTER); 

        add(tablaDetallesScroll, BorderLayout.SOUTH);

        tablaPedidos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int filaSeleccionada = tablaPedidos.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    Object codigoPedidoObj = tablaPedidos.getValueAt(filaSeleccionada, 3); 
                    if (codigoPedidoObj != null) {
                        String codigoPedido = codigoPedidoObj.toString(); 
                        obtenerDetallesPedido(codigoPedido); 
                    } else {
                        JOptionPane.showMessageDialog(this, "El pedido seleccionado no tiene un código válido.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
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

    private void buscarPedidos(ActionEvent event) {

        String codigoPedido = busquedaTextField.getText().trim();
        String nombreVendedor = nombreVendedorField.getText().trim();
        String apellidoVendedor = apellidoVendedorField.getText().trim();
        String nombreCliente = nombreClienteField.getText().trim();
        String apellidoCliente = apellidoClienteField.getText().trim();
        String fechaDesde = fechaDesdeField.getText().trim();
        String fechaHasta = fechaHastaField.getText().trim();

        PedidoDaoImpl CrudPedido = new PedidoDaoImpl();

        List<Pedido> pedidos;

        if (codigoPedido.isEmpty() && nombreVendedor.isEmpty() && apellidoVendedor.isEmpty()
                && nombreCliente.isEmpty() && apellidoCliente.isEmpty() && fechaDesde.isEmpty() && fechaHasta.isEmpty()) {

            pedidos = CrudPedido.obtenerPedidos("", "", "", "", "", "", "");
        } else {
            pedidos = CrudPedido.obtenerPedidos(codigoPedido, nombreVendedor, apellidoVendedor,
                    nombreCliente, apellidoCliente, fechaDesde, fechaHasta);
        }

        actualizarTablaPedidos(pedidos);
    }

    private void obtenerDetallesPedido(String codigoPedido) {

        PedidoDaoImpl CrudPedido = new PedidoDaoImpl();
        List<DetallePedido> detalles = CrudPedido.obtenerDetallesPorPedido(codigoPedido);

        if (detalles == null || detalles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron detalles para el pedido con código: " + codigoPedido, "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        DefaultTableModel modeloDetalles = (DefaultTableModel) tablaDetalles.getModel();
        modeloDetalles.setRowCount(0); 

        for (DetallePedido detalle : detalles) {
            modeloDetalles.addRow(new Object[]{
                detalle.getCodigo(), 
                detalle.getProducto().getNombre(), 
                detalle.getCantidad(), 
                detalle.getPrecio(), 
                detalle.getDescuento(),
                detalle.getTotal() 
            });
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
            java.util.logging.Logger.getLogger(ListarPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ListarPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ListarPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ListarPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ListarPedido().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
