package principal.vista.gente.cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableModel;
import principal.vista.gente.HomeMenuGente;
import modelo.cliente.Cliente;
import repositorio.dao.cliente.ClienteDaoImpl;
import java.util.List;

/**
 *
 * @author rodri
 */

public class BajaCliente extends javax.swing.JFrame {

    private JTextField codigoField, nombreField, apellidoField;
    private JTable clienteTable;
    private JButton eliminarButton;

    public BajaCliente() {
        setTitle("Baja Cliente");
        setSize(600, 300);  
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  
        setLayout(new BorderLayout(10, 10));

        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Baja Cliente", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));  
        inputPanel.add(new JLabel("Código del Cliente:"));
        codigoField = new JTextField();
        inputPanel.add(codigoField);

        inputPanel.add(new JLabel("Nombre del Cliente:"));
        nombreField = new JTextField();
        inputPanel.add(nombreField);

        inputPanel.add(new JLabel("Apellido del Cliente:"));
        apellidoField = new JTextField();
        inputPanel.add(apellidoField);

        JButton buscarButton = new JButton("Buscar");
        buscarButton.addActionListener(new BuscarButtonListener());
        inputPanel.add(buscarButton);  

        searchPanel.add(inputPanel, BorderLayout.NORTH);

        String[] columnNames = {"Código", "CUIL", "Nombre", "Apellido", "DNI", "Teléfono", "Email"};
        Object[][] data = new Object[0][7]; 
        clienteTable = new JTable(data, columnNames);
        clienteTable.setEnabled(false);  
        clienteTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  
        clienteTable.addMouseListener(new PedidosTableMouseListener()); 
        JScrollPane scrollPane = new JScrollPane(clienteTable);
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
                new HomeMenuGente().setVisible(true);
                dispose();
            }
        });
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);

        configurarTabla();
    }

    private void configurarTabla() {
        clienteTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  
        clienteTable.setEnabled(true); 
        clienteTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  

        clienteTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = clienteTable.getSelectedRow();
            if (selectedRow != -1) {
                eliminarButton.setEnabled(true);
            }
        });
    }

    private class PedidosTableMouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            int selectedRow = clienteTable.getSelectedRow(); 
            if (selectedRow != -1) {
                String codigo = clienteTable.getValueAt(selectedRow, 0).toString();
                eliminarButton.setEnabled(true); 
            }
        }
    }

    private class BuscarButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String codigo = codigoField.getText();
                String nombre = nombreField.getText();
                String apellido = apellidoField.getText();

                ClienteDaoImpl clienteDao = new ClienteDaoImpl();

                List<Cliente> clientes = clienteDao.getClientes(codigo, nombre, apellido);

                if (clientes != null && !clientes.isEmpty()) {
                    Object[][] data = new Object[clientes.size()][7];  
                    for (int i = 0; i < clientes.size(); i++) {
                        Cliente cliente = clientes.get(i);
                        data[i][0] = cliente.getCodigo();  
                        data[i][1] = cliente.getCuil();    
                        data[i][2] = cliente.getNombre();  
                        data[i][3] = cliente.getApellido(); 
                        data[i][4] = cliente.getDni();     
                        data[i][5] = cliente.getTelefono(); 
                        data[i][6] = cliente.getEmail();  
                    }

                    clienteTable.setModel(new DefaultTableModel(data, new String[]{"Código", "CUIL", "Nombre", "Apellido", "DNI", "Teléfono", "Email"}));
                    eliminarButton.setEnabled(false);  
                } else {
                    Object[][] data = new Object[0][7]; 
                    clienteTable.setModel(new DefaultTableModel(data, new String[]{"Código", "CUIL", "Nombre", "Apellido", "DNI", "Teléfono", "Email"}));
                    JOptionPane.showMessageDialog(null, "Cliente(s) no encontrado(s).", "Error", JOptionPane.ERROR_MESSAGE);
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
                int selectedRow = clienteTable.getSelectedRow(); 

                if (selectedRow != -1) {  
                    String codigo = clienteTable.getValueAt(selectedRow, 0).toString(); 

                    ClienteDaoImpl clienteDao = new ClienteDaoImpl();

                    Cliente cliente = clienteDao.obtenerCliente(codigo, null, null);

                    if (cliente != null) {
                        int confirmacion = JOptionPane.showConfirmDialog(null,
                                "¿Está seguro de que desea eliminar este cliente?",
                                "Confirmar Eliminación",
                                JOptionPane.YES_NO_OPTION);

                        if (confirmacion == JOptionPane.YES_OPTION) {
                            clienteDao.eliminarCliente(codigo, null, null);
                            JOptionPane.showMessageDialog(null, "El cliente ha sido eliminado exitosamente.");

                            clienteTable.setModel(new DefaultTableModel(
                                    new Object[0][7], new String[]{"Código", "CUIL", "Nombre", "Apellido", "DNI", "Teléfono", "Email"}
                            ));
                            codigoField.setText("");
                            nombreField.setText("");
                            apellidoField.setText("");
                            eliminarButton.setEnabled(false);  
                        } else {
                            JOptionPane.showMessageDialog(null, "Eliminación cancelada.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione un cliente de la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
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
            java.util.logging.Logger.getLogger(BajaCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BajaCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BajaCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BajaCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BajaCliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
