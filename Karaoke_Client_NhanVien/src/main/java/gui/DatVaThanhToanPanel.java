package gui;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import net.miginfocom.swing.MigLayout;
import utils.ClientIDAO;
import utils.VietnameseFormatter;
import utils.guicomponents.MyImageIcon;
import utils.guicomponents.MyPanel;
import utils.guicomponents.notification.MyNotification;
import utils.guicomponents.optionmessage.GlassPanePopup;
import utils.guicomponents.optionmessage.MyMessage;
import utils.guicomponents.searchoptiontextfield.MySearchTextField;
import utils.guicomponents.searchoptiontextfield.SearchOptinEvent;
import utils.guicomponents.searchoptiontextfield.SearchOption;
import utils.guicomponents.switchbutton.MySwitchButton;
import utils.guicomponents.switchbutton.SwitchButtonModel;
import utils.guicomponents.switchbutton.SwitchListener;
import utils.guicomponents.table.MyTable;
import utils.guicomponents.table.TableActionCellEditor;
import utils.guicomponents.table.TableActionCellRender;
import utils.guicomponents.table.TableActionEvent;
import utils.guicomponents.table.TableImageCellRender;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JTextField;
import javax.swing.JScrollPane;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;

import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.plaf.basic.BasicTreeUI.CellEditorHandler;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import entities.ChiTietHoaDonDichVu;
import entities.ChiTietHoaDonPhong;
import entities.DichVu;
import entities.HoaDon;
import entities.KhachHang;
import entities.NguoiDung;
import entities.NhanVien;
import entities.Phong;
import entities.enums.ChucVu;
import entities.enums.HoiVien;
import entities.enums.TrangThaiDichVu;
import entities.enums.TrangThaiHoaDon;
import entities.enums.TrangThaiNhanVien;
import entities.enums.TrangThaiPhong;
import gui.subgui.FormManager;
import gui.subgui.HoaDonPDF;
import gui.subgui.RoomCardPanel;
import gui.subgui.ServiceCardPanel;
import interfaces.ChiTietHoaDonDichVuIDAO;
import interfaces.ChiTietHoaDonPhongIDAO;
import interfaces.DichVuIDAO;
import interfaces.HoaDonIDAO;
import interfaces.KhachHangIDAO;
import interfaces.NhanVienIDAO;
import interfaces.PhongIDAO;
import interfaces.TaiKhoanIDAO;
import jakarta.persistence.EntityManager;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.JTable;

import java.awt.Color;
import javax.swing.SwingConstants;

public class DatVaThanhToanPanel extends MyPanel implements ActionListener, ChangeListener, SwitchListener {

	private static final long serialVersionUID = 1L;
	private JTextField txtSDT;
	private JTextField txtHoTen;
	private JTextField txtHoTenPV;
	private MySwitchButton btnDatHayThanhToan;
	private JTextField txtTongTienSauKhuyenMai;
	private JTextField txtTongTienTruocKhuyenMai;
	private JTextField txtTienThoi;
	private JButton btnHienDSDonDat;
	private JButton btnHienDSPhong;
	private JButton btnHienDSDV;
	private MySearchTextField txtTimKiemDD;
	private JPanel pnDanhSach;
	private JPanel pnChiTietPhong;
	private JPanel pnChiTietDV;
	private PhongIDAO phongIDAO;
	private DichVuIDAO dichVuIDAO;
	private KhachHangIDAO khachHangIDAO;
	private NhanVienIDAO nhanVienIDAO;
	private TaiKhoanIDAO taiKhoanIDAO;
	private MyTable tblPhong;
	private MyTable tblDV;

	private JButton btnLamMoiThongTin;
	private JButton btnLamMoiDanhSach;
	private JLabel lblDanhSach;
	private JSpinner spnTienKhachDua;
	private MySearchTextField txtTimKiemKH;
	private MySearchTextField txtTimKiemPV;
	private JPanel pnDatVaThanhToan;
	private JButton btnDatVaThanhToan;
	private NguoiDung nguoiDung;
	private HoaDonIDAO hoaDonIDAO;
	private ChiTietHoaDonPhongIDAO chiTietHoaDonPhongIDAO;
	private ChiTietHoaDonDichVuIDAO chiTietHoaDonDichVuIDAO;
	private MyTable tblChiTietDonDat;
	private TableActionEvent eventDichVu;
	private TableActionEvent eventPhong;
	private boolean khoaDonDat = false;
	private JPanel pnThanhToan;
	private ClientIDAO clientIDAO;

	/**
	 * Create the panel.
	 */
	@SuppressWarnings("deprecation")
	public DatVaThanhToanPanel(ClientIDAO clientIDAO, NguoiDung nguoiDung) {
		this.clientIDAO = clientIDAO;
		this.nguoiDung = nguoiDung;
	}

	@Override
	public void KhoiTaoGiaoDien() {
		dichVuIDAO = clientIDAO.getDichVuIDAO();
		phongIDAO = clientIDAO.getPhongIDAO();
		nhanVienIDAO = clientIDAO.getNhanVienIDAO();
		khachHangIDAO = clientIDAO.getKhachHangIDAO();
		taiKhoanIDAO = clientIDAO.getTaiKhoanIDAO();
		hoaDonIDAO = clientIDAO.getHoaDonIDAO();
		chiTietHoaDonPhongIDAO = clientIDAO.getChiTietHoaDonPhongIDAO();
		chiTietHoaDonDichVuIDAO = clientIDAO.getChiTietHoaDonDichVuIDAO();
		setLayout(new MigLayout("", "[65%:65%:65%,grow][][33%:33%:33%,grow]", "[109.00][370.00,grow][30px:40px:40px]"));

		JPanel pnDieuKhien = new JPanel();
		add(pnDieuKhien, "cell 0 0,grow");
		pnDieuKhien.setLayout(new MigLayout("", "[][][][grow]", "[30px:40px:40px][][]"));

		btnHienDSDonDat = new JButton("Hiện danh sách Đơn đặt");
		btnHienDSDonDat.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnDieuKhien.add(btnHienDSDonDat, "cell 0 0,growx");
		btnHienDSDonDat.addActionListener(this);

		btnHienDSPhong = new JButton("Hiện danh sách Phòng");
		btnHienDSPhong.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnDieuKhien.add(btnHienDSPhong, "cell 1 0,growx");
		btnHienDSPhong.addActionListener(this);

		btnHienDSDV = new JButton("Hiện danh sách Dịch vụ");
		btnHienDSDV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnDieuKhien.add(btnHienDSDV, "flowx,cell 2 0,growx");
		btnHienDSDV.addActionListener(this);

		btnLamMoiDanhSach = new JButton("Làm mới");
		btnLamMoiDanhSach.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnDieuKhien.add(btnLamMoiDanhSach, "cell 3 0,growx");
		btnLamMoiDanhSach.addActionListener(this);

		txtTimKiemDD = new MySearchTextField();
		txtTimKiemDD.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnDieuKhien.add(txtTimKiemDD, "cell 0 1 4 1,growx");
		txtTimKiemDD.setEnabled(false);

		lblDanhSach = new JLabel("Hãy chọn nút ở trên để hiện danh sách bất kỳ!");
		lblDanhSach.setFont(new Font("Times New Roman", Font.BOLD, 20));
		pnDieuKhien.add(lblDanhSach, "cell 0 2 4 1,growx");

		pnDanhSach = new JPanel();
		add(pnDanhSach, "cell 0 1,grow");
		pnDanhSach.setLayout(new BorderLayout(0, 0));

		pnThanhToan = new JPanel();
		add(pnThanhToan, "cell 2 0 1 2,grow");
		pnThanhToan.setLayout(new MigLayout("", "[100px:100px][80px:80px:80px][grow]",
				"[][][:30px:40px,grow][30px:40px:40px,fill][30px:40px:40px][][:30px:40px,grow][30px:40px:40px][:30px:40px,grow][30px:100px,grow][:30px:40px,grow][30px:100px,grow][30px:40px:40px][30px:40px:40px][grow][30px:40px:40px][grow]"));

		JLabel lblKhachHang = new JLabel("Thông tin Khách hàng");
		lblKhachHang.setFont(new Font("Times New Roman", Font.BOLD, 20));
		pnThanhToan.add(lblKhachHang, "cell 0 1 3 1,growx");

		txtTimKiemKH = new MySearchTextField();
		txtTimKiemKH.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnThanhToan.add(txtTimKiemKH, "cell 0 2 3 1,growx");
		txtTimKiemKH.addEventOptionSelected(new SearchOptinEvent() {

			@Override
			public void optionSelected(SearchOption option, int index) {
				// TODO Auto-generated method stub
				txtTimKiemKH.setHint("Lọc khách hàng bởi " + option.getName() + "...");
			}
		});
		txtTimKiemKH.addOption(new SearchOption("mã khách hàng",
				new MyImageIcon("src/main/resources/images/icons/id.png", 20, 20, 0)));
		txtTimKiemKH.addOption(new SearchOption("họ tên khách hàng",
				new MyImageIcon("src/main/resources/images/icons/name.png", 20, 20, 0)));
		txtTimKiemKH.addOption(new SearchOption("số điện thoại",
				new MyImageIcon("src/main/resources/images/icons/tel.png", 20, 20, 0)));
		txtTimKiemKH.setColumns(10);
		txtTimKiemKH.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				if (txtTimKiemKH.getText().equals("")) {
					txtMaKH.setText("");
					txtGioiTinh.setText("");
					txtSDT.setText("");
					txtHoiVien.setText("");
					txtHoTen.setText("");
				} else if (txtTimKiemKH.isSelected()) {
					int option = txtTimKiemKH.getSelectedIndex();
					String searchText = "%" + txtTimKiemKH.getText().trim() + "%";
					try {
						switch (option) {
						case 0:
							hienThiDanhSachKHVoiDieuKien("where mand like ?", searchText);
							break;
						case 1:
							hienThiDanhSachKHVoiDieuKien("where hoten like ?", searchText);
							break;
						case 2:
							hienThiDanhSachKHVoiDieuKien("where sdt like ?", searchText);
							break;
						default:
							hienThiDanhSachKHVoiDieuKien("");
							break;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				lamMoiTien();
			}

			private void hienThiDanhSachKHVoiDieuKien(String where, Object... objectSearch) throws RemoteException {
				// TODO Auto-generated method stub
				List<KhachHang> dsKH = khachHangIDAO.timKiemMotTruong(where, objectSearch);
				if (dsKH.size() == 0) {
					MyNotification panel = new MyNotification(chinhDeskstop, MyNotification.Type.INFO,
							MyNotification.Location.CENTER, "Không khách hàng nào được tìm thấy!");
					panel.showNotification();
					txtMaKH.setText("");
					txtGioiTinh.setText("");
					txtSDT.setText("");
					txtHoiVien.setText("");
					txtHoTen.setText("");

				} else {
					KhachHang khachHang = dsKH.getFirst();
					txtMaKH.setText(khachHang.getMaND() + "");
					txtGioiTinh.setText(khachHang.getGioiTinh().getGioiTinh());
					txtSDT.setText(khachHang.getSdt());
					txtHoiVien.setText(khachHang.getHoiVien().getHoiVien());
					txtHoTen.setText(khachHang.getHoTen());
				}
			}
		});
		List<JTextField> dsTextField = new ArrayList<JTextField>();
		txtMaKH = new JTextField();
		txtMaKH.setForeground(Color.WHITE);
		txtMaKH.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtMaKH.setColumns(10);
		pnThanhToan.add(txtMaKH, "cell 0 3,growx");

		txtGioiTinh = new JTextField();
		txtGioiTinh.setForeground(Color.WHITE);
		txtGioiTinh.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtGioiTinh.setColumns(10);
		pnThanhToan.add(txtGioiTinh, "cell 1 3,grow");

		txtSDT = new JTextField();
		txtSDT.setForeground(Color.WHITE);
		txtSDT.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnThanhToan.add(txtSDT, "cell 2 3,grow");
		txtSDT.setColumns(10);

		txtHoiVien = new JTextField();
		txtHoiVien.setForeground(Color.WHITE);
		txtHoiVien.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtHoiVien.setColumns(10);
		pnThanhToan.add(txtHoiVien, "cell 0 4 2 1,grow");

		txtHoTen = new JTextField();
		txtHoTen.setForeground(Color.WHITE);
		txtHoTen.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtHoTen.setColumns(10);
		pnThanhToan.add(txtHoTen, "flowx,cell 2 4,grow");

		JLabel lblPhucVu = new JLabel("Thông tin Phục vụ");
		lblPhucVu.setFont(new Font("Times New Roman", Font.BOLD, 20));
		pnThanhToan.add(lblPhucVu, "cell 0 5 3 1");

		txtTimKiemPV = new MySearchTextField();
		txtTimKiemPV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnThanhToan.add(txtTimKiemPV, "cell 0 6 3 1,growx");
		txtTimKiemPV.addEventOptionSelected(new SearchOptinEvent() {

			@Override
			public void optionSelected(SearchOption option, int index) {
				// TODO Auto-generated method stub
				txtTimKiemPV.setHint("Lọc nhân viên phục vụ bởi " + option.getName() + "...");
			}
		});
		txtTimKiemPV.addOption(
				new SearchOption("mã nhân viên", new MyImageIcon("src/main/resources/images/icons/id.png", 20, 20, 0)));
		txtTimKiemPV.addOption(new SearchOption("họ tên nhân viên",
				new MyImageIcon("src/main/resources/images/icons/name.png", 20, 20, 0)));
		txtTimKiemPV.addOption(new SearchOption("số điện thoại",
				new MyImageIcon("src/main/resources/images/icons/tel.png", 20, 20, 0)));
		txtTimKiemPV.addOption(new SearchOption("căn cước công dân",
				new MyImageIcon("src/main/resources/images/icons/cccd.png", 20, 20, 0)));
		txtTimKiemPV.setColumns(10);
		txtTimKiemPV.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				if (txtTimKiemPV.getText().equals("")) {
					txtMaPV.setText("");
					txtHoTenPV.setText("");
				} else if (txtTimKiemPV.isSelected()) {
					int option = txtTimKiemPV.getSelectedIndex();
					String searchText = "%" + txtTimKiemPV.getText().trim() + "%";
					String whereplus = " and chucvu = 'PHỤCVỤ' and trangthai = 'ĐANGLÀM'";
					try {
						switch (option) {
						case 0:
							hienThiDanhSachNVVoiDieuKien("where mand like ?" + whereplus, searchText);
							break;
						case 1:
							hienThiDanhSachNVVoiDieuKien("where hoten like ?" + whereplus, searchText);
							break;
						case 2:
							hienThiDanhSachNVVoiDieuKien("where sdt like ?" + whereplus, searchText);
							break;
						case 3:
							hienThiDanhSachNVVoiDieuKien("where cccd like ?" + whereplus, searchText);
							break;
						default:
							hienThiDanhSachNVVoiDieuKien("");
							break;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			private void hienThiDanhSachNVVoiDieuKien(String where, Object... objectSearch) throws RemoteException {
				// TODO Auto-generated method stub
				List<NhanVien> dsNV = nhanVienIDAO.timKiemMotTruong(where, objectSearch);
				if (dsNV.size() == 0) {
					MyNotification panel = new MyNotification(chinhDeskstop, MyNotification.Type.INFO,
							MyNotification.Location.CENTER, "Không nhân viên nào được tìm thấy!");
					panel.showNotification();
					txtMaPV.setText("");
					txtHoTenPV.setText("");
				} else {
					NhanVien nhanVien = dsNV.getFirst();
					txtMaPV.setText(nhanVien.getMaND() + "");
					txtHoTenPV.setText(nhanVien.getHoTen());
				}
			}
		});

		txtMaPV = new JTextField();
		txtMaPV.setForeground(Color.WHITE);
		txtMaPV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtMaPV.setColumns(10);
		pnThanhToan.add(txtMaPV, "cell 0 7,grow");

		txtHoTenPV = new JTextField();
		txtHoTenPV.setForeground(Color.WHITE);
		txtHoTenPV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtHoTenPV.setColumns(10);
		pnThanhToan.add(txtHoTenPV, "cell 1 7 2 1,grow");

		JLabel lblDSPhong = new JLabel("Danh sách chọn Phòng");
		lblDSPhong.setFont(new Font("Times New Roman", Font.BOLD, 20));
		pnThanhToan.add(lblDSPhong, "cell 0 8 2 1");

		JScrollPane spDanhSachPhong = new JScrollPane((Component) null, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		spDanhSachPhong.setOpaque(false);

		tblPhong = new MyTable();
		tblPhong.setBorder(null);
		tblPhong.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Mã phòng", "Tên phòng", "Giá phòng",
				"Bắt đầu", "Kết thúc", "Số giờ", "Thành tiền", "Chức năng" }) {
			boolean[] canEdit = new boolean[] { false, false, false, false, false, false, false, true };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
				// return false;
			}
		});
		eventPhong = new TableActionEvent() {

			@Override
			public void onView(int row) {
				// TODO Auto-generated method stub
				try {
					FormManager.getInstance().showForm("Thông tin chi tiết Phòng",
							new ThongTinChiTietPhongPanel(
									phongIDAO.layTheoMa(Long.parseLong(tblPhong.getValueAt(row, 0).toString()))),
							320, 700);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onEdit(int row) {

			}

			@Override
			public void onDelete(int row) {
				// TODO Auto-generated method stub
				if (tblPhong.isEditing()) {
					tblPhong.getCellEditor().stopCellEditing();
				}
				DefaultTableModel model = (DefaultTableModel) tblPhong.getModel();
				model.removeRow(row);
				lamMoiTien();
			}
		};
		tblPhong.getColumnModel().getColumn(7).setCellRenderer(new TableActionCellRender(true, false));
		tblPhong.getColumnModel().getColumn(7).setCellEditor(new TableActionCellEditor(eventPhong, true, false));
		Map<Integer, Integer> setCellAlignMapTblPhong = new HashMap<Integer, Integer>();
		setCellAlignMapTblPhong.put(0, SwingConstants.RIGHT);
		setCellAlignMapTblPhong.put(1, SwingConstants.CENTER);
		setCellAlignMapTblPhong.put(2, SwingConstants.RIGHT);
		setCellAlignMapTblPhong.put(3, SwingConstants.RIGHT);
		setCellAlignMapTblPhong.put(4, SwingConstants.RIGHT);
		setCellAlignMapTblPhong.put(5, SwingConstants.RIGHT);
		setCellAlignMapTblPhong.put(6, SwingConstants.RIGHT);
		tblPhong.setColumnCenterAndCellAlign(setCellAlignMapTblPhong);
		spDanhSachPhong.setViewportView(tblPhong);
		pnThanhToan.add(spDanhSachPhong, "cell 0 9 3 1,grow");
		JLabel lblDSDV = new JLabel("Danh sách chọn Dịch vụ");
		lblDSDV.setFont(new Font("Times New Roman", Font.BOLD, 20));
		pnThanhToan.add(lblDSDV, "cell 0 10");

		JScrollPane spDanhSachDichVu = new JScrollPane((Component) null, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		spDanhSachDichVu.setOpaque(false);

		tblDV = new MyTable();
		tblDV.setBorder(null);
		tblDV.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Mã dịch vụ", "Tên dịch vụ",
				"Giá dịch vụ", "Số lượng chọn", "Thành tiền", "Chức năng" }) {
			boolean[] canEdit = new boolean[] { false, false, false, true, false, true };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
				// return false;
			}
		});
		eventDichVu = new TableActionEvent() {

			@Override
			public void onView(int row) {
				// TODO Auto-generated method stub
				try {
					FormManager.getInstance().showForm("Thông tin chi tiết Dịch vụ",
							new ThongTinChiTietDichVuPanel(
									dichVuIDAO.layTheoMa(Long.parseLong(tblDV.getValueAt(row, 0).toString()))),
							320, 700);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onEdit(int row) {

			}

			@Override
			public void onDelete(int row) {
				// TODO Auto-generated method stub
				if (tblDV.isEditing()) {
					tblDV.getCellEditor().stopCellEditing();
				}
				DefaultTableModel model = (DefaultTableModel) tblDV.getModel();
				model.removeRow(row);
				lamMoiTien();
			}
		};
		tblDV.getColumnModel().getColumn(3).setCellEditor(new SpinnerEditor());
		tblDV.getColumnModel().getColumn(5).setCellRenderer(new TableActionCellRender(true, false));
		tblDV.getColumnModel().getColumn(5).setCellEditor(new TableActionCellEditor(eventDichVu, true, false));

		Map<Integer, Integer> setCellAlignMapTblDV = new HashMap<Integer, Integer>();
		setCellAlignMapTblDV.put(0, SwingConstants.RIGHT);
		setCellAlignMapTblDV.put(1, SwingConstants.CENTER);
		setCellAlignMapTblDV.put(2, SwingConstants.RIGHT);
		setCellAlignMapTblDV.put(3, SwingConstants.RIGHT);
		setCellAlignMapTblDV.put(4, SwingConstants.RIGHT);
		setCellAlignMapTblDV.put(5, SwingConstants.RIGHT);

		SpinnerEditor spinnerEditor = (SpinnerEditor) tblDV.getColumnModel().getColumn(3).getCellEditor();
		JSpinner spinner = spinnerEditor.getSpinner();
		spinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int row = tblDV.getEditingRow();
				Long giaDV = VietnameseFormatter.dinhDangTienNguoc(tblDV.getValueAt(row, 2).toString());
				int soLuongChon = (int) spinner.getValue();
				tblDV.setValueAt(VietnameseFormatter.dinhDangTien(giaDV * soLuongChon), row, 4);
				lamMoiTien();
			}
		});

		tblDV.setColumnCenterAndCellAlign(setCellAlignMapTblDV);

		spDanhSachDichVu.setViewportView(tblDV);
		pnThanhToan.add(spDanhSachDichVu, "cell 0 11 3 1,grow");
		txtTongTienTruocKhuyenMai = new JTextField();
		txtTongTienTruocKhuyenMai.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtTongTienTruocKhuyenMai.setColumns(10);
		pnThanhToan.add(txtTongTienTruocKhuyenMai, "cell 0 12 2 1,grow");

		txtTongTienSauKhuyenMai = new JTextField();
		txtTongTienSauKhuyenMai.setHorizontalAlignment(SwingConstants.TRAILING);
		txtTongTienSauKhuyenMai.setForeground(Color.RED);
		txtTongTienSauKhuyenMai.setFont(new Font("Times New Roman", Font.BOLD, 15));
		txtTongTienSauKhuyenMai.setColumns(10);
		pnThanhToan.add(txtTongTienSauKhuyenMai, "cell 2 12,grow");

		btnDatHayThanhToan = new MySwitchButton(new SwitchButtonModel("Đặt", "Thanh toán", Color.gray, Color.gray));
		btnDatHayThanhToan.setOn(false);
		btnDatHayThanhToan.addEventSwitchSelected(this);
		pnThanhToan.add(btnDatHayThanhToan, "cell 0 13 3 1,grow");

		pnDatVaThanhToan = new JPanel();
		pnThanhToan.add(pnDatVaThanhToan, "cell 0 14 3 1,grow");
		pnDatVaThanhToan
				.setLayout(new MigLayout("", "[106px][grow]", "[30px:40px:40px][30px:40px:40px][30px:40px:40px]"));

		JLabel lblTienKhachDua = new JLabel("Số tiền khách đưa");
		lblTienKhachDua.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnDatVaThanhToan.add(lblTienKhachDua, "cell 0 0,alignx trailing");

		spnTienKhachDua = new JSpinner(new SpinnerNumberModel(0L, 0L, null, 1000L));
		spnTienKhachDua.setForeground(Color.WHITE);
		spnTienKhachDua.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnDatVaThanhToan.add(spnTienKhachDua, "cell 1 0,grow");
		spnTienKhachDua.addChangeListener(this);

		JLabel lblTienThoi = new JLabel("Tiền thối");
		lblTienThoi.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnDatVaThanhToan.add(lblTienThoi, "cell 0 1,alignx trailing");

		txtTienThoi = new JTextField();
		txtTienThoi.setForeground(Color.WHITE);
		txtTienThoi.setHorizontalAlignment(SwingConstants.TRAILING);
		txtTienThoi.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtTienThoi.setColumns(10);
		pnDatVaThanhToan.add(txtTienThoi, "cell 1 1,grow");

		lblThoiHan = new JLabel("Thời hạn");
		lblThoiHan.setHorizontalAlignment(SwingConstants.TRAILING);
		lblThoiHan.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnDatVaThanhToan.add(lblThoiHan, "cell 0 2,alignx trailing");

		txtThoiHan = new JTextField();
		txtThoiHan.setForeground(Color.WHITE);
		txtThoiHan.setText(VietnameseFormatter.dinhDangNgayString(LocalDate.now().plusDays(7)));
		txtThoiHan.setHorizontalAlignment(SwingConstants.TRAILING);
		txtThoiHan.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtThoiHan.setColumns(10);
		pnDatVaThanhToan.add(txtThoiHan, "cell 1 2,grow");

		btnDatVaThanhToan = new JButton("Thanh toán");
		btnDatVaThanhToan.addActionListener(this);

		btnLamMoiThongTin = new JButton("Làm mới");
		btnLamMoiThongTin.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnThanhToan.add(btnLamMoiThongTin, "cell 0 15 2 1,grow");
		btnDatVaThanhToan.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnThanhToan.add(btnDatVaThanhToan, "cell 2 15,grow");
		btnLamMoiThongTin.addActionListener(this);

		dsTextField.add(txtMaKH);
		dsTextField.add(txtGioiTinh);
		dsTextField.add(txtSDT);
		dsTextField.add(txtHoiVien);
		dsTextField.add(txtHoTen);
		dsTextField.add(txtMaPV);
		dsTextField.add(txtHoTenPV);
		dsTextField.add(txtTongTienTruocKhuyenMai);
		dsTextField.add(txtTongTienSauKhuyenMai);
		dsTextField.add(txtTienThoi);
		dsTextField.add(txtThoiHan);

		txtCanhBao = new JLabel(
				"Thông tin lọc bên trên chỉ là kết quả tìm kiếm đầu tiên, thông tin lọc càng chính xác thì kết quả càng chuẩn");
		txtCanhBao.setForeground(Color.ORANGE);
		txtCanhBao.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		add(txtCanhBao, "cell 0 2 3 1,alignx right");
		for (JTextField jTextField : dsTextField) {
			jTextField.setEditable(false);
			jTextField.setFocusable(false);
		}
		lamMoiTien();
	}

	private Long maHD;

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(spnTienKhachDua)) {
			lamMoiTien();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnHienDSDonDat)) {
			lblDanhSach.setText("Danh sách Đơn đặt");
			pnDanhSach.removeAll();
			txtTimKiemDD.setEnabled(true);
			txtTimKiemDD.addEventOptionSelected(new SearchOptinEvent() {
				@Override
				public void optionSelected(SearchOption option, int index) {
					txtTimKiemDD.setHint("Tìm kiếm đơn đặt bởi " + option.getName() + "...");
				}
			});
			txtTimKiemDD.addOption(new SearchOption("mã hóa đơn",
					new MyImageIcon("src/main/resources/images/icons/id.png", 20, 20, 0)));
			txtTimKiemDD.addOption(new SearchOption("mã khách hàng",
					new MyImageIcon("src/main/resources/images/icons/customerid.png", 20, 20, 0)));
			txtTimKiemDD.addOption(new SearchOption("họ tên khách hàng",
					new MyImageIcon("src/main/resources/images/icons/customername.png", 20, 20, 0)));
			txtTimKiemDD.addOption(new SearchOption("số điện thoại khách hàng",
					new MyImageIcon("src/main/resources/images/icons/tel.png", 20, 20, 0)));
			txtTimKiemDD.addOption(new SearchOption("mã nhân viên tiếp tân",
					new MyImageIcon("src/main/resources/images/icons/employeeid.png", 20, 20, 0)));
			txtTimKiemDD.addOption(new SearchOption("họ tên nhân viên tiếp tân",
					new MyImageIcon("src/main/resources/images/icons/employeename.png", 20, 20, 0)));
			txtTimKiemDD.setColumns(10);
			txtTimKiemDD.setSelectedIndex(0);
			txtTimKiemDD.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyReleased(java.awt.event.KeyEvent evt) {
					if (txtTimKiemDD.isSelected()) {
						int option = txtTimKiemDD.getSelectedIndex();
						String searchText = txtTimKiemDD.getText().trim();
						String searchTextLike = "%" + searchText + "%";
						try {
							if (searchText.trim().equals("")) {
								hienThiDanhSachDonDatVoiDieuKien(
										"where trangthai = 'CHƯATHANHTOÁN' order BY thoigianlap desc", "");
							} else {
								switch (option) {
								case 0:
									hienThiDanhSachDonDatVoiDieuKien(
											"where mahd like ? AND trangthai = 'CHƯATHANHTOÁN' order BY thoigianlap desc",
											"", searchTextLike);
									break;
								case 1:
									hienThiDanhSachDonDatVoiDieuKien(
											"where makh like ? AND trangthai = 'CHƯATHANHTOÁN' order BY thoigianlap desc",
											"", searchTextLike);
									break;
								case 2:
									hienThiDanhSachDonDatVoiDieuKien(
											"where trangthai = 'CHƯATHANHTOÁN' order BY thoigianlap desc",
											"khachhang:hoten:" + searchText);
									break;
								case 3:
									hienThiDanhSachDonDatVoiDieuKien(
											"where trangthai = 'CHƯATHANHTOÁN' order BY thoigianlap desc",
											"khachhang:sdt:" + searchText);
									break;
								case 4:
									hienThiDanhSachDonDatVoiDieuKien(
											"where trangthai = 'CHƯATHANHTOÁN' order BY thoigianlap desc",
											"tieptan:manvtt:"+searchText);
									break;
								case 5:
									hienThiDanhSachDonDatVoiDieuKien(
											"where trangthai = 'CHƯATHANHTOÁN' order BY thoigianlap desc",
											"tieptan:hoten:" + searchText);
									break;
								default:
									hienThiDanhSachDonDatVoiDieuKien(
											"where trangthai = 'CHƯATHANHTOÁN' order BY thoigianlap desc", "");
									break;
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			});
			try {
				pnDanhSach.add(danhSachChiTietHoaDon(), BorderLayout.CENTER);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			txtTimKiemDD.revalidate();
			txtTimKiemDD.repaint();
			pnDanhSach.revalidate();
			pnDanhSach.repaint();
		} else if (o.equals(btnHienDSPhong)) {
			if (khoaDonDat) {
				MyNotification jPanel = new MyNotification(chinhDeskstop, MyNotification.Type.WARNING,
						MyNotification.Location.CENTER,
						"Đang thực hiện thao tác đơn đặt, không thể thêm danh sách phòng mới");
				jPanel.showNotification();
				return;
			} else {
				lblDanhSach.setText("Danh sách Phòng");
				pnDanhSach.removeAll();
				txtTimKiemDD.setEnabled(true);
				txtTimKiemDD.addEventOptionSelected(new SearchOptinEvent() {
					@Override
					public void optionSelected(SearchOption option, int index) {
						txtTimKiemDD.setHint("Tìm kiếm phòng bởi " + option.getName() + "...");
					}
				});
				txtTimKiemDD.addOption(new SearchOption("mã phòng",
						new MyImageIcon("src/main/resources/images/icons/id.png", 20, 20, 0)));
				txtTimKiemDD.addOption(new SearchOption("tên phòng",
						new MyImageIcon("src/main/resources/images/icons/name.png", 20, 20, 0)));
				txtTimKiemDD.addOption(new SearchOption("loại phòng",
						new MyImageIcon("src/main/resources/images/icons/servicetype.png", 20, 20, 0)));
				txtTimKiemDD.setColumns(10);
				txtTimKiemDD.setSelectedIndex(0);
				txtTimKiemDD.addKeyListener(new java.awt.event.KeyAdapter() {
					public void keyReleased(java.awt.event.KeyEvent evt) {
						if (txtTimKiemDD.isSelected()) {
							int option = txtTimKiemDD.getSelectedIndex();
							String searchText = "%" + txtTimKiemDD.getText().trim() + "%";
							try {
								switch (option) {
								case 0:
									hienThiDanhSachPhongVoiDieuKien("where maphong like ?", searchText);
									break;
								case 1:
									hienThiDanhSachPhongVoiDieuKien("where tenphong like ?", searchText);
									break;
								case 2:
									hienThiDanhSachPhongVoiDieuKien("where loaiphong like ?", searchText);
									break;
								case 3:
									hienThiDanhSachPhongVoiDieuKien("where trangthai like ?", searchText);
									break;
								default:
									hienThiDanhSachPhongVoiDieuKien("");
									break;
								}
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}
						}
					}
				});
				try {
					pnDanhSach.add(danhSachChiTietPhong(), BorderLayout.CENTER);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				txtTimKiemDD.revalidate();
				txtTimKiemDD.repaint();
				pnDanhSach.revalidate();
				pnDanhSach.repaint();
			}
		} else if (o.equals(btnHienDSDV)) {
			if (khoaDonDat) {
				MyNotification jPanel = new MyNotification(chinhDeskstop, MyNotification.Type.WARNING,
						MyNotification.Location.CENTER,
						"Đang thực hiện thao tác đơn đặt, không thể thêm danh sách phòng mới");
				jPanel.showNotification();
				return;
			} else {
				lblDanhSach.setText("Danh sách Dịch vụ");
				pnDanhSach.removeAll();
				txtTimKiemDD.setEnabled(true);
				txtTimKiemDD.addEventOptionSelected(new SearchOptinEvent() {
					@Override
					public void optionSelected(SearchOption option, int index) {
						txtTimKiemDD.setHint("Tìm kiếm dịch vụ bởi " + option.getName() + "...");
					}
				});
				txtTimKiemDD.addOption(new SearchOption("mã dịch vụ",
						new MyImageIcon("src/main/resources/images/icons/id.png", 20, 20, 0)));
				txtTimKiemDD.addOption(new SearchOption("tên dịch vụ",
						new MyImageIcon("src/main/resources/images/icons/name.png", 20, 20, 0)));
				txtTimKiemDD.addOption(new SearchOption("loại dịch vụ",
						new MyImageIcon("src/main/resources/images/icons/servicetype.png", 20, 20, 0)));
				txtTimKiemDD.setColumns(10);
				txtTimKiemDD.setSelectedIndex(0);
				txtTimKiemDD.addKeyListener(new java.awt.event.KeyAdapter() {
					public void keyReleased(java.awt.event.KeyEvent evt) {
						if (txtTimKiemDD.isSelected()) {
							int option = txtTimKiemDD.getSelectedIndex();
							String searchText = "%" + txtTimKiemDD.getText().trim() + "%";
							try {
								switch (option) {
								case 0:
									hienThiDanhSachDVVoiDieuKien("where madv like ?", searchText);
									break;
								case 1:
									hienThiDanhSachDVVoiDieuKien("where tendv like ?", searchText);
									break;
								case 2:
									hienThiDanhSachDVVoiDieuKien("where loaidv like ?", searchText);
									break;
								case 3:
									hienThiDanhSachDVVoiDieuKien("where trangthai like ?", searchText);
									break;
								default:
									hienThiDanhSachDVVoiDieuKien("");
									break;
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				});
				try {
					pnDanhSach.add(danhSachChiTietDichVu(), BorderLayout.CENTER);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				txtTimKiemDD.revalidate();
				txtTimKiemDD.repaint();
				pnDanhSach.revalidate();
				pnDanhSach.repaint();
			}
		} else if (o.equals(btnLamMoiThongTin)) {
			lamMoiThongTin();
		}
		if (o.equals(btnDatVaThanhToan)) {
			if (btnDatHayThanhToan.isOn()) { // Đặt
				KhachHang khachHang = null;
				if (!txtMaKH.getText().trim().equals("")) {
					try {
						khachHang = khachHangIDAO.layTheoMa(Long.parseLong(txtMaKH.getText()));
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					MyNotification jPanel = new MyNotification(chinhDeskstop, MyNotification.Type.WARNING,
							MyNotification.Location.CENTER, "Không thể tạo đơn đặt mà không có thông tin khách hàng");
					jPanel.showNotification();
					return;
				}
				HoaDon hoaDon = new HoaDon();
				try {
					hoaDonIDAO.themMot(hoaDon);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				hoaDon.setKhachHang(khachHang);
				if (!txtMaPV.getText().trim().equals("")) {
					try {
						hoaDon.setPhucVu(nhanVienIDAO.layTheoMa(Long.parseLong(txtMaPV.getText())));
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				if (nguoiDung instanceof NhanVien) {
					NhanVien tiepTan = (NhanVien) nguoiDung;
					hoaDon.setTiepTan(tiepTan);
				}
				hoaDon.setKhuyenMai(VietnameseFormatter.dinhDangTienNguoc(txtTongTienTruocKhuyenMai.getText())
						- VietnameseFormatter.dinhDangTienNguoc(txtTongTienSauKhuyenMai.getText()));
				hoaDon.setTrangThai(TrangThaiHoaDon.CHƯATHANHTOÁN);
				for (int i = 0; i < tblPhong.getRowCount(); i++) {
					ChiTietHoaDonPhong chiTietHoaDonPhong = new ChiTietHoaDonPhong();
					chiTietHoaDonPhong
							.setDonGia(VietnameseFormatter.dinhDangTienNguoc(tblPhong.getValueAt(i, 2).toString()));
					chiTietHoaDonPhong.setHoaDon(hoaDon);
					Phong phong = null;
					try {
						phong = phongIDAO.layTheoMa(Long.parseLong(tblPhong.getValueAt(i, 0).toString()));
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					chiTietHoaDonPhong.setPhong(phong);
					if (phong.getTrangThai() == TrangThaiPhong.TRỐNG) {
						phong.setTrangThai(TrangThaiPhong.ĐANGSỬDỤNG);
						try {
							phongIDAO.suaMot(phong);
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else {
						MyNotification jPanel = new MyNotification(chinhDeskstop, MyNotification.Type.WARNING,
								MyNotification.Location.CENTER, "Phòng vừa mới ai đó đặt rồi");
						jPanel.showNotification();
						return;
					}
					chiTietHoaDonPhong.setThoiGianBatDau(VietnameseFormatter
							.dinhDangNgayThoiGianLocalDateTime(tblPhong.getValueAt(i, 3).toString()));
					chiTietHoaDonPhong.setThoiGianKetThuc(
							VietnameseFormatter.dinhDangThoiGianLocalTime(tblPhong.getValueAt(i, 4).toString()));
					try {
						chiTietHoaDonPhongIDAO.themMot(chiTietHoaDonPhong);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					hoaDon.getDsCTHDP().add(chiTietHoaDonPhong);

				}
				// (long donGia, int soLuong, HoaDon hoaDon, DichVu dichVu) {
				for (int i = 0; i < tblDV.getRowCount(); i++) {
					ChiTietHoaDonDichVu chiTietHoaDonDichVu = new ChiTietHoaDonDichVu();
					DichVu dichVu = null;
					try {
						dichVu = dichVuIDAO.layTheoMa(Long.parseLong(tblDV.getValueAt(i, 0).toString()));
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					chiTietHoaDonDichVu.setDichVu(dichVu);

					chiTietHoaDonDichVu
							.setDonGia(VietnameseFormatter.dinhDangTienNguoc(tblDV.getValueAt(i, 2).toString()));
					chiTietHoaDonDichVu.setHoaDon(hoaDon);
					chiTietHoaDonDichVu.setSoLuong(Integer.parseInt(tblDV.getValueAt(i, 3).toString()));
					if (dichVu.getSoLuong() >= chiTietHoaDonDichVu.getSoLuong()) {
						dichVu.setSoLuong(dichVu.getSoLuong() - chiTietHoaDonDichVu.getSoLuong());
						if (dichVu.getSoLuong() == 0) {
							dichVu.setTrangThai(TrangThaiDichVu.NGỪNGKINHDOANH);
							try {
								dichVuIDAO.suaMot(dichVu);
							} catch (RemoteException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					} else {
						MyNotification jPanel = new MyNotification(chinhDeskstop, MyNotification.Type.WARNING,
								MyNotification.Location.CENTER,
								"Số lượng của dịch vụ đã chọn lớn hơn số lượng trong kho");
						jPanel.showNotification();
						tblDV.setValueAt(dichVu.getSoLuong(), i, 3);
						return;
					}
					hoaDon.getDsCTHDDV().add(chiTietHoaDonDichVu);
				}

				try {
					if (hoaDonIDAO.suaMot(hoaDon) == null) {
						MyNotification jPanel = new MyNotification(chinhDeskstop, MyNotification.Type.WARNING,
								MyNotification.Location.CENTER, "Đã có lỗi");
						jPanel.showNotification();
					} else {
						lamMoiThongTin();
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {// Thanh toán
				if (VietnameseFormatter.dinhDangTienNguoc(txtTienThoi.getText()) < 0) {
					MyNotification jPanel = new MyNotification(chinhDeskstop, MyNotification.Type.WARNING,
							MyNotification.Location.CENTER, "Tiền thối âm kìa, muốn mất việc hả?");
					jPanel.showNotification();
					return;
				}
				HoaDon hoaDon = null;
				if (khoaDonDat) {
					try {
						hoaDon = hoaDonIDAO.layTheoMa(maHD);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (hoaDon.getThoiHan().isBefore(LocalDateTime.now())) {
						MyNotification jPanel = new MyNotification(chinhDeskstop, MyNotification.Type.WARNING,
								MyNotification.Location.CENTER, "Đơn đã bị hết hạn");
						jPanel.showNotification();
						hoaDon.setTrangThai(TrangThaiHoaDon.ĐÃHẾTHẠN);
						try {
							hoaDonIDAO.suaMot(hoaDon);
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						lamMoiThongTin();
						return;
					}
					if (hoaDon.getTrangThai() != TrangThaiHoaDon.CHƯATHANHTOÁN) {
						MyNotification jPanel = new MyNotification(chinhDeskstop, MyNotification.Type.WARNING,
								MyNotification.Location.CENTER, "Đơn đã ai đó thanh toán");
						jPanel.showNotification();
						lamMoiThongTin();
						return;
					}
				} else {
					hoaDon = new HoaDon();
					try {
						hoaDonIDAO.themMot(hoaDon);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (!txtMaKH.getText().trim().equals("")) {
						try {
							hoaDon.setKhachHang(khachHangIDAO.layTheoMa(Long.parseLong(txtMaKH.getText())));
						} catch (NumberFormatException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

					if (!txtMaPV.getText().trim().equals("")) {
						try {
							hoaDon.setPhucVu(nhanVienIDAO.layTheoMa(Long.parseLong(txtMaPV.getText())));
						} catch (NumberFormatException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					if (nguoiDung instanceof NhanVien) {
						NhanVien tiepTan = (NhanVien) nguoiDung;
						hoaDon.setTiepTan(tiepTan);
					}

					hoaDon.setKhuyenMai(VietnameseFormatter.dinhDangTienNguoc(txtTongTienTruocKhuyenMai.getText())
							- VietnameseFormatter.dinhDangTienNguoc(txtTongTienSauKhuyenMai.getText()));
					for (int i = 0; i < tblPhong.getRowCount(); i++) {
						ChiTietHoaDonPhong chiTietHoaDonPhong = new ChiTietHoaDonPhong();
						chiTietHoaDonPhong
								.setDonGia(VietnameseFormatter.dinhDangTienNguoc(tblPhong.getValueAt(i, 2).toString()));
						chiTietHoaDonPhong.setHoaDon(hoaDon);
						Phong phong = null;
						try {
							phong = phongIDAO.layTheoMa(Long.parseLong(tblPhong.getValueAt(i, 0).toString()));
						} catch (NumberFormatException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						chiTietHoaDonPhong.setPhong(phong);
						if (phong.getTrangThai() == TrangThaiPhong.TRỐNG) {
							phong.setTrangThai(TrangThaiPhong.ĐANGSỬDỤNG);
							try {
								phongIDAO.suaMot(phong);
							} catch (RemoteException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} else {
							MyNotification jPanel = new MyNotification(chinhDeskstop, MyNotification.Type.WARNING,
									MyNotification.Location.CENTER, "Phòng vừa mới ai đó đặt rồi");
							jPanel.showNotification();
							return;
						}
						chiTietHoaDonPhong.setThoiGianBatDau(VietnameseFormatter
								.dinhDangNgayThoiGianLocalDateTime(tblPhong.getValueAt(i, 3).toString()));
						chiTietHoaDonPhong.setThoiGianKetThuc(
								VietnameseFormatter.dinhDangThoiGianLocalTime(tblPhong.getValueAt(i, 4).toString()));
						try {
							chiTietHoaDonPhong.setHoaDon(hoaDon);
							chiTietHoaDonPhongIDAO.themMot(chiTietHoaDonPhong);
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						hoaDon.getDsCTHDP().add(chiTietHoaDonPhong);

					}
					// (long donGia, int soLuong, HoaDon hoaDon, DichVu dichVu) {
					for (int i = 0; i < tblDV.getRowCount(); i++) {
						ChiTietHoaDonDichVu chiTietHoaDonDichVu = new ChiTietHoaDonDichVu();
						DichVu dichVu = null;
						try {
							dichVu = dichVuIDAO.layTheoMa(Long.parseLong(tblDV.getValueAt(i, 0).toString()));
						} catch (NumberFormatException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						chiTietHoaDonDichVu.setDichVu(dichVu);
						chiTietHoaDonDichVu
								.setDonGia(VietnameseFormatter.dinhDangTienNguoc(tblDV.getValueAt(i, 2).toString()));
						chiTietHoaDonDichVu.setHoaDon(hoaDon);
						chiTietHoaDonDichVu.setSoLuong(Integer.parseInt(tblDV.getValueAt(i, 3).toString()));
						if (dichVu.getSoLuong() >= chiTietHoaDonDichVu.getSoLuong()) {
							dichVu.setSoLuong(dichVu.getSoLuong() - chiTietHoaDonDichVu.getSoLuong());
							if (dichVu.getSoLuong() == 0) {
								dichVu.setTrangThai(TrangThaiDichVu.NGỪNGKINHDOANH);
								try {
									dichVuIDAO.suaMot(dichVu);
								} catch (RemoteException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							try {
								chiTietHoaDonDichVu.setHoaDon(hoaDon);
								chiTietHoaDonDichVuIDAO.themMot(chiTietHoaDonDichVu);
							} catch (RemoteException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} else {
							MyNotification jPanel = new MyNotification(chinhDeskstop, MyNotification.Type.WARNING,
									MyNotification.Location.CENTER,
									"Số lượng của dịch vụ đã chọn lớn hơn số lượng trong kho");
							jPanel.showNotification();
							tblDV.setValueAt(dichVu.getSoLuong(), i, 3);
							return;
						}
						hoaDon.getDsCTHDDV().add(chiTietHoaDonDichVu);
					}
					hoaDon.setThoiGianLap(LocalDateTime.now());
					hoaDon.setThoiHan(LocalDateTime.now().plusDays(7));
				}
				hoaDon.setTrangThai(TrangThaiHoaDon.ĐÃTHANHTOÁN);
				hoaDon.setTienKhachDua((long) spnTienKhachDua.getValue());
				hoaDon.setThoiGianThanhToan(LocalDateTime.now());
				try {
					if (hoaDonIDAO.suaMot(hoaDon) == null) {
						MyNotification jPanel = new MyNotification(chinhDeskstop, MyNotification.Type.WARNING,
								MyNotification.Location.CENTER, "Đã có lỗi");
						jPanel.showNotification();
					} else {
						MyNotification jPanel = new MyNotification(chinhDeskstop, MyNotification.Type.SUCCESS,
								MyNotification.Location.CENTER, "Đã thành công");
						jPanel.showNotification();

						try {
							String filePath = "Hóa đơn.pdf"; // Thay đổi đường dẫn tới tệp PDF của bạn
							HoaDonPDF hoaDonPDF = new HoaDonPDF(hoaDon, filePath);
							File pdfFile = new File(filePath);
							if (pdfFile.exists()) {
								Desktop.getDesktop().open(pdfFile);
							} else {
								System.out.println("Tệp PDF không tồn tại.");
							}
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							MyNotification jPanel2 = new MyNotification(chinhDeskstop, MyNotification.Type.WARNING,
									MyNotification.Location.CENTER, "Đã gặp lỗi xuất và mở PDF: ");
							jPanel2.showNotification();
							e1.printStackTrace();
						}
						lamMoiThongTin();
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} else if (o.equals(btnLamMoiDanhSach)) {
			txtTimKiemDD.setText("");
			txtTimKiemDD.setSelectedIndex(0);
		}
	}

	private JScrollPane danhSachChiTietHoaDon() throws RemoteException {
		// TODO Auto-generated method stub
		tblChiTietDonDat = new MyTable();
		tblChiTietDonDat.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Mã hóa đơn", "Họ tên khách hàng", "Số điện thoại khách", "Họ tên tiếp tân",
						"Thời gian lập", "Thời hạn", "Mã khách hàng", "Mã tiếp tân" }) {
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false;
			}
		});
		tblChiTietDonDat.addMouseListener(new MouseAdapter() {
			private HoaDon hoaDon;

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

				if (SwingUtilities.isRightMouseButton(e)) {
				} else {
					try {
						hoaDon = hoaDonIDAO.layTheoMa(Long.parseLong(
								tblChiTietDonDat.getValueAt(tblChiTietDonDat.getSelectedRow(), 0).toString()));
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					maHD = hoaDon.getMaHD();

					MyMessage myMessage = null;
					myMessage = new MyMessage("Thanh toán hóa đơn",
							"Nếu chọn có thì các trường thông tin sẽ bị ghi đè, bạn có chắc không?", true);
					myMessage.eventOK(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent ae) {
							lamMoiThongTin();
							GlassPanePopup.closePopupAll();
							if (hoaDon.getTrangThai() != TrangThaiHoaDon.CHƯATHANHTOÁN) {
								MyNotification panel = new MyNotification(chinhDeskstop, MyNotification.Type.WARNING,
										MyNotification.Location.CENTER, "Hóa đơn đã thanh toán rồi!");
								panel.showNotification();
							} else {
								khoaDonDat = true;
								pnThanhToan.remove(txtTimKiemKH);
								pnThanhToan.remove(txtTimKiemPV);
								btnDatHayThanhToan.setOn(false);
								btnDatHayThanhToan.setEnabled(false);
								DefaultTableModel tblmChiTietDonDat = (DefaultTableModel) tblChiTietDonDat.getModel();
								tblDV.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Mã dịch vụ",
										"Tên dịch vụ", "Giá dịch vụ", "Số lượng chọn", "Thành tiền", "Chức năng" }) {
									boolean[] canEdit = new boolean[] { false, false, false, false, false, true };

									public boolean isCellEditable(int rowIndex, int columnIndex) {
										return canEdit[columnIndex];
										// return false;
									}
								});
								tblDV.getColumnModel().getColumn(5)
										.setCellRenderer(new TableActionCellRender(false, false));
								tblDV.getColumnModel().getColumn(5)
										.setCellEditor(new TableActionCellEditor(eventDichVu, false, false));

								tblDV.revalidate();
								tblDV.repaint();
								tblPhong.setModel(new DefaultTableModel(new Object[][] {},
										new String[] { "Mã phòng", "Tên phòng", "Giá phòng", "Bắt đầu", "Kết thúc",
												"Số giờ", "Thành tiền", "Chức năng" }) {
									boolean[] canEdit = new boolean[] { false, false, false, false, false, false, false,
											true };

									public boolean isCellEditable(int rowIndex, int columnIndex) {
										return canEdit[columnIndex];
										// return false;
									}
								});
								tblPhong.getColumnModel().getColumn(7)
										.setCellRenderer(new TableActionCellRender(false, false));
								tblPhong.getColumnModel().getColumn(7)
										.setCellEditor(new TableActionCellEditor(eventPhong, false, false));
								tblPhong.revalidate();
								tblPhong.repaint();
								KhachHang khachHang = hoaDon.getKhachHang();
								NhanVien phucVu = hoaDon.getPhucVu();
								txtMaKH.setText(khachHang.getMaND() + "");
								txtHoTen.setText(khachHang.getHoTen());
								txtSDT.setText(khachHang.getSdt());
								txtHoiVien.setText(khachHang.getHoiVien().getHoiVien());
								txtGioiTinh.setText(khachHang.getGioiTinh().getGioiTinh());
								txtMaPV.setText(phucVu.getMaND() + "");
								txtHoTenPV.setText(phucVu.getHoTen());
								hoaDon.getDsCTHDP().stream().forEach(cthdp -> {
									Phong phong = cthdp.getPhong();
									int khoangThoiGian = 1;
									((DefaultTableModel) tblPhong.getModel()).addRow(new String[] {
											phong.getMaPhong() + "", phong.getTenPhong(),
											VietnameseFormatter.dinhDangTien(phong.getGiaPhong()),
											VietnameseFormatter.dinhDangNgayThoiGianString(cthdp.getThoiGianBatDau()),
											VietnameseFormatter.dinhDangThoiGianString(cthdp.getThoiGianKetThuc()),
											(khoangThoiGian = VietnameseFormatter.tinhKhoangThoiGian(
													cthdp.getThoiGianKetThuc(), cthdp.getThoiGianBatDau())) + "",
											VietnameseFormatter.dinhDangTien(khoangThoiGian * cthdp.getDonGia()) });
								});

								hoaDon.getDsCTHDDV().stream().forEach(cthddv -> {
									DichVu dichVu = cthddv.getDichVu();
									((DefaultTableModel) tblDV.getModel()).addRow(new String[] { dichVu.getMaDV() + "",
											dichVu.getTenDV(), VietnameseFormatter.dinhDangTien(cthddv.getDonGia()),
											cthddv.getSoLuong() + "", VietnameseFormatter
													.dinhDangTien(cthddv.getDonGia() * cthddv.getSoLuong()) });
								});
								lamMoiTien();
							}

						}
					});
					GlassPanePopup.showPopup(myMessage);
				}
			}
		});
		Map<Integer, Integer> setCellAlignMap = new HashMap<Integer, Integer>();
		setCellAlignMap.put(0, SwingConstants.RIGHT);
		setCellAlignMap.put(2, SwingConstants.RIGHT);
		setCellAlignMap.put(4, SwingConstants.RIGHT);
		setCellAlignMap.put(5, SwingConstants.RIGHT);
		setCellAlignMap.put(6, SwingConstants.RIGHT);
		setCellAlignMap.put(7, SwingConstants.RIGHT);
		((MyTable) tblChiTietDonDat).setColumnCenterAndCellAlign(setCellAlignMap);
		JScrollPane spChiTietHoaDon = new JScrollPane();
		spChiTietHoaDon.setViewportView(tblChiTietDonDat);
		spChiTietHoaDon.getVerticalScrollBar().setUnitIncrement(16);
		hienThiDanhSachDonDatVoiDieuKien("where trangthai = 'CHƯATHANHTOÁN' order BY thoigianlap desc", "");
		return spChiTietHoaDon;
	}

	private JScrollPane danhSachChiTietPhong() throws RemoteException {
		// TODO Auto-generated method stub
		pnChiTietPhong = new JPanel(new GridBagLayout());
		pnChiTietPhong.setOpaque(false);
		JScrollPane spChiTietPhong = new JScrollPane();
		spChiTietPhong.setOpaque(false);
		spChiTietPhong.setViewportView(pnChiTietPhong);
		spChiTietPhong.getVerticalScrollBar().setUnitIncrement(16);
		hienThiDanhSachPhongVoiDieuKien("");
		return spChiTietPhong;
	}

	private JScrollPane danhSachChiTietDichVu() throws RemoteException {
		// TODO Auto-generated method stub
		pnChiTietDV = new JPanel(new GridBagLayout());
		pnChiTietDV.setOpaque(false);
		JScrollPane spChiTietDV = new JScrollPane();
		spChiTietDV.setOpaque(false);
		spChiTietDV.setViewportView(pnChiTietDV);
		spChiTietDV.getVerticalScrollBar().setUnitIncrement(16);
		hienThiDanhSachDVVoiDieuKien("");
		return spChiTietDV;
	}

	private final int NUMBEROFCOLUMN = 3;
	private JTextField txtMaKH;
	private JTextField txtHoiVien;
	private JTextField txtGioiTinh;
	private JTextField txtMaPV;
	private JLabel txtCanhBao;
	private JTextField txtThoiHan;
	private JLabel lblThoiHan;
	private JTextField textField;
	private List<HoaDon> dsHoaDon;

	public void hienThiDanhSachDonDatVoiDieuKien(String where, String whereForeign, Object... objectSearch)
			throws RemoteException {
		DefaultTableModel defaultTableModel = (DefaultTableModel) tblChiTietDonDat.getModel();
		defaultTableModel.setRowCount(0);
		dsHoaDon = new Vector<HoaDon>(hoaDonIDAO.timKiemMotTruong(where, objectSearch));
		if (!dsHoaDon.isEmpty() && dsHoaDon != null) {
			txtTimKiemDD.setFocusable(false);
			txtTimKiemDD.setEditable(false);
		}
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() {
				try {
					synchronized (dsHoaDon) {
						dsHoaDon = dsHoaDon.stream().filter(hoadon -> {
							if (whereForeign.trim().equals("")) {
								return true;
							} else {
								String[] object = whereForeign.split(":");
								if (object[0].equals("khachhang")) {
									switch (object[1]) {
									case "hoten":
										return hoadon.getKhachHang() != null
												&& hoadon.getKhachHang().getHoTen().contains(object[2]);
									case "sdt":
										return hoadon.getKhachHang() != null
												&& hoadon.getKhachHang().getSdt().contains(object[2]);
									default:
										return false;
									}
								} else if (object[0].equals("tieptan")) {
									switch (object[1]) {
									case "manvtt":
                                        return hoadon.getTiepTan() != null
                                                && (hoadon.getTiepTan().getMaND()+"").contains(object[2]);
									case "hoten":
										return hoadon.getTiepTan() != null
												&& hoadon.getTiepTan().getHoTen().contains(object[2]);
									default:
										return false;
									}
								} else {
									return false;
								}
							}
						}).collect(Collectors.toList());
						dsHoaDon.stream().forEach(hoaDon -> {
							String maHD = hoaDon.getMaHD() + "";
							String hoTenKhachHang = hoaDon.getKhachHang() != null ? hoaDon.getKhachHang().getHoTen()
									: "Không có";
							String sdtKhachHang = hoaDon.getKhachHang() != null ? hoaDon.getKhachHang().getSdt()
									: "Không có";
							String hoTenTiepTan = hoaDon.getTiepTan() != null ? hoaDon.getTiepTan().getHoTen()
									: "Không có";
							String thoiGianLap = VietnameseFormatter
									.dinhDangNgayThoiGianString(hoaDon.getThoiGianLap());
							String thoiHan = VietnameseFormatter.dinhDangNgayThoiGianString(hoaDon.getThoiHan());
							String maNDKhachHang = hoaDon.getKhachHang() != null ? hoaDon.getKhachHang().getMaND() + ""
									: "Không có";
							String maNDTiepTan = hoaDon.getTiepTan() != null ? hoaDon.getTiepTan().getMaND() + ""
									: "Không có";

							defaultTableModel.addRow(new String[] { maHD, hoTenKhachHang, sdtKhachHang, hoTenTiepTan,
									thoiGianLap, thoiHan, maNDKhachHang, maNDTiepTan });
						});
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;

			}

			@Override
			protected void done() {
				txtTimKiemDD.setEditable(true);
				txtTimKiemDD.setFocusable(true);
				txtTimKiemDD.requestFocus();
			}
		};

		worker.execute();
	}

	public void hienThiDanhSachPhongVoiDieuKien(String where, Object... objectSearch) throws RemoteException {
		pnChiTietPhong.removeAll(); // Xóa các thành phần hiển thị cũ
		List<Phong> dsPhong = new Vector<>(phongIDAO.timKiemMotTruong(where, objectSearch));
		new Thread(() -> {
			if (!dsPhong.isEmpty() && dsPhong != null) {
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.insets = new Insets(5, 5, 5, 5);

				synchronized (dsPhong) {
					dsPhong.stream().forEach(phong -> {
						RoomCardPanel pnChild = new RoomCardPanel(phong);
						pnChild.setPreferredSize(new Dimension(180, 200));
						pnChild.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2));

						// Cập nhật vị trí của phần tử
						gbc.gridx = dsPhong.indexOf(phong) % NUMBEROFCOLUMN;
						gbc.gridy = dsPhong.indexOf(phong) / NUMBEROFCOLUMN;

						pnChild.addMouseListener(new MouseListener() {
							private JPanel panel;

							@Override
							public void mouseClicked(MouseEvent e) {
								panel = (JPanel) e.getSource();
								panel.setBorder(new LineBorder(Color.GREEN, 3));
								panel.repaint();
							}

							@Override
							public void mouseEntered(MouseEvent e) {
								panel = (JPanel) e.getSource();
								panel.setBorder(new LineBorder(Color.BLACK, 3));
								panel.repaint();
							}

							private boolean isExit = false;

							@Override
							public void mouseExited(MouseEvent e) {
								panel = (JPanel) e.getSource();
								panel.setBorder(new LineBorder(Color.lightGray, 2));
								panel.repaint();
								isExit = true;
							}

							@Override
							public void mousePressed(MouseEvent e) {
								// TODO Auto-generated method stub
								mouseClicked(e);
							}

							@Override
							public void mouseReleased(MouseEvent e) {
								// TODO Auto-generated method stub
								if (isExit)
									mouseExited(e);
								else
									mouseEntered(e);
								if (SwingUtilities.isRightMouseButton(e)) {
									FormManager.getInstance().showForm("Thông tin chi tiết Phòng",
											new ThongTinChiTietPhongPanel(phong), 320, 700);
								} else {
									DefaultTableModel defaultTableModel = (DefaultTableModel) tblPhong.getModel();
									ArrayList<Long> danhSachPhongTrongBang = new ArrayList<Long>();
									for (int i = 0; i < defaultTableModel.getRowCount(); i++) {
										danhSachPhongTrongBang
												.add(Long.parseLong(defaultTableModel.getValueAt(i, 0).toString()));
									}
									if (phong.getTrangThai() != TrangThaiPhong.TRỐNG) {
										MyNotification panel = new MyNotification(chinhDeskstop,
												MyNotification.Type.WARNING, MyNotification.Location.CENTER,
												"Phòng đã được sử dụng hay bảo trì");
										panel.showNotification();
									} else if (!danhSachPhongTrongBang.contains(phong.getMaPhong())) {
										ChonNgayGioDatPhongPanel chonNgayGioDatPhongPanel;
										FormManager.getInstance().showForm("Chọn thời gian bắt đầu và kết thúc",
												chonNgayGioDatPhongPanel = new ChonNgayGioDatPhongPanel(), 400, 300)
												.addInternalFrameListener(new InternalFrameListener() {

													@Override
													public void internalFrameOpened(InternalFrameEvent e) {
														// TODO Auto-generated method stub

													}

													@Override
													public void internalFrameIconified(InternalFrameEvent e) {
														// TODO Auto-generated method stub

													}

													@Override
													public void internalFrameDeiconified(InternalFrameEvent e) {
														// TODO Auto-generated method stub

													}

													@Override
													public void internalFrameDeactivated(InternalFrameEvent e) {
														// TODO Auto-generated method stub

													}

													@Override
													public void internalFrameClosing(InternalFrameEvent e) {
														// TODO Auto-generated method stub

													}

													@Override
													public void internalFrameClosed(InternalFrameEvent e) {
														// TODO Auto-generated method stub
														LocalDateTime thoiGianBatDau = chonNgayGioDatPhongPanel
																.getThoiGianBatDau();
														LocalTime thoiGianKetThuc = chonNgayGioDatPhongPanel
																.getThoiGianKetThuc();
														if ((thoiGianBatDau != null) && (thoiGianKetThuc != null)) {
															int khoangThoiGian = 0;
															defaultTableModel.addRow(new String[] {
																	phong.getMaPhong() + "", phong.getTenPhong(),
																	VietnameseFormatter
																			.dinhDangTien(phong.getGiaPhong()),
																	VietnameseFormatter
																			.dinhDangNgayThoiGianString(thoiGianBatDau),
																	VietnameseFormatter
																			.dinhDangThoiGianString(thoiGianKetThuc),
																	(khoangThoiGian = VietnameseFormatter
																			.tinhKhoangThoiGian(thoiGianKetThuc,
																					thoiGianBatDau))
																			+ "",
																	VietnameseFormatter.dinhDangTien(
																			khoangThoiGian * phong.getGiaPhong()) });
															lamMoiTien();
														}
													}

													@Override
													public void internalFrameActivated(InternalFrameEvent e) {
														// TODO Auto-generated method stub

													}
												});

									} else {
										MyNotification panel = new MyNotification(chinhDeskstop,
												MyNotification.Type.WARNING, MyNotification.Location.CENTER,
												"Phòng này đã trong danh sách chọn");
										panel.showNotification();

									}
								}
							}
						});

						pnChiTietPhong.add(pnChild, gbc);
						pnChiTietPhong.revalidate();
						pnChiTietPhong.repaint();
					});
				}
			}
		}).start();
	}

	public void hienThiDanhSachDVVoiDieuKien(String where, Object... objectSearch) throws RemoteException {
		pnChiTietDV.removeAll(); // Xóa các thành phần hiển thị cũ
		List<DichVu> dsDV = new Vector<>(dichVuIDAO.timKiemMotTruong(where, objectSearch));
		new Thread(() -> {
			if (!dsDV.isEmpty() && dsDV != null) {
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.insets = new Insets(5, 5, 5, 5);

				synchronized (dsDV) {
					dsDV.stream().forEach(dichVu -> {
						ServiceCardPanel pnChild = new ServiceCardPanel(dichVu);
						pnChild.setPreferredSize(new Dimension(180, 200));
						pnChild.setPreferredSize(new Dimension(180, 230));
						pnChild.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2));

						// Cập nhật vị trí của phần tử
						gbc.gridx = dsDV.indexOf(dichVu) % NUMBEROFCOLUMN;
						gbc.gridy = dsDV.indexOf(dichVu) / NUMBEROFCOLUMN;

						pnChild.addMouseListener(new MouseListener() {
							private JPanel panel;

							@Override
							public void mouseClicked(MouseEvent e) {
								panel = (JPanel) e.getSource();
								panel.setBorder(new LineBorder(Color.GREEN, 3));
								panel.repaint();
							}

							@Override
							public void mouseEntered(MouseEvent e) {
								panel = (JPanel) e.getSource();
								panel.setBorder(new LineBorder(Color.BLACK, 3));
								panel.repaint();
							}

							private boolean isExit = false;

							@Override
							public void mouseExited(MouseEvent e) {
								panel = (JPanel) e.getSource();
								panel.setBorder(new LineBorder(Color.lightGray, 2));
								panel.repaint();
								isExit = true;
							}

							@Override
							public void mousePressed(MouseEvent e) {
								// TODO Auto-generated method stub
								mouseClicked(e);
							}

							@Override
							public void mouseReleased(MouseEvent e) {
								// TODO Auto-generated method stub
								if (isExit)
									mouseExited(e);
								else
									mouseEntered(e);
								if (SwingUtilities.isRightMouseButton(e)) {
									FormManager.getInstance().showForm("Thông tin chi tiết dịch vụ",
											new ThongTinChiTietDichVuPanel(dichVu), 320, 700);
								} else {
									DefaultTableModel defaultTableModel = (DefaultTableModel) tblDV.getModel();
									ArrayList<Long> danhSachDVTrongBang = new ArrayList<Long>();
									for (int i = 0; i < defaultTableModel.getRowCount(); i++) {
										danhSachDVTrongBang
												.add(Long.parseLong(defaultTableModel.getValueAt(i, 0).toString()));
									}
									if (dichVu.getTrangThai() != TrangThaiDichVu.KINHDOANH) {
										MyNotification panel = new MyNotification(chinhDeskstop,
												MyNotification.Type.WARNING, MyNotification.Location.CENTER,
												"Dịch vụ đã ngừng kinh doanh");
										panel.showNotification();
									} else if (!danhSachDVTrongBang.contains(dichVu.getMaDV())) {
										defaultTableModel.addRow(new String[] { dichVu.getMaDV() + "",
												dichVu.getTenDV(), VietnameseFormatter.dinhDangTien(dichVu.getGiaDV()),
												1 + "", VietnameseFormatter.dinhDangTien(dichVu.getGiaDV()) });
										lamMoiTien();
									} else {
										MyNotification panel = new MyNotification(chinhDeskstop,
												MyNotification.Type.WARNING, MyNotification.Location.CENTER,
												"Dịch vụ này đã trong danh sách chọn");
										panel.showNotification();

									}
								}
							}
						});
						pnChiTietDV.add(pnChild, gbc);
						pnChiTietDV.revalidate();
						pnChiTietDV.repaint();
					});
				}
			}
		}).start();
		;
	}

	private void lamMoiTien() {
		long tongTien = 0L;
		long tienKhuyenMai = 0L;
		DefaultTableModel tblmPhong = (DefaultTableModel) tblPhong.getModel();
		DefaultTableModel tblmDV = (DefaultTableModel) tblDV.getModel();

		for (int i = 0; i < tblmPhong.getRowCount(); i++) {
			tongTien += VietnameseFormatter.dinhDangTienNguoc(tblmPhong.getValueAt(i, 2).toString());
		}
		for (int i = 0; i < tblmDV.getRowCount(); i++) {
			tongTien += VietnameseFormatter.dinhDangTienNguoc(tblmDV.getValueAt(i, 4).toString());
		}
		txtTongTienTruocKhuyenMai.setText(VietnameseFormatter.dinhDangTien(tongTien));
		if (txtMaKH.getText().trim().equals("")) {
			tienKhuyenMai = tongTien;
			txtTongTienSauKhuyenMai.setText("Tổng tiền: " + VietnameseFormatter.dinhDangTien(tongTien));
		} else {
			HoiVien hoiVien = HoiVien.valueOf(txtHoiVien.getText().replaceAll(" ", "").toUpperCase());

			switch (hoiVien) {
			case ĐỒNG:
				txtTongTienSauKhuyenMai.setText("Tổng tiền: " + VietnameseFormatter
						.dinhDangTien(tienKhuyenMai = VietnameseFormatter.dinhDangSoTienLe(tongTien * 0.95)));
				break;
			case VÀNG:
				txtTongTienSauKhuyenMai.setText("Tổng tiền: " + VietnameseFormatter
						.dinhDangTien(tienKhuyenMai = VietnameseFormatter.dinhDangSoTienLe(tongTien * 0.90)));
				break;
			case BẠC:
				txtTongTienSauKhuyenMai.setText("Tổng tiền: " + VietnameseFormatter
						.dinhDangTien(tienKhuyenMai = VietnameseFormatter.dinhDangSoTienLe(tongTien * 0.85)));
				break;
			case BẠCHKIM:
				txtTongTienSauKhuyenMai.setText("Tổng tiền: " + VietnameseFormatter
						.dinhDangTien(tienKhuyenMai = VietnameseFormatter.dinhDangSoTienLe(tongTien * 0.80)));
				break;
			case KIMCƯONG:
				txtTongTienSauKhuyenMai.setText("Tổng tiền: " + VietnameseFormatter
						.dinhDangTien(tienKhuyenMai = VietnameseFormatter.dinhDangSoTienLe(tongTien * 0.75)));
				break;
			default:
				txtTongTienSauKhuyenMai.setText("Tổng tiền: " + VietnameseFormatter
						.dinhDangTien(tienKhuyenMai = VietnameseFormatter.dinhDangSoTienLe(tongTien)));
			}
		}
		txtTienThoi.setText(VietnameseFormatter
				.dinhDangTien((Long.parseLong(spnTienKhachDua.getValue().toString()) - tienKhuyenMai)));
		System.out.println("tong tien"  + tongTien);
	}

	@Override
	public void selectChange(boolean on) {
		// TODO Auto-generated method stub
		pnDatVaThanhToan.removeAll();
		pnDatVaThanhToan.setLayout(new MigLayout("", "[106px][grow]", "[:30px:40px,grow][:30px:40px,grow][][24px]"));

		if (on) {
			JLabel lblThoiHan = new JLabel("Thời hạn");
			lblThoiHan.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			pnDatVaThanhToan.add(lblThoiHan, "cell 0 0,alignx trailing");
			pnDatVaThanhToan.add(txtThoiHan, "cell 1 0,growx");
			btnDatVaThanhToan.setText("Đặt");
		} else {
			JLabel lblTienKhachDua = new JLabel("Số tiền khách đưa");
			lblTienKhachDua.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			pnDatVaThanhToan.add(lblTienKhachDua, "cell 0 0,alignx trailing");
			pnDatVaThanhToan.add(spnTienKhachDua, "cell 1 0,grow");

			JLabel lblTienThoi = new JLabel("Tiền thối");
			lblTienThoi.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			pnDatVaThanhToan.add(lblTienThoi, "cell 0 1,alignx trailing");
			pnDatVaThanhToan.add(txtTienThoi, "cell 1 1,growx");

			pnDatVaThanhToan.add(lblThoiHan, "cell 0 2,alignx trailing");
			pnDatVaThanhToan.add(txtThoiHan, "cell 1 2,growx");
			btnDatVaThanhToan.setText("Thanh toán");
		}

		pnDatVaThanhToan.revalidate();
		pnDatVaThanhToan.repaint();
	}

	class SpinnerRenderer extends DefaultTableCellRenderer {
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (value instanceof JSpinner) {
				return (JSpinner) value;
			}
			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}
	}

	// Custom editor để chỉnh sửa giá trị bằng JSpinner
	class SpinnerEditor extends AbstractCellEditor implements TableCellEditor {
		private final JSpinner spinner;

		public SpinnerEditor() {
			spinner = new JSpinner(new SpinnerNumberModel(1, 1, null, 1));
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			spinner.setValue(Integer.parseInt(value.toString())); // Set initial value
			return spinner;
		}

		@Override
		public Object getCellEditorValue() {
			return spinner.getValue(); // Return edited value
		}

		@Override
		public boolean isCellEditable(EventObject e) {
			return true; // Allow editing
		}

		public JSpinner getSpinner() {
			return spinner;
		}
	}

	private void lamMoiThongTin() {
		khoaDonDat = false;
		btnDatHayThanhToan.setEnabled(true);
		pnThanhToan.add(txtTimKiemPV, "cell 0 6 3 1,growx");
		pnThanhToan.add(txtTimKiemKH, "cell 0 2 3 1,growx");
		pnThanhToan.revalidate();
		pnThanhToan.repaint();
		txtTimKiemKH.setText("");
		txtTimKiemPV.setText("");
		txtMaKH.setText("");
		txtGioiTinh.setText("");
		txtSDT.setText("");
		txtHoiVien.setText("");
		txtHoTen.setText("");
		txtMaPV.setText("");
		txtHoTenPV.setText("");
		tblDV.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Mã dịch vụ", "Tên dịch vụ",
				"Giá dịch vụ", "Số lượng chọn", "Thành tiền", "Chức năng" }) {
			boolean[] canEdit = new boolean[] { false, false, false, true, false, true };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
				// return false;
			}
		});
		tblDV.getColumnModel().getColumn(3).setCellEditor(new SpinnerEditor());
		tblDV.getColumnModel().getColumn(5).setCellRenderer(new TableActionCellRender(true, false));
		tblDV.getColumnModel().getColumn(5).setCellEditor(new TableActionCellEditor(eventDichVu, true, false));
		SpinnerEditor spinnerEditor = (SpinnerEditor) tblDV.getColumnModel().getColumn(3).getCellEditor();
		JSpinner spinner = spinnerEditor.getSpinner();
		spinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int row = tblDV.getEditingRow();
				Long giaDV = VietnameseFormatter.dinhDangTienNguoc(tblDV.getValueAt(row, 2).toString());
				int soLuongChon = (int) spinner.getValue();
				tblDV.setValueAt(VietnameseFormatter.dinhDangTien(giaDV * soLuongChon), row, 4);
			}
		});
		tblPhong.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Mã phòng", "Tên phòng", "Giá phòng",
				"Bắt đầu", "Kết thúc", "Số giờ", "Thành tiền", "Chức năng" }) {
			boolean[] canEdit = new boolean[] { false, false, false, false, false, false, false, true };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
				// return false;
			}
		});
		tblPhong.getColumnModel().getColumn(7).setCellRenderer(new TableActionCellRender(true, false));
		tblPhong.getColumnModel().getColumn(7).setCellEditor(new TableActionCellEditor(eventPhong, true, false));
		
		spnTienKhachDua.setValue(0L);
		lamMoiTien();
	}
}
