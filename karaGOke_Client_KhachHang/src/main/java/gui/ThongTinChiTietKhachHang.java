package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.TitledBorder;

import org.jdesktop.swingx.combobox.ListComboBoxModel;

import entities.DichVu;
import entities.KhachHang;
import entities.enums.GioiTinh;
import entities.enums.HoiVien;
import entities.enums.LoaiDichVu;
import entities.enums.TrangThaiDichVu;

import javax.swing.border.EtchedBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;
import utils.guicomponents.MyImageIcon;
import utils.guicomponents.MyPanel;
import utils.guicomponents.optionmessage.GlassPanePopup;
import utils.guicomponents.optionmessage.MyMessage;

import javax.swing.SpinnerModel;

public class ThongTinChiTietKhachHang extends MyPanel {

	private JTextField txtMaND;
	private JTextField txtHoTen;
	private JLabel lblGioiTinh;
	private JTextField txtSDT;
	private JRadioButton rbNam;
	private JRadioButton rbNu;
	private JTextField txtHoiVien;
	private KhachHang khachHang;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	
	public ThongTinChiTietKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	};
	@Override
	public void KhoiTaoGiaoDien() {
		
		setLayout(new MigLayout("", "[400.00px,grow]", "[][][30px:40px:40px][][30px:40px:40px][][30px:40px:40px][][30px:40px:40px][][30px:40px:40px]"));

		JLabel lblTieuDe = new JLabel();
		lblTieuDe.setHorizontalAlignment(SwingConstants.CENTER);
		lblTieuDe.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTieuDe.setForeground(Color.WHITE);
		lblTieuDe.setText("Thông tin chi tiết Khách Hàng");
		add(lblTieuDe, "cell 0 0,alignx center");

		List<JTextField> listTextField = new ArrayList<JTextField>();

		JLabel lblMaND = new JLabel("Mã người dùng");
		lblMaND.setHorizontalAlignment(SwingConstants.CENTER);
		lblMaND.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblMaND, "cell 0 1,alignx center");

		txtMaND = new JTextField(khachHang.getMaND() + "");
		txtMaND.setForeground(Color.WHITE);
		txtMaND.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		listTextField.add(txtMaND);
		add(txtMaND, "cell 0 2,grow");

		JLabel lblHoTen = new JLabel("Họ tên");
		lblHoTen.setHorizontalAlignment(SwingConstants.CENTER);
		lblHoTen.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblHoTen, "cell 0 3,alignx center");

		txtHoTen = new JTextField(khachHang.getHoTen());
		txtHoTen.setForeground(Color.WHITE);
		txtHoTen.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		listTextField.add(txtHoTen);
		add(txtHoTen, "cell 0 4,grow");

		JLabel lblSDT = new JLabel("Số điện thoại");
		lblSDT.setHorizontalAlignment(SwingConstants.CENTER);
		lblSDT.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblSDT, "cell 0 5,alignx center");
		
		txtSDT = new JTextField(khachHang.getSdt());
		txtSDT.setForeground(Color.WHITE);
		txtSDT.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		listTextField.add(txtSDT);
		add(txtSDT, "cell 0 6,grow");

		lblGioiTinh = new JLabel("Giới tính");
		lblGioiTinh.setHorizontalAlignment(SwingConstants.CENTER);
		lblGioiTinh.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblGioiTinh, "cell 0 7,alignx center");
		
		rbNam = new JRadioButton("Nam");
		rbNam.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		rbNam.setForeground(Color.white);
		rbNam.setEnabled(false);
		add(rbNam, "flowx,cell 0 8,alignx center,growy");
		rbNu = new JRadioButton("Nữ");
		rbNu.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		rbNu.setEnabled(false);
		rbNu.setForeground(getBackground());
		add(rbNu, "cell 0 8,alignx center,growy");
		
		ButtonGroup bgGioiTinh = new ButtonGroup();
		switch (khachHang.getGioiTinh()) {
		case NAM:
			rbNam.setSelected(true);
			break;
		case NỮ:
			rbNu.setSelected(true);
			break;
		}
		bgGioiTinh.add(rbNam);
		bgGioiTinh.add(rbNu);
		JLabel lblHoiVien = new JLabel("Hội viên");
		lblHoiVien.setHorizontalAlignment(SwingConstants.CENTER);
		lblHoiVien.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblHoiVien, "cell 0 9,alignx center");

		txtHoiVien = new JTextField(khachHang.getHoiVien().getHoiVien());
		txtHoiVien.setForeground(Color.WHITE);
		txtHoiVien.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		listTextField.add(txtHoiVien);
		add(txtHoiVien, "cell 0 10,grow");

		for (JTextField jTextField : listTextField) {
			jTextField.setEditable(false);
			jTextField.setFocusable(false);
			jTextField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			jTextField.setBorder(null);
			jTextField.setHorizontalAlignment(SwingConstants.CENTER);
		}
	}
}
