package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.rmi.RemoteException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import entities.KhachHang;
import entities.TaiKhoan;
import entities.enums.GioiTinh;
import gui.subgui.FormManager;
import interfaces.KhachHangIDAO;
import jakarta.persistence.EntityManager;
import net.miginfocom.swing.MigLayout;
import raven.swing.blur.BlurBackground;
import raven.swing.blur.style.StyleOverlay;
import utils.ClientIDAO;
import utils.guicomponents.FlatRobotoInit;
import utils.guicomponents.MyImageIcon;
import utils.guicomponents.MyPanel;
import utils.guicomponents.notification.MyNotification;

import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JCheckBox;

public class DangKyTaiKhoanKhachHang extends MyPanel implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;
	private JDesktopPane desktopPane;
	private JTextField txtSDT;
	private JTextField txtHoTen;
	private JPasswordField txtMatKhau;
	private JPasswordField txtNhapLaiMatKhau;
	private JLabel lblKiemTraNhapLieu;
	private KhachHangIDAO khachHangIDAO;
	private JButton btnDangKy;
	private ButtonGroup bgGioiTinh;
	private JCheckBox ckbHienMatKhau;
	private JRadioButton rbNam;
	private JRadioButton rbNu;

	public DangKyTaiKhoanKhachHang() {

	}

	@Override
	public void KhoiTaoGiaoDien() {
		khachHangIDAO = clientIDAO.getKhachHangIDAO();
		setLayout(new MigLayout("", "[30px:n][400.00px,grow][30px:n]",
				"[30px:n][30px:n][][:30px:40px,grow][][:30px:40px,grow][][:30px:40px,grow][][:30px:40px,grow][][:30px:40px,grow][][:30px:40px,grow][]"));

		JLabel lblTieuDe = new JLabel();
		lblTieuDe.setHorizontalAlignment(SwingConstants.CENTER);
		lblTieuDe.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTieuDe.setForeground(Color.WHITE);
		lblTieuDe.setText("Đăng ký tài khoản Khách Hàng");
		add(lblTieuDe, "cell 1 1,alignx center");

		JLabel lblHoTen = new JLabel("Họ tên");
		lblHoTen.setHorizontalAlignment(SwingConstants.CENTER);
		lblHoTen.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblHoTen, "cell 1 2,alignx left");

		txtHoTen = new JTextField();
		txtHoTen.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(txtHoTen, "cell 1 3,grow");
		txtHoTen.addKeyListener(this);

		JLabel lblSDT = new JLabel("Số điện thoại");
		lblSDT.setHorizontalAlignment(SwingConstants.CENTER);
		lblSDT.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblSDT, "cell 1 4,alignx left");

		txtSDT = new JTextField();
		txtSDT.setFont(new Font("Times New Roman", Font.BOLD, 14));
		txtSDT.addKeyListener(this);
		add(txtSDT, "cell 1 5,grow");

		JLabel lblGioiTinh = new JLabel("Giới tính");
		lblGioiTinh.setHorizontalAlignment(SwingConstants.CENTER);
		lblGioiTinh.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblGioiTinh, "cell 1 6,alignx left");

		rbNam = new JRadioButton("Nam");
		rbNam.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		rbNam.setHorizontalAlignment(SwingConstants.CENTER);
		add(rbNam, "flowx,cell 1 7,alignx left,growy");
		rbNu = new JRadioButton("Nữ");
		rbNu.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		rbNu.setHorizontalAlignment(SwingConstants.CENTER);
		add(rbNu, "cell 1 7,alignx center,growy");

		bgGioiTinh = new ButtonGroup();
		bgGioiTinh.add(rbNam);
		bgGioiTinh.add(rbNu);

		JLabel lblMatKhau = new JLabel("Mật khẩu");
		lblMatKhau.setHorizontalAlignment(SwingConstants.CENTER);
		lblMatKhau.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblMatKhau, "cell 1 8,alignx left");

		txtMatKhau = new JPasswordField();
		txtMatKhau.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(txtMatKhau, "cell 1 9,grow");
		txtMatKhau.setEchoChar('*');
		txtMatKhau.addKeyListener(this);

		JLabel lblNhapLaiMatKhau = new JLabel("Nhập lại mật khẩu");
		lblNhapLaiMatKhau.setHorizontalAlignment(SwingConstants.CENTER);
		lblNhapLaiMatKhau.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblNhapLaiMatKhau, "cell 1 10,alignx left");

		txtNhapLaiMatKhau = new JPasswordField();
		txtNhapLaiMatKhau.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(txtNhapLaiMatKhau, "cell 1 11,grow");
		txtNhapLaiMatKhau.setEchoChar('*');
		txtNhapLaiMatKhau.addKeyListener(this);

		ckbHienMatKhau = new JCheckBox("Hiện mật khẩu");
		ckbHienMatKhau.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		ckbHienMatKhau.addActionListener(this);
		add(ckbHienMatKhau, "cell 1 12,alignx right");

		btnDangKy = new JButton("Đăng ký");
		btnDangKy.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		btnDangKy.setForeground(Color.WHITE);
		btnDangKy.setBackground(Color.LIGHT_GRAY);
		btnDangKy.addActionListener(this);
		add(btnDangKy, "cell 1 13,grow");

		lblKiemTraNhapLieu = new JLabel("");
		lblKiemTraNhapLieu.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		add(lblKiemTraNhapLieu, "cell 1 14");
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	private boolean hoTenHopLe = false;
	private boolean sdtHopLe = false;
	private boolean matKhauHopLe = false;
	private boolean nhapLaiMatKhauHopLe = false;

	private static final String HOTEN_BIEUTHUC = "^[\\p{L}']+(\\s+[\\p{L}']{1,255})+$";
	private static final String SDT_BIEUTHUC = "^(0|84)(2(0[1-9]|1[0-9]|2[0-9]|3[0-9]|4[0-9]|5[1-9]|6[0-9]|7[0-9]|8[0-9]|9[0-9])|3[2-9]|5[5689]|7[06-9]|8[0-689]|9[0-4679])([0-9]{7})$";

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		lblKiemTraNhapLieu.setText("");
		Object o = e.getSource();
		if (o.equals(txtHoTen)) {
			Pattern pattern = Pattern.compile(HOTEN_BIEUTHUC);
			Matcher matcher = pattern.matcher(txtHoTen.getText());
			if (matcher.matches()) {
				hoTenHopLe = true;
				lblKiemTraNhapLieu.setText("Họ tên hợp lệ");
				lblKiemTraNhapLieu.setForeground(Color.green);
			} else {
				hoTenHopLe = false;
				lblKiemTraNhapLieu.setForeground(Color.red);
				lblKiemTraNhapLieu.setText("Họ tên viết hoa ký tự đầu và cách ra mỗi từ");
			}
		} else if (o.equals(txtSDT)) {
			Pattern pattern = Pattern.compile(SDT_BIEUTHUC);
			Matcher matcher = pattern.matcher(txtSDT.getText());
			if (matcher.matches()) {
				try {
					if (khachHangIDAO.tonTaiSDT(txtSDT.getText())) {
						sdtHopLe = false;
						lblKiemTraNhapLieu.setForeground(Color.red);
						lblKiemTraNhapLieu.setText("Số điện thoại đã được sử dụng, chọn sdt khác");
					} else {
						sdtHopLe = true;
						lblKiemTraNhapLieu.setText("Ồ số điện thoại hợp lệ rồi này");
						lblKiemTraNhapLieu.setForeground(Color.green);
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				sdtHopLe = false;
				lblKiemTraNhapLieu.setForeground(Color.red);
				lblKiemTraNhapLieu.setText("Số điện thoại này không hợp lệ. Bạn trêu tôi à?");
			}
		} else if (o.equals(txtMatKhau)) {
			if (txtMatKhau.getText().trim().equals("")) {
				matKhauHopLe = false;
				lblKiemTraNhapLieu.setForeground(Color.red);
				lblKiemTraNhapLieu.setText("Mật khẩu không được để trống");
			} else {
				matKhauHopLe = true;
				lblKiemTraNhapLieu.setForeground(Color.green);
				lblKiemTraNhapLieu.setText("Mật khẩu hợp lệ");
			}
		} else if (o.equals(txtNhapLaiMatKhau)) {
			if (txtNhapLaiMatKhau.getText().equals(txtMatKhau.getText())) {
				nhapLaiMatKhauHopLe = true;
				lblKiemTraNhapLieu.setForeground(Color.green);
				lblKiemTraNhapLieu.setText("Mật khẩu trùng khớp");
			} else {
				nhapLaiMatKhauHopLe = false;
				lblKiemTraNhapLieu.setForeground(Color.red);
				lblKiemTraNhapLieu.setText("Mật khẩu không trùng khớp");
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnDangKy)) {
			if (bgGioiTinh.getSelection() == null) {
				MyNotification panel = new MyNotification(chinhFrame, MyNotification.Type.WARNING,
						MyNotification.Location.CENTER, "Vui lòng chọn giới tính!");
				panel.showNotification();
			} else if (kiemTraRangBuoc()) {
				KhachHang khachHang = new KhachHang();
				khachHang.setHoTen(txtHoTen.getText());
				khachHang.setSdt(txtSDT.getText());
				String gioiTinh = "";
				if (rbNam.isSelected())
					gioiTinh = "NAM";
				else if (rbNu.isSelected())
					gioiTinh = "NỮ";
				khachHang.setGioiTinh(GioiTinh.valueOf(gioiTinh));
				khachHang.setTaiKhoan(new TaiKhoan(khachHang.getSdt(), txtNhapLaiMatKhau.getText()));
				try {
					khachHangIDAO.themMot(khachHang);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				tienManHinh.setEnabled(true);
				MyNotification panel = new MyNotification(chinhFrame, MyNotification.Type.SUCCESS,
						MyNotification.Location.CENTER, "Tạo tài khoản thành công");
				panel.showNotification();

				manHinhChua.dispose();
			}
		}
		if (o.equals(ckbHienMatKhau)) {
			System.out.println("a");
			if (ckbHienMatKhau.isSelected()) {
				txtMatKhau.setEchoChar((char) 0); // set echo character to be empty
				txtNhapLaiMatKhau.setEchoChar((char) 0); // set echo character to be empty
			} else {
				txtMatKhau.setEchoChar('*'); // set echo character to be empty
				txtNhapLaiMatKhau.setEchoChar('*'); // set echo character to be empty

			}
		}

	}

	private boolean kiemTraRangBuoc() {
		MyNotification panel = null;
		if (!hoTenHopLe) {
			panel = new MyNotification(chinhFrame, MyNotification.Type.WARNING, MyNotification.Location.CENTER,
					"Họ tên sai cú pháp");
			panel.showNotification();
			return false;
		}
		if (!sdtHopLe) {
			panel = new MyNotification(chinhFrame, MyNotification.Type.WARNING, MyNotification.Location.CENTER,
					"Số điện thoại đã sai cú pháp hoặc đã tồn tại");
			panel.showNotification();
			return false;
		}
		if (!matKhauHopLe) {
			panel = new MyNotification(chinhFrame, MyNotification.Type.WARNING, MyNotification.Location.CENTER,
					"Mật khẩu không được để trống");
			panel.showNotification();
			return false;
		}
		if (!nhapLaiMatKhauHopLe) {
			panel = new MyNotification(chinhFrame, MyNotification.Type.WARNING, MyNotification.Location.CENTER,
					"Mật khẩu không trùng khớp");
			panel.showNotification();
			return false;
		}
		return true;
	}

}
