package principal.vista.productos.productos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.producto.Producto;
import principal.vista.productos.HomeMenuProductos;
import modelo.producto.Categoria;
import modelo.producto.marca.Modelo;
import modelo.proveedor.Proveedor;
import repositorio.dao.categoria.CategoriaDaoImpl;
import repositorio.dao.marca.MarcaDaoImpl;
import repositorio.dao.modelo.ModeloDaoImpl;
import repositorio.dao.producto.ProductoDaoImpl;
import repositorio.dao.proveedor.ProveedorDaoImpl;
import modelo.producto.marca.Marca;


/**
 *
 * @author rodri
 */

public class AltaProductos extends javax.swing.JFrame {

    private JTextField nombreProductoField, descripcionProductoField, precioField, cantidadField, imagenField;
    private JComboBox<Categoria> categoriaComboBox;
    private JComboBox<Modelo> modeloComboBox;
    private JComboBox<Proveedor> proveedorComboBox;
    private JComboBox<Marca> marcaComboBox;  

    public AltaProductos() {
        setTitle("Alta Producto");
        setSize(500, 450);  
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(10, 2, 10, 10));  

        add(new JLabel("Nombre del Producto:"));
        nombreProductoField = new JTextField();
        add(nombreProductoField);

        add(new JLabel("Descripción del Producto:"));
        descripcionProductoField = new JTextField();
        add(descripcionProductoField);

        add(new JLabel("Categoría:"));
        categoriaComboBox = new JComboBox<>();
        cargarCategorias();  
        add(categoriaComboBox);

        add(new JLabel("Modelo:"));
        modeloComboBox = new JComboBox<>();
        cargarModelos();  
        add(modeloComboBox);

        add(new JLabel("Marca:"));
        marcaComboBox = new JComboBox<>();
        cargarMarcas();  
        add(marcaComboBox);

        add(new JLabel("Proveedor:"));
        proveedorComboBox = new JComboBox<>();
        cargarProveedores();  
        add(proveedorComboBox);

        add(new JLabel("Precio:"));
        precioField = new JTextField();
        add(precioField);

        add(new JLabel("Cantidad:"));
        cantidadField = new JTextField();
        add(cantidadField);

        add(new JLabel("Imagen (ruta):"));
        imagenField = new JTextField();
        add(imagenField);

        JButton submitButton = new JButton("Dar de alta");
        submitButton.addActionListener(new SubmitButtonListener());
        add(submitButton);

        JButton backButton = new JButton("Volver");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HomeMenuProductos().setVisible(true);
                dispose();
            }
        });
        add(backButton);
    }

    private void cargarCategorias() {
        CategoriaDaoImpl CrudCategoria = new CategoriaDaoImpl();
        java.util.List<Categoria> categorias = CrudCategoria.getCategoriasComboBox();  
        categoriaComboBox.removeAllItems(); 

        for (Categoria categoria : categorias) {
            categoriaComboBox.addItem(categoria); 
        }
    }

    private void cargarModelos() {
        ModeloDaoImpl CrudModelo = new ModeloDaoImpl();
        java.util.List<Modelo> modelos = CrudModelo.getModelosComboBox();  
        for (Modelo modelo : modelos) {
            modeloComboBox.addItem(modelo); 
        }

        if (!modelos.isEmpty()) {
            modeloComboBox.setSelectedIndex(0); 
        }
    }

    private void cargarProveedores() {
        ProveedorDaoImpl CrudProveedor = new ProveedorDaoImpl();
        java.util.List<Proveedor> proveedores = CrudProveedor.getProveedoresComboBox();  
        for (Proveedor proveedor : proveedores) {
            proveedorComboBox.addItem(proveedor);  
        }
    }

    private void cargarMarcas() {
        MarcaDaoImpl CrudMarca = new MarcaDaoImpl();
        java.util.List<Marca> marcas = CrudMarca.getMarcasComboBox(); 
        marcaComboBox.removeAllItems(); 
        for (Marca marca : marcas) {
            marcaComboBox.addItem(marca);  
        }
    }

    private class SubmitButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String nombreProducto = nombreProductoField.getText();
                String descripcionProducto = descripcionProductoField.getText();

                Categoria categoria = (Categoria) categoriaComboBox.getSelectedItem();
                Modelo modelo = (Modelo) modeloComboBox.getSelectedItem();

                if (modelo == null) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un modelo.", "Error", JOptionPane.ERROR_MESSAGE);
                } 

                Proveedor proveedor = (Proveedor) proveedorComboBox.getSelectedItem();
                Marca marca = (Marca) marcaComboBox.getSelectedItem();  
                
                String codigoCategoria = categoria != null ? categoria.getCodigo() : "";
                String codigoModelo = modelo != null ? modelo.getCodigo() : "";
                String codigoProveedor = proveedor != null ? proveedor.getCodigo() : "";

                String codigoMarca = (marca != null) ? marca.getCodigo() : "";

                if (codigoMarca.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una marca.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                float precio = Float.parseFloat(precioField.getText());
                int cantidad = Integer.parseInt(cantidadField.getText());
                String pathImagen = imagenField.getText();

                if (nombreProducto.isEmpty() || descripcionProducto.isEmpty() || categoria == null || modelo == null || proveedor == null || precio <= 0 || cantidad <= 0 || pathImagen.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Todos los campos deben ser completados correctamente.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Producto producto = new Producto(
                        null,
                        nombreProducto,
                        descripcionProducto,
                        categoria,
                        modelo,
                        proveedor,
                        precio,
                        pathImagen,
                        cantidad,
                        marca 
                );

                ProductoDaoImpl productoDao = new ProductoDaoImpl();
                productoDao.insertarNuevoProducto(producto); 

                JOptionPane.showMessageDialog(null, "Producto registrado exitosamente.");

                limpiarCampos();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "El precio o cantidad no son válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void limpiarCampos() {
            nombreProductoField.setText("");
            descripcionProductoField.setText("");
            precioField.setText("");
            cantidadField.setText("");
            imagenField.setText("");
            categoriaComboBox.setSelectedIndex(0);
            modeloComboBox.setSelectedIndex(0);
            proveedorComboBox.setSelectedIndex(0);
            marcaComboBox.setSelectedIndex(0);
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
            java.util.logging.Logger.getLogger(AltaProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AltaProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AltaProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AltaProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AltaProductos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
