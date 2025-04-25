package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import entities.DichVu;
import gui.subgui.FormManager;
import interfaces.DichVuIDAO;

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
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

public class QuanLyDichVuPanel extends MyPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnLamMoi;
	private MySearchTextField txtLoc;
	private JLabel lblLoc;
	private JPanel pnChucNang;
	private MyTable tblDV;
	private JPanel pnDanhSach;
	private JScrollPane sp;
	private JButton btnThemDV;
	private DichVuIDAO dichVuIDAO;

	public QuanLyDichVuPanel() {
	}

	@Override
	public void KhoiTaoGiaoDien() {
		dichVuIDAO = clientIDAO.getDichVuIDAO();

		setOpaque(false);
		setLayout(new BorderLayout());
		pnChucNang = new JPanel();
		pnChucNang.setOpaque(false);
		btnLamMoi = new JButton("Làm mới");
		btnLamMoi.setFont(new Font("Times New Roman", Font.PLAIN, 15));

		lblLoc = new JLabel("Lọc");
		lblLoc.setFont(new Font("Times New Roman", Font.PLAIN, 15));

		txtLoc = new MySearchTextField();
		txtLoc.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtLoc.addEventOptionSelected(new SearchOptinEvent() {
			@Override
			public void optionSelected(SearchOption option, int index) {
				txtLoc.setHint("Lọc bởi " + option.getName() + "...");
			}
		});
		txtLoc.addOption(
				new SearchOption("mã dịch vụ", new MyImageIcon("src/main/resources/images/icons/id.png", 20, 20, 0)));
		txtLoc.addOption(new SearchOption("tên dịch vụ",
				new MyImageIcon("src/main/resources/images/icons/name.png", 20, 20, 0)));
		txtLoc.addOption(new SearchOption("loại dịch vụ",
				new MyImageIcon("src/main/resources/images/icons/servicetype.png", 20, 20, 0)));
		txtLoc.addOption(new SearchOption("trạng thái dịch vụ",
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

		btnThemDV = new JButton("Thêm dịch vụ");
		btnThemDV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnThemDV.addActionListener(this);
		pnChucNang.add(btnThemDV, "cell 3 0,alignx left,growy");
		tblDV = new MyTable();
		tblDV.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Hình ảnh", "Mã dịch vụ", "Tên dịch vụ",
				"Loại dịch vụ", "Trạng thái", "Giá dịch vụ", "Số lượng", "Chức năng" }) {
			boolean[] canEdit = new boolean[] { false, false, false, false, false, false, false, true };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});

		TableActionEvent event = new TableActionEvent() {

			@Override
			public void onView(int row) {
				// TODO Auto-generated method stub
				try {
					FormManager.getInstance().showForm("Thông tin chi tiết Dịch vụ",
							new ThongTinChiTietDichVuPanel(
									dichVuIDAO.layTheoMa(Long.parseLong(tblDV.getValueAt(row, 1).toString()))),
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
				Long maDV = Long.parseLong(tblDV.getValueAt(row, 1).toString());
				CapNhatDichVuPanel capNhatDichVuPanel = new CapNhatDichVuPanel(maDV);
				capNhatDichVuPanel.setClientIDAO(clientIDAO);
				capNhatDichVuPanel.setTienManHinh(getManHinhChua());
				capNhatDichVuPanel.setChinhDeskstop(chinhDeskstop);
				FormManager.getInstance().showForm("Cập nhật Dịch vụ", capNhatDichVuPanel, 720, 700);
				capNhatDichVuPanel.getManHinhChua().addInternalFrameListener(new InternalFrameAdapter() {
					@Override
					public void internalFrameClosed(InternalFrameEvent e) {
						if (capNhatDichVuPanel.getThanhCong()) {
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
		capNhatBangVoiDieuKien("");
		tblDV.getColumnModel().getColumn(0).setCellRenderer(new TableImageCellRender());
		tblDV.getColumnModel().getColumn(7).setCellRenderer(new TableActionCellRender(false));
		tblDV.getColumnModel().getColumn(7).setCellEditor(new TableActionCellEditor(event, false));

		Map<Integer, Integer> setCellAlignMap = new HashMap<Integer, Integer>();
		setCellAlignMap.put(0, SwingConstants.CENTER);
		setCellAlignMap.put(1, SwingConstants.CENTER);
		setCellAlignMap.put(2, SwingConstants.LEFT);
		setCellAlignMap.put(3, SwingConstants.LEFT);
		setCellAlignMap.put(4, SwingConstants.LEFT);
		setCellAlignMap.put(5, SwingConstants.RIGHT);
		setCellAlignMap.put(6, SwingConstants.RIGHT);
		setCellAlignMap.put(7, SwingConstants.CENTER);
		tblDV.setColumnCenterAndCellAlign(setCellAlignMap);
		btnLamMoi.addActionListener(this);

		pnDanhSach = new JPanel();
		pnDanhSach.setOpaque(false);
		pnDanhSach.setBorder(new TitledBorder(null, "Danh s\u00E1ch d\u1ECBch v\u1EE5", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		add(pnDanhSach, BorderLayout.CENTER);
		pnDanhSach.setLayout(new BorderLayout(0, 0));

		sp = new JScrollPane((Component) null, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setViewportView(tblDV);
		sp.setOpaque(false);
		pnDanhSach.add(sp, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();

		if (o.equals(btnThemDV)) {
			ThemDichVuPanel themDichVuPanel = new ThemDichVuPanel();
			themDichVuPanel.setTienManHinh(getManHinhChua());
			themDichVuPanel.setClientIDAO(clientIDAO);
			themDichVuPanel.setChinhDeskstop(chinhDeskstop);
			FormManager.getInstance().showForm("Thêm Dịch vụ",
					themDichVuPanel, 900, 700);
			themDichVuPanel.getManHinhChua().addInternalFrameListener(new InternalFrameAdapter() {

				@Override
				public void internalFrameClosed(InternalFrameEvent e) {
					// TODO Auto-generated method stub
					if (themDichVuPanel.getThanhCong()) {
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

	private void txtKeyReleased(java.awt.event.KeyEvent evt) {
		if (txtLoc.isSelected()) {
			int option = txtLoc.getSelectedIndex();
			String searchText = "%" + txtLoc.getText().trim() + "%";
			switch (option) {
			case 0:
				capNhatBangVoiDieuKien("where madv like ?", searchText);
				break;
			case 1:
				capNhatBangVoiDieuKien("where tendv like ?", searchText);
				break;
			case 2:
				capNhatBangVoiDieuKien("where loaidv like ?", searchText);
				break;
			case 3:
				capNhatBangVoiDieuKien("where trangthai like ?", searchText);
				break;
			default:
				capNhatBangVoiDieuKien("");
				break;
			}
		}
	}
	private  List<DichVu> dsDV; 
	public void capNhatBangVoiDieuKien(String where, Object... objectSearch) {
		DefaultTableModel tableModel = (DefaultTableModel) tblDV.getModel();
		tableModel.setRowCount(0);
		try {
			dsDV = dichVuIDAO.timKiemMotTruong(where, objectSearch);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (dsDV.isEmpty() || dsDV == null) {
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
				synchronized (dsDV) {
					dsDV.stream().forEach(dichVu -> {
						tableModel.addRow(new Object[] { dichVu.getHinh(60, 40, 0), dichVu.getMaDV(), dichVu.getTenDV(),
								dichVu.getLoaiDV().getLoaiDichVu(), dichVu.getTrangThai().getTrangThaiDichVu(),
								VietnameseFormatter.dinhDangTien(dichVu.getGiaDV()), dichVu.getSoLuong() });
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
		txtLoc.setText("");
		capNhatBangVoiDieuKien("");
}
}
