package principal.vista.gente.cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.AbstractTableModel;
import principal.vista.gente.HomeMenuGente;
import repositorio.dao.cliente.ClienteDaoImpl;
import modelo.cliente.Cliente;

/**
 *
 * @author rodri
 */

public class EditarCliente extends javax.swing.JFrame {

    private JTextField codigoField;
    private JTable clienteTable;
    private JButton buscarButton, modificarButton;

    public EditarCliente() {
        setTitle("Modificar Cliente");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Modificar Cliente", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new BorderLayout(10, 10));
        inputPanel.add(new JLabel("Código del Cliente:"), BorderLayout.WEST);
        codigoField = new JTextField();
        inputPanel.add(codigoField, BorderLayout.CENTER);

        buscarButton = new JButton("Buscar");
        buscarButton.addActionListener(new BuscarButtonListener());
        inputPanel.add(buscarButton, BorderLayout.EAST);

        searchPanel.add(inputPanel, BorderLayout.NORTH);

        String[] columnNames = {"Campo", "Valor"};
        Object[][] data = {
            {"CUIL", ""},
            {"Nombre", ""},
            {"Apellido", ""},
            {"DNI", ""},
            {"Teléfono", ""},
            {"Email", ""}
        };

        clienteTable = new JTable(new ClienteTableModel(data, columnNames));
        JScrollPane scrollPane = new JScrollPane(clienteTable);
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

    private class ClienteTableModel extends AbstractTableModel {

        private Object[][] data;
        private String[] columnNames;

        public ClienteTableModel(Object[][] data, String[] columnNames) {
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

                ClienteDaoImpl clienteDao = new ClienteDaoImpl();

                Cliente cliente = clienteDao.obtenerCliente(codigo, null, null);

                if (cliente != null) {
                    clienteTable.setValueAt(cliente.getCuil(), 0, 1);
                    clienteTable.setValueAt(cliente.getNombre(), 1, 1);
                    clienteTable.setValueAt(cliente.getApellido(), 2, 1);
                    clienteTable.setValueAt(cliente.getDni(), 3, 1);
                    clienteTable.setValueAt(cliente.getTelefono(), 4, 1);
                    clienteTable.setValueAt(cliente.getEmail(), 5, 1);
                    modificarButton.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Cliente no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
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
                ClienteDaoImpl clienteDao = new ClienteDaoImpl();
                Cliente clienteExistente = clienteDao.obtenerCliente(codigo, null, null);

                if (clienteExistente != null) {

                    clienteTable.revalidate();
                    clienteTable.repaint();

                    String nuevoCuil = (String) clienteTable.getValueAt(0, 1);

                    String nuevoNombre = (String) clienteTable.getValueAt(1, 1);

                    String nuevoApellido = (String) clienteTable.getValueAt(2, 1);

                    String nuevoDni = (String) clienteTable.getValueAt(3, 1);

                    String nuevoTelefono = (String) clienteTable.getValueAt(4, 1);

                    clienteTable.getCellEditor().stopCellEditing();
                    String nuevoEmail = (String) clienteTable.getValueAt(5, 1);

                    if (!nuevoCuil.isEmpty()) {
                        clienteExistente.setCuil(nuevoCuil);
                    }
                    if (!nuevoNombre.isEmpty()) {
                        clienteExistente.setNombre(nuevoNombre);
                    }
                    if (!nuevoApellido.isEmpty()) {
                        clienteExistente.setApellido(nuevoApellido);
                    }
                    if (!nuevoDni.isEmpty()) {
                        clienteExistente.setDni(nuevoDni);
                    }
                    if (!nuevoTelefono.isEmpty()) {
                        clienteExistente.setTelefono(nuevoTelefono);
                    }
                    if (!nuevoEmail.isEmpty()) {
                        clienteExistente.setEmail(nuevoEmail);
                    }

                    clienteDao.modificarCliente(codigo, clienteExistente);
                    JOptionPane.showMessageDialog(null, "Cliente modificado con éxito.");

                    ((AbstractTableModel) clienteTable.getModel()).fireTableDataChanged();

                } else {
                    JOptionPane.showMessageDialog(null, "Cliente no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
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
            java.util.logging.Logger.getLogger(EditarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditarCliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
