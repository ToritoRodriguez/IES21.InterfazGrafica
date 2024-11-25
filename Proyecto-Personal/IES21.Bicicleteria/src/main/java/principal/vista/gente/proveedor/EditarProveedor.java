package principal.vista.gente.proveedor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.AbstractTableModel;
import principal.vista.gente.HomeMenuGente;
import repositorio.dao.proveedor.ProveedorDaoImpl;
import modelo.proveedor.Proveedor;

/**
 *
 * @author rodri
 */

public class EditarProveedor extends javax.swing.JFrame {

    private JTextField codigoField;
    private JTable proveedorTable;
    private JButton buscarButton, modificarButton;

    public EditarProveedor() {
        setTitle("Modificar Proveedor");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Modificar Proveedor", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new BorderLayout(10, 10));
        inputPanel.add(new JLabel("Código del Proveedor:"), BorderLayout.WEST);
        codigoField = new JTextField();
        inputPanel.add(codigoField, BorderLayout.CENTER);

        buscarButton = new JButton("Buscar");
        buscarButton.addActionListener(new BuscarButtonListener());
        inputPanel.add(buscarButton, BorderLayout.EAST);

        searchPanel.add(inputPanel, BorderLayout.NORTH);

        String[] columnNames = {"Campo", "Valor"};
        Object[][] data = {
            {"CUIT", ""},
            {"Nombre Fantasía", ""},
            {"Nombre", ""},
            {"Apellido", ""},
            {"DNI", ""},
            {"Teléfono", ""},
            {"Email", ""}
        };

        proveedorTable = new JTable(new ProveedorTableModel(data, columnNames));
        JScrollPane scrollPane = new JScrollPane(proveedorTable);
        searchPanel.add(scrollPane, BorderLayout.CENTER);

        modificarButton = new JButton("Guardar Cambios");
        modificarButton.setEnabled(false);
        modificarButton.addActionListener(new ModificarButtonListener());
        searchPanel.add(modificarButton, BorderLayout.SOUTH);

        add(searchPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton backButton = new JButton("Volver");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HomeMenuGente().setVisible(true);
                dispose();
            }
        });
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private class ProveedorTableModel extends AbstractTableModel {

        private Object[][] data;
        private String[] columnNames;

        public ProveedorTableModel(Object[][] data, String[] columnNames) {
            this.data = data;
            this.columnNames = columnNames;
        }

        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int columnIndex) {
            return columnNames[columnIndex];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == 1;  
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if (isCellEditable(rowIndex, columnIndex)) {
                data[rowIndex][columnIndex] = aValue;
                fireTableCellUpdated(rowIndex, columnIndex);
            }
        }
    }

    private class BuscarButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String codigo = codigoField.getText();
                ProveedorDaoImpl proveedorDao = new ProveedorDaoImpl();
                Proveedor proveedor = proveedorDao.obtenerProveedor(codigo, null);

                if (proveedor != null) {
                    proveedorTable.setValueAt(proveedor.getCuit(), 0, 1);
                    proveedorTable.setValueAt(proveedor.getNombreFantasia(), 1, 1);
                    proveedorTable.setValueAt(proveedor.getNombre(), 2, 1);
                    proveedorTable.setValueAt(proveedor.getApellido(), 3, 1);
                    proveedorTable.setValueAt(proveedor.getDni(), 4, 1);
                    proveedorTable.setValueAt(proveedor.getTelefono(), 5, 1);
                    proveedorTable.setValueAt(proveedor.getEmail(), 6, 1);
                    modificarButton.setEnabled(true);  
                } else {
                    JOptionPane.showMessageDialog(null, "Proveedor no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
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
                String codigo = codigoField.getText();
                ProveedorDaoImpl proveedorDao = new ProveedorDaoImpl();
                Proveedor proveedorExistente = proveedorDao.obtenerProveedor(codigo, null);

                if (proveedorExistente != null) {
                    String nuevoCuit = (String) proveedorTable.getValueAt(0, 1);
                    if (!nuevoCuit.isEmpty()) {
                        proveedorExistente.setCuit(nuevoCuit);
                    }

                    String nuevoNombreFantasia = (String) proveedorTable.getValueAt(1, 1);
                    if (!nuevoNombreFantasia.isEmpty()) {
                        proveedorExistente.setNombreFantasia(nuevoNombreFantasia);
                    }

                    String nuevoNombre = (String) proveedorTable.getValueAt(2, 1);
                    if (!nuevoNombre.isEmpty()) {
                        proveedorExistente.setNombre(nuevoNombre);
                    }

                    String nuevoApellido = (String) proveedorTable.getValueAt(3, 1);
                    if (!nuevoApellido.isEmpty()) {
                        proveedorExistente.setApellido(nuevoApellido);
                    }

                    String nuevoDni = (String) proveedorTable.getValueAt(4, 1);
                    if (!nuevoDni.isEmpty()) {
                        proveedorExistente.setDni(nuevoDni);
                    }

                    String nuevoTelefono = (String) proveedorTable.getValueAt(5, 1);
                    if (!nuevoTelefono.isEmpty()) {
                        proveedorExistente.setTelefono(nuevoTelefono);
                    }

                    proveedorTable.getCellEditor().stopCellEditing();
                    String nuevoEmail = (String) proveedorTable.getValueAt(6, 1);
                    if (!nuevoEmail.isEmpty()) {
                        proveedorExistente.setEmail(nuevoEmail);
                    }

                    proveedorDao.modificarProveedor(codigo, proveedorExistente);
                    JOptionPane.showMessageDialog(null, "Proveedor modificado con éxito.");
                } else {
                    JOptionPane.showMessageDialog(null, "Proveedor no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
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
            java.util.logging.Logger.getLogger(EditarProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditarProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditarProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditarProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditarProveedor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
