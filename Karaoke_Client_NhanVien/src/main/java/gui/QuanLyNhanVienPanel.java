package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
import entities.KhachHang;
import entities.NhanVien;
import entities.Phong;
import gui.subgui.FormManager;
import interfaces.NhanVienIDAO;

import java.awt.Font;
import net.miginfocom.swing.MigLayout;
import utils.VietnameseFormatter;
import utils.guicomponents.MyImageIcon;
import utils.guicomponents.MyPanel;
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

import interfaces.NhanVienIDAO;

public class QuanLyNhanVienPanel extends MyPanel implements ActionListener {
	private JButton btnLamMoi;
	private MySearchTextField txtLoc;
	private JLabel lblLoc;
	private JComponent pnChucNang;
	private MyTable tblNV;
	private JPanel pnDanhSach;
	private JScrollPane sp;
	private NhanVienIDAO nhanVienIDAO;

	public QuanLyNhanVienPanel() {
	}

	@Override
	public void KhoiTaoGiaoDien() {
		nhanVienIDAO = clientIDAO.getNhanVienIDAO();
		setOpaque(false);
		setLayout(new BorderLayout());
		pnChucNang = new JPanel();
		pnChucNang.setOpaque(false);
		btnLamMoi = new JButton("Làm mới");
		btnLamMoi.setFont(new Font("Dialog", Font.BOLD, 12));

		lblLoc = new JLabel("Lọc");
		lblLoc.setFont(new Font("Times New Roman", Font.PLAIN, 15));

		txtLoc = new MySearchTextField();
		txtLoc.addEventOptionSelected(new SearchOptinEvent() {
			@Override
			public void optionSelected(SearchOption option, int index) {
				txtLoc.setHint("Lọc bởi " + option.getName() + "...");
			}
		});
		txtLoc.addOption(new SearchOption("mã người dùng",
				new MyImageIcon("src/main/resources/images/icons/id.png", 20, 20, 0)));
		txtLoc.addOption(
				new SearchOption("họ tên", new MyImageIcon("src/main/resources/images/icons/name.png", 20, 20, 0)));
		txtLoc.addOption(new SearchOption("số điện thoại",
				new MyImageIcon("src/main/resources/images/icons/tel.png", 20, 20, 0)));
		txtLoc.addOption(new SearchOption("giới tính",
				new MyImageIcon("src/main/resources/images/icons/gender.png", 20, 20, 0)));
		txtLoc.setColumns(10);

		txtLoc.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				txtKeyReleased(evt);
			}
		});

		pnChucNang.setLayout(new MigLayout("", "[24px][340px,grow][120px][]", "[25px]"));
		pnChucNang.add(lblLoc, "cell 0 0,alignx left,aligny center");
		pnChucNang.add(txtLoc, "cell 1 0,growx,aligny center");
		pnChucNang.add(btnLamMoi, "cell 2 0,grow");
		add(pnChucNang, BorderLayout.NORTH);

		tblNV = new MyTable();
		tblNV.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Hình ảnh", "Mã người dùng", "Họ tên nhân viên", "Căn cước công dân", "Số điện thoại",
						"Chức vụ", "Trạng thái", "Giới tính", "Ngày sinh", "Địa chỉ", "Chức năng" }) {
			boolean[] canEdit = new boolean[] { false, false, false, false, false, false, false, false, false, false,
					true };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});

		TableActionEvent event = new TableActionEvent() {

			@Override
			public void onView(int row) {
				// TODO Auto-generated method stub
				try {
					FormManager.getInstance().showForm("Thông tin chi tiết Nhân viên",
							new ThongTinChiTietNhanVienPanel(
									nhanVienIDAO.layTheoMa(Long.parseLong(tblNV.getValueAt(row, 1).toString()))),
							650, 700);
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
				Long maNV = Long.parseLong(tblNV.getValueAt(row, 1).toString());
				CapNhatNhanVienPanel capNhatNhanVienPanel = new CapNhatNhanVienPanel(maNV);
				capNhatNhanVienPanel.setClientIDAO(clientIDAO);
				capNhatNhanVienPanel.setTienManHinh(tienManHinh);
				capNhatNhanVienPanel.setChinhDeskstop(chinhDeskstop);
				FormManager.getInstance().showForm("Cập nhật Nhân viên", capNhatNhanVienPanel, 950, 700);
			}

			@Override
			public void onDelete(int row) {
				// TODO Auto-generated method stub
				System.out.println("Edited row : " + row);

			}
		};

		capNhatBangVoiDieuKien("");
		tblNV.getColumnModel().getColumn(0).setCellRenderer(new TableImageCellRender());
		tblNV.getColumnModel().getColumn(10).setCellRenderer(new TableActionCellRender(false));
		tblNV.getColumnModel().getColumn(10).setCellEditor(new TableActionCellEditor(event, false));

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
		btnLamMoi.addActionListener(this);

		pnDanhSach = new JPanel();
		pnDanhSach.setOpaque(false);
		pnDanhSach.setBorder(
				new TitledBorder(null, "Danh sách nhân viên", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(pnDanhSach, BorderLayout.CENTER);
		pnDanhSach.setLayout(new BorderLayout(0, 0));

		sp = new JScrollPane((Component) null, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setViewportView(tblNV);
		sp.setOpaque(false);
		pnDanhSach.add(sp, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnLamMoi)) {
			lamMoi();
		}
	}

	private void txtKeyReleased(java.awt.event.KeyEvent evt) {
		if (txtLoc.isSelected()) {
			int option = txtLoc.getSelectedIndex();
			String searchText = "%" + txtLoc.getText().trim() + "%";
			switch (option) {
			case 0:
				capNhatBangVoiDieuKien("where mand like ?", searchText);
				break;
			case 1:
				capNhatBangVoiDieuKien("where hoten like ?", searchText);
				break;
			case 2:
				capNhatBangVoiDieuKien("where sdt like ?", searchText);
				break;
			case 3:
				capNhatBangVoiDieuKien("where gioitinh like ?", searchText);
				break;
			case 4:
				capNhatBangVoiDieuKien("where hoivien like ?", searchText);
				break;
			default:
				capNhatBangVoiDieuKien("");
				break;
			}
		}
	}
	private List<NhanVien> dsNV;
	public void capNhatBangVoiDieuKien(String where, Object... objectSearch) {
			DefaultTableModel tableModel = (DefaultTableModel) tblNV.getModel();
			tableModel.setRowCount(0);
			
			try {
				dsNV = new Vector<NhanVien>(nhanVienIDAO.timKiemMotTruong(where, objectSearch));
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
				txtLoc.setFocusable(false);
			}
			
			SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
				@Override
				protected Void doInBackground() throws Exception {
					synchronized (dsNV) {
						dsNV.parallelStream().forEach(nhanVien -> {
							tableModel.addRow(new Object[] { nhanVien.getHinh(30, 40, 0), nhanVien.getMaND(),
									nhanVien.getHoTen(), nhanVien.getCccd(), nhanVien.getSdt(),
									nhanVien.getChucVu().getChucVu(), nhanVien.getTrangThai().getTrangThaiNhanVien(),
									nhanVien.getGioiTinh().getGioiTinh(),
									VietnameseFormatter.dinhDangNgayString(nhanVien.getNgaySinh()), nhanVien.getDiaChi() });
						});
					}
					return null;
				}

				@Override
				protected void done() {
					btnLamMoi.setEnabled(true);
					txtLoc.setEditable(true);
					txtLoc.setFocusable(true);
					txtLoc.requestFocus();
				}
			};

			worker.execute();
	}

	private void lamMoi() {
		// TODO Auto-generated method stub
		txtLoc.setText("");
		capNhatBangVoiDieuKien("");
	}
}
