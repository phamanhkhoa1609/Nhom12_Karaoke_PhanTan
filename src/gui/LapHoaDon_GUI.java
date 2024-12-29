package gui;


import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;

import connectDB.ConnectDB;
import dao.DatDichVu_DAO;
import dao.DatPhong_DAO;
import dao.KhachHang_DAO;
import dao.LapHoaDon_DAO;
import dao.Phong_DAO;
import entity.DatDichVu;
import entity.DatPhong;
import entity.LapHoaDon;
import entity.Phong;

public class LapHoaDon_GUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel pnContent, pnNorth, pnWest, pnEast, pnThem, pnTable_DDV, pnHoaDon;
	private JLabel lblTenPhong, lblLuongKhach, lblPhieuGiamGia, lblGioTra, lblGioDat, lblTTTienPhong, lblTTTienDV,
			lblTTThue, lblTTGiamGia, lblTTThanhTien, lblHD_NgayThue, lblHD_TTNgayThue, lblHD_KThuc, lblHD_TTKThuc,
			lblTenKH, lblTTTenKH, lblTenPhongHD, lblTTTenPhongHD;
	private JTextField txtLuongKhach, txtPhieuGiamGia;
	private JComboBox<Object> cmbTenPhong;
	private JButton btnCapNhat, btnXoaTrang, btnLapHoaDon, btnPDF;
	private DefaultTableModel model_DDV;
	private JTable table_DDV;
	private JDateChooser dateNgayDat;
	private JComboBox<String> cmbGioDat, cmbPhutDat, cmbGioTra, cmbPhutTra;
	private DatDichVu_DAO ddv;
	private Phong_DAO p;
	private DatPhong_DAO dp;
	private LapHoaDon_DAO lhd;
	private KhachHang_DAO kh;

	Color mauNen = new Color(205, 38, 38);// màu đỏ
	Color mauBang = new Color(99, 184, 255);// màu lam

	/***
	 * Chỉnh sửa tiền tệ
	 */
	Locale localeVN = new Locale("vi", "VN");
	NumberFormat tienTeVN = NumberFormat.getCurrencyInstance(localeVN);

	public LapHoaDon_GUI() throws Exception {
//		setJMenuBar(MenuUtil.createMenu());
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ddv = new DatDichVu_DAO();
		p = new Phong_DAO();
		dp = new DatPhong_DAO();
		lhd = new LapHoaDon_DAO();
		kh = new KhachHang_DAO();
		createGui();
	}

	public void createGui() {
		setTitle("Hóa đơn");
		setSize(1000, 650);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(true);
		setLocationRelativeTo(null);
		lapHoaHon();
	}

	public void lapHoaHon() {
		pnContent = new JPanel();
		pnContent.setLayout(new BorderLayout());
		pnNorth = new JPanel();
		pnContent.add(pnNorth, BorderLayout.NORTH);

		JLabel lblTitle = new JLabel("Lập hóa đơn");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
		lblTitle.setForeground(Color.WHITE);
		pnNorth.add(lblTitle);
		pnNorth.setBackground(mauNen);

		pnWest = new JPanel(new GridLayout(2, 1));
		pnContent.add(pnWest, BorderLayout.WEST);

		pnThem = new JPanel();
		Box b = Box.createVerticalBox();
		Box b1 = Box.createHorizontalBox();
		Box b2 = Box.createHorizontalBox();
		Box b3 = Box.createHorizontalBox();
		Box b4 = Box.createHorizontalBox();
		Box b5 = Box.createHorizontalBox();
		Box b6 = Box.createHorizontalBox();
		Box b7 = Box.createHorizontalBox();

		lblTenPhong = new JLabel("Tên phòng: ");
		cmbTenPhong = new JComboBox<>();
		cmbTenPhong.addItem("");
		b1.add(lblTenPhong);
		b1.add(Box.createHorizontalStrut(50));
		b1.add(cmbTenPhong);

		lblLuongKhach = new JLabel("Lượng khách: ");
		txtLuongKhach = new JTextField(20);
		b2.add(lblLuongKhach);
		b2.add(Box.createHorizontalStrut(40));
		b2.add(txtLuongKhach);

		lblPhieuGiamGia = new JLabel("Phiếu giảm giá(%): ");
		txtPhieuGiamGia = new JTextField();
		b3.add(lblPhieuGiamGia);
		b3.add(Box.createHorizontalStrut(10));
		b3.add(txtPhieuGiamGia);
		txtPhieuGiamGia.setEditable(false);

		cmbPhutDat = new JComboBox<>();
		cmbGioDat = new JComboBox<>();
		cmbPhutTra = new JComboBox<>();
		cmbGioTra = new JComboBox<>();
		for (int i = 0; i <= 60; i++) {
			cmbPhutDat.addItem(Integer.toString(i));
			cmbPhutTra.addItem(Integer.toString(i));
		}
		for (int j = 10; j <= 24; j++) {
			cmbGioDat.addItem(Integer.toString(j));
			cmbGioTra.addItem(Integer.toString(j));
		}

		lblGioDat = new JLabel("Giờ thuê: ");
		b4.add(lblGioDat);
		b4.add(Box.createHorizontalStrut(70));
		b4.add(cmbGioDat);
		JLabel lblHaiCham_Dat = new JLabel(" : ");
		b4.add(lblHaiCham_Dat);
		b4.add(cmbPhutDat);

		dateNgayDat = new JDateChooser();
		dateNgayDat.setBounds(320, 90, 200, 30);
		dateNgayDat.setDateFormatString("yyyy/MM/dd");
		JLabel lblNgayDat = new JLabel("Ngày thuê: ");
		b5.add(lblNgayDat);
		b5.add(Box.createHorizontalStrut(60));
		b5.add(dateNgayDat);

		lblGioTra = new JLabel("Giờ trả: ");
		JLabel lblHaiCham_Tra = new JLabel(" : ");
		b6.add(lblGioTra);
		b6.add(Box.createHorizontalStrut(70));
		b6.add(cmbGioTra);
		b6.add(lblHaiCham_Tra);
		b6.add(cmbPhutTra);

		b.add(b1);
		b.add(Box.createRigidArea(new DimensionUIResource(0, 10)));
		b.add(b2);
		b.add(Box.createRigidArea(new DimensionUIResource(0, 10)));
		b.add(b3);
		b.add(Box.createRigidArea(new DimensionUIResource(0, 10)));
		b.add(b4);
		b.add(Box.createRigidArea(new DimensionUIResource(0, 10)));
		b.add(b5);
		b.add(Box.createRigidArea(new DimensionUIResource(0, 10)));
		b.add(b6);
		b.add(Box.createRigidArea(new DimensionUIResource(0, 10)));
		b.add(b7);
		pnThem.add(b);
		pnWest.add(pnThem);

		btnCapNhat = new JButton("Điền thông tin");
		btnCapNhat.setBackground(mauNen);
		btnCapNhat.setForeground(Color.white);
		btnXoaTrang = new JButton("Xóa trắng");
		btnXoaTrang.setBackground(mauNen);
		btnXoaTrang.setForeground(Color.white);
		b7.add(btnCapNhat);
		b7.add(Box.createHorizontalStrut(10));
		b7.add(btnXoaTrang);

		TitledBorder titledThemTT = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(mauNen),
				"Thông tin hóa đơn");
		titledThemTT.setTitleJustification(TitledBorder.CENTER);
		pnThem.setBorder(titledThemTT);

		pnTable_DDV = new JPanel();
//		bEast3.add(pnTable_DDV);
		pnWest.add(pnTable_DDV);
		createTable_DDV();

		TitledBorder titledDDV = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(mauNen),
				"Danh sách dịch vụ đã sử dụng");
		titledDDV.setTitleJustification(TitledBorder.CENTER);
		pnTable_DDV.setBorder(titledDDV);

		pnEast = new JPanel(new GridLayout(1, 1));
		pnContent.add(pnEast, BorderLayout.EAST);

		pnHoaDon = new JPanel(new GridLayout(11, 2));
		lblTenKH = new JLabel("Tên khách hàng:");
		lblTTTenKH = new JLabel();
		lblTenPhongHD = new JLabel("Tên phòng:");
		lblTTTenPhongHD = new JLabel();
		lblHD_NgayThue = new JLabel("Thời gian thuê:");
		lblHD_TTNgayThue = new JLabel();
		lblHD_KThuc = new JLabel("Thời gian kết thúc:");
		lblHD_TTKThuc = new JLabel();
		JLabel lblTienPhong = new JLabel("Tiền phòng:");
		lblTTTienPhong = new JLabel();
		JLabel lblTienDV = new JLabel("Tiền dịch vụ: ");
		lblTTTienDV = new JLabel();
		JLabel lblThue = new JLabel("Thuế(8%): ");
		lblTTThue = new JLabel();
		JLabel lblGiamGia = new JLabel("Giảm giá: ");
		lblTTGiamGia = new JLabel();
		JLabel lblGach = new JLabel("------------------");
		JLabel lblGach1 = new JLabel("------------------");
		JLabel lblThanhTien = new JLabel("Thành tiền");
		lblTTThanhTien = new JLabel();

		Font newFont = new Font("Arial", Font.BOLD, 12);

		lblTenKH.setFont(newFont);
		lblTTTenKH.setFont(newFont);
		lblTenPhongHD.setFont(newFont);
		lblTTTenPhongHD.setFont(newFont);
		lblHD_NgayThue.setFont(newFont);
		lblHD_TTNgayThue.setFont(newFont);
		lblHD_KThuc.setFont(newFont);
		lblHD_TTKThuc.setFont(newFont);
		lblTienPhong.setFont(newFont);
		lblTTTienPhong.setFont(newFont);
		lblTienDV.setFont(newFont);
		lblTTTienDV.setFont(newFont);
		lblThue.setFont(newFont);
		lblTTThue.setFont(newFont);
		lblGiamGia.setFont(newFont);
		lblTTGiamGia.setFont(newFont);
		lblGach.setFont(newFont);
		lblGach1.setFont(newFont);
		lblThanhTien.setFont(new Font("Arial", Font.BOLD, 22));
		lblTTThanhTien.setFont(new Font("Arial", Font.BOLD, 22));

		btnLapHoaDon = new JButton("Lập hóa đơn");
		btnLapHoaDon.setBackground(mauNen);
		btnLapHoaDon.setForeground(Color.white);
		btnPDF = new JButton("Xuất DPF");
		btnPDF.setBackground(mauNen);
		btnPDF.setForeground(Color.white);

		pnEast.add(pnHoaDon);
		pnHoaDon.add(lblTenKH);
		pnHoaDon.add(Box.createHorizontalStrut(10));
		pnHoaDon.add(lblTTTenKH);
		pnHoaDon.add(lblTenPhongHD);
		pnHoaDon.add(Box.createHorizontalStrut(10));
		pnHoaDon.add(lblTTTenPhongHD);
		pnHoaDon.add(lblHD_NgayThue);
		pnHoaDon.add(Box.createHorizontalStrut(10));
		pnHoaDon.add(lblHD_TTNgayThue);
		pnHoaDon.add(lblHD_KThuc);
		pnHoaDon.add(Box.createHorizontalStrut(10));
		pnHoaDon.add(lblHD_TTKThuc);
		pnHoaDon.add(lblTienPhong);
		pnHoaDon.add(Box.createHorizontalStrut(10));
		pnHoaDon.add(lblTTTienPhong);
		pnHoaDon.add(lblTienDV);
		pnHoaDon.add(Box.createHorizontalStrut(10));
		pnHoaDon.add(lblTTTienDV);
		pnHoaDon.add(lblThue);
		pnHoaDon.add(Box.createHorizontalStrut(10));
		pnHoaDon.add(lblTTThue);
		pnHoaDon.add(lblGiamGia);
		pnHoaDon.add(Box.createHorizontalStrut(10));
		pnHoaDon.add(lblTTGiamGia);
		pnHoaDon.add(lblGach);
		pnHoaDon.add(Box.createHorizontalStrut(10));
		pnHoaDon.add(lblGach1);
		pnHoaDon.add(lblThanhTien);
		pnHoaDon.add(Box.createHorizontalStrut(10));
		pnHoaDon.add(lblTTThanhTien);
		JPanel pnThanhToan = new JPanel();
		pnThanhToan.add(btnLapHoaDon);
		pnHoaDon.add(pnThanhToan);
		pnHoaDon.add(Box.createHorizontalStrut(10));
		JPanel pnPDF = new JPanel();
		pnPDF.add(btnPDF);
		pnHoaDon.add(pnPDF);

		try {
			for (Phong phong : p.getAllPhong()) {
				if (phong.getTinhTrang().equals("Đã đặt"))
					cmbTenPhong.addItem(phong.getTenPhong());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.add(pnContent);
		btnCapNhat.addActionListener(this);
		btnXoaTrang.addActionListener(this);
		btnLapHoaDon.addActionListener(this);
		btnPDF.addActionListener(this);
	}

	public void createTable_DDV() {
		model_DDV = new DefaultTableModel();
		table_DDV = new JTable(model_DDV);
		model_DDV.addColumn("Mã dịch vụ");
		model_DDV.addColumn("Tên dịch vụ");
		model_DDV.addColumn("Số lượng");
		model_DDV.addColumn("Giá($)");
		model_DDV.addColumn("Thành tiền");

		pnTable_DDV.add(table_DDV);
		JScrollPane sp = new JScrollPane(table_DDV, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(550, 250));
		pnTable_DDV.add(sp);

		DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
		headerRenderer.setBackground(mauBang);
		table_DDV.getTableHeader().setDefaultRenderer(headerRenderer);
		headerRenderer.setForeground(Color.WHITE);
		table_DDV.setGridColor(mauNen);

	}

	public static void main(String[] args) throws Exception {
		new LapHoaDon_GUI().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		giamGiaTheoNgay();
		Object o = e.getSource();
		if (o.equals(btnCapNhat)) {
			capNhat();
		} else if (o.equals(btnXoaTrang)) {
			xoaRong();
		} else if (o.equals(btnLapHoaDon)) {
			thucHienLapHoaHon();
		} else if (o.equals(btnPDF)) {
			generatePDF();
		}

	}

	public void capNhat() {
		int numRows = model_DDV.getRowCount();
		for (int i = numRows - 1; i >= 0; i--) {
			model_DDV.removeRow(i);
		}
		if (kTraLoi()) {

			for (DatPhong datPhong : dp.getAllDatPhong()) {
				for (Phong phong : p.getAllPhong()) {
					if (cmbTenPhong.getSelectedItem().toString().equals(datPhong.getTenPhong())
							&& phong.getTinhTrang().equals("Đã đặt")) {
						lblTTTenKH.setText(datPhong.getTenKhachHang());
					}
				}
			}

			lblTTTenPhongHD.setText(cmbTenPhong.getSelectedItem().toString());

			// Lấy thông tin Ngày và thời gian theo định dạng yyyy/MM/dd hh:mm:ss
			String ngayStr = new SimpleDateFormat("yyyy/MM/dd").format(dateNgayDat.getDate());
			int gioDat = Integer.parseInt(cmbGioDat.getSelectedItem().toString());
			int phutDat = Integer.parseInt(cmbPhutDat.getSelectedItem().toString());
			int gioTra = Integer.parseInt(cmbGioTra.getSelectedItem().toString());
			int phutTra = Integer.parseInt(cmbPhutTra.getSelectedItem().toString());

			String tgianThueStr = String.format("%s %02d:%02d:00", ngayStr, gioDat, phutDat);
			String tgianKThucStr = String.format("%s %02d:%02d:00", ngayStr, gioTra, phutTra);

			lblHD_TTNgayThue.setText(tgianThueStr);
			lblHD_TTKThuc.setText(tgianKThucStr);

			boolean coDichVu = false;

			// Xóa phòng đã chọn
			for (Phong phong : p.getAllPhong()) {
				if (cmbTenPhong.getSelectedItem().toString().equals(phong.getTenPhong())) {
//	            	Nếu gioDat là 10:00 sáng và gioTra là 11:00 sáng => gioThue sẽ là 1 giờ.
					int gioThue = gioTra - gioDat;
//	                Nếu phutDat là 0 (10:00 sáng) và phutTra là 15 (11:15 sáng) => phutThue sẽ là 15 phút
					int phutThue = phutTra - phutDat;
//	                Kết quả là số âm => qua một giờ mới (ví dụ: từ 59 phút sang 0 phút)
					if (phutThue < 0) {
//	                	gioThue-- Đã trôi qua 1 giờ
//	                	Ví dụ: 10:20 đến 11:10 thì gioThue=1, phutThue=-10 => thuê được 50p < 1h
						gioThue--;
//					    Chuyển phút từ âm sang dương để thực hiện tính toán
						phutThue += 60;
					}
//					gioThue * phong.getGia(): tính tiền số giừo thuê
//	                phutThue * phong.getGia() / 60: tính tiền số phút thuê
					float tienPhong = (gioThue * phong.getGiaPhong()) + (phutThue * phong.getGiaPhong() / 60);
					lblTTTienPhong.setText(tienTeVN.format(tienPhong));
				}
			}

			for (DatDichVu datDichVu : ddv.getDatDichVuForPhongDaDat(cmbTenPhong.getSelectedItem().toString())) {
				Object[] row = { datDichVu.getMaDichVu(), datDichVu.getTenDichVu(), datDichVu.getSoLuong(),
						tienTeVN.format(datDichVu.getGia()), tienTeVN.format(datDichVu.thanhTien()) };
				model_DDV.addRow(row);

				// Tiền dịch vụ
				coDichVu = true;
				lblTTTienDV.setText(tienTeVN.format(datDichVu.thanhTien()));
			}

			if (!coDichVu) {
				lblTTTienDV.setText(tienTeVN.format(0));
			}
			tinhGiamGiaVaThanhTien();
		}
	}

	public void xoaRong() {
		txtPhieuGiamGia.setText("");
		txtLuongKhach.setText("");
		dateNgayDat.setDateFormatString("");
	}

	public boolean kTraLoi() {
		String tenPhong = cmbTenPhong.getSelectedItem().toString();
		String luongKhach = txtLuongKhach.getText();
		Date ngay = dateNgayDat.getDate();
		int gioDat = Integer.parseInt(cmbGioDat.getSelectedItem().toString());
		int phutDat = Integer.parseInt(cmbPhutDat.getSelectedItem().toString());
		int gioTra = Integer.parseInt(cmbGioTra.getSelectedItem().toString());
		int phutTra = Integer.parseInt(cmbPhutTra.getSelectedItem().toString());

		if (tenPhong.equals("") || luongKhach.equals("") || ngay == null) {
			JOptionPane.showMessageDialog(this, "Tên phòng, lượng khách và ngày không được để trống");
			return false;
		}
		if (gioDat > gioTra || (gioDat == gioTra && phutDat >= phutTra)) {
			JOptionPane.showMessageDialog(this, "Giờ kết thúc phải lớn hơn giờ thuê");
			return false;
		}
		try {
			if (Integer.parseInt(luongKhach) < 2) {
				JOptionPane.showMessageDialog(this, "Lượng khách phải lớn hơn 1");
				txtLuongKhach.requestFocus();
				return false;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Lượng khách phải nhập vào số");
			return false;
		}
		return true;
	}

	/***
	 * Tính tiền giảm giá và thành tiền 
	 * Tiền giảm giá = (tiền phòng + tiền dịch vụ) * phần trăm giảm 
	 * Thuế = (tiền phòng + tiền dịch vụ) * 8% 
	 * Thành tiền = (tiền phòng + tiền dich vụ) - tiền giảm + thuế
	 */
	public void tinhGiamGiaVaThanhTien() {
		try {
			Number chuyenTienPhongSangSo = tienTeVN.parse(lblTTTienPhong.getText());
			double tienPhong = chuyenTienPhongSangSo.doubleValue();

			Number chuyenTienDVSangSo = tienTeVN.parse(lblTTTienDV.getText());
			double tienDichVu = chuyenTienDVSangSo.doubleValue();

			double tongTien = tienPhong + tienDichVu;
			double thue = tongTien * 0.08;
			lblTTThue.setText(tienTeVN.format(thue));

			// Kiểm tra phieuGiamGiaText có giá trị hợp lệ hay không
			if (!txtPhieuGiamGia.getText().isEmpty()) {
				try {
					int phieuGiamGia = Integer.parseInt(txtPhieuGiamGia.getText());
//	                Tính tiền giảm dựa theo phần trăm
					double giamGia = (phieuGiamGia / 100.0) * tongTien;
//	                Tính tổng tiền sau khi áp dụng giảm giá
					double thanhTien = tongTien - giamGia + thue;
					lblTTGiamGia.setText(tienTeVN.format(giamGia));
					lblTTThanhTien.setText(tienTeVN.format(thanhTien));
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(this, "Phần trăm giảm giá không hợp lệ");
				}
			} else {
				lblTTGiamGia.setText(tienTeVN.format(0));
				double thanhTien = tienPhong + tienDichVu + thue;
				lblTTThanhTien.setText(tienTeVN.format(thanhTien));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/***
	 * Lập hóa đơn
	 */
	public void thucHienLapHoaHon() {
		try {
			String maHD = "";
			String maPhong = "";
			String maKH = "";
			String maNV = "";

			// Lấy giá trị từ các nhãn hiển thị
			String ngayThueText = lblHD_TTNgayThue.getText();
			String ngayKetThucText = lblHD_TTKThuc.getText();

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime tgianThue = LocalDateTime.parse(ngayThueText, formatter);
			LocalDateTime tgianKThuc = LocalDateTime.parse(ngayKetThucText, formatter);

			for (DatPhong datPhong : dp.getAllDatPhong()) {
				for (Phong phong : p.getAllPhong()) {
//					for (LapHoaDon lapHoaDon : lhd.getAllLapHoaDon()) {
					if (cmbTenPhong.getSelectedItem().toString().equals(datPhong.getTenPhong())
							&& phong.getTinhTrang().equals("Đã đặt")) {
						maPhong = datPhong.getMaPhong();
						maHD = datPhong.getMaHoaDon();
						maKH = datPhong.getMaKhachHang();
						maNV = datPhong.getMaNhanVien();
					}
//					}
				}
			}

			Number chuyenTienPhongSangSo = tienTeVN.parse(lblTTThanhTien.getText());
			float thanhTien = chuyenTienPhongSangSo.floatValue();

			LapHoaDon lapHoaDon = new LapHoaDon(maHD, maKH, maPhong, maNV, cmbTenPhong.getSelectedItem().toString(),
					Integer.parseInt(txtPhieuGiamGia.getText()), Integer.parseInt(txtLuongKhach.getText()), tgianThue,
					tgianKThuc, 1, thanhTien);
			lhd.lapHoaDon(lapHoaDon);
			p.updatePhongDaDatSangPhongTrong(maPhong);
			kh.updateKhachHangSangDaThanhToan(maKH);
			cmbTenPhong.removeItem(cmbTenPhong.getSelectedItem().toString());
			cmbTenPhong.setSelectedIndex(0);
			JOptionPane.showMessageDialog(this, "Lập hóa đơn thành công");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/***
	 * Giảm giá theo ngày
	 */
	public void giamGiaTheoNgay() {
	    try {
	        Date ngayDatDate = dateNgayDat.getDate();
	        LocalDate ngayDat = ngayDatDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

	        int soNgay = ngayDat.getDayOfMonth();
	        
	        if (soNgay >= 1 && soNgay <= 10) {
	            // Giảm giá 5%
	            txtPhieuGiamGia.setText("5");
	        } else if (soNgay > 10 && soNgay <= 20) {
	            // Không giảm giá
	            txtPhieuGiamGia.setText("0");
	        } else if (soNgay > 20 && soNgay <= 31) {
	            // Giảm giá 10%
	            txtPhieuGiamGia.setText("10");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	/***
	 * Xuất file PDF
	 */
	public void generatePDF() {
	    try {
	        Document document = new Document();
	        String filePath = "HoaDon.pdf";
	        PdfWriter.getInstance(document, new FileOutputStream(filePath));
	        document.open();

	        // Thêm content của PDF
	        BaseFont unicodeFont = BaseFont.createFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            com.itextpdf.text.Font font = new com.itextpdf.text.Font(unicodeFont, 12);
            
	        document.add(new Paragraph("PHIẾU THANH TOÁN KARAOKE NICE", font));
	        document.add(new Paragraph("-----------------------------------------------------"));
	        document.add(new Paragraph("Tên khách hàng: 	" + lblTTTenKH.getText(), font));
	        document.add(new Paragraph("Tên phòng: 			" + lblTTTenPhongHD.getText(), font));
	        document.add(new Paragraph("Thời gian thuê: 	" + lblHD_TTNgayThue.getText(), font));
	        document.add(new Paragraph("Thời gian kết thúc: " + lblHD_TTKThuc.getText(), font));
	        document.add(new Paragraph("Giá phòng: 			" + lblTTTienPhong.getText(), font));
	        document.add(new Paragraph("Dịch vụ sử dụng: 	" + lblTTTienDV.getText(), font));
	        document.add(new Paragraph("Giảm giá(%): 		" + lblTTGiamGia.getText(), font));
	        document.add(new Paragraph("Thuế: 				" + lblTTThue.getText(), font));
	        document.add(new Paragraph("-----------------------------------------------------"));
            
	        com.itextpdf.text.Font boldFont = new com.itextpdf.text.Font(unicodeFont, 16, com.itextpdf.text.Font.BOLD);
	        document.add(new Paragraph("Tổng cộng thanh toán: " + lblTTThanhTien.getText(), boldFont));
	        // Đóng tài liệu
	        document.close();

	        // Mở file PDF đã tạo
	        Desktop.getDesktop().open(new File(filePath));

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


}
