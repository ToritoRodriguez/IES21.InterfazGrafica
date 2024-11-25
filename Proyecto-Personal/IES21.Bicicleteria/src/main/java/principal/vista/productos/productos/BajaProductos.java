package principal.vista.productos.productos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import repositorio.dao.categoria.CategoriaDaoImpl;
import repositorio.dao.marca.MarcaDaoImpl;
import repositorio.dao.modelo.ModeloDaoImpl;
import repositorio.dao.producto.ProductoDaoImpl;
import repositorio.dao.proveedor.ProveedorDaoImpl;
import modelo.producto.Categoria;
import modelo.producto.marca.Modelo;
import modelo.proveedor.Proveedor;
import modelo.producto.marca.Marca;
import modelo.producto.Producto;
import modelo.producto.TipoCategoria;


/**
 *
 * @author rodri
 */

public class BajaProductos extends javax.swing.JFrame {

    private JComboBox<Categoria> categoriaComboBox;
    private JComboBox<Modelo> modeloComboBox;
    private JComboBox<Marca> marcaComboBox;
    private JComboBox<Proveedor> proveedorComboBox;
    private JTextField busquedaTextField;
    private JTable tablaProductos;
    private JButton eliminarButton;

    public BajaProductos() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Baja de Productos");
        setSize(800, 600);
        setLayout(new BorderLayout());

        JPanel filtrosPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        filtrosPanel.setBorder(BorderFactory.createTitledBorder("Filtros de Búsqueda"));

        JLabel categoriaLabel = new JLabel("Categoría:");
        categoriaComboBox = new JComboBox<>();
        cargarCategorias();

        JLabel modeloLabel = new JLabel("Modelo:");
        modeloComboBox = new JComboBox<>();
        cargarModelos();

        JLabel marcaLabel = new JLabel("Marca:");
        marcaComboBox = new JComboBox<>();
        cargarMarcas();

        JLabel proveedorLabel = new JLabel("Proveedor:");
        proveedorComboBox = new JComboBox<>();
        cargarProveedores();

        JLabel busquedaLabel = new JLabel("Buscar por código o nombre:");
        busquedaTextField = new JTextField();

        filtrosPanel.add(categoriaLabel);
        filtrosPanel.add(categoriaComboBox);
        filtrosPanel.add(modeloLabel);
        filtrosPanel.add(modeloComboBox);
        filtrosPanel.add(marcaLabel);
        filtrosPanel.add(marcaComboBox);
        filtrosPanel.add(proveedorLabel);
        filtrosPanel.add(proveedorComboBox);
        filtrosPanel.add(busquedaLabel);
        filtrosPanel.add(busquedaTextField);

        JPanel buscarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton buscarButton = new JButton("Buscar");
        buscarButton.addActionListener(this::buscarProductos);
        buscarButton.setPreferredSize(new Dimension(100, 30)); 
        buscarPanel.add(buscarButton);

        eliminarButton = new JButton("Eliminar");
        eliminarButton.setEnabled(false); 
        eliminarButton.addActionListener(this::eliminarProducto);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(eliminarButton);

        tablaProductos = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Código", "Nombre", "Categoría", "Modelo", "Marca", "Proveedor", "Cantidad", "Precio"}
        ));
        JScrollPane tablaScroll = new JScrollPane(tablaProductos);

        JPanel panelBusqueda = new JPanel();
        panelBusqueda.setLayout(new BoxLayout(panelBusqueda, BoxLayout.Y_AXIS));  
        panelBusqueda.add(filtrosPanel);
        panelBusqueda.add(buscarPanel);

        add(panelBusqueda, BorderLayout.NORTH); 
        add(tablaScroll, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        tablaProductos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (tablaProductos.getSelectedRow() != -1) {
                    eliminarButton.setEnabled(true);
                }
            }
        });
    }

    private void cargarCategorias() {
        CategoriaDaoImpl CrudCategoria = new CategoriaDaoImpl();
        java.util.List<Categoria> categorias = CrudCategoria.getCategoriasComboBox();

        categoriaComboBox.removeAllItems();

        categoriaComboBox.addItem(new Categoria(0, "Seleccione...", TipoCategoria.BICICLETA));

        for (Categoria categoria : categorias) {
            categoriaComboBox.addItem(categoria);
        }
    }

    private void cargarModelos() {
        ModeloDaoImpl CrudModelo = new ModeloDaoImpl();
        java.util.List<Modelo> modelos = CrudModelo.getModelosComboBox();

        modeloComboBox.removeAllItems();

        modeloComboBox.addItem(new Modelo("0", "Seleccione modelo"));

        for (Modelo modelo : modelos) {
            modeloComboBox.addItem(modelo);
        }
    }

    private void cargarProveedores() {
        ProveedorDaoImpl CrudProveedor = new ProveedorDaoImpl();
        java.util.List<Proveedor> proveedores = CrudProveedor.getProveedoresComboBox();

        proveedorComboBox.removeAllItems();

        proveedorComboBox.addItem(new Proveedor("0", "Seleccione proveedor"));

        for (Proveedor proveedor : proveedores) {
            proveedorComboBox.addItem(proveedor);
        }
    }

    private void cargarMarcas() {
        MarcaDaoImpl CrudMarca = new MarcaDaoImpl();
        java.util.List<Marca> marcas = CrudMarca.getMarcasComboBox();

        marcaComboBox.removeAllItems();

        marcaComboBox.addItem(new Marca("0", "Seleccione marca")); 

        for (Marca marca : marcas) {
            marcaComboBox.addItem(marca);
        }
    }

    private void buscarProductos(ActionEvent event) {
        String codigoProducto = busquedaTextField.getText().trim();
        String nombreProducto = busquedaTextField.getText().trim();

        Categoria categoriaSeleccionada = (Categoria) categoriaComboBox.getSelectedItem();
        String codigoCategoria = categoriaSeleccionada != null ? categoriaSeleccionada.getCodigo() : "";

        Modelo modeloSeleccionado = (Modelo) modeloComboBox.getSelectedItem();
        String codigoModelo = modeloSeleccionado != null ? modeloSeleccionado.getCodigo() : "";

        Marca marcaSeleccionada = (Marca) marcaComboBox.getSelectedItem();
        String codigoMarca = marcaSeleccionada != null ? marcaSeleccionada.getCodigo() : "";

        Proveedor proveedorSeleccionado = (Proveedor) proveedorComboBox.getSelectedItem();
        String codigoProveedor = proveedorSeleccionado != null ? proveedorSeleccionado.getCodigo() : "";

        int cantidad = 0; 

        ProductoDaoImpl CrudProducto = new ProductoDaoImpl();
        List<Producto> productos = CrudProducto.getProductos(codigoProducto, nombreProducto,
                codigoCategoria, codigoModelo, codigoProveedor, cantidad, codigoMarca);

        DefaultTableModel modeloTabla = (DefaultTableModel) tablaProductos.getModel();
        modeloTabla.setRowCount(0);

        for (Producto producto : productos) {
            
            modeloTabla.addRow(new Object[]{
                producto.getCodigo(), 
                producto.getNombre(), 
                producto.getCategoria().getNombre(), 
                producto.getModelo().getModelo(), 
                producto.getModelo().getMarca().getMarca(), 
                producto.getProveedor().getNombreFantasia(), 
                producto.getCantidad(), 
                producto.getPrecio() 
            });
        }
    }

    private void eliminarProducto(ActionEvent event) {
        int filaSeleccionada = tablaProductos.getSelectedRow();
        if (filaSeleccionada != -1) {

            String codigoProducto = (String) tablaProductos.getValueAt(filaSeleccionada, 0); 
            String nombreProducto = (String) tablaProductos.getValueAt(filaSeleccionada, 1); 
            
            ProductoDaoImpl CrudProducto = new ProductoDaoImpl();
            CrudProducto.eliminarProducto(codigoProducto, nombreProducto);

            JOptionPane.showMessageDialog(this, "Producto eliminado exitosamente.");
            buscarProductos(event); 
            eliminarButton.setEnabled(false); 
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
            java.util.logging.Logger.getLogger(BajaProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BajaProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BajaProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BajaProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BajaProductos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
