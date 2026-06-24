                        /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.tubes_kasir_f2ker.Pages;

import com.mycompany.tubes_kasir_f2ker.model.SessionUser;
import com.mycompany.tubes_kasir_f2ker.controller.ProdukController;
import com.mycompany.tubes_kasir_f2ker.model.ProdukItem;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author attau
 */
public class ManageProduk extends javax.swing.JFrame {
    private final ProdukController produkController = new ProdukController();
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ManageProduk.class.getName());
    private java.util.Map<String, Integer> kategoriMap = new java.util.HashMap<>();

    /**
     * Creates new form ManageProduk
     */
    private int selectedId = -1;
    public ManageProduk() {
        initComponents();
        setTitle("Manage Produk - " + SessionUser.username);
        this.setLocationRelativeTo(null);
        model.addColumn("ID");
        model.addColumn("Nama Produk");
        model.addColumn("Kategori");
        model.addColumn("Harga");
        model.addColumn("Stok");

        tblProduk.setModel(model);
        tblProduk.setRowSorter(rowSorter);

        // Sembunyikan kolom ID
        tblProduk.getColumnModel().getColumn(0).setMinWidth(0);
        tblProduk.getColumnModel().getColumn(0).setMaxWidth(0);
        tblProduk.getColumnModel().getColumn(0).setWidth(0);

        tblProduk.getColumnModel().getColumn(1).setPreferredWidth(180);
        tblProduk.getColumnModel().getColumn(2).setPreferredWidth(120);
        tblProduk.getColumnModel().getColumn(3).setPreferredWidth(80);
        tblProduk.getColumnModel().getColumn(4).setPreferredWidth(60);

        // Klik row auto-fill form
        tblProduk.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                klikRow();
            }
        });

        loadKategori();
        loadDataProduk("");
    }
    
    private void loadKategori() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/db_kasir", "root", ""
            );

            ps = conn.prepareStatement(
                "SELECT id_kategori, nama_kategori FROM kategori_produk ORDER BY nama_kategori ASC"
            );
            rs = ps.executeQuery();

            cmbKategori.removeAllItems();
            kategoriMap.clear();

            cmbKategori.addItem("-- Pilih Kategori --");

            while (rs.next()) {
                String nama = rs.getString("nama_kategori");
                int id      = rs.getInt("id_kategori");
                cmbKategori.addItem(nama);
                kategoriMap.put(nama, id);   
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Gagal load kategori: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null)   rs.close();
                if (ps != null)   ps.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }
    
    public void loadDataProduk(String keyword) {
        try {
            List<ProdukItem> list = produkController.searchProduk(keyword);
            model.setRowCount(0);
            for (ProdukItem p : list) {
                model.addRow(new Object[]{
                    p.getId(),
                    p.getNama(),
                    p.getNamaKategori(),
                    p.getHarga(),
                    p.getStok()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void klikRow() {
        int row = tblProduk.getSelectedRow();
        if (row == -1) return;

        selectedId        = Integer.parseInt(tblProduk.getValueAt(row, 0).toString());
        String nama       = tblProduk.getValueAt(row, 1).toString();
        String kategori   = tblProduk.getValueAt(row, 2) != null 
                            ? tblProduk.getValueAt(row, 2).toString() : "";
        String harga      = tblProduk.getValueAt(row, 3).toString();
        String stok       = tblProduk.getValueAt(row, 4).toString();

        tfNama.setText(nama);
        tfHarga.setText(harga);
        tfStok.setText(stok);

        cmbKategori.setSelectedItem(kategori);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblProduk = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tfNama = new javax.swing.JTextField();
        tfHarga = new javax.swing.JTextField();
        tfStok = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        cmbKategori = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblProduk.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nama Produk", "Kategori", "Harga", "Stok"
            }
        ));
        jScrollPane1.setViewportView(tblProduk);

        jLabel1.setText("Nama Produk");

        jLabel2.setText("Harga");

        jLabel3.setText("Stok");

        tfHarga.addActionListener(this::tfHargaActionPerformed);

        jButton1.setText("Add Produk");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jButton2.setText("Update Produk");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        jButton3.setText("Delete Produk");
        jButton3.addActionListener(this::jButton3ActionPerformed);

        jButton4.setText("Back");
        jButton4.addActionListener(this::jButton4ActionPerformed);

        jLabel4.setText("Kategori");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton4))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel4)))
                .addContainerGap(486, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(54, 54, 54)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfHarga)
                            .addComponent(tfStok)
                            .addComponent(tfNama)
                            .addComponent(cmbKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton4)
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tfNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tfHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tfStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cmbKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ProdukItem produk = getFormInput();
        if (produk == null) return;

        try {
            produkController.addProduk(produk);
            JOptionPane.showMessageDialog(this, "Produk berhasil ditambahkan!", "Sukses",
                JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            loadDataProduk("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih produk dari tabel terlebih dahulu!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ProdukItem produk = getFormInput();
        if (produk == null) return;
        produk.setId(selectedId);

        try {
            produkController.updateProduk(produk);
            JOptionPane.showMessageDialog(this, "Produk berhasil diupdate!", "Sukses",
                JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            loadDataProduk("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih produk dari tabel terlebih dahulu!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(this,
            "Yakin ingin menghapus produk " + tfNama.getText() + "?",
            "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (konfirmasi != JOptionPane.YES_OPTION) return;

        try {
            produkController.deleteProduk(selectedId);
            JOptionPane.showMessageDialog(this, "Produk berhasil dihapus!", "Sukses",
                JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            loadDataProduk("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private ProdukItem getFormInput() {
        String nama  = tfNama.getText().trim();
        String harga = tfHarga.getText().trim();
        String stok  = tfStok.getText().trim();

        if (nama.isEmpty() || harga.isEmpty() || stok.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        double hargaVal;
        int stokVal;
        try {
            hargaVal = Double.parseDouble(harga);
            stokVal  = Integer.parseInt(stok);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Harga dan stok harus berupa angka!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        if (hargaVal < 0 || stokVal < 0) {
            JOptionPane.showMessageDialog(this, "Harga dan stok tidak boleh negatif!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        String selectedNama = (String) cmbKategori.getSelectedItem();
        if (selectedNama == null || selectedNama.equals("-- Pilih Kategori --")) {
            JOptionPane.showMessageDialog(this, "Pilih kategori terlebih dahulu!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        int idKategori = kategoriMap.get(selectedNama);
        return new ProdukItem(nama, idKategori, selectedNama, hargaVal, stokVal);
    }
    
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        new DashboardProduct().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void tfHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfHargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfHargaActionPerformed

    private void clearForm() {
        tfNama.setText("");
        tfHarga.setText("");
        tfStok.setText("");
        cmbKategori.setSelectedIndex(0); 
        selectedId = -1;
        tblProduk.clearSelection();
    }
    /**
     * @param args the command line arguments
     */
    
    private DefaultTableModel model = new DefaultTableModel(){
        @Override
        public boolean isCellEditable(int row, int column){
           return false;
        }
    };
    
    
    private TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(model);
    
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
        java.awt.EventQueue.invokeLater(() -> new ManageProduk().setVisible(true));
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbKategori;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblProduk;
    private javax.swing.JTextField tfHarga;
    private javax.swing.JTextField tfNama;
    private javax.swing.JTextField tfStok;
    // End of variables declaration//GEN-END:variables
}
