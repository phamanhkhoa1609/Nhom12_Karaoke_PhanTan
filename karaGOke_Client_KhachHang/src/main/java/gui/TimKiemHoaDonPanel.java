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
import gui.subgui.UserCardPanel;
import entities.NguoiDung;

public class TimKiemHoaDonPanel extends MyPanel implements ActionListener {
	private MyTable tblDD;
	private JPanel pnDanhSach;
	private JScrollPane sp;
	private HoaDonIDAO hoaDonIDAO;

	public TimKiemHoaDonPanel() {

	};

	@Override
	public void KhoiTaoGiaoDien() {
		hoaDonIDAO = clientIDAO.getHoaDonIDAO();
		setOpaque(false);
		setLayout(new BorderLayout());

		tblDD = new MyTable();
		tblDD.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Mã hóa đơn",
						"Thời gian lập", "Thời hạn", "Thời gian thanh toán", "Mã tiếp tân",
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
					MyNotification jPanel2 = new MyNotification(chinhFrame, MyNotification.Type.WARNING,
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
		pnChucNang.setLayout(new MigLayout("", "[120,grow]", "[30px:40px:40px,grow]"));

		btnLamMoi = new JButton("Làm mới");
		btnLamMoi.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnChucNang.add(btnLamMoi, "cell 0 0,grow");
		btnLamMoi.addActionListener(this);
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
	private JButton btnLamMoi;
	private List<HoaDon> dsHoaDon;

	public void hienThiDanhSachDonDat()
			throws RemoteException {
		DefaultTableModel defaultTableModel = (DefaultTableModel) tblDD.getModel();
		defaultTableModel.setRowCount(0);
		dsHoaDon = new Vector<HoaDon>(hoaDonIDAO.layTheoMaKH(nguoiDung.getMaND()));
		if (!dsHoaDon.isEmpty() && dsHoaDon != null) {
			btnLamMoi.setEnabled(false);
		}
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() {
				try {
					synchronized (dsHoaDon) {
						dsHoaDon.stream().forEach(hoaDon -> {
							String maHD = hoaDon.getMaHD() + "";
							String hoTenTiepTan = hoaDon.getTiepTan() != null ? hoaDon.getTiepTan().getHoTen()
									: "Không có";
							String thoiGianLap = VietnameseFormatter
									.dinhDangNgayThoiGianString(hoaDon.getThoiGianLap());
							String thoiHan = VietnameseFormatter.dinhDangNgayThoiGianString(hoaDon.getThoiHan());
							String maNDTiepTan = hoaDon.getTiepTan() != null ? hoaDon.getTiepTan().getMaND() + ""
									: "Không có";
							String trangThaiHoaDon = hoaDon.getTrangThai() != null
									? hoaDon.getTrangThai().getTrangThaiHoaDon()
									: "Không có";
							String thoiGianThanhToan = hoaDon.getThoiGianThanhToan() != null
									? VietnameseFormatter.dinhDangNgayThoiGianString(hoaDon.getThoiGianThanhToan())
									: thoiGianLap;
                            defaultTableModel.addRow(new Object[] { maHD, thoiGianLap, thoiHan, thoiGianThanhToan, maNDTiepTan, trangThaiHoaDon });
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
			}
		};

		worker.execute();
	}

	private void lamMoi() {
		new Thread(() -> {
			try {
				hienThiDanhSachDonDat();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}).start();
	}
}
