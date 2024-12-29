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

import connectDB.ConnectDB;
import dao.DichVu_DAO;
import entity.DichVu;

public class TraCuuDichVu_GUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel pnContent, pnCenter, pnNorth, pnSouth, pnThem, pnTable;
	private JLabel lblMaDichVu, lblTenDichVu, lblGia;
	private JTextField txtMaDichVu, txtTenDichVu, txtGia;
	private DefaultTableModel model;
	private JTable table;
	private JButton btnTim, btnDatDichVu;
	private DichVu_DAO dv;

	Color mauNen = new Color(205, 38, 38); // màu đỏ
	Color mauBang = new Color(99, 184, 255); // màu lam

	Locale localeVN = new Locale("vi", "VN");
	NumberFormat tienTeVN = NumberFormat.getCurrencyInstance(localeVN);

	public TraCuuDichVu_GUI() throws Exception {
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dv = new DichVu_DAO();
		createGui();
	}

	public void createGui() {
		setTitle("Tra cứu");
		setSize(800, 550);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(true);
		setLocationRelativeTo(null);

		dichVu();
	}

	public void dichVu() {
		pnContent = new JPanel();
		pnContent.setLayout(new BorderLayout());

		pnNorth = new JPanel();
		pnContent.add(pnNorth, BorderLayout.NORTH);

		JLabel lblTitle = new JLabel("Tra cứu thông tin dịch vụ");
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

		lblMaDichVu = new JLabel("Mã dịch vụ: ");
		txtMaDichVu = new JTextField(20);
		lblGia = new JLabel("Chi phí($): ");
		txtGia = new JTextField(20);
		b1.add(lblMaDichVu);
		b1.add(Box.createHorizontalStrut(10));
		b1.add(txtMaDichVu);
		b1.add(Box.createHorizontalStrut(110));
		b1.add(lblGia);
		b1.add(Box.createHorizontalStrut(10));
		b1.add(txtGia);

		lblTenDichVu = new JLabel("Tên dịch vụ:   ");
		txtTenDichVu = new JTextField();
		b2.add(lblTenDichVu);
		b2.add(Box.createHorizontalStrut(4));
		b2.add(txtTenDichVu);
		b2.add(Box.createHorizontalStrut(200));

		btnTim = new JButton("Tìm");
		btnTim.setBackground(mauNen);
		btnTim.setForeground(Color.WHITE);
		btnDatDichVu = new JButton("Đặt dịch vụ");
		btnDatDichVu.setBackground(mauNen);
		btnDatDichVu.setForeground(Color.WHITE);
		b2.add(btnTim);
		b2.add(Box.createHorizontalStrut(20));
//		b2.add(btnDatDichVu);
//        b2.add(Box.createHorizontalStrut(20));

		lblMaDichVu.setPreferredSize(lblTenDichVu.getPreferredSize());
		lblGia.setPreferredSize(lblTenDichVu.getPreferredSize());

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
				"Danh sách dịch vụ ");
		titledBorder1.setTitleJustification(TitledBorder.CENTER);
		pnTable.setBorder(titledBorder1);

		this.add(pnContent);

		btnDatDichVu.addActionListener(this);
		btnTim.addActionListener(this);
	}

	public void createTable() {
		model = new DefaultTableModel();
		table = new JTable(model);
		model.addColumn("Mã dịch vụ");
		model.addColumn("Tên dịch vụ");
		model.addColumn("Số lượng");
		model.addColumn("Phí dịch vụ($)");

		try {
			for (DichVu dichVu : dv.getAllDichVu()) {
				Object[] row = { dichVu.getMaDichVu(), dichVu.getTenDichVu(), dichVu.getSoLuong(),
						tienTeVN.format(dichVu.getGia()) };
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
		new TraCuuDichVu_GUI().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnDatDichVu)) {
			try {
				new DatDichVu_GUI().setVisible(true);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
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
		String maDichVu = txtMaDichVu.getText().trim();
		String tenDichVu = txtTenDichVu.getText().trim();
		String giaDichVu = txtGia.getText().trim();
		try {
			boolean tim = false;
			for (DichVu dichVu : dv.getAllDichVu()) {
//	        	equalsIgnoreCase: so sánh hai chuỗi mà không phân biệt chữ hoa và chữ thường (2 chuỗi có cùng nội dung. VD: DV001 == dv001)
//	        	toLowerCase(): chuyển đổi một chuỗi thành dạng chữ thường
//	        	contains: lấy một chuỗi con
//	        	replace("$", ""): loại bỏ dấu tiền tệ (12.300 đ => 12.300), replace(",", ""): loại bỏ dấu phẩy (12.300 => 12300)
//	        	Integer.parseInt(): để chuyển đổi chuỗi thành một số nguyên
				boolean maDichVuMatch = maDichVu.isEmpty() || dichVu.getMaDichVu().equalsIgnoreCase(maDichVu);
				boolean tenDichVuMatch = tenDichVu.isEmpty() || dichVu.getTenDichVu().toLowerCase().contains(tenDichVu.toLowerCase());
				boolean giaDichVuMatch = giaDichVu.isEmpty() || dichVu.getGia() == Integer.parseInt(giaDichVu.replace("$", "").replace(",", ""));
//				boolean giaMatch = giaDichVuText.isEmpty() || String.valueOf(dichVu.getGia()).contains(giaDichVuText);

				if (maDichVuMatch && tenDichVuMatch && giaDichVuMatch) {
					Object[] row = { dichVu.getMaDichVu(), dichVu.getTenDichVu(), dichVu.getSoLuong(),
							tienTeVN.format(dichVu.getGia()) };
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
