package principal.vista.productos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import principal.vista.productos.marca.AltaMarca;
import principal.vista.productos.marca.BajaMarca;
import principal.vista.productos.marca.EditarMarca;
import principal.vista.productos.marca.ListarMarca;
import principal.vista.productos.modelo.AltaModelo;
import principal.vista.productos.modelo.BajaModelo;
import principal.vista.productos.modelo.EditarModelo;
import principal.vista.productos.modelo.ListarModelo;
import principal.vista.productos.categoria.AltaCategoria;
import principal.vista.productos.categoria.BajaCategoria;
import principal.vista.productos.categoria.EditarCategoria;
import principal.vista.productos.categoria.ListarCategoria;
import principal.vista.productos.productos.AltaProductos;
import principal.vista.productos.productos.BajaProductos;
import principal.vista.productos.productos.EditarProductos;
import principal.vista.productos.productos.ListarProductos;

/**
 *
 * @author rodri
 */

public class HomeMenuProductos extends javax.swing.JFrame {

    private JPanel optionsPanel;

    public HomeMenuProductos() {
        setTitle("Menú de Productos");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear el JMenuBar
        JMenuBar menuBar = new JMenuBar();

        // Crear los menús
        JMenu productosMenu = new JMenu("Productos");
        JMenu marcasMenu = new JMenu("Marcas");
        JMenu modelosMenu = new JMenu("Modelos");
        JMenu categoriasMenu = new JMenu("Categorías");

        // Agregar los menús al JMenuBar
        menuBar.add(productosMenu);
        menuBar.add(marcasMenu);
        menuBar.add(modelosMenu);
        menuBar.add(categoriasMenu);

        // Agregar el JMenuBar a la ventana
        setJMenuBar(menuBar);

        // Crear opciones de menú para cada categoría
        JMenuItem altaProductos = new JMenuItem("Alta");
        JMenuItem bajaProductos = new JMenuItem("Baja");
        JMenuItem modificacionProductos = new JMenuItem("Modificación");
        JMenuItem listadoProductos = new JMenuItem("Listado");

        JMenuItem altaMarcas = new JMenuItem("Alta");
        JMenuItem bajaMarcas = new JMenuItem("Baja");
        JMenuItem modificacionMarcas = new JMenuItem("Modificación");
        JMenuItem listadoMarcas = new JMenuItem("Listado");

        JMenuItem altaModelos = new JMenuItem("Alta");
        JMenuItem bajaModelos = new JMenuItem("Baja");
        JMenuItem modificacionModelos = new JMenuItem("Modificación");
        JMenuItem listadoModelos = new JMenuItem("Listado");

        JMenuItem altaCategorias = new JMenuItem("Alta");
        JMenuItem bajaCategorias = new JMenuItem("Baja");
        JMenuItem modificacionCategorias = new JMenuItem("Modificación");
        JMenuItem listadoCategorias = new JMenuItem("Listado");

        // Agregar las opciones a los menús correspondientes
        productosMenu.add(altaProductos);
        productosMenu.add(bajaProductos);
        productosMenu.add(modificacionProductos);
        productosMenu.add(listadoProductos);

        marcasMenu.add(altaMarcas);
        marcasMenu.add(bajaMarcas);
        marcasMenu.add(modificacionMarcas);
        marcasMenu.add(listadoMarcas);

        modelosMenu.add(altaModelos);
        modelosMenu.add(bajaModelos);
        modelosMenu.add(modificacionModelos);
        modelosMenu.add(listadoModelos);

        categoriasMenu.add(altaCategorias);
        categoriasMenu.add(bajaCategorias);
        categoriasMenu.add(modificacionCategorias);
        categoriasMenu.add(listadoCategorias);

        // Panel central
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(4, 1));

        add(optionsPanel, BorderLayout.CENTER);

        // Productos
        altaProductos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AltaProductos().setVisible(true);
                dispose();
            }
        });

        bajaProductos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BajaProductos().setVisible(true);
                dispose();
            }
        });

        modificacionProductos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EditarProductos().setVisible(true);
                dispose();
            }
        });

        listadoProductos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ListarProductos().setVisible(true);
                dispose();
            }
        });

        // Marcas
        altaMarcas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AltaMarca().setVisible(true);
                dispose();
            }
        });

        bajaMarcas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BajaMarca().setVisible(true);
                dispose();
            }
        });

        modificacionMarcas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EditarMarca().setVisible(true);
                dispose();
            }
        });

        listadoMarcas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ListarMarca().setVisible(true);
                dispose();
            }
        });

        // Modelos
        altaModelos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AltaModelo().setVisible(true);
                dispose();
            }
        });

        bajaModelos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BajaModelo().setVisible(true);
                dispose();
            }
        });

        modificacionModelos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EditarModelo().setVisible(true);
                dispose();
            }
        });

        listadoModelos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ListarModelo().setVisible(true);
                dispose();
            }
        });

        // Categorías
        altaCategorias.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AltaCategoria().setVisible(true);
                dispose();
            }
        });

        bajaCategorias.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BajaCategoria().setVisible(true);
                dispose();
            }
        });

        modificacionCategorias.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EditarCategoria().setVisible(true);
                dispose();
            }
        });

        listadoCategorias.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ListarCategoria().setVisible(true);
                dispose();
            }
        });
    }

    private class MenuActionListener implements ActionListener {

        private String option;

        public MenuActionListener(String option) {
            this.option = option;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            showOptions(option);
        }
    }

    private void showOptions(String option) {
        optionsPanel.removeAll();

        JLabel titleLabel = new JLabel("Opciones para " + option, SwingConstants.CENTER);
        optionsPanel.add(titleLabel);

        optionsPanel.revalidate();
        optionsPanel.repaint();
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
            java.util.logging.Logger.getLogger(HomeMenuProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomeMenuProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomeMenuProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomeMenuProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomeMenuProductos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
