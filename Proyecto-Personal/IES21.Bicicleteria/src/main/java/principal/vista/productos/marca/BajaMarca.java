package principal.vista.productos.marca;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import repositorio.dao.marca.MarcaDaoImpl;
import modelo.producto.marca.Marca;
import principal.vista.productos.HomeMenuProductos;

/**
 *
 * @author rodri
 */

public class BajaMarca extends javax.swing.JFrame {

    private JTextField nombreMarcaField, codigoField;
    private JTable marcaTable;
    private JButton eliminarButton;

    public BajaMarca() {
        setTitle("Baja Marca");
        setSize(600, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Baja Marca", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));  

        inputPanel.add(new JLabel("Código:"));
        codigoField = new JTextField();
        inputPanel.add(codigoField);

        inputPanel.add(new JLabel("Nombre Marca:"));
        nombreMarcaField = new JTextField();
        inputPanel.add(nombreMarcaField);

        JButton buscarButton = new JButton("Buscar");
        buscarButton.addActionListener(new BuscarButtonListener());
        inputPanel.add(buscarButton);  

        searchPanel.add(inputPanel, BorderLayout.NORTH);

        String[] columnNames = {"Código", "Marca"};
        Object[][] data = new Object[0][2];  
        marcaTable = new JTable(data, columnNames);
        configurarTabla();
        JScrollPane scrollPane = new JScrollPane(marcaTable);
        searchPanel.add(scrollPane, BorderLayout.CENTER);

        eliminarButton = new JButton("Eliminar");
        eliminarButton.setForeground(Color.WHITE);
        eliminarButton.setBackground(Color.RED);
        eliminarButton.setEnabled(false); 
        eliminarButton.addActionListener(new EliminarButtonListener());
        searchPanel.add(eliminarButton, BorderLayout.SOUTH);

        add(searchPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton backButton = new JButton("Volver");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HomeMenuProductos().setVisible(true);
                dispose();
            }
        });
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void configurarTabla() {
        marcaTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        marcaTable.setEnabled(true);

        marcaTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent  e) {
                int selectedRow = marcaTable.getSelectedRow();
                if (selectedRow != -1) {
                    eliminarButton.setEnabled(true);
                }
            }
        });
    }

    private class BuscarButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String nombreMarca = nombreMarcaField.getText();
                String codigo = codigoField.getText();

                MarcaDaoImpl marcaDao = new MarcaDaoImpl();
                java.util.List<Marca> marcas = marcaDao.getMarcas(codigo, nombreMarca);

                if (!marcas.isEmpty()) {
                    Object[][] data = new Object[marcas.size()][2];
                    for (int i = 0; i < marcas.size(); i++) {
                        Marca marca = marcas.get(i);
                        data[i] = new Object[]{
                            marca.getCodigo(), 
                            marca.getMarca() 
                        };
                    }
                    marcaTable.setModel(new DefaultTableModel(data, new String[]{"Código", "Marca"}));
                    eliminarButton.setEnabled(false);  
                } else {
                    Object[][] data = new Object[0][2]; 
                    marcaTable.setModel(new DefaultTableModel(data, new String[]{"Código", "Marca"}));
                    JOptionPane.showMessageDialog(null, "No se encontraron marcas con esos parámetros.", "Resultado de búsqueda", JOptionPane.INFORMATION_MESSAGE);
                    eliminarButton.setEnabled(false);  
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class EliminarButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int selectedRow = marcaTable.getSelectedRow();
                String codigo = marcaTable.getValueAt(selectedRow, 0).toString();

                MarcaDaoImpl marcaDao = new MarcaDaoImpl();
                Marca marca = marcaDao.obtenerMarca(codigo, null);

                if (marca != null) {
                    int confirmacion = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar esta marca?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

                    if (confirmacion == JOptionPane.YES_OPTION) {
                        marcaDao.eliminarMarca(codigo, null);

                        JOptionPane.showMessageDialog(null, "La marca ha sido eliminada exitosamente.");

                        marcaTable.setModel(new DefaultTableModel(new Object[0][2], new String[]{"Código", "Marca"}));
                        nombreMarcaField.setText("");
                        codigoField.setText("");
                        eliminarButton.setEnabled(false); 
                    } else {
                        JOptionPane.showMessageDialog(null, "Eliminación cancelada.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Marca no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
                    eliminarButton.setEnabled(false); 
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
            java.util.logging.Logger.getLogger(BajaMarca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BajaMarca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BajaMarca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BajaMarca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BajaMarca().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
