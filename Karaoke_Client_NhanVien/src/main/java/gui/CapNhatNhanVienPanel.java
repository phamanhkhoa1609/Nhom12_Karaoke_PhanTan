package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jdesktop.swingx.combobox.ListComboBoxModel;

import entities.DichVu;
import entities.NhanVien;
import entities.enums.ChucVu;
import entities.enums.GioiTinh;
import entities.enums.LoaiDichVu;
import entities.enums.TrangThaiDichVu;
import entities.enums.TrangThaiNhanVien;
import interfaces.NhanVienIDAO;

import javax.swing.border.EtchedBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTextPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;
import utils.VietnameseFormatter;
import utils.guicomponents.MyImageIcon;
import utils.guicomponents.MyPanel;
import utils.guicomponents.datechooser.EventDateChooser;
import utils.guicomponents.datechooser.MyDateChooser;
import utils.guicomponents.datechooser.SelectedAction;
import utils.guicomponents.datechooser.SelectedDate;
import utils.guicomponents.notification.MyNotification;
import utils.guicomponents.optionmessage.GlassPanePopup;
import utils.guicomponents.optionmessage.MyMessage;

import javax.swing.SpinnerModel;

public class CapNhatNhanVienPanel extends MyPanel implements ActionListener,KeyListener {

	private JTextField txtMaNV;
	private JTextField txtHoTen;
	private JTextField txtPublicIDNV;
	private JButton btnChonHinh;
	private JLabel lblHinhNV;
	private JButton btnQuayLaiHinhCu;
	private JLabel lblCCCD;
	private JTextField txtCCCD;
	private JLabel lblSDT;
	private JTextField txtSDT;
	private JLabel lblChucVu;
	private JLabel lblGioiTinh;
	private JRadioButton rbNam;
	private JRadioButton rbNu;
	private JLabel lblDiaChi;
	private JTextField txtDiaChi;
	private MyDateChooser dateChooser;
	private JLabel lblNgaySinh;
	private JTextField txtNgaySinh;
	private JLabel lblTrangThai;
	private JRadioButton rbDangLam;
	private JRadioButton rbNghi;
	private NhanVien nhanVien;
	private JButton btnCapNhat;
	private JComboBox cboChucVu;
	private JLabel lblKiemTraNhapLieu;
	private long maNV;
	private NhanVienIDAO nhanVienIDAO;
	public CapNhatNhanVienPanel(long maNV) {
		this.maNV = maNV;
	}
	@Override
	public void KhoiTaoGiaoDien() {
		nhanVienIDAO = clientIDAO.getNhanVienIDAO();
		try {
			nhanVien = nhanVienIDAO.layTheoMa(maNV);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setLayout(new MigLayout("", "[0px:275px:275px][300px:300px,grow][300px:300px,grow][grow]", "[][][30px:40px:40px][][30px:40px:40px][][30px:40px:40px][][30px:40px:40px][][30px:40px:40px][30px:40px:40px][0px:40px:40px]"));
		Border blackline = BorderFactory.createLineBorder(Color.black);
		List<JTextField> listTextField = new ArrayList<JTextField>();
		JLabel lblTieuDe = new JLabel();
		lblTieuDe.setHorizontalAlignment(SwingConstants.CENTER);
		lblTieuDe.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTieuDe.setForeground(Color.WHITE);
		lblTieuDe.setText("Cập nhật Nhân viên");
		add(lblTieuDe, "cell 0 0 3 1,alignx center");

		JLabel lblHinhAnhID = new JLabel("ID Hình Ảnh");
		lblHinhAnhID.setHorizontalAlignment(SwingConstants.CENTER);
		lblHinhAnhID.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblHinhAnhID, "cell 0 1,alignx center");

		lblCCCD = new JLabel("Căn cước công dân");
		lblCCCD.setHorizontalAlignment(SwingConstants.CENTER);
		lblCCCD.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblCCCD, "cell 2 1,alignx center");

		txtPublicIDNV = new JTextField(nhanVien.getPublicID());
		txtPublicIDNV.setForeground(Color.WHITE);
		txtPublicIDNV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtPublicIDNV.addKeyListener(this);
		add(txtPublicIDNV, "cell 0 2,grow");

		txtCCCD = new JTextField(nhanVien.getCccd());
		txtCCCD.setEditable(false);
		txtCCCD.setFocusable(false);
		txtCCCD.setForeground(Color.WHITE);
		txtCCCD.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtCCCD.addKeyListener(this);
		listTextField.add(txtCCCD);
		add(txtCCCD, "cell 2 2,grow");

		btnChonHinh = new JButton("Chọn hình");
		btnChonHinh.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		add(btnChonHinh, "flowx,cell 0 3,grow");
		btnChonHinh.addActionListener(this);

		lblSDT = new JLabel("Số điện thoại");
		lblSDT.setHorizontalAlignment(SwingConstants.CENTER);
		lblSDT.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblSDT, "cell 2 3,alignx center");

		lblHinhNV = new JLabel(nhanVien.getHinh(270, 360, 0));
		add(lblHinhNV, "cell 0 4 1 7,aligny top");

		JLabel lblMaNV = new JLabel("Mã nhân viên");
		lblMaNV.setHorizontalAlignment(SwingConstants.CENTER);
		lblMaNV.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblMaNV, "cell 1 1,alignx center");

		txtMaNV = new JTextField(nhanVien.getMaND() + "");
		txtMaNV.setForeground(Color.WHITE);
		txtMaNV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtMaNV.setEditable(false);
		txtMaNV.setFocusable(false);
		listTextField.add(txtMaNV);
		add(txtMaNV, "cell 1 2,grow");

		JLabel lblHoTen = new JLabel("Họ tên");
		lblHoTen.setHorizontalAlignment(SwingConstants.CENTER);
		lblHoTen.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblHoTen, "cell 1 3,alignx center");

		txtHoTen = new JTextField(nhanVien.getHoTen());
		txtHoTen.setForeground(Color.WHITE);
		txtHoTen.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtHoTen.addKeyListener(this);
		listTextField.add(txtHoTen);
		add(txtHoTen, "cell 1 4,grow");

		txtSDT = new JTextField(nhanVien.getSdt());
		txtSDT.setFocusable(false);
		txtSDT.setEditable(false);
		txtSDT.setForeground(Color.WHITE);
		txtSDT.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtSDT.addKeyListener(this);
		listTextField.add(txtSDT);
		add(txtSDT, "cell 2 4,grow");

		lblChucVu = new JLabel("Chức vụ");
		lblChucVu.setHorizontalAlignment(SwingConstants.CENTER);
		lblChucVu.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblChucVu, "cell 1 5,alignx center");

		lblNgaySinh = new JLabel("Ngày sinh");
		lblNgaySinh.setHorizontalAlignment(SwingConstants.CENTER);
		lblNgaySinh.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblNgaySinh, "cell 2 5,alignx center");

		List<String> listStringChucVu = new ArrayList<String>();
		for (ChucVu chucVu : ChucVu.values()) {
			listStringChucVu.add(chucVu.getChucVu());
		}

		cboChucVu = new JComboBox(new ListComboBoxModel<String>(listStringChucVu));
		cboChucVu.setForeground(Color.WHITE);
		cboChucVu.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		add(cboChucVu, "cell 1 6,grow");

		txtNgaySinh = new JTextField();
		txtNgaySinh.setForeground(Color.WHITE);
		txtNgaySinh.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtNgaySinh.setHorizontalAlignment(SwingConstants.RIGHT);
		add(txtNgaySinh, "cell 2 6,grow");
		dateChooser = new MyDateChooser();
		dateChooser.setTextRefernce(txtNgaySinh);
		dateChooser.setSelectedDate(VietnameseFormatter.dinhDangNgaySelectedDate(nhanVien.getNgaySinh()));

		dateChooser.addEventDateChooser(new EventDateChooser() {
			@Override
			public void dateSelected(SelectedAction action, SelectedDate date) {
				chooseDateNgaySinh(date);
			}
		});

		lblGioiTinh = new JLabel("Giới tính");
		lblGioiTinh.setHorizontalAlignment(SwingConstants.CENTER);
		lblGioiTinh.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblGioiTinh, "cell 1 7,alignx center");

		lblTrangThai = new JLabel("Trạng thái:");
		lblTrangThai.setHorizontalAlignment(SwingConstants.CENTER);
		lblTrangThai.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblTrangThai, "cell 2 7,alignx center");

		rbNam = new JRadioButton("Nam");
		rbNam.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		add(rbNam, "flowx,cell 1 8,grow");
		rbNu = new JRadioButton("Nữ");
		rbNu.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		add(rbNu, "cell 1 8,grow");
		ButtonGroup bgGioiTinh = new ButtonGroup();
		bgGioiTinh.add(rbNam);
		bgGioiTinh.add(rbNu);
		switch (nhanVien.getGioiTinh()) {
		case NAM:
			rbNam.setSelected(true);
			break;
		case NỮ:
			rbNu.setSelected(true);
			break;
		}

		rbDangLam = new JRadioButton("Đang làm");
		rbDangLam.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		add(rbDangLam, "flowx,cell 2 8,grow");

		lblDiaChi = new JLabel("Địa chỉ");
		lblDiaChi.setHorizontalAlignment(SwingConstants.CENTER);
		lblDiaChi.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblDiaChi, "cell 1 9 2 1,alignx center");

		txtDiaChi = new JTextField(nhanVien.getDiaChi());
		txtDiaChi.setForeground(Color.WHITE);
		txtDiaChi.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		add(txtDiaChi, "cell 1 10 2 1,grow");

		for (JTextField jTextField : listTextField) {
			jTextField.setBorder(null);
			jTextField.setHorizontalAlignment(SwingConstants.CENTER);
		}

		btnCapNhat = new JButton("Cập nhật");
		btnCapNhat.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		add(btnCapNhat, "cell 1 11 2 1,grow");

		btnCapNhat.addActionListener(this);

		btnQuayLaiHinhCu = new JButton("Quay lại hình cũ");
		btnQuayLaiHinhCu.addActionListener(this);
		btnQuayLaiHinhCu.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		add(btnQuayLaiHinhCu, "cell 0 3,grow");

		rbNghi = new JRadioButton("Nghỉ");
		rbNghi.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		add(rbNghi, "cell 2 8,grow");

		lblKiemTraNhapLieu = new JLabel("");
		lblKiemTraNhapLieu.setHorizontalAlignment(SwingConstants.CENTER);
		lblKiemTraNhapLieu.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		add(lblKiemTraNhapLieu, "cell 1 12 2 1");
		ButtonGroup bgTinhTrang = new ButtonGroup();
		bgTinhTrang.add(rbDangLam);
		bgTinhTrang.add(rbNghi);
		switch (nhanVien.getTrangThai()) {
		case ĐANGLÀM:
			rbDangLam.setSelected(true);
			break;
		case NGHỈ:
			rbNghi.setSelected(true);
			break;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnCapNhat) && kiemTraRangBuoc()) {
			MyMessage myMessage = new MyMessage("Bạn có muốn cập nhật không?",
					"Hãy xem kỹ thông tin trước khi cập nhật!", true);
			GlassPanePopup.showPopup(myMessage);
			myMessage.eventOK(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {
					GlassPanePopup.closePopupLast();
					MyNotification panel = new MyNotification(chinhDeskstop, MyNotification.Type.INFO,
							MyNotification.Location.CENTER, "Đang tải hình lên");
					panel.showNotification();
					nhanVien.setHoTen(txtHoTen.getText().trim());
					nhanVien.setChucVu(
							ChucVu.valueOf(cboChucVu.getSelectedItem().toString().replaceAll(" ", "").toUpperCase()));
					TrangThaiNhanVien trangThaiNV = null;
					if (rbDangLam.isSelected()) {
						trangThaiNV = TrangThaiNhanVien.valueOf(rbDangLam.getText().toUpperCase());
					} else if (rbNghi.isSelected()) {
						trangThaiNV = TrangThaiNhanVien.valueOf(rbNghi.getText().toUpperCase());
					}
					nhanVien.setTrangThai(trangThaiNV);
					GioiTinh gioiTinh = null;
					if (rbNam.isSelected()) {
						gioiTinh = GioiTinh.valueOf(rbNam.getText().toUpperCase());
					} else if (rbNam.isSelected()) {
						gioiTinh = GioiTinh.valueOf(rbNam.getText().toUpperCase());
					}
					nhanVien.setGioiTinh(gioiTinh);
					nhanVien.setDiaChi(txtDiaChi.getText().trim());
					LocalDate ngaySinh = LocalDate.parse(txtNgaySinh.getText().trim(),
							DateTimeFormatter.ofPattern("dd-MM-yyyy"));
					nhanVien.setNgaySinh(ngaySinh);
					if(selectedFile != null) {
						nhanVien.setPublicID(MyImageIcon.updateImageToCloud("employees/", selectedFile));
					} else {
						nhanVien.setPublicID(txtPublicIDNV.getText());
					}
					try {
						nhanVienIDAO.suaMot(nhanVien);
							thanhCong = true;
						manHinhChua.dispose();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						MyNotification panel1 = new MyNotification(chinhDeskstop, MyNotification.Type.WARNING,
								MyNotification.Location.CENTER, "Cập nhật thất bại, hãy kiểm tra lại kết nối!");
						panel1.showNotification();
					}
				}
			});
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
		else if (o.equals(btnQuayLaiHinhCu)) {
			lblHinhNV.setIcon(nhanVien.getHinh(270, 360, 0));
		}
	}
	private boolean thanhCong = false;
	private File selectedFile = null;
	private boolean ngaySinhHopLe;
	private boolean hoTenHopLe;

	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	private static final String HOTEN_BIEUTHUC = "^[\\p{L}']+(\\s+[\\p{L}']{1,255})+$";
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o.equals(txtPublicIDNV)) {
			NhanVien nhanVien = new NhanVien();
			nhanVien.setPublicID(txtPublicIDNV.getText());
			lblHinhNV.setIcon(nhanVien.getHinh(270, 360, 0));
		} else if (o.equals(txtHoTen)) {
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
	private boolean kiemTraRangBuoc() {
		MyNotification panel = null;
		if (!hoTenHopLe) {
			panel = new MyNotification(chinhDeskstop, MyNotification.Type.WARNING, MyNotification.Location.CENTER,
					"Họ tên sai cú pháp");
			panel.showNotification();
			return false;
		}
		if (!ngaySinhHopLe) {
			panel = new MyNotification(chinhDeskstop, MyNotification.Type.WARNING, MyNotification.Location.CENTER,
					"Ngày sinh không hợp lệ");
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
	public boolean getThanhCong() {
		return thanhCong;
	}
}
