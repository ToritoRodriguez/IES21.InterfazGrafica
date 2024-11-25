package principal.vista.productos.categoria;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import repositorio.dao.categoria.CategoriaDaoImpl;
import principal.vista.productos.HomeMenuProductos;
import modelo.producto.Categoria;

/**
 *
 * @author rodri
 */

public class BajaCategoria extends javax.swing.JFrame {

    private JTextField codigoField, nombreField;
    private JTable categoriaTable;
    private JButton eliminarButton;

    public BajaCategoria() {
        setTitle("Baja Categoría");
        setSize(600, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Baja Categoría", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        inputPanel.add(new JLabel("Código:"));
        codigoField = new JTextField();
        inputPanel.add(codigoField);

        inputPanel.add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        inputPanel.add(nombreField);

        JButton buscarButton = new JButton("Buscar");
        buscarButton.addActionListener(new BuscarButtonListener());
        inputPanel.add(buscarButton);

        searchPanel.add(inputPanel, BorderLayout.NORTH);

        String[] columnNames = {"Código", "Nombre", "Tipo"};
        Object[][] data = new Object[0][3];  
        categoriaTable = new JTable(data, columnNames);
        configurarTabla();
        JScrollPane scrollPane = new JScrollPane(categoriaTable);
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
        categoriaTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        categoriaTable.setEnabled(true);

        categoriaTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = categoriaTable.getSelectedRow();
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
                String nombre = nombreField.getText();
                String codigo = codigoField.getText();

                CategoriaDaoImpl categoriaDao = new CategoriaDaoImpl();
                java.util.List<Categoria> categorias = categoriaDao.getCategorias(codigo, nombre, null);

                if (!categorias.isEmpty()) {
                    Object[][] data = new Object[categorias.size()][3];
                    for (int i = 0; i < categorias.size(); i++) {
                        Categoria categoria = categorias.get(i);
                        data[i] = new Object[]{
                            categoria.getCodigo(), 
                            categoria.getNombre(),
                            categoria.getTipo()
                        };
                    }
                    categoriaTable.setModel(new DefaultTableModel(data, new String[]{"Código", "Nombre", "Tipo"}));
                    eliminarButton.setEnabled(false);  
                } else {
                    Object[][] data = new Object[0][3]; 
                    categoriaTable.setModel(new DefaultTableModel(data, new String[]{"Código", "Nombre", "Tipo"}));
                    JOptionPane.showMessageDialog(null, "No se encontraron categorías con esos parámetros.", "Resultado de búsqueda", JOptionPane.INFORMATION_MESSAGE);
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
                int selectedRow = categoriaTable.getSelectedRow();
                String codigo = categoriaTable.getValueAt(selectedRow, 0).toString();

                CategoriaDaoImpl categoriaDao = new CategoriaDaoImpl();
                Categoria categoria = categoriaDao.obtenerCategoria(codigo, null, null);

                if (categoria != null) {
                    int confirmacion = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar esta categoría?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

                    if (confirmacion == JOptionPane.YES_OPTION) {
                        categoriaDao.eliminarCategoria(codigo, null, null);
                        JOptionPane.showMessageDialog(null, "La categoría ha sido eliminada exitosamente.");

                        categoriaTable.setModel(new DefaultTableModel(new Object[0][3], new String[]{"Código", "Nombre", "Tipo"}));
                        nombreField.setText("");
                        codigoField.setText("");
                        eliminarButton.setEnabled(false); 
                    } else {
                        JOptionPane.showMessageDialog(null, "Eliminación cancelada.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Categoría no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
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
            java.util.logging.Logger.getLogger(BajaCategoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BajaCategoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BajaCategoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BajaCategoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BajaCategoria().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
