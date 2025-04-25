package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import java.awt.Color;
import java.awt.Dimension;


import entities.NhanVien;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;
import utils.VietnameseFormatter;
import utils.guicomponents.MyPanel;
import utils.guicomponents.datechooser.MyDateChooser;

import javax.swing.Icon;

public class ThongTinChiTietNhanVienPanel extends MyPanel {

	private JTextField txtMaNV;
	private JTextField txtHoTen;
	private JLabel lblCCCD;
	private JTextField txtCCCD;
	private JLabel lblSDT;
	private JTextField txtSDT;
	private JLabel lblChucVu;
	private JLabel lblGioiTinh;
	private JLabel lblDiaChi;
	private JTextField txtDiaChi;
	private MyDateChooser dateChooser;
	private JLabel lblNgaySinh;
	private JTextField txtNgaySinh;
	private JLabel lblTrangThai;
	private NhanVien nhanVien;
	private JLabel lblHinhNV;
	private JTextField txtGioiTinh;
	private JTextField txtTrangThai;
	private JTextField txtChucVu;


	public ThongTinChiTietNhanVienPanel(NhanVien nhanVien){
		this.nhanVien = nhanVien;
	}
	@Override
	public void KhoiTaoGiaoDien() {
		setLayout(new MigLayout("", "[300px:300px,grow][300px:300px,grow]", "[][0px:365px:365px][][30px:40px:40px][][30px:40px:40px][][30px:40px:40px][][30px:40px:40px][][30px:40px:40px]"));
		Border blackline = BorderFactory.createLineBorder(Color.black);
		List<JTextField> listTextField = new ArrayList<JTextField>();
		JLabel lblTieuDe = new JLabel();
		lblTieuDe.setHorizontalAlignment(SwingConstants.CENTER);
		lblTieuDe.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTieuDe.setForeground(Color.WHITE);
		lblTieuDe.setText("Thông tin chi tiết Nhân viên");
		add(lblTieuDe, "cell 0 0 2 1,alignx center");
		
		lblHinhNV = new JLabel(nhanVien.getHinh(270, 360, 0));
		add(lblHinhNV, "cell 0 1 2 1,alignx center,aligny center");
		
		lblCCCD = new JLabel("Căn cước công dân");
		lblCCCD.setHorizontalAlignment(SwingConstants.CENTER);
		lblCCCD.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblCCCD, "cell 1 2,alignx center");
		
		txtCCCD = new JTextField(nhanVien.getCccd());
		txtCCCD.setForeground(Color.WHITE);
		txtCCCD.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		listTextField.add(txtCCCD);
		add(txtCCCD, "cell 1 3,grow");
		
		lblSDT = new JLabel("Số điện thoại");
		lblSDT.setHorizontalAlignment(SwingConstants.CENTER);
		lblSDT.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblSDT, "cell 1 4,alignx center");

		JLabel lblMaNV = new JLabel("Mã nhân viên");
		lblMaNV.setHorizontalAlignment(SwingConstants.CENTER);
		lblMaNV.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblMaNV, "cell 0 2,alignx center");

		txtMaNV = new JTextField(nhanVien.getMaND() + "");
		txtMaNV.setForeground(Color.WHITE);
		txtMaNV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		listTextField.add(txtMaNV);
		listTextField.add(txtMaNV);
		add(txtMaNV, "cell 0 3,grow");

		JLabel lblHoTen = new JLabel("Họ tên");
		lblHoTen.setHorizontalAlignment(SwingConstants.CENTER);
		lblHoTen.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblHoTen, "cell 0 4,alignx center");

		txtHoTen = new JTextField(nhanVien.getHoTen());
		txtHoTen.setForeground(Color.WHITE);
		txtHoTen.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		listTextField.add(txtHoTen);
		listTextField.add(txtHoTen);
		add(txtHoTen, "cell 0 5,grow");
		
		txtSDT = new JTextField(nhanVien.getSdt());
		txtSDT.setForeground(Color.WHITE);
		txtSDT.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		listTextField.add(txtSDT);
		add(txtSDT, "cell 1 5,grow");
		
		lblChucVu = new JLabel("Chức vụ");
		lblChucVu.setHorizontalAlignment(SwingConstants.CENTER);
		lblChucVu.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblChucVu, "cell 0 6,alignx center");
		
		lblNgaySinh = new JLabel("Ngày sinh");
		lblNgaySinh.setHorizontalAlignment(SwingConstants.CENTER);
		lblNgaySinh.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblNgaySinh, "cell 1 6,alignx center");
		
		txtChucVu = new JTextField(nhanVien.getChucVu().getChucVu());
		txtChucVu.setForeground(Color.WHITE);
		txtChucVu.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		listTextField.add(txtChucVu);
		add(txtChucVu, "cell 0 7,grow");
		
		txtNgaySinh = new JTextField(VietnameseFormatter.dinhDangNgayString(nhanVien.getNgaySinh())); 
		txtNgaySinh.setForeground(Color.WHITE);
		txtNgaySinh.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		listTextField.add(txtNgaySinh);
		add(txtNgaySinh, "cell 1 7,grow");
		
		
		lblGioiTinh = new JLabel("Giới tính");
		lblGioiTinh.setHorizontalAlignment(SwingConstants.CENTER);
		lblGioiTinh.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblGioiTinh, "cell 0 8,alignx center");
		
		lblTrangThai = new JLabel("Trạng thái:");
		lblTrangThai.setHorizontalAlignment(SwingConstants.CENTER);
		lblTrangThai.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblTrangThai, "cell 1 8,alignx center");
		
		txtGioiTinh = new JTextField(nhanVien.getGioiTinh().getGioiTinh());
		txtGioiTinh.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtGioiTinh.setForeground(Color.WHITE);
		listTextField.add(txtGioiTinh);
		add(txtGioiTinh, "cell 0 9,grow");
		
		txtTrangThai = new JTextField(nhanVien.getTrangThai().getTrangThaiNhanVien());
		txtTrangThai.setForeground(Color.WHITE);
		txtTrangThai.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		add(txtTrangThai, "cell 1 9,grow");
		listTextField.add(txtTrangThai);
		lblDiaChi = new JLabel("Địa chỉ");
		lblDiaChi.setHorizontalAlignment(SwingConstants.CENTER);
		lblDiaChi.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblDiaChi, "cell 0 10 2 1,alignx center");
		
		txtDiaChi = new JTextField(nhanVien.getDiaChi());
		txtDiaChi.setForeground(Color.WHITE);
		txtDiaChi.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		listTextField.add(txtDiaChi);
		add(txtDiaChi, "cell 0 11 2 1,grow");

		for (JTextField jTextField : listTextField) {
			jTextField.setBorder(null);
			jTextField.setHorizontalAlignment(SwingConstants.CENTER);
			jTextField.setEditable(false);
			jTextField.setFocusable(false);
		}
	}
}
