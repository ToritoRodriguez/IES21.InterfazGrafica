package principal.vista.gente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import principal.vista.gente.cliente.AltaCliente;
import principal.vista.gente.cliente.BajaCliente;
import principal.vista.gente.cliente.EditarCliente;
import principal.vista.gente.cliente.ListarClientes;
import principal.vista.gente.proveedor.AltaProveedor;
import principal.vista.gente.proveedor.BajaProveedor;
import principal.vista.gente.proveedor.EditarProveedor;
import principal.vista.gente.proveedor.ListarProveedor;
import principal.vista.gente.vendedor.AltaVendedor;
import principal.vista.gente.vendedor.BajaVendedor;
import principal.vista.gente.vendedor.EditarVendedor;
import principal.vista.gente.vendedor.ListarVendedor;

/**
 *
 * @author rodri
 */

public class HomeMenuGente extends javax.swing.JFrame {

    private JPanel optionsPanel;
    
    /**
     * Creates new form PrincipalCliente
     */
    public HomeMenuGente() {
        setTitle("Gente Menu");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Creamos el JMenuBar
        JMenuBar menuBar = new JMenuBar();

        // Creamos los menús
        JMenu clientesMenu = new JMenu("Clientes");
        JMenu vendedoresMenu = new JMenu("Vendedores");
        JMenu proveedoresMenu = new JMenu("Proveedores");

        // Agregamos los menús al JMenuBar
        menuBar.add(clientesMenu);
        menuBar.add(vendedoresMenu);
        menuBar.add(proveedoresMenu);

        // Agregamos el JMenuBar a la ventana
        setJMenuBar(menuBar);

        // Creamos opciones de menú para cada categoría
        JMenuItem altaClientes = new JMenuItem("Alta");
        JMenuItem bajaClientes = new JMenuItem("Baja");
        JMenuItem modificacionClientes = new JMenuItem("Modificación");
        JMenuItem listadoClientes = new JMenuItem("Listado");

        JMenuItem altaVendedores = new JMenuItem("Alta");
        JMenuItem bajaVendedores = new JMenuItem("Baja");
        JMenuItem modificacionVendedores = new JMenuItem("Modificación");
        JMenuItem listadoVendedores = new JMenuItem("Listado");

        JMenuItem altaProveedores = new JMenuItem("Alta");
        JMenuItem bajaProveedores = new JMenuItem("Baja");
        JMenuItem modificacionProveedores = new JMenuItem("Modificación");
        JMenuItem listadoProveedores = new JMenuItem("Listado");

        // Agregamos las opciones a los menús correspondientes
        clientesMenu.add(altaClientes);
        clientesMenu.add(bajaClientes);
        clientesMenu.add(modificacionClientes);
        clientesMenu.add(listadoClientes);

        vendedoresMenu.add(altaVendedores);
        vendedoresMenu.add(bajaVendedores);
        vendedoresMenu.add(modificacionVendedores);
        vendedoresMenu.add(listadoVendedores);

        proveedoresMenu.add(altaProveedores);
        proveedoresMenu.add(bajaProveedores);
        proveedoresMenu.add(modificacionProveedores);
        proveedoresMenu.add(listadoProveedores);

        // Panel central
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(4, 1));

        add(optionsPanel, BorderLayout.CENTER);

        // Clientes
        altaClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AltaCliente().setVisible(true);
                dispose(); 
            }
        });
        
        bajaClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BajaCliente().setVisible(true);
                dispose(); 
            }
        });

        modificacionClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EditarCliente().setVisible(true);
                dispose();
            }
        });
        
        listadoClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ListarClientes().setVisible(true);
                dispose();
            }
        });

        // Vendedores
        altaVendedores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AltaVendedor().setVisible(true);
                dispose();
            }
        });

        bajaVendedores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BajaVendedor().setVisible(true);
                dispose();
            }
        });

        modificacionVendedores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EditarVendedor().setVisible(true);
                dispose();
            }
        });

        listadoVendedores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ListarVendedor().setVisible(true);
                dispose();
            }
        });
        
        // Proveedores
        altaProveedores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AltaProveedor().setVisible(true);
                dispose();
            }
        });

        bajaProveedores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BajaProveedor().setVisible(true);
                dispose();
            }
        });

        modificacionProveedores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EditarProveedor().setVisible(true);
                dispose();
            }
        });

        listadoProveedores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ListarProveedor().setVisible(true);
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
            .addGap(0, 628, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 515, Short.MAX_VALUE)
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
            java.util.logging.Logger.getLogger(HomeMenuGente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomeMenuGente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomeMenuGente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomeMenuGente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomeMenuGente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
