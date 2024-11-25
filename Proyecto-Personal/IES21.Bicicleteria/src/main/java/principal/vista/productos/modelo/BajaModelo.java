package principal.vista.productos.modelo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import repositorio.dao.modelo.ModeloDaoImpl;
import principal.vista.productos.HomeMenuProductos;
import modelo.producto.marca.Modelo;
import repositorio.dao.marca.MarcaDaoImpl;
import modelo.producto.marca.Rodado;
import modelo.producto.marca.Marca;


/**
 *
 * @author rodri
 */

public class BajaModelo extends javax.swing.JFrame {

    private JTextField codigoField;
    private JComboBox<Rodado> rodadoComboBox;
    private JComboBox<Marca> marcaComboBox;  
    private JTable modeloTable;
    private JButton eliminarButton;

    public BajaModelo() {
        setTitle("Baja Modelo");
        setSize(600, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Baja Modelo", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.add(new JLabel("Código del Modelo:"));
        codigoField = new JTextField();
        inputPanel.add(codigoField);

        inputPanel.add(new JLabel("Rodado:"));
        rodadoComboBox = new JComboBox<>(Rodado.values());
        rodadoComboBox.setSelectedItem(null); 
        inputPanel.add(rodadoComboBox);

        inputPanel.add(new JLabel("Marca:"));
        marcaComboBox = new JComboBox<>();  
        marcaComboBox.setSelectedItem(null); 
        MarcaDaoImpl marcaDao = new MarcaDaoImpl();
        java.util.List<Marca> marcas = marcaDao.getMarcasComboBox();

        marcaComboBox.addItem(null);  
        for (Marca marca : marcas) {
            marcaComboBox.addItem(marca); 
        }
        inputPanel.add(marcaComboBox);

        JButton buscarButton = new JButton("Buscar");
        buscarButton.addActionListener(new BuscarButtonListener());
        inputPanel.add(buscarButton);

        searchPanel.add(inputPanel, BorderLayout.NORTH);

        String[] columnNames = {"Código Marca", "Código Modelo", "Modelo", "Descripción", "Rodado"};
        Object[][] data = new Object[0][5]; 
        modeloTable = new JTable(data, columnNames);
        configurarTabla();
        JScrollPane scrollPane = new JScrollPane(modeloTable);
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
        modeloTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        modeloTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = modeloTable.getSelectedRow();
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
                String codigoModelo = codigoField.getText().trim();
                String nombreModelo = ""; 
                Marca marcaSeleccionada = (Marca) marcaComboBox.getSelectedItem();  
                Rodado rodado = (Rodado) rodadoComboBox.getSelectedItem();

                if (codigoModelo.isEmpty()) {
                    codigoModelo = null;  
                }

                String codigoMarca = (marcaSeleccionada != null) ? marcaSeleccionada.getCodigo() : null;  

                if (rodado == null) {
                    rodado = null;
                }

                ModeloDaoImpl modeloDao = new ModeloDaoImpl();
                java.util.List<Modelo> modelos = modeloDao.getModelos(codigoModelo, nombreModelo, codigoMarca, rodado);

                if (modelos != null && !modelos.isEmpty()) {

                    Object[][] data = new Object[modelos.size()][5];
                    for (int i = 0; i < modelos.size(); i++) {
                        Modelo modelo = modelos.get(i);

                        String nombreMarcaModelo = (modelo.getMarca() != null) ? modelo.getMarca().getMarca() : "No Disponible";
                        data[i] = new Object[]{
                            nombreMarcaModelo, 
                            modelo.getCodigo(), 
                            modelo.getModelo(), 
                            modelo.getDescripcion(), 
                            modelo.getRodado() 
                        };
                    }
                    modeloTable.setModel(new DefaultTableModel(data, new String[]{"Código Marca", "Código Modelo", "Modelo", "Descripción", "Rodado"}));
                    eliminarButton.setEnabled(true); 
                } else {

                    Object[][] data = new Object[0][5];
                    modeloTable.setModel(new DefaultTableModel(data, new String[]{"Código Marca", "Código Modelo", "Modelo", "Descripción", "Rodado"}));
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

                int selectedRow = modeloTable.getSelectedRow();

                if (selectedRow != -1) {

                    String codigoModelo = modeloTable.getValueAt(selectedRow, 1).toString();  

                    ModeloDaoImpl modeloDao = new ModeloDaoImpl();
                    Modelo modelo = modeloDao.obtenerModelo(codigoModelo, null, null, null); 

                    if (modelo != null) {

                        int confirmacion = JOptionPane.showConfirmDialog(null,
                                "¿Está seguro de que desea eliminar este modelo?",
                                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

                        if (confirmacion == JOptionPane.YES_OPTION) {

                            modeloDao.eliminarModelo(codigoModelo, null);  

                            JOptionPane.showMessageDialog(null, "El modelo ha sido eliminado exitosamente.");

                            modeloTable.setModel(new DefaultTableModel(new Object[0][5],
                                    new String[]{"Código Marca", "Código Modelo", "Modelo", "Descripción", "Rodado"}));

                            eliminarButton.setEnabled(false);
                        } else {
                            JOptionPane.showMessageDialog(null, "Eliminación cancelada.");
                        }
                    } else {

                        JOptionPane.showMessageDialog(null, "Modelo no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                        eliminarButton.setEnabled(false); 
                    }
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
            java.util.logging.Logger.getLogger(BajaModelo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BajaModelo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BajaModelo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BajaModelo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BajaModelo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
