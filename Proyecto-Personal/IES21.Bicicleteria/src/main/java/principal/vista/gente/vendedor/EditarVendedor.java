package principal.vista.gente.vendedor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.AbstractTableModel;
import principal.vista.gente.HomeMenuGente;
import repositorio.dao.vendedor.VendedorDaoImpl;
import modelo.vendedor.Vendedor;

/**
 *
 * @author rodri
 */

public class EditarVendedor extends javax.swing.JFrame {

    private JTextField codigoField;
    private JTable vendedorTable;
    private JButton buscarButton, modificarButton;

    public EditarVendedor() {
        setTitle("Modificar Vendedor");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  
        setLayout(new BorderLayout(10, 10));

        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Modificar Vendedor", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new BorderLayout(10, 10));
        inputPanel.add(new JLabel("Código del Vendedor:"), BorderLayout.WEST);
        codigoField = new JTextField();
        inputPanel.add(codigoField, BorderLayout.CENTER);

        buscarButton = new JButton("Buscar");
        buscarButton.addActionListener(new BuscarButtonListener());
        inputPanel.add(buscarButton, BorderLayout.EAST);

        searchPanel.add(inputPanel, BorderLayout.NORTH);

        String[] columnNames = {"Campo", "Valor"};
        Object[][] data = {
            {"CUIT", ""},
            {"Nombre", ""},
            {"Apellido", ""},
            {"DNI", ""},
            {"Teléfono", ""},
            {"Email", ""},
            {"Sucursal", ""}
        };

        vendedorTable = new JTable(new VendedorTableModel(data, columnNames));
        JScrollPane scrollPane = new JScrollPane(vendedorTable);
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

    private class VendedorTableModel extends AbstractTableModel {

        private Object[][] data;
        private String[] columnNames;

        public VendedorTableModel(Object[][] data, String[] columnNames) {
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

                VendedorDaoImpl vendedorDao = new VendedorDaoImpl();

                Vendedor vendedor = vendedorDao.obtenerVendedor(codigo, null, null);

                if (vendedor != null) {
                    vendedorTable.setValueAt(vendedor.getCuit(), 0, 1);
                    vendedorTable.setValueAt(vendedor.getNombre(), 1, 1);
                    vendedorTable.setValueAt(vendedor.getApellido(), 2, 1);
                    vendedorTable.setValueAt(vendedor.getDni(), 3, 1);
                    vendedorTable.setValueAt(vendedor.getTelefono(), 4, 1);
                    vendedorTable.setValueAt(vendedor.getEmail(), 5, 1);
                    vendedorTable.setValueAt(vendedor.getSucursal(), 6, 1);
                    modificarButton.setEnabled(true);  
                } else {
                    JOptionPane.showMessageDialog(null, "Vendedor no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
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

                VendedorDaoImpl vendedorDao = new VendedorDaoImpl();

                Vendedor vendedorExistente = vendedorDao.obtenerVendedor(codigo, null, null);

                if (vendedorExistente != null) {
                    String nuevoCuit = (String) vendedorTable.getValueAt(0, 1); 
                    if (!nuevoCuit.isEmpty()) {
                        vendedorExistente.setCuit(nuevoCuit);
                    }

                    String nuevoNombre = (String) vendedorTable.getValueAt(1, 1); 
                    if (!nuevoNombre.isEmpty()) {
                        vendedorExistente.setNombre(nuevoNombre);
                    }

                    String nuevoApellido = (String) vendedorTable.getValueAt(2, 1); 
                    if (!nuevoApellido.isEmpty()) {
                        vendedorExistente.setApellido(nuevoApellido);
                    }

                    String nuevoDni = (String) vendedorTable.getValueAt(3, 1);
                    if (!nuevoDni.isEmpty()) {
                        vendedorExistente.setDni(nuevoDni);
                    }

                    String nuevoTelefono = (String) vendedorTable.getValueAt(4, 1); 
                    if (!nuevoTelefono.isEmpty()) {
                        vendedorExistente.setTelefono(nuevoTelefono);
                    }

                    String nuevoEmail = (String) vendedorTable.getValueAt(5, 1); 
                    if (!nuevoEmail.isEmpty()) {
                        vendedorExistente.setEmail(nuevoEmail);
                    }

                    vendedorTable.getCellEditor().stopCellEditing();
                    String nuevaSucursal = (String) vendedorTable.getValueAt(6, 1);
                    if (!nuevaSucursal.isEmpty()) {
                        vendedorExistente.setSucursal(nuevaSucursal);
                    }

                    vendedorDao.modificarVendedor(codigo, vendedorExistente);
                    JOptionPane.showMessageDialog(null, "Vendedor modificado con éxito.");
                } else {
                    JOptionPane.showMessageDialog(null, "Vendedor no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
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
            java.util.logging.Logger.getLogger(EditarVendedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditarVendedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditarVendedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditarVendedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditarVendedor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
