package gui.subgui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;

import javax.swing.SwingConstants;

import entities.DichVu;
import entities.enums.TrangThaiDichVu;
import entities.enums.TrangThaiPhong;

import java.awt.Font;
import net.miginfocom.swing.MigLayout;
import utils.VietnameseFormatter;

public class ServiceCardPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private DichVu dichVu;

	public DichVu getDichVu() {
		return dichVu;
	}

	/**
	 * Create the panel.
	 */
	public ServiceCardPanel(DichVu dichVu) {
		setOpaque(false);

		JLabel lblTenDichVu = new JLabel();
		lblTenDichVu.setText(VietnameseFormatter.catChuoi(dichVu.getMaDV() + " - " + dichVu.getTenDV(), 20));
		lblTenDichVu.setHorizontalAlignment(SwingConstants.CENTER);
		lblTenDichVu.setVerticalAlignment(SwingConstants.BOTTOM);
		lblTenDichVu.setForeground(Color.WHITE);
		lblTenDichVu.setFont(new Font("Times New Roman", Font.BOLD, 15));

		JLabel lblHinhDichVu = new JLabel();
		lblHinhDichVu.setVerticalAlignment(SwingConstants.BOTTOM);
		lblHinhDichVu.setHorizontalAlignment(SwingConstants.CENTER);
		lblHinhDichVu.setIcon(dichVu.getHinh(60, 80, 0));

		JLabel lblGia = new JLabel();
		lblGia.setText("Giá: " + VietnameseFormatter.dinhDangTien(dichVu.getGiaDV() ));
		lblGia.setHorizontalAlignment(SwingConstants.CENTER);
		lblGia.setVerticalAlignment(SwingConstants.BOTTOM);
		lblGia.setForeground(Color.RED);
		lblGia.setFont(new Font("Tahoma", Font.BOLD, 17));
		setLayout(new MigLayout("", "[179px,grow][6px]", "[210px][68px][][][][26px]"));
		add(lblHinhDichVu, "cell 0 0 2 1,grow");
		add(lblTenDichVu, "cell 0 1 2 1,alignx center");

		JLabel lblTrangThai = new JLabel(dichVu.getTrangThai().getTrangThaiDichVu());
		lblTrangThai.setHorizontalAlignment(SwingConstants.LEFT);
		if(dichVu.getTrangThai().equals(TrangThaiDichVu.KINHDOANH))
			lblTrangThai.setForeground(Color.green);
		if(dichVu.getTrangThai().equals(TrangThaiDichVu.NGỪNGKINHDOANH))
			lblTrangThai.setForeground(Color.red);
		lblTrangThai.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblTrangThai.setVerticalAlignment(SwingConstants.BOTTOM);
		add(lblTrangThai, "cell 0 2,alignx center");
		
		JLabel lblLoaiPhong = new JLabel();
		lblLoaiPhong.setText(dichVu.getLoaiDV().getLoaiDichVu());
		lblLoaiPhong.setVerticalAlignment(SwingConstants.BOTTOM);
		lblLoaiPhong.setForeground(Color.LIGHT_GRAY);
		lblLoaiPhong.setFont(new Font("Tahoma", Font.PLAIN, 15));
		add(lblLoaiPhong, "cell 0 3,alignx center");

		JLabel lblSoLuong = new JLabel();
		lblSoLuong.setText("SL: " + dichVu.getSoLuong());
		lblSoLuong.setVerticalAlignment(SwingConstants.BOTTOM);
		lblSoLuong.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSoLuong.setForeground(Color.LIGHT_GRAY);
		add(lblSoLuong, "cell 0 4 2 1,alignx center,growy");
		add(lblGia, "cell 0 5 2 1,grow");
	}
}
