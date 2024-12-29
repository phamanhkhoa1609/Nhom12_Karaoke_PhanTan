package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
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

public class DichVu_GUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel pnContent, pnCenter, pnNorth, pnSouth, pnThem, pnTable;
	private JLabel lblMaDichVu, lblTenDichVu, lblSoLuong, lblGia;
	private JTextField txtMaDichVu, txtTenDichVu, txtSoLuong, txtGia;
	private DefaultTableModel model;
	private JTable table;
	private JButton btnThem, btnSua, btnXoa, btnXoaTrang, btnLuu, btnThoat;

	private DichVu_DAO dv;

	/***
	 * Chỉnh sửa tiền tệ
	 */
	Locale localeVN = new Locale("vi", "VN");
	NumberFormat tienTeVN = NumberFormat.getCurrencyInstance(localeVN);

//	Khai báo màu
	Color mauNen = new Color(205, 38, 38);// màu đỏ
	Color mauBang = new Color(99, 184, 255);// màu lam

	public DichVu_GUI() throws Exception {
//		setJMenuBar(MenuUtil.createMenu());
//		Kết nối với SQL
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dv = new DichVu_DAO();
		createGui();
	}

	public void createGui() {
//		Khởi tạo khung Giao diện
		setTitle("Danh sách dịch vụ");
		setSize(1000, 600);
//		setExtendedState(MAXIMIZED_BOTH);
//		Toan man hinh 
//		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(true);
//		Dua Giao dien ra giua man hinh
		setLocationRelativeTo(null);

		dichVu();
	}

	public void dichVu() {
//		Khai báo JPanel tổng
		pnContent = new JPanel();
		pnContent.setLayout(new BorderLayout());

//		Khai báo JPanel Tiêu đề
		pnNorth = new JPanel();
		pnContent.add(pnNorth, BorderLayout.NORTH);

		JLabel lblTitle = new JLabel("Quản lý thông tin dịch vụ");
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

		pnThem = new JPanel();
		pnCenter.add(pnThem);
//		Tạo các Box để nó auto canh lable với textfield
		Box b = Box.createVerticalBox();
		Box b1 = Box.createHorizontalBox();
		Box b2 = Box.createHorizontalBox();
//		Box b3 = Box.createHorizontalBox();

//		b.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(mauNen), "Thông tin dịch vụ"));
//		BorderFactory.createLineBorder(mauNen): đổi màu khung bao
		TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(mauNen),
				"Thông tin dịch vụ");
//		Canh giữa "Thông tin dịch vụ"
		titledBorder.setTitleJustification(TitledBorder.CENTER);
//		Thêm border "Thông tin dịch vụ" vào Giao diện
		pnThem.setBorder(titledBorder);

//		Cái này khỏi cần giải thích há
//		À mà new JLabel, new JTextField: khởi tạo chứ chưa có thêm vô giao diện 
		lblMaDichVu = new JLabel("Mã dịch vụ: ");
		txtMaDichVu = new JTextField(30);
		b1.add(lblMaDichVu);
//		Box.createHorizontalStrut(10): tạo khoảng cách giữa Jlabel với JTextField
		b1.add(Box.createHorizontalStrut(10));
		b1.add(txtMaDichVu);
//		Box.createHorizontalStrut(50): tạo khoảng cách của "Mã dịch vụ" và "Số lượng" trên cùng 1 dòng 
		b1.add(Box.createHorizontalStrut(50));
		lblSoLuong = new JLabel("Số lượng: ");
		txtSoLuong = new JTextField(30);
		b1.add(lblSoLuong);
		b1.add(Box.createHorizontalStrut(10));
		b1.add(txtSoLuong);
//		Nếu thêm 2 value trên cùng 1 dòng thì add vào cùng 1 cái b1

		lblTenDichVu = new JLabel("Tên dịch vụ:   ");
		txtTenDichVu = new JTextField();
		b2.add(lblTenDichVu);
		b2.add(Box.createHorizontalStrut(4));
		b2.add(txtTenDichVu);
		b2.add(Box.createHorizontalStrut(50));
		lblGia = new JLabel("Chi phí($): ");
		txtGia = new JTextField();
		b2.add(lblGia);
		b2.add(txtGia);

//		Canh tiêu đề 'mã dịch vụ' bằng tiêu đề 'tên dịch vụ', 
//		canh cho nó đẹp thôi chứ kh cần thiết phải có
		lblMaDichVu.setPreferredSize(lblTenDichVu.getPreferredSize());
		lblGia.setPreferredSize(lblTenDichVu.getPreferredSize());

//		Thêm các Box vào giao diện chính
		pnThem.add(b);
		b.add(b1);
//		Box.createRigidArea(new DimensionUIResource(0, 20)): tạo khoảng cách giữa label trên và label dưới
		b.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
		b.add(b2);
		b.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
//		b.add(b3);
//		b.add(Box.createRigidArea(new DimensionUIResource(0, 20)));

//		Tạo các chức năng như thêm xóa sửa
		pnSouth = new JPanel();
		pnContent.add(pnSouth, BorderLayout.SOUTH);
//		pnSouth.setBorder(BorderFactory.createTitledBorder("Chọn chức năng"));
		btnThem = new JButton("Thêm");
//		Đổi màu nền của button
		btnThem.setBackground(mauNen);
//		Đổi màu chữ
//		new JButton chỉ là khởi tạo thôi, chứ nó chưa có thêm vô giao diện nha
		btnThem.setForeground(Color.WHITE);
		btnXoa = new JButton("Xóa");
		btnXoa.setBackground(mauNen);
		btnXoa.setForeground(Color.WHITE);
		btnXoaTrang = new JButton("Xóa trắng");
		btnXoaTrang.setBackground(mauNen);
		btnXoaTrang.setForeground(Color.white);
		btnSua = new JButton("Cập nhật");
		btnSua.setBackground(mauNen);
		btnSua.setForeground(Color.white);
		btnLuu = new JButton("Lưu");
		btnLuu.setBackground(mauNen);
		btnLuu.setForeground(Color.white);
		btnThoat = new JButton("Thoát");
		btnThoat.setBackground(mauNen);
		btnThoat.setForeground(Color.white);
//		Thêm các chức năng đó vào Giao diện
		pnSouth.add(btnThem);
		pnSouth.add(Box.createHorizontalStrut(30));
		pnSouth.add(btnXoa);
		pnSouth.add(Box.createHorizontalStrut(30));
		pnSouth.add(btnXoaTrang);
		pnSouth.add(Box.createHorizontalStrut(30));
		pnSouth.add(btnSua);
		pnSouth.add(Box.createHorizontalStrut(30));
		pnSouth.add(btnLuu);
		pnSouth.add(Box.createHorizontalStrut(30));
		pnSouth.add(btnThoat);

//		Khởi tạo bảng nèeeeee. Cái này khởi tạo để xíu xuống public void createTable() tạo bảng thôi
		pnTable = new JPanel();
//		createTable(): là đem public void createTable() lên đây thì nó mới thực thi trong giao diện được
		createTable();
//		Thêm bảng vào trong giao diện
		pnSouth.add(pnTable);
//		Tạo khung bao và tiêu đề cho bẳng
		TitledBorder titledBorder1 = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(mauNen),
				"Danh sách dịch vụ ");
		titledBorder1.setTitleJustification(TitledBorder.CENTER);
		pnTable.setBorder(titledBorder1);

//		Nhớ phải có cái này:
//		pnContent = new JPanel();
//		pnContent.setLayout(new BorderLayout()); ở phía trên chỉ mới khai báo
//		Cái this.add(pnContent); mới thêm vô giao diện nha
		this.add(pnContent);

//		MenuUtil.itemDangXuat.addActionListener(this);
		btnThem.addActionListener(this);
		btnXoa.addActionListener(this);
		btnXoaTrang.addActionListener(this);
		btnSua.addActionListener(this);
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
		model.addColumn("Mã dịch vụ");
		model.addColumn("Tên dịch vụ");
		model.addColumn("Số lượng");
		model.addColumn("Phí dịch vụ(VNĐ)");

//		Thêm dữ liệu từ SQL và bảng
		try {
			for (DichVu dichVu : dv.getAllDichVu()) {
				Object[] row = { dichVu.getMaDichVu(), dichVu.getTenDichVu(), dichVu.getSoLuong(),
						tienTeVN.format(dichVu.getGia()) };
//				Object[] row = { dichVu.getMaDichVu(), dichVu.getTenDichVu(),
//						dichVu.getSoLuong(), dichVu.getGia()};
				model.addRow(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

//		Chỉnh cỡ chữ của dữ liệu trong bảng
		Font fontTable = new Font("Arial", Font.PLAIN, 14);
		table.setFont(fontTable);

//		Giờ mới thêm bảng vào Panel nè
		pnTable.add(table);
//		JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
//		JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED 2 cái này là thêm cái thanh trên dưới á
//		Nói sao ta thôi thử xóa 2 cái đó đi là biết:>>
		JScrollPane sp = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//		Tạo kích thước của bảng
		sp.setPreferredSize(new Dimension(950, 350));
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
				txtMaDichVu.setText(model.getValueAt(row, 0).toString());
				txtTenDichVu.setText(model.getValueAt(row, 1).toString());
				txtSoLuong.setText(model.getValueAt(row, 2).toString());
				try {
					Number chuyenTienSangSo = tienTeVN.parse(model.getValueAt(row, 3).toString());
					double soTien = chuyenTienSangSo.doubleValue();
					txtGia.setText(String.valueOf(soTien));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
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
		new DichVu_GUI().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnThem)) {
			if (btnThem.getText().equalsIgnoreCase("Thêm")) {
				moKhoaTxt();
				btnThem.setText("Hủy");
				btnLuu.setEnabled(true);
				btnSua.setEnabled(false);
				btnXoa.setEnabled(false);
				btnXoaTrang.setEnabled(false);
			} else {
				khoaTxt();
				btnThem.setText("Thêm");
				btnLuu.setEnabled(false);
				btnSua.setEnabled(true);
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

			String maDichVuMoi = txtMaDichVu.getText();
			boolean maDaTonTai = false;

			for (DichVu dichVu : dv.getAllDichVu()) {
				if (dichVu.getMaDichVu().contains(maDichVuMoi)) {
					maDaTonTai = true;
					break;
				}
			}
			if (btnThem.getText().equals("Hủy")) {
//				Kiểm tra tồn tại mã khi thêm vào bảng
				if (maDaTonTai == true) {
					JOptionPane.showMessageDialog(this, "Mã dịch vụ đã tồn tại");
				} else {
					if (kTraLoi() == true) {
						DichVu dichVu = new DichVu(txtMaDichVu.getText(), txtTenDichVu.getText(),
								Integer.parseInt(txtSoLuong.getText()), Float.parseFloat(txtGia.getText()));
						dv.addDichVu(dichVu);
						JOptionPane.showMessageDialog(this, "Lưu thành công!");
						String[] data = { txtMaDichVu.getText(), txtTenDichVu.getText(), txtSoLuong.getText(),
								txtGia.getText() };
						model.addRow(data);
						reset();
					}
				}
			} else if (btnSua.getText().equals("Hủy")) {
				if (kTraLoi() == true) {
					DichVu dichVu = new DichVu(txtMaDichVu.getText(), txtTenDichVu.getText(),
							Integer.parseInt(txtSoLuong.getText()), Float.parseFloat(txtGia.getText()));
					String[] data = { txtMaDichVu.getText(), txtTenDichVu.getText(), txtSoLuong.getText(),
							txtGia.getText() };
//				Thêm dòng mới vào Jtable
					model.insertRow(row, data);
					model.removeRow(row + 1);
					dv.updateDichVu(dichVu);
					JOptionPane.showMessageDialog(this, "Lưu thành công");
					reset();
				}
				
			}
		} else if (o.equals(btnSua)) {
			int row = table.getSelectedRow();
			if (row != -1) {
				if (btnSua.getText().equals("Sửa")) {
					btnSua.setText("Hủy");
					moKhoaTxt();
					btnLuu.setEnabled(true);
					btnThem.setEnabled(false);
					btnXoa.setEnabled(false);
				} else {
					khoaTxt();
					btnSua.setText("Sửa");
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
		String ma = txtMaDichVu.getText();
		String ten = txtTenDichVu.getText();
		String soLuong = txtSoLuong.getText();
		String gia = txtGia.getText();
		if (ma.equals("") || ten.equals("") || soLuong.equals("") || gia.equals("")) {
			JOptionPane.showMessageDialog(this, "Dữ liệu không được để trống");
			return false;
		}
		if (!ma.matches("DV\\d{3}")) {
			JOptionPane.showMessageDialog(this, "Mã dịch vụ lỗi! Theo dạng DVxxx với x từ [0-9]");
			txtMaDichVu.requestFocus();
			return false;
		}
		try {
			if (Integer.parseInt(soLuong) < 0) {
				JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0");
				txtSoLuong.requestFocus();
				return false;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Số lượng phải nhập vào số");
			return false;
		}
		try {
			if (Float.parseFloat(gia) < 0) {
				JOptionPane.showMessageDialog(this, "Giá phải lớn hơn 0");
				txtGia.requestFocus();
				return false;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Giá phải nhập vào số");
			return false;
		}
		return true;
	}

	public void moKhoaTxt() {
		if (btnSua.getText().equals("Hủy"))
			txtMaDichVu.setEditable(false);
		else
			txtMaDichVu.setEditable(true);
		txtTenDichVu.setEditable(true);
		txtSoLuong.setEditable(true);
		txtGia.setEditable(true);

	}

	public void khoaTxt() {
		txtMaDichVu.setEditable(false);
		txtTenDichVu.setEditable(false);
		txtSoLuong.setEditable(false);
		txtGia.setEditable(false);
	}

	public void xoaRong() {
		txtMaDichVu.setText("");
		txtTenDichVu.setText("");
		txtSoLuong.setText("");
		txtGia.setText("");
		txtMaDichVu.requestFocus();
	}

	public void reset() {
		khoaTxt();

		if (btnThem.getText().equals("Hủy")) {
			btnThem.setText("Thêm");
			btnLuu.setEnabled(false);
			btnSua.setEnabled(true);
			btnXoa.setEnabled(true);
			btnXoaTrang.setEnabled(true);
		}

		if (btnSua.getText().equals("Hủy")) {
			btnSua.setText("Sửa");
			btnLuu.setEnabled(false);
			btnThem.setEnabled(true);
			btnXoa.setEnabled(true);
			btnXoaTrang.setEnabled(true);
		}
	}

	public void xoa() throws Exception {
		int r = table.getSelectedRow();
		if (r != -1) {
			int tb = JOptionPane.showConfirmDialog(null, "Bạn có chắc xóa dịch vụ này không!", "delete",
					JOptionPane.YES_NO_CANCEL_OPTION);
			if (tb == JOptionPane.YES_OPTION) {
				String ma = txtMaDichVu.getText();
			dv.deleteDichVu(ma);
			model.removeRow(r);
			}
		} else
			JOptionPane.showMessageDialog(null, "bạn chưa chọn dòng muốn xóa");
	}
}