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
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jdesktop.swingx.combobox.ListComboBoxModel;

import entities.Phong;
import entities.enums.LoaiPhong;
import entities.enums.TrangThaiPhong;
import interfaces.PhongIDAO;

import javax.swing.border.EtchedBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

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
import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;
import utils.ClientIDAO;
import utils.VietnameseFormatter;
import utils.guicomponents.MyImageIcon;
import utils.guicomponents.MyPanel;
import utils.guicomponents.notification.MyNotification;
import utils.guicomponents.optionmessage.GlassPanePopup;
import utils.guicomponents.optionmessage.MyMessage;

public class CapNhatPhongPanel extends MyPanel implements ActionListener, KeyListener {

	private JTextField txtMaPhong;
	private JTextField txtTenPhong;
	private JSpinner spnGiaPhong;
	private JComboBox<String> cboLoaiPhong;
	private Phong phong;
	private JComboBox<String> cboTrangThai;
	private JButton btnCapNhat;
	private JTextPane txtMoTa;
	private JTextField txtPublicIDPhong;
	private JButton btnChonHinh;
	private JLabel lblHinhPhong;
	private JButton btnQuayLaiHinhCu;
	private JLabel lblKiemTraNhapLieu;
	private PhongIDAO phongIDAO;
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 * 
	 * @throws RemoteException
	 */
	private Long maPhong;

	public CapNhatPhongPanel(long maPhong) {
		this.maPhong = maPhong;
	};

	@Override
	public void KhoiTaoGiaoDien() {
		phongIDAO = clientIDAO.getPhongIDAO();
		try {
			phong = phongIDAO.layTheoMa(maPhong);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setLayout(new MigLayout("", "[365px][400px:400px,grow]", "[][][30px:40px:40px][][30px:40px:40px][][30px:40px:40px][][30px:40px:40px][][30px:40px:40px][][200px:200px,grow][30px:40px:40px][]"));
		Border blackline = BorderFactory.createLineBorder(Color.black);

		JLabel lblTieuDe = new JLabel();
		lblTieuDe.setHorizontalAlignment(SwingConstants.CENTER);
		lblTieuDe.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTieuDe.setForeground(Color.WHITE);
		lblTieuDe.setText("Cập nhật Phòng");
		add(lblTieuDe, "cell 0 0 2 1,alignx center");

		JLabel lblHinhAnhID = new JLabel("ID Hình Ảnh");
		lblHinhAnhID.setHorizontalAlignment(SwingConstants.CENTER);
		lblHinhAnhID.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblHinhAnhID, "cell 0 1,alignx center");

		txtPublicIDPhong = new JTextField(this.phong.getPublicID());
		txtPublicIDPhong.setForeground(Color.WHITE);
		txtPublicIDPhong.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtPublicIDPhong.addKeyListener(this);
		add(txtPublicIDPhong, "cell 0 2,grow");

		btnChonHinh = new JButton("Chọn hình");
		btnChonHinh.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		add(btnChonHinh, "flowx,cell 0 3,growx");
		btnChonHinh.addActionListener(this);

		lblHinhPhong = new JLabel(phong.getHinh(360, 270, 0));

		lblHinhPhong.setSize(new Dimension(360, 270));
		add(lblHinhPhong, "cell 0 4 1 7,aligny top");

		List<JTextField> listTextField = new ArrayList<JTextField>();

		JLabel lblMaPhong = new JLabel("Mã phòng");
		lblMaPhong.setHorizontalAlignment(SwingConstants.CENTER);
		lblMaPhong.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblMaPhong, "cell 1 1,alignx center");

		txtMaPhong = new JTextField(phong.getMaPhong() + "");
		txtMaPhong.setForeground(Color.WHITE);
		txtMaPhong.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtMaPhong.setEditable(false);
		txtMaPhong.setFocusable(false);
		listTextField.add(txtMaPhong);
		add(txtMaPhong, "cell 1 2,grow");
		txtMaPhong.addKeyListener(this);

		JLabel lblTenPhong = new JLabel("Tên phòng");
		lblTenPhong.setHorizontalAlignment(SwingConstants.CENTER);
		lblTenPhong.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblTenPhong, "cell 1 3,alignx center");

		txtTenPhong = new JTextField(phong.getTenPhong());
		txtTenPhong.setForeground(Color.WHITE);
		txtTenPhong.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		listTextField.add(txtTenPhong);
		add(txtTenPhong, "cell 1 4,grow");
		txtTenPhong.addKeyListener(this);

		JLabel lblGiaPhong = new JLabel("Giá phòng");
		lblGiaPhong.setHorizontalAlignment(SwingConstants.CENTER);
		lblGiaPhong.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblGiaPhong, "cell 1 5,alignx center");

		spnGiaPhong = new JSpinner(new SpinnerNumberModel(phong.getGiaPhong(), 0L, null, 1000L));
		spnGiaPhong.setForeground(Color.WHITE);
		spnGiaPhong.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		add(spnGiaPhong, "cell 1 6,grow");
		spnGiaPhong.addKeyListener(this);

		JLabel lblLoaiPhong = new JLabel("Loại phòng:");
		lblLoaiPhong.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoaiPhong.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblLoaiPhong, "cell 1 7,alignx center");

		List<String> listStringLoaiPhong = new ArrayList<String>();
		for (LoaiPhong loaiPhong : LoaiPhong.values()) {
			listStringLoaiPhong.add(loaiPhong.getLoaiPhong());
		}
		cboLoaiPhong = new JComboBox<String>();
		cboLoaiPhong.setForeground(Color.WHITE);
		ComboBoxModel<String> cbomLoaiPhong = new ListComboBoxModel<String>(listStringLoaiPhong);
		cboLoaiPhong.setModel(cbomLoaiPhong);
		cboLoaiPhong.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		add(cboLoaiPhong, "cell 1 8,grow");

		JLabel lblTrangThai = new JLabel("Trạng thái:");
		lblTrangThai.setHorizontalAlignment(SwingConstants.CENTER);
		lblTrangThai.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblTrangThai, "cell 1 9,growx");

		List<String> listStringTrangThaiPhong = new ArrayList<String>();
		for (TrangThaiPhong trangThaiPhong : TrangThaiPhong.values()) {
			listStringTrangThaiPhong.add(trangThaiPhong.getTrangThaiPhong());
		}
		ComboBoxModel<String> cbomTrangThaiPhong = new ListComboBoxModel<String>(listStringTrangThaiPhong);
		cboTrangThai = new JComboBox<String>();
		cboTrangThai.setForeground(Color.WHITE);
		cboTrangThai.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		cboTrangThai.setModel(cbomTrangThaiPhong);
		add(cboTrangThai, "cell 1 10,grow");

		JLabel lblMoTa = new JLabel("Mô tả");
		lblMoTa.setHorizontalAlignment(SwingConstants.CENTER);
		lblMoTa.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblMoTa, "cell 1 11,alignx center");

		txtMoTa = new JTextPane();
		txtMoTa.setForeground(Color.WHITE);
		txtMoTa.setText(phong.getMoTa());
		txtMoTa.setCaretPosition(0); // Đặt con trỏ văn bản ở đầu
		JScrollPane scrMoTa = new JScrollPane(txtMoTa);
		scrMoTa.getVerticalScrollBar().setValue(0);
		add(scrMoTa, "cell 1 12,grow");

		for (JTextField jTextField : listTextField) {
			jTextField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			jTextField.setBorder(null);
			jTextField.setHorizontalAlignment(SwingConstants.CENTER);
		}
		txtMoTa.setBorder(null);
		txtMoTa.setFont(new Font("Times New Roman", Font.PLAIN, 15));

		btnCapNhat = new JButton("Cập nhật");
		btnCapNhat.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		add(btnCapNhat, "cell 1 13,grow");

		btnCapNhat.addActionListener(this);

		btnQuayLaiHinhCu = new JButton("Quay lại hình cũ");
		btnQuayLaiHinhCu.addActionListener(this);
		btnQuayLaiHinhCu.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnQuayLaiHinhCu.addActionListener(this);
		add(btnQuayLaiHinhCu, "cell 0 3,growx");

		lblKiemTraNhapLieu = new JLabel("");
		lblKiemTraNhapLieu.setHorizontalAlignment(SwingConstants.CENTER);
		lblKiemTraNhapLieu.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		add(lblKiemTraNhapLieu, "cell 0 14 2 1");

		Pattern pattern = Pattern.compile(TENPHONG_BIEUTHUC);
		Matcher matcher = pattern.matcher(phong.getTenPhong());
		phongHopLe = matcher.matches();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	private boolean phongHopLe;
	private static final String TENPHONG_BIEUTHUC = "^Phòng(?: [a-zA-Z0-9]+)+$";

	@Override
	public void keyReleased(KeyEvent e) {
		lblKiemTraNhapLieu.setText("");
		Object o = e.getSource();
		if (o.equals(txtTenPhong)) {
			Pattern pattern = Pattern.compile(TENPHONG_BIEUTHUC);
			Matcher matcher = pattern.matcher(txtTenPhong.getText());
			if (matcher.matches()) {
				phongHopLe = true;
				lblKiemTraNhapLieu.setText("Tên phòng hợp lệ");
				lblKiemTraNhapLieu.setForeground(Color.green);
			} else {
				phongHopLe = false;
				lblKiemTraNhapLieu.setForeground(Color.red);
				lblKiemTraNhapLieu.setText("Tên phòng phải bắt đầu bằng chuỗi ký tự “Phòng” và dấu cách.");
			}
		} else if (o.equals(txtPublicIDPhong)) {
			Phong phong = new Phong();
			phong.setPublicID(txtPublicIDPhong.getText());
			lblHinhPhong.setIcon(phong.getHinh(360, 270, 0));
		}
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
					MyNotification panel = new MyNotification(chinhDeskstop, MyNotification.Type.INFO,
							MyNotification.Location.CENTER, "Đang tải hình lên");
					panel.showNotification();
					phong.setTenPhong(txtTenPhong.getText().trim());
					phong.setGiaPhong(Long.parseLong(spnGiaPhong.getValue().toString()));
					LoaiPhong loaiPhong = LoaiPhong
							.valueOf(cboLoaiPhong.getSelectedItem().toString().replaceAll(" ", "").toUpperCase());
					phong.setLoaiPhong(loaiPhong);
					TrangThaiPhong trangThaiPhong = TrangThaiPhong
							.valueOf(cboTrangThai.getSelectedItem().toString().replaceAll(" ", "").toUpperCase());
					phong.setTrangThai(trangThaiPhong);
					phong.setMoTa(txtMoTa.getText());
					if (selectedFile != null)
						phong.setPublicID(MyImageIcon.updateImageToCloud("rooms/", selectedFile));
					else
						phong.setPublicID(txtPublicIDPhong.getText());
					try {
						phongIDAO.suaMot(phong);
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
		if (o.equals(btnChonHinh)) {
			JFileChooser fileChooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Hình ảnh", "png", "jpg", "jpeg");
			fileChooser.setFileFilter(filter);
			int returnValue = fileChooser.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				selectedFile = fileChooser.getSelectedFile();
				lblHinhPhong.setIcon(new MyImageIcon(selectedFile.toString(), 360, 270, 0));
				txtPublicIDPhong.setText("");
			}
		}
		if (o.equals(btnQuayLaiHinhCu)) {
			lblHinhPhong.setIcon(phong.getHinh(360, 270, 0));
		}
	}

	private boolean thanhCong = false;

	public boolean getThanhCong() {
		return thanhCong;
	}

	private File selectedFile = null;

	private boolean kiemTraRangBuoc() {
		MyNotification panel = null;
		if (!phongHopLe) {
			panel = new MyNotification(chinhDeskstop, MyNotification.Type.WARNING, MyNotification.Location.CENTER,
					"Tên phòng sai cú pháp");
			panel.showNotification();
			return false;
		}
		return true;
	}

}
