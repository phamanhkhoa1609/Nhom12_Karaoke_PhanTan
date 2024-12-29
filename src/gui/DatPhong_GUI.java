package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
import dao.DatPhong_DAO;
import dao.KhachHang_DAO;
import dao.NhanVien_DAO;
import dao.Phong_DAO;
import entity.DatPhong;
import entity.KhachHang;
import entity.NhanVien;
import entity.Phong;

public class DatPhong_GUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JButton btnThem, btnXoa, btnXoaTrang, btnCapNhat, btnLuu, btnThoat;
	private JLabel lblMaHD, lblTenPhong, lblMaKH, lblTenNV, lblThoiGianDat, lblSoLuongKhach, lblGhiChu;
	private JTextField txtHoaDon, txtGhiChu, txtSoLuong;
	private JComboBox<Object> cmbTenPhong, cmbMaKH, cmbTenNhanVien;
	private JPanel pnContent, pnWest, pnNorth, pnDSPhongDat, pnThem, pnChucNang, pnEast, pnDSPhong, pnDSKH;
	private JDateChooser dateNgayDat;
	private JComboBox<String> cmbGioDat, cmbPhutDat;
	private NhanVien_DAO nv;
	private Phong_DAO p;
	private KhachHang_DAO kh;
	private DatPhong_DAO dp;

	Color mauNen = new Color(205, 38, 38); // màu đỏ
	Color mauBang = new Color(99, 184, 255); // màu lam
	private DefaultTableModel model_DSPhong, model_DSKH, model_DSPhongDat;
	private JTable table_DSPhong, table_DSKH, table_DSPhongDat;

	/***
	 * Chỉnh sửa tiền tệ
	 */
	Locale localeVN = new Locale("vi", "VN");
	NumberFormat tienTeVN = NumberFormat.getCurrencyInstance(localeVN);
	DateTimeFormatter dft = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

	private int taoIDHoaDon = 0;

	public DatPhong_GUI() throws Exception {
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		nv = new NhanVien_DAO();
		p = new Phong_DAO();
		kh = new KhachHang_DAO();
		dp = new DatPhong_DAO();
		createGui();
		
	}

	public void createGui() {
		setTitle("Đặt phòng");
		setSize(1200, 700);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(true);
		setLocationRelativeTo(null);
		datPhong();
	}

	public void datPhong() {
		pnContent = new JPanel();
		pnContent.setLayout(new BorderLayout());

		pnNorth = new JPanel();
		pnContent.add(pnNorth, BorderLayout.NORTH);

		JLabel lblTitle = new JLabel("Đặt phòng");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
		lblTitle.setForeground(Color.WHITE);
		pnNorth.add(lblTitle);
		pnNorth.setBackground(mauNen);

		pnWest = new JPanel(new GridLayout(2, 1));
		pnContent.add(pnWest, BorderLayout.WEST);
		pnThem = new JPanel();
		pnWest.add(pnThem);
		Box b = Box.createVerticalBox();
		Box b1 = Box.createHorizontalBox();
		Box b2 = Box.createHorizontalBox();
		Box b3 = Box.createHorizontalBox();
		Box b4 = Box.createHorizontalBox();
		Box b5 = Box.createHorizontalBox();
		Box b6 = Box.createHorizontalBox();
		Box b7 = Box.createHorizontalBox();

		TitledBorder titledTTPhong = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(mauNen),
				"Thông tin đặt phòng");
		titledTTPhong.setTitleJustification(TitledBorder.CENTER);
		pnThem.setBorder(titledTTPhong);

		lblMaHD = new JLabel("Mã hóa đơn: ");
		txtHoaDon = new JTextField();
		lblTenPhong = new JLabel("Tên phòng: ");
		cmbTenPhong = new JComboBox<>();

		for (Phong phong : p.getAllPhong()) {
			if (phong.getTinhTrang().equals("Trống")) {
				cmbTenPhong.addItem(phong.getTenPhong());
			}
		}

		lblMaKH = new JLabel("Mã khách hàng: ");
		cmbMaKH = new JComboBox<>();

		try {
			for (KhachHang khachHang : kh.getAllKhachHang()) {
				if (khachHang.getTrangThai().equals("Chưa thanh toán")) {
					cmbMaKH.addItem(khachHang.getMaKH());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		lblTenNV = new JLabel("Tên nhân viên: ");
		cmbTenNhanVien = new JComboBox<>();
		for (NhanVien nhanVien : nv.getAllNhanVien()) {
			if (nhanVien.getBoPhan().equals("Nhân viên quản lý") || nhanVien.getBoPhan().equals("Nhân viên tiếp tân"))
				cmbTenNhanVien.addItem(nhanVien.getTenNV());
		}
		lblThoiGianDat = new JLabel("Thời gian đặt: ");
		cmbPhutDat = new JComboBox<>();
		cmbGioDat = new JComboBox<>();
		for (int i = 1; i <= 60; i++) {
			cmbPhutDat.addItem(Integer.toString(i));
		}
		for (int j = 10; j <= 24; j++) {
			cmbGioDat.addItem(Integer.toString(j));
		}
		dateNgayDat = new JDateChooser();
		dateNgayDat.setBounds(320, 90, 200, 30);
		dateNgayDat.setDateFormatString("dd/MM/yyyy");
//		formatter = DateTimeFormatter.ofPattern("ss:mm:HH dd/MM/yyyy");
//		now = LocalDateTime.now();
//		String formattedDateTime = now.format(formatter);
//		txtThoiGianDat.setText(formattedDateTime);
		lblSoLuongKhach = new JLabel("Số lượng khách hàng: ");
		txtSoLuong = new JTextField();
		lblGhiChu = new JLabel("Ghi chú: ");
		txtGhiChu = new JTextField();

//		b1.add(lblMaHD);
//		b1.add(Box.createHorizontalStrut(27));
//		b1.add(txtHoaDon);

		b2.add(lblTenPhong);
		b2.add(Box.createHorizontalStrut(35));
		b2.add(cmbTenPhong);

		b3.add(lblMaKH);
		b3.add(Box.createHorizontalStrut(5));
		b3.add(cmbMaKH);

		b4.add(lblTenNV);
		b4.add(Box.createHorizontalStrut(12));
		b4.add(cmbTenNhanVien);

		b5.add(lblThoiGianDat);
		b5.add(Box.createHorizontalStrut(20));
		b5.add(dateNgayDat);
		b5.add(Box.createHorizontalStrut(70));
		b5.add(cmbGioDat);
		JLabel lblHaiCham_Dat = new JLabel(" : ");
		b5.add(lblHaiCham_Dat);
		b5.add(cmbPhutDat);

		b6.add(lblSoLuongKhach);
		b6.add(Box.createHorizontalStrut(10));
		b6.add(txtSoLuong);

		b7.add(lblGhiChu);
		b7.add(Box.createHorizontalStrut(52));
		b7.add(txtGhiChu);

		b.add(b1);
		b.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
		b.add(b2);
		b.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
		b.add(b3);
		b.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
		b.add(b4);
		b.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
		b.add(b5);
		b.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
		b.add(b6);
		b.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
		b.add(b7);
		pnThem.add(b);

		btnThem = new JButton("Thêm");
		btnThem.setBackground(mauNen);
		btnThem.setForeground(Color.WHITE);
		btnXoa = new JButton("Xóa");
		btnXoa.setBackground(mauNen);
		btnXoa.setForeground(Color.WHITE);
		btnXoaTrang = new JButton("Xóa trắng");
		btnXoaTrang.setBackground(mauNen);
		btnXoaTrang.setForeground(Color.white);
		btnCapNhat = new JButton("Cập nhật");
		btnCapNhat.setBackground(mauNen);
		btnCapNhat.setForeground(Color.white);
		btnLuu = new JButton("Lưu");
		btnLuu.setBackground(mauNen);
		btnLuu.setForeground(Color.white);
		btnThoat = new JButton("Thoát");
		btnThoat.setBackground(mauNen);
		btnThoat.setForeground(Color.white);

		pnChucNang = new JPanel();
		TitledBorder titleChucNang = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(mauNen),
				"Chức năng");
		titleChucNang.setTitleJustification(TitledBorder.CENTER);
		pnChucNang.setBorder(titleChucNang);

		Box bChucNang = Box.createVerticalBox();
		Box bDong1 = Box.createHorizontalBox();
		Box bDong2 = Box.createHorizontalBox();
		pnWest.add(pnChucNang);
		pnChucNang.add(bChucNang);
		bChucNang.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
		bChucNang.add(bDong1);
		bChucNang.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
		bChucNang.add(bDong2);
		bDong1.add(Box.createHorizontalStrut(20));
		bDong1.add(btnThem);
		bDong1.add(Box.createHorizontalStrut(20));
		bDong1.add(btnXoa);
		bDong1.add(Box.createHorizontalStrut(20));
		bDong1.add(btnXoaTrang);
		bDong1.add(Box.createHorizontalStrut(20));
		bDong2.add(btnCapNhat);
		bDong2.add(Box.createHorizontalStrut(20));
		bDong2.add(btnLuu);
		bDong2.add(Box.createHorizontalStrut(20));
		bDong2.add(btnThoat);

		pnEast = new JPanel();
		pnContent.add(pnEast, BorderLayout.EAST);
		Box bEast = Box.createVerticalBox();
		Box bEast1 = Box.createHorizontalBox();
		Box bEast2 = Box.createHorizontalBox();
		Box bEast3 = Box.createHorizontalBox();
		pnEast.add(bEast);
		bEast.add(bEast1);
		bEast.add(bEast2);
		bEast.add(bEast3);

		pnDSPhong = new JPanel();
		bEast1.add(pnDSPhong);
		TitledBorder titledDSPhong = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(mauNen),
				"Danh sách phòng");
		titledDSPhong.setTitleJustification(TitledBorder.CENTER);
		pnDSPhong.setBorder(titledDSPhong);
		createTable_DSP();

		pnDSKH = new JPanel();
		bEast2.add(pnDSKH);
		TitledBorder titledDSKH = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(mauNen),
				"Danh sách khách hàng");
		titledDSKH.setTitleJustification(TitledBorder.CENTER);
		pnDSKH.setBorder(titledDSKH);
		createTable_DSKH();

		pnDSPhongDat = new JPanel();
		bEast3.add(pnDSPhongDat);
		TitledBorder titledDSPhongDat = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(mauNen),
				"Danh sách phòng đã đặt");
		titledDSPhongDat.setTitleJustification(TitledBorder.CENTER);
		pnDSPhongDat.setBorder(titledDSPhongDat);
		createTable_DSPDat();

		this.add(pnContent);

		btnThem.addActionListener(this);
		btnXoa.addActionListener(this);
		btnXoaTrang.addActionListener(this);
		btnCapNhat.addActionListener(this);
		btnLuu.addActionListener(this);
		btnThoat.addActionListener(this);

		khoaTxt();
	}

	public void createTable_DSP() {
		model_DSPhong = new DefaultTableModel();
		table_DSPhong = new JTable(model_DSPhong);
		model_DSPhong.addColumn("IDPhong");
		model_DSPhong.addColumn("Tên phòng");
		model_DSPhong.addColumn("Loại phòng");
		model_DSPhong.addColumn("Thiết bị");
		model_DSPhong.addColumn("Tình trạng");
		model_DSPhong.addColumn("Ghi chú");
		model_DSPhong.addColumn("Giá 1h/ca");

		try {
			for (Phong phong : p.getAllPhong()) {
				if (phong.getTinhTrang().equals("Trống")) {
					Object[] row = { phong.getMaPhong(), phong.getTenPhong(), phong.getLoaiPhong(), phong.getThietBi(),
							phong.getTinhTrang(), phong.getGhiChu(), tienTeVN.format(phong.getGiaPhong()) };
					model_DSPhong.addRow(row);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		pnDSPhong.add(table_DSPhong);
		JScrollPane sp = new JScrollPane(table_DSPhong, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(750, 150));
		pnDSPhong.add(sp);

		DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
		headerRenderer.setBackground(mauBang);
		table_DSPhong.getTableHeader().setDefaultRenderer(headerRenderer);
		headerRenderer.setForeground(Color.WHITE);
		table_DSPhong.setGridColor(mauNen);
	}

	public void createTable_DSKH() {
		model_DSKH = new DefaultTableModel();
		table_DSKH = new JTable(model_DSKH);
		model_DSKH.addColumn("IDKhachHang");
		model_DSKH.addColumn("Tên khách hàng");
		model_DSKH.addColumn("Tuổi");
		model_DSKH.addColumn("Giới tính");
		model_DSKH.addColumn("SDT");
		model_DSKH.addColumn("Trạng thái");

		try {
			for (KhachHang khachHang : kh.getAllKhachHang()) {
				if (khachHang.getTrangThai().equals("Chưa thanh toán")) {
					Object[] row = { khachHang.getMaKH(), khachHang.getTenKH(), khachHang.getTuoi(),
							khachHang.getGioiTinh(), khachHang.getsDT(), khachHang.getTrangThai() };
					model_DSKH.addRow(row);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		pnDSKH.add(table_DSKH);
		JScrollPane sp = new JScrollPane(table_DSKH, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(750, 150));
		pnDSKH.add(sp);

		DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
		headerRenderer.setBackground(mauBang);
		table_DSKH.getTableHeader().setDefaultRenderer(headerRenderer);
		headerRenderer.setForeground(Color.WHITE);
		table_DSKH.setGridColor(mauNen);
	}

	public void createTable_DSPDat() {
		model_DSPhongDat = new DefaultTableModel();
		table_DSPhongDat = new JTable(model_DSPhongDat);
		model_DSPhongDat.addColumn("IDHoaDon");
		model_DSPhongDat.addColumn("Tên phòng");
		model_DSPhongDat.addColumn("IDKhachHang");
		model_DSPhongDat.addColumn("Tên khách hàng");
		model_DSPhongDat.addColumn("Thời gian đặt");
		model_DSPhongDat.addColumn("Số lượng khách");
		model_DSPhongDat.addColumn("Ghi chú");
		try {
			for (DatPhong datPhong : dp.getAllDatPhong()) {
				Object[] row = { datPhong.getMaHoaDon(), datPhong.getTenPhong(), datPhong.getMaKhachHang(),
						datPhong.getTenKhachHang(), dft.format(datPhong.getTgianDat()), datPhong.getSoLuongKhach(),
						datPhong.getGhiChu() };
				model_DSPhongDat.addRow(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		pnDSPhongDat.add(table_DSPhongDat);
		JScrollPane sp = new JScrollPane(table_DSPhongDat, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(750, 200));
		pnDSPhongDat.add(sp);

		DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
		headerRenderer.setBackground(mauBang);
		table_DSPhongDat.getTableHeader().setDefaultRenderer(headerRenderer);
		headerRenderer.setForeground(Color.WHITE);
		table_DSPhongDat.setGridColor(mauNen);

		table_DSPhongDat.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				int row = table_DSPhongDat.getSelectedRow();
				txtHoaDon.setText(model_DSPhongDat.getValueAt(row, 0).toString());

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

	public static void main(String[] args) throws Exception {
		new DatPhong_GUI().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnThem)) {
			if (btnThem.getText().equalsIgnoreCase("Thêm")) {
				moKhoaTxt();
				btnThem.setText("Hủy");
				btnLuu.setEnabled(true);
				btnCapNhat.setEnabled(false);
				btnXoa.setEnabled(false);
				btnXoaTrang.setEnabled(false);
			} else {
				khoaTxt();
				btnThem.setText("Thêm");
				btnLuu.setEnabled(false);
				btnCapNhat.setEnabled(true);
				btnXoa.setEnabled(true);
				btnXoaTrang.setEnabled(true);
			}
			taoIDHoaDon = layIDHoaDonMax() + 1;
			tuTaoIDHoaDon();
		} else if (o.equals(btnXoa)) {
			try {
				xoa();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else if (o.equals(btnXoaTrang)) {
			xoaRong();
		} else if (o.equals(btnLuu)) {
			int row = table_DSPhongDat.getSelectedRow();
			String tenKH = "";
			String idPhong = "";
//			Lấy ngày và giờ
			String ngayStr = new SimpleDateFormat("yyyy/MM/dd").format(dateNgayDat.getDate());
			int gioDat = Integer.parseInt(cmbGioDat.getSelectedItem().toString());
			int phutDat = Integer.parseInt(cmbPhutDat.getSelectedItem().toString());
			String tgianStr = String.format("%s %02d:%02d:00", ngayStr, gioDat, phutDat);

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime tgianDat = LocalDateTime.parse(tgianStr, formatter);

//			Lấy tên khách hàng từ mã khách hàng
			for (KhachHang khachHang : kh.getAllKhachHang()) {
				if (cmbMaKH.getSelectedItem().toString().equals(khachHang.getMaKH()))
					tenKH = khachHang.getTenKH();
			}
			for (Phong phong : p.getAllPhong()) {
				if (cmbTenPhong.getSelectedItem().toString().equals(phong.getTenPhong()))
					idPhong = phong.getMaPhong();
			}
			if (btnThem.getText().equals("Hủy")) {
				if (kTraLoi() == true) {
					JOptionPane.showMessageDialog(this, "Lưu thành công!");
					DatPhong datPhong = new DatPhong(txtHoaDon.getText(), idPhong,
							cmbTenPhong.getSelectedItem().toString(), cmbMaKH.getSelectedItem().toString(), tenKH,
							"NV003", tgianDat, Integer.parseInt(txtSoLuong.getText()), txtGhiChu.getText());
					dp.addDatPhong(datPhong);
					String[] data = { txtHoaDon.getText(), cmbTenPhong.getSelectedItem().toString(),
							cmbMaKH.getSelectedItem().toString(), tenKH, tgianStr, txtSoLuong.getText(),
							txtGhiChu.getText() };
					model_DSPhongDat.addRow(data);
					reset();

					/***
					 * Cập nhật lại phòng trống
					 */
					
					p.updatePhongTrongSangDaDat(idPhong);
					model_DSPhong.setRowCount(0);
					for (Phong phong : p.getAllPhong()) {
						if (phong.getTinhTrang().equals("Trống")) {
							Object[] row1 = { phong.getMaPhong(), phong.getTenPhong(), phong.getLoaiPhong(),
									phong.getThietBi(), phong.getTinhTrang(), phong.getGhiChu(),
									tienTeVN.format(phong.getGiaPhong()) };
							model_DSPhong.addRow(row1);
						}
					}
				}
			} else if (btnCapNhat.getText().equals("Hủy")) {
				if (kTraLoi() == true) {
					String tenPhong = (String) cmbTenPhong.getSelectedItem();
					String IDKhachHang = (String) cmbMaKH.getSelectedItem();
					String tenKhachHang = "";
					int soLuongKhach = Integer.parseInt(txtSoLuong.getText());
					String ngayStr1 = new SimpleDateFormat("yyyy/MM/dd").format(dateNgayDat.getDate());
					int gioDat1 = Integer.parseInt(cmbGioDat.getSelectedItem().toString());
					int phutDat1 = Integer.parseInt(cmbPhutDat.getSelectedItem().toString());
					String tgianStr1 = String.format("%s %02d:%02d:00", ngayStr, gioDat, phutDat);
					DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
					LocalDateTime tgianDat1 = LocalDateTime.parse(tgianStr, formatter);
					String ghiChu = txtGhiChu.getText();
					String hoaDon = txtHoaDon.getText();
					String IDPhong, maNhanVien = "";

					try {
						for (KhachHang khachHang : kh.getAllKhachHang()) {
							if (IDKhachHang.equals(khachHang.getMaKH())) {
								cmbMaKH.addItem(khachHang.getMaKH());
								tenKhachHang = khachHang.getTenKH();
							}
						}
						for (Phong phong : p.getAllPhong()) {
							if (tenPhong.equals(phong.getTenPhong())) {
								idPhong = phong.getMaPhong();
							}
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					DatPhong datPhong = new DatPhong(hoaDon, idPhong, tenPhong, IDKhachHang, tenKhachHang, maNhanVien,
							tgianDat1, soLuongKhach, ghiChu);
					String[] data = { txtHoaDon.getText(), tenPhong, IDKhachHang, tenKhachHang, tgianStr1,
							String.valueOf(soLuongKhach), ghiChu };
					model_DSPhongDat.insertRow(row, data);
					model_DSPhongDat.removeRow(row + 1);
					dp.updateDatPhong(datPhong);
					JOptionPane.showMessageDialog(this, "Lưu thành công");
					reset();
					

					/***
					 * Cập nhật lại phòng trống
					 */
					String idPhongCu = "";
					int selectedRow = table_DSPhongDat.getSelectedRow();
					String tenPhongCu =table_DSPhongDat.getValueAt(selectedRow, 2).toString();
					for(Phong phong:p.getAllPhong()) {
						if(tenPhongCu.equals(phong.getTenPhong()))
							idPhongCu = phong.getMaPhong();
					}
					p.updatePhongDaDatSangPhongTrong(idPhongCu);
					p.updatePhongTrongSangDaDat(idPhong);
					model_DSPhong.setRowCount(0);
					for (Phong phong : p.getAllPhong()) {
						if (phong.getTinhTrang().equals("Trống")) {
							Object[] row1 = { phong.getMaPhong(), phong.getTenPhong(), phong.getLoaiPhong(),
									phong.getThietBi(), phong.getTinhTrang(), phong.getGhiChu(),
									tienTeVN.format(phong.getGiaPhong()) };
							model_DSPhong.addRow(row1);
						}
					}
				}

			}
		} else if (o.equals(btnCapNhat)) {
			int row1 = table_DSPhongDat.getSelectedRow();
			if (row1 != -1) {
				if (btnCapNhat.getText().equals("Sửa")) {
					btnCapNhat.setText("Hủy");
					moKhoaTxt();
					btnLuu.setEnabled(true);
					btnThem.setEnabled(false);
					btnXoa.setEnabled(false);

					dateNgayDat.setEnabled(true);
					cmbPhutDat.setEditable(true);
					cmbGioDat.setEditable(true);
					txtSoLuong.setEditable(true);
					txtGhiChu.setEditable(true);
					cmbMaKH.setEnabled(false);
					cmbTenNhanVien.setEnabled(false);
				} else {
					khoaTxt();
					btnCapNhat.setText("Sửa");
					btnLuu.setEnabled(false);
					btnThem.setEnabled(true);
					btnXoa.setEnabled(true);
				}
			} else {
				JOptionPane.showMessageDialog(this, "Bạn chưa chọn dòng để sửa !");
			}
		} else if (o.equals(btnThoat)) {
			dispose();
		}
	}

	public boolean kTraLoi() {
		String luongKhach = txtSoLuong.getText();
		Date ngay = dateNgayDat.getDate();
//		Date ngay = dateNgayDat.getDateFormatString();
		if (luongKhach.equals("") || ngay == null) {
			JOptionPane.showMessageDialog(this, "Lượng khách và ngày không được để trống");
			return false;
		}
		try {
			if (Integer.parseInt(luongKhach) < 2) {
				JOptionPane.showMessageDialog(this, "Lượng khách phải lớn hơn 1");
				txtSoLuong.requestFocus();
				return false;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Lượng khách phải nhập vào số");
			return false;
		}
		return true;
	}

	public void moKhoaTxt() {
		if (btnCapNhat.getText().equals("Hủy"))
			txtHoaDon.setEditable(false);
		else {
			cmbTenPhong.setEditable(true);
			cmbMaKH.setEditable(true);
			cmbTenNhanVien.setEditable(true);
			dateNgayDat.setEnabled(true);
			cmbPhutDat.setEditable(true);
			cmbGioDat.setEditable(true);
			txtSoLuong.setEditable(true);
			txtGhiChu.setEditable(true);
		}
	}

	public void khoaTxt() {
		txtHoaDon.setEditable(false);
		cmbTenPhong.setEditable(false);
		cmbMaKH.setEditable(false);
		cmbTenNhanVien.setEditable(false);
		dateNgayDat.setEnabled(false);
		cmbPhutDat.setEditable(false);
		cmbGioDat.setEditable(false);
		txtSoLuong.setEditable(false);
		txtGhiChu.setEditable(false);
	}

	public void xoaRong() {
		cmbTenPhong.setSelectedItem(null);
		cmbMaKH.setSelectedItem(null);
		cmbTenNhanVien.setSelectedItem(null);
		dateNgayDat.setCalendar(null);
		cmbGioDat.setSelectedItem(null);
		cmbPhutDat.setSelectedItem(null);
		txtSoLuong.setText("");
		txtGhiChu.setText("");
	}

	public void reset() {
		khoaTxt();

		if (btnThem.getText().equals("Hủy")) {
			btnThem.setText("Thêm");
			btnLuu.setEnabled(false);
			btnCapNhat.setEnabled(true);
			btnXoa.setEnabled(true);
			btnXoaTrang.setEnabled(true);
		}

		if (btnCapNhat.getText().equals("Hủy")) {
			btnCapNhat.setText("Sửa");
			btnLuu.setEnabled(false);
			btnThem.setEnabled(true);
			btnXoa.setEnabled(true);
			btnXoaTrang.setEnabled(true);
		}
	}

	public void xoa() throws Exception {
		int r = table_DSPhongDat.getSelectedRow();
		if (r != -1) {
			int tb = JOptionPane.showConfirmDialog(null, "Bạn có chắc xóa phòng đã đặt này không!", "delete",
					JOptionPane.YES_NO_CANCEL_OPTION);
			if (tb == JOptionPane.YES_OPTION) {
				String maHD = txtHoaDon.getText();
				dp.deleteDatPhong(maHD);
				model_DSPhongDat.removeRow(r);
			}
		} else
			JOptionPane.showMessageDialog(null, "bạn chưa chọn dòng muốn xóa");
	}

	private void tuTaoIDHoaDon() {
		String hoaDonID;
		boolean isDuplicate = false;
		do {
			// Tạo mã HDxxxxxx, trong đó x là số từ 0 đến 9
			hoaDonID = "HD" + String.format("%06d", taoIDHoaDon);

			// Kiểm tra xem mã đã tồn tại trong danh sách hóa đơn chưa
			isDuplicate = isHoaDonIDDuplicate(hoaDonID);

			// Nếu mã đã tồn tại, tăng currentHoaDonIndex lên
			if (isDuplicate) {
				taoIDHoaDon++;
			}
		} while (isDuplicate);

		// Đặt mã đã tạo vào txtIDHoaDon
		txtHoaDon.setText(hoaDonID);

		// Tăng giá trị biến currentHoaDonIndex để chuẩn bị cho lần tạo tiếp theo
		taoIDHoaDon++;
	}

	private boolean isHoaDonIDDuplicate(String hoaDonID) {
		// Lặp qua danh sách hóa đơn và kiểm tra xem mã đã tồn tại hay chưa
		for (int i = 0; i < model_DSPhongDat.getRowCount(); i++) {
			String existingID = (String) model_DSPhongDat.getValueAt(i, 0);
			if (hoaDonID.equals(existingID)) {
				return true; // Mã đã tồn tại
			}
		}

		return false; // Mã không trùng lặp
	}

	// Hàm để lấy ID hóa đơn lớn nhất từ danh sách hóa đơn hiện có
	private int layIDHoaDonMax() {
		int maxIndex = 0;
//		for (int i = 0; i < model_DSPhongDat.getRowCount(); i++) {
//			String hoaDonID = (String) model_DSPhongDat.getValueAt(i, 0);
		for (DatPhong datPhong : dp.getIDHoaDon()) {
			if (datPhong.getMaHoaDon().startsWith("HD")) {
				try {
					// Tách "HD" ra khỏi mã hóa đơn và chuyển phần còn lại thành số
					int index = Integer.parseInt(datPhong.getMaHoaDon().substring(2));
					if (index > maxIndex) {
						maxIndex = index;
					}
				} catch (NumberFormatException e) {
					// Xử lý ngoại lệ nếu có mã hóa đơn không đúng định dạng
					e.printStackTrace();
				}
			}
		}
		return maxIndex;
	}
}
