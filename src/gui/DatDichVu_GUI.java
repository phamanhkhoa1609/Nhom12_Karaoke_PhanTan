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
import javax.swing.plaf.DimensionUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import connectDB.ConnectDB;
import dao.DatDichVu_DAO;
import dao.DichVu_DAO;
import dao.KhachHang_DAO;
import dao.LapHoaDon_DAO;
import entity.DatDichVu;
import entity.DichVu;
import entity.KhachHang;
import entity.LapHoaDon;

public class DatDichVu_GUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel pnContent, pnNorth, pnChucNang, pnWest, pnEast, pnThem, pnTable_DSDV, pnTable_DDV, pnKH;
	private JLabel lblSoDienThoai, lblTenDichVu, lblSoLuong, lblIDKH, lblTT_IDDV, lblTT_IDKH, lblTTTen, lblSDT,
			lblTTSDT, lblTTHD;
	private JComboBox<Object> cmbTenDV;
	private JTextField txtSDT, txtSoLuong;
	private DefaultTableModel model_DSDichVu, model_DDV;
	private JTable table_DSDichVu, table_DDV;
	private JButton btnTim, btnThem, btnSua, btnXoa, btnXoaTrang, btnLuu, btnThoat;

	private DichVu_DAO dv;
	private DatDichVu_DAO ddv;
	private KhachHang_DAO kh;
	private LapHoaDon_DAO hd;
	Color mauNen = new Color(205, 38, 38);// màu đỏ
	Color mauBang = new Color(99, 184, 255);// màu lam

	/***
	 * Chỉnh sửa tiền tệ
	 */
	Locale localeVN = new Locale("vi", "VN");
	NumberFormat tienTeVN = NumberFormat.getCurrencyInstance(localeVN);

	public DatDichVu_GUI() throws Exception {
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		kh = new KhachHang_DAO();
		dv = new DichVu_DAO();
		ddv = new DatDichVu_DAO();
		hd = new LapHoaDon_DAO();
		createGui();
	}

	public void createGui() {
		setTitle("Đặt dịch vụ");
		setSize(950, 500);
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

		JLabel lblTitle = new JLabel("Đặt dịch vụ");
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
		Box b4 = Box.createHorizontalBox();

		TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(mauNen),
				"Thông tin đặt dịch vụ");
		titledBorder.setTitleJustification(TitledBorder.CENTER);
		pnThem.setBorder(titledBorder);

		lblSoDienThoai = new JLabel("Số điện thoại KH: ");
		txtSDT = new JTextField();
		btnTim = new JButton("Tìm");
		btnTim.setBackground(mauNen);
		btnTim.setForeground(Color.WHITE);

		b1.add(lblSoDienThoai);
		b1.add(Box.createHorizontalStrut(10));
		b1.add(txtSDT);
		b1.add(Box.createHorizontalStrut(10));
		b1.add(btnTim);

		lblTenDichVu = new JLabel("Tên dịch vụ:");
		cmbTenDV = new JComboBox<>();
		b2.add(lblTenDichVu);
		b2.add(Box.createHorizontalStrut(10));
		b2.add(cmbTenDV);

		lblSoLuong = new JLabel("Số lượng: ");
		txtSoLuong = new JTextField();
		b3.add(lblSoLuong);
		b3.add(Box.createHorizontalStrut(10));
		b3.add(txtSoLuong);

		lblTTHD = new JLabel();
		lblTT_IDDV = new JLabel();
//		b4.add(lblTTHD);

		b.add(b1);
		b.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
		b.add(b2);
		b.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
		b.add(b3);
		b.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
		b.add(b4);
		pnThem.add(b);
		lblTenDichVu.setPreferredSize(lblSoDienThoai.getPreferredSize());
		lblSoLuong.setPreferredSize(lblSoDienThoai.getPreferredSize());

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
		Box bEast3 = Box.createHorizontalBox();
		pnEast.add(bEast);
		bEast.add(bEast1);
		bEast.add(bEast2);
		bEast.add(bEast3);

		pnKH = new JPanel();
		bEast1.add(pnKH);
		lblIDKH = new JLabel("ID khách hàng: ");
		lblTT_IDKH = new JLabel(" ");
		lblTTTen = new JLabel(" ");
		lblSDT = new JLabel("SDT: ");
		lblTTSDT = new JLabel(" ");
		pnKH.add(lblIDKH);
		pnKH.add(lblTT_IDKH);
		pnKH.add(Box.createHorizontalStrut(20));
		pnKH.add(lblTTTen);
		pnKH.add(Box.createHorizontalStrut(20));
		pnKH.add(lblSDT);
		pnKH.add(lblTTSDT);

		TitledBorder titledKH = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(mauNen),
				"Thông tin khách hàng");
		titledKH.setTitleJustification(TitledBorder.CENTER);
		pnKH.setBorder(titledKH);

		/**
		 * Table Danh sách dịch vụ
		 */
		pnTable_DSDV = new JPanel();
		bEast2.add(pnTable_DSDV);
		createTable_DSDV();

		TitledBorder titleDSDV = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(mauNen),
				"Danh sách dịch vụ ");
		titleDSDV.setTitleJustification(TitledBorder.CENTER);
		pnTable_DSDV.setBorder(titleDSDV);

		/**
		 * Table Danh sách đặt dịch vụ
		 */
		pnTable_DDV = new JPanel();
		bEast3.add(pnTable_DDV);
		createTable_DDV();

		TitledBorder titledDDV = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(mauNen),
				"Danh sách đặt dịch vụ ");
		titledDDV.setTitleJustification(TitledBorder.CENTER);
		pnTable_DDV.setBorder(titledDDV);

		try {
			for (DichVu dichVu : dv.getAllDichVu()) {
				cmbTenDV.addItem(dichVu.getTenDichVu());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.add(pnContent);

		btnThem.addActionListener(this);
		btnXoa.addActionListener(this);
		btnXoaTrang.addActionListener(this);
		btnSua.addActionListener(this);
		btnLuu.addActionListener(this);
		btnThoat.addActionListener(this);
		btnTim.addActionListener(this);

		khoaTxt();
		btnTim.setEnabled(false);
	}

	public void createTable_DSDV() {
		model_DSDichVu = new DefaultTableModel();
		table_DSDichVu = new JTable(model_DSDichVu);
		model_DSDichVu.addColumn("Mã dịch vụ");
		model_DSDichVu.addColumn("Tên dịch vụ");
		model_DSDichVu.addColumn("Số lượng");
		model_DSDichVu.addColumn("Phí dịch vụ($)");

		try {
			for (DichVu dichVu : dv.getAllDichVu()) {
				if (dichVu.getSoLuong() != 0) {
					Object[] row = { dichVu.getMaDichVu(), dichVu.getTenDichVu(), dichVu.getSoLuong(),
							tienTeVN.format(dichVu.getGia()) };
					model_DSDichVu.addRow(row);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		pnTable_DSDV.add(table_DSDichVu);
		JScrollPane sp = new JScrollPane(table_DSDichVu, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(550, 150));
		pnTable_DSDV.add(sp);

		DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
		headerRenderer.setBackground(mauBang);
		table_DSDichVu.getTableHeader().setDefaultRenderer(headerRenderer);
		headerRenderer.setForeground(Color.WHITE);
		table_DSDichVu.setGridColor(mauNen);

	}

	public void createTable_DDV() {
		model_DDV = new DefaultTableModel();
		table_DDV = new JTable(model_DDV);
		model_DDV.addColumn("Mã hóa đơn");
		model_DDV.addColumn("Mã khách hàng");
		model_DDV.addColumn("Tên dịch vụ");
		model_DDV.addColumn("Tên khách hàng");
		model_DDV.addColumn("Số lượng");

		try {
			for (DatDichVu datDichVu : ddv.getDatDichVuForPhongDaDat()) {
				Object[] row = { datDichVu.getMaHoaDon(), datDichVu.getMaKhachHang(), datDichVu.getTenDichVu(),
						datDichVu.getTenKhachHang(), datDichVu.getSoLuong() };
				model_DDV.addRow(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		pnTable_DDV.add(table_DDV);
		JScrollPane sp = new JScrollPane(table_DDV, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(550, 150));
		pnTable_DDV.add(sp);

		DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
		headerRenderer.setBackground(mauBang);
		table_DDV.getTableHeader().setDefaultRenderer(headerRenderer);
		headerRenderer.setForeground(Color.WHITE);
		table_DDV.setGridColor(mauNen);
	}

	public static void main(String[] args) throws Exception {
		new DatDichVu_GUI().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnTim)) {
			boolean tim = false;
			for (KhachHang khachHang : kh.getAllKhachHang()) {
				if (khachHang.getsDT().contains(txtSDT.getText())) {
					if (khachHang.getTrangThai().equalsIgnoreCase("Chưa thanh toán")) {
						lblTT_IDKH.setText(khachHang.getMaKH());
						lblTTTen.setText(khachHang.getTenKH());
						lblTTSDT.setText(khachHang.getsDT());
						for (LapHoaDon hoaDon : hd.getHoaDonForKhachHang(lblTT_IDKH.getText()))
							lblTTHD.setText(hoaDon.getMaHoaDon());
					} else {
						JOptionPane.showMessageDialog(this, "Số điện thoại này chưa đặt phòng");
					}
					tim = true;
					break;
				}
			}
			if (!tim) {
				JOptionPane.showMessageDialog(this, "Không tìm thấy");
			}
		} else if (o.equals(btnThem)) {
			if (btnThem.getText().equalsIgnoreCase("Thêm")) {
				moKhoaTxt();
				btnThem.setText("Hủy");
				btnLuu.setEnabled(true);
				btnSua.setEnabled(false);
				btnXoa.setEnabled(false);
				btnXoaTrang.setEnabled(false);
				btnTim.setEnabled(true);
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
			int row = table_DDV.getSelectedRow();
			if (btnThem.getText().equals("Hủy")) {
				if (kTraLoi() == true) {
					for (DichVu dichVu : dv.getAllDichVu()) {
						if (cmbTenDV.getSelectedItem().toString().equals(dichVu.getTenDichVu())) {
							lblTT_IDDV.setText(dichVu.getMaDichVu());

//							int soLuong = dichVu.getSoLuong() - Integer.parseInt(txtSoLuong.getText());
//							dv.updateSoLuongCuaDichVu(lblTT_IDDV.getText(), soLuong);
//							for (int i = 0; i < model_DSDichVu.getRowCount(); i++) {
//								if (lblTT_IDDV.getText().equals(model_DSDichVu.getValueAt(i, 0).toString())) {
//									int soLuongHienCo_DSDV = Integer
//											.parseInt(model_DSDichVu.getValueAt(i, 2).toString());
//									int soLuongConLai_DSDV = soLuongHienCo_DSDV
//											- Integer.parseInt(txtSoLuong.getText());
//									model_DSDichVu.setValueAt(soLuongConLai_DSDV, i, 2);
//									break;
//								}
//							}
						}
					}
					DatDichVu datDichVu = new DatDichVu(lblTTHD.getText(), lblTT_IDDV.getText(), lblTT_IDKH.getText(),
							cmbTenDV.getSelectedItem().toString(), lblTTTen.getText(),
							Integer.parseInt(txtSoLuong.getText()), 0);
					ddv.addDatDichVu(datDichVu);

					JOptionPane.showMessageDialog(this, "Lưu thành công!");
					String[] data = { lblTTHD.getText(), lblTT_IDKH.getText(), cmbTenDV.getSelectedItem().toString(),
							lblTTTen.getText(), txtSoLuong.getText() };
					model_DDV.addRow(data);
					reset();

				}
			} else if (btnSua.getText().equals("Hủy")) {
				if (kTraLoi() == true) {
					String maHD = model_DDV.getValueAt(row, 0).toString();
					String maKH = model_DDV.getValueAt(row, 1).toString();
					String tenDV = model_DDV.getValueAt(row, 2).toString();
					String tenKH = model_DDV.getValueAt(row, 3).toString();
					String maDV = "";
					for (DichVu dichVU : dv.getAllDichVu()) {
						if (tenDV.equals(dichVU.getTenDichVu())) {
							maDV = dichVU.getMaDichVu();

//							int soLuong = dichVU.getSoLuong() - Integer.parseInt(txtSoLuong.getText());
//							dv.updateSoLuongCuaDichVu(lblTT_IDDV.getText(), soLuong);
							for (int i = 0; i < model_DSDichVu.getRowCount(); i++) {
								if (lblTT_IDDV.getText().equals(model_DSDichVu.getValueAt(i, 0).toString())) {
									int soLuongHienCo_DSDV = Integer
											.parseInt(model_DSDichVu.getValueAt(i, 2).toString());
									int soLuongConLai_DSDV = soLuongHienCo_DSDV
											- Integer.parseInt(txtSoLuong.getText());
									model_DSDichVu.setValueAt(soLuongConLai_DSDV, i, 2);
									break;
								}
							}
						}

					}
					DatDichVu datDichVu = new DatDichVu(maHD, maDV, maKH, tenDV, tenKH,
							Integer.parseInt(txtSoLuong.getText()), 0);
					String[] data = { maHD, maKH, tenDV, tenKH, txtSoLuong.getText() };
					model_DDV.insertRow(row, data);
					model_DDV.removeRow(row + 1);
					ddv.updateDatDichVu(datDichVu);
					JOptionPane.showMessageDialog(this, "Lưu thành công");
//					capNhatSoLuong();
					reset();
				}
			}
		} else if (o.equals(btnSua)) {
			int row = table_DDV.getSelectedRow();
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
		String soLuong = txtSoLuong.getText();
		if (soLuong.equals("")) {
			JOptionPane.showMessageDialog(this, "Dữ liệu không được để trống");
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
		return true;
	}

	public void moKhoaTxt() {
		if (btnSua.getText().equals("Hủy"))
			txtSDT.setEditable(false);
		else
			txtSDT.setEditable(true);
		txtSoLuong.setEditable(true);
	}

	public void khoaTxt() {
		txtSDT.setEditable(false);
		txtSoLuong.setEditable(false);
	}

	public void xoaRong() {
		txtSDT.setText("");
		txtSoLuong.setText("");
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
		int r = table_DDV.getSelectedRow();
		String maHD = model_DDV.getValueAt(r, 0).toString();
		String maKH = model_DDV.getValueAt(r, 1).toString();
		String tenDV = model_DDV.getValueAt(r, 2).toString();
		String maDV = "";
		for (DichVu dichVU : dv.getAllDichVu()) {
			if (tenDV.equals(dichVU.getTenDichVu()))
				maDV = dichVU.getMaDichVu();
		}
		if (r != -1) {
			int tb = JOptionPane.showConfirmDialog(null, "Bạn có chắc xóa dịch vụ này không!", "delete",
					JOptionPane.YES_NO_CANCEL_OPTION);
			if (tb == JOptionPane.YES_OPTION) {
				ddv.deleteDatDichVu(maDV, maKH, maHD);
				model_DDV.removeRow(r);
			}
		} else
			JOptionPane.showMessageDialog(null, "bạn chưa chọn dòng muốn xóa");
	}
}
