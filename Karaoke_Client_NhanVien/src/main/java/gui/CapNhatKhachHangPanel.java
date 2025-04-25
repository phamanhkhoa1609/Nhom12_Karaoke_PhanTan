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

import entities.KhachHang;
import entities.TaiKhoan;
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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import interfaces.KhachHangIDAO;
import net.miginfocom.swing.MigLayout;
import utils.VietnameseFormatter;
import utils.guicomponents.MyImageIcon;
import utils.guicomponents.MyPanel;
import utils.guicomponents.notification.MyNotification;
import utils.guicomponents.optionmessage.GlassPanePopup;
import utils.guicomponents.optionmessage.MyMessage;

import javax.swing.SpinnerModel;

public class CapNhatKhachHangPanel extends MyPanel implements ActionListener, KeyListener {

	private JTextField txtMaND;
	private JTextField txtHoTen;
	private KhachHang khachHang;
	private JButton btnCapNhat;
	private KhachHangIDAO khachHangIDAO;
	private JLabel lblGioiTinh;
	private JTextField txtSDT;
	private JRadioButton rbNam;
	private JRadioButton rbNu;
	private JTextField txtHoiVien;
	private long maKH;

	public CapNhatKhachHangPanel(long maKH) {
		this.maKH = maKH;
	}

	@Override
	public void KhoiTaoGiaoDien() {
		khachHangIDAO = clientIDAO.getKhachHangIDAO();
		try {
			khachHang = khachHangIDAO.layTheoMa(maKH);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setLayout(new MigLayout("", "[400.00px,grow]", "[][][30px:40px:40px][][30px:40px:40px][][30px:40px:40px][][30px:40px:40px][][30px:40px:40px][30px:40px:40px][]"));

		JLabel lblTieuDe = new JLabel();
		lblTieuDe.setHorizontalAlignment(SwingConstants.CENTER);
		lblTieuDe.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTieuDe.setForeground(Color.WHITE);
		lblTieuDe.setText("Cập nhật Khách Hàng");
		add(lblTieuDe, "cell 0 0,alignx center");

		List<JTextField> listTextField = new ArrayList<JTextField>();

		JLabel lblMaND = new JLabel("Mã người dùng");
		lblMaND.setHorizontalAlignment(SwingConstants.CENTER);
		lblMaND.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblMaND, "cell 0 1,alignx center");

		txtMaND = new JTextField(khachHang.getMaND() + "");
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
		txtHoTen.setHorizontalAlignment(SwingConstants.CENTER);
		add(txtHoTen, "cell 0 4,grow");
		txtHoTen.addKeyListener(this);

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
		add(rbNam, "flowx,cell 0 8,alignx center,growy");
		rbNu = new JRadioButton("Nữ");
		rbNu.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
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

		btnCapNhat = new JButton("Cập nhật");
		btnCapNhat.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		add(btnCapNhat, "cell 0 11,grow");

		btnCapNhat.addActionListener(this);

		lblKiemTraNhapLieu = new JLabel("");
		lblKiemTraNhapLieu.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		add(lblKiemTraNhapLieu, "cell 0 12");

		Pattern pattern = Pattern.compile(HOTEN_BIEUTHUC);
		Matcher matcher = pattern.matcher(khachHang.getHoTen());
		hoTenHopLe = matcher.matches();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	private boolean hoTenHopLe;
	private JLabel lblKiemTraNhapLieu;
	private static final String HOTEN_BIEUTHUC = "^[\\p{L}']+(\\s+[\\p{L}']{1,255})+$";

	@Override
	public void keyReleased(KeyEvent e) {
		lblKiemTraNhapLieu.setText("");
		Object o = e.getSource();
		if (o.equals(txtHoTen)) {
			Pattern pattern = Pattern.compile(HOTEN_BIEUTHUC);
			Matcher matcher = pattern.matcher(txtHoTen.getText());
			if (matcher.matches()) {
				hoTenHopLe = true;
				lblKiemTraNhapLieu.setText("Họ tên hợp lệ");
				lblKiemTraNhapLieu.setForeground(Color.green);
			} else {
				hoTenHopLe = false;
				lblKiemTraNhapLieu.setForeground(Color.red);
				lblKiemTraNhapLieu.setText("Họ tên viết hoa ký tự đầu và cách ra mỗi từ");
			}
		}
	}

	private boolean thanhCong = false;

	public boolean getThanhCong() {
		return thanhCong;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnCapNhat) && kiemTraRangBuoc()) {
			MyMessage myMessage = new MyMessage("Bạn có muốn cập nhật không?",
					"Hãy xem kỹ thông tin trước khi cập nhật!", true);
			GlassPanePopup.showPopup(myMessage);
			myMessage.eventOK(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {
					GlassPanePopup.closePopupLast();
					khachHang.setHoTen(txtHoTen.getText().trim());
					GioiTinh gioiTinh = null;
					if (rbNam.isSelected()) {
						gioiTinh = GioiTinh.valueOf(rbNam.getText().toUpperCase());
					} else if (rbNu.isSelected()) {
						gioiTinh = GioiTinh.valueOf(rbNu.getText().toUpperCase());
					}
					khachHang.setGioiTinh(gioiTinh);
					try {
						khachHangIDAO.suaMot(khachHang);
						thanhCong = true;
						manHinhChua.dispose();
					} catch (RemoteException e) {
						MyNotification panel1 = new MyNotification(chinhDeskstop, MyNotification.Type.WARNING,
								MyNotification.Location.CENTER, "Cập nhật thất bại, hãy kiểm tra lại kết nối!");
						panel1.showNotification();
					}

				}
			});
		}
	}

	private boolean kiemTraRangBuoc() {
		MyNotification panel = null;
		if (!hoTenHopLe) {
			panel = new MyNotification(chinhDeskstop, MyNotification.Type.WARNING, MyNotification.Location.CENTER,
					"Họ tên sai cú pháp");
			panel.showNotification();
			return false;
		}
		return true;
	}

}
