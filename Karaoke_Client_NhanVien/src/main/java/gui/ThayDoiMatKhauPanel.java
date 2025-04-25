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
import entities.NguoiDung;
import entities.TaiKhoan;
import entities.enums.LoaiNguoiDung;
import gui.subgui.FormManager;
import interfaces.KhachHangIDAO;
import interfaces.TaiKhoanIDAO;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JCheckBox;

public class ThayDoiMatKhauPanel extends MyPanel implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;
	private JDesktopPane desktopPane;
	private JPasswordField txtNhapLaiMatKhauMoi;
	private JLabel lblKiemTraNhapLieu;
	private JButton btnThayDoiMK;
	private JPasswordField txtMatKhauCu;
	private JPasswordField txtMatKhauMoi;
	private KhachHangIDAO khachHangIDAO;
	private JCheckBox ckbHienMatKhau;

	public ThayDoiMatKhauPanel() {

	}

	@Override
	public void KhoiTaoGiaoDien() {
		taiKhoanIDAO = clientIDAO.getTaiKhoanIDAO();

		setLayout(new MigLayout("", "[30px:n][400.00px,grow][30px:n]",
				"[30px:n][][30px:40px:40px][][30px:40px:40px][][30px:40px:40px][30px:40px:40px][30px:40px:40px][30px:40px:40px]"));

		JLabel lblTieuDe = new JLabel();
		lblTieuDe.setHorizontalAlignment(SwingConstants.CENTER);
		lblTieuDe.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTieuDe.setForeground(Color.WHITE);
		lblTieuDe.setText("Thay Đổi Mật Khẩu");
		add(lblTieuDe, "cell 1 0,alignx center");

		JLabel lblHoTen = new JLabel("Mật khẩu củ");
		lblHoTen.setHorizontalAlignment(SwingConstants.CENTER);
		lblHoTen.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblHoTen, "cell 1 1,alignx left");

		txtMatKhauCu = new JPasswordField();
		txtMatKhauCu.setForeground(Color.WHITE);
		txtMatKhauCu.setFont(new Font("Times New Roman", Font.BOLD, 14));
		txtMatKhauCu.setEchoChar('*');
		add(txtMatKhauCu, "cell 1 2,grow");
		txtMatKhauCu.addKeyListener(this);

		JLabel lblMatKhauMoi = new JLabel("Mật khẩu mới");
		lblMatKhauMoi.setHorizontalAlignment(SwingConstants.CENTER);
		lblMatKhauMoi.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblMatKhauMoi, "cell 1 3,alignx left");

		txtMatKhauMoi = new JPasswordField();
		txtMatKhauMoi.setForeground(Color.WHITE);
		txtMatKhauMoi.setFont(new Font("Times New Roman", Font.BOLD, 14));
		txtMatKhauMoi.addKeyListener(this);
		txtMatKhauMoi.setEchoChar('*');
		add(txtMatKhauMoi, "cell 1 4,grow");
		JLabel lblNhapLaiMatKhau = new JLabel("Nhập lại mật khẩu mới");
		lblNhapLaiMatKhau.setHorizontalAlignment(SwingConstants.CENTER);
		lblNhapLaiMatKhau.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblNhapLaiMatKhau, "cell 1 5,alignx left");

		txtNhapLaiMatKhauMoi = new JPasswordField();
		txtNhapLaiMatKhauMoi.setForeground(Color.WHITE);
		txtNhapLaiMatKhauMoi.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(txtNhapLaiMatKhauMoi, "cell 1 6,grow");
		txtNhapLaiMatKhauMoi.setEchoChar('*');
		txtNhapLaiMatKhauMoi.addKeyListener(this);

		ckbHienMatKhau = new JCheckBox("Hiện mật khẩu");
		ckbHienMatKhau.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		ckbHienMatKhau.addActionListener(this);
		add(ckbHienMatKhau, "cell 1 7,alignx right");

		btnThayDoiMK = new JButton("Đổi Mật Khẩu");
		btnThayDoiMK.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		add(btnThayDoiMK, "cell 1 8,grow");
		btnThayDoiMK.addActionListener(this);

		lblKiemTraNhapLieu = new JLabel("");
		lblKiemTraNhapLieu.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblKiemTraNhapLieu.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblKiemTraNhapLieu, "cell 1 9");

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	private boolean matKhauCuHopLe = false;
	private boolean matKhauMoiHopLe = false;
	private boolean nhapLaiMatKhauMoiHopLe = false;
	private TaiKhoanIDAO taiKhoanIDAO;

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		lblKiemTraNhapLieu.setText("");
		Object o = e.getSource();
		if (o.equals(txtMatKhauCu)) {
			if (txtMatKhauCu.getText().trim().equals("")) {
				matKhauCuHopLe = false;
				lblKiemTraNhapLieu.setForeground(Color.red);
				lblKiemTraNhapLieu.setText("Mật khẩu cũ không được để trống");
			} else {
				matKhauCuHopLe = true;
				lblKiemTraNhapLieu.setForeground(Color.green);
				lblKiemTraNhapLieu.setText("Mật khẩu cũ hợp lệ");
			}
		} else if (o.equals(txtMatKhauMoi)) {
			if (txtMatKhauMoi.getText().trim().equals("")) {
				matKhauMoiHopLe = false;
				lblKiemTraNhapLieu.setForeground(Color.red);
				lblKiemTraNhapLieu.setText("Mật khẩu mới không được để trống");
			} else {
				matKhauMoiHopLe = true;
				lblKiemTraNhapLieu.setForeground(Color.green);
				lblKiemTraNhapLieu.setText("Mật khẩu hợp lệ");
			}
		} else if (o.equals(txtNhapLaiMatKhauMoi)) {
			if (txtNhapLaiMatKhauMoi.getText().equals(txtMatKhauMoi.getText())) {
				nhapLaiMatKhauMoiHopLe = true;
				lblKiemTraNhapLieu.setForeground(Color.green);
				lblKiemTraNhapLieu.setText("Mật khẩu mới trùng khớp");
			} else {
				nhapLaiMatKhauMoiHopLe = false;
				lblKiemTraNhapLieu.setForeground(Color.red);
				lblKiemTraNhapLieu.setText("Mật khẩu không trùng khớp");
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnThayDoiMK) && kiemTraRangBuoc()) {
			String matKhauCu = txtMatKhauCu.getText();
			String matKhauMoi = txtMatKhauMoi.getText();
			try {
				taiKhoanIDAO.suaMatKhau(nguoiDung, matKhauMoi);

			} catch (RemoteException ex) {
				ex.printStackTrace();
			}
			MyNotification panel = new MyNotification(chinhDeskstop, MyNotification.Type.SUCCESS,
					MyNotification.Location.CENTER, "Thay đổi mật khẩu thành công, hãy thoát ra để đăng nhập lại");
			panel.showNotification();
			manHinhChua.dispose();
		}
		if (o.equals(ckbHienMatKhau)) {
			if (ckbHienMatKhau.isSelected()) {
				txtMatKhauCu.setEchoChar((char) 0);
				txtMatKhauMoi.setEchoChar((char) 0);
				txtNhapLaiMatKhauMoi.setEchoChar((char) 0);
			} else {
				txtMatKhauCu.setEchoChar('*');
				txtMatKhauMoi.setEchoChar('*');
				txtNhapLaiMatKhauMoi.setEchoChar('*');

			}
		}
	}

	private boolean kiemTraRangBuoc() {
		MyNotification panel = null;
		if (!txtMatKhauCu.getText().equals(nguoiDung.getTaiKhoan().getMatKhau())) {
			panel = new MyNotification(chinhDeskstop, MyNotification.Type.WARNING, MyNotification.Location.CENTER,
					"Mật khẩu cũ sai");
			panel.showNotification();
			return false;
		}
		if (!matKhauCuHopLe) {
			panel = new MyNotification(chinhDeskstop, MyNotification.Type.WARNING, MyNotification.Location.CENTER,
					"Mật khẩu sai cú pháp hoặc không tồn tại");
			panel.showNotification();
			return false;
		}
		if (!matKhauMoiHopLe) {
			panel = new MyNotification(chinhDeskstop, MyNotification.Type.WARNING, MyNotification.Location.CENTER,
					"Mật khẩu mới sai cú pháp hoặc trùng mật khẩu củ");
			panel.showNotification();
			return false;
		}
		if (!nhapLaiMatKhauMoiHopLe) {
			panel = new MyNotification(chinhDeskstop, MyNotification.Type.WARNING, MyNotification.Location.CENTER,
					"Mật khẩu sai cú pháp hoặc không trùng với mật khẩu mới");
			panel.showNotification();
			return false;
		}

		return true;
	}

}
