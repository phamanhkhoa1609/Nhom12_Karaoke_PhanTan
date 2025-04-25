package gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import entities.DichVu;
import entities.HoaDon;
import entities.KhachHang;
import entities.Phong;
import entities.enums.GioiTinh;
import entities.enums.HoiVien;
import entities.enums.TrangThaiDichVu;
import gui.subgui.FormManager;
import gui.subgui.HoaDonPDF;

import java.awt.Font;
import net.miginfocom.swing.MigLayout;
import utils.VietnameseFormatter;
import utils.guicomponents.MyImageIcon;
import utils.guicomponents.MyPanel;
import utils.guicomponents.daterangechooser.DateBetween;
import utils.guicomponents.daterangechooser.DateRangeChooser;
import utils.guicomponents.daterangechooser.listener.DateChooserAction;
import utils.guicomponents.daterangechooser.listener.DateChooserAdapter;
import utils.guicomponents.notification.MyNotification;
import utils.guicomponents.searchoptiontextfield.MySearchTextField;
import utils.guicomponents.searchoptiontextfield.SearchOptinEvent;
import utils.guicomponents.searchoptiontextfield.SearchOption;
import utils.guicomponents.table.MyTable;
import utils.guicomponents.table.TableActionCellEditor;
import utils.guicomponents.table.TableActionCellRender;
import utils.guicomponents.table.TableActionEvent;
import utils.guicomponents.table.TableImageCellRender;

import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.jidesoft.swing.AutoCompletionComboBox;

import interfaces.DichVuIDAO;
import interfaces.HoaDonIDAO;
import interfaces.KhachHangIDAO;
import interfaces.NhanVienIDAO;
import interfaces.PhongIDAO;
import interfaces.TaiKhoanIDAO;

public class TimKiemHoaDonPanel extends MyPanel implements ActionListener {
	private MyTable tblDD;
	private JPanel pnDanhSach;
	private JScrollPane sp;
	private KhachHangIDAO khachHangIDAO;
	private AutoCompletionComboBox cboMaKH;
	private MySearchTextField txtTimKiemDD;
	private DichVuIDAO dichVuIDAO;
	private PhongIDAO phongIDAO;
	private NhanVienIDAO nhanVienIDAO;
	private TaiKhoanIDAO taiKhoanIDAO;
	private HoaDonIDAO hoaDonIDAO;
	private DateRangeChooser dateRangeChooser;

	public TimKiemHoaDonPanel() {

	};

	@Override
	public void KhoiTaoGiaoDien() {
		dichVuIDAO = clientIDAO.getDichVuIDAO();
		phongIDAO = clientIDAO.getPhongIDAO();
		nhanVienIDAO = clientIDAO.getNhanVienIDAO();
		khachHangIDAO = clientIDAO.getKhachHangIDAO();
		taiKhoanIDAO = clientIDAO.getTaiKhoanIDAO();
		hoaDonIDAO = clientIDAO.getHoaDonIDAO();
		setOpaque(false);
		setLayout(new BorderLayout());

		tblDD = new MyTable();
		tblDD.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Mã hóa đơn", "Họ tên khách hàng", "Số điện thoại khách", "Họ tên tiếp tân",
						"Thời gian lập", "Thời hạn", "Thời gian thanh toán", "Mã khách hàng", "Mã tiếp tân",
						"Trạng thái" }) {
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false;
			}
		});
		tblDD.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				try {
					HoaDon hoaDon = hoaDonIDAO
							.layTheoMa(Long.parseLong(tblDD.getValueAt(tblDD.getSelectedRow(), 0).toString()));
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
			}
		});
		Map<Integer, Integer> setCellAlignMap = new HashMap<Integer, Integer>();
		setCellAlignMap.put(0, SwingConstants.CENTER);
		setCellAlignMap.put(1, SwingConstants.LEFT);
		setCellAlignMap.put(2, SwingConstants.RIGHT);
		setCellAlignMap.put(3, SwingConstants.LEFT);
		setCellAlignMap.put(4, SwingConstants.RIGHT);
		setCellAlignMap.put(5, SwingConstants.RIGHT);
		setCellAlignMap.put(6, SwingConstants.RIGHT);
		setCellAlignMap.put(7, SwingConstants.CENTER);
		setCellAlignMap.put(8, SwingConstants.CENTER);
		setCellAlignMap.put(9, SwingConstants.LEFT);
		tblDD.setColumnCenterAndCellAlign(setCellAlignMap);

		pnDanhSach = new JPanel();
		pnDanhSach.setOpaque(false);
		pnDanhSach.setBorder(new TitledBorder(null, "Danh sách hóa đơn: Hãy ấn vào để tạo file PDF",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(pnDanhSach, BorderLayout.CENTER);
		pnDanhSach.setLayout(new BorderLayout(0, 0));

		sp = new JScrollPane((Component) null, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setViewportView(tblDD);
		sp.setOpaque(false);
		pnDanhSach.add(sp, BorderLayout.CENTER);

		pnChucNang = new JPanel();
		pnChucNang.setOpaque(false);
		add(pnChucNang, BorderLayout.NORTH);
		pnChucNang.setLayout(new MigLayout("", "[grow][grow][120]", "[30px:40px:40px]"));

		Vector<String> dsHoiVien = new Vector<String>();
		dsHoiVien.add("Tất cả");
		for (HoiVien hoiVien : HoiVien.values()) {
			dsHoiVien.add(hoiVien.getHoiVien());
		}

		txtChonNgay = new JTextField();
		txtChonNgay.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		dateRangeChooser = new DateRangeChooser();
		dateRangeChooser.setTextField(txtChonNgay);
		dateRangeChooser.setDateSelectionMode(DateRangeChooser.DateSelectionMode.BETWEEN_DATE_SELECTED);
		pnChucNang.add(txtChonNgay, "cell 0 0,grow");

		txtTimKiemDD = new MySearchTextField();
		txtTimKiemDD.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnChucNang.add(txtTimKiemDD, "cell 1 0,grow");
		txtTimKiemDD.addEventOptionSelected(new SearchOptinEvent() {
			@Override
			public void optionSelected(SearchOption option, int index) {
				txtTimKiemDD.setHint("Tìm kiếm đơn đặt bởi " + option.getName() + "...");
			}
		});
		txtTimKiemDD.addOption(
				new SearchOption("mã hóa đơn", new MyImageIcon("src/main/resources/images/icons/id.png", 20, 20, 0)));
		txtTimKiemDD.addOption(new SearchOption("mã khách hàng",
				new MyImageIcon("src/main/resources/images/icons/customerid.png", 20, 20, 0)));
		txtTimKiemDD.addOption(new SearchOption("họ tên khách hàng",
				new MyImageIcon("src/main/resources/images/icons/customername.png", 20, 20, 0)));
		txtTimKiemDD.addOption(new SearchOption("số điện thoại khách hàng",
				new MyImageIcon("src/main/resources/images/icons/tel.png", 20, 20, 0)));
		txtTimKiemDD.addOption(new SearchOption("mã nhân viên phụ vụ",
				new MyImageIcon("src/main/resources/images/icons/employeeid.png", 20, 20, 0)));
		txtTimKiemDD.addOption(new SearchOption("họ tên nhân viên phụ vụ",
				new MyImageIcon("src/main/resources/images/icons/employeename.png", 20, 20, 0)));
		txtTimKiemDD.setColumns(10);
		txtTimKiemDD.setSelectedIndex(0);

		btnLamMoi = new JButton("Làm mới");
		btnLamMoi.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnChucNang.add(btnLamMoi, "cell 2 0,grow");
		btnLamMoi.addActionListener(this);
		txtTimKiemDD.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				if (txtTimKiemDD.isSelected()) {
					int option = txtTimKiemDD.getSelectedIndex();
					String searchText = txtTimKiemDD.getText().trim();
					String searchTextLike = "%" + searchText + "%";

					try {
						if (searchText.trim().equals("")) {
							lamMoi();
						} else {
							switch (option) {
							case 0:
								hienThiDanhSachDonDatVoiDieuKien("where mahd like ? and "
										+ VietnameseFormatter
												.dinhDangNgayStringYMD_CSDL(dateRangeChooser.getSelectedDateBetween())
										+ " order BY thoigianlap desc", "", searchTextLike);
								break;
							case 1:
								hienThiDanhSachDonDatVoiDieuKien("where makh like ? and "
										+ VietnameseFormatter
												.dinhDangNgayStringYMD_CSDL(dateRangeChooser.getSelectedDateBetween())
										+ " order BY thoigianlap desc", "", searchTextLike);
								break;
							case 2:
								hienThiDanhSachDonDatVoiDieuKien(" where "
										+ VietnameseFormatter
												.dinhDangNgayStringYMD_CSDL(dateRangeChooser.getSelectedDateBetween())
										+ " order BY thoigianlap desc", "khachhang:hoten:" + searchText);
								break;
							case 3:
								hienThiDanhSachDonDatVoiDieuKien(" where "
										+ VietnameseFormatter
												.dinhDangNgayStringYMD_CSDL(dateRangeChooser.getSelectedDateBetween())
										+ " order BY thoigianlap desc", "khachhang:sdt:" + searchText);
								break;
							case 4:
								hienThiDanhSachDonDatVoiDieuKien("where "
										+ VietnameseFormatter
												.dinhDangNgayStringYMD_CSDL(dateRangeChooser.getSelectedDateBetween())
										+ " order BY thoigianlap desc", "tieptan:manvtt:", searchText);
								break;
							case 5:
								hienThiDanhSachDonDatVoiDieuKien(" where "
										+ VietnameseFormatter
												.dinhDangNgayStringYMD_CSDL(dateRangeChooser.getSelectedDateBetween())
										+ " order BY thoigianlap desc", "tieptan:hoten:" + searchText);
								break;
							default:
								hienThiDanhSachDonDatVoiDieuKien(" where "
										+ VietnameseFormatter
												.dinhDangNgayStringYMD_CSDL(dateRangeChooser.getSelectedDateBetween())
										+ " order BY thoigianlap desc", "");
								break;
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});

		dateRangeChooser = new DateRangeChooser();
		dateRangeChooser.setDateSelectionMode(DateRangeChooser.DateSelectionMode.BETWEEN_DATE_SELECTED);

		lamMoi();
	}

	private JPanel pnChucNang;
	private String gioiTinh;

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnLamMoi)) {
			lamMoi();
		}
	}

	private List<KhachHang> dsKH;
	private JTextField txtChonNgay;
	private JButton btnLamMoi;
	private List<HoaDon> dsHoaDon;

	public void hienThiDanhSachDonDatVoiDieuKien(String where, String whereForeign, Object... objectSearch)
			throws RemoteException {
		DefaultTableModel defaultTableModel = (DefaultTableModel) tblDD.getModel();
		defaultTableModel.setRowCount(0);
		dsHoaDon = new Vector<HoaDon>(hoaDonIDAO.timKiemMotTruong(where, objectSearch));
		if (!dsHoaDon.isEmpty() && dsHoaDon != null) {
			btnLamMoi.setEnabled(false);
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
										System.out.println(object[0] + " " + object[1] + " " + object[2]);
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
							String trangThaiHoaDon = hoaDon.getTrangThai() != null
									? hoaDon.getTrangThai().getTrangThaiHoaDon()
									: "Không có";
							String thoiGianThanhToan = hoaDon.getThoiGianThanhToan() != null
									? VietnameseFormatter.dinhDangNgayThoiGianString(hoaDon.getThoiGianThanhToan())
									: thoiGianLap;

							defaultTableModel.addRow(
									new String[] { maHD, hoTenKhachHang, sdtKhachHang, hoTenTiepTan, thoiGianLap,
											thoiHan, thoiGianThanhToan, maNDKhachHang, maNDTiepTan, trangThaiHoaDon });
						});
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;

			}

			@Override
			protected void done() {
				btnLamMoi.setEnabled(true);
				txtTimKiemDD.setEditable(true);
				txtTimKiemDD.setFocusable(true);
				txtTimKiemDD.requestFocus();
			}
		};

		worker.execute();
	}

	private void lamMoi() {
		new Thread(() -> {
			try {
				txtTimKiemDD.setText("");
				dateRangeChooser.setSelectedDate(new Date());
				hienThiDanhSachDonDatVoiDieuKien(" where "
						+ VietnameseFormatter.dinhDangNgayStringYMD_CSDL(dateRangeChooser.getSelectedDateBetween())
						+ " order BY thoigianlap desc", "");
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}).start();
	}
}
