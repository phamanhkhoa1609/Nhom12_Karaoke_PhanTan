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
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import connectDB.ConnectDB;
import dao.NhanVien_DAO;
import entity.NhanVien;

public class NhanVien_GUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JButton btnThem, btnCapNhat, btnXoa, btnXoaTrang, btnLuu, btnThoat;
	private JPanel pnContent, pnCenter, pnNorth, pnSouth, pnThem, pnTable;
	private JLabel lblMaNV, lblTenNV, lblSDT, lblTuoi, lblGioiTinh, lblBoPhan, lblLuong;
	private JRadioButton radNam, radNu;
	private JComboBox<String> cmbBoPhan;
	private JTextField txtMaNV, txtTenNV, txtSDT, txtTuoi, txtLuong;
	private DefaultTableModel model;
	private JTable table;
	private NhanVien_DAO nv;

	/***
	 * Chỉnh sửa tiền tệ
	 */
	Locale localeVN = new Locale("vi", "VN");
	NumberFormat tienTeVN = NumberFormat.getCurrencyInstance(localeVN);

//	Khai báo màu
	Color mauNen = new Color(205, 38, 38);// màu đỏ
	Color mauBang = new Color(99, 184, 255);// màu lam

	public NhanVien_GUI() throws Exception {
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		nv = new NhanVien_DAO();
		createGui();
	}

	public void createGui() {
//		Khởi tạo khung Giao diện
		setTitle("Danh sách nhân viên");
		setSize(900, 650);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(true);
		setLocationRelativeTo(null);
		nhanVien();
	}

	public void nhanVien() {
//		Khai báo JPanel tổng
		pnContent = new JPanel();
		pnContent.setLayout(new BorderLayout());

//		Khai báo JPanel Tiêu đề
		pnNorth = new JPanel();
		pnContent.add(pnNorth, BorderLayout.NORTH);

		JLabel lblTitle = new JLabel("Quản lý thông tin nhân viên");
//		Đổi kiểu chữ
		lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
//		Đổi màu chữ
		lblTitle.setForeground(Color.WHITE);
//		Thêm lblTitle vào Giao diện Tiêu đề (cái này quan trọng, không thêm lblTitle là nó không thêm Tiêu đề được)
		pnNorth.add(lblTitle);
//		Đổi màu nền
		pnNorth.setBackground(mauNen);

//		Khai báo Panel Thêm thông tin
		pnCenter = new JPanel();
		pnContent.add(pnCenter, BorderLayout.CENTER);

		pnThem = new JPanel(new GridLayout(1, 1));
		pnCenter.add(pnThem);
//		Tạo các Box để nó auto canh lable với textfield
		Box b = Box.createVerticalBox();
		Box b1 = Box.createHorizontalBox();
		Box b2 = Box.createHorizontalBox();
		Box b3 = Box.createHorizontalBox();
		Box b4 = Box.createHorizontalBox();
		Box b5 = Box.createHorizontalBox();

//		b.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(mauNen), "Thông tin khách hàng"));
//		BorderFactory.createLineBorder(mauNen): đổi màu khung bao
		TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(mauNen),
				"Thông tin nhân viên");
//		Canh giữa "Thông tin khách hàng"
		titledBorder.setTitleJustification(TitledBorder.CENTER);
//		Thêm border "Thông tin khách hàng" vào Giao diện
		pnThem.setBorder(titledBorder);

		lblMaNV = new JLabel("Mã nhân viên: ");
		txtMaNV = new JTextField(20);
		lblTenNV = new JLabel("Tên nhân viên: ");
		txtTenNV = new JTextField(35);
		b1.add(lblMaNV);
		b1.add(Box.createHorizontalStrut(10));
		b1.add(txtMaNV);
		b1.add(Box.createHorizontalStrut(50));
		b1.add(lblTenNV);
		b1.add(Box.createHorizontalStrut(10));
		b1.add(txtTenNV);

		lblGioiTinh = new JLabel("Giới tính: ");
		radNam = new JRadioButton("Nam");
		radNu = new JRadioButton("Nữ");
		ButtonGroup bg = new ButtonGroup();
		lblBoPhan = new JLabel("Bộ phận:");
		String boPhan[] = { "Nhân viên tiếp tân", "Nhân viên kế toán", "Nhân viên phục vụ", "Nhân viên kỹ thuật",
				"Nhân viên quản lý" };
		cmbBoPhan = new JComboBox(boPhan);
		cmbBoPhan.setBounds(100, 50, 150, 20);
		bg.add(radNam);
		bg.add(radNu);
		b2.add(lblGioiTinh);
		b2.add(Box.createHorizontalStrut(30));
		b2.add(radNam);
		b2.add(radNu);
		b2.add(Box.createHorizontalStrut(150));
		b2.add(lblBoPhan);
		b2.add(Box.createHorizontalStrut(45));
		b2.add(cmbBoPhan);

		lblSDT = new JLabel("Số điện thoại:");
		txtSDT = new JTextField(11);
		lblLuong = new JLabel("Lương/ca:");
		txtLuong = new JTextField(25);
//		txtLuong.setBounds(100, 50, 150, 20);
		b3.add(lblSDT);
		b3.add(Box.createHorizontalStrut(10));
		b3.add(txtSDT);
		b3.add(Box.createHorizontalStrut(55));
		b3.add(lblLuong);
		b3.add(Box.createHorizontalStrut(10));
		b3.add(txtLuong);

		lblTuoi = new JLabel("Tuổi:");
		txtTuoi = new JTextField();
		b4.add(lblTuoi);
		b4.add(Box.createHorizontalStrut(20));
		b4.add(txtTuoi);
		b4.add(Box.createHorizontalStrut(550));

		lblLuong.setPreferredSize(lblTenNV.getPreferredSize());
		lblTuoi.setPreferredSize(lblSDT.getPreferredSize());

//		Thêm các Box vào giao diện chính
		pnThem.add(b);
		b.add(b1);
//		Box.createRigidArea(new DimensionUIResource(0, 20)): tạo khoảng cách giữa label trên và label dưới
		b.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
		b.add(b2);
		b.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
		b.add(b3);
		b.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
		b.add(b4);
		b.add(Box.createRigidArea(new DimensionUIResource(0, 20)));

//		Tạo các chức năng như thêm, xóa, xóa trắng, cập nhật, lưu, thoát
		pnSouth = new JPanel();
		pnContent.add(pnSouth, BorderLayout.SOUTH);
//		pnSouth.setBorder(BorderFactory.createTitledBorder("Chọn chức năng"));
		btnThem = new JButton("Thêm");
//		Đổi màu nền của button
		btnThem.setBackground(mauNen);
//		Đổi màu chữ
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
//		Thêm các chức năng đó vào Giao diện
		pnSouth.add(btnThem);
		pnSouth.add(Box.createHorizontalStrut(30));
//		pnSouth.add(btnXoa);
//		pnSouth.add(Box.createHorizontalStrut(30));
		pnSouth.add(btnXoaTrang);
		pnSouth.add(Box.createHorizontalStrut(30));
		pnSouth.add(btnCapNhat);
		pnSouth.add(Box.createHorizontalStrut(30));
		pnSouth.add(btnLuu);
		pnSouth.add(Box.createHorizontalStrut(30));
		pnSouth.add(btnThoat);

//		Khởi tạo bảng
		pnTable = new JPanel();
//		createTable(): là đem public void createTable() lên đây thì nó mới thực thi trong giao diện được
		createTable();
//		Thêm bảng vào trong giao diện
		pnSouth.add(pnTable);
//		Tạo khung bao và tiêu đề cho bẳng
		TitledBorder titledBorder1 = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(mauNen),
				"Danh sách nhân viên ");
		titledBorder1.setTitleJustification(TitledBorder.CENTER);
		pnTable.setBorder(titledBorder1);

//		pnContent = new JPanel();
//		pnContent.setLayout(new BorderLayout()); ở phía trên chỉ mới khai báo
		this.add(pnContent);

		btnThem.addActionListener(this);
		btnXoa.addActionListener(this);
		btnXoaTrang.addActionListener(this);
		btnCapNhat.addActionListener(this);
		btnLuu.addActionListener(this);
		btnThoat.addActionListener(this);

		khoaTxt();
	}

//	Khởi tạo bảng
	public void createTable() {
//		khai báo model để tạo 1 khung table rỗng 
		model = new DefaultTableModel();
//		Giờ khai báo table này để add model vào rỗng nè
		table = new JTable(model);
//		addColumn là thêm tiêu đè cột thôi
		model.addColumn("Mã nhân viên");
		model.addColumn("Tên nhân viên");
		model.addColumn("SĐT");
		model.addColumn("Tuổi");
		model.addColumn("Giới tính");
		model.addColumn("Bộ phận");
		model.addColumn("Lương/ca(VNĐ)");

		try {
			for (NhanVien nhanVien : nv.getAllNhanVien()) {
				Object[] row = { nhanVien.getIDNhanVien(), nhanVien.getTenNV(), nhanVien.getSDT(), nhanVien.getTuoi(),
						nhanVien.getGioiTinh(), nhanVien.getBoPhan(), tienTeVN.format(nhanVien.getLuong()) };
				model.addRow(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

//		Thêm bảng vào Panel
		pnTable.add(table);
		JScrollPane sp = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//		Tạo kích thước của bảng
		sp.setPreferredSize(new Dimension(850, 300));
		pnTable.add(sp);

//		Đổi màu xanh của thanh tiêu đề
		DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
		headerRenderer.setBackground(mauBang);
		table.getTableHeader().setDefaultRenderer(headerRenderer);
//		Đổi màu chữ của tiêu đề 
		headerRenderer.setForeground(Color.WHITE);
//		Đổi màu viền của hàng và cột
		table.setGridColor(mauNen);

//		Click chuột vào bảng thì dữ liệu sẽ chuyển lên trên JTextField
		table.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				int row = table.getSelectedRow();
				txtMaNV.setText(model.getValueAt(row, 0).toString());
				txtTenNV.setText(model.getValueAt(row, 1).toString());
				txtSDT.setText(model.getValueAt(row, 2).toString());
				txtTuoi.setText(model.getValueAt(row, 3).toString());
				cmbBoPhan.setSelectedItem(model.getValueAt(row, 5).toString());
				txtLuong.setText(model.getValueAt(row, 6).toString());
				String gioiTinh = (model.getValueAt(row, 4).toString());
				if(gioiTinh.equals("Nam")) {
					radNam.setSelected(true);
				}else
					radNu.setSelected(true);
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
		new NhanVien_GUI().setVisible(true);
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
		} else if (o.equals(btnXoa)) {
			try {
				xoa();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else if (o.equals(btnXoaTrang)) {
			xoaRong();
		} else if (o.equals(btnLuu)) {
			int row = table.getSelectedRow();

			String maNVMoi = txtMaNV.getText();
			boolean maDaTonTai = false;

			for (NhanVien nhanVien : nv.getAllNhanVien()) {
				if (nhanVien.getIDNhanVien().contains(maNVMoi)) {
					maDaTonTai = true;
					break;
				}
			}
			if (btnThem.getText().equals("Hủy")) {
//				Kiểm tra tồn tại mã khi thêm vào bảng
				if (maDaTonTai == true) {
					JOptionPane.showMessageDialog(this, "Mã nhân viên đã tồn tại");
				} else {
					if (kTraLoi() == true) {
						String gt;
						if(radNam.isSelected())
							gt = "Nam";
						else
							gt = "Nữ";
						NhanVien nhanVien = new NhanVien(txtMaNV.getText(), txtTenNV.getText(), txtSDT.getText(),
								Integer.parseInt(txtTuoi.getText()), cmbBoPhan.getSelectedItem().toString(),
								Float.parseFloat(txtLuong.getText()), gt);
						nv.addNhanVien(nhanVien);
						JOptionPane.showMessageDialog(this, "Lưu thành công!");
						String[] data = { txtMaNV.getText(), txtTenNV.getText(), txtSDT.getText(), txtTuoi.getText(),
								gt, cmbBoPhan.getSelectedItem().toString(), txtLuong.getText() };
						model.addRow(data);
						reset();
					}
				}
			} else if (btnCapNhat.getText().equals("Hủy")) {
				if (kTraLoi() == true) {
					String gt;
					if(radNam.isSelected())
						gt = "Nam";
					else
						gt = "Nữ";
					NhanVien nhanVien = new NhanVien(txtMaNV.getText(), txtTenNV.getText(), txtSDT.getText(),
							Integer.parseInt(txtTuoi.getText()), cmbBoPhan.getSelectedItem().toString(),
							Float.parseFloat(txtLuong.getText()), gt);
////					Thêm dòng mới vào Jtable
					String[] data = { txtMaNV.getText(), txtTenNV.getText(), txtSDT.getText(), txtTuoi.getText(),
							gt, cmbBoPhan.getSelectedItem().toString(), txtLuong.getText() };
					model.insertRow(row, data);
					model.removeRow(row + 1);
					nv.updateNhanVien(nhanVien);
					JOptionPane.showMessageDialog(this, "Lưu thành công");
					reset();
				}
			}
		} else if (o.equals(btnCapNhat)) {
			int row = table.getSelectedRow();
			if (row != -1) {
				if (btnCapNhat.getText().equals("Sửa")) {
					btnCapNhat.setText("Hủy");
					moKhoaTxt();
					btnLuu.setEnabled(true);
					btnThem.setEnabled(false);
					btnXoa.setEnabled(false);
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
		String ma = txtMaNV.getText();
		String ten = txtTenNV.getText();
		String tuoi = txtTuoi.getText();
		String luong = txtLuong.getText();
		if (ma.equals("") || ten.equals("") || tuoi.equals("") || luong.equals("")) {
			JOptionPane.showMessageDialog(this, "Dữ liệu không được để trống");
			return false;
		}
		if (!ma.matches("NV\\d{3}")) {
			JOptionPane.showMessageDialog(this, "Mã nhân viên lỗi! Theo dạng NVxxx với x từ [0-9]");
			txtMaNV.requestFocus();
			return false;
		}
		try {
			if (Integer.parseInt(tuoi) < 18) {
				JOptionPane.showMessageDialog(this, "Tuổi phải từ đủ 18");
				txtLuong.requestFocus();
				return false;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Tuổi phải nhập vào số");
			return false;
		}
		try {
			if (Float.parseFloat(luong) < 0) {
				JOptionPane.showMessageDialog(this, "Lương phải lớn hơn 0");
				txtLuong.requestFocus();
				return false;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Lương phải nhập vào số");
			return false;
		}
		return true;
	}

	public void moKhoaTxt() {
		if (btnCapNhat.getText().equals("Hủy"))
			txtMaNV.setEditable(false);
		else
			txtMaNV.setEditable(true);
		txtTenNV.setEditable(true);
		txtSDT.setEditable(true);
		txtTuoi.setEditable(true);
		txtLuong.setEditable(true);
		cmbBoPhan.setEnabled(true);
	}

	public void khoaTxt() {
		txtMaNV.setEditable(false);
		txtTenNV.setEditable(false);
		txtSDT.setEditable(false);
		txtTuoi.setEditable(false);
		txtLuong.setEditable(false);
		cmbBoPhan.setEnabled(false);
	}

	public void xoaRong() {
		txtMaNV.setText("");
		txtTenNV.setText("");
		txtSDT.setText("");
		txtLuong.setText("");
		txtTuoi.setText("");
		txtMaNV.requestFocus();
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
		int r = table.getSelectedRow();
		if (r != -1) {
			int tb = JOptionPane.showConfirmDialog(null, "Bạn có chắc xóa thông tin nhân viên này không!", "delete",
					JOptionPane.YES_NO_CANCEL_OPTION);
			if (tb == JOptionPane.YES_OPTION) {
				String ma = txtMaNV.getText();
				nv.deleteNhanVien(ma);
				model.removeRow(r);
			}
		} else
			JOptionPane.showMessageDialog(null, "bạn chưa chọn dòng muốn xóa");
	}
}
