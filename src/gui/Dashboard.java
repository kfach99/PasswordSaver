/*
 * To change this license header, choose License Headers in Project Properties.
 * Developped by kamal fach
 * To change this template file, choose Tools | Templates
 * Powered by Kamal fach
 * and open the template in the editor.
 */
package gui;

import com.qoppa.pdf.PDFException;
import db.ConnectionUtil;
import java.awt.CardLayout;
import java.awt.print.PrinterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.sql.ResultSet;
import java.text.MessageFormat;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import com.qoppa.pdf.PrintSettings;
import com.qoppa.pdfProcess.PDFDocument;
import com.qoppa.pdfProcess.PDFPage;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


/**
 *
 * @author FACH
 */
public class Dashboard extends javax.swing.JFrame {

    /**
     * Creates new form Dashboard
     */
    
                        //Developped by kamal fach

           CardLayout crrd;
           public static Connection con;
           public static DefaultTableModel ml2;
           public static DefaultTableModel ml;
           private int tx, ty;                   
           
                            //Developped by kamal fach

           
           
    public Dashboard() {
        initComponents();
        crrd = (CardLayout) (CrdPanel.getLayout());
        crrd.show(CrdPanel , "Home");
        jPanel11.setBackground(new java.awt.Color(226,106,106));
        Userrr.setText("Connected As : "+Helper.username);
        cboPlatformRemplissage();
        TBLPass();
        ml2 = (DefaultTableModel) tblsearch.getModel();

    }
        
                            //Developped by kamal fach
    
    
    public void exportToExcel(JTable t) throws IOException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("EXCEL FILE", "xls");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Save file ");
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            String Extension = chooser.getSelectedFile().toString().concat(".xls");
            try {
                File ArchivXLS = new File(Extension);
                if (ArchivXLS.exists()) {
                    ArchivXLS.delete();
                }
                ArchivXLS.createNewFile();
                Workbook lib = new HSSFWorkbook();
                try (FileOutputStream archive = new FileOutputStream(ArchivXLS)) {
                    Sheet sheet = lib.createSheet("sheet1");
                    sheet.setDisplayGridlines(false);
                    for (int f = 0; f < t.getRowCount(); f++) {
                        Row file = sheet.createRow(f);
                        for (int c = 0; c < t.getColumnCount(); c++) {
                            Cell cell = file.createCell(c);
                            if (f == 0) {
                                cell.setCellValue(t.getColumnName(c));
                            }
                        }
                    }
                    int fileinit = 1;
                    for (int f = 0; f < t.getRowCount(); f++) {
                        Row file = sheet.createRow(fileinit);
                        fileinit++;
                        for (int c = 0; c < t.getColumnCount(); c++) {
                            Cell cell = file.createCell(c);
                            if (t.getValueAt(f, c) instanceof Double) {
                                cell.setCellValue(Double.parseDouble(t.getValueAt(f, c).toString()));
                            } else if (t.getValueAt(f, c) instanceof Float) {
                                cell.setCellValue(Float.parseFloat((String) t.getValueAt(f, c)));
                            } else {
                                cell.setCellValue(String.valueOf(t.getValueAt(f, c)));
                            }
                        }
                    }
                    lib.write(archive);
                }
                Desktop.getDesktop().open(ArchivXLS);
            } catch (IOException | NumberFormatException e) {
                throw e;
            }
        }
    }
    
/////////////////////////////////////////////////////////
    

        public static void RotatePdf()
    {
        try
        {
            // load original PDF
            PDFDocument pdfDoc = new PDFDocument ("Path of printed file", null);
 
            // Loop through all pages
            for (int i = 0; i < pdfDoc.getPageCount(); i++) 
            {
            // get page in the original PDF 
                PDFPage page = pdfDoc.getPage(i);
 
                // change the page rotation to flip it by 180 degrees
                page.setPageRotation(90);
            }
 
            // print the document 
            pdfDoc.print(new PrintSettings());
 
            // save the document
            pdfDoc.saveDocument ("Path of printed file/modified");
        }
        catch (PDFException | PrinterException | IOException t)
        {
            System.out.println("Not Rotated");
        }
    }
    /////////////////////////////////////////////////////////////
    
                            //Developped by kamal fach

        public static void closiing()
        {
            
        }
    
                            //Developped by kamal fach

        public static void cboPlatformRemplissage(){
            con = ConnectionUtil.getConnection();
            cboPlatform.removeAllItems();
            PreparedStatement pstmt8;
            String SQL8 = "Select platform from pdata join userdata using(pid) where userdata.username = ?";
            
               try {
                   pstmt8 = con.prepareStatement(SQL8);
                   pstmt8.setString(1, Helper.username);
                    ResultSet rs8 = pstmt8.executeQuery();
                    while(rs8.next())
                    {
                        cboPlatform.addItem(rs8.getString(1));
                    }
               } catch (SQLException ex) {
                   Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
               }
                    
        }
                            //Developped by kamal fach

        public static void TBLPass(){
            con = ConnectionUtil.getConnection();
                            //Developped by kamal fach
                            ListPasss.setAutoCreateRowSorter(true);
            String SQL10 = "select pid, platform,pemail , pusername ,  ppass from pdata join userdata using(pid) where userdata.USERNAME = ?";
            PreparedStatement pstmt10;
            ml = (DefaultTableModel) ListPasss.getModel();
            try {
                   pstmt10 = con.prepareStatement(SQL10);
                   pstmt10.setString(1, Helper.username);
                try (ResultSet rs = pstmt10.executeQuery()) {
                    ml.setRowCount(0);
                    while(rs.next())
                    {
                        
                        ml.addRow(new Object[]{rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)});
                    }
                }
                   pstmt10.close();
               } catch (SQLException ex) {
                   Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                 //  System.out.println("erreur affichage !");
               }
        }
        
        
                            //Developped by kamal fach

        
        private void resetColor(JPanel panel)
        {
            panel.setBackground(new java.awt.Color(226,106,106));
        }
       
        
                            //Developped by kamal fach

        
       private void setColor(JPanel panel)
        {
            panel.setBackground(new java.awt.Color(230,90,100));
            
        }
       
                           //Developped by kamal fach

       
       private void setColor1(JPanel panel)
        {
               panel.setBackground(new java.awt.Color(46,49,49));
       
                            //Developped by kamal fach
     
        }
       private void resetColor1(JPanel panel)
        {
            panel.setBackground(new java.awt.Color(0,0,0));
        }

       
                           //Developped by kamal fach

       
       
       /**
     * This method is called from within the constructor to initialize the form.
     * Developped by kamal fach
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel27 = new javax.swing.JLabel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel11 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        CrdPanel = new javax.swing.JPanel();
        AddPass = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        BackBtn = new javax.swing.JButton();
        TxtPlatform = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtUsername = new javax.swing.JTextField();
        SaveBtn = new javax.swing.JButton();
        txtPassword = new javax.swing.JPasswordField();
        jLabel28 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        UpdatePass = new javax.swing.JPanel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        Txtidupdate = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        txtEmailUpdate = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        txtUsernameUpdate = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        txtPasswordUpdate = new javax.swing.JPasswordField();
        jLabel46 = new javax.swing.JLabel();
        BackBtn1 = new javax.swing.JButton();
        UpdateBtn = new javax.swing.JButton();
        jLabel47 = new javax.swing.JLabel();
        TxtPlatformUpdate = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        SearchPass = new javax.swing.JPanel();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        cboPlatform = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblsearch = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        BackBtn4 = new javax.swing.JButton();
        jLabel64 = new javax.swing.JLabel();
        DownloadPass = new javax.swing.JPanel();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel33 = new javax.swing.JLabel();
        btndownload = new javax.swing.JButton();
        btnExcel = new javax.swing.JButton();
        BackBtn3 = new javax.swing.JButton();
        jLabel56 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        ListPass = new javax.swing.JPanel();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ListPasss = new javax.swing.JTable();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        DeletePass = new javax.swing.JPanel();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        deleteid = new javax.swing.JTextField();
        DeleteBtn = new javax.swing.JButton();
        BackBtn2 = new javax.swing.JButton();
        jLabel66 = new javax.swing.JLabel();
        Home = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        Userrr = new javax.swing.JLabel();

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_hide_20px_1.png"))); // NOI18N
        jLabel27.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel27MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel27MouseReleased(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        jSplitPane1.setBorder(null);
        jSplitPane1.setDividerSize(0);
        jSplitPane1.setForeground(new java.awt.Color(0, 0, 0));

        jPanel4.setBackground(new java.awt.Color(0, 0, 0));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 204, 204));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Dashboard : ");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 140, 36));

        jPanel7.setBackground(new java.awt.Color(0, 0, 0));
        jPanel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel7MouseExited(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(204, 204, 204));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_logout_rounded_down_20px.png"))); // NOI18N
        jLabel8.setText("Logout");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 440, 140, 30));

        jSeparator1.setBackground(new java.awt.Color(226, 106, 106));
        jSeparator1.setForeground(new java.awt.Color(226, 106, 106));
        jPanel4.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, 100, 10));

        jPanel11.setBackground(new java.awt.Color(0, 0, 0));
        jPanel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel11MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel11MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel11MouseExited(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(204, 204, 204));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_home_20px_1.png"))); // NOI18N
        jLabel14.setText("Home");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 140, 30));

        jPanel12.setBackground(new java.awt.Color(0, 0, 0));
        jPanel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel12MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel12MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel12MouseExited(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(204, 204, 204));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_add_20px.png"))); // NOI18N
        jLabel16.setText("Add");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel12Layout.createSequentialGroup()
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 20, Short.MAX_VALUE)))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 140, 30));

        jPanel13.setBackground(new java.awt.Color(0, 0, 0));
        jPanel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel13MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel13MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel13MouseExited(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(204, 204, 204));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_update_20px.png"))); // NOI18N
        jLabel17.setText("Update");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel13Layout.createSequentialGroup()
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 20, Short.MAX_VALUE)))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 140, 30));

        jPanel14.setBackground(new java.awt.Color(0, 0, 0));
        jPanel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel14MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel14MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel14MouseExited(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(204, 204, 204));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_search_20px.png"))); // NOI18N
        jLabel18.setText("Search");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel14Layout.createSequentialGroup()
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 20, Short.MAX_VALUE)))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 140, 30));

        jPanel15.setBackground(new java.awt.Color(0, 0, 0));
        jPanel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel15MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel15MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel15MouseExited(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(204, 204, 204));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_download_20px.png"))); // NOI18N
        jLabel19.setText("Download");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel15Layout.createSequentialGroup()
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 20, Short.MAX_VALUE)))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 140, 30));

        jPanel16.setBackground(new java.awt.Color(0, 0, 0));
        jPanel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel16MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel16MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel16MouseExited(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(204, 204, 204));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_list_20px.png"))); // NOI18N
        jLabel20.setText("Liste");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
            .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel16Layout.createSequentialGroup()
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 20, Short.MAX_VALUE)))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
            .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 140, 30));

        jPanel17.setBackground(new java.awt.Color(0, 0, 0));
        jPanel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel17MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel17MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel17MouseExited(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(204, 204, 204));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_delete_20px.png"))); // NOI18N
        jLabel21.setText("Delete");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel17Layout.createSequentialGroup()
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 20, Short.MAX_VALUE)))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 140, 30));

        jSeparator2.setBackground(new java.awt.Color(226, 106, 106));
        jSeparator2.setForeground(new java.awt.Color(226, 106, 106));
        jPanel4.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 100, 10));

        jSplitPane1.setLeftComponent(jPanel4);

        jPanel1.setBackground(new java.awt.Color(68, 108, 179));

        CrdPanel.setBackground(new java.awt.Color(191, 191, 191));
        CrdPanel.setLayout(new java.awt.CardLayout());

        AddPass.setBackground(new java.awt.Color(191, 191, 191));

        jLabel23.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(46, 49, 49));
        jLabel23.setText("Platform Name :");

        jLabel24.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(46, 49, 49));
        jLabel24.setText("Email :");

        jLabel25.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(46, 49, 49));
        jLabel25.setText("Password :");

        jLabel26.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(46, 49, 49));
        jLabel26.setText("Username : ");

        BackBtn.setBackground(new java.awt.Color(226, 106, 106));
        BackBtn.setFont(new java.awt.Font("Berlin Sans FB", 0, 20)); // NOI18N
        BackBtn.setForeground(new java.awt.Color(20, 20, 20));
        BackBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_back_arrow_20px.png"))); // NOI18N
        BackBtn.setText("Back");
        BackBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackBtnActionPerformed(evt);
            }
        });

        TxtPlatform.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        TxtPlatform.setForeground(new java.awt.Color(226, 106, 106));
        TxtPlatform.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtPlatformActionPerformed(evt);
            }
        });

        txtEmail.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        txtEmail.setForeground(new java.awt.Color(226, 106, 106));

        txtUsername.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        txtUsername.setForeground(new java.awt.Color(226, 106, 106));
        txtUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsernameActionPerformed(evt);
            }
        });

        SaveBtn.setBackground(new java.awt.Color(226, 106, 106));
        SaveBtn.setFont(new java.awt.Font("Berlin Sans FB", 0, 20)); // NOI18N
        SaveBtn.setForeground(new java.awt.Color(20, 20, 20));
        SaveBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_add_20px_1.png"))); // NOI18N
        SaveBtn.setText("Add");
        SaveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveBtnActionPerformed(evt);
            }
        });

        txtPassword.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        txtPassword.setForeground(new java.awt.Color(226, 106, 106));
        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });

        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_hide_20px_1.png"))); // NOI18N
        jLabel28.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel28MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel28MouseReleased(evt);
            }
        });

        jSeparator9.setBackground(new java.awt.Color(226, 106, 106));
        jSeparator9.setForeground(new java.awt.Color(226, 106, 106));
        jSeparator9.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator9.setToolTipText("");

        jLabel48.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(46, 49, 49));
        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel48.setText("To your list of saved password");

        jLabel49.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(226, 106, 106));
        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel49.setText("<html> <body> <u>Contact developper :)</u> </html> </body>");
        jLabel49.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel49.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel49MouseClicked(evt);
            }
        });

        jLabel50.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(46, 49, 49));
        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel50.setText("Add New Password");

        javax.swing.GroupLayout AddPassLayout = new javax.swing.GroupLayout(AddPass);
        AddPass.setLayout(AddPassLayout);
        AddPassLayout.setHorizontalGroup(
            AddPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddPassLayout.createSequentialGroup()
                .addGroup(AddPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddPassLayout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(SaveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(76, 76, 76)
                        .addComponent(BackBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(AddPassLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(AddPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26)
                            .addComponent(jLabel25))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                        .addGroup(AddPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(AddPassLayout.createSequentialGroup()
                                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TxtPlatform, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)))
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(AddPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddPassLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(AddPassLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel48))
                    .addGroup(AddPassLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19))
        );
        AddPassLayout.setVerticalGroup(
            AddPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddPassLayout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(AddPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TxtPlatform, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addGap(18, 18, 18)
                .addGroup(AddPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addGap(18, 18, 18)
                .addGroup(AddPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addGap(21, 21, 21)
                .addGroup(AddPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel25))
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(AddPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BackBtn)
                    .addComponent(SaveBtn))
                .addGap(40, 40, 40))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddPassLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(AddPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(AddPassLayout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(jLabel50)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel48)))
                .addGap(17, 17, 17)
                .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        CrdPanel.add(AddPass, "AddPass");

        UpdatePass.setBackground(new java.awt.Color(191, 191, 191));
        UpdatePass.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator4.setBackground(new java.awt.Color(226, 106, 106));
        jSeparator4.setForeground(new java.awt.Color(226, 106, 106));
        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator4.setToolTipText("");
        UpdatePass.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 60, 10, 250));

        jLabel29.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(46, 49, 49));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("To update a password ");
        UpdatePass.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 90, 170, -1));

        jLabel30.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(226, 106, 106));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("<html> <body> <u>here</u> </html> </body>");
        jLabel30.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel30.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel30MouseClicked(evt);
            }
        });
        UpdatePass.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 130, 170, -1));

        Txtidupdate.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        Txtidupdate.setForeground(new java.awt.Color(226, 106, 106));
        Txtidupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtidupdateActionPerformed(evt);
            }
        });
        UpdatePass.add(Txtidupdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(227, 50, 294, -1));

        jLabel42.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(46, 49, 49));
        jLabel42.setText("Platform Name :");
        UpdatePass.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 101, -1, -1));

        txtEmailUpdate.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        txtEmailUpdate.setForeground(new java.awt.Color(226, 106, 106));
        txtEmailUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailUpdateActionPerformed(evt);
            }
        });
        UpdatePass.add(txtEmailUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(227, 144, 294, -1));

        jLabel43.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(46, 49, 49));
        jLabel43.setText("Email :");
        UpdatePass.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 148, 90, -1));

        jLabel44.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(46, 49, 49));
        jLabel44.setText("Username : ");
        UpdatePass.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 195, -1, -1));

        txtUsernameUpdate.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        txtUsernameUpdate.setForeground(new java.awt.Color(226, 106, 106));
        txtUsernameUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsernameUpdateActionPerformed(evt);
            }
        });
        UpdatePass.add(txtUsernameUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(227, 191, 294, -1));

        jLabel45.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(46, 49, 49));
        jLabel45.setText("Password :");
        UpdatePass.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 241, -1, -1));

        txtPasswordUpdate.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        txtPasswordUpdate.setForeground(new java.awt.Color(226, 106, 106));
        UpdatePass.add(txtPasswordUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(227, 238, 259, -1));

        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_hide_20px_1.png"))); // NOI18N
        jLabel46.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel46.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel46MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel46MouseReleased(evt);
            }
        });
        UpdatePass.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(492, 238, 29, 27));

        BackBtn1.setBackground(new java.awt.Color(226, 106, 106));
        BackBtn1.setFont(new java.awt.Font("Berlin Sans FB", 0, 20)); // NOI18N
        BackBtn1.setForeground(new java.awt.Color(20, 20, 20));
        BackBtn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_back_arrow_20px_1.png"))); // NOI18N
        BackBtn1.setText("Back");
        BackBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackBtn1ActionPerformed(evt);
            }
        });
        UpdatePass.add(BackBtn1, new org.netbeans.lib.awtextra.AbsoluteConstraints(339, 296, 140, -1));

        UpdateBtn.setBackground(new java.awt.Color(226, 106, 106));
        UpdateBtn.setFont(new java.awt.Font("Berlin Sans FB", 0, 20)); // NOI18N
        UpdateBtn.setForeground(new java.awt.Color(20, 20, 20));
        UpdateBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_update_20px_1.png"))); // NOI18N
        UpdateBtn.setText("Update");
        UpdateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateBtnActionPerformed(evt);
            }
        });
        UpdatePass.add(UpdateBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 296, 140, -1));

        jLabel47.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(46, 49, 49));
        jLabel47.setText("ID :");
        UpdatePass.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 54, -1, -1));

        TxtPlatformUpdate.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        TxtPlatformUpdate.setForeground(new java.awt.Color(226, 106, 106));
        TxtPlatformUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtPlatformUpdateActionPerformed(evt);
            }
        });
        UpdatePass.add(TxtPlatformUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(227, 97, 294, -1));

        jLabel51.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(226, 106, 106));
        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel51.setText("<html> <body> <u>Contact developper :)</u> </html> </body>");
        jLabel51.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel51.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel51MouseClicked(evt);
            }
        });
        UpdatePass.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 330, 110, -1));

        jLabel52.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(46, 49, 49));
        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel52.setText("Please go to password list");
        UpdatePass.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 110, 170, -1));

        jLabel53.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(46, 49, 49));
        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel53.setText("get the ID that you want update");
        UpdatePass.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 150, 170, -1));

        jLabel54.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(46, 49, 49));
        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel54.setText("then put it in ID field");
        UpdatePass.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 170, 120, -1));

        jLabel55.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(46, 49, 49));
        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel55.setText("and fill field you want update");
        UpdatePass.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 190, 160, -1));

        CrdPanel.add(UpdatePass, "UpdatePass");

        SearchPass.setBackground(new java.awt.Color(191, 191, 191));
        SearchPass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SearchPassMouseClicked(evt);
            }
        });

        jSeparator5.setBackground(new java.awt.Color(226, 106, 106));
        jSeparator5.setForeground(new java.awt.Color(226, 106, 106));
        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator5.setToolTipText("");

        jLabel31.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(46, 49, 49));
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("Select Platform to search password");

        jLabel32.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(46, 49, 49));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("And click Search button");
        jLabel32.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        cboPlatform.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        cboPlatform.setForeground(new java.awt.Color(226, 106, 106));

        jLabel22.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(46, 49, 49));
        jLabel22.setText("Platform : ");

        tblsearch.setBackground(new java.awt.Color(226, 106, 106));
        tblsearch.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tblsearch.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        tblsearch.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Platform", "E-mail", "Username", "Password"
            }
        ));
        tblsearch.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblsearch.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tblsearch.setGridColor(new java.awt.Color(51, 51, 51));
        tblsearch.setSelectionBackground(new java.awt.Color(255, 0, 51));
        jScrollPane3.setViewportView(tblsearch);

        jButton1.setBackground(new java.awt.Color(226, 106, 106));
        jButton1.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(20, 20, 20));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_search_20px_1.png"))); // NOI18N
        jButton1.setText("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        BackBtn4.setBackground(new java.awt.Color(226, 106, 106));
        BackBtn4.setFont(new java.awt.Font("Berlin Sans FB", 0, 20)); // NOI18N
        BackBtn4.setForeground(new java.awt.Color(20, 20, 20));
        BackBtn4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_back_arrow_20px.png"))); // NOI18N
        BackBtn4.setText("Back");
        BackBtn4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackBtn4ActionPerformed(evt);
            }
        });

        jLabel64.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(1, 50, 66));
        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel64.setText("<html> <body> <u>Contact developper :)</u> </html> </body>");
        jLabel64.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel64.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel64MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout SearchPassLayout = new javax.swing.GroupLayout(SearchPass);
        SearchPass.setLayout(SearchPassLayout);
        SearchPassLayout.setHorizontalGroup(
            SearchPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SearchPassLayout.createSequentialGroup()
                .addGroup(SearchPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SearchPassLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(SearchPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(SearchPassLayout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(jLabel22)
                                .addGap(31, 31, 31)
                                .addComponent(cboPlatform, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(46, 46, 46)
                                .addComponent(jButton1))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(SearchPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(SearchPassLayout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(SearchPassLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(SearchPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel31)))))
                    .addGroup(SearchPassLayout.createSequentialGroup()
                        .addGap(222, 222, 222)
                        .addComponent(BackBtn4, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        SearchPassLayout.setVerticalGroup(
            SearchPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SearchPassLayout.createSequentialGroup()
                .addGroup(SearchPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SearchPassLayout.createSequentialGroup()
                        .addGap(148, 148, 148)
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel32))
                    .addGroup(SearchPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, SearchPassLayout.createSequentialGroup()
                            .addGap(40, 40, 40)
                            .addGroup(SearchPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cboPlatform, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton1))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(12, 12, 12))
                        .addGroup(SearchPassLayout.createSequentialGroup()
                            .addGap(60, 60, 60)
                            .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(SearchPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BackBtn4)
                    .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        CrdPanel.add(SearchPass, "SearchPass");

        DownloadPass.setBackground(new java.awt.Color(191, 191, 191));
        DownloadPass.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator6.setBackground(new java.awt.Color(226, 106, 106));
        jSeparator6.setForeground(new java.awt.Color(226, 106, 106));
        jSeparator6.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator6.setToolTipText("");
        DownloadPass.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 60, 10, 250));

        jLabel33.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(46, 49, 49));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("Then click Print");
        DownloadPass.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 210, 160, -1));

        btndownload.setBackground(new java.awt.Color(226, 106, 106));
        btndownload.setFont(new java.awt.Font("Berlin Sans FB", 0, 20)); // NOI18N
        btndownload.setForeground(new java.awt.Color(20, 20, 20));
        btndownload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_export_pdf_20px.png"))); // NOI18N
        btndownload.setText("Print or Downlaod As PDF");
        btndownload.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btndownload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndownloadActionPerformed(evt);
            }
        });
        DownloadPass.add(btndownload, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 60, 290, -1));

        btnExcel.setBackground(new java.awt.Color(226, 106, 106));
        btnExcel.setFont(new java.awt.Font("Berlin Sans FB", 0, 20)); // NOI18N
        btnExcel.setForeground(new java.awt.Color(20, 20, 20));
        btnExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_export_csv_20px.png"))); // NOI18N
        btnExcel.setText("Export to Excel");
        btnExcel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelActionPerformed(evt);
            }
        });
        DownloadPass.add(btnExcel, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 140, 290, -1));

        BackBtn3.setBackground(new java.awt.Color(226, 106, 106));
        BackBtn3.setFont(new java.awt.Font("Berlin Sans FB", 0, 20)); // NOI18N
        BackBtn3.setForeground(new java.awt.Color(20, 20, 20));
        BackBtn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_back_arrow_20px.png"))); // NOI18N
        BackBtn3.setText("Back to Home");
        BackBtn3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        BackBtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackBtn3ActionPerformed(evt);
            }
        });
        DownloadPass.add(BackBtn3, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 230, 290, -1));

        jLabel56.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(46, 49, 49));
        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel56.setText("To generate PDF Report");
        DownloadPass.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 130, 160, -1));

        jLabel34.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(46, 49, 49));
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("Select Path you want to save in");
        DownloadPass.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 150, 160, -1));

        jLabel57.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(46, 49, 49));
        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel57.setText("In Print service name shoose");
        DownloadPass.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 170, 160, -1));

        jLabel58.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(46, 49, 49));
        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel58.setText("Microsoft Print to PDF");
        DownloadPass.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 190, 160, -1));

        jLabel63.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(1, 50, 66));
        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel63.setText("<html> <body> <u>Contact developper :)</u> </html> </body>");
        jLabel63.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel63.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel63MouseClicked(evt);
            }
        });
        DownloadPass.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 330, 110, -1));

        CrdPanel.add(DownloadPass, "DownloadPass");

        ListPass.setBackground(new java.awt.Color(191, 191, 191));
        ListPass.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator7.setBackground(new java.awt.Color(226, 106, 106));
        jSeparator7.setForeground(new java.awt.Color(226, 106, 106));
        jSeparator7.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator7.setToolTipText("");
        ListPass.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 60, 10, 250));

        jLabel35.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(46, 49, 49));
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setText("If you want to update or delete");
        ListPass.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 220, 170, 20));

        jLabel36.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(46, 49, 49));
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("Your saved password");
        jLabel36.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        ListPass.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 180, 170, -1));

        ListPasss.setBackground(new java.awt.Color(226, 106, 106));
        ListPasss.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        ListPasss.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        ListPasss.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Platform", "E-mail", "Username", "Password"
            }
        ));
        ListPasss.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        ListPasss.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        ListPasss.setGridColor(new java.awt.Color(51, 51, 51));
        ListPasss.setSelectionBackground(new java.awt.Color(255, 0, 51));
        jScrollPane1.setViewportView(ListPasss);

        ListPass.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 500, 190));

        jLabel59.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(46, 49, 49));
        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel59.setText("Here you can see all");
        ListPass.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 160, 170, -1));

        jLabel60.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(46, 49, 49));
        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel60.setText("You can get the id in case you");
        ListPass.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 200, 170, 20));

        jLabel65.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(1, 50, 66));
        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel65.setText("<html> <body> <u>Contact developper :)</u> </html> </body>");
        jLabel65.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel65.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel65MouseClicked(evt);
            }
        });
        ListPass.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 330, 110, -1));

        CrdPanel.add(ListPass, "ListPass");

        DeletePass.setBackground(new java.awt.Color(191, 191, 191));

        jSeparator8.setBackground(new java.awt.Color(226, 106, 106));
        jSeparator8.setForeground(new java.awt.Color(226, 106, 106));
        jSeparator8.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator8.setToolTipText("");

        jLabel37.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(46, 49, 49));
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setText("and get the id of the password");

        jLabel38.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(226, 106, 106));
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setText("<html> <body> <u>Password List</u> </html> </body>");
        jLabel38.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel38.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel38MouseClicked(evt);
            }
        });

        jLabel39.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(46, 49, 49));
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setText("To delete a password go to");

        jLabel40.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(46, 49, 49));
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setText("then put it in ID field and click delete");

        jLabel41.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(46, 49, 49));
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setText(" that you want to delete");

        jLabel6.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(46, 49, 49));
        jLabel6.setText("ID :");

        deleteid.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        deleteid.setForeground(new java.awt.Color(226, 106, 106));
        deleteid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteidActionPerformed(evt);
            }
        });

        DeleteBtn.setBackground(new java.awt.Color(226, 106, 106));
        DeleteBtn.setFont(new java.awt.Font("Berlin Sans FB", 0, 20)); // NOI18N
        DeleteBtn.setForeground(new java.awt.Color(20, 20, 20));
        DeleteBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_delete_20px_2.png"))); // NOI18N
        DeleteBtn.setText("Delete");
        DeleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteBtnActionPerformed(evt);
            }
        });

        BackBtn2.setBackground(new java.awt.Color(226, 106, 106));
        BackBtn2.setFont(new java.awt.Font("Berlin Sans FB", 0, 20)); // NOI18N
        BackBtn2.setForeground(new java.awt.Color(20, 20, 20));
        BackBtn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_back_arrow_20px.png"))); // NOI18N
        BackBtn2.setText("Back");
        BackBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackBtn2ActionPerformed(evt);
            }
        });

        jLabel66.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(1, 50, 66));
        jLabel66.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel66.setText("<html> <body> <u>Contact developper :)</u> </html> </body>");
        jLabel66.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel66.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel66MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout DeletePassLayout = new javax.swing.GroupLayout(DeletePass);
        DeletePass.setLayout(DeletePassLayout);
        DeletePassLayout.setHorizontalGroup(
            DeletePassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DeletePassLayout.createSequentialGroup()
                .addGap(133, 133, 133)
                .addGroup(DeletePassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DeletePassLayout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(deleteid, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(DeletePassLayout.createSequentialGroup()
                        .addComponent(DeleteBtn)
                        .addGap(101, 101, 101)
                        .addComponent(BackBtn2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(DeletePassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DeletePassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(DeletePassLayout.createSequentialGroup()
                            .addGap(10, 10, 10)
                            .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(DeletePassLayout.createSequentialGroup()
                            .addGap(40, 40, 40)
                            .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DeletePassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel40))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DeletePassLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );
        DeletePassLayout.setVerticalGroup(
            DeletePassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DeletePassLayout.createSequentialGroup()
                .addGroup(DeletePassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DeletePassLayout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addGroup(DeletePassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(deleteid, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(97, 97, 97)
                        .addGroup(DeletePassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(DeleteBtn)
                            .addComponent(BackBtn2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DeletePassLayout.createSequentialGroup()
                        .addContainerGap(31, Short.MAX_VALUE)
                        .addGroup(DeletePassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DeletePassLayout.createSequentialGroup()
                                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DeletePassLayout.createSequentialGroup()
                                .addComponent(jLabel39)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel37)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel41)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel40)
                                .addGap(108, 108, 108)))))
                .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        CrdPanel.add(DeletePass, "DeletePass");

        Home.setBackground(new java.awt.Color(191, 191, 191));
        Home.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(226, 106, 106));
        jPanel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel3MouseExited(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(1, 50, 66));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_add_60px.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
        );

        Home.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 90, 80, 80));

        jPanel5.setBackground(new java.awt.Color(226, 106, 106));
        jPanel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel5MouseExited(evt);
            }
        });

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_bulleted_list_60px_1.png"))); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        Home.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 210, -1, 80));

        jPanel8.setBackground(new java.awt.Color(226, 106, 106));
        jPanel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel8MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel8MouseExited(evt);
            }
        });

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_download_60px_1.png"))); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        Home.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 210, -1, -1));

        jPanel9.setBackground(new java.awt.Color(226, 106, 106));
        jPanel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel9MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel9MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel9MouseExited(evt);
            }
        });

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_search_60px_1.png"))); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        Home.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 90, -1, 80));

        jPanel6.setBackground(new java.awt.Color(226, 106, 106));
        jPanel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel6MouseExited(evt);
            }
        });

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_trash_60px_1.png"))); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
        );

        Home.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 210, -1, -1));

        jPanel10.setBackground(new java.awt.Color(226, 106, 106));
        jPanel10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel10MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel10MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel10MouseExited(evt);
            }
        });

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_update_60px_1.png"))); // NOI18N

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
        );

        Home.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 90, -1, -1));

        jSeparator3.setBackground(new java.awt.Color(226, 106, 106));
        jSeparator3.setForeground(new java.awt.Color(226, 106, 106));
        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator3.setToolTipText("");
        Home.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 60, 10, 250));

        jLabel10.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(46, 49, 49));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("If you forgot theme");
        Home.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 180, 170, -1));

        jLabel61.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(46, 49, 49));
        jLabel61.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel61.setText("This software manage and save");
        Home.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 140, 170, -1));

        jLabel62.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(46, 49, 49));
        jLabel62.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel62.setText("Your password and email");
        Home.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 160, 170, -1));

        jLabel67.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(1, 50, 66));
        jLabel67.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel67.setText("<html> <body> <u>Contact developper :)</u> </html> </body>");
        jLabel67.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel67.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel67MouseClicked(evt);
            }
        });
        Home.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 330, 110, -1));

        CrdPanel.add(Home, "Home");

        jPanel2.setBackground(new java.awt.Color(226, 106, 106));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(46, 49, 49));
        jLabel1.setFont(new java.awt.Font("Berlin Sans FB", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(20, 20, 20));
        jLabel1.setText("Password Saver");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 330, -1));

        jLabel2.setBackground(new java.awt.Color(46, 49, 49));
        jLabel2.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(20, 20, 20));
        jLabel2.setText("By FACH");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 60, -1, -1));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_close_window_40px_1_1.png"))); // NOI18N
        jLabel15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel15MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel15MouseExited(evt);
            }
        });
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 0, 40, 40));

        Userrr.setBackground(new java.awt.Color(204, 204, 204));
        Userrr.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        Userrr.setForeground(new java.awt.Color(20, 20, 20));
        Userrr.setText("Connected As :");
        jPanel2.add(Userrr, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 90, 160, 20));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(CrdPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 750, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(CrdPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jSplitPane1.setRightComponent(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        // TODO add your handling code here:
        setLocation(evt.getXOnScreen() -tx, evt.getYOnScreen() -ty);
    }//GEN-LAST:event_formMouseDragged

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        // TODO add your handling code here:
        tx= evt.getX();
        ty=evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
                    //Developped by kamal fach

        // TODO add your handling code here:
        Confirm cf = new Confirm();
        cf.setVisible(true);
                    //Developped by kamal fach

    }//GEN-LAST:event_jLabel15MouseClicked

    private void jPanel7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseEntered
        // TODO add your handling code here:   
                            //Developped by kamal fach

        jPanel7.setBackground(new java.awt.Color(46,49,49));
    }//GEN-LAST:event_jPanel7MouseEntered

    private void jPanel7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseExited
        // TODO add your handling code here:
                            //Developped by kamal fach

        jPanel7.setBackground(new java.awt.Color(0,0,0));

    }//GEN-LAST:event_jPanel7MouseExited

    private void jPanel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseClicked
        // TODO add your handling code here:
                    //Developped by kamal fach
        this.setVisible(false);
        /*
        Logout logout = new Logout();
        logout.setVisible(true);
        */
       
        Login lg = new Login();
        lg.setVisible(true);
        System.out.println("disconnected from : "+Helper.username);
        Helper.username = null;
     
                    //Developped by kamal fach
        
    }//GEN-LAST:event_jPanel7MouseClicked

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
                    //Developped by kamal fach
        // TODO add your handling code here:
                crrd.show(CrdPanel, "AddPass");
                    TxtPlatform.setText("");
                    txtEmail.setText("");
                    txtUsername.setText("");
                    txtPassword.setText("");
                    
            jPanel11.setBackground(new java.awt.Color(0,0,0));
               //Home
               jPanel12.setBackground(new java.awt.Color(226,106,106));
               //Add Password
               jPanel13.setBackground(new java.awt.Color(0,0,0));
               //Update Password
               jPanel14.setBackground(new java.awt.Color(0,0,0));
               //Search for password
               jPanel15.setBackground(new java.awt.Color(0,0,0));
               //Download password 
               jPanel16.setBackground(new java.awt.Color(0,0,0));
               //Delete Password
               jPanel17.setBackground(new java.awt.Color(0,0,0));
               //Delete Password 
                
    }//GEN-LAST:event_jPanel3MouseClicked

    private void jPanel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseEntered
        // TODO add your handling code here:
        setColor(jPanel3);
    }//GEN-LAST:event_jPanel3MouseEntered

    private void jPanel3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseExited
        // TODO add your handling code here:
        resetColor(jPanel3);
    }//GEN-LAST:event_jPanel3MouseExited

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked
        // TODO add your handling code here:
                crrd.show(CrdPanel, "ListPass");
                TBLPass();
                
               jPanel11.setBackground(new java.awt.Color(0,0,0));
               //Home
               jPanel12.setBackground(new java.awt.Color(0,0,0));
               //Add Password
               jPanel13.setBackground(new java.awt.Color(0,0,0));
               //Update Password
               jPanel14.setBackground(new java.awt.Color(0,0,0));
               //Search for password
               jPanel15.setBackground(new java.awt.Color(0,0,0));
               //Download password 
               jPanel16.setBackground(new java.awt.Color(226,106,106));
               //liste
               jPanel17.setBackground(new java.awt.Color(0,0,0));
               //Delete Password 
                

    }//GEN-LAST:event_jPanel5MouseClicked

    private void jPanel5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseEntered
        // TODO add your handling code here:
        setColor(jPanel5);
    }//GEN-LAST:event_jPanel5MouseEntered

    private void jPanel5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseExited
        // TODO add your handling code here:
        resetColor(jPanel5);
    }//GEN-LAST:event_jPanel5MouseExited

    private void jPanel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseClicked
        // TODO add your handling code here:
                crrd.show(CrdPanel, "DownloadPass");
                
                jPanel11.setBackground(new java.awt.Color(0,0,0));
               //Home
               jPanel12.setBackground(new java.awt.Color(0,0,0));
               //Add Password
               jPanel13.setBackground(new java.awt.Color(0,0,0));
               //Update Password
               jPanel14.setBackground(new java.awt.Color(0,0,0));
               //Search for password
               jPanel15.setBackground(new java.awt.Color(226,106,106));
               //Download password 
               jPanel16.setBackground(new java.awt.Color(0,0,0));
               //liste
               jPanel17.setBackground(new java.awt.Color(0,0,0));
               //Delete Password 
                

    }//GEN-LAST:event_jPanel8MouseClicked

    private void jPanel8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseEntered
        // TODO add your handling code here:
        setColor(jPanel8);
    }//GEN-LAST:event_jPanel8MouseEntered

    private void jPanel8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseExited
        // TODO add your handling code here:
        resetColor(jPanel8);
    }//GEN-LAST:event_jPanel8MouseExited

    private void jPanel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseClicked
        // TODO add your handling code here:
                cboPlatformRemplissage();
                ml2.setRowCount(0);
                crrd.show(CrdPanel, "SearchPass");

                
                jPanel11.setBackground(new java.awt.Color(0,0,0));
               //Home
               jPanel12.setBackground(new java.awt.Color(0,0,0));
               //Add Password
               jPanel13.setBackground(new java.awt.Color(0,0,0));
               //Update Password
               jPanel14.setBackground(new java.awt.Color(226,106,106));
               //Search for password
               jPanel15.setBackground(new java.awt.Color(0,0,0));
               //Download password 
               jPanel16.setBackground(new java.awt.Color(0,0,0));
               //liste
               jPanel17.setBackground(new java.awt.Color(0,0,0));
               //Delete Password 
                
               
               
    }//GEN-LAST:event_jPanel9MouseClicked

    private void jPanel9MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseEntered
        // TODO add your handling code here:
        setColor(jPanel9);
    }//GEN-LAST:event_jPanel9MouseEntered

    private void jPanel9MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseExited
        // TODO add your handling code here:
        resetColor(jPanel9);
    }//GEN-LAST:event_jPanel9MouseExited

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
        // TODO add your handling code here:
                crrd.show(CrdPanel, "DeletePass");
                deleteid.setText("");  
                
                jPanel11.setBackground(new java.awt.Color(0,0,0));
               //Home
               jPanel12.setBackground(new java.awt.Color(0,0,0));
               //Add Password
               jPanel13.setBackground(new java.awt.Color(0,0,0));
               //Update Password
               jPanel14.setBackground(new java.awt.Color(0,0,0));
               //Search for password
               jPanel15.setBackground(new java.awt.Color(0,0,0));
               //Download password 
               jPanel16.setBackground(new java.awt.Color(0,0,0));
               //liste
               jPanel17.setBackground(new java.awt.Color(226,106,106));
               //Delete Password 
                
                
    }//GEN-LAST:event_jPanel6MouseClicked

    private void jPanel6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseEntered
        // TODO add your handling code here:
        setColor(jPanel6);
    }//GEN-LAST:event_jPanel6MouseEntered

    private void jPanel6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseExited
        // TODO add your handling code here:
        resetColor(jPanel6);
    }//GEN-LAST:event_jPanel6MouseExited

    private void jPanel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseClicked
                    //Developped by kamal fach
        // TODO add your handling code here:        
        crrd.show(CrdPanel, "UpdatePass");
        Txtidupdate.setText("");
        TxtPlatformUpdate.setText("");
        txtEmailUpdate.setText("");
        txtUsernameUpdate.setText("");
        txtPasswordUpdate.setText("");
                    //Developped by kamal fach
                    
         jPanel11.setBackground(new java.awt.Color(0,0,0));
               //Home
               jPanel12.setBackground(new java.awt.Color(0,0,0));
               //Add Password
               jPanel13.setBackground(new java.awt.Color(226,106,106));
               //Update Password
               jPanel14.setBackground(new java.awt.Color(0,0,0));
               //Search for password
               jPanel15.setBackground(new java.awt.Color(0,0,0));
               //Download password 
               jPanel16.setBackground(new java.awt.Color(0,0,0));
               //liste
               jPanel17.setBackground(new java.awt.Color(0,0,0));
               //Delete Password 
                

    }//GEN-LAST:event_jPanel10MouseClicked

    private void jPanel10MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseEntered
        // TODO add your handling code here:
        setColor(jPanel10);
    }//GEN-LAST:event_jPanel10MouseEntered

    private void jPanel10MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseExited
        // TODO add your handling code here:
        resetColor(jPanel10);
    }//GEN-LAST:event_jPanel10MouseExited

    private void jPanel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel11MouseClicked
                    //Developped by kamal fach
        // TODO add your handling code here:
        crrd.show(CrdPanel, "Home");
        
                
        jPanel11.setBackground(new java.awt.Color(226,106,106));
        //Home
        jPanel12.setBackground(new java.awt.Color(0,0,0));
        //Add Password
        jPanel13.setBackground(new java.awt.Color(0,0,0));
        //Update Password
        jPanel14.setBackground(new java.awt.Color(0,0,0));
        //Search for password
        jPanel15.setBackground(new java.awt.Color(0,0,0));
        //Download password 
        jPanel16.setBackground(new java.awt.Color(0,0,0));
        //List
        jPanel17.setBackground(new java.awt.Color(0,0,0));

    }//GEN-LAST:event_jPanel11MouseClicked

    private void jPanel11MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel11MouseEntered
                    //Developped by kamal fach
        // TODO add your handling code here:
               jPanel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());

    }//GEN-LAST:event_jPanel11MouseEntered

    private void jPanel11MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel11MouseExited
                    //Developped by kamal fach
        // TODO add your handling code here:
                jPanel11.setBorder(null);


    }//GEN-LAST:event_jPanel11MouseExited

    private void jPanel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel12MouseClicked
                    //Developped by kamal fach
        // TODO add your handling code here:
        crrd.show(CrdPanel, "AddPass");
                    TxtPlatform.setText("");
                    txtEmail.setText("");
                    txtUsername.setText("");
                    txtPassword.setText("");
               jPanel11.setBackground(new java.awt.Color(0,0,0));
               //Home
               jPanel12.setBackground(new java.awt.Color(226,106,106));
               //Add Password
               jPanel13.setBackground(new java.awt.Color(0,0,0));
               //Update Password
               jPanel14.setBackground(new java.awt.Color(0,0,0));
               //Search for password
               jPanel15.setBackground(new java.awt.Color(0,0,0));
               //Download password 
               jPanel16.setBackground(new java.awt.Color(0,0,0));
               //Delete Password
               jPanel17.setBackground(new java.awt.Color(0,0,0));
               //Delete Password 
    }//GEN-LAST:event_jPanel12MouseClicked

    private void jPanel12MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel12MouseEntered
        // TODO add your handling code here:
       jPanel12.setBorder(javax.swing.BorderFactory.createEtchedBorder());
    }//GEN-LAST:event_jPanel12MouseEntered

    private void jPanel12MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel12MouseExited
        // TODO add your handling code here:
       jPanel12.setBorder(null);

    }//GEN-LAST:event_jPanel12MouseExited

    private void jPanel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel13MouseClicked
                    //Developped by kamal fach
        // TODO add your handling code here:
        crrd.show(CrdPanel, "UpdatePass");
        Txtidupdate.setText("");
        TxtPlatformUpdate.setText("");
        txtEmailUpdate.setText("");
        txtUsernameUpdate.setText("");
        txtPasswordUpdate.setText("");
        
                            //Developped by kamal fach

                jPanel11.setBackground(new java.awt.Color(0,0,0));
               //Home
               jPanel12.setBackground(new java.awt.Color(0,0,0));
               //Add Password
               jPanel13.setBackground(new java.awt.Color(226,106,106));
               //Update Password
               jPanel14.setBackground(new java.awt.Color(0,0,0));
               //Search for password
               jPanel15.setBackground(new java.awt.Color(0,0,0));
               //Download password 
               jPanel16.setBackground(new java.awt.Color(0,0,0));
               //liste
               jPanel17.setBackground(new java.awt.Color(0,0,0));
               //Delete Password 
               
    }//GEN-LAST:event_jPanel13MouseClicked

    private void jPanel13MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel13MouseEntered
                    //Developped by kamal fach
        // TODO add your handling code here:
       jPanel13.setBorder(javax.swing.BorderFactory.createEtchedBorder());

    }//GEN-LAST:event_jPanel13MouseEntered

    private void jPanel13MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel13MouseExited
                    //Developped by kamal fach
        // TODO add your handling code here:
       jPanel13.setBorder(null);

    }//GEN-LAST:event_jPanel13MouseExited

    private void jPanel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel14MouseClicked
        // TODO add your handling code here:
                    //Developped by kamal fach
        cboPlatformRemplissage();
        ml2.setRowCount(0);
        crrd.show(CrdPanel, "SearchPass");
        jPanel11.setBackground(new java.awt.Color(0,0,0));
               //Home
               jPanel12.setBackground(new java.awt.Color(0,0,0));
               //Add Password
               jPanel13.setBackground(new java.awt.Color(0,0,0));
               //Update Password
               jPanel14.setBackground(new java.awt.Color(226,106,106));
               //Search for password
               jPanel15.setBackground(new java.awt.Color(0,0,0));
               //Download password 
               jPanel16.setBackground(new java.awt.Color(0,0,0));
               //liste
               jPanel17.setBackground(new java.awt.Color(0,0,0));
               //Delete Password 
                
    }//GEN-LAST:event_jPanel14MouseClicked

    private void jPanel14MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel14MouseEntered
        // TODO add your handling code here:
       jPanel14.setBorder(javax.swing.BorderFactory.createEtchedBorder());

    }//GEN-LAST:event_jPanel14MouseEntered

    private void jPanel14MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel14MouseExited
                    //Developped by kamal fach
        // TODO add your handling code here:
       jPanel14.setBorder(null);

    }//GEN-LAST:event_jPanel14MouseExited

    private void jPanel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel15MouseClicked
                    //Developped by kamal fach
        // TODO add your handling code here:
        crrd.show(CrdPanel, "DownloadPass");
        
        jPanel11.setBackground(new java.awt.Color(0,0,0));
               //Home
               jPanel12.setBackground(new java.awt.Color(0,0,0));
               //Add Password
               jPanel13.setBackground(new java.awt.Color(0,0,0));
               //Update Password
               jPanel14.setBackground(new java.awt.Color(0,0,0));
               //Search for password
               jPanel15.setBackground(new java.awt.Color(226,106,106));
               //Download password 
               jPanel16.setBackground(new java.awt.Color(0,0,0));
               //liste
               jPanel17.setBackground(new java.awt.Color(0,0,0));
               //Delete Password 
    }//GEN-LAST:event_jPanel15MouseClicked

    private void jPanel15MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel15MouseEntered
                    //Developped by kamal fach
        // TODO add your handling code here:
       jPanel15.setBorder(javax.swing.BorderFactory.createEtchedBorder());

    }//GEN-LAST:event_jPanel15MouseEntered

    private void jPanel15MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel15MouseExited
                    //Developped by kamal fach
        // TODO add your handling code here:
       jPanel15.setBorder(null);

    }//GEN-LAST:event_jPanel15MouseExited

    private void jPanel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel16MouseClicked
                    //Developped by kamal fach
        // TODO add your handling code here:
        crrd.show(CrdPanel,"ListPass");
        TBLPass();
        
        jPanel11.setBackground(new java.awt.Color(0,0,0));
               //Home
               jPanel12.setBackground(new java.awt.Color(0,0,0));
               //Add Password
               jPanel13.setBackground(new java.awt.Color(0,0,0));
               //Update Password
               jPanel14.setBackground(new java.awt.Color(0,0,0));
               //Search for password
               jPanel15.setBackground(new java.awt.Color(0,0,0));
               //Download password 
               jPanel16.setBackground(new java.awt.Color(226,106,106));
               //liste
               jPanel17.setBackground(new java.awt.Color(0,0,0));
               //Delete Password 
    }//GEN-LAST:event_jPanel16MouseClicked

    private void jPanel16MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel16MouseEntered
                    //Developped by kamal fach
        // TODO add your handling code here:
       jPanel16.setBorder(javax.swing.BorderFactory.createEtchedBorder());

    }//GEN-LAST:event_jPanel16MouseEntered

    private void jPanel16MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel16MouseExited
        // TODO add your handling code here:
       jPanel16.setBorder(null);

    }//GEN-LAST:event_jPanel16MouseExited

    private void jPanel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel17MouseClicked
                    //Developped by kamal fach
        // TODO add your handling code here:
        crrd.show(CrdPanel, "DeletePass");
        deleteid.setText("");
        
        jPanel11.setBackground(new java.awt.Color(0,0,0));
        //Home
        jPanel12.setBackground(new java.awt.Color(0,0,0));
        //Add Password
        jPanel13.setBackground(new java.awt.Color(0,0,0));
        //Update Password
        jPanel14.setBackground(new java.awt.Color(0,0,0));
        //Search for password
        jPanel15.setBackground(new java.awt.Color(0,0,0));
        //Download password 
        jPanel16.setBackground(new java.awt.Color(0,0,0));
        //List
        jPanel17.setBackground(new java.awt.Color(226,106,106));
    }//GEN-LAST:event_jPanel17MouseClicked

    private void jPanel17MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel17MouseEntered
        // TODO add your handling code here:
       jPanel17.setBorder(javax.swing.BorderFactory.createEtchedBorder());
                    //Developped by kamal fach

    }//GEN-LAST:event_jPanel17MouseEntered

    private void jPanel17MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel17MouseExited
        // TODO add your handling code here:
       jPanel17.setBorder(null);
                    //Developped by kamal fach

    }//GEN-LAST:event_jPanel17MouseExited

    private void TxtPlatformActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtPlatformActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtPlatformActionPerformed

    private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameActionPerformed

    private void BackBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackBtnActionPerformed
        // TODO add your handling code here:
        crrd.show(CrdPanel, "Home");
                    //Developped by kamal fach
                    jPanel11.setBackground(new java.awt.Color(226,106,106));
        //Home
        jPanel12.setBackground(new java.awt.Color(0,0,0));
        //Add Password
        jPanel13.setBackground(new java.awt.Color(0,0,0));
        //Update Password
        jPanel14.setBackground(new java.awt.Color(0,0,0));
        //Search for password
        jPanel15.setBackground(new java.awt.Color(0,0,0));
        //Download password 
        jPanel16.setBackground(new java.awt.Color(0,0,0));
        //List
        jPanel17.setBackground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_BackBtnActionPerformed

    private void SaveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveBtnActionPerformed
        // TODO add your handling code here:
                    //Developped by kamal fach
        String AddPlatform = TxtPlatform.getText();
        String AddEmail = txtEmail.getText();
        String AddUsername = txtUsername.getText();
        char[] pass = txtPassword.getPassword();
        String AddPassword = "";
            for (char x : pass) {
                AddPassword += x;
            }
        
        con = ConnectionUtil.getConnection();
        
         String SQL1 = "insert into pdata (platform,pemail,pusername,ppass) values (?,?,?,?)";
         String SQL3 = "insert into userdata (USERNAME,PID) values (?,Pseq.currval)";
                    
         
                    if(TxtPlatform.getText().length()==0 || txtEmail.getText().length() == 0 || txtUsername.getText().length()==0 || txtPassword.getPassword().length==0)
                    {
                        Error1 er1 = new Error1();
                        er1.setVisible(true);
                    }
                    else
                    {
         PreparedStatement pstmt;
         PreparedStatement pstmt1 ;
               try {
                   pstmt = con.prepareStatement(SQL1);
                   pstmt.setString(1, AddPlatform);
                   pstmt.setString(2, AddEmail);
                   pstmt.setString(3, AddUsername);
                   pstmt.setString(4, AddPassword);
                   pstmt.executeQuery();
                   pstmt.close();
           
                   //////////////////////////////
                   
                   pstmt1 = con.prepareStatement(SQL3);
                   pstmt1.setString(1, Helper.username);
                   pstmt1.executeQuery();
                   pstmt1.close();
                   
                   Done3 dn3 = new Done3();
                   dn3.setVisible(true);
                   
                   TxtPlatform.setText("");
                   txtEmail.setText("");
                    txtUsername.setText("");
                    txtPassword.setText("");
                   
               } catch (SQLException ex) {
                    System.out.println("erreur");
                    
               }
                    }
            
                    //Developped by kamal fach
     
    }//GEN-LAST:event_SaveBtnActionPerformed

    private void jLabel27MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel27MousePressed
        // TODO add your handling code here:
                    //Developped by kamal fach
        txtPassword.setEchoChar((char)0);
        ImageIcon icon = new ImageIcon("src/Icons/icons8_eye_20px_1.png");
        jLabel27.setIcon(icon);
    }//GEN-LAST:event_jLabel27MousePressed

    private void jLabel27MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel27MouseReleased
        // TODO add your handling code here:
                    //Developped by kamal fach
        txtPassword.setEchoChar('*');
        ImageIcon icon = new ImageIcon(getClass().getResource("/Icons/icons8_hide_20px_1.png"));
        jLabel27.setIcon(icon);
    }//GEN-LAST:event_jLabel27MouseReleased

    private void jLabel28MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel28MousePressed
        // TODO add your handling code here:
                    //Developped by kamal fach
        txtPassword.setEchoChar((char)0);
        ImageIcon icon = new ImageIcon(getClass().getResource("/Icons/icons8_eye_20px_1.png"));
        jLabel28.setIcon(icon);
    }//GEN-LAST:event_jLabel28MousePressed

    private void jLabel28MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel28MouseReleased
        // TODO add your handling code here:
                    //Developped by kamal fach
        txtPassword.setEchoChar('*');
        ImageIcon icon = new ImageIcon(getClass().getResource("/Icons/icons8_hide_20px_1.png"));
        jLabel28.setIcon(icon);
    }//GEN-LAST:event_jLabel28MouseReleased

    private void jLabel30MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel30MouseClicked
                    //Developped by kamal fach
                    crrd.show(CrdPanel,"ListPass");
        // TODO add your handling code here:
        
        jPanel11.setBackground(new java.awt.Color(0,0,0));
               //Home
               jPanel12.setBackground(new java.awt.Color(0,0,0));
               //Add Password
               jPanel13.setBackground(new java.awt.Color(0,0,0));
               //Update Password
               jPanel14.setBackground(new java.awt.Color(0,0,0));
               //Search for password
               jPanel15.setBackground(new java.awt.Color(0,0,0));
               //Download password 
               jPanel16.setBackground(new java.awt.Color(226,106,106));
               //liste
               jPanel17.setBackground(new java.awt.Color(0,0,0));
               //Delete Password 
    }//GEN-LAST:event_jLabel30MouseClicked

    private void jLabel38MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel38MouseClicked
        // TODO add your handling code here:
        crrd.show(CrdPanel,"ListPass");
        
        jPanel11.setBackground(new java.awt.Color(0,0,0));
               //Home
               jPanel12.setBackground(new java.awt.Color(0,0,0));
               //Add Password
               jPanel13.setBackground(new java.awt.Color(0,0,0));
               //Update Password
               jPanel14.setBackground(new java.awt.Color(0,0,0));
               //Search for password
               jPanel15.setBackground(new java.awt.Color(0,0,0));
               //Download password 
               jPanel16.setBackground(new java.awt.Color(226,106,106));
               //liste
               jPanel17.setBackground(new java.awt.Color(0,0,0));
               //Delete Password 
    }//GEN-LAST:event_jLabel38MouseClicked

    private void deleteidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteidActionPerformed

    private void BackBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackBtn2ActionPerformed
        // TODO add your handling code here:
        crrd.show(CrdPanel, "Home");
                    //Developped by kamal fach
        jPanel11.setBackground(new java.awt.Color(226,106,106));
        //Home
        jPanel12.setBackground(new java.awt.Color(0,0,0));
        //Add Password
        jPanel13.setBackground(new java.awt.Color(0,0,0));
        //Update Password
        jPanel14.setBackground(new java.awt.Color(0,0,0));
        //Search for password
        jPanel15.setBackground(new java.awt.Color(0,0,0));
        //Download password 
        jPanel16.setBackground(new java.awt.Color(0,0,0));
        //List
        jPanel17.setBackground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_BackBtn2ActionPerformed

    private void DeleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteBtnActionPerformed
        // TODO add your handling code here:


                            //Developped by kamal fach

        int idtodelete = Integer.parseInt(deleteid.getText());
        String SQL4 ="Delete from USERDATA where Username = ? and pid = ?";
        String SQL5 = "Delete from pdata where pid = ?";
        PreparedStatement pstmt4;
        PreparedStatement pstmt5;
        con = ConnectionUtil.getConnection();
        
        if (deleteid.getText().length()==0)
        {
            Error1 er1 = new Error1();
            
            er1.setVisible(true);
        }
        else
        {
        try {
           pstmt4 = con.prepareStatement(SQL4);
           pstmt4.setString(1,Helper.username);
           pstmt4.setInt(2,idtodelete);
           pstmt4.executeQuery();
           pstmt5 = con.prepareStatement(SQL5);
           pstmt5.setInt(1, idtodelete);
           pstmt5.executeQuery();
           
           Done4 dn4 = new Done4();
           dn4.setVisible(true);
           
           deleteid.setText("");
           
           pstmt4.close();
           pstmt5.close();
           
        } catch (SQLException ex) {
            
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);

        }
        

        }
        
                            //Developped by kamal fach

    }//GEN-LAST:event_DeleteBtnActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
                    //Developped by kamal fach
       
            con = ConnectionUtil.getConnection();
            
            String SQL9 = "select pid, platform,pemail , pusername ,  ppass from pdata join userdata using(pid) where userdata.USERNAME = ? and platform = ?";
            PreparedStatement pstmt9;
            
            ml2 = (DefaultTableModel) tblsearch.getModel();
            
            try {
                    
                   pstmt9 = con.prepareStatement(SQL9);
                   pstmt9.setString(1, Helper.username);
                   pstmt9.setString(2, (String) cboPlatform.getSelectedItem());
                try (ResultSet rs = pstmt9.executeQuery()) {
                    ml2.setRowCount(0);
                    while(rs.next())
                    {
                        ml2.addRow(new Object[]{rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)});
                    }
                }
            
                pstmt9.close(); 
            
            } catch (SQLException ex) {
                   Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                   System.out.println("erreur search !");
               }
                    //Developped by kamal fach
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btndownloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndownloadActionPerformed
        // TODO add your handling code here:
        
       /*
        con = ConnectionUtil.getConnection();
        String path = "";
        JFileChooser filechooser = new JFileChooser();
        filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int x = filechooser.showSaveDialog(this);
        
        if (x==JFileChooser.APPROVE_OPTION)
        {
            path=filechooser.getSelectedFile().getPath();
        }
        
        
        Document document = new Document();
        
               try {
                   PdfWriter.getInstance(document,new FileOutputStream(path+"/PasswordReport.pdf"));
                   
                   
                   document.open();
                   
                   
                   PdfPTable tbl = new PdfPTable(5);
                   
                   
                   tbl.addCell("Password id");
                   tbl.addCell("Platform");
                   tbl.addCell("Email");
                   tbl.addCell("Username");
                   tbl.addCell("Password");

                   for (int i = 0; i < ListPasss.getRowCount(); i++) {
                       
                        String idppp = ListPasss.getValueAt(i,0).toString();
                        String Platformpp = ListPasss.getValueAt(i,1).toString();
                        String Emailpp = ListPasss.getValueAt(i,2).toString();
                        String Usernamepp = ListPasss.getValueAt(i,3).toString();
                        String Passwordpp = ListPasss.getValueAt(i,4).toString();
                        
                        
                        tbl.addCell(idppp);
                        tbl.addCell(Platformpp);
                        tbl.addCell(Emailpp);
                        tbl.addCell(Usernamepp);
                        tbl.addCell(Passwordpp);
                        
                   }
                   
                  document.add(tbl);
                  
                   System.out.println("PDF SUCCEFULLY GENERATED");
                   
                   } catch (DocumentException | FileNotFoundException ex) {
               }
               
               document .close();
         
       */        
///////////////////////////////////
       
               MessageFormat Header = new MessageFormat("Password List");
               MessageFormat footer = new MessageFormat("PAGE # {0}");
               PrintRequestAttributeSet set = new HashPrintRequestAttributeSet();
               set.add(OrientationRequested.LANDSCAPE);
               try {
               Boolean Printing =    ListPasss.print(JTable.PrintMode.FIT_WIDTH, Header, footer);
               if (Printing )
               {
               System.out.println("succefully..");
               }
               else
               {
               System.out.println("Cancelled..");
               }
               } catch (PrinterException e) {
               System.out.println("error");
               }
              
///////////////////////////////////
/*MessageFormat Header = new MessageFormat("Password List");
MessageFormat footer = new MessageFormat("PAGE # {0}");

PrintRequestAttributeSet set = new HashPrintRequestAttributeSet();
set.add(OrientationRequested.LANDSCAPE);

try {

Boolean Printing =    ListPasss.print(JTable.PrintMode.FIT_WIDTH, Header, footer);

if (Printing )
{
System.out.println("succefully..");

}
else
{
System.out.println("Cancelled..");
}
} catch (PrinterException e) {
System.out.println("error");
}
               */     
    }//GEN-LAST:event_btndownloadActionPerformed

    private void TxtidupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtidupdateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtidupdateActionPerformed

    private void txtUsernameUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsernameUpdateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameUpdateActionPerformed

    private void jLabel46MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel46MousePressed
        // TODO add your handling code here:
                    //Developped by kamal fach
        txtPasswordUpdate.setEchoChar((char)0);
        ImageIcon icon = new ImageIcon(getClass().getResource("/Icons/icons8_eye_20px_1.png"));
        jLabel46.setIcon(icon);
    }//GEN-LAST:event_jLabel46MousePressed

    private void jLabel46MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel46MouseReleased
        // TODO add your handling code here:
                    //Developped by kamal fach
        txtPasswordUpdate.setEchoChar('*');
        ImageIcon icon = new ImageIcon(getClass().getResource("/Icons/icons8_hide_20px_1.png"));
        jLabel46.setIcon(icon);
    }//GEN-LAST:event_jLabel46MouseReleased

    private void BackBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackBtn1ActionPerformed
        // TODO add your handling code here:
        crrd.show(CrdPanel, "Home");
        jPanel11.setBackground(new java.awt.Color(226,106,106));
        //Home
        jPanel12.setBackground(new java.awt.Color(0,0,0));
        //Add Password
        jPanel13.setBackground(new java.awt.Color(0,0,0));
        //Update Password
        jPanel14.setBackground(new java.awt.Color(0,0,0));
        //Search for password
        jPanel15.setBackground(new java.awt.Color(0,0,0));
        //Download password 
        jPanel16.setBackground(new java.awt.Color(0,0,0));
        //List
        jPanel17.setBackground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_BackBtn1ActionPerformed

    private void UpdateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateBtnActionPerformed
        // TODO add your handling code here:
                    //Developped by kamal fach

                    con = ConnectionUtil.getConnection();
        
       
        
        
        int upId = Integer.parseInt(Txtidupdate.getText());
        String upplatform = TxtPlatformUpdate.getText() ; 
        String upemail = txtEmailUpdate.getText() ; 
        String upusername = txtUsernameUpdate.getText(); 
        char[] pass = txtPasswordUpdate.getPassword();
        String uppass = "";
            for (char x : pass) {
                uppass += x;
            }
            
        
        
        
        
        
       
                
                
        PreparedStatement pstmt1;
        PreparedStatement pstmt2;
        PreparedStatement pstmt3;
        PreparedStatement pstmt4;
        
        
        
        String SQLup1 = "MERGE into PDATA USING userdata ON (PDATA.PID = USERDATA.PID) WHEN MATCHED THEN UPDATE SET Platform = ? WHERE PID = ? and Username = ?";
        String SQLup2 = "MERGE into PDATA USING userdata ON (PDATA.PID = USERDATA.PID) WHEN MATCHED THEN UPDATE SET pemail = ? WHERE PID = ? and Username = ?";
        String SQLup3 = "MERGE into PDATA USING userdata ON (PDATA.PID = USERDATA.PID) WHEN MATCHED THEN UPDATE SET pusername = ? WHERE PID = ? and Username = ?";
        String SQLup4 = "MERGE into PDATA USING userdata ON (PDATA.PID = USERDATA.PID) WHEN MATCHED THEN UPDATE SET ppass = ? WHERE PID = ? and Username = ?";
        
        
            
        try {
        
            if(txtPasswordUpdate.getPassword().length == 0 &&  txtUsernameUpdate.getText().length()==0&&  txtEmailUpdate.getText().length()==0&& TxtPlatformUpdate.getText().length() ==0)
        {
            
            Error1 er1 = new Error1();
            
            er1.setVisible(true);
        }
        else
        {
            
        if (! TxtPlatformUpdate.getText().equals(""))
        {
            
                pstmt1 = con.prepareStatement(SQLup1);
                pstmt1.setString(1, upplatform);
                pstmt1.setInt(2, upId);
                pstmt1.setString(3,Helper.username);
                pstmt1.execute();
                System.out.println("Platfrom Updated for id = "+upId);
       
                pstmt1.close();
            
        }
        
        if (! txtEmailUpdate.getText().equals(""))
        {
           
                pstmt2 = con.prepareStatement(SQLup2);
                pstmt2.setString(1, upemail);
                pstmt2.setInt(2, upId);
                pstmt2.setString(3,Helper.username);
                pstmt2.execute();
                System.out.println("Email Updated for id = "+upId);
                
                pstmt2.close();
           
        }
        
        if (! txtUsernameUpdate.getText().equals(""))
        {
          
                pstmt3 = con.prepareStatement(SQLup3);
                pstmt3.setString(1, upusername);
                pstmt3.setInt(2, upId);
                pstmt3.setString(3,Helper.username);
                pstmt3.execute();
                System.out.println("Username Updated for id = "+upId);
                
                pstmt3.close();
           
        }
        
        if (txtPasswordUpdate.getPassword().length != 0)
        {
           
                pstmt4 = con.prepareStatement(SQLup4);
                pstmt4.setString(1, uppass);
                pstmt4.setInt(2, upId);
                pstmt4.setString(3,Helper.username);
                pstmt4.execute();
                System.out.println("Password Updated for id = "+upId);
                
                pstmt4.close();
                
                
             }
        
        
                Txtidupdate.setText("");
                TxtPlatformUpdate.setText("");
                txtEmailUpdate.setText("");
                txtPasswordUpdate.setText("");
                txtUsernameUpdate.setText("");
                Done5 dn5 = new Done5();
                dn5.setVisible(true); 
        }
                
        
        } catch (SQLException e) {
           
        }
        
        ///////////////
           
                
        
                    //Developped by kamal fach
        
        
        
    }//GEN-LAST:event_UpdateBtnActionPerformed
                    //Developped by kamal fach

    private void TxtPlatformUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtPlatformUpdateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtPlatformUpdateActionPerformed
                    //Developped by kamal fach

    private void jLabel49MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel49MouseClicked
        // TODO add your handling code here:
        //contact developper
        
        Desktop desktop;
if (Desktop.isDesktopSupported() 
    && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {

                
                URI mailto;
            try {
                mailto = new URI("mailto:contact.fach@gmail.com?subject=contact%20from%20"+Helper.username);
                desktop.mail(mailto);
            } catch (URISyntaxException | IOException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
                
                
          
} else {
  // TODO fallback to some Runtime.exec(..) voodoo?
  throw new RuntimeException("desktop doesn't support mailto; mail is dead anyway ;)");
}
        
        ///////////////////////////////
    }//GEN-LAST:event_jLabel49MouseClicked
                    //Developped by kamal fach

    private void txtEmailUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailUpdateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailUpdateActionPerformed
                    //Developped by kamal fach

    private void BackBtn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackBtn3ActionPerformed
        // TODO add your handling code here:
        crrd.show(CrdPanel,"Home");
        jPanel11.setBackground(new java.awt.Color(226,106,106));
        //Home
        jPanel12.setBackground(new java.awt.Color(0,0,0));
        //Add Password
        jPanel13.setBackground(new java.awt.Color(0,0,0));
        //Update Password
        jPanel14.setBackground(new java.awt.Color(0,0,0));
        //Search for password
        jPanel15.setBackground(new java.awt.Color(0,0,0));
        //Download password 
        jPanel16.setBackground(new java.awt.Color(0,0,0));
        //List
        jPanel17.setBackground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_BackBtn3ActionPerformed
                    //Developped by kamal fach

    private void BackBtn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackBtn4ActionPerformed
        // TODO add your handling code here:
        crrd.show(CrdPanel,"Home");
        jPanel11.setBackground(new java.awt.Color(226,106,106));
        //Home
        jPanel12.setBackground(new java.awt.Color(0,0,0));
        //Add Password
        jPanel13.setBackground(new java.awt.Color(0,0,0));
        //Update Password
        jPanel14.setBackground(new java.awt.Color(0,0,0));
        //Search for password
        jPanel15.setBackground(new java.awt.Color(0,0,0));
        //Download password 
        jPanel16.setBackground(new java.awt.Color(0,0,0));
        //List
        jPanel17.setBackground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_BackBtn4ActionPerformed
                    //Developped by kamal fach

    private void SearchPassMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SearchPassMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchPassMouseClicked

    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelActionPerformed
               try {
                   // TODO add your handling code here:
                   
                   
                   /* export to pdf
                   
                   MessageFormat Header = new MessageFormat("Password List");
                   MessageFormat footer = new MessageFormat("PAGE # {0}");
                   
                   PrintRequestAttributeSet set = new HashPrintRequestAttributeSet();
                   set.add(OrientationRequested.LANDSCAPE);
                   
                   try {
                   
                   Boolean Printing =    ListPasss.print(JTable.PrintMode.FIT_WIDTH, Header, footer);
                   
                   if (Printing )
                   {
                   System.out.println("succefully..");
                   
                   }
                   else
                   {
                   System.out.println("Cancelled..");
                   }
                   } catch (PrinterException e) {
                   System.out.println("error");
                   }
                   
                   */
                   ///////////////////////////////////////////////
                   
                   
                   exportToExcel(ListPasss);
                   
                   
               } catch (IOException ex) {
                   Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
               }
    
    }//GEN-LAST:event_btnExcelActionPerformed

    private void jLabel51MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel51MouseClicked
        // TODO add your handling code here:
        
                //contact developper
        
        Desktop desktop;
if (Desktop.isDesktopSupported() 
    && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {

                
                URI mailto;
            try {
                mailto = new URI("mailto:contact.fach@gmail.com?subject=contact%20from%20"+Helper.username);
                desktop.mail(mailto);
            } catch (URISyntaxException | IOException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
                
                
          
} else {
  // TODO fallback to some Runtime.exec(..) voodoo?
  throw new RuntimeException("desktop doesn't support mailto; mail is dead anyway ;)");
}

    }//GEN-LAST:event_jLabel51MouseClicked

    private void jLabel63MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel63MouseClicked
        // TODO add your handling code here:
        
                       //contact developper
        
        Desktop desktop;
if (Desktop.isDesktopSupported() 
    && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {

                
                URI mailto;
            try {
                mailto = new URI("mailto:contact.fach@gmail.com?subject=contact%20from%20"+Helper.username);
                desktop.mail(mailto);
            } catch (URISyntaxException | IOException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
                
                
          
} else {
  // TODO fallback to some Runtime.exec(..) voodoo?
  throw new RuntimeException("desktop doesn't support mailto; mail is dead anyway ;)");
}

    }//GEN-LAST:event_jLabel63MouseClicked

    private void jLabel64MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel64MouseClicked
        // TODO add your handling code here:
            
                       //contact developper
        
        Desktop desktop;
if (Desktop.isDesktopSupported() 
    && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {

                
                URI mailto;
            try {
                mailto = new URI("mailto:contact.fach@gmail.com?subject=contact%20from%20"+Helper.username);
                desktop.mail(mailto);
            } catch (URISyntaxException | IOException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
                
                
          
} else {
  // TODO fallback to some Runtime.exec(..) voodoo?
  throw new RuntimeException("desktop doesn't support mailto; mail is dead anyway ;)");
}
    }//GEN-LAST:event_jLabel64MouseClicked

    private void jLabel65MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel65MouseClicked
        // TODO add your handling code here:
        
             // TODO add your handling code here:
            
                       //contact developper
        
        Desktop desktop;
if (Desktop.isDesktopSupported() 
    && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {

                
                URI mailto;
            try {
                mailto = new URI("mailto:contact.fach@gmail.com?subject=contact%20from%20"+Helper.username);
             
                desktop.mail(mailto);
            } catch (URISyntaxException | IOException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
                
                
          
} else {
  // TODO fallback to some Runtime.exec(..) voodoo?
  throw new RuntimeException("desktop doesn't support mailto; mail is dead anyway ;)");
}

    }//GEN-LAST:event_jLabel65MouseClicked

    private void jLabel66MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel66MouseClicked
        // TODO add your handling code here:
             
             // TODO add your handling code here:
            
                       //contact developper
        
        Desktop desktop;
if (Desktop.isDesktopSupported() 
    && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {

                
                URI mailto;
            try {
                mailto = new URI("mailto:contact.fach@gmail.com?subject=contact%20from%20"+Helper.username);
                desktop.mail(mailto);
            } catch (URISyntaxException | IOException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
                
                
          
} else {
  // TODO fallback to some Runtime.exec(..) voodoo?
  throw new RuntimeException("desktop doesn't support mailto; mail is dead anyway ;)");
}
    }//GEN-LAST:event_jLabel66MouseClicked

    private void jLabel67MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel67MouseClicked
        // TODO add your handling code here:
                         //contact developper
        
        Desktop desktop;
if (Desktop.isDesktopSupported() 
    && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {

                
                URI mailto;
            try {
                mailto = new URI("mailto:contact.fach@gmail.com?subject=contact%20from%20"+Helper.username);
                desktop.mail(mailto);
            } catch (URISyntaxException | IOException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
                
                
          
} else {
  // TODO fallback to some Runtime.exec(..) voodoo?
  throw new RuntimeException("desktop doesn't support mailto; mail is dead anyway ;)");
}
    }//GEN-LAST:event_jLabel67MouseClicked

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordActionPerformed

    private void jLabel15MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseEntered
        // TODO add your handling code here:
        ImageIcon icon = new ImageIcon(getClass().getResource("/Icons/icons8_close_window_40px_1.png"));
        jLabel15.setIcon(icon);
    }//GEN-LAST:event_jLabel15MouseEntered

    private void jLabel15MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseExited
        // TODO add your handling code here:
        ImageIcon icon = new ImageIcon(getClass().getResource("/Icons/icons8_close_window_40px_1_1_1.png"));
        jLabel15.setIcon(icon);
    }//GEN-LAST:event_jLabel15MouseExited

           @SuppressWarnings("empty-statement")
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
               //</editor-fold>
               //</editor-fold>
               //</editor-fold>
               //</editor-fold>
               //</editor-fold>
               //</editor-fold>
               //</editor-fold>
               //</editor-fold>
               //</editor-fold>
               //</editor-fold>
               //</editor-fold>
               //</editor-fold>
               //</editor-fold>
               //</editor-fold>
               //</editor-fold>
               //</editor-fold>
               
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
         
           Dashboard ddsh= new Dashboard();
           ddsh.setVisible(true);
           
        ;
    }
                    //Developped by kamal fach

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AddPass;
    private javax.swing.JButton BackBtn;
    private javax.swing.JButton BackBtn1;
    private javax.swing.JButton BackBtn2;
    private javax.swing.JButton BackBtn3;
    private javax.swing.JButton BackBtn4;
    private javax.swing.JPanel CrdPanel;
    private javax.swing.JButton DeleteBtn;
    private javax.swing.JPanel DeletePass;
    private javax.swing.JPanel DownloadPass;
    private javax.swing.JPanel Home;
    private javax.swing.JPanel ListPass;
    public static javax.swing.JTable ListPasss;
    private javax.swing.JButton SaveBtn;
    private javax.swing.JPanel SearchPass;
    private javax.swing.JTextField TxtPlatform;
    private javax.swing.JTextField TxtPlatformUpdate;
    private javax.swing.JTextField Txtidupdate;
    private javax.swing.JButton UpdateBtn;
    private javax.swing.JPanel UpdatePass;
    private javax.swing.JLabel Userrr;
    private javax.swing.JButton btnExcel;
    private javax.swing.JButton btndownload;
    public static javax.swing.JComboBox<String> cboPlatform;
    private javax.swing.JTextField deleteid;
    private javax.swing.JButton jButton1;
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
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JSplitPane jSplitPane1;
    public static javax.swing.JTable tblsearch;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEmailUpdate;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JPasswordField txtPasswordUpdate;
    private javax.swing.JTextField txtUsername;
    private javax.swing.JTextField txtUsernameUpdate;
    // End of variables declaration//GEN-END:variables
                    //Developped by kamal fach
}
