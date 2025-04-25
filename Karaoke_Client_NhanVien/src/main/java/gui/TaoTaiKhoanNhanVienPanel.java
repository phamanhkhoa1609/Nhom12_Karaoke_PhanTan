package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jdesktop.swingx.combobox.ListComboBoxModel;

import interfaces.NhanVienIDAO;
import entities.NhanVien;
import entities.TaiKhoan;
import entities.enums.ChucVu;
import entities.enums.GioiTinh;
import entities.enums.TrangThaiNhanVien;
import gui.subgui.FormManager;
import gui.subgui.MainForm;
import net.miginfocom.swing.MigLayout;
import utils.guicomponents.FlatRobotoInit;
import utils.guicomponents.MyImageIcon;
import utils.guicomponents.MyPanel;
import utils.guicomponents.datechooser.EventDateChooser;
import utils.guicomponents.datechooser.MyDateChooser;
import utils.guicomponents.datechooser.SelectedAction;
import utils.guicomponents.datechooser.SelectedDate;
import utils.guicomponents.notification.MyNotification;

import javax.swing.JLabel;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

public class TaoTaiKhoanNhanVienPanel extends MyPanel implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;
	private JDesktopPane desktopPane;
	private JTextField txtHoTen;
	private JLabel lblKiemTraNhapLieu;
	private NhanVienIDAO nhanVienIDAO;
	private JButton btnTaoTaiKhoan;
	private ButtonGroup bgGioiTinh;
	private JCheckBox ckbHienMatKhau;
	private MyDateChooser dateChooser;
	
	public TaoTaiKhoanNhanVienPanel() {}
	@Override
	public void KhoiTaoGiaoDien() {
		// TODO Auto-generated method stub
		nhanVienIDAO = clientIDAO.getNhanVienIDAO();

		setLayout(new MigLayout("", "[30px:n][300.00px,grow][300.00px,grow][30px:n]", "[30px:n][][30px:40px:40px][][30px:40px:40px][][30px:40px:40px][][30px:40px:40px][][30px:40px:40px][][30px:40px:40px][][30px:40px:40px][0px:365px:365px][30px:40px:40px][:30px:40px,grow][][]"));

		JLabel lblTieuDe = new JLabel();
		lblTieuDe.setHorizontalAlignment(SwingConstants.CENTER);
		lblTieuDe.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTieuDe.setForeground(Color.WHITE);
		lblTieuDe.setText("Tạo tài khoản Nhân viên");
		add(lblTieuDe, "cell 1 0 2 1,alignx center");

		lblMaND = new JLabel("Mã người dùng");
		lblMaND.setHorizontalAlignment(SwingConstants.CENTER);
		lblMaND.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblMaND, "cell 1 1");

		lblSDT = new JLabel("Số điện thoại");
		lblSDT.setHorizontalAlignment(SwingConstants.CENTER);
		lblSDT.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblSDT, "cell 2 1");

		txtMaND = new JTextField();
		txtMaND.setForeground(Color.WHITE);
		txtMaND.setText("Mã người dùng sẽ được tạo tự động");
		txtMaND.setEditable(false);
		txtMaND.setFocusable(false);
		txtMaND.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		add(txtMaND, "cell 1 2,grow");

		txtSDT = new JTextField();
		txtSDT.setForeground(Color.WHITE);
		txtSDT.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		add(txtSDT, "cell 2 2,grow");
		txtSDT.addKeyListener(this);

		JLabel lblHoTen = new JLabel("Họ tên");
		lblHoTen.setHorizontalAlignment(SwingConstants.CENTER);
		lblHoTen.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblHoTen, "cell 1 3,alignx left");

		lblMatKhau = new JLabel("Mật khẩu");
		lblMatKhau.setHorizontalAlignment(SwingConstants.CENTER);
		lblMatKhau.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblMatKhau, "cell 2 3");

		txtHoTen = new JTextField();
		txtHoTen.setForeground(Color.WHITE);
		txtHoTen.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		add(txtHoTen, "cell 1 4,grow");
		txtHoTen.addKeyListener(this);

		txtMatKhau = new JPasswordField();
		txtMatKhau.setForeground(Color.WHITE);
		txtMatKhau.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtMatKhau.setEchoChar('*');
		add(txtMatKhau, "cell 2 4,grow");
		txtMatKhau.addKeyListener(this);

		lblCCCD = new JLabel("Căn cước công dân");
		lblCCCD.setHorizontalAlignment(SwingConstants.CENTER);
		lblCCCD.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblCCCD, "cell 1 5");

		lblNhapLaiMatKhau = new JLabel("Nhập lại mật khẩu");
		lblNhapLaiMatKhau.setHorizontalAlignment(SwingConstants.CENTER);
		lblNhapLaiMatKhau.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblNhapLaiMatKhau, "cell 2 5");

		txtCCCC = new JTextField();
		txtCCCC.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(txtCCCC, "cell 1 6,grow");
		txtCCCC.addKeyListener(this);

		txtNhapLaiMatKhau = new JPasswordField();
		txtNhapLaiMatKhau.setFont(new Font("Times New Roman", Font.BOLD, 14));
		txtNhapLaiMatKhau.setEchoChar('*');
		add(txtNhapLaiMatKhau, "cell 2 6,grow");
		txtNhapLaiMatKhau.addKeyListener(this);

		lblChucVu = new JLabel("Chức vụ");
		lblChucVu.setHorizontalAlignment(SwingConstants.CENTER);
		lblChucVu.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblChucVu, "cell 1 7");

		lblNgaySinh = new JLabel("Ngày sinh");
		lblNgaySinh.setHorizontalAlignment(SwingConstants.CENTER);
		lblNgaySinh.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblNgaySinh, "cell 2 7");
		List<String> dsChucVuString = new ArrayList<String>();
		for (ChucVu chucVu : ChucVu.values()) {
			dsChucVuString.add(chucVu.getChucVu());
		}
		dsChucVuString.remove(0);
		cboChucVu = new JComboBox(new ListComboBoxModel<String>(dsChucVuString));
		cboChucVu.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		cboChucVu.setForeground(Color.WHITE);
		add(cboChucVu, "cell 1 8,grow");

		txtNgaySinh = new JTextField();
		txtNgaySinh.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtNgaySinh.setForeground(Color.WHITE);
		txtNgaySinh.setHorizontalAlignment(SwingConstants.RIGHT);
		add(txtNgaySinh, "cell 2 8,grow");
		dateChooser = new MyDateChooser();
		dateChooser.setTextRefernce(txtNgaySinh);
		dateChooser.addEventDateChooser(new EventDateChooser() {

			@Override
			public void dateSelected(SelectedAction action, SelectedDate date) {
				// TODO Auto-generated method stub
				chooseDateNgaySinh(date);
			}
		});

		lblGioiTinh = new JLabel("Giới tính");
		lblGioiTinh.setHorizontalAlignment(SwingConstants.CENTER);
		lblGioiTinh.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblGioiTinh, "cell 1 9");

		lblTrangThai = new JLabel("Trạng thái:");
		lblTrangThai.setHorizontalAlignment(SwingConstants.CENTER);
		lblTrangThai.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblTrangThai, "cell 2 9");

		rbNam = new JRadioButton("Nam");
		rbNam.setHorizontalAlignment(SwingConstants.CENTER);
		rbNam.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		add(rbNam, "flowx,cell 1 10,grow");
		rbNu = new JRadioButton("Nữ");
		rbNu.setHorizontalAlignment(SwingConstants.CENTER);
		rbNu.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		add(rbNu, "cell 1 10,grow");

		bgGioiTinh = new ButtonGroup();
		bgGioiTinh.add(rbNam);
		bgGioiTinh.add(rbNu);

		ckbHienMatKhau = new JCheckBox("Hiện mật khẩu");
		ckbHienMatKhau.setForeground(Color.BLACK);
		ckbHienMatKhau.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		ckbHienMatKhau.addActionListener(this);

		rbDangLam = new JRadioButton("Đang làm");
		rbDangLam.setSelected(true);
		rbDangLam.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		add(rbDangLam, "flowx,cell 2 10,grow");

		lblDiaChi = new JLabel("Địa chỉ");
		lblDiaChi.setHorizontalAlignment(SwingConstants.CENTER);
		lblDiaChi.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblDiaChi, "cell 1 11 2 1");

		txtDiaChi = new JTextField((String) null);
		txtDiaChi.setForeground(Color.WHITE);
		txtDiaChi.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		add(txtDiaChi, "cell 1 12 2 1,grow");

		lblHinhNVAnh = new JLabel("Hình ảnh");
		lblHinhNVAnh.setHorizontalAlignment(SwingConstants.CENTER);
		lblHinhNVAnh.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblHinhNVAnh, "cell 1 13");

		txtPublicIDNV = new JTextField((String) null);
		txtPublicIDNV.setForeground(Color.WHITE);
		txtPublicIDNV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtPublicIDNV.addKeyListener(this);
		add(txtPublicIDNV, "cell 1 14,grow");

		btnChonHinh = new JButton("hoặc Chọn hình...");
		btnChonHinh.setForeground(Color.BLACK);
		btnChonHinh.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnChonHinh.setBackground(Color.LIGHT_GRAY);
		add(btnChonHinh, "cell 2 14,grow");
		btnChonHinh.addActionListener(this);
		lblHinhNV = new JLabel("");
		lblHinhNV.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblHinhNV, "cell 1 15 2 1,alignx center,growy");
		add(ckbHienMatKhau, "cell 2 16,alignx right");

		btnTaoTaiKhoan = new JButton("Tạo tài khoản");
		btnTaoTaiKhoan.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnTaoTaiKhoan.setForeground(Color.BLACK);
		btnTaoTaiKhoan.setBackground(Color.LIGHT_GRAY);
		btnTaoTaiKhoan.addActionListener(this);
		add(btnTaoTaiKhoan, "cell 1 17 2 1,grow");

		lblKiemTraNhapLieu = new JLabel("");
		lblKiemTraNhapLieu.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		add(lblKiemTraNhapLieu, "cell 1 19 2 1");

		rbNghi = new JRadioButton("Nghỉ");
		rbNghi.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		add(rbNghi, "cell 2 10,grow");

		ButtonGroup bgTrangThai = new ButtonGroup();
		bgTrangThai.add(rbDangLam);
		bgTrangThai.add(rbNghi);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	private JLabel lblMaND;
	private JTextField txtMaND;
	private JLabel lblCCCD;
	private JTextField txtCCCC;
	private JLabel lblSDT;
	private JTextField txtSDT;
	private JLabel lblMatKhau;
	private JPasswordField txtMatKhau;
	private JLabel lblNhapLaiMatKhau;
	private JPasswordField txtNhapLaiMatKhau;
	private JLabel lblGioiTinh;
	private JRadioButton rbNam;
	private JRadioButton rbNu;
	private JLabel lblChucVu;
	private JComboBox cboChucVu;
	private JLabel lblNgaySinh;
	private JTextField txtNgaySinh;
	private JLabel lblTrangThai;
	private JRadioButton rbDangLam;
	private JRadioButton rbNghi;
	private JLabel lblDiaChi;
	private JTextField txtDiaChi;
	private JLabel lblHinhNVAnh;
	private JLabel lblHinhNV;

	private boolean hoTenHopLe = false;
	private boolean sdtHopLe = false;
	private boolean matKhauHopLe = false;
	private boolean nhapLaiMatKhauHopLe = false;
	private boolean cccdHopLe = false;
	private boolean ngaySinhHopLe = false;

	private static final String HOTEN_BIEUTHUC = "^[\\p{L}']+(\\s+[\\p{L}']{1,255})+$";
	private static final String SDT_BIEUTHUC = "^(0|84)(2(0[1-9]|1[0-9]|2[0-9]|3[0-9]|4[0-9]|5[1-9]|6[0-9]|7[0-9]|8[0-9]|9[0-9])|3[2-9]|5[5689]|7[06-9]|8[0-689]|9[0-4679])([0-9]{7})$";
	private static final String CCCD_BIEUTHUC = "^(\\d{9}|\\d{12})(\\d{3})?$";

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
					if (nhanVienIDAO.tonTaiSDT(txtSDT.getText())) {
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
		} else if (o.equals(txtCCCC)) {
			Pattern pattern = Pattern.compile(CCCD_BIEUTHUC);
			Matcher matcher = pattern.matcher(txtCCCC.getText());
			if (matcher.matches()) {
				cccdHopLe = true;
				lblKiemTraNhapLieu.setForeground(Color.green);
				lblKiemTraNhapLieu.setText("CCCD hợp lệ");
			} else {
				cccdHopLe = false;
				lblKiemTraNhapLieu.setForeground(Color.red);
				lblKiemTraNhapLieu.setText("CCCD phải 9 hoặc 12 số");
			}
		} else if (o.equals(txtPublicIDNV)) {
			NhanVien nhanVien = new NhanVien();
			nhanVien.setPublicID(txtPublicIDNV.getText());
			lblHinhNV.setIcon(nhanVien.getHinh(270, 360, 0));
		}
	}

	private JButton btnChonHinh;
	private JTextField txtPublicIDNV;

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnTaoTaiKhoan)) {
			if (bgGioiTinh.getSelection() == null) { 
				MyNotification panel = new MyNotification(chinhDeskstop, MyNotification.Type.WARNING,
						MyNotification.Location.CENTER, "Vui lòng chọn giới tính!");
				panel.showNotification();
			} else if (kiemTraRangBuoc()) {
				NhanVien nhanVien = new NhanVien();
				nhanVien.setHoTen(txtHoTen.getText());
				nhanVien.setSdt(txtSDT.getText());
				nhanVien.setCccd(txtCCCC.getText());
				ChucVu chucVu = ChucVu
						.valueOf(cboChucVu.getSelectedItem().toString().replaceAll(" ", "").toUpperCase());
				nhanVien.setChucVu(chucVu);
				TrangThaiNhanVien trangThaiNhanVien = null;
				nhanVien.setDiaChi(txtDiaChi.getText());
				LocalDate ngaySinh = LocalDate.parse(txtNgaySinh.getText().trim(),
						DateTimeFormatter.ofPattern("dd-MM-yyyy"));
				nhanVien.setNgaySinh(ngaySinh);
				if (rbDangLam.isSelected()) {
					trangThaiNhanVien = TrangThaiNhanVien.ĐANGLÀM;
				} else if (rbNghi.isSelected()) {
					trangThaiNhanVien = TrangThaiNhanVien.NGHỈ;
				}
				nhanVien.setTrangThai(trangThaiNhanVien);
				String gioiTinh = "";
				if (rbNam.isSelected())
					gioiTinh = "NAM";
				else if (rbNu.isSelected())
					gioiTinh = "NỮ";
				nhanVien.setGioiTinh(GioiTinh.valueOf(gioiTinh));
				nhanVien.setTaiKhoan(new TaiKhoan(nhanVien.getSdt(), txtNhapLaiMatKhau.getText()));
				if (selectedFile != null)
					nhanVien.setPublicID(MyImageIcon.updateImageToCloud("employees/", selectedFile));
				else nhanVien.setPublicID(txtPublicIDNV.getText());
				try {
					nhanVienIDAO.themMot(nhanVien);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				MyNotification panel = new MyNotification(chinhDeskstop, MyNotification.Type.SUCCESS,
						MyNotification.Location.CENTER, "Tạo tài khoản thành công");
				panel.showNotification();
			}
		}
		else if (o.equals(ckbHienMatKhau)) {
			if (ckbHienMatKhau.isSelected()) {
				txtMatKhau.setEchoChar((char) 0); // set echo character to be empty
				txtNhapLaiMatKhau.setEchoChar((char) 0); // set echo character to be empty
			} else {
				txtMatKhau.setEchoChar('*'); // set echo character to be empty
				txtNhapLaiMatKhau.setEchoChar('*'); // set echo character to be empty

			}
		}
		else if (o.equals(btnChonHinh)) {
			JFileChooser fileChooser = new JFileChooser();
			// Chỉ chấp nhận các file có định dạng là png, jpg, jpeg
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Hình ảnh", "png", "jpg", "jpeg");
			fileChooser.setFileFilter(filter);
			int returnValue = fileChooser.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				selectedFile = fileChooser.getSelectedFile();
				lblHinhNV.setIcon(new MyImageIcon(selectedFile.toString(), 360, 270, 0));
				txtPublicIDNV.setText("");
			}
		}

	}

	private File selectedFile = null;

	private boolean kiemTraRangBuoc() {
		MyNotification panel = null;
		if (!hoTenHopLe) {
			panel = new MyNotification(chinhDeskstop, MyNotification.Type.WARNING, MyNotification.Location.CENTER,
					"Họ tên sai cú pháp");
			panel.showNotification();
			return false;
		}
		if (!sdtHopLe) {
			panel = new MyNotification(chinhDeskstop, MyNotification.Type.WARNING, MyNotification.Location.CENTER,
					"Số điện thoại đã sai cú pháp hoặc đã tồn tại");
			panel.showNotification();
			return false;
		}
		if (!matKhauHopLe) {
			panel = new MyNotification(chinhDeskstop, MyNotification.Type.WARNING, MyNotification.Location.CENTER,
					"Mật khẩu không được để trống");
			panel.showNotification();
			return false;
		}
		if (!nhapLaiMatKhauHopLe) {
			panel = new MyNotification(chinhDeskstop, MyNotification.Type.WARNING, MyNotification.Location.CENTER,
					"Mật khẩu không trùng khớp");
			panel.showNotification();
			return false;
		}
		if (!cccdHopLe) {
			panel = new MyNotification(chinhDeskstop, MyNotification.Type.WARNING, MyNotification.Location.CENTER,
					"CCCD phải 9 hoặc 12 số");
			panel.showNotification();
			return false;
		}
		if (!ngaySinhHopLe) {
			panel = new MyNotification(chinhDeskstop, MyNotification.Type.WARNING, MyNotification.Location.CENTER,
					"Bạn muốn thêm nhân viên chưa đủ 16 tuổi à =))");
			panel.showNotification();
			return false;
		}
		return true;
	}

	public void chooseDateNgaySinh(SelectedDate date) {
		// TODO Auto-generated method stub
		LocalDate ngaySinh = LocalDate.parse(txtNgaySinh.getText().trim(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		LocalDate ngayHienTai = LocalDate.now();
		LocalDate ngaySinh16Tuoi = ngayHienTai.minusYears(16);
		if (ngaySinh.isBefore(ngaySinh16Tuoi)) {
			ngaySinhHopLe = true;
			lblKiemTraNhapLieu.setForeground(Color.green);
			lblKiemTraNhapLieu.setText("Đủ 16 tuổi");
		} else {
			ngaySinhHopLe = false;
			lblKiemTraNhapLieu.setForeground(Color.red);
			lblKiemTraNhapLieu.setText("Chưa đủ 16 tuổi :|");
		}
	}

}
