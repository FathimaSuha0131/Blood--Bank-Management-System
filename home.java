import project.connectionprovider;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.Timer;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */


import javax.swing.JOptionPane;

/**
 *
 * @author FATHIMA SUHA
 */
public class home extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(home.class.getName());

    /**
     * Creates new form home
     */
    public home() {
      initComponents();

    // Current Date
    SimpleDateFormat dateFormat =
            new SimpleDateFormat("dd MMMM yyyy");

    date.setText(dateFormat.format(new Date()));

    // Current Time
    Timer timer = new Timer(1000, e -> {

        SimpleDateFormat timeFormat =
                new SimpleDateFormat("hh:mm:ss a");

        time.setText(timeFormat.format(new Date()));

    });

    timer.start();

    // Load Dashboard
    refreshDashboard();
    // Automatic Dashboard Refresh
Timer dashboardTimer = new Timer(2000, e -> {
    refreshDashboard();
});

dashboardTimer.start();
    
    }
    
    private void loadDashboardData() {
        try (Connection con = connectionprovider.getCon()) {

        // Total Donors
        String sql1 = "SELECT COUNT(*) AS total FROM donor";

        try (PreparedStatement pst1 = con.prepareStatement(sql1);
             ResultSet rs1 = pst1.executeQuery()) {

            if (rs1.next()) {

                totaldonors.setText(
                    String.valueOf(rs1.getInt("total"))
                );
            }
        }

        // Total Blood Units
        String sql2 =
                "SELECT COALESCE(SUM(units), 0) AS total " +
                "FROM stock";

        try (PreparedStatement pst2 = con.prepareStatement(sql2);
             ResultSet rs2 = pst2.executeQuery()) {

            if (rs2.next()) {

                totalunits.setText(
                    String.valueOf(rs2.getInt("total"))
                );
            }
        }

    } catch (Exception e) {

        JOptionPane.showMessageDialog(
            this,
            "Database Error: " + e.getMessage(),
            "Database Error",
            JOptionPane.ERROR_MESSAGE
        );
    }

       
    }
    
    private void loadBloodStock() {

    try {

        Connection con = connectionprovider.getCon();

        PreparedStatement ps = con.prepareStatement(
            "SELECT bloodGroup, units FROM stock"
        );

        ResultSet rs = ps.executeQuery();

        // Reset all labels
        aplus.setText("0");
        aminus.setText("0");
        bplus.setText("0");
        bminus.setText("0");
        oplus.setText("0");
        ominus.setText("0");
        abplus.setText("0");
        abminus.setText("0");

        while (rs.next()) {

            String bloodGroup = rs.getString("bloodGroup");
            int units = rs.getInt("units");

            switch (bloodGroup) {

                case "A+":
                    aplus.setText(String.valueOf(units));
                    break;

                case "A-":
                    aminus.setText(String.valueOf(units));
                    break;

                case "B+":
                    bplus.setText(String.valueOf(units));
                    break;

                case "B-":
                    bminus.setText(String.valueOf(units));
                    break;

                case "O+":
                    oplus.setText(String.valueOf(units));
                    break;

                case "O-":
                    ominus.setText(String.valueOf(units));
                    break;

                case "AB+":
                    abplus.setText(String.valueOf(units));
                    break;

                case "AB-":
                    abminus.setText(String.valueOf(units));
                    break;
            }
        }

        rs.close();
        ps.close();
        con.close();

    } catch (Exception e) {

        JOptionPane.showMessageDialog(
            this,
            "Blood Stock Error: " + e.getMessage()
        );
    }
}
    private void loadRecentDonors() {

    String sql = "SELECT donorID, name, bloodGroup, city, MobileNo "
               + "FROM donor "
               + "ORDER BY donorID DESC "
               + "LIMIT 5";

    try (Connection con = connectionprovider.getCon();
         PreparedStatement pst = con.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {

        javax.swing.table.DefaultTableModel model =
                new javax.swing.table.DefaultTableModel();

        model.addColumn("Donor ID");
        model.addColumn("Name");
        model.addColumn("Blood Group");
        model.addColumn("City");
        model.addColumn("Mobile No");

        while (rs.next()) {

            model.addRow(new Object[]{
                rs.getInt("donorID"),
                rs.getString("name"),
                rs.getString("bloodGroup"),
                rs.getString("city"),
                rs.getString("MobileNo")
            });
        }

        recentDonorsTable.setModel(model);

    } catch (Exception e) {

        JOptionPane.showMessageDialog(
                this,
                "Recent Donors Error: " + e.getMessage()
        );
    }
   
}
   private void loadRecentDonations() {

    String sql = "SELECT donorID, bloodGroup, quantity, donationDate "
               + "FROM donation "
               + "ORDER BY donationID DESC "
               + "LIMIT 5";


    try (Connection con = connectionprovider.getCon();
         PreparedStatement pst = con.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {


        javax.swing.table.DefaultTableModel model =
                new javax.swing.table.DefaultTableModel();


        model.addColumn("Donor ID");
        model.addColumn("Blood Group");
        model.addColumn("Quantity");
        model.addColumn("Date");


        while(rs.next()) {

            model.addRow(new Object[]{

                rs.getInt("donorID"),
                rs.getString("bloodGroup"),
                rs.getInt("quantity"),
                rs.getDate("donationDate")

            });

        }


        recentDonationTable.setModel(model);


    } catch(Exception e) {

        JOptionPane.showMessageDialog(
                this,
                "Recent Donation Error : " + e.getMessage()
        );

    }
    

} 
   public void refreshDashboard() {

    loadDashboardData();
    loadBloodStock();
    loadRecentDonors();
    loadRecentDonations();
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jCalendarPanel1 = new de.wannawork.jcalendar.JCalendarPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jSeparator14 = new javax.swing.JSeparator();
        userPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 8), new java.awt.Dimension(0, 8), new java.awt.Dimension(32767, 8));
        todayPanel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        date = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        timePanel = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        time = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        totaldonors = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        totalunits = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        bloodStockPanel = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        stockTitle = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        aplus = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        aminus = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        bplus = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        bminus = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        oplus = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        ominus = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        abplus = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        abminus = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        recentDonorsTable = new javax.swing.JTable();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        recentDonationTable = new javax.swing.JTable();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jSeparator13 = new javax.swing.JPopupMenu.Separator();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu8 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenu9 = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenu10 = new javax.swing.JMenu();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/home.png"))); // NOI18N
        jLabel2.setText("jLabel2");

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
        jScrollPane1.setViewportView(jTable1);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane4.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        userPanel.setBackground(new java.awt.Color(255, 204, 204));
        userPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        userPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 5, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Welcome, User ");
        userPanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel5.setText("Together we can save lives.");
        userPanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, -1, -1));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8-home-48.png"))); // NOI18N
        userPanel.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));
        userPanel.add(filler1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 0, -1, 410));

        getContentPane().add(userPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 280, 70));

        todayPanel.setBackground(new java.awt.Color(153, 153, 153));
        todayPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        todayPanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Today");
        todayPanel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 50, -1));

        date.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        date.setText("Current Date");
        todayPanel.add(date, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, 160, -1));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8-date-48 (1).png"))); // NOI18N
        jLabel16.setText("jLabel16");
        todayPanel.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 50, -1));

        jSeparator8.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator8.setForeground(new java.awt.Color(0, 0, 0));
        todayPanel.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 280, -1));

        getContentPane().add(todayPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 370, 280, 60));

        timePanel.setBackground(new java.awt.Color(153, 153, 153));
        timePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        timePanel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Current Time");
        timePanel.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, -1, -1));

        time.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        time.setText("current time");
        timePanel.add(time, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, 90, -1));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8-time-40.png"))); // NOI18N
        jLabel17.setText("jLabel17");
        timePanel.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 40, -1));

        getContentPane().add(timePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 430, 280, 70));

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setForeground(new java.awt.Color(204, 204, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Total Donors");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, -1, -1));

        totaldonors.setBackground(new java.awt.Color(153, 153, 153));
        totaldonors.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        totaldonors.setText("Donors");
        jPanel1.add(totaldonors, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, 140, -1));

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8-drop-of-blood-48.png"))); // NOI18N
        jLabel18.setText("jLabel18");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 40, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 500, 280, 70));

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, -1, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("Total Blood Units");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, -1, -1));

        totalunits.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        totalunits.setText("units");
        jPanel2.add(totalunits, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, 130, -1));

        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8-users-48.png"))); // NOI18N
        jLabel45.setText("jLabel45");
        jPanel2.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 50, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 570, 280, 70));

        jPanel3.setBackground(new java.awt.Color(153, 153, 153));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, -1, 20));

        jLabel12.setFont(new java.awt.Font("Tahoma", 3, 15)); // NOI18N
        jLabel12.setText("\"Donate Blood");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 130, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 3, 15)); // NOI18N
        jLabel15.setText("Make a Difference\"");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 160, -1));

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8-love-25.png"))); // NOI18N
        jLabel20.setText("jLabel20");
        jPanel3.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 30, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 640, 280, 70));

        bloodStockPanel.setBackground(new java.awt.Color(204, 0, 0));
        bloodStockPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        bloodStockPanel.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, -1, -1));

        stockTitle.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        stockTitle.setText("BLOOD STOCK SUMMARY");
        bloodStockPanel.add(stockTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, -1, -1));

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8-drop-of-blood-48.png"))); // NOI18N
        jLabel23.setText("jLabel23");
        bloodStockPanel.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 40, 40));

        jPanel4.setBackground(new java.awt.Color(255, 0, 51));

        jLabel22.setBackground(new java.awt.Color(255, 255, 255));
        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("A+");
        jPanel4.add(jLabel22);

        bloodStockPanel.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 90, 30));

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel32.setText("Units");
        jPanel5.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, -1));

        aplus.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        aplus.setText("0");
        jPanel5.add(aplus, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        bloodStockPanel.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 90, 80));

        jPanel6.setBackground(new java.awt.Color(0, 153, 255));

        jLabel24.setBackground(new java.awt.Color(255, 255, 255));
        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jPanel6.add(jLabel24);

        jLabel25.setBackground(new java.awt.Color(255, 255, 255));
        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("A-");
        jPanel6.add(jLabel25);

        bloodStockPanel.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, 90, 30));

        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel33.setText("Units");
        jPanel7.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, -1));

        aminus.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        aminus.setText("0");
        jPanel7.add(aminus, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        bloodStockPanel.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, 90, 80));

        jPanel8.setBackground(new java.awt.Color(0, 204, 51));

        jLabel26.setBackground(new java.awt.Color(255, 255, 255));
        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("B+");
        jPanel8.add(jLabel26);

        bloodStockPanel.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 60, 90, 30));

        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel34.setText("Units");
        jPanel9.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, -1));

        bplus.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        bplus.setText("0");
        jPanel9.add(bplus, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        bloodStockPanel.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 90, 90, 80));

        jPanel10.setBackground(new java.awt.Color(255, 153, 0));

        jLabel27.setBackground(new java.awt.Color(255, 255, 255));
        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("B-");
        jPanel10.add(jLabel27);

        bloodStockPanel.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 60, 90, 30));

        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel35.setText("Units");
        jPanel11.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, -1));

        bminus.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        bminus.setText("0");
        jPanel11.add(bminus, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        bloodStockPanel.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 90, 90, 80));

        jPanel12.setBackground(new java.awt.Color(153, 0, 255));

        jLabel28.setBackground(new java.awt.Color(255, 255, 255));
        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("O+");
        jPanel12.add(jLabel28);

        bloodStockPanel.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 60, 90, 30));

        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel36.setText("Units");
        jPanel13.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, -1));

        oplus.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        oplus.setText("0");
        jPanel13.add(oplus, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        bloodStockPanel.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 90, 90, 80));

        jPanel14.setBackground(new java.awt.Color(0, 204, 204));

        jLabel29.setBackground(new java.awt.Color(255, 255, 255));
        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("O-");
        jPanel14.add(jLabel29);

        bloodStockPanel.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 60, 90, 30));

        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel37.setText("Units");
        jPanel15.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, -1));

        ominus.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        ominus.setText("0");
        jPanel15.add(ominus, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        bloodStockPanel.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 90, 90, 80));

        jPanel16.setBackground(new java.awt.Color(255, 0, 153));

        jLabel30.setBackground(new java.awt.Color(255, 255, 255));
        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("AB+");
        jPanel16.add(jLabel30);

        bloodStockPanel.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 60, 90, 30));

        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel38.setText("Units");
        jPanel17.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, -1));

        abplus.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        abplus.setText("0");
        jPanel17.add(abplus, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        bloodStockPanel.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 90, 90, 80));

        jPanel18.setBackground(new java.awt.Color(204, 204, 0));

        jLabel31.setBackground(new java.awt.Color(255, 255, 255));
        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("AB-");
        jPanel18.add(jLabel31);

        bloodStockPanel.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 60, 90, 30));

        jPanel19.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel39.setText("Units");
        jPanel19.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, -1));

        abminus.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        abminus.setText("0");
        jPanel19.add(abminus, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        bloodStockPanel.add(jPanel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 90, 90, 80));

        getContentPane().add(bloodStockPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 300, 980, 190));

        recentDonorsTable.setBackground(new java.awt.Color(153, 0, 0));
        recentDonorsTable.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        recentDonorsTable.setForeground(new java.awt.Color(255, 255, 255));
        recentDonorsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(recentDonorsTable);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 550, 480, 150));

        jLabel42.setBackground(new java.awt.Color(255, 255, 255));
        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setText("RECENT DONORS");
        getContentPane().add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 500, -1, 30));

        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/search user.png"))); // NOI18N
        getContentPane().add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 490, -1, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Untitled-3.png"))); // NOI18N
        jLabel3.setText("jLabel3");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1260, -1));

        jLabel44.setText("jLabel44");
        getContentPane().add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        recentDonationTable.setBackground(new java.awt.Color(153, 0, 0));
        recentDonationTable.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        recentDonationTable.setForeground(new java.awt.Color(255, 255, 255));
        recentDonationTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(recentDonationTable);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 550, 470, 150));
        getContentPane().add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 510, -1, -1));

        jLabel41.setBackground(new java.awt.Color(0, 0, 0));
        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setText("RECENT DONATION");
        getContentPane().add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 510, -1, -1));

        jLabel46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8-drop-of-blood-48.png"))); // NOI18N
        getContentPane().add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 500, 60, -1));

        jPanel20.setBackground(new java.awt.Color(102, 102, 102));
        getContentPane().add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 490, 980, 220));

        jMenuBar2.setBackground(new java.awt.Color(255, 0, 51));

        jMenu6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Donor.png"))); // NOI18N
        jMenu6.setText("Donor");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Add new.png"))); // NOI18N
        jMenuItem1.setText("Add New");
        jMenuItem1.addActionListener(this::jMenuItem1ActionPerformed);
        jMenu6.add(jMenuItem1);
        jMenu6.add(jSeparator1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Update details.png"))); // NOI18N
        jMenuItem2.setText("Update Details");
        jMenuItem2.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
                jMenuItem2AncestorMoved(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jMenuItem2.addActionListener(this::jMenuItem2ActionPerformed);
        jMenu6.add(jMenuItem2);
        jMenu6.add(jSeparator2);

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Details.png"))); // NOI18N
        jMenuItem6.setText("All Donor Details");
        jMenuItem6.addActionListener(this::jMenuItem6ActionPerformed);
        jMenu6.add(jMenuItem6);

        jMenuItem12.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Add new.png"))); // NOI18N
        jMenuItem12.setText("Add Donation");
        jMenuItem12.addActionListener(this::jMenuItem12ActionPerformed);
        jMenu6.add(jMenuItem12);
        jMenu6.add(jSeparator13);

        jMenuBar2.add(jMenu6);

        jMenu7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/search user.png"))); // NOI18N
        jMenu7.setText("Search Blood Donor");

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.ALT_DOWN_MASK));
        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Location.png"))); // NOI18N
        jMenuItem4.setText("Location");
        jMenuItem4.addActionListener(this::jMenuItem4ActionPerformed);
        jMenu7.add(jMenuItem4);
        jMenu7.add(jSeparator3);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.ALT_DOWN_MASK));
        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Blood group.png"))); // NOI18N
        jMenuItem3.setText("Blood Group");
        jMenuItem3.addActionListener(this::jMenuItem3ActionPerformed);
        jMenu7.add(jMenuItem3);

        jMenuBar2.add(jMenu7);

        jMenu8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stock.png"))); // NOI18N
        jMenu8.setText("Stock");

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.SHIFT_DOWN_MASK));
        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Inc.png"))); // NOI18N
        jMenuItem5.setText("Increase");
        jMenuItem5.addActionListener(this::jMenuItem5ActionPerformed);
        jMenu8.add(jMenuItem5);
        jMenu8.add(jSeparator4);

        jMenuItem7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.SHIFT_DOWN_MASK));
        jMenuItem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Dec.png"))); // NOI18N
        jMenuItem7.setText("Decrease");
        jMenuItem7.addActionListener(this::jMenuItem7ActionPerformed);
        jMenu8.add(jMenuItem7);

        jMenuItem8.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.SHIFT_DOWN_MASK));
        jMenuItem8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Details.png"))); // NOI18N
        jMenuItem8.setText("Details");
        jMenuItem8.addActionListener(this::jMenuItem8ActionPerformed);
        jMenu8.add(jMenuItem8);

        jMenuBar2.add(jMenu8);

        jMenu9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/delete donor.png"))); // NOI18N
        jMenu9.setText("Delete Donor");

        jMenuItem9.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/delete.png"))); // NOI18N
        jMenuItem9.setText("Delete Donor");
        jMenuItem9.addActionListener(this::jMenuItem9ActionPerformed);
        jMenu9.add(jMenuItem9);

        jMenuBar2.add(jMenu9);

        jMenu10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exit.png"))); // NOI18N
        jMenu10.setText("Exit");
        jMenu10.add(jSeparator5);

        jMenuItem10.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Logout.png"))); // NOI18N
        jMenuItem10.setText("Logout");
        jMenuItem10.addActionListener(this::jMenuItem10ActionPerformed);
        jMenu10.add(jMenuItem10);

        jMenuItem11.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Exit application.png"))); // NOI18N
        jMenuItem11.setText("Exit Application");
        jMenuItem11.addActionListener(this::jMenuItem11ActionPerformed);
        jMenu10.add(jMenuItem11);

        jMenuBar2.add(jMenu10);

        setJMenuBar(jMenuBar2);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem2AncestorMoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jMenuItem2AncestorMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2AncestorMoved

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        new searchBloodDonorLocation(). setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
        new stockDecrease().setVisible(true);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        new stockIncrease().setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // TODO add your handling code here:
        int a=JOptionPane.showConfirmDialog(null,"Do you really want to logout","Select",JOptionPane.YES_NO_OPTION);
        if (a==0)
        {
            setVisible(false);
            new login ().setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        // TODO add your handling code here:
        int a=JOptionPane.showConfirmDialog(null,"Do you really want to Close Application","Select",JOptionPane.YES_NO_OPTION);
        if (a==0)
        {
            System.exit(0);
        }
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
    addNewDonor donorForm = new addNewDonor();
      donorForm.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        updateDetailsDonor updateForm = new updateDetailsDonor();
        updateForm.setLocationRelativeTo(this);
        updateForm.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        new allDonorDetails(). setVisible(true);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        new searchBloodDonorBloodGroup().setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
        new stockDetails().setVisible(true);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:
        new deleteDonor().setVisible(true);
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        // TODO add your handling code here:
        addDonation ad = new addDonation();
    ad.setVisible(true);

    }//GEN-LAST:event_jMenuItem12ActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new home().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel abminus;
    private javax.swing.JLabel abplus;
    private javax.swing.JLabel aminus;
    private javax.swing.JLabel aplus;
    private javax.swing.JPanel bloodStockPanel;
    private javax.swing.JLabel bminus;
    private javax.swing.JLabel bplus;
    private javax.swing.JLabel date;
    private javax.swing.Box.Filler filler1;
    private de.wannawork.jcalendar.JCalendarPanel jCalendarPanel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu10;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel ominus;
    private javax.swing.JLabel oplus;
    private javax.swing.JTable recentDonationTable;
    private javax.swing.JTable recentDonorsTable;
    private javax.swing.JLabel stockTitle;
    private javax.swing.JLabel time;
    private javax.swing.JPanel timePanel;
    private javax.swing.JPanel todayPanel;
    private javax.swing.JLabel totaldonors;
    private javax.swing.JLabel totalunits;
    private javax.swing.JPanel userPanel;
    // End of variables declaration//GEN-END:variables
}
