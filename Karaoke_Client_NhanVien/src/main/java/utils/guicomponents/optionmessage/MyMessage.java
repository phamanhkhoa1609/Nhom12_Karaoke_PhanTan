package utils.guicomponents.optionmessage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

/**
 *
 * @author RAVEN
 */
public class MyMessage extends javax.swing.JPanel {
	public MyMessage(String title, String content, boolean canNutHuy) {
        setOpaque(false);
        
        lblTieuDe = new javax.swing.JLabel();
        txtContent = new javax.swing.JTextPane();
        btnOK = new MyMessageButton();
        btnCancel = new MyMessageButton();
        
        txtContent.setBackground(new Color(0, 0, 0, 0));
        txtContent.setSelectionColor(new Color(48, 170, 63, 200));
        txtContent.setOpaque(false);

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(25, 25, 25, 25));

        lblTieuDe.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        lblTieuDe.setForeground(new java.awt.Color(80, 80, 80));
        lblTieuDe.setText(title);

        txtContent.setEditable(false);
        txtContent.setForeground(new java.awt.Color(133, 133, 133));
        txtContent.setText(content);

        btnOK.setBackground(new java.awt.Color(48, 170, 63));
        btnOK.setForeground(new java.awt.Color(255, 255, 255));
        btnOK.setText("OK");

        btnCancel.setBackground(Color.red);
        btnCancel.setText("Hủy");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCancelActionPerformed(evt);
            }
        });
        btnCancel.setVisible(canNutHuy);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTieuDe)
                        .addGap(0, 261, Short.MAX_VALUE))
                    .addComponent(txtContent, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnOK, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTieuDe)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtContent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
	}

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
        g2.dispose();
        super.paintComponent(grphcs);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents

    private void cmdCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCancelActionPerformed
        GlassPanePopup.closePopupLast();
    }//GEN-LAST:event_cmdCancelActionPerformed

    public void eventOK(ActionListener event) {
        btnOK.addActionListener(event);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private MyMessageButton btnCancel;
    private MyMessageButton btnOK;
    private javax.swing.JLabel lblTieuDe;
    private javax.swing.JTextPane txtContent;
    private boolean canNutHuy;
    // End of variables declaration//GEN-END:variables
}
