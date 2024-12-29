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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import connectDB.ConnectDB;
import dao.Phong_DAO;
import entity.Phong;

public class TraCuuPhong_GUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel pnContent, pnCenter, pnNorth, pnSouth, pnThem, pnTable;
	private JLabel lblIDPhong, lblTenPhong, lblLoaiPhong, lblTrangThai, lblGia;
	private JComboBox<Object> cmbLoaiPhong, cmbTinhTrang;
	private JTextField txtIDPhong, txtTenPhong, txtGia;
	private DefaultTableModel model;
	private JTable table;
	private JButton btnTim, btnDatPhong;
	private Phong_DAO p;

	Color mauNen = new Color(205, 38, 38); // Màu đỏ
	Color mauBang = new Color(99, 184, 255); // Màu lam
	/***
	 * Chỉnh sửa tiền tệ
	 */
	Locale localeVN = new Locale("vi", "VN");
	NumberFormat tienTeVN = NumberFormat.getCurrencyInstance(localeVN);

	public TraCuuPhong_GUI() throws Exception {
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		p = new Phong_DAO();
		createGui();
	}

	public void createGui() {
		setTitle("Tra cứu phòng");
		setSize(800, 600);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(true);
		setLocationRelativeTo(null);

		phong();
	}

	public void phong() {
		pnContent = new JPanel();
		pnContent.setLayout(new BorderLayout());

		pnNorth = new JPanel();
		pnContent.add(pnNorth, BorderLayout.NORTH);

		JLabel lblTitle = new JLabel("Tra cứu thông tin phòng");
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
		Box b3 = Box.createHorizontalBox();

		TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(mauNen),
				"Tìm kiếm theo");
		titledBorder.setTitleJustification(TitledBorder.CENTER);
		pnThem.setBorder(titledBorder);

		lblIDPhong = new JLabel("ID phòng: ");
		txtIDPhong = new JTextField(15);
		lblTrangThai = new JLabel("Tình trạng: ");
		cmbTinhTrang = new JComboBox<>();
		cmbTinhTrang.addItem("Tất cả");
		cmbTinhTrang.addItem("Trống");
		cmbTinhTrang.addItem("Đã đặt");
		cmbTinhTrang.addItem("Bảo trì");
		b1.add(lblIDPhong);
		b1.add(Box.createHorizontalStrut(10));
		b1.add(txtIDPhong);
		b1.add(Box.createHorizontalStrut(120));
		b1.add(lblTrangThai);
		b1.add(Box.createHorizontalStrut(10));
		b1.add(cmbTinhTrang);

		lblTenPhong = new JLabel("Tên phòng: ");
		txtTenPhong = new JTextField();
		lblGia = new JLabel("Giá phòng mỗi giờ theo ca: ");
		txtGia = new JTextField(10);
		b2.add(lblTenPhong);
		b2.add(Box.createHorizontalStrut(4));
		b2.add(txtTenPhong);
		b2.add(Box.createHorizontalStrut(150));
		b2.add(lblGia);
		b2.add(Box.createHorizontalStrut(10));
		b2.add(txtGia);

		lblLoaiPhong = new JLabel("Loại phòng: ");
		cmbLoaiPhong = new JComboBox<>();
		cmbLoaiPhong.addItem("Tất cả");
		cmbLoaiPhong.addItem("Standard (1-7 khách)");
		cmbLoaiPhong.addItem("Superior (7- 15 khách)");
		cmbLoaiPhong.addItem("Deluxe (trên 15 khách)");
		btnTim = new JButton("Tìm");
		btnTim.setBackground(mauNen);
		btnTim.setForeground(Color.WHITE);
		btnDatPhong = new JButton("Đặt phòng");
		btnDatPhong.setBackground(mauNen);
		btnDatPhong.setForeground(Color.WHITE);
		b3.add(lblLoaiPhong);
		b3.add(Box.createHorizontalStrut(10));
		b3.add(cmbLoaiPhong);
		b3.add(Box.createHorizontalStrut(100));
		b3.add(btnTim);
		b3.add(Box.createHorizontalStrut(20));
//		b3.add(btnDatPhong);
//        b3.add(Box.createHorizontalStrut(20));

		txtTenPhong.setPreferredSize(txtIDPhong.getPreferredSize());
		cmbTinhTrang.setPreferredSize(txtGia.getPreferredSize());
//        lblIDPhong.setPreferredSize(lblTenPhong.getPreferredSize());
//        lblLoaiPhong.setPreferredSize(lblTenPhong.getPreferredSize());
//        lblTrangThai.setPreferredSize(lblTenPhong.getPreferredSize());

		pnThem.add(b);
		b.add(b1);
		b.add(Box.createRigidArea(new Dimension(0, 20)));
		b.add(b2);
		b.add(Box.createRigidArea(new Dimension(0, 20)));
		b.add(b3);
		b.add(Box.createRigidArea(new Dimension(0, 20)));

		pnSouth = new JPanel();
		pnContent.add(pnSouth, BorderLayout.SOUTH);

		pnTable = new JPanel();
		createTable();
		pnSouth.add(pnTable);
		TitledBorder titledBorder1 = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(mauNen),
				"Danh sách phòng ");
		titledBorder1.setTitleJustification(TitledBorder.CENTER);
		pnTable.setBorder(titledBorder1);

		this.add(pnContent);
		
		btnDatPhong.addActionListener(this);
		btnTim.addActionListener(this);
	}

	public void createTable() {
		model = new DefaultTableModel();
		table = new JTable(model);
//        model.addColumn("ID phòng");
//        model.addColumn("Tên phòng");
//        model.addColumn("Loại phòng");
//        model.addColumn("Trạng thái");
		model.addColumn("IDPhong");
		model.addColumn("Tên phòng");
		model.addColumn("Loại phòng");
		model.addColumn("Thiết bị");
		model.addColumn("Tình trạng");
		model.addColumn("Giá 1h/ca");

		try {
			for (Phong phong : p.getAllPhong()) {
				Object[] row = { phong.getMaPhong(), phong.getTenPhong(), phong.getLoaiPhong(), phong.getThietBi(),
						phong.getTinhTrang(), tienTeVN.format(phong.getGiaPhong()) };
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
		new TraCuuPhong_GUI().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnDatPhong)) {
			try {
				new DatPhong_GUI().setVisible(true);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			this.dispose();
		} else if (o.equals(btnTim))
			timKiem();
	}

	public void timKiem() {
		int numRows = model.getRowCount();
		for (int i = numRows - 1; i >= 0; i--) {
			model.removeRow(i);
		}

		String maPhong = txtIDPhong.getText().trim();
		String tenPhong = txtTenPhong.getText().trim();
		String loaiPhong = cmbLoaiPhong.getSelectedItem().toString();
		String tinhTrang = cmbTinhTrang.getSelectedItem().toString();
		String gia = txtGia.getText().trim();

		try {
			boolean tim = false;
			for (Phong phong : p.getAllPhong()) {
				boolean maPhongMatch = maPhong.isEmpty() || phong.getMaPhong().equalsIgnoreCase(maPhong);
				boolean tenPhongMatch = tenPhong.isEmpty()
						|| phong.getTenPhong().toLowerCase().contains(tenPhong.toLowerCase());
				boolean loaiPhongMatch = loaiPhong.equals("Tất cả") || phong.getLoaiPhong().equalsIgnoreCase(loaiPhong);
				boolean tinhTrangMatch = tinhTrang.equals("Tất cả") || phong.getTinhTrang().equalsIgnoreCase(tinhTrang);
//				boolean giaMatch = gia.isEmpty() || String.valueOf(phong.getGiaPhong()).contains(gia);
				boolean giaMatch = gia.isEmpty() || phong.getGiaPhong() == Integer.parseInt(gia.replace("$", "").replace(",", ""));

				if (maPhongMatch && tenPhongMatch && loaiPhongMatch && tinhTrangMatch && giaMatch) {
					Object[] row = { phong.getMaPhong(), phong.getTenPhong(), phong.getLoaiPhong(), phong.getThietBi(),
							phong.getTinhTrang(), tienTeVN.format(phong.getGiaPhong()) };
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
