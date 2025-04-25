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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jdesktop.swingx.combobox.ListComboBoxModel;

import entities.DichVu;
import entities.Phong;
import entities.enums.LoaiDichVu;
import entities.enums.LoaiPhong;
import entities.enums.TrangThaiDichVu;
import entities.enums.TrangThaiPhong;
import interfaces.DichVuIDAO;

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
import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;
import utils.VietnameseFormatter;
import utils.guicomponents.MyImageIcon;
import utils.guicomponents.MyPanel;
import utils.guicomponents.notification.MyNotification;
import utils.guicomponents.optionmessage.GlassPanePopup;
import utils.guicomponents.optionmessage.MyMessage;

import javax.swing.SpinnerModel;

public class ThemDichVuPanel extends MyPanel implements ActionListener, ChangeListener {

	private JTextField txtMaDV;
	private JTextField txtTenDV;
	private JSpinner spnGiaDV;
	private JComboBox<String> cboLoaiDV;
	private JComboBox<String> cboTrangThai;
	private JButton btnThem;
	private JTextPane txtMoTa;
	private JTextField txtPublicIDDV;
	private JButton btnChonHinh;
	private JLabel lblHinhDV;
	private DichVuIDAO dichVuIDAO;
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */

	public ThemDichVuPanel() {}
	@Override
	public void KhoiTaoGiaoDien() {
		dichVuIDAO = clientIDAO.getDichVuIDAO();
		setLayout(new MigLayout("", "[365px][400px:400px,grow]",
				"[][][30px:40px:40px][][30px:40px:40px][][30px:40px:40px][][30px:40px:40px][][30px:40px:40px][][30px:40px:40px][][0px:200px:200px][30px:40px:40px][0px:40px:40px]"));
		Border blackline = BorderFactory.createLineBorder(Color.black);

		JLabel lblTieuDe = new JLabel();
		lblTieuDe.setHorizontalAlignment(SwingConstants.CENTER);
		lblTieuDe.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTieuDe.setForeground(Color.WHITE);
		lblTieuDe.setText("Thêm Dịch vụ");
		add(lblTieuDe, "cell 0 0 2 1,alignx center");

		JLabel lblHinhAnhID = new JLabel("ID Hình Ảnh");
		lblHinhAnhID.setHorizontalAlignment(SwingConstants.CENTER);
		lblHinhAnhID.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblHinhAnhID, "cell 0 1,alignx center");

		txtPublicIDDV = new JTextField();
		txtPublicIDDV.setForeground(Color.WHITE);
		txtPublicIDDV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		add(txtPublicIDDV, "cell 0 2,grow");

		btnChonHinh = new JButton("Chọn hình");
		btnChonHinh.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		add(btnChonHinh, "flowx,cell 0 3,alignx center");
		btnChonHinh.addActionListener(this);

		lblHinhDV = new JLabel();
		lblHinhDV.setIcon(new MyImageIcon("src/main/resources/images/icons/dichvu.png", 360, 270, 0));
		add(lblHinhDV, "cell 0 4 1 9,aligny top");

		List<JTextField> listTextField = new ArrayList<JTextField>();

		JLabel lblMaDV = new JLabel("Mã dịch vụ");
		lblMaDV.setHorizontalAlignment(SwingConstants.CENTER);
		lblMaDV.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblMaDV, "cell 1 1,alignx center");

		txtMaDV = new JTextField("ID sẽ được tạo tự động");
		txtMaDV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtMaDV.setEditable(false);
		txtMaDV.setFocusable(false);
		listTextField.add(txtMaDV);
		add(txtMaDV, "cell 1 2,grow");

		JLabel lblTenDV = new JLabel("Tên dịch vụ");
		lblTenDV.setHorizontalAlignment(SwingConstants.CENTER);
		lblTenDV.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblTenDV, "cell 1 3,alignx center");

		txtTenDV = new JTextField();
		txtTenDV.setForeground(Color.WHITE);
		txtTenDV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		listTextField.add(txtTenDV);
		add(txtTenDV, "cell 1 4,grow");

		lblSoLuong = new JLabel("Số lượng");
		lblSoLuong.setHorizontalAlignment(SwingConstants.CENTER);
		lblSoLuong.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblSoLuong, "cell 1 5,growx");

		spnSoLuong = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
		spnSoLuong.setForeground(Color.WHITE);
		spnSoLuong.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		spnSoLuong.addChangeListener(this);
		add(spnSoLuong, "cell 1 6,grow");

		JLabel lblGiaDV = new JLabel("Giá dịch vụ");
		lblGiaDV.setHorizontalAlignment(SwingConstants.CENTER);
		lblGiaDV.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblGiaDV, "cell 1 7,alignx center");

		spnGiaDV = new JSpinner(new SpinnerNumberModel(0L, 0L, null, 1000L));
		spnGiaDV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		add(spnGiaDV, "cell 1 8,grow");

		JLabel lblLoaiDV = new JLabel("Loại dịch vụ:");
		lblLoaiDV.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoaiDV.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblLoaiDV, "cell 1 9,alignx center");

		List<String> listStringLoaiDichVu = new ArrayList<String>();
		for (LoaiDichVu loaiDichVu : LoaiDichVu.values()) {
			listStringLoaiDichVu.add(loaiDichVu.getLoaiDichVu());
		}
		cboLoaiDV = new JComboBox<String>();
		cboLoaiDV.setForeground(Color.WHITE);
		ComboBoxModel<String> cbomLoaiDV = new ListComboBoxModel<String>(listStringLoaiDichVu);
		cboLoaiDV.setModel(cbomLoaiDV);
		cboLoaiDV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		add(cboLoaiDV, "cell 1 10,grow");

		JLabel lblTrangThai = new JLabel("Trạng thái:");
		lblTrangThai.setHorizontalAlignment(SwingConstants.CENTER);
		lblTrangThai.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblTrangThai, "cell 1 11,growx");

		List<String> listStringTrangThaiDichVu = new ArrayList<String>();
		for (TrangThaiDichVu trangThaiDichVu : TrangThaiDichVu.values()) {
			listStringTrangThaiDichVu.add(trangThaiDichVu.getTrangThaiDichVu());
		}
		ComboBoxModel<String> cbomTrangThaiPhong = new ListComboBoxModel<String>(listStringTrangThaiDichVu);
		cboTrangThai = new JComboBox<String>();
		cboTrangThai.setForeground(Color.WHITE);
		cboTrangThai.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		cboTrangThai.setModel(cbomTrangThaiPhong);
		add(cboTrangThai, "cell 1 12,grow");

		JLabel lblMoTa = new JLabel("Mô tả");
		lblMoTa.setHorizontalAlignment(SwingConstants.CENTER);
		lblMoTa.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblMoTa, "cell 1 13,alignx center");

		txtMoTa = new JTextPane();
		txtMoTa.setForeground(Color.WHITE);
		txtMoTa.setCaretPosition(0); // Đặt con trỏ văn bản ở đầu
		JScrollPane scrMoTa = new JScrollPane(txtMoTa);
		scrMoTa.getVerticalScrollBar().setValue(0);
		add(scrMoTa, "cell 1 14,grow");

		for (JTextField jTextField : listTextField) {
			jTextField.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			jTextField.setBorder(null);
			jTextField.setHorizontalAlignment(SwingConstants.CENTER);
		}
		txtMoTa.setBorder(null);
		txtMoTa.setFont(new Font("Times New Roman", Font.PLAIN, 12));

		btnThem = new JButton("Thêm");
		btnThem.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		add(btnThem, "cell 1 15,grow");

		btnThem.addActionListener(this);

		lblKiemTraHopLe = new JLabel("");
		lblKiemTraHopLe.setForeground(Color.ORANGE);
		lblKiemTraHopLe.setHorizontalAlignment(SwingConstants.CENTER);
		lblKiemTraHopLe.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		add(lblKiemTraHopLe, "cell 0 16 2 1");
	}

	private JLabel lblSoLuong;
	private JSpinner spnSoLuong;

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnThem)) {
			DichVu dichVu = new DichVu();
			dichVu.setTenDV(txtTenDV.getText().trim());
			dichVu.setGiaDV(Long.parseLong(spnGiaDV.getValue().toString()));
			dichVu.setSoLuong((int) spnSoLuong.getValue());
			LoaiDichVu loaiDV = LoaiDichVu
					.valueOf(cboLoaiDV.getSelectedItem().toString().replaceAll(" ", "").toUpperCase());
			dichVu.setLoaiDV(loaiDV);
			TrangThaiDichVu trangThaiDV = TrangThaiDichVu
					.valueOf(cboTrangThai.getSelectedItem().toString().replaceAll(" ", "").toUpperCase());
			dichVu.setTrangThai(trangThaiDV);
			dichVu.setMoTa(txtMoTa.getText());
			if (selectedFile != null)
				dichVu.setPublicID(MyImageIcon.updateImageToCloud("services/", selectedFile));
			else
				dichVu.setPublicID(txtPublicIDDV.getText());
			try {
				MyNotification panel = new MyNotification(chinhDeskstop, MyNotification.Type.INFO,
						MyNotification.Location.CENTER, "Đang tải ảnh lên");
				panel.showNotification();
				dichVuIDAO.themMot(dichVu);
				thanhCong = true;
				manHinhChua.dispose();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				MyNotification panel = new MyNotification(chinhDeskstop, MyNotification.Type.WARNING,
						MyNotification.Location.CENTER, "Thêm thất bại, hãy kiểm tra lại kết nối!");
				panel.showNotification();
			}
		}
		if (o.equals(btnChonHinh)) {
			JFileChooser fileChooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Hình ảnh", "png", "jpg", "jpeg");
			fileChooser.setFileFilter(filter);
			int returnValue = fileChooser.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				selectedFile = fileChooser.getSelectedFile();
				lblHinhDV.setIcon(new MyImageIcon(selectedFile.toString(), 360, 270, 0));
				txtPublicIDDV.setText("");
			}
		}
	}

	private boolean thanhCong = false;

	public boolean getThanhCong() {
		return thanhCong;
	}

	private File selectedFile = null;
	private JLabel lblKiemTraHopLe;

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(spnGiaDV)) {
			kiemTraRangBuoc();
		}
	}

	private void kiemTraRangBuoc() {
		if (spnGiaDV.getValue().equals(0)) {
			lblKiemTraHopLe.setText("Số lượng = 0 thì không thể kinh doanh, nhưng vẫn cập nhật được!");
			cboTrangThai.setSelectedItem("Ngừng kinh doanh");
			cboTrangThai.setEnabled(false);
		} else {
			cboTrangThai.setEnabled(true);
		}
	}
}
