package gui.subgui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;

import javax.swing.SwingConstants;

import entities.Phong;
import entities.enums.TrangThaiPhong;

import java.awt.Font;
import net.miginfocom.swing.MigLayout;
import utils.VietnameseFormatter;

public class RoomCardPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Phong phong;

	public Phong getPhong() {
		return phong;
	}

	/**
	 * Create the panel.
	 */
	public RoomCardPanel(Phong phong) {
		setOpaque(false);
		this.phong = phong;
		JLabel lblTenPhong = new JLabel();
		lblTenPhong.setText(VietnameseFormatter.catChuoi(phong.getMaPhong() + " - " + phong.getTenPhong(), 20));
		lblTenPhong.setHorizontalAlignment(SwingConstants.CENTER);
		lblTenPhong.setVerticalAlignment(SwingConstants.BOTTOM);
		lblTenPhong.setForeground(Color.WHITE);
		lblTenPhong.setFont(new Font("Times New Roman", Font.BOLD, 15));

		JLabel lblHinhPhong = new JLabel();
		lblHinhPhong.setVerticalAlignment(SwingConstants.BOTTOM);
		lblHinhPhong.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblGia = new JLabel( VietnameseFormatter.dinhDangTien(phong.getGiaPhong()) +"/Giờ");
		lblGia.setHorizontalAlignment(SwingConstants.CENTER);
		lblGia.setVerticalAlignment(SwingConstants.BOTTOM);
		lblGia.setForeground(Color.RED);
		lblGia.setFont(new Font("Times New Roman", Font.BOLD, 17));
		setLayout(new MigLayout("", "[180px]", "[210px][68px][26px][][]"));
		add(lblHinhPhong, "cell 0 0,grow");
		add(lblTenPhong, "cell 0 1,alignx center,growy");

		JLabel lblTrangThai = new JLabel(phong.getTrangThai().getTrangThaiPhong());
		if(phong.getTrangThai().equals(TrangThaiPhong.TRỐNG))
			lblTrangThai.setForeground(Color.green);
		if(phong.getTrangThai().equals(TrangThaiPhong.ĐANGSỬDỤNG))
			lblTrangThai.setForeground(Color.yellow);
		if(phong.getTrangThai().equals(TrangThaiPhong.ĐANGBẢOTRÌ))
			lblTrangThai.setForeground(Color.red);
		
		lblTrangThai.setHorizontalAlignment(SwingConstants.CENTER);
		lblTrangThai.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		add(lblTrangThai, "cell 0 2,growx");
		
		JLabel lblLoaiPhong = new JLabel(phong.getLoaiPhong().getLoaiPhong());
		lblLoaiPhong.setVerticalAlignment(SwingConstants.BOTTOM);
		lblLoaiPhong.setHorizontalAlignment(SwingConstants.TRAILING);
		lblLoaiPhong.setForeground(Color.LIGHT_GRAY);
		lblLoaiPhong.setFont(new Font("Tahoma", Font.PLAIN, 13));
		add(lblLoaiPhong, "cell 0 3,alignx center");
		add(lblGia, "cell 0 4,grow");
		
		final int imageHeight = 60, imageWidth = 90;
		lblHinhPhong.setIcon(phong.getHinh(imageWidth,imageHeight,0));
	}
}
