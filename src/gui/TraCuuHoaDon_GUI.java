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
import java.text.SimpleDateFormat;
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
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import connectDB.ConnectDB;
import dao.DatDichVu_DAO;
import dao.LapHoaDon_DAO;
import dao.Phong_DAO;
import entity.DatDichVu;
import entity.LapHoaDon;
import entity.Phong;

public class TraCuuHoaDon_GUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel pnContent, pnCenter, pnNorth, pnSouth, pnThem, pnTable;
	private JLabel lblIDHoaDon, lblThoiGianThue, lblThoiGianKetThuc;
	private JTextField txtIDHoaDon;
	private JDateChooser dateNgayThue, dateNgayKThuc;
	private DefaultTableModel model;
	private JTable table;
	private JButton btnTim;
	private LapHoaDon_DAO hd;
	private Phong_DAO p;
	private DatDichVu_DAO ddv;

	Color mauNen = new Color(205, 38, 38); // màu đỏ
	Color mauBang = new Color(99, 184, 255); // màu lam
	/***
	 * Chỉnh sửa tiền tệ
	 */
	Locale localeVN = new Locale("vi", "VN");
	NumberFormat tienTeVN = NumberFormat.getCurrencyInstance(localeVN);
	DateTimeFormatter dft = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

	public TraCuuHoaDon_GUI() throws Exception {
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		p = new Phong_DAO();
		ddv = new DatDichVu_DAO();
		hd = new LapHoaDon_DAO();
		createGui();
	}

	public void createGui() {
		setTitle("Tra cứu hóa đơn");
		setSize(800, 550);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(true);
		setLocationRelativeTo(null);

		hoaDon();
	}

	public void hoaDon() {
		pnContent = new JPanel();
		pnContent.setLayout(new BorderLayout());

		pnNorth = new JPanel();
		pnContent.add(pnNorth, BorderLayout.NORTH);

		JLabel lblTitle = new JLabel("Tra cứu thông tin hóa đơn");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
		lblTitle.setForeground(Color.WHITE);
		pnNorth.add(lblTitle);
		pnNorth.setBackground(mauNen);

		pnCenter = new JPanel(new GridLayout(1, 1));
		pnContent.add(pnCenter, BorderLayout.CENTER);

		pnThem = new JPanel();
		pnCenter.add(pnThem);
		Box b = Box.createVerticalBox();
		Box b1 = Box.createHorizontalBox();
		Box b2 = Box.createHorizontalBox();

		TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(mauNen),
				"Tìm kiếm theo");
		titledBorder.setTitleJustification(TitledBorder.CENTER);
		pnThem.setBorder(titledBorder);

		lblIDHoaDon = new JLabel("ID Hóa Đơn: ");
		txtIDHoaDon = new JTextField(20);
		lblThoiGianThue = new JLabel("Thời gian thuê: ");
		dateNgayThue = new JDateChooser();
//		dateNgayThue.setBounds(320, 90, 200, 30);
		dateNgayThue.setDateFormatString("dd/MM/yyyy");
		lblThoiGianKetThuc = new JLabel("Thời gian kết thúc: ");
		dateNgayKThuc = new JDateChooser();
//		dateNgayKThuc.setBounds(320, 90, 200, 30);
		dateNgayKThuc.setDateFormatString("dd/MM/yyyy");
		b1.add(lblIDHoaDon);
		b1.add(Box.createHorizontalStrut(10));
		b1.add(txtIDHoaDon);
		b1.add(Box.createHorizontalStrut(50));
		b1.add(lblThoiGianThue);
		b1.add(Box.createHorizontalStrut(10));
		b1.add(dateNgayThue);

		btnTim = new JButton("Tìm");
		btnTim.setBackground(mauNen);
		btnTim.setForeground(Color.WHITE);
		b2.add(Box.createHorizontalStrut(90));
		b2.add(btnTim);
		b2.add(Box.createHorizontalStrut(150));
		b2.add(lblThoiGianKetThuc);
		b2.add(Box.createHorizontalStrut(10));
		b2.add(dateNgayKThuc);

//		lblIDHoaDon.setPreferredSize(lblThoiGianThue.getPreferredSize());
//		lblThoiGianKetThuc.setPreferredSize(lblThoiGianThue.getPreferredSize());

		pnThem.add(b);
		b.add(b1);
		b.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
		b.add(b2);
		b.add(Box.createRigidArea(new DimensionUIResource(0, 20)));

		pnSouth = new JPanel();
		pnContent.add(pnSouth, BorderLayout.SOUTH);

		pnTable = new JPanel();
		createTable();
		pnSouth.add(pnTable);
		TitledBorder titledBorder1 = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(mauNen),
				"Danh sách hóa đơn ");
		titledBorder1.setTitleJustification(TitledBorder.CENTER);
		pnTable.setBorder(titledBorder1);

		this.add(pnContent);
		btnTim.addActionListener(this);
	}

	public void createTable() {
		model = new DefaultTableModel();
		table = new JTable(model);
		model.addColumn("ID Hóa Đơn");
		model.addColumn("Tên phòng");
		model.addColumn("Thời gian thuê");
		model.addColumn("Thời gian kết thúc");
		model.addColumn("Lượng khách");
		model.addColumn("Thành tiền");

//		try {
//			DateTimeFormatter dft = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//			for (LapHoaDon hoaDon : hd.getAllLapHoaDon()) {
//				Object[] row = { hoaDon.getMaHoaDon(), hoaDon.getTenPhong(), dft.format(hoaDon.getThoiGianThue()),
//						dft.format(hoaDon.getThoiGianKetThuc()), hoaDon.getLuongKhach()};
//				model.addRow(row);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		double tongTien = 0.0;
		try {
			for (LapHoaDon hoaDon : hd.getAllLapHoaDon()) {
				double tienPhong = 0;
				double tienDV = 0;
				for (Phong phong : p.getAllPhong()) {
					if (hoaDon.getMaPhong().equals(phong.getMaPhong())) {
						tienPhong = hd.tinhThanhTienPhong(hoaDon.getMaHoaDon());
					}
				}
				for (DatDichVu datDichVu : ddv.getAllDatDichVu()) {
					if (hoaDon.getMaHoaDon().equals(datDichVu.getMaHoaDon())) {
						tienDV = hd.tinhThanhTienDichVu(hoaDon.getMaHoaDon());
					}
				}
				double thanhTien = tienPhong + tienDV;
				tongTien += thanhTien;
				Object[] row = { hoaDon.getMaHoaDon(), hoaDon.getTenPhong(), dft.format(hoaDon.getThoiGianThue()),
						dft.format(hoaDon.getThoiGianKetThuc()), hoaDon.getLuongKhach(), tienTeVN.format(thanhTien) };
				model.addRow(row);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		pnTable.add(table);
		JScrollPane sp = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(750, 300));
		pnTable.add(sp);

		DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
		headerRenderer.setBackground(mauBang);
		table.getTableHeader().setDefaultRenderer(headerRenderer);
		headerRenderer.setForeground(Color.WHITE);
		table.setGridColor(mauNen);
	}

	public static void main(String[] args) throws Exception {
		new TraCuuHoaDon_GUI().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnTim))
			timKiem();
	}

	public void timKiem() {
		int numRows = model.getRowCount();
		for (int i = numRows - 1; i >= 0; i--) {
			model.removeRow(i);
		}

		String idHoaDon = txtIDHoaDon.getText().trim();

		String thoiGianThue = null;
		if (dateNgayThue.getDate() != null) {
			thoiGianThue = new SimpleDateFormat("yyyy/MM/dd").format(dateNgayThue.getDate());
		}

		String thoiGianKetThuc = null;
		if (dateNgayKThuc.getDate() != null) {
			thoiGianKetThuc = new SimpleDateFormat("yyyy/MM/dd").format(dateNgayKThuc.getDate());
		}

		try {
			boolean tim = false;
			for (LapHoaDon hoaDon : hd.getAllLapHoaDon()) {
				boolean idHoaDonMatch = idHoaDon.isEmpty() || hoaDon.getMaHoaDon().equalsIgnoreCase(idHoaDon);
				boolean thoiGianThueMatch = thoiGianThue == null
						|| dft.format(hoaDon.getThoiGianThue()).contains(thoiGianThue);
				boolean thoiGianKetThucMatch = thoiGianKetThuc == null
						|| dft.format(hoaDon.getThoiGianKetThuc()).contains(thoiGianKetThuc);

				if (idHoaDonMatch && thoiGianThueMatch && thoiGianKetThucMatch) {

//					Object[] row = { hoaDon.getMaHoaDon(), hoaDon.getTenPhong(), dft.format(hoaDon.getThoiGianThue()),
//							dft.format(hoaDon.getThoiGianKetThuc()), hoaDon.getLuongKhach(), hoaDon.getThanhTien() };
//					model.addRow(row);
					double tongTien = 0.0;
					double tienPhong = 0;
					double tienDV = 0;
					for (Phong phong : p.getAllPhong()) {
						if (hoaDon.getMaPhong().equals(phong.getMaPhong())) {
							tienPhong = hd.tinhThanhTienPhong(hoaDon.getMaHoaDon());
						}
					}
					for (DatDichVu datDichVu : ddv.getAllDatDichVu()) {
						if (hoaDon.getMaHoaDon().equals(datDichVu.getMaHoaDon())) {
							tienDV = hd.tinhThanhTienDichVu(hoaDon.getMaHoaDon());
						}
					}
					double thanhTien = tienPhong + tienDV;
					tongTien += thanhTien;
					Object[] row = { hoaDon.getMaHoaDon(), hoaDon.getTenPhong(), dft.format(hoaDon.getThoiGianThue()),
							dft.format(hoaDon.getThoiGianKetThuc()), hoaDon.getLuongKhach(),
							tienTeVN.format(thanhTien) };
					model.addRow(row);
					tim = true;
				}
			}

			if (!tim) {
				JOptionPane.showMessageDialog(this, "Không tìm thấy");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
