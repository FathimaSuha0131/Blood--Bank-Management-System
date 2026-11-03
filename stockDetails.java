import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import project.connectionprovider;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author FATHIMA SUHA
 */
public class stockDetails extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(stockDetails.class.getName());

    /**
     * Creates new form stockDetails
     */
    public stockDetails() {
        initComponents();
        loadStockData();
    }

    @SuppressWarnings("unchecked")
    
     private void loadStockData() {

      try {
        Connection con = connectionprovider.getCon();
        Statement st = con.createStatement();

        ResultSet rs = st.executeQuery(
            "SELECT bloodGroup, units, " +
            "CASE " +
            "WHEN units = 0 THEN 'OUT OF STOCK' " +
            "WHEN units <= 5 THEN 'LOW STOCK' " +
            "WHEN units <= 15 THEN 'NORMAL' " +
            "ELSE 'AVAILABLE' " +
            "END AS status " +
            "FROM stock"
        );

        jTable1.setModel(
            DbUtils.resultSetToTableModel(rs)
        );

        rs.close();
        st.close();
        con.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(
            this,
            "Unable to load stock data.\n\n" + e.getMessage(),
            "Database Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
    }

    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator3 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jSeparator2 = new javax.swing.JSeparator();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/all page background image.png"))); // NOI18N
        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Algerian", 1, 36)); // NOI18N
        jLabel1.setText("STOCK (DETAILS)");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(181, 20, -1, -1));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 74, 689, 10));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jTable1ComponentShown(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 90, 670, 336));
        getContentPane().add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 438, 689, 10));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/print.png"))); // NOI18N
        jButton1.setText("Print");
        jButton1.addActionListener(this::jButton1ActionPerformed);
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 450, -1, -1));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Exit application.png"))); // NOI18N
        jButton2.setText("Close");
        jButton2.addActionListener(this::jButton2ActionPerformed);
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 450, -1, -1));

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8-details-24.png"))); // NOI18N
        jButton3.setText("Stock Details");
        jButton3.addActionListener(this::jButton3ActionPerformed);
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 450, -1, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/all page background image.png"))); // NOI18N
        jLabel3.setText("jLabel3");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
     try {
            javax.swing.table.TableModel model = jTable1.getModel();

            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No stock data found in table to export!");
                return;
            }

            // Create temporary CSV file containing active table contents
            File csvFile = File.createTempFile("Stock_Details_", ".csv");
            csvFile.deleteOnExit();

            FileWriter writer = new FileWriter(csvFile);

            // Write dynamic table column headers
            for (int i = 0; i < model.getColumnCount(); i++) {
                writer.write("\"" + model.getColumnName(i) + "\"" + (i == model.getColumnCount() - 1 ? "" : ","));
            }
            writer.write("\n");

            // Write active row data (bloodGroup, units)
            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++) {
                    Object cellValue = model.getValueAt(i, j);
                    String value = (cellValue != null) ? cellValue.toString().replace("\"", "\"\"") : "";
                    writer.write("\"" + value + "\"" + (j == model.getColumnCount() - 1 ? "" : ","));
                }
                writer.write("\n");
            }
            writer.close();

            // Script execution to launch Excel directly with populated CSV data
            File scriptFile = File.createTempFile("openInExcel", ".vbs");
            scriptFile.deleteOnExit();

            FileWriter scriptWriter = new FileWriter(scriptFile);
            scriptWriter.write("Set objExcel = CreateObject(\"Excel.Application\")\n");
            scriptWriter.write("objExcel.Visible = True\n");
            scriptWriter.write("Set objWorkbook = objExcel.Workbooks.Open(\"" + csvFile.getAbsolutePath().replace("\\", "\\\\") + "\")\n");
            scriptWriter.close();

            Runtime.getRuntime().exec("wscript " + scriptFile.getAbsolutePath());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error exporting to Excel: " + e.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jTable1ComponentShown
        // TODO add your handling code here:
        loadStockData(); 
    
    }//GEN-LAST:event_jTable1ComponentShown

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
      try {

        Connection con = connectionprovider.getCon();

        String sql =
            "SELECT bloodGroup, units, " +
            "CASE " +
            "WHEN units = 0 THEN 'OUT OF STOCK' " +
            "WHEN units <= 5 THEN 'LOW STOCK' " +
            "WHEN units <= 15 THEN 'NORMAL' " +
            "ELSE 'AVAILABLE' " +
            "END AS status " +
            "FROM stock";

        java.sql.PreparedStatement ps =
                con.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        StringBuilder result = new StringBuilder();

        while (rs.next()) {

            result.append("Blood Group: ")
                  .append(rs.getString("bloodGroup"))
                  .append("\n");

            result.append("Quantity: ")
                  .append(rs.getInt("units"))
                  .append("\n");

            result.append("Status: ")
                  .append(rs.getString("status"))
                  .append("\n");

            result.append("----------------------\n");
        }

        if (result.length() == 0) {
            result.append("No blood stock data found.");
        }

        JOptionPane.showMessageDialog(
            this,
            result.toString(),
            "Blood Stock Analysis",
            JOptionPane.INFORMATION_MESSAGE
        );

        rs.close();
        ps.close();
        con.close();

    } catch (Exception e) {

        JOptionPane.showMessageDialog(
            this,
            "Error: " + e.getMessage(),
            "Database Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
    }//GEN-LAST:event_jButton3ActionPerformed

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new stockDetails().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
