package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.border.TitledBorder;

import entities.DichVu;
import entities.Phong;
import entities.enums.TrangThaiPhong;

import javax.swing.border.EtchedBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import net.miginfocom.swing.MigLayout;
import utils.VietnameseFormatter;
import utils.guicomponents.MyImageIcon;
import utils.guicomponents.MyPanel;

public class ThongTinChiTietPhongPanel extends MyPanel {

	private JPanel contentPane;
	private JTextField txtMaPhong;
	private JTextField txtTenPhong;
	private JTextField txtGiaPHong;
	private JTextField txtLoaiPhong;
	private Phong phong;
	private JTextField txtTrangThai;
	private JTextField txtSoLuong;
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public ThongTinChiTietPhongPanel(Phong phong) {
		this.phong = phong;
	}
	@Override
	public void KhoiTaoGiaoDien() {
		// TODO Auto-generated method stub
        setLayout(new MigLayout("", "[407.00px,grow]", "[][0px:270px:275px][][30px:40px:40px][][30px:40px:40px][][30px:40px:40px][][30px:40px:40px][][30px:40px:40px][][30px:40px:40px][][200px:200px,grow]"));
		JLabel lblHinhPhong = new JLabel();
		lblHinhPhong.setIcon(phong.getHinh(360,270,0));
		Border blackline = BorderFactory.createLineBorder(Color.black);
		
		JLabel lblTieuDe = new JLabel();
		lblTieuDe.setHorizontalAlignment(SwingConstants.CENTER);
		lblTieuDe.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTieuDe.setForeground(Color.WHITE);
		lblTieuDe.setText("Thông tin chi tiết Phòng");
		add(lblTieuDe, "cell 0 0,alignx center");
		lblHinhPhong.setBorder(blackline);
		add(lblHinhPhong, "cell 0 1,alignx center,aligny center");
		
		
		List<JTextField> listTextField = new ArrayList<JTextField>();
		
		JLabel lblMaPhong = new JLabel("Mã phòng");
		lblMaPhong.setHorizontalAlignment(SwingConstants.CENTER);
		lblMaPhong.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblMaPhong, "cell 0 2,alignx center");
		
		txtMaPhong = new JTextField(phong.getMaPhong()+"");
		txtMaPhong.setForeground(Color.WHITE);
		txtMaPhong.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		listTextField.add(txtMaPhong);
		add(txtMaPhong, "cell 0 3,grow");
		
		JLabel lblTenPhong = new JLabel("Tên phòng");
		lblTenPhong.setHorizontalAlignment(SwingConstants.CENTER);
		lblTenPhong.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblTenPhong, "cell 0 4,alignx center");
		
		txtTenPhong = new JTextField(phong.getTenPhong());
		txtTenPhong.setForeground(Color.WHITE);
		txtTenPhong.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		listTextField.add(txtTenPhong);
		add(txtTenPhong, "cell 0 5,grow");
		
		JLabel lblGiaPhong = new JLabel("Giá phòng");
		lblGiaPhong.setHorizontalAlignment(SwingConstants.CENTER);
		lblGiaPhong.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblGiaPhong, "cell 0 6,alignx center");
		
		txtGiaPHong = new JTextField( VietnameseFormatter.dinhDangTien(phong.getGiaPhong()));
		txtGiaPHong.setForeground(Color.WHITE);
		txtGiaPHong.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		listTextField.add(txtGiaPHong);
		add(txtGiaPHong, "cell 0 7,grow");
		
		JLabel lblSoLuong = new JLabel("Số lượng");
		lblSoLuong.setHorizontalAlignment(SwingConstants.CENTER);
		lblSoLuong.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblSoLuong, "cell 0 8,alignx center");
		
		txtSoLuong = new JTextField("0");
		txtSoLuong.setForeground(Color.WHITE);
		txtSoLuong.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		listTextField.add(txtSoLuong);
		add(txtSoLuong, "cell 0 9,grow");
		
		JLabel lblLoaiPhong = new JLabel("Loại phòng:");
		lblLoaiPhong.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoaiPhong.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblLoaiPhong, "cell 0 10,alignx center");
		
		txtLoaiPhong = new JTextField(phong.getLoaiPhong().getLoaiPhong());
		txtLoaiPhong.setForeground(Color.WHITE);
		txtLoaiPhong.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		listTextField.add(txtLoaiPhong);
		add(txtLoaiPhong, "cell 0 11,grow");
		
		JLabel lblTrangThai = new JLabel("Trạng thái:");
		lblTrangThai.setHorizontalAlignment(SwingConstants.CENTER);
		lblTrangThai.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblTrangThai, "cell 0 12,growx");
		
		txtTrangThai = new JTextField(phong.getTrangThai().getTrangThaiPhong());
		txtTrangThai.setForeground(Color.WHITE);
		txtTrangThai.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		listTextField.add(txtTrangThai);
		add(txtTrangThai, "cell 0 13,grow");
		
		JLabel lblMoTa = new JLabel("Mô tả");
		lblMoTa.setHorizontalAlignment(SwingConstants.CENTER);
		lblMoTa.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblMoTa, "cell 0 14,alignx center");
		
		JTextPane txtMoTa = new JTextPane();
		txtMoTa.setForeground(Color.WHITE);
		txtMoTa.setText(phong.getMoTa());
		txtMoTa.setCaretPosition(0); // Đặt con trỏ văn bản ở đầu
		JScrollPane scrMoTa = new JScrollPane(txtMoTa);
		scrMoTa.getVerticalScrollBar().setValue(0); // Cuộn đến đầu văn bản
		add(scrMoTa, "cell 0 15,grow");
		
		
		
		for (JTextField jTextField : listTextField) {
			jTextField.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			jTextField.setBorder(null);
			jTextField.setHorizontalAlignment(SwingConstants.CENTER);
			jTextField.setEditable(false);
			jTextField.setFocusable(false);
		}
		txtMoTa.setEditable(false);
		txtMoTa.setFocusable(false);
		txtMoTa.setBorder(null);
		txtMoTa.setFont(new Font("Times New Roman", Font.PLAIN, 15));
	}
}
