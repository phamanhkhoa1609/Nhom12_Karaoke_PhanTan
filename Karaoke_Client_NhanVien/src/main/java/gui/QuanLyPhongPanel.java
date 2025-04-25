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
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import entities.DichVu;
import entities.NhanVien;
import entities.Phong;
import entities.enums.ChucVu;
import gui.subgui.FormManager;
import interfaces.PhongIDAO;
import java.awt.Font;
import net.miginfocom.swing.MigLayout;
import utils.ClientIDAO;
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
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.border.EtchedBorder;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class QuanLyPhongPanel extends MyPanel implements ActionListener {
	private JButton btnLamMoi;
	private DefaultTableModel model;
	private MySearchTextField txtLoc;
	private JLabel lblLoc;
	private JComponent pnChucNang;
	private MyTable tblPhong;
	private JPanel pnDanhSach;
	private JScrollPane sp;
	private PhongIDAO phongIDAO;
	private JButton btnThemPhong;

	public QuanLyPhongPanel() {
	}

	@Override
	public void KhoiTaoGiaoDien() {
		// TODO Auto-generated method stub
		phongIDAO = clientIDAO.getPhongIDAO();
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
		txtLoc.addOption(
				new SearchOption("mã phòng", new MyImageIcon("src/main/resources/images/icons/id.png", 20, 20, 0)));
		txtLoc.addOption(
				new SearchOption("tên phòng", new MyImageIcon("src/main/resources/images/icons/name.png", 20, 20, 0)));
		txtLoc.addOption(new SearchOption("loại phòng",
				new MyImageIcon("src/main/resources/images/icons/roomtype.png", 20, 20, 0)));
		txtLoc.addOption(new SearchOption("trạng thái phòng",
				new MyImageIcon("src/main/resources/images/icons/status.png", 20, 20, 0)));
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

		btnThemPhong = new JButton("Thêm phòng");
		btnThemPhong.setFont(new Font("Dialog", Font.BOLD, 12));
		btnThemPhong.addActionListener(this);
		pnChucNang.add(btnThemPhong, "cell 3 0,alignx left,growy");
		tblPhong = new MyTable();
		tblPhong.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Hình ảnh", "Mã phòng", "Tên phòng",
				"Loại phòng", "Trạng thái", "Giá phòng", "Chức năng" }) {
			boolean[] canEdit = new boolean[] { false, false, false, false, false, false, true };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});

		TableActionEvent event = new TableActionEvent() {

			@Override
			public void onView(int row) {
				// TODO Auto-generated method stub
				try {
					FormManager.getInstance().showForm("Thông tin chi tiết Phòng",
							new ThongTinChiTietPhongPanel(
									phongIDAO.layTheoMa(Long.parseLong(tblPhong.getValueAt(row, 1).toString()))),
							500, 700);
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
				if((NhanVien) nguoiDung != null && !(((NhanVien) nguoiDung).getChucVu() ==ChucVu.QUẢNLÝ)) {
                    MyNotification notification = new MyNotification(chinhDeskstop, MyNotification.Type.WARNING, MyNotification.Location.CENTER, "Chỉ quản lý mới được cập nhật phòng");
                    notification.showNotification();
                    return;
                }
				Long maPhong = Long.parseLong(tblPhong.getValueAt(row, 1).toString());
				CapNhatPhongPanel capNhatPhongPanel = new CapNhatPhongPanel(maPhong);
				capNhatPhongPanel.setClientIDAO(clientIDAO);
				capNhatPhongPanel.setTienManHinh(getManHinhChua());
				capNhatPhongPanel.setChinhDeskstop(chinhDeskstop);
				FormManager.getInstance().showForm("Cập nhật Phòng", capNhatPhongPanel, 900, 700);
				capNhatPhongPanel.getManHinhChua().addInternalFrameListener(new InternalFrameAdapter() {
					@Override
					public void internalFrameClosed(InternalFrameEvent e) {
						if (capNhatPhongPanel.getThanhCong()) {
							MyNotification panel = new MyNotification(chinhDeskstop, MyNotification.Type.SUCCESS,
									MyNotification.Location.CENTER, "Cập nhật thành công");
							panel.showNotification();
							lamMoi();
						}
					}
				});
			}

			@Override
			public void onDelete(int row) {
				// TODO Auto-generated method stub
				System.out.println("Edited row : " + row);

			}
		};
		tblPhong.getColumnModel().getColumn(0).setCellRenderer(new TableImageCellRender());
		tblPhong.getColumnModel().getColumn(6).setCellRenderer(new TableActionCellRender(false));
		tblPhong.getColumnModel().getColumn(6).setCellEditor(new TableActionCellEditor(event, false));

		Map<Integer, Integer> setCellAlignMap = new HashMap<Integer, Integer>();
		setCellAlignMap.put(0, SwingConstants.CENTER);
		setCellAlignMap.put(1, SwingConstants.CENTER);
		setCellAlignMap.put(2, SwingConstants.LEFT);
		setCellAlignMap.put(3, SwingConstants.LEFT);
		setCellAlignMap.put(4, SwingConstants.LEFT);
		setCellAlignMap.put(5, SwingConstants.RIGHT);
		setCellAlignMap.put(6, SwingConstants.CENTER);
		tblPhong.setColumnCenterAndCellAlign(setCellAlignMap);
		btnLamMoi.addActionListener(this);

		pnDanhSach = new JPanel();
		pnDanhSach.setOpaque(false);
		pnDanhSach.setBorder(new TitledBorder(null, "Danh s\u00E1ch d\u1ECBch v\u1EE5", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		add(pnDanhSach, BorderLayout.CENTER);
		pnDanhSach.setLayout(new BorderLayout(0, 0));

		sp = new JScrollPane((Component) null, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setViewportView(tblPhong);
		sp.setOpaque(false);
		pnDanhSach.add(sp, BorderLayout.CENTER);
		lamMoi();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();

		if (o.equals(btnThemPhong)) {
			ThemPhongPanel themPhongPanel = new ThemPhongPanel();
			themPhongPanel.setTienManHinh(getManHinhChua());
			themPhongPanel.setClientIDAO(clientIDAO);
			themPhongPanel.setChinhDeskstop(chinhDeskstop);
			FormManager.getInstance().showForm("Thêm Phòng", themPhongPanel, 900, 700);
			themPhongPanel.getManHinhChua().addInternalFrameListener(new InternalFrameAdapter() {

				@Override
				public void internalFrameClosed(InternalFrameEvent e) {
					// TODO Auto-generated method stub
					if (themPhongPanel.getThanhCong()) {
						MyNotification panel = new MyNotification(chinhDeskstop, MyNotification.Type.SUCCESS,
								MyNotification.Location.CENTER, "Thêm thành công");
						panel.showNotification();
						lamMoi();
					}
				}
			});
		}
		if (o.equals(btnLamMoi)) {
			lamMoi();
		}
	}

	private void lamMoi() {
			txtLoc.setText("");
			capNhatBangVoiDieuKien("");
	}

	private void txtKeyReleased(java.awt.event.KeyEvent evt) {
		if (txtLoc.isSelected()) {
			int option = txtLoc.getSelectedIndex();
			String searchText = "%" + txtLoc.getText().trim() + "%";
				try {
					switch (option) {
					case 0:
						capNhatBangVoiDieuKien("where maphong like ?", searchText);
						break;
					case 1:
						capNhatBangVoiDieuKien("where tenphong like ?", searchText);
						break;
					case 2:
						capNhatBangVoiDieuKien("where loaiphong like ?", searchText);
						break;
					case 3:
						capNhatBangVoiDieuKien("where trangthai like ?", searchText);
						break;
					default:
						capNhatBangVoiDieuKien("");
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}

	private List<Phong> dsPhong;

	public void capNhatBangVoiDieuKien(String where, Object... objectSearch) {
		DefaultTableModel tableModel = (DefaultTableModel) tblPhong.getModel();
		tableModel.setRowCount(0);
		try {
			dsPhong = new Vector<Phong>(phongIDAO.timKiemMotTruong(where, objectSearch));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if (dsPhong.isEmpty() || dsPhong == null) {
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
				synchronized (dsPhong) {
					dsPhong.stream().forEach(phong -> {
						tableModel.addRow(
								new Object[] { phong.getHinh(40, 30, 0), phong.getMaPhong(), phong.getTenPhong(),
										phong.getLoaiPhong().getLoaiPhong(), phong.getTrangThai().getTrangThaiPhong(),
										VietnameseFormatter.dinhDangTien(phong.getGiaPhong()) });
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
}