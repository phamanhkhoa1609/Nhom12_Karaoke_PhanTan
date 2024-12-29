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
import dao.KhachHang_DAO;
import dao.LapHoaDon_DAO;
import dao.Phong_DAO;
import entity.DatDichVu;
import entity.KhachHang;
import entity.LapHoaDon;
import entity.Phong;

public class ThongKeKhachHang_GUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel pnContent, pnNorth, pnWest, pnEast, pnThongKe, pnThongKeKhac, pnTable, pnTongTien;
	private JLabel lblTuNgay, lblDenNgay, lblTongSoTien;
	private DefaultTableModel model;
	private JTable table;
	private JButton btnThongKe, btnThoat, btnHoaDon, btnDichVu, btnPhong;
	private JDateChooser dateTuNgay, dateDenNgay;
	private KhachHang_DAO kh;
	private LapHoaDon_DAO lhd;
	private DatDichVu_DAO ddv;
	private Phong_DAO p;

	Color mauNen = new Color(205, 38, 38); // màu đỏ
	Color mauBang = new Color(99, 184, 255); // màu lam
	/***
	 * Chỉnh sửa tiền tệ
	 */
	Locale localeVN = new Locale("vi", "VN");
	NumberFormat tienTeVN = NumberFormat.getCurrencyInstance(localeVN);
	DateTimeFormatter dft = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

	public ThongKeKhachHang_GUI() throws Exception {
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		p = new Phong_DAO();
		ddv = new DatDichVu_DAO();
		lhd = new LapHoaDon_DAO();
		kh = new KhachHang_DAO();
		createGui();
	}

	public void createGui() {
		setTitle("Thống kê khách hàng");
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

		JLabel lblTitle = new JLabel("Thống kê khách hàng");
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

		lblTuNgay = new JLabel("Từ Ngày: ");
		dateTuNgay = new JDateChooser();
		dateTuNgay.setBounds(320, 90, 200, 30);
		dateTuNgay.setDateFormatString("dd/MM/yyy");
		b1.add(lblTuNgay);
		b1.add(Box.createHorizontalStrut(20));
		b1.add(dateTuNgay);

		lblDenNgay = new JLabel("Đến Ngày: ");
		dateDenNgay = new JDateChooser();
		dateDenNgay.setBounds(320, 90, 200, 30);
		dateDenNgay.setDateFormatString("dd/MM/yyyy");
		b2.add(lblDenNgay);
		b2.add(Box.createHorizontalStrut(12));
		b2.add(dateDenNgay);

		btnThongKe = new JButton("Thống kê");
		btnThongKe.setBackground(mauNen);
		btnThongKe.setForeground(Color.white);
		btnThoat = new JButton("Thoát");
		btnThoat.setBackground(mauNen);
		btnThoat.setForeground(Color.white);
		b3.add(btnThongKe);
		b3.add(Box.createHorizontalStrut(10));
		b3.add(btnThoat);

		pnWest.add(pnThongKe);
		pnThongKe.add(b);
		b.add(b1);
		b.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
		b.add(b2);
		b.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
		b.add(b3);

		pnThongKeKhac = new JPanel();
		btnHoaDon = new JButton("Thống kê hóa đơn");
		btnHoaDon.setBackground(mauNen);
		btnHoaDon.setForeground(Color.white);
		btnDichVu = new JButton("Thống kê dịch vụ");
		btnDichVu.setBackground(mauNen);
		btnDichVu.setForeground(Color.WHITE);
		btnPhong = new JButton("Thống kê phòng");
		btnPhong.setBackground(mauNen);
		btnPhong.setForeground(Color.WHITE);
		pnThongKeKhac.add(btnHoaDon);
		pnThongKeKhac.add(btnDichVu);
		pnThongKeKhac.add(btnPhong);
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
		lblTongSoTien = new JLabel("a");
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
		btnDichVu.addActionListener(this);
		btnHoaDon.addActionListener(this);
		btnPhong.addActionListener(this);
	}

	public void createTable() {
		model = new DefaultTableModel();
		table = new JTable(model);
		model.addColumn("IDKH");
		model.addColumn("Tên khách hàng");
		model.addColumn("Tuổi");
		model.addColumn("Giới tính");
		model.addColumn("SDT");
		model.addColumn("Thành tiền");
		double tongTien = 0.0;
		try {
			for (LapHoaDon hoaDon : lhd.getAllLapHoaDon()) {
				double tienPhong = 0;
				double tienDV = 0;
				for (Phong phong : p.getAllPhong()) {
					if (hoaDon.getMaPhong().equals(phong.getMaPhong())) {
						tienPhong = lhd.tinhThanhTienPhong(hoaDon.getMaHoaDon());
					}
				}
				for (DatDichVu datDichVu : ddv.getAllDatDichVu()) {
					if (hoaDon.getMaHoaDon().equals(datDichVu.getMaHoaDon())) {
						tienDV = lhd.tinhThanhTienDichVu(hoaDon.getMaHoaDon());
					}
				}
				double thanhTien = tienPhong + tienDV;
				tongTien += thanhTien;
				for (KhachHang khachHang : kh.getAllKhachHang()) {
					if (khachHang.getMaKH().equals(hoaDon.getMaKhachHang())) {
						Object[] row = { khachHang.getMaKH(), khachHang.getTenKH(), khachHang.getTuoi(),
								khachHang.getGioiTinh(), khachHang.getsDT(), tienTeVN.format(thanhTien) };
						model.addRow(row);
					}
				}

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
		new ThongKeKhachHang_GUI().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		try {
			if (o.equals(btnThongKe)) {
				thongKe();
			} else if (o.equals(btnDichVu)) {
				new ThongKeDichVu_GUI().setVisible(true);
				this.dispose();
			} else if (o.equals(btnHoaDon)) {
				new ThongKeHoaDon_GUI().setVisible(true);
				this.dispose();
			} else if (o.equals(btnPhong)) {
				new ThongKePhong_GUI().setVisible(true);
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
			return; // Kết thúc phương thức nếu ngày chưa được chọn
		}
		try {
			for (LapHoaDon lapHD : lhd.getAllLapHoaDon()) {
				LocalDateTime thoiGianThue = lapHD.getThoiGianThue();
				LocalDateTime thoiGianKThuc = lapHD.getThoiGianKetThuc();
				if ((thoiGianThue.toLocalDate().isAfter(tuNgay) || thoiGianThue.toLocalDate().isEqual(tuNgay))
						&& (thoiGianKThuc.toLocalDate().isBefore(denNgay)
								|| thoiGianKThuc.toLocalDate().isEqual(denNgay))) {
					double tienPhong = 0;
					double tienDV = 0;
					for (Phong phong : p.getAllPhong()) {
						if (lapHD.getMaPhong().equals(phong.getMaPhong())) {
							tienPhong = lhd.tinhThanhTienPhong(lapHD.getMaHoaDon());
						}
					}
					for (DatDichVu datDichVu : ddv.getAllDatDichVu()) {
						if (lapHD.getMaHoaDon().equals(datDichVu.getMaHoaDon())) {
							tienDV = lhd.tinhThanhTienDichVu(lapHD.getMaHoaDon());
						}
					}
					double thanhTien = tienPhong + tienDV;
					tongTien += thanhTien;
					for (KhachHang khachHang : kh.getAllKhachHang()) {
						if (khachHang.getMaKH().equals(lapHD.getMaKhachHang())) {
							Object[] row = { khachHang.getMaKH(), khachHang.getTenKH(), khachHang.getTuoi(),
									khachHang.getGioiTinh(), khachHang.getsDT(), tienTeVN.format(thanhTien) };
							model.addRow(row);
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		lblTongSoTien.setText(tienTeVN.format(tongTien));
	}
}
