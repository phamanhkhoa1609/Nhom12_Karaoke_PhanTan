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

public class ThongTinChiTietDichVuPanel extends JPanel {

	private JPanel contentPane;
	private JTextField txtMaDV;
	private JTextField txtTenDV;
	private JTextField txtGiaDV;
	private JTextField txtLoaiDV;
	private DichVu dichVu;
	private JTextField txtTrangThai;
	private JTextField txtSoLuong;
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	
	public ThongTinChiTietDichVuPanel(DichVu dichVu) {
		this.dichVu = dichVu;
        setLayout(new MigLayout("", "[407.00px,grow]", "[][42px][200px][][30px,grow][][30px,grow][][30px,grow][][][][30px,grow][][30px,grow][][200px,grow]"));

		
		JLabel lblHinhPhong = new JLabel();
		lblHinhPhong.setIcon(dichVu.getHinh(300,200,0));
		Border blackline = BorderFactory.createLineBorder(Color.black);
		
		JLabel lblTieuDe = new JLabel();
		lblTieuDe.setHorizontalAlignment(SwingConstants.CENTER);
		lblTieuDe.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTieuDe.setForeground(Color.WHITE);
		lblTieuDe.setText("Thông tin chi tiết Dịch vụ");
		add(lblTieuDe, "cell 0 0,alignx center");
		lblHinhPhong.setBorder(blackline);
		add(lblHinhPhong, "cell 0 2,alignx center,aligny center");
		
		
		List<JTextField> listTextField = new ArrayList<JTextField>();
		
		JLabel lblMaDV = new JLabel("Mã dịch vụ");
		lblMaDV.setHorizontalAlignment(SwingConstants.CENTER);
		lblMaDV.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblMaDV, "cell 0 3,alignx center");
		
		txtMaDV = new JTextField(dichVu.getMaDV()+"");
		listTextField.add(txtMaDV);
		add(txtMaDV, "cell 0 4,growx");
		
		JLabel lblTenDV = new JLabel("Tên dịch vụ");
		lblTenDV.setHorizontalAlignment(SwingConstants.CENTER);
		lblTenDV.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblTenDV, "cell 0 5,alignx center");
		
		txtTenDV = new JTextField(dichVu.getTenDV());
		listTextField.add(txtTenDV);
		add(txtTenDV, "cell 0 6,growx");
		
		JLabel lblGiaDV = new JLabel("Giá dịch vụ");
		lblGiaDV.setHorizontalAlignment(SwingConstants.CENTER);
		lblGiaDV.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblGiaDV, "cell 0 7,alignx center");
		
		txtGiaDV = new JTextField( VietnameseFormatter.dinhDangTien(dichVu.getGiaDV()));
		listTextField.add(txtGiaDV);
		add(txtGiaDV, "cell 0 8,growx");
		
		JLabel lblSoLuong = new JLabel("Số lượng");
		lblSoLuong.setHorizontalAlignment(SwingConstants.CENTER);
		lblSoLuong.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblSoLuong, "cell 0 9,alignx center");
		
		txtSoLuong = new JTextField(dichVu.getSoLuong()+"");
		listTextField.add(txtSoLuong);
		add(txtSoLuong, "cell 0 10,growx");
		
		JLabel lblLoaiDV = new JLabel("Loại dịch vụ");
		lblLoaiDV.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoaiDV.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblLoaiDV, "cell 0 11,alignx center");
		
		txtLoaiDV = new JTextField(dichVu.getLoaiDV().getLoaiDichVu());
		listTextField.add(txtLoaiDV);
		add(txtLoaiDV, "cell 0 12,growx");
		
		JLabel lblTrangThai = new JLabel("Trạng thái");
		lblTrangThai.setHorizontalAlignment(SwingConstants.CENTER);
		lblTrangThai.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblTrangThai, "cell 0 13,growx");
		
		txtTrangThai = new JTextField(dichVu.getTrangThai().getTrangThaiDichVu());
		listTextField.add(txtTrangThai);
		add(txtTrangThai, "cell 0 14,growx");
		
		JLabel lblMoTa = new JLabel("Mô tả");
		lblMoTa.setHorizontalAlignment(SwingConstants.CENTER);
		lblMoTa.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblMoTa, "cell 0 15,alignx center");
		
		JTextPane txtMoTa = new JTextPane();
		txtMoTa.setText(dichVu.getMoTa());
		txtMoTa.setCaretPosition(0); // Đặt con trỏ văn bản ở đầu
		JScrollPane scrMoTa = new JScrollPane(txtMoTa);
		scrMoTa.getVerticalScrollBar().setValue(0); // Cuộn đến đầu văn bản
		add(scrMoTa, "cell 0 16,grow");
		
		
		
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
		txtMoTa.setFont(new Font("Times New Roman", Font.PLAIN, 12));
	}
}
