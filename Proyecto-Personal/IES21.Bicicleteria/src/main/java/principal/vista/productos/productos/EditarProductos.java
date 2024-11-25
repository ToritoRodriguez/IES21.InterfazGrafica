package principal.vista.productos.productos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import repositorio.dao.producto.ProductoDaoImpl;
import modelo.producto.Producto;
import principal.vista.productos.HomeMenuProductos;
import repositorio.dao.categoria.CategoriaDaoImpl;
import repositorio.dao.marca.MarcaDaoImpl;
import repositorio.dao.modelo.ModeloDaoImpl;
import repositorio.dao.proveedor.ProveedorDaoImpl;
import modelo.producto.Categoria;
import modelo.producto.marca.Modelo;
import modelo.proveedor.Proveedor;
import modelo.producto.marca.Marca;


/**
 *
 * @author rodri
 */
public class EditarProductos extends javax.swing.JFrame {

    private JTextField codigoField;
    private JTable productoTable;
    private JButton buscarButton, modificarButton;

    public EditarProductos() {
        setTitle("Editar Productos");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Editar Productos", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel(new BorderLayout(10, 10));
        JPanel inputPanel = new JPanel(new BorderLayout(10, 10));
        inputPanel.add(new JLabel("Código del Producto:"), BorderLayout.WEST);
        codigoField = new JTextField();
        inputPanel.add(codigoField, BorderLayout.CENTER);

        buscarButton = new JButton("Buscar");
        buscarButton.addActionListener(new BuscarButtonListener());
        inputPanel.add(buscarButton, BorderLayout.EAST);
        searchPanel.add(inputPanel, BorderLayout.NORTH);

        String[] columnNames = {"Nombre", "Categoría", "Modelo", "Marca", "Proveedor", "Descripción", "Cantidad", "Precio"};
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, columnNames);
        productoTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(productoTable);
        searchPanel.add(scrollPane, BorderLayout.CENTER);

        modificarButton = new JButton("Guardar Cambios");
        modificarButton.setEnabled(false);
        modificarButton.addActionListener(new ModificarButtonListener());
        searchPanel.add(modificarButton, BorderLayout.SOUTH);

        add(searchPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton backButton = new JButton("Volver");
        backButton.addActionListener(e -> {
            new HomeMenuProductos().setVisible(true);
            dispose();
        });
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private class BuscarButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String codigoProducto = codigoField.getText().trim();
                if (codigoProducto.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un código de producto.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ProductoDaoImpl productoDao = new ProductoDaoImpl();
                Producto producto = productoDao.obtenerProducto(codigoProducto, null, null, null, null, null, -1);

                if (producto != null) {
                    Object[][] data = {
                        {producto.getNombre(), 
                            producto.getCategoria().getNombre(), 
                            producto.getModelo().getModelo(),
                            producto.getMarca().getMarca(), 
                            producto.getProveedor().getNombreFantasia(),
                            producto.getDescripcion(), 
                            producto.getCantidad(), 
                            producto.getPrecio()}
                    };
                    DefaultTableModel model = new DefaultTableModel(
                            data,
                            new String[]{"Nombre", "Categoría", "Modelo", "Marca", "Proveedor", "Descripción", "Cantidad", "Precio"}
                    );
                    productoTable.setModel(model);
                    modificarButton.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Producto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                    modificarButton.setEnabled(false);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class ModificarButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int selectedRow = productoTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione un producto para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                productoTable.getCellEditor().stopCellEditing(); 

                String nuevoNombre = (String) productoTable.getValueAt(selectedRow, 0);
                String nuevaCategoria = (String) productoTable.getValueAt(selectedRow, 1);
                String nuevoModelo = (String) productoTable.getValueAt(selectedRow, 2);
                String nuevaMarca = (String) productoTable.getValueAt(selectedRow, 3);
                String nuevoProveedor = (String) productoTable.getValueAt(selectedRow, 4);
                String nuevaDescripcion = (String) productoTable.getValueAt(selectedRow, 5);
                int nuevaCantidad = Integer.parseInt(productoTable.getValueAt(selectedRow, 6).toString());
                double nuevoPrecio = Double.parseDouble(productoTable.getValueAt(selectedRow, 7).toString());

                if (nuevoNombre.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El nombre del producto no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (nuevaCategoria.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "La categoría no puede estar vacía.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (nuevoModelo.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El modelo no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (nuevaMarca.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "La marca no puede estar vacía.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (nuevoProveedor.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El proveedor no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                CategoriaDaoImpl categoriaDao = new CategoriaDaoImpl();
                Categoria categoria = categoriaDao.obtenerCategoria(null, nuevaCategoria, null);
                if (categoria == null) {
                    JOptionPane.showMessageDialog(null, "Categoría no encontrada en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                MarcaDaoImpl marcaDao = new MarcaDaoImpl();
                Marca marca = marcaDao.obtenerMarca(null, nuevaMarca);
                if (marca == null) {
                    JOptionPane.showMessageDialog(null, "Marca no encontrada en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ModeloDaoImpl modeloDao = new ModeloDaoImpl();
                Modelo modelo = modeloDao.obtenerModelo(null, nuevoModelo, null, null);
                if (modelo == null) {
                    JOptionPane.showMessageDialog(null, "Modelo no encontrado en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ProveedorDaoImpl proveedorDao = new ProveedorDaoImpl();
                Proveedor proveedor = proveedorDao.obtenerProveedor(null, nuevoProveedor);
                if (proveedor == null) {
                    JOptionPane.showMessageDialog(null, "Proveedor no encontrado en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ProductoDaoImpl productoDao = new ProductoDaoImpl();
                Producto producto = productoDao.obtenerProducto(codigoField.getText().trim(), null, null, null, null, null, -1);
                if (producto != null) {

                    producto.setNombre(nuevoNombre);
                    producto.setDescripcion(nuevaDescripcion);
                    producto.setCantidad(nuevaCantidad);
                    producto.setPrecio(nuevoPrecio);
                    producto.setCategoria(categoria); 
                    producto.setModelo(modelo);  
                    producto.setMarca(marca);  
                    producto.setProveedor(proveedor); 

                    productoDao.modificarProducto(producto.getCodigo(), producto);

                    JOptionPane.showMessageDialog(null, "Producto modificado con éxito.");
                } else {
                    JOptionPane.showMessageDialog(null, "Producto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace(); 
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
            java.util.logging.Logger.getLogger(EditarProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditarProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditarProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditarProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditarProductos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
