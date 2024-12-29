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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
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
import dao.CaTruc_DAO;
import entity.CaTruc;

public class QuanLyThongTinCaLamViec_GUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JButton btnThem, btnSua, btnXoa, btnXoaTrang, btnLuu, btnThoat;
	private JPanel pnContent, pnNorth, pnEast, pnWest, pnThem, pnTable;
	private JLabel lblIDCaTruc, lblCaTruc;
	private JRadioButton rad8h, rad13h, rad18h;
	private JTextField txtIDCaTruc;
	private DefaultTableModel model;
	private JTable table;
	private CaTruc_DAO ct;

//	Khai báo màu
	Color mauNen = new Color(205, 38, 38);// màu đỏ
	Color mauBang = new Color(99, 184, 255);// màu lam

	public QuanLyThongTinCaLamViec_GUI() throws Exception {
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ct = new CaTruc_DAO();
		createGui();
	}

	public void createGui() {
		setTitle("Quản lý thông tin ca làm việc");
		setSize(850, 400);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(true);
		setLocationRelativeTo(null);
		createCa();
	}

	public void createCa() {
//		Khai báo JPanel tổng
		pnContent = new JPanel();
		pnContent.setLayout(new BorderLayout());

//		Khai báo JPanel Tiêu đề
		pnNorth = new JPanel();
		pnContent.add(pnNorth, BorderLayout.NORTH);

		JLabel lblTitle = new JLabel("Quản lý thông tin ca làm việc");
//		Đổi kiểu chữ
		lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
//		Đổi màu chữ
		lblTitle.setForeground(Color.WHITE);
//		Thêm lblTitle vào Giao diện Tiêu đề (cái này quan trọng, không thêm lblTitle là nó không thêm Tiêu đề được)
		pnNorth.add(lblTitle);
//		Đổi màu nền
		pnNorth.setBackground(mauNen);

//		Khai báo Panel Thêm thông tin
//		pnCenter = new JPanel();
		pnWest = new JPanel(new GridLayout(2, 1));
		pnContent.add(pnWest, BorderLayout.WEST);

		pnThem = new JPanel();
		pnWest.add(pnThem);
//		Tạo các Box để nó auto canh lable với textfield
		Box b = Box.createVerticalBox();
		Box b1 = Box.createHorizontalBox();
		Box b2 = Box.createHorizontalBox();
		Box b3 = Box.createHorizontalBox();
		Box b4 = Box.createHorizontalBox();
		Box b5 = Box.createHorizontalBox();

//		b.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(mauNen), "Thông tin nhân viên"));
//		BorderFactory.createLineBorder(mauNen): đổi màu khung bao
		TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(mauNen),
				"Thông tin ca trực");
//		Canh giữa "Thông tin ca trực"
		titledBorder.setTitleJustification(TitledBorder.CENTER);
//		Thêm border "Thông tinca trực" vào Giao diện
		pnThem.setBorder(titledBorder);

		lblIDCaTruc = new JLabel("Mã ca trực: ");
		txtIDCaTruc = new JTextField(20);
		b1.add(lblIDCaTruc);
		b1.add(txtIDCaTruc);

		lblCaTruc = new JLabel("Ca trực");
		rad8h = new JRadioButton("8h sáng đến 13h chiều");
		rad13h = new JRadioButton("13h chiều đến 18h tối");
		rad18h = new JRadioButton("18h tối đến 0h sáng");
		ButtonGroup bg = new ButtonGroup();

		bg.add(rad8h);
		bg.add(rad13h);
		bg.add(rad18h);
//		b3.add(lblCaTruc);

		b3.add(rad8h);
		b4.add(rad13h);
		b5.add(rad18h);

		lblCaTruc = new JLabel("Ca Trực");
		b2.add(lblCaTruc);

		b.add(b1);
		b.add(Box.createRigidArea(new DimensionUIResource(0, 30)));
		b.add(b2);
		b.add(b3);
		b.add(b4);
		b.add(b5);
		pnThem.add(b);

		pnEast = new JPanel();
		pnContent.add(pnEast, BorderLayout.EAST);
		Box bEast = Box.createVerticalBox();
		Box bEast1 = Box.createHorizontalBox();
		Box bEast2 = Box.createHorizontalBox();
		Box bEast3 = Box.createHorizontalBox();

		pnTable = new JPanel();
//		createTable(): là đem public void createTable() lên đây thì nó mới thực thi trong giao diện được
		createTable();
//		Thêm bảng vào trong giao diện
//		Tạo khung bao và tiêu đề cho bẳng
		TitledBorder titledBorder1 = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(mauNen),
				"Danh sách ca trực ");
		titledBorder1.setTitleJustification(TitledBorder.CENTER);
		pnTable.setBorder(titledBorder1);

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
		bEast2.add(btnThem);
		bEast2.add(Box.createHorizontalStrut(30));
		bEast2.add(btnXoa);
		bEast2.add(Box.createHorizontalStrut(30));
		bEast2.add(btnXoaTrang);
		bEast3.add(btnSua);
		bEast3.add(Box.createHorizontalStrut(30));
		bEast3.add(btnLuu);
		bEast3.add(Box.createHorizontalStrut(30));
		bEast3.add(btnThoat);
		pnEast.add(bEast);
		bEast.add(bEast1);
		bEast.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
		bEast.add(bEast2);
		bEast.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
		bEast.add(bEast3);
		bEast1.add(pnTable);

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
//		khai báo model để tạo 1 khung table rỗng 
		model = new DefaultTableModel();
//		Giờ khai báo table này để add model vào rỗng nè
		table = new JTable(model);
//		addColumn là thêm tiêu đè cột thôi
		model.addColumn("Mã ca trực");
		model.addColumn("Thông tin ca trực");

		try {
			for (CaTruc caTruc : ct.getAllCaTruc()) {
				Object[] row = { caTruc.getIDCaTruc(), caTruc.getCaTruc() };
				model.addRow(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

//		Giờ mới thêm bảng vào Panel nè
		pnTable.add(table);
//		JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
//		JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED 2 cái này là thêm cái thanh trên dưới á
		JScrollPane sp = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//		Tạo kích thước của bảng
		sp.setPreferredSize(new Dimension(500, 180));
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
				txtIDCaTruc.setText(model.getValueAt(row, 0).toString());

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
		new QuanLyThongTinCaLamViec_GUI().setVisible(true);
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

			String maCaTrucMoi = txtIDCaTruc.getText();
			boolean maDaTonTai = false;

			for (CaTruc caTruc : ct.getAllCaTruc()) {
				if (caTruc.getIDCaTruc().contains(maCaTrucMoi)) {
					maDaTonTai = true;
					break;
				}
			}
			String caTruc = "";
			if (rad8h.isSelected()) {
				caTruc = "8h sáng đến 13h chiều";
			} else if (rad13h.isSelected()) {
				caTruc = "13h chiều đến 18h tối";
			} else if (rad18h.isSelected()) {
				caTruc = "18h tối đến 0h sáng";
			}

			if (btnThem.getText().equals("Hủy")) {
//				Kiểm tra tồn tại mã khi thêm vào bảng
				if (maDaTonTai == true) {
					JOptionPane.showMessageDialog(this, "Mã ca trực đã tồn tại");
				} else if (caTruc.equals("")) {
					JOptionPane.showMessageDialog(this, "Chưa chọn ca");
				} else {
					if (kTraLoi() == true) {
						CaTruc CaTruc = new CaTruc(maCaTrucMoi, caTruc);
						ct.addCaTruc(CaTruc);
						JOptionPane.showMessageDialog(this, "Lưu thành công!");
						String[] data = { txtIDCaTruc.getText(), caTruc };
						model.addRow(data);
						reset();
					}
				}
			} else if (btnSua.getText().equals("Hủy")) {
				if (kTraLoi() == true) {
					CaTruc CaTruc = new CaTruc(maCaTrucMoi, caTruc);
					String[] data = { txtIDCaTruc.getText(), caTruc };
//				Thêm dòng mới vào Jtable
					model.insertRow(row, data);
					model.removeRow(row + 1);
					ct.updateCaTruc(CaTruc);
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
		String ma = txtIDCaTruc.getText();
		if (ma.equals("")) {
			JOptionPane.showMessageDialog(this, "Mã ca trực không được để trống");
			return false;
		}
		if (!ma.matches("CT\\d{3}")) {
			JOptionPane.showMessageDialog(this, "Mã ca trực lỗi! Theo dạng CTxxx với x từ [0-9]");
			txtIDCaTruc.requestFocus();
			return false;
		}
		return true;
	}

	public void moKhoaTxt() {
		if (btnSua.getText().equals("Hủy"))
			txtIDCaTruc.setEditable(false);
		else
			txtIDCaTruc.setEditable(true);
		rad8h.setEnabled(true);
		rad13h.setEnabled(true);
		rad18h.setEnabled(true);

	}

	public void khoaTxt() {
		txtIDCaTruc.setEditable(false);
		rad8h.setEnabled(false);
		rad13h.setEnabled(false);
		rad18h.setEnabled(false);
	}

	public void xoaRong() {
		txtIDCaTruc.setText("");
		txtIDCaTruc.requestFocus();
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
			int tb = JOptionPane.showConfirmDialog(null, "Bạn có chắc xóa thông tin ca trực này không!", "delete",
					JOptionPane.YES_NO_CANCEL_OPTION);
			if (tb == JOptionPane.YES_OPTION) {
				String ma = txtIDCaTruc.getText();
				ct.deleteCaTruc(ma);
				model.removeRow(r);
			}
		} else
			JOptionPane.showMessageDialog(null, "bạn chưa chọn dòng muốn xóa");
	}

}