package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
import javax.swing.border.TitledBorder;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import connectDB.ConnectDB;
import dao.DatDichVu_DAO;
import dao.DichVu_DAO;
import dao.LapHoaDon_DAO;
import entity.DatDichVu;
import entity.DichVu;
import entity.LapHoaDon;

public class ThongKeDichVu_GUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel pnContent, pnNorth, pnWest, pnEast, pnThongKe, pnThongKeKhac, pnTable, pnTongTien;
	private JLabel lblTuNgay, lblDenNgay, lblTenDichVu, lblTongSoTien;
	private JComboBox<String> cmbTenDichVu;
	private DefaultTableModel model;
	private JTable table;
	private JButton btnThongKe, btnThoat, btnHoaDon, btnPhong, btnKhachHang;
	private JDateChooser dateTuNgay, dateDenNgay;
	private DichVu_DAO dv;
	private LapHoaDon_DAO lhd;
	private DatDichVu_DAO ddv;

	Color mauNen = new Color(205, 38, 38); // màu đỏ
	Color mauBang = new Color(99, 184, 255); // màu lam

	Locale localeVN = new Locale("vi", "VN");
	NumberFormat tienTeVN = NumberFormat.getCurrencyInstance(localeVN);
	DateTimeFormatter dft = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

	public ThongKeDichVu_GUI() throws Exception {
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dv = new DichVu_DAO();
		lhd = new LapHoaDon_DAO();
		ddv = new DatDichVu_DAO();
		createGui();
	}

	public void createGui() {
		setTitle("Thống kê dịch vụ");
		setSize(1200, 500);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(true);
		setLocationRelativeTo(null);

		thongKePhong();
	}

	public void thongKePhong() {
		pnContent = new JPanel();
		pnContent.setLayout(new BorderLayout());

		pnNorth = new JPanel();
		pnContent.add(pnNorth, BorderLayout.NORTH);

		JLabel lblTitle = new JLabel("Thống kê dịch vụ");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
		lblTitle.setForeground(Color.WHITE);
		pnNorth.add(lblTitle);
		pnNorth.setBackground(mauNen);

		pnWest = new JPanel(new GridLayout(2, 1));
		pnContent.add(pnWest, BorderLayout.WEST);

		pnThongKe = new JPanel();
		TitledBorder titledThongKe = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(mauNen),
				"Danh mục thống kê");
		titledThongKe.setTitleJustification(TitledBorder.CENTER);
		pnThongKe.setBorder(titledThongKe);

		Box b = Box.createVerticalBox();
		Box b1 = Box.createHorizontalBox();
		Box b2 = Box.createHorizontalBox();
		Box b3 = Box.createHorizontalBox();
		Box b4 = Box.createHorizontalBox();

		lblTuNgay = new JLabel("Từ Ngày: ");
		dateTuNgay = new JDateChooser();
		dateTuNgay.setBounds(320, 90, 200, 30);
		dateTuNgay.setDateFormatString("dd/MM/yyy");
		b1.add(lblTuNgay);
		b1.add(Box.createHorizontalStrut(28));
		b1.add(dateTuNgay);

		lblDenNgay = new JLabel("Đến Ngày: ");
		dateDenNgay = new JDateChooser();
		dateDenNgay.setBounds(320, 90, 200, 30);
		dateDenNgay.setDateFormatString("dd/MM/yyyy");
		b2.add(lblDenNgay);
		b2.add(Box.createHorizontalStrut(20));
		b2.add(dateDenNgay);

//		lblTenDichVu = new JLabel("Tên dịch vụ: ");
//		cmbTenDichVu = new JComboBox<>();
//		b3.add(lblTenDichVu);
//		b3.add(Box.createHorizontalStrut(10));
//		b3.add(cmbTenDichVu);

		btnThongKe = new JButton("Tống kê");
		btnThongKe.setBackground(mauNen);
		btnThongKe.setForeground(Color.white);
		btnThoat = new JButton("Thoát");
		btnThoat.setBackground(mauNen);
		btnThoat.setForeground(Color.white);
		b4.add(btnThongKe);
		b4.add(Box.createHorizontalStrut(10));
		b4.add(btnThoat);

		pnWest.add(pnThongKe);
		pnThongKe.add(b);
		b.add(b1);
		b.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
		b.add(b2);
		b.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
		b.add(b3);
		b.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
		b.add(b4);

		pnThongKeKhac = new JPanel();
		btnHoaDon = new JButton("Thống kê hóa đơn");
		btnHoaDon.setBackground(mauNen);
		btnHoaDon.setForeground(Color.white);
		btnPhong = new JButton("Thống kê phòng");
		btnPhong.setBackground(mauNen);
		btnPhong.setForeground(Color.WHITE);
		btnKhachHang = new JButton("Thống kê khách hàng");
		btnKhachHang.setBackground(mauNen);
		btnKhachHang.setForeground(Color.WHITE);
		pnThongKeKhac.add(btnHoaDon);
		pnThongKeKhac.add(btnPhong);
		pnThongKeKhac.add(btnKhachHang);
		pnWest.add(pnThongKeKhac);

		TitledBorder titledThongKeKhac = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(mauNen),
				"Thống kê khác");
		titledThongKeKhac.setTitleJustification(TitledBorder.CENTER);
		pnThongKeKhac.setBorder(titledThongKeKhac);

		pnEast = new JPanel(new GridLayout(2, 1));
		pnContent.add(pnEast, BorderLayout.EAST);

		pnTable = new JPanel();
		pnEast.add(pnTable);

		TitledBorder titledTable = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(mauNen),
				"Danh sách phòng");
		titledTable.setTitleJustification(TitledBorder.CENTER);
		pnTable.setBorder(titledTable);

		pnTongTien = new JPanel();
		JLabel lblTong = new JLabel("Tổng số tiền");
		lblTongSoTien = new JLabel();
		pnTongTien.add(lblTong);
		pnTongTien.add(lblTongSoTien);
		pnEast.add(pnTongTien);
		Font newFont = new Font("Arial", Font.BOLD, 25);
		lblTong.setFont(newFont);
		lblTongSoTien.setFont(newFont);
		TitledBorder titledTongTien = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(mauNen));
		pnTongTien.setBorder(titledTongTien);

		createTable();
		this.add(pnContent);
		btnThongKe.addActionListener(this);
		btnThoat.addActionListener(this);
		btnPhong.addActionListener(this);
		btnHoaDon.addActionListener(this);
		btnKhachHang.addActionListener(this);
	}

	public void createTable() {
		model = new DefaultTableModel();
		table = new JTable(model);
		model.addColumn("ID dịch vụ");
		model.addColumn("Tên dịch vụ");
		model.addColumn("Số lượng");
		model.addColumn("Giá(VNĐ)");
		model.addColumn("Đã sử dụng");
		model.addColumn("Tồn");
		model.addColumn("Thành tiền");
		double tongTien = 0.0;
		try {
			for (DichVu dichVu : dv.getAllDichVu()) {
				int tongSoLuong = 0;
				int ton = dichVu.getSoLuong();
				double thanhTien = 0.0;

				// Lặp qua danh sách đặt dịch vụ
				for (DatDichVu datDichVu : ddv.getAllDatDichVu()) {
					if (dichVu.getMaDichVu().equals(datDichVu.getMaDichVu())) {
						// Cộng dồn số lượng
						tongSoLuong += datDichVu.getSoLuong();
						ton -= datDichVu.getSoLuong();

					}
				}

				// Tính thành tiền
				thanhTien = tongSoLuong * dichVu.getGia();
				tongTien += thanhTien;
				// Thêm vào bảng
				Object[] row = { dichVu.getMaDichVu(), dichVu.getTenDichVu(), dichVu.getSoLuong(),
						tienTeVN.format(dichVu.getGia()), tongSoLuong, ton, tienTeVN.format(thanhTien) };
				model.addRow(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		lblTongSoTien.setText(tienTeVN.format(tongTien));
		pnTable.add(table);
		JScrollPane sp = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(700, 176));
		pnTable.add(sp);

		DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
		headerRenderer.setBackground(mauBang);
		table.getTableHeader().setDefaultRenderer(headerRenderer);
		headerRenderer.setForeground(Color.WHITE);
		table.setGridColor(mauNen);
	}

	public static void main(String[] args) throws Exception {
		new ThongKeDichVu_GUI().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		try {
			if (o.equals(btnThongKe)) {
				thongKe();
			} else if (o.equals(btnPhong)) {
				new ThongKePhong_GUI().setVisible(true);
				this.dispose();
			} else if (o.equals(btnHoaDon)) {
				new ThongKeHoaDon_GUI().setVisible(true);
				this.dispose();
			} else if (o.equals(btnKhachHang)) {
				new ThongKeKhachHang_GUI().setVisible(true);
				this.dispose();
			} else if (o.equals(btnThoat)) {
				this.dispose();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void thongKe() {
		model.setRowCount(0);
		double tongTien = 0.0;

		LocalDate tuNgay = dateTuNgay.getDate() != null
				? dateTuNgay.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
				: null;
		LocalDate denNgay = dateDenNgay.getDate() != null
				? dateDenNgay.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
				: null;

		// Kiểm tra xem ngày đã được chọn hay chưa
		if (tuNgay == null || denNgay == null) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn cả ngày bắt đầu và ngày kết thúc để thống kê.",
					"Thông báo", JOptionPane.WARNING_MESSAGE);
			return;
		}
		try {
			for (LapHoaDon lapHD : lhd.getAllLapHoaDon()) {
				LocalDateTime thoiGianThue = lapHD.getThoiGianThue();
				LocalDateTime thoiGianKThuc = lapHD.getThoiGianKetThuc();
				if (((thoiGianThue.toLocalDate().isAfter(tuNgay) || thoiGianThue.toLocalDate().isEqual(tuNgay))
						&& (thoiGianKThuc.toLocalDate().isBefore(denNgay)
								|| thoiGianKThuc.toLocalDate().isEqual(denNgay)))) {
					for (DichVu dichVu : dv.getAllDichVu()) {
						for (DatDichVu datDichVu : ddv.getAllDatDichVu()) {
							if (dichVu.getMaDichVu().equals(datDichVu.getMaDichVu())) {
								if (datDichVu.getMaHoaDon().equals(lapHD.getMaHoaDon())) {
									// Kiểm tra xem mã dịch vụ đã tồn tại trong bảng hay chưa
									boolean daTonTai = false;
									int rowIndex = -1;
									for (int i = 0; i < model.getRowCount(); i++) {
										if (model.getValueAt(i, 0).equals(dichVu.getMaDichVu())) {
											daTonTai = true;
											rowIndex = i;
											break;
										}
									}

									int tongSoLuong = datDichVu.getSoLuong();
									int ton = dichVu.getSoLuong() - datDichVu.getSoLuong();
									double thanhTien = tongSoLuong * dichVu.getGia();

									if (daTonTai) {
										// Nếu đã tồn tại, cập nhật các giá trị
										int soLuongCu = (int) model.getValueAt(rowIndex, 4);
										model.setValueAt(soLuongCu + tongSoLuong, rowIndex, 4);

										int tonCu = (int) model.getValueAt(rowIndex, 5);
										model.setValueAt(tonCu - datDichVu.getSoLuong(), rowIndex, 5);

										double thanhTienCu = Double
												.parseDouble(model.getValueAt(rowIndex, 6).toString());
										model.setValueAt(thanhTienCu + thanhTien, rowIndex, 6);
									} else {
										// Nếu chưa tồn tại, thêm hàng mới
										Object[] row = { dichVu.getMaDichVu(), dichVu.getTenDichVu(),
												dichVu.getSoLuong(), tienTeVN.format(dichVu.getGia()), tongSoLuong, ton,
												thanhTien };
										model.addRow(row);
									}

									tongTien += thanhTien;
								}
							}
						}
					}
				}
			}
			lblTongSoTien.setText(tienTeVN.format(tongTien));

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
