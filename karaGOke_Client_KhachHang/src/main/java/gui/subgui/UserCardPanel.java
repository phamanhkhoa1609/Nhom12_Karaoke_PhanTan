package gui.subgui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;

import javax.swing.SwingConstants;

import entities.KhachHang;
import entities.NguoiDung;
import entities.NhanVien;
import entities.enums.GioiTinh;

import java.awt.GridLayout;
import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class UserCardPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel lblHinhNhanVien;

	/**
	 * Create the panel.
	 */
	public UserCardPanel(NguoiDung nguoiDung) {
		setOpaque(false);
		String chucVu = "";
		int kickThuocHinh = 0;
		if(nguoiDung instanceof KhachHang) {
			chucVu = "Khách hàng ";
		}
		chucVu += " "+nguoiDung.getMaND();
		JLabel lblChucVu = new JLabel(chucVu);
		lblChucVu.setVerticalAlignment(SwingConstants.BOTTOM);
		lblChucVu.setForeground(Color.LIGHT_GRAY);
		lblChucVu.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblHinhNhanVien = new JLabel();
		if(nguoiDung instanceof NhanVien) {
			lblHinhNhanVien.setVerticalAlignment(SwingConstants.BOTTOM);
			lblHinhNhanVien.setHorizontalAlignment(SwingConstants.CENTER);
			lblHinhNhanVien.setIcon(((NhanVien)nguoiDung).getHinh(50, 50, 0));
		}

		JLabel lblHoTenNhanVien = new JLabel(nguoiDung.getHoTen());
		lblHoTenNhanVien.setVerticalAlignment(SwingConstants.BOTTOM);
		lblHoTenNhanVien.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblHoTenNhanVien.setForeground(Color.WHITE);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(lblHinhNhanVien, GroupLayout.PREFERRED_SIZE, kickThuocHinh, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblChucVu, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addContainerGap())
						.addComponent(lblHoTenNhanVien, GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(215, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblHinhNhanVien, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(lblChucVu, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblHoTenNhanVien, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		setLayout(groupLayout);
	}
}
