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
import dao.KhachHang_DAO;
import entity.KhachHang;

public class KhachHang_GUI extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JButton btnSua, btnThem, btnXoa, btnXoaTrang, btnLuu, btnThoat;
	private JPanel pnContent, pnCenter, pnNorth, pnSouth, pnThem, pnTable;
	private JLabel lblMaKH, lblTenKH, lblSDT, lblTuoi, lblGioiTinh, lblTrangThai;
	private JRadioButton radNam, radNu;
	private JComboBox<String> cbTrangThai;
	private JTextField txtMaKH, txtTenKH, txtSDT, txtTuoi;
	private DefaultTableModel model;
	private JTable table;
	private ButtonGroup bg;
	private KhachHang_DAO kh;
	
//	Khai báo màu
	Color mauNen = new Color(205, 38, 38);// màu đỏ
	Color mauBang = new Color(99, 184, 255);// màu lam
	
	public KhachHang_GUI() throws Exception{
		try {
			ConnectDB.getInstance().connect();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		kh = new KhachHang_DAO();
		createGui();
	}

	public void createGui() {
//		Khởi tạo khung Giao diện
		setTitle("Danh sách khách hàng");
		setSize(800, 600);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(true);
		setLocationRelativeTo(null);
		khachHang();
	}
	public void khachHang() {
//		Khai báo JPanel tổng
		pnContent = new JPanel();
		pnContent.setLayout(new BorderLayout());

//		Khai báo JPanel Tiêu đề
		pnNorth = new JPanel();
		pnContent.add(pnNorth, BorderLayout.NORTH);

		JLabel lblTitle = new JLabel("Quản lý thông tin khách hàng");
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
		Box b3 = Box.createHorizontalBox();
		Box b4 = Box.createHorizontalBox();
		
//		b.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(mauNen), "Thông tin khách hàng"));
//		BorderFactory.createLineBorder(mauNen): đổi màu khung bao
		TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(mauNen),
				"Thông tin khách hàng");
//		Canh giữa "Thông tin khách hàng"
		titledBorder.setTitleJustification(TitledBorder.CENTER);
//		Thêm border "Thông tin khách hàng" vào Giao diện
		pnThem.setBorder(titledBorder);
		
		lblMaKH = new JLabel("Mã khách hàng");
		txtMaKH = new JTextField(20);
		b1.add(lblMaKH);
//		Box.createHorizontalStrut(10): tạo khoảng cách giữa Jlabel với JTextField
		b1.add(Box.createHorizontalStrut(10));
		b1.add(txtMaKH);
		
//		Box.createHorizontalStrut(50): tạo khoảng cách của "Mã khách hàng" và "Giới tính" trên cùng 1 dòng 
		b1.add(Box.createHorizontalStrut(50));
		
		lblGioiTinh = new JLabel("Giới tính");
		radNam = new JRadioButton("Nam");
		radNu = new JRadioButton("Nữ");
		bg = new ButtonGroup();
		b1.add(Box.createHorizontalStrut(20));
		bg.add(radNam); bg.add(radNu);
		b1.add(lblGioiTinh);
		b1.add(Box.createHorizontalStrut(30));
		b1.add(radNam); b1.add(radNu);
		 
		lblTenKH = new JLabel("Tên khách hàng ");
		txtTenKH = new JTextField();
		b2.add(lblTenKH);
		b2.add(Box.createHorizontalStrut(10));
		b2.add(txtTenKH);
		b2.add(Box.createHorizontalStrut(40));
		lblSDT = new JLabel("SDT");
		txtSDT = new JTextField();
		b2.add(lblSDT);
		b2.add(Box.createHorizontalStrut(10));
		b2.add(txtSDT);
		
		lblTuoi = new JLabel("        Tuổi");
		txtTuoi = new JTextField(10);
		b3.add(lblTuoi);
		b3.add(Box.createHorizontalStrut(10));
		b3.add(txtTuoi);
		b3.add(Box.createHorizontalStrut(50));
		lblTrangThai = new JLabel("Trạng thái");
		String trangthai[] = {"Đã thanh toán", "Chưa thanh toán"};
		cbTrangThai = new JComboBox(trangthai);
		cbTrangThai.setBounds(100, 50, 150, 20);
		b3.add(lblTrangThai);
		b3.add(Box.createHorizontalStrut(10));
		b3.add(cbTrangThai);
		
//		Canh tiêu đề 'mã khách hàng' bằng tiêu đề 'tên khách hàng', 
//		...
		lblMaKH.setPreferredSize(lblTenKH.getPreferredSize());
		lblTuoi.setPreferredSize(lblTenKH.getPreferredSize());
//		lblSDT.setPreferredSize(lblTrangThai.getPreferredSize());
		
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
//		pnSouth.add(btnXoa);
//		pnSouth.add(Box.createHorizontalStrut(30));
		pnSouth.add(btnXoaTrang);
		pnSouth.add(Box.createHorizontalStrut(30));
		pnSouth.add(btnSua);
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
				"Danh sách khách hàng ");
		titledBorder1.setTitleJustification(TitledBorder.CENTER);
		pnTable.setBorder(titledBorder1);
		
//		pnContent = new JPanel();
//		pnContent.setLayout(new BorderLayout()); ở phía trên chỉ mới khai báo
		this.add(pnContent);
		
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
		model.addColumn("Mã khách hàng");
		model.addColumn("Tên khách hàng");
		model.addColumn("Tuổi");
		model.addColumn("Giới tính");
		model.addColumn("SĐT");
		model.addColumn("Trạng thái");

//		Thêm dữ liệu từ SQL và bảng
		try {
			for(KhachHang khachHang : kh.getAllKhachHang()) {
				Object[] row = { khachHang.getMaKH(), khachHang.getTenKH(),
						khachHang.getTuoi(), khachHang.getGioiTinh(),
						khachHang.getsDT(), khachHang.getTrangThai()};
				model.addRow(row);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

//		Chỉnh cỡ chữ của dữ liệu trong bảng
		Font fontTable = new Font("Arial", Font.PLAIN, 14);
		table.setFont(fontTable);
		
//		Thêm bảng vào Panel
		pnTable.add(table);
		JScrollPane sp = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//		Tạo kích thước của bảng
		sp.setPreferredSize(new Dimension(750, 300));
		pnTable.add(sp);
		
//		Đổi màu xanh của thanh tiêu đề
		DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
		headerRenderer.setBackground(mauBang);
		table.getTableHeader().setDefaultRenderer(headerRenderer);
//		Đổi màu chữ của tiêu đề 
		headerRenderer.setForeground(Color.WHITE);
//		Đổi màu viền của hàng và cột
		table.setGridColor(mauNen);
		table.addMouseListener(new MouseListener() {
//		Click chuột vào bảng thì dữ liệu sẽ chuyển lên trên JTextField
		
//		@Override
		public void mouseReleased(MouseEvent e) {			
			int row = table.getSelectedRow();
			txtMaKH.setText(model.getValueAt(row, 0).toString());
			txtTenKH.setText(model.getValueAt(row, 1).toString());
			txtTuoi.setText(model.getValueAt(row, 2).toString());
			String gioiTinh = (model.getValueAt(row, 3).toString());
			if(gioiTinh.equals("Nam")) {
				radNam.setSelected(true);
			}else
				radNu.setSelected(true);
			txtSDT.setText(model.getValueAt(row, 4).toString());
			String trangThai = (model.getValueAt(row, 5).toString());
			cbTrangThai.setSelectedItem(trangThai);
		}

//		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

//		@Override
		public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

		}

//		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

//		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}
	});
}

	public static void main(String[] args) throws Exception {
		new KhachHang_GUI().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
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

			String maKHMoi = txtMaKH.getText();
			boolean maDaTonTai = false;

			for (KhachHang khachHang : kh.getAllKhachHang()) {
				if (khachHang.getMaKH().contains(maKHMoi)) {
					maDaTonTai = true;
					break;
				}
			}
			if (btnThem.getText().equals("Hủy")) {
				if (maDaTonTai == true) {
					JOptionPane.showMessageDialog(this, "Mã khách hàng đã tồn tại");
				} else {
					if (kTraLoi() == true) {
						String tinhTrang = (String) cbTrangThai.getSelectedItem();
						String gt;
						if(radNam.isSelected())
							gt = "Nam";
						else
							gt = "Nữ";
						KhachHang khachHang = new KhachHang(txtMaKH.getText(), txtTenKH.getText(),
								Integer.parseInt(txtTuoi.getText()), gt, txtSDT.getText(),
								tinhTrang);
						kh.addKhachHang(khachHang);
						JOptionPane.showMessageDialog(this, "Lưu thành công!");
						String[] data = { txtMaKH.getText(), txtTenKH.getText(), txtTuoi.getText(),
								gt, txtSDT.getText(), tinhTrang };
						model.addRow(data);
						reset();
					}
				}
			} else if (btnSua.getText().equals("Hủy")) {
				if (kTraLoi() == true) {
					String tinhTrang = (String) cbTrangThai.getSelectedItem();
					String gt;
					if (radNam.isSelected())
						gt = "Nam";
					else
						gt = "Nữ";
					KhachHang khachHang = new KhachHang(txtMaKH.getText(), txtTenKH.getText(),
							Integer.parseInt(txtTuoi.getText()), gt, txtSDT.getText(), tinhTrang);
					String[] data = { txtMaKH.getText(), txtTenKH.getText(), txtTuoi.getText(), gt, txtSDT.getText(),
							tinhTrang };
//				Thêm dòng mới vào Jtable
					model.insertRow(row, data);
					model.removeRow(row + 1);
					kh.updateKhachHang(khachHang);
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
	private void xoaRong() {
		// TODO Auto-generated method stub
		txtMaKH.setText("");
		txtTenKH.setText("");
		txtTuoi.setText("");
		radNam.setDisabledSelectedIcon(null);
		radNu.setDisabledSelectedIcon(null);
		txtSDT.setText("");
		cbTrangThai.setSelectedItem(null);
	}

	private void xoa() {
		// TODO Auto-generated method stub
		int r = table.getSelectedRow();
		if (r != -1) {
			int tb = JOptionPane.showConfirmDialog(null, "Bạn có chắc xóa khách hàng này không!", "delete",
					JOptionPane.YES_NO_CANCEL_OPTION);
			if (tb == JOptionPane.YES_OPTION) {
				String ma = txtMaKH.getText();
			kh.deleteKhachHang(ma);
			model.removeRow(r);
			}
		} else
			JOptionPane.showMessageDialog(null, "Bạn chưa chọn dòng muốn xóa");
	}

	public boolean kTraLoi() {
		String ma = txtMaKH.getText();
		String ten = txtTenKH.getText();
		String tuoi = txtTuoi.getText();
		String sDT = txtSDT.getText();
		String trangThai = (String) cbTrangThai.getSelectedItem();
		if(ma.equals("") || ten.equals("") || tuoi.equals("") || 
				sDT.equals("") || trangThai.equals(""))
//				radNam.getSelectedIcon() == null && radNu.getSelectedIcon() == null)) {
			{JOptionPane.showMessageDialog(this, "Dữ liệu không được để trống");
		return false;
		}
		if(!ma.matches("KH\\d{6}")) {
			JOptionPane.showMessageDialog(this, "Mã khách hàng lỗi! Theo dạng KHxxxxxx với x từ [0-9]");
			txtMaKH.requestFocus();
			return false;
		}
		try {
			if (Integer.parseInt(tuoi) < 0) {
				JOptionPane.showMessageDialog(this, "Tuổi phải lớn hơn 0");
				txtTuoi.requestFocus();
				return false;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Tuổi phải nhập vào số");
			return false;
		}
		try {
			if (Integer.parseInt(sDT) < 0 || sDT.length() != 10 || sDT.charAt(0) != '0') {
				JOptionPane.showMessageDialog(this, "Sai định dạng SDT");
				txtSDT.requestFocus();
				return false;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "SDT phải nhập vào số");
			return false;
		}
		return true;
	}
	public void reset() {
		// TODO Auto-generated method stub
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
	public void moKhoaTxt() {
		// TODO Auto-generated method stub
		if (btnSua.getText().equals("Hủy"))
			txtMaKH.setEditable(false);
		else
			txtMaKH.setEditable(true);
		txtTenKH.setEditable(true);
		txtTuoi.setEditable(true);
		radNam.setFocusable(true);
		radNu.setFocusable(true);
		txtSDT.setEditable(true);
		cbTrangThai.setEditable(true);
	}
	public void khoaTxt() {
		// TODO Auto-generated method stub
		txtMaKH.setEditable(false);
		txtTenKH.setEditable(false);
		txtTuoi.setEditable(false);
		radNam.setFocusable(false);
		radNu.setFocusable(false);
		txtSDT.setEditable(false);
		cbTrangThai.setEditable(false);
	}
}

