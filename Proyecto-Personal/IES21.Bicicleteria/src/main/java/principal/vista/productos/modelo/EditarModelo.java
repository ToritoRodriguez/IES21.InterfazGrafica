package principal.vista.productos.modelo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import repositorio.dao.modelo.ModeloDaoImpl;
import modelo.producto.marca.Marca;
import modelo.producto.marca.Modelo;
import modelo.producto.marca.Rodado;
import principal.vista.productos.HomeMenuProductos;

/**
 *
 * @author rodri
 */

public class EditarModelo extends javax.swing.JFrame {

    private JTextField codigoField;
    private JTable modeloTable;
    private JButton buscarButton, modificarButton;

    public EditarModelo() {
        setTitle("Modificar Modelo");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Modificar Modelo", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new BorderLayout(10, 10));
        inputPanel.add(new JLabel("Código del Modelo:"), BorderLayout.WEST);
        codigoField = new JTextField();
        inputPanel.add(codigoField, BorderLayout.CENTER);

        buscarButton = new JButton("Buscar");
        buscarButton.addActionListener(new BuscarButtonListener());
        inputPanel.add(buscarButton, BorderLayout.EAST);

        searchPanel.add(inputPanel, BorderLayout.NORTH);

        String[] columnNames = {"Código", "Modelo", "Marca", "Descripción", "Rodado"};
        Object[][] data = new Object[1][5]; 
        modeloTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(modeloTable);
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
                String input = codigoField.getText().trim();

                if (input.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un código de modelo.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ModeloDaoImpl modeloDao = new ModeloDaoImpl();
                Modelo modelo = modeloDao.obtenerModelo(input, null, null, null); 

                if (modelo != null) {
                    Object[][] data = {
                        {modelo.getCodigo(), modelo.getModelo(), modelo.getMarca().getMarca(), modelo.getDescripcion(), modelo.getRodado().toString()}
                    };
                    modeloTable.setModel(new javax.swing.table.DefaultTableModel(
                            data,
                            new String[]{"Código", "Modelo", "Marca", "Descripción", "Rodado"}
                    ));
                    modificarButton.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Modelo no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
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
                int selectedRow = modeloTable.getSelectedRow();

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione un modelo para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                modeloTable.getCellEditor().stopCellEditing();
                String codigoModelo = (String) modeloTable.getValueAt(selectedRow, 0);
                String nuevoNombre = (String) modeloTable.getValueAt(selectedRow, 1);
                String nuevaMarca = (String) modeloTable.getValueAt(selectedRow, 2);
                String nuevaDescripcion = (String) modeloTable.getValueAt(selectedRow, 3);
                String nuevoRodadoStr = (String) modeloTable.getValueAt(selectedRow, 4);

                if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El nombre del modelo no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Rodado nuevoRodado = Rodado.valueOf(nuevoRodadoStr);
                Marca marca = new Marca(nuevaMarca, nuevaMarca); 

                Modelo modeloModificado = new Modelo(codigoModelo, nuevoNombre, marca, nuevaDescripcion, nuevoRodado);

                ModeloDaoImpl modeloDao = new ModeloDaoImpl();
                modeloDao.modificarModelo(codigoModelo, modeloModificado);

                JOptionPane.showMessageDialog(null, "Modelo modificado con éxito.");

                Object[][] data = {
                    {modeloModificado.getCodigo(), modeloModificado.getModelo(), modeloModificado.getMarca().getMarca(), modeloModificado.getDescripcion(), modeloModificado.getRodado().toString()}
                };
                modeloTable.setModel(new javax.swing.table.DefaultTableModel(
                        data,
                        new String[]{"Código", "Modelo", "Marca", "Descripción", "Rodado"}
                ));
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
            java.util.logging.Logger.getLogger(EditarModelo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditarModelo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditarModelo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditarModelo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditarModelo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
