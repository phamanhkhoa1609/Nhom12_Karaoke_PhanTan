package gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

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
import entities.KhachHang;
import entities.Phong;
import entities.enums.GioiTinh;
import entities.enums.HoiVien;
import entities.enums.TrangThaiDichVu;
import gui.subgui.FormManager;

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

import com.jidesoft.swing.AutoCompletionComboBox;

import interfaces.KhachHangIDAO;

public class TimKiemKhachHangPanel extends MyPanel implements ActionListener {
	private MyTable tblKH;
	private JPanel pnDanhSach;
	private JScrollPane sp;
	private KhachHangIDAO khachHangIDAO;
	private JButton btnTimKiem;
	private JButton btnLamMoi;
	private AutoCompletionComboBox cboSDT;
	private AutoCompletionComboBox cboMaKH;
	private AutoCompletionComboBox cboHoTen;

	public TimKiemKhachHangPanel() {

	};

	@Override
	public void KhoiTaoGiaoDien() {
		khachHangIDAO = clientIDAO.getKhachHangIDAO();
		setOpaque(false);
		setLayout(new BorderLayout());

		tblKH = new MyTable();
		tblKH.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Mã người dùng", "Họ tên", "Số điện thoại", "Giới tính", "Hội viên" }) {
			boolean[] canEdit = new boolean[] { false, false, false, false, false, false, false };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		tblKH.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				try {
					int row = tblKH.getSelectedRow();
					FormManager.getInstance().showForm("Thông tin chi tiết Khách hàng",
							new ThongTinChiTietKhachHang(
									khachHangIDAO.layTheoMa(Long.parseLong(tblKH.getValueAt(row, 0).toString()))),
							420, 550);
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		Map<Integer, Integer> setCellAlignMap = new HashMap<Integer, Integer>();
		setCellAlignMap.put(0, SwingConstants.CENTER);
		setCellAlignMap.put(1, SwingConstants.CENTER);
		setCellAlignMap.put(2, SwingConstants.CENTER);
		setCellAlignMap.put(3, SwingConstants.CENTER);
		setCellAlignMap.put(4, SwingConstants.CENTER);
		setCellAlignMap.put(5, SwingConstants.RIGHT);
		tblKH.setColumnCenterAndCellAlign(setCellAlignMap);

		pnDanhSach = new JPanel();
		pnDanhSach.setOpaque(false);
		pnDanhSach.setBorder(
				new TitledBorder(null, "Danh sách khách hàng", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(pnDanhSach, BorderLayout.CENTER);
		pnDanhSach.setLayout(new BorderLayout(0, 0));

		sp = new JScrollPane((Component) null, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setViewportView(tblKH);
		sp.setOpaque(false);
		pnDanhSach.add(sp, BorderLayout.CENTER);

		pnChucNang = new JPanel();
		pnChucNang.setOpaque(false);
		add(pnChucNang, BorderLayout.NORTH);
		pnChucNang.setLayout(
				new MigLayout("", "[][grow,fill][][grow,fill][][grow][120]", "[30px:40px:40px][30px:40px:40px]"));

		JLabel lblMaKH = new JLabel("Mã khách hàng");
		lblMaKH.setFont(new Font("Dialog", Font.BOLD, 12));
		pnChucNang.add(lblMaKH, "cell 0 0,grow");

		cboMaKH = new AutoCompletionComboBox();
		cboMaKH.setForeground(Color.WHITE);
		cboMaKH.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		cboMaKH.addActionListener(this);
		pnChucNang.add(cboMaKH, "cell 1 0,grow");

		rbTatCa = new JRadioButton("Tất cả");
		rbTatCa.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		rbTatCa.setForeground(Color.WHITE);
		pnChucNang.add(rbTatCa, "flowx,cell 3 0,growy");

		lblHoiVien = new JLabel("Hội viên");
		lblHoiVien.setFont(new Font("Dialog", Font.BOLD, 12));
		pnChucNang.add(lblHoiVien, "cell 4 0,alignx trailing,aligny center");
		Vector<String> dsHoiVien = new Vector<String>();
		dsHoiVien.add("Tất cả");
		for (HoiVien hoiVien : HoiVien.values()) {
			dsHoiVien.add(hoiVien.getHoiVien());
		}
		cboHoiVien = new JComboBox(dsHoiVien);
		pnChucNang.add(cboHoiVien, "cell 5 0,grow");

		JLabel lblHoTenKH = new JLabel("Họ tên khách hàng");
		lblHoTenKH.setFont(new Font("Dialog", Font.BOLD, 12));
		pnChucNang.add(lblHoTenKH, "cell 0 1,grow");

		cboHoTen = new AutoCompletionComboBox();
		cboHoTen.setForeground(Color.WHITE);
		cboHoTen.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnChucNang.add(cboHoTen, "cell 1 1,grow");

		JLabel lblGioiTinh = new JLabel("Giới tính");
		lblGioiTinh.setFont(new Font("Dialog", Font.BOLD, 12));
		pnChucNang.add(lblGioiTinh, "cell 2 0,grow");

		JLabel lblSDT = new JLabel("Số điện thoại");
		lblSDT.setFont(new Font("Dialog", Font.BOLD, 12));
		pnChucNang.add(lblSDT, "cell 2 1,grow");

		btnTimKiem = new JButton("Tìm kiếm");
		btnTimKiem.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnTimKiem.addActionListener(this);
		pnChucNang.add(btnTimKiem, "cell 6 0,grow");

		btnLamMoi = new JButton("Làm mới");
		btnLamMoi.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnLamMoi.addActionListener(this);
		pnChucNang.add(btnLamMoi, "cell 6 1,grow");

		cboSDT = new AutoCompletionComboBox();
		cboSDT.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		cboSDT.setForeground(Color.WHITE);

		pnChucNang.add(cboSDT, "cell 3 1 3 1,grow");

		rbNam = new JRadioButton("Nam");
		rbNam.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		rbNam.setForeground(Color.WHITE);
		pnChucNang.add(rbNam, "cell 3 0,growy");

		rbNu = new JRadioButton("Nữ");
		rbNu.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		rbNu.setForeground(Color.WHITE);
		ButtonGroup bgGioiTinh = new ButtonGroup();
		bgGioiTinh.add(rbTatCa);
		bgGioiTinh.add(rbNam);
		bgGioiTinh.add(rbNu);
		pnChucNang.add(rbNu, "cell 3 0,growy");
		lamMoi();
	}

	private JPanel pnChucNang;
	private JRadioButton rbTatCa;
	private JRadioButton rbNam;
	private JRadioButton rbNu;
	private JLabel lblHoiVien;
	private JComboBox cboHoiVien;
	private String gioiTinh;

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnLamMoi)) {
			lamMoi();
		} else if (o.equals(btnTimKiem) || o.equals(cboMaKH) || o.equals(cboSDT)) {
			if (rbTatCa.isSelected()) {
				gioiTinh = "Tất cả";
			} else if (rbNam.isSelected()) {
				gioiTinh = "Nam";
			} else if (rbNu.isSelected()) {
				gioiTinh = "Nữ";
			}
			try {
				capNhatBangVoiDieuKien(cboMaKH.getSelectedItem(), cboHoTen.getSelectedItem().toString().trim(),
						gioiTinh, cboSDT.getSelectedItem().toString(), cboHoiVien.getSelectedItem().toString());
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	private List<KhachHang> dsKH;
	public void capNhatBangVoiDieuKien(Object... objectSearch) {
		DefaultTableModel tableModel = (DefaultTableModel) tblKH.getModel();
		tableModel.setRowCount(0);
		
		try {
			dsKH = new Vector<KhachHang>(khachHangIDAO.timKiemNhieuTruong(objectSearch));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (dsKH.isEmpty() || dsKH == null) {
			MyNotification panel = new MyNotification(chinhDeskstop, MyNotification.Type.INFO,
					MyNotification.Location.CENTER, "Không tìm thấy kết quả nào!");
			panel.showNotification();
		} else {
			btnLamMoi.setEnabled(false);
		}
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				synchronized (dsKH) {
					dsKH.parallelStream().forEach(khachHang -> {
						tableModel.addRow(new Object[] { khachHang.getMaND(), khachHang.getHoTen(), khachHang.getSdt(),
								khachHang.getGioiTinh().getGioiTinh(), khachHang.getHoiVien().getHoiVien(),
						});
					});
				};
				return null;
			}

	@Override
	protected void done() {
		btnLamMoi.setEnabled(true);
	}

	};

	worker.execute();}

	private void lamMoi() {
		new Thread(() -> {
			try {
				cboMaKH.setModel(new DefaultComboBoxModel<String>(khachHangIDAO.layTatCaTheoMa()));
				cboHoTen.setModel(new DefaultComboBoxModel<String>(khachHangIDAO.layTatCaTheoTen()));
				cboHoiVien.setSelectedIndex(0);
				rbTatCa.setSelected(true);
				cboSDT.setModel(new DefaultComboBoxModel<String>(khachHangIDAO.layTatCaTheoSDT()));
				capNhatBangVoiDieuKien(null);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}).start();
	}
}
