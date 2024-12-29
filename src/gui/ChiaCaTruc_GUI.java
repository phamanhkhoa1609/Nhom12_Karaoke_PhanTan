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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
import dao.CaTruc_DAO;
import dao.NhanVien_DAO;
import dao.PhieuCaTruc_DAO;
import entity.CaTruc;
import entity.NhanVien;
import entity.PhieuCaTruc;

public class ChiaCaTruc_GUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel pnContent, pnNorth, pnChucNang, pnWest, pnEast, pnThem, pnTable_DSNV, pnTable_CCT;
	private JLabel lblCaTruc, lblTenNhanVien, lblNgayTruc;
	private JComboBox<Object> cmbCaTruc, cmbTenNV;
	private DefaultTableModel model_DSNhanVien, model_ChiaCaTruc;
	private JTable table_DSNhanVien, table_ChiaCaTruc;
	private JDateChooser dateNgayTruc;
	private JButton btnThem, btnSua, btnXoa, btnXoaTrang, btnLuu, btnThoat;
	private NhanVien_DAO nv;
	private PhieuCaTruc_DAO pct;
	private CaTruc_DAO ct;

	Color mauNen = new Color(205, 38, 38);// màu đỏ
	Color mauBang = new Color(99, 184, 255);// màu lam

	/***
	 * Chỉnh sửa tiền tệ
	 */
	Locale localeVN = new Locale("vi", "VN");
	NumberFormat tienTeVN = NumberFormat.getCurrencyInstance(localeVN);

	public ChiaCaTruc_GUI() throws Exception {
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ct = new CaTruc_DAO();
		nv = new NhanVien_DAO();
		pct = new PhieuCaTruc_DAO();
		createGui();
	}

	public void createGui() {
		setTitle("Chia ca trực");
		setSize(1100, 500);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(true);
		setLocationRelativeTo(null);
		datDichVu();
	}

	public void datDichVu() {
		pnContent = new JPanel();
		pnContent.setLayout(new BorderLayout());

		pnNorth = new JPanel();
		pnContent.add(pnNorth, BorderLayout.NORTH);

		JLabel lblTitle = new JLabel("Chia ca trực");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
		lblTitle.setForeground(Color.WHITE);
		pnNorth.add(lblTitle);
		pnNorth.setBackground(mauNen);

		pnWest = new JPanel(new GridLayout(2, 1));
		pnContent.add(pnWest, BorderLayout.WEST);

		pnThem = new JPanel();
		pnWest.add(pnThem);
		Box b = Box.createVerticalBox();
		Box b1 = Box.createHorizontalBox();
		Box b2 = Box.createHorizontalBox();
		Box b3 = Box.createHorizontalBox();

		TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(mauNen),
				"Thông tin chia ca");
		titledBorder.setTitleJustification(TitledBorder.CENTER);
		pnThem.setBorder(titledBorder);

		lblCaTruc = new JLabel("Ca trực: ");
		String caTruc[] = { "8h sáng đến 13h chiều", "13h chiều đến 18h tối", "18h tối đến 0h sáng" };
		cmbCaTruc = new JComboBox<Object>(caTruc);
		b1.add(lblCaTruc);
		b1.add(Box.createHorizontalStrut(18));
		b1.add(cmbCaTruc);

		lblTenNhanVien = new JLabel("Tên nhân viên:");
		cmbTenNV = new JComboBox<>();
		for (NhanVien nhanVien : nv.getAllNhanVien()) {
			cmbTenNV.addItem(nhanVien.getTenNV());
		}
		b2.add(lblTenNhanVien);
		b2.add(Box.createHorizontalStrut(12));
		b2.add(cmbTenNV);

		dateNgayTruc = new JDateChooser();
		dateNgayTruc.setBounds(320, 90, 200, 30);
		dateNgayTruc.setDateFormatString("yyyy-MM-dd");
		lblNgayTruc = new JLabel("Ngày trực: ");
		b3.add(lblNgayTruc);
		b3.add(Box.createHorizontalStrut(20));
		b3.add(dateNgayTruc);

		b.add(b1);
		b.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
		b.add(b2);
		b.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
		b.add(b3);
		pnThem.add(b);

		lblCaTruc.setPreferredSize(lblTenNhanVien.getPreferredSize());
		lblNgayTruc.setPreferredSize(lblTenNhanVien.getPreferredSize());

		btnThem = new JButton("Thêm");
		btnThem.setBackground(mauNen);
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

		pnChucNang = new JPanel();
		TitledBorder titleChucNang = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(mauNen),
				"Chức năng");
		titleChucNang.setTitleJustification(TitledBorder.CENTER);
		pnChucNang.setBorder(titleChucNang);

		Box bChucNang = Box.createVerticalBox();
		Box bChucNangDong1 = Box.createHorizontalBox();
		Box bChucNangDong2 = Box.createHorizontalBox();
		pnWest.add(pnChucNang);
		pnChucNang.add(bChucNang);
		bChucNang.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
		bChucNang.add(bChucNangDong1);
		bChucNang.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
		bChucNang.add(bChucNangDong2);
		bChucNangDong1.add(Box.createHorizontalStrut(20));
		bChucNangDong1.add(btnThem);
		bChucNangDong1.add(Box.createHorizontalStrut(30));
		bChucNangDong1.add(btnXoa);
		bChucNangDong1.add(Box.createHorizontalStrut(30));
		bChucNangDong1.add(btnXoaTrang);
		bChucNangDong2.add(btnSua);
		bChucNangDong2.add(Box.createHorizontalStrut(25));
		bChucNangDong2.add(btnLuu);
		bChucNangDong2.add(Box.createHorizontalStrut(20));
		bChucNangDong2.add(btnThoat);

		pnEast = new JPanel();
		pnContent.add(pnEast, BorderLayout.EAST);
		Box bEast = Box.createVerticalBox();
		Box bEast1 = Box.createHorizontalBox();
		Box bEast2 = Box.createHorizontalBox();
		pnEast.add(bEast);
		bEast.add(bEast1);
		bEast.add(bEast2);

		/**
		 * Table Danh sách dịch vụ
		 */
		pnTable_DSNV = new JPanel();
		bEast1.add(pnTable_DSNV);
		createTable_DSNV();

		TitledBorder titleDSDV = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(mauNen),
				"Danh sách nhân viên ");
		titleDSDV.setTitleJustification(TitledBorder.CENTER);
		pnTable_DSNV.setBorder(titleDSDV);

		/**
		 * Table Danh sách đặt dịch vụ
		 */
		pnTable_CCT = new JPanel();
		bEast2.add(pnTable_CCT);
		createTable_ChiaCaTruc();

		TitledBorder titledDDV = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(mauNen),
				"Danh sách ca trực ");
		titledDDV.setTitleJustification(TitledBorder.CENTER);
		pnTable_CCT.setBorder(titledDDV);

		this.add(pnContent);
		btnThem.addActionListener(this);
		btnXoa.addActionListener(this);
		btnXoaTrang.addActionListener(this);
		btnSua.addActionListener(this);
		btnLuu.addActionListener(this);
		btnThoat.addActionListener(this);

		khoaTxt();
	}

	public void createTable_DSNV() {
		model_DSNhanVien = new DefaultTableModel();
		table_DSNhanVien = new JTable(model_DSNhanVien);
		model_DSNhanVien.addColumn("Mã nhân viên");
		model_DSNhanVien.addColumn("Tên nhân viên");
		model_DSNhanVien.addColumn("Số điện thoại");
		model_DSNhanVien.addColumn("Bộ phận");
		model_DSNhanVien.addColumn("Lương/ca(VNĐ)");

		try {
			for (NhanVien nhanVien : nv.getAllNhanVien()) {
				Object[] row = { nhanVien.getIDNhanVien(), nhanVien.getTenNV(), nhanVien.getSDT(), nhanVien.getBoPhan(),
						tienTeVN.format(nhanVien.getLuong()) };
				model_DSNhanVien.addRow(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		pnTable_DSNV.add(table_DSNhanVien);
		JScrollPane sp = new JScrollPane(table_DSNhanVien, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(700, 170));
		pnTable_DSNV.add(sp);

		DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
		headerRenderer.setBackground(mauBang);
		table_DSNhanVien.getTableHeader().setDefaultRenderer(headerRenderer);
		headerRenderer.setForeground(Color.WHITE);
		table_DSNhanVien.setGridColor(mauNen);

	}

	public void createTable_ChiaCaTruc() {
		model_ChiaCaTruc = new DefaultTableModel();
		table_ChiaCaTruc = new JTable(model_ChiaCaTruc);
		model_ChiaCaTruc.addColumn("Mã ca trực");
		model_ChiaCaTruc.addColumn("Mã nhân viên");
		model_ChiaCaTruc.addColumn("Tên nhân viên");
		model_ChiaCaTruc.addColumn("Ca trực");
		model_ChiaCaTruc.addColumn("Ngày trực");

		try {
			for (PhieuCaTruc phieuCaTruc : pct.getAllPhieuCaTruc()) {
				Object[] row = { phieuCaTruc.getIDCaTruc(), phieuCaTruc.getIDNhanVien(), phieuCaTruc.getTenNV(),
						phieuCaTruc.getCaTruc(), phieuCaTruc.getNgayTruc() };
				model_ChiaCaTruc.addRow(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		pnTable_CCT.add(table_ChiaCaTruc);
		JScrollPane sp = new JScrollPane(table_ChiaCaTruc, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(700, 150));
		pnTable_CCT.add(sp);

		DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
		headerRenderer.setBackground(mauBang);
		table_ChiaCaTruc.getTableHeader().setDefaultRenderer(headerRenderer);
		headerRenderer.setForeground(Color.WHITE);
		table_ChiaCaTruc.setGridColor(mauNen);

		table_ChiaCaTruc.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				int row = table_ChiaCaTruc.getSelectedRow();
				String caTruc = (model_ChiaCaTruc.getValueAt(row, 3).toString());
				cmbCaTruc.setSelectedItem(caTruc);
				String tenNV = (model_ChiaCaTruc.getValueAt(row, 2).toString());
				cmbTenNV.setSelectedItem(tenNV);
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
		new ChiaCaTruc_GUI().setVisible(true);
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
			int row = table_ChiaCaTruc.getSelectedRow();
			String idCaTruc = "";
			String idNhanVien = "";
			for (CaTruc caTruc : ct.getAllCaTruc()) {
				if (cmbCaTruc.getSelectedItem().toString().equals(caTruc.getCaTruc()))
					idCaTruc = caTruc.getIDCaTruc();
			}
			for (NhanVien nhanVien : nv.getAllNhanVien()) {
				if (cmbTenNV.getSelectedItem().toString().equals(nhanVien.getTenNV()))
					idNhanVien = nhanVien.getIDNhanVien();
			}
			LocalDate ngayTruc = dateNgayTruc.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			if (btnThem.getText().equals("Hủy")) {
//				Kiểm tra tồn tại mã khi thêm vào bảng
				if (kTraLoi() == true) {
					PhieuCaTruc phieuCaTruc = new PhieuCaTruc(idCaTruc, idNhanVien,
							cmbTenNV.getSelectedItem().toString(), cmbCaTruc.getSelectedItem().toString(), ngayTruc);
					pct.addCaTruc(phieuCaTruc);
					JOptionPane.showMessageDialog(this, "Lưu thành công!");
					String[] data = { idCaTruc, idNhanVien, cmbTenNV.getSelectedItem().toString(),
							cmbCaTruc.getSelectedItem().toString(), ngayTruc.toString() };
					model_ChiaCaTruc.addRow(data);
					reset();
				}
			} else if (btnSua.getText().equals("Hủy")) {
				PhieuCaTruc phieuCaTruc = new PhieuCaTruc(idCaTruc, idNhanVien, cmbTenNV.getSelectedItem().toString(),
						cmbCaTruc.getSelectedItem().toString(), ngayTruc);
				String[] data = { idCaTruc, idNhanVien, cmbTenNV.getSelectedItem().toString(),
						cmbCaTruc.getSelectedItem().toString(), ngayTruc.toString() };
				model_ChiaCaTruc.insertRow(row, data);
				model_ChiaCaTruc.removeRow(row + 1);
				pct.updatePhieuCaTruc(phieuCaTruc);
				JOptionPane.showMessageDialog(this, "Lưu thành công");
				reset();
			}
		} else if (o.equals(btnSua)) {
			int row = table_ChiaCaTruc.getSelectedRow();
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
		Date ngay = dateNgayTruc.getDate();
		if (ngay == null) {
			JOptionPane.showMessageDialog(this, "Dữ liệu không được để trống");
			return false;
		}
		return true;
	}

	public void moKhoaTxt() {
		if (btnSua.getText().equals("Hủy")) {
			cmbCaTruc.setEnabled(false);
			cmbTenNV.setEnabled(false);
		} else {
			cmbCaTruc.setEnabled(true);
			cmbTenNV.setEnabled(true);
		}
		dateNgayTruc.setEnabled(true);
	}

	public void khoaTxt() {
		cmbCaTruc.setEnabled(false);
		cmbTenNV.setEnabled(false);
		dateNgayTruc.setEnabled(false);
	}

	public void xoaRong() {
		dateNgayTruc.setCalendar(null);
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
		int r = table_ChiaCaTruc.getSelectedRow();
		if (r != -1) {
			int tb = JOptionPane.showConfirmDialog(null, "Bạn có chắc xóa ca trực này không!", "delete",
					JOptionPane.YES_NO_CANCEL_OPTION);
			if (tb == JOptionPane.YES_OPTION) {
				String idCaTruc = "";
				String idNhanVien = "";
				for (CaTruc caTruc : ct.getAllCaTruc()) {
					if (cmbCaTruc.getSelectedItem().toString().equals(caTruc.getCaTruc()))
						idCaTruc = caTruc.getIDCaTruc();
				}
				for (NhanVien nhanVien : nv.getAllNhanVien()) {
					if (cmbTenNV.getSelectedItem().toString().equals(nhanVien.getTenNV()))
						idNhanVien = nhanVien.getIDNhanVien();
				}
				pct.deletePhieuCaTruc(idCaTruc, idNhanVien);
				model_ChiaCaTruc.removeRow(r);
			}
		} else
			JOptionPane.showMessageDialog(null, "bạn chưa chọn dòng muốn xóa");
	}

}
