package gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.jidesoft.swing.AutoCompletionComboBox;

import entities.DichVu;
import entities.NhanVien;
import entities.enums.ChucVu;
import entities.enums.LoaiDichVu;
import gui.subgui.FormManager;
import gui.subgui.ServiceCardPanel;
import interfaces.NhanVienIDAO;

import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import net.miginfocom.swing.MigLayout;
import utils.VietnameseFormatter;
import utils.guicomponents.MyImageIcon;
import utils.guicomponents.MyPanel;
import utils.guicomponents.notification.MyNotification;
import utils.guicomponents.table.MyTable;
import utils.guicomponents.table.TableImageCellRender;

import java.awt.BorderLayout;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JRadioButton;

public class TimKiemNhanVienPanel extends MyPanel implements ActionListener {
	private AutoCompletionComboBox cboMaNV, cboHoTen;
	private JTextField txtDiaChi;
	private JButton btnTimKiem, btnLamMoi, btnThemDV;
	private JPanel pnChucNang, pnDanhSach;
	private JLabel lblMaNV, lblHoTen, lblDanhSachDV, lblImageDV, lblGiaDV, lblSoLuong;
	private NhanVienIDAO nhanVienIDAO;

	public TimKiemNhanVienPanel() {
	}

	@Override
	public void KhoiTaoGiaoDien() {
		nhanVienIDAO = clientIDAO.getNhanVienIDAO();
		setPreferredSize(new Dimension(2000, 781));
		setLayout(new BorderLayout(0, 0));
		setOpaque(false);
		pnChucNang = new JPanel();
		pnChucNang.setOpaque(false);
		add(pnChucNang, BorderLayout.NORTH);
		pnChucNang.setLayout(new MigLayout("",
				"[50px:n][grow][50px:n][grow][50px:n][grow][50px:n][grow][50px:n][grow][50px:n][grow][50px:n][grow][120px]",
				"[30px:40px:40px][30px:40px:40px]"));

		lblMaNV = new JLabel("Mã nhân viên");
		lblMaNV.setHorizontalAlignment(SwingConstants.TRAILING);
		lblMaNV.setFont(new Font("Dialog", Font.BOLD, 12));
		pnChucNang.add(lblMaNV, "cell 0 0,alignx trailing,growy");

		cboMaNV = new AutoCompletionComboBox();
		cboMaNV.setForeground(Color.WHITE);
		cboMaNV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		cboMaNV.addActionListener(this);
		pnChucNang.add(cboMaNV, "cell 1 0,grow");

		lblCCCD = new JLabel("Căn cước công dân:");
		lblCCCD.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCCCD.setFont(new Font("Dialog", Font.BOLD, 12));
		pnChucNang.add(lblCCCD, "cell 2 0,alignx trailing");

		cboCCCD = new AutoCompletionComboBox();
		cboCCCD.setForeground(Color.WHITE);
		cboCCCD.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		cboCCCD.addActionListener(this);
		pnChucNang.add(cboCCCD, "cell 3 0,grow");

		lblChucVu = new JLabel("Chức vụ:");
		lblChucVu.setHorizontalAlignment(SwingConstants.TRAILING);
		lblChucVu.setFont(new Font("Dialog", Font.BOLD, 12));
		pnChucNang.add(lblChucVu, "cell 4 0,alignx trailing");

		Vector<String> dsChucVu = new Vector<String>();
		dsChucVu.add("Tất cả");
		for (ChucVu chucVu : ChucVu.values()) {
			dsChucVu.add(chucVu.getChucVu());
		}
		dsChucVu.remove("Quản lý");
		cboChucVu = new AutoCompletionComboBox(dsChucVu);
		cboChucVu.setForeground(Color.WHITE);
		cboChucVu.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnChucNang.add(cboChucVu, "cell 5 0,grow");

		lblNgay = new JLabel("Ngày sinh");
		lblNgay.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNgay.setFont(new Font("Dialog", Font.BOLD, 12));
		pnChucNang.add(lblNgay, "cell 6 0,alignx trailing");

		spnNgay = new JSpinner(new SpinnerNumberModel(LocalDate.now().getDayOfMonth(), 0, 31, 1));
		spnNgay.setForeground(Color.WHITE);
		spnNgay.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnChucNang.add(spnNgay, "cell 7 0,grow");

		lblThang = new JLabel("Tháng");
		lblThang.setFont(new Font("Dialog", Font.BOLD, 12));
		pnChucNang.add(lblThang, "cell 8 0,alignx trailing");

		spnThang = new JSpinner(new SpinnerNumberModel(LocalDate.now().getMonthValue(), 0, 12, 1));
		spnThang.setForeground(Color.WHITE);
		spnThang.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnChucNang.add(spnThang, "cell 9 0,grow");

		lblNam = new JLabel("Năm");
		lblNam.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNam.setFont(new Font("Dialog", Font.BOLD, 12));
		pnChucNang.add(lblNam, "cell 10 0,alignx trailing");

		spnNam = new JSpinner(new SpinnerNumberModel(LocalDate.now().getYear(), 0, LocalDate.now().getYear(), 1));
		spnNam.setForeground(Color.WHITE);
		spnNam.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnChucNang.add(spnNam, "cell 11 0,grow");

		lblGioiTinh = new JLabel("Giới tính");
		lblGioiTinh.setHorizontalAlignment(SwingConstants.TRAILING);
		lblGioiTinh.setFont(new Font("Dialog", Font.BOLD, 12));
		pnChucNang.add(lblGioiTinh, "cell 12 0,alignx trailing");

		rbGTTatCa = new JRadioButton("Tất cả");
		rbGTTatCa.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		rbGTTatCa.setForeground(Color.WHITE);
		pnChucNang.add(rbGTTatCa, "flowx,cell 13 0,grow");

		lblHoTen = new JLabel("Họ tên");
		lblHoTen.setHorizontalAlignment(SwingConstants.TRAILING);
		lblHoTen.setFont(new Font("Dialog", Font.BOLD, 12));
		pnChucNang.add(lblHoTen, "cell 0 1,grow");

		cboHoTen = new AutoCompletionComboBox();
		cboHoTen.setForeground(Color.WHITE);
		cboHoTen.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnChucNang.add(cboHoTen, "cell 1 1,grow");

		btnTimKiem = new JButton("Tìm kiếm");
		btnTimKiem.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnTimKiem.addActionListener(this);
		pnChucNang.add(btnTimKiem, "cell 14 0,grow");

		lblSDT = new JLabel("Số điện thoại:");
		lblSDT.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSDT.setFont(new Font("Dialog", Font.BOLD, 12));
		pnChucNang.add(lblSDT, "cell 2 1,alignx trailing");

		cboSDT = new AutoCompletionComboBox();
		cboSDT.setForeground(Color.WHITE);
		cboSDT.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		cboSDT.addActionListener(this);
		pnChucNang.add(cboSDT, "cell 3 1,grow");

		lblTinhTrang = new JLabel("Tình trạng:");
		lblTinhTrang.setHorizontalAlignment(SwingConstants.TRAILING);
		lblTinhTrang.setFont(new Font("Dialog", Font.BOLD, 12));
		pnChucNang.add(lblTinhTrang, "cell 4 1,alignx trailing");

		rbTTTatCa = new JRadioButton("Tất cả");
		rbTTTatCa.setSelected(true);
		rbTTTatCa.setForeground(Color.WHITE);
		rbTTTatCa.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		pnChucNang.add(rbTTTatCa, "flowx,cell 5 1,grow");

		lblDiaChi = new JLabel("Địa chỉ");
		lblDiaChi.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDiaChi.setFont(new Font("Dialog", Font.BOLD, 12));
		pnChucNang.add(lblDiaChi, "cell 6 1,alignx right");

		btnLamMoi = new JButton("Làm mới");
		btnLamMoi.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnLamMoi.addActionListener(this);
		pnChucNang.add(btnLamMoi, "cell 14 1,grow");

		txtDiaChi = new JTextField();
		txtDiaChi.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtDiaChi.setForeground(Color.WHITE);

		pnChucNang.add(txtDiaChi, "cell 7 1 7 1,grow");

		rbNam = new JRadioButton("Nam");
		rbNam.setForeground(Color.WHITE);
		rbNam.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		pnChucNang.add(rbNam, "cell 13 0,grow");

		rbNu = new JRadioButton("Nữ");
		rbNu.setForeground(Color.WHITE);
		rbNu.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		pnChucNang.add(rbNu, "cell 13 0,grow");

		lblDanhSachDV = new JLabel("Danh Sách Nhân viên");
		lblDanhSachDV.setOpaque(true);
		lblDanhSachDV.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDanhSachDV.setHorizontalAlignment(SwingConstants.CENTER);
		lblDanhSachDV.setForeground(Color.WHITE);
		lblDanhSachDV.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblDanhSachDV.setBackground(new Color(72, 209, 204));

		pnDanhSach = new JPanel();
		pnDanhSach.setOpaque(false);
		add(pnDanhSach);
		pnDanhSach.setBorder(new TitledBorder(null, "Danh sách dịch vụ:"));
		pnDanhSach.setLayout(new BorderLayout(0, 0));

		sp = new JScrollPane((Component) null, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setOpaque(false);
		pnDanhSach.add(sp, BorderLayout.CENTER);

		tblNV = new MyTable();
		tblNV.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Hình ảnh", "Mã người dùng", "Họ tên nhân viên", "Căn cước công dân", "Số điện thoại",
						"Chức vụ", "Trạng thái", "Giới tính", "Ngày sinh", "Địa chỉ" }) {

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false;
			}
		});
		tblNV.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				try {
					int row = tblNV.getSelectedRow();
					FormManager.getInstance().showForm("Thông tin chi tiết Khách hàng",
							new ThongTinChiTietNhanVienPanel(
									nhanVienIDAO.layTheoMa(Long.parseLong(tblNV.getValueAt(row, 1).toString()))),
							650, 700);
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		tblNV.getColumnModel().getColumn(0).setCellRenderer(new TableImageCellRender());

		Map<Integer, Integer> setCellAlignMap = new HashMap<Integer, Integer>();
		setCellAlignMap.put(0, SwingConstants.CENTER);
		setCellAlignMap.put(1, SwingConstants.CENTER);
		setCellAlignMap.put(2, SwingConstants.LEFT);
		setCellAlignMap.put(3, SwingConstants.CENTER);
		setCellAlignMap.put(4, SwingConstants.CENTER);
		setCellAlignMap.put(5, SwingConstants.LEFT);
		setCellAlignMap.put(6, SwingConstants.LEFT);
		setCellAlignMap.put(7, SwingConstants.LEFT);
		setCellAlignMap.put(8, SwingConstants.RIGHT);
		setCellAlignMap.put(9, SwingConstants.LEFT);
		tblNV.setColumnCenterAndCellAlign(setCellAlignMap);
		sp.setViewportView(tblNV);
		ButtonGroup bgGioiTinh = new ButtonGroup();
		bgGioiTinh.add(rbGTTatCa);
		bgGioiTinh.add(rbNam);
		bgGioiTinh.add(rbNu);

		rbDangLam = new JRadioButton("Đang làm");
		rbDangLam.setForeground(Color.WHITE);
		rbDangLam.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		pnChucNang.add(rbDangLam, "cell 5 1,grow");

		rbNghi = new JRadioButton("Nghỉ");
		rbNghi.setForeground(Color.WHITE);
		rbNghi.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		pnChucNang.add(rbNghi, "cell 5 1,grow");

		ButtonGroup bgTinhTrang = new ButtonGroup();
		bgTinhTrang.add(rbTTTatCa);
		bgTinhTrang.add(rbDangLam);
		bgTinhTrang.add(rbNghi);

		lamMoi();
	}

	private JScrollPane sp;
	private MyTable tblNV;
	private JLabel lblCCCD;
	private JLabel lblSDT;
	private AutoCompletionComboBox cboSDT;
	private AutoCompletionComboBox cboCCCD;
	private JLabel lblDiaChi;
	private JLabel lblNgay;
	private JSpinner spnNgay, spnThang, spnNam;
	private JLabel lblThang;
	private JLabel lblNam;
	private JLabel lblGioiTinh;
	private JLabel lblChucVu;
	private AutoCompletionComboBox cboChucVu;
	private JLabel lblTinhTrang;
	private JRadioButton rbGTTatCa;
	private JRadioButton rbNam;
	private JRadioButton rbNu;
	private JRadioButton rbTTTatCa;
	private JRadioButton rbDangLam;
	private JRadioButton rbNghi;
	private List<NhanVien> dsNV;
	public void capNhatBangVoiDieuKien(Object... objectSearch) {
		DefaultTableModel tableModel = (DefaultTableModel) tblNV.getModel();
		tableModel.setRowCount(0);
		try {
			dsNV = new Vector<NhanVien>(nhanVienIDAO.timKiemNhieuTruong(objectSearch));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (dsNV.isEmpty() || dsNV == null) {
			MyNotification panel = new MyNotification(chinhDeskstop, MyNotification.Type.INFO,
					MyNotification.Location.CENTER, "Không tìm thấy kết quả nào!");
			panel.showNotification();
		} else {
			btnLamMoi.setEnabled(false);
		}
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				synchronized (dsNV) {
					dsNV.parallelStream().forEach(nhanVien -> {
						tableModel.addRow(new Object[] { nhanVien.getHinh(30, 40, 0), nhanVien.getMaND(), nhanVien.getHoTen(),
								nhanVien.getCccd(), nhanVien.getSdt(), nhanVien.getChucVu().getChucVu(),
								nhanVien.getTrangThai().getTrangThaiNhanVien(), nhanVien.getGioiTinh().getGioiTinh(),
								VietnameseFormatter.dinhDangNgayString(nhanVien.getNgaySinh()), nhanVien.getDiaChi() });
					});
				}
				return null;
			}

			@Override
			protected void done() {
				btnLamMoi.setEnabled(true);
			}
		};

		worker.execute();
	}

	private String gioiTinh, trangthai;

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnLamMoi)) {
			lamMoi();
		} else if (o.equals(btnTimKiem) || o.equals(cboMaNV) || o.equals(cboSDT) || o.equals(cboCCCD)) {
			if (rbGTTatCa.isSelected()) {
				gioiTinh = "Tất cả";
			} else if (rbNam.isSelected()) {
				gioiTinh = "Nam";
			} else if (rbNu.isSelected()) {
				gioiTinh = "Nữ";
			}
			if (rbTTTatCa.isSelected()) {
				trangthai = "Tất cả";
			} else if (rbDangLam.isSelected()) {
				trangthai = "Đang làm";
			} else if (rbNghi.isSelected()) {
				trangthai = "Nghỉ";
			}
			try {
				capNhatBangVoiDieuKien(cboMaNV.getSelectedItem(), cboHoTen.getSelectedItem().toString().trim(),
						gioiTinh, trangthai, cboSDT.getSelectedItem().toString().trim(),
						cboCCCD.getSelectedItem().toString().trim(), cboChucVu.getSelectedItem().toString(),
						txtDiaChi.getText(), spnNgay.getValue(), spnThang.getValue(), spnNam.getValue());

			} catch (Exception e2) {
				e2.printStackTrace();
			}
			;
		}
	}

	private void lamMoi() {
		new Thread(() -> {
			try {
				// Thực hiện các tác vụ nền ở đây
				cboMaNV.setModel(new DefaultComboBoxModel<String>(nhanVienIDAO.layTatCaTheoMa()));
				cboHoTen.setModel(new DefaultComboBoxModel<String>(nhanVienIDAO.layTatCaTheoTen()));
				cboCCCD.setModel(new DefaultComboBoxModel<String>(nhanVienIDAO.layTatCaTheoCCCD()));
				cboSDT.setModel(new DefaultComboBoxModel<String>(nhanVienIDAO.layTatCaTheoSDT()));
				cboChucVu.setSelectedIndex(0);
				txtDiaChi.setText("Tất cả");
				rbGTTatCa.setSelected(true);
				rbTTTatCa.setSelected(true);
				spnNgay.setValue(0);
				spnThang.setValue(0);
				spnNam.setValue(0);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}).start();
		capNhatBangVoiDieuKien(null);
	}
}
