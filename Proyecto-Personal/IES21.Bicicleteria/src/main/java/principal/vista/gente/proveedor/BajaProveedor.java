package principal.vista.gente.proveedor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import principal.vista.gente.HomeMenuGente;
import repositorio.dao.proveedor.ProveedorDaoImpl;
import modelo.proveedor.Proveedor;

/**
 *
 * @author rodri
 */

public class BajaProveedor extends javax.swing.JFrame {

    private JTextField codigoField, nombreFantasiaField, nombreField, apellidoField;
    private JTable proveedorTable;
    private JButton eliminarButton;

    public BajaProveedor() {
        setTitle("Baja Proveedor");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setLayout(new BorderLayout(10, 10));

        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Baja Proveedor", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS)); 
        addSearchField(searchPanel, "Código del Proveedor:", codigoField = new JTextField(20));
        addSearchField(searchPanel, "Nombre Fantasía:", nombreFantasiaField = new JTextField(20));
        addSearchField(searchPanel, "Nombre:", nombreField = new JTextField(20));
        addSearchField(searchPanel, "Apellido:", apellidoField = new JTextField(20));

        JButton buscarButton = new JButton("Buscar");
        buscarButton.addActionListener(new BuscarButtonListener());
        searchPanel.add(buscarButton);

        String[] columnNames = {"Código", "CUIT", "Nombre Fantasía", "Nombre", "Apellido", "DNI", "Teléfono", "Email"};
        proveedorTable = new JTable(new Object[1][8], columnNames); 
        JScrollPane scrollPane = new JScrollPane(proveedorTable);
        searchPanel.add(scrollPane);

        configurarTabla();

        eliminarButton = new JButton("Eliminar");
        eliminarButton.setForeground(Color.WHITE);
        eliminarButton.setBackground(Color.RED);
        eliminarButton.setEnabled(false); 
        eliminarButton.addActionListener(new EliminarButtonListener());
        searchPanel.add(eliminarButton);

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

    private void addSearchField(JPanel panel, String labelText, JTextField textField) {
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.add(new JLabel(labelText));
        inputPanel.add(textField);
        panel.add(inputPanel);
    }

    private void configurarTabla() {
        proveedorTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        proveedorTable.setEnabled(true);

        proveedorTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = proveedorTable.getSelectedRow();
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
                String codigo = codigoField.getText();
                String nombreFantasia = nombreFantasiaField.getText();
                String nombre = nombreField.getText();
                String apellido = apellidoField.getText();

                ProveedorDaoImpl proveedorDao = new ProveedorDaoImpl();
                java.util.List<Proveedor> proveedores = proveedorDao.getProveedores(codigo, nombre, apellido, nombreFantasia);

                if (!proveedores.isEmpty()) {
                    Object[][] data = new Object[proveedores.size()][8];
                    for (int i = 0; i < proveedores.size(); i++) {
                        Proveedor proveedor = proveedores.get(i);
                        data[i] = new Object[]{
                            proveedor.getCodigo(),
                            proveedor.getCuit(),
                            proveedor.getNombreFantasia(),
                            proveedor.getNombre(),
                            proveedor.getApellido(),
                            proveedor.getDni(),
                            proveedor.getTelefono(),
                            proveedor.getEmail()
                        };
                    }
                    proveedorTable.setModel(new DefaultTableModel(data, new String[]{"Código", "CUIT", "Nombre Fantasía", "Nombre", "Apellido", "DNI", "Teléfono", "Email"}));
                    eliminarButton.setEnabled(false);  
                } else {
                    Object[][] data = new Object[0][8];
                    proveedorTable.setModel(new DefaultTableModel(data, new String[]{"Código", "CUIT", "Nombre Fantasía", "Nombre", "Apellido", "DNI", "Teléfono", "Email"}));
                    JOptionPane.showMessageDialog(null, "No se encontraron proveedores con esos parámetros.", "Resultado de búsqueda", JOptionPane.INFORMATION_MESSAGE);
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
                int selectedRow = proveedorTable.getSelectedRow();
                String codigo = proveedorTable.getValueAt(selectedRow, 0).toString();

                ProveedorDaoImpl proveedorDao = new ProveedorDaoImpl();
                Proveedor proveedor = proveedorDao.obtenerProveedor(codigo, null);

                if (proveedor != null) {
                    int confirmacion = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar este proveedor?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

                    if (confirmacion == JOptionPane.YES_OPTION) {
                        proveedorDao.eliminarProveedor(codigo, null, null, null);
                        JOptionPane.showMessageDialog(null, "El proveedor ha sido eliminado exitosamente.");

                        proveedorTable.setModel(new DefaultTableModel(new Object[0][8], new String[]{"Código", "CUIT", "Nombre Fantasía", "Nombre", "Apellido", "DNI", "Teléfono", "Email"}));
                        codigoField.setText("");
                        nombreFantasiaField.setText("");
                        nombreField.setText("");
                        apellidoField.setText("");
                        eliminarButton.setEnabled(false); 
                    } else {
                        JOptionPane.showMessageDialog(null, "Eliminación cancelada.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Proveedor no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
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
            java.util.logging.Logger.getLogger(BajaProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BajaProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BajaProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BajaProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BajaProveedor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
