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
import javax.swing.text.Utilities;

import com.microsoft.sqlserver.jdbc.SQLServerConnection;

import connectDB.ConnectDB;
import dao.Phong_DAO;
import entity.Phong;

public class QLPhong_GUI extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JLabel lblMaPhong, lblTenPhong, lblLoaiPhong, lblThietBi, lblTinhTrang, lblGhiChu, lblGiaPhong;
	private JPanel pnContent, pnNorth, pnCenter, pnThem, pnSouth, pnTable;
	private JTextField txtMaPhong, txtTenPhong, txtThietBi, txtGhiChu;
	private JComboBox cbTinhTrang, cbLoaiPhong, cbGiaPhong;
	private JButton btnThem, btnXoa, btnXoaTrang, btnSua, btnLuu, btnThoat;
	private DefaultTableModel model;
	private JTable table;
	
	private Phong_DAO p;
	Color mauNen = new Color(205, 38, 38);// màu đỏ
	Color mauBang = new Color(99, 184, 255);// màu lam
	
	/***
	 * Chỉnh sửa tiền tệ
	 */
	Locale localeVN = new Locale("vi", "VN");
	NumberFormat tienTeVN = NumberFormat.getCurrencyInstance(localeVN);

	//	Lấy dữ liêu từ CSDL vào combobox
	
	public QLPhong_GUI() throws Exception{
//		Kết nối SQL
		try {
			ConnectDB.getInstance().connect();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		p = new Phong_DAO();
		createGui();
	}

	public void createGui() {
		setTitle("Phòng");
		setSize(850, 650);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(true);
		setLocationRelativeTo(null);
		quanLyPhong();
	}
	
	public void quanLyPhong() {
		pnContent = new JPanel();
		pnContent.setLayout(new BorderLayout());
		
		pnNorth = new JPanel();
		pnContent.add(pnNorth, BorderLayout.NORTH);
		
		JLabel lblTitle = new JLabel("Quản lý thông tin phòng");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
//		Đổi màu chữ
		lblTitle.setForeground(Color.WHITE);
		
		pnNorth.add(lblTitle);
//		Đổi màu nền
		pnNorth.setBackground(mauNen);
		
		pnCenter = new JPanel();
		pnContent.add(pnCenter, BorderLayout.CENTER);

		pnThem = new JPanel();
		pnCenter.add(pnThem);
		
		Box b = Box.createVerticalBox();
		Box b1 = Box.createHorizontalBox();
		Box b2 = Box.createHorizontalBox();
		Box b3 = Box.createHorizontalBox();
		Box b4 = Box.createHorizontalBox();
		TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(mauNen),
				"Thông tin phòng");
//		Canh giữa "Thông tin dịch vụ"
		titledBorder.setTitleJustification(TitledBorder.CENTER);
//		Thêm border "Thông tin dịch vụ" vào Giao diện
		pnThem.setBorder(titledBorder);
		
		lblMaPhong = new JLabel("IDPhong");
		txtMaPhong = new JTextField(2);
		
		lblTenPhong = new JLabel("Tên phòng");
		txtTenPhong = new JTextField();
		lblThietBi = new JLabel("Thiết bị");
		txtThietBi = new JTextField();
		lblTinhTrang = new JLabel("Tình trạng");
		cbTinhTrang = new JComboBox<>();
		cbTinhTrang.addItem("");
		cbTinhTrang.addItem("Trống");
		cbTinhTrang.addItem("Đã đặt");
		cbTinhTrang.addItem("Bảo trì");
		lblLoaiPhong = new JLabel("Loại phòng");
		cbLoaiPhong = new JComboBox<>();
		cbLoaiPhong.addItem("");
		cbLoaiPhong.addItem("Standard (1-7 khách)");
		cbLoaiPhong.addItem("Superior (7-15 khách)");
		cbLoaiPhong.addItem("Deluxe (trên 15 khách)");
		lblGhiChu = new JLabel("Ghi chú");
		txtGhiChu = new JTextField(15);
		lblGiaPhong = new JLabel("Giá phòng mỗi giờ theo ca(VND)");
		cbGiaPhong = new JComboBox<>();
		cbGiaPhong.addItem("");
		cbGiaPhong.addItem("25000.0");
		cbGiaPhong.addItem("26000.0");
		cbGiaPhong.addItem("27000.0");
		
		b1.add(lblMaPhong);
		b1.add(Box.createHorizontalStrut(22));
		b1.add(txtMaPhong);
		b1.add(Box.createHorizontalStrut(60));
		b1.add(lblThietBi);
		b1.add(Box.createHorizontalStrut(10));
		b1.add(txtThietBi);
		
		b2.add(lblTenPhong);
		b2.add(Box.createHorizontalStrut(10));
		b2.add(txtTenPhong);
		b2.add(Box.createHorizontalStrut(60));
		b2.add(lblTinhTrang);
		b2.add(Box.createHorizontalStrut(50));
		b2.add(cbTinhTrang);
		
		b3.add(lblLoaiPhong);
		b3.add(Box.createHorizontalStrut(10));
		b3.add(cbLoaiPhong);
		b3.add(Box.createHorizontalStrut(60));
		b3.add(lblGhiChu);
		b3.add(Box.createHorizontalStrut(10));
		b3.add(txtGhiChu);
		
		b4.add(Box.createHorizontalStrut(50));
		b4.add(lblGiaPhong);
		b4.add(Box.createHorizontalStrut(20));
		b4.add(cbGiaPhong);
		b4.add(Box.createHorizontalStrut(80));
		
		pnThem.add(b);
		b.add(b1);
//		Box.createRigidArea(new DimensionUIResource(0, 20)): tạo khoảng cách giữa label trên và label dưới
		b.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
		b.add(b2);
		b.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
		b.add(b3);
		b.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
		b.add(b4);
		
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
		
		pnTable = new JPanel();
//		createTable(): là đem public void createTable() lên đây thì nó mới thực thi trong giao diện được
		createTable();
//		Thêm bảng vào trong giao diện
		pnSouth.add(pnTable);
//		Tạo khung bao và tiêu đề cho bẳng
		TitledBorder titledBorder1 = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(mauNen),
				"Danh sách phòng ");
		titledBorder1.setTitleJustification(TitledBorder.CENTER);
		pnTable.setBorder(titledBorder1);

//		Nhớ phải có cái này:
//		pnContent = new JPanel();
//		pnContent.setLayout(new BorderLayout()); ở phía trên chỉ mới khai báo
//		Cái this.add(pnContent); mới thêm vô giao diện nha
		this.add(pnContent);
		
		btnThem.addActionListener(this);
		btnXoa.addActionListener(this);
		btnXoaTrang.addActionListener(this);
		btnSua.addActionListener(this);
		btnLuu.addActionListener(this);
		btnThoat.addActionListener(this);
	
		khoaTxt();
	}
	public void createTable() { 
		model = new DefaultTableModel();
//		Giờ khai báo table này để add model vào rỗng nè
		table = new JTable(model);
//		addColumn là thêm tiêu đè cột thôi
		model.addColumn("IDPhong");
		model.addColumn("Tên phòng");
		model.addColumn("Loại phòng");
		model.addColumn("Thiết bị");
		model.addColumn("Tình trạng");
		model.addColumn("Ghi chú");
		model.addColumn("Giá 1h/ca");
		
//		Thêm dữ liệu SQL vào bảng
		try {
			for (Phong phong : p.getAllPhong()) {
				Object[] row = { phong.getMaPhong(), phong.getTenPhong(), 
						phong.getLoaiPhong(), phong.getThietBi(), phong.getTinhTrang(),
						phong.getGhiChu(), tienTeVN.format(phong.getGiaPhong())};
				model.addRow(row);
				}
			}catch(Exception e) {
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
		sp.setPreferredSize(new Dimension(750, 330));
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

			@Override
			public void mouseReleased(MouseEvent e) {
				int row = table.getSelectedRow();
				String loai;
				String tinhTrang;
				txtMaPhong.setText(model.getValueAt(row, 0).toString());
				txtTenPhong.setText(model.getValueAt(row, 1).toString());
				loai = (model.getValueAt(row, 2).toString());
				cbLoaiPhong.setSelectedItem(loai);
				txtThietBi.setText(model.getValueAt(row, 3).toString());
				tinhTrang = (model.getValueAt(row, 4).toString());
				cbTinhTrang.setSelectedItem(tinhTrang);
				txtGhiChu.setText(model.getValueAt(row, 5).toString());
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
		new QLPhong_GUI().setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(btnThem)) {
			if(btnThem.getText().equalsIgnoreCase("Thêm")) {
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
			}
		else if (o.equals(btnXoa)) {
			try {
				xoa();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else if (o.equals(btnXoaTrang)) {
			xoaRong();
		} else if (o.equals(btnLuu)) {
			int row = table.getSelectedRow();
			
			String maPhongMoi = txtMaPhong.getText();
			boolean maDaTonTai = false;
			
			for(Phong phong : p.getAllPhong()) {
				if(phong.getMaPhong().contains(maPhongMoi)) {
					maDaTonTai = true;
					break;
				}
			}
			if(btnThem.getText().equals("Hủy")){
//				Kiểm tra tồn tại mã khi thêm vào bảng
				if(maDaTonTai == true) {
					JOptionPane.showMessageDialog(this, "Mã phòng đã tồn tại");	
				}else {
					if(kTraLoi() == true) {
						String loai = (String) cbLoaiPhong.getSelectedItem();
						String tinhTrang = (String) cbTinhTrang.getSelectedItem();
						String gia = cbGiaPhong.getSelectedItem().toString();
						Phong phong = new Phong(txtMaPhong.getText(), txtTenPhong.getText(),
								loai, txtThietBi.getText(), tinhTrang, txtGhiChu.getText(),
								Float.parseFloat(gia));
						p.addPhong(phong);
						JOptionPane.showMessageDialog(this, "Lưu thành công!");
						String[] data = { txtMaPhong.getText(), txtTenPhong.getText(), loai,
								txtThietBi.getText(), tinhTrang, txtGhiChu.getText(),tienTeVN.format(Float.parseFloat(gia))};
						model.addRow(data);
						reset();
					}
						
					}
				}else if(btnSua.getText().equals("Hủy")) {
					if (kTraLoi() == true) {
						String loai = (String) cbLoaiPhong.getSelectedItem();
						String tinhTrang = (String) cbTinhTrang.getSelectedItem();
						String gia = cbGiaPhong.getSelectedItem().toString();
						Phong phong = new Phong(txtMaPhong.getText(), txtTenPhong.getText(), loai, txtThietBi.getText(),
								tinhTrang, txtGhiChu.getText(), Float.parseFloat(gia));
						String[] data = { txtMaPhong.getText(), txtTenPhong.getText(), loai, txtThietBi.getText(),
								tinhTrang, txtGhiChu.getText(), String.valueOf(gia) };
						model.insertRow(row, data);
						model.removeRow(row + 1);
						p.updatePhong(phong);
						JOptionPane.showMessageDialog(this, "Lưu thành công");
						reset();
					}
					
			}
		}else if(o.equals(btnSua)) {
			int row = table.getSelectedRow();
			if(row != -1) {
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
			}else {
				JOptionPane.showMessageDialog(this, "Bạn chưa chọn dòng để sửa !");
			}
		}else if (o.equals(btnThoat)) {
			dispose();
		}
	}

	public void xoa() throws Exception{
		// TODO Auto-generated method stub
		int r = table.getSelectedRow();
		if (r != -1) {
			int tb = JOptionPane.showConfirmDialog(null, "Bạn có chắc xóa dịch vụ này không!", "delete",
					JOptionPane.YES_NO_CANCEL_OPTION);
			if (tb == JOptionPane.YES_OPTION) {
				String ma = txtMaPhong.getText();
			p.deletePhong(ma);
			model.removeRow(r);
			}
		} else
			JOptionPane.showMessageDialog(null, "Bạn chưa chọn dòng muốn xóa");
	}

	public void xoaRong() {
		// TODO Auto-generated method stub
		txtMaPhong.setText("");
		txtTenPhong.setText("");
		txtGhiChu.setText("");
		txtThietBi.setText("");
		cbGiaPhong.setSelectedItem(null);
		cbLoaiPhong.setSelectedItem(null);
		cbTinhTrang.setSelectedItem(null);
	}

	public boolean kTraLoi() {
		// TODO Auto-generated method stub
		String ma = txtMaPhong.getText();
		String loai = (String) cbLoaiPhong.getSelectedItem();
		String ghiChu = txtGhiChu.getText();
		String tinhTrang = (String) cbTinhTrang.getSelectedItem();
		String thietBi = txtThietBi.getText();
		String gia = (String) cbGiaPhong.getSelectedItem();
		String ten = txtTenPhong.getText();
		if (ma.equals("") || ten.equals("") || 
				gia.equals("") || tinhTrang.equals("") ||loai.equals("")) {
			JOptionPane.showMessageDialog(this, "Dữ liệu không được để trống");
			return false;
		}
		if(!ma.matches("P\\d{3}")) {
			JOptionPane.showMessageDialog(this, "Mã phòng lỗi! Theo dạng Pxxx với x từ [0-9]");
			txtMaPhong.requestFocus();
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
			txtMaPhong.setEditable(false);
		else
			txtMaPhong.setEditable(true);
		txtTenPhong.setEditable(true);
		cbLoaiPhong.setEditable(true);
		txtThietBi.setEditable(true);
		cbTinhTrang.setEditable(true);
		txtGhiChu.setEditable(true);
		cbGiaPhong.setEditable(true);
	}

	public void khoaTxt() {
		// TODO Auto-generated method stub
		txtMaPhong.setEditable(false);
		txtTenPhong.setEditable(false);
		cbLoaiPhong.setEditable(false);
		txtThietBi.setEditable(false);
		cbTinhTrang.setEditable(false);
		txtGhiChu.setEditable(false);
		cbGiaPhong.setEditable(false);
	}
}
