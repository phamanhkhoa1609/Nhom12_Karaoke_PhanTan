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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import entities.DichVu;
import entities.Phong;
import entities.enums.LoaiDichVu;
import entities.enums.LoaiPhong;
import entities.enums.TrangThaiPhong;
import gui.subgui.FormManager;
import gui.subgui.RoomCardPanel;
import gui.subgui.ServiceCardPanel;
import interfaces.PhongIDAO;

import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import net.miginfocom.swing.MigLayout;
import utils.guicomponents.MyImageIcon;
import utils.guicomponents.MyPanel;
import utils.guicomponents.notification.MyNotification;
import utils.guicomponents.optionmessage.GlassPanePopup;
import utils.guicomponents.optionmessage.MyMessage;

import java.awt.BorderLayout;
import com.jidesoft.swing.AutoCompletionComboBox;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;

public class TimKiemPhongPanel extends MyPanel implements ActionListener {
	private AutoCompletionComboBox cboMaPhong, cboTenPhong;
	private JComboBox<String> cboLoaiPhong;
	private JSpinner spnGiaBD, spnGiaKT;
	private JButton btnTimKiem, btnLamMoi, btnThemDV;
	private JPanel pnChucNang, pnDanhSach, pnChiTietPhong;
	private JLabel lblMaPhong, lblTenPhong, lblLoaiPhong, lblKhoangGia, lblDanhSachDV, lblImageDV, lblGiaDV, lblSoLuong;
	private JScrollPane scrollPane;
	private PhongIDAO phongIDAO;

	public TimKiemPhongPanel() {

	};

	@Override
	public void KhoiTaoGiaoDien() {
		phongIDAO = clientIDAO.getPhongIDAO();
		setLayout(new BorderLayout(0, 0));
		setOpaque(false);
		pnChucNang = new JPanel();
		pnChucNang.setOpaque(false);
		add(pnChucNang, BorderLayout.NORTH);
		pnChucNang.setLayout(new MigLayout("", "[100px:n][grow][100px:n][grow][100px:n][grow][120px:120px]",
				"[30px:40px:40px][30px:40px:40px]"));

		lblMaPhong = new JLabel("Mã phòng:");
		lblMaPhong.setHorizontalAlignment(SwingConstants.TRAILING);
		lblMaPhong.setFont(new Font("Dialog", Font.BOLD, 12));
		pnChucNang.add(lblMaPhong, "cell 0 0,grow");

		cboMaPhong = new AutoCompletionComboBox();
		cboMaPhong.setForeground(Color.WHITE);
		cboMaPhong.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		cboMaPhong.addActionListener(this);
		pnChucNang.add(cboMaPhong, "cell 1 0,grow");

		lblTrangThai = new JLabel("Trạng thái");
		lblTrangThai.setFont(new Font("Dialog", Font.BOLD, 12));
		pnChucNang.add(lblTrangThai, "cell 4 0,alignx trailing");

		cboTrangThai = new JComboBox<String>();
		cboTrangThai.setForeground(Color.WHITE);
		cboTrangThai.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		pnChucNang.add(cboTrangThai, "cell 5 0,grow");

		lblTenPhong = new JLabel("Tên phòng:");
		lblTenPhong.setHorizontalAlignment(SwingConstants.TRAILING);
		lblTenPhong.setFont(new Font("Dialog", Font.BOLD, 12));
		pnChucNang.add(lblTenPhong, "cell 0 1,grow");

		cboTenPhong = new AutoCompletionComboBox();
		cboTenPhong.setForeground(Color.WHITE);
		cboTenPhong.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnChucNang.add(cboTenPhong, "cell 1 1,grow");

		lblLoaiPhong = new JLabel("Loại phòng:");
		lblLoaiPhong.setHorizontalAlignment(SwingConstants.TRAILING);
		lblLoaiPhong.setFont(new Font("Dialog", Font.BOLD, 12));
		pnChucNang.add(lblLoaiPhong, "cell 2 0,grow");

		cboLoaiPhong = new JComboBox<String>();
		cboLoaiPhong.setForeground(Color.WHITE);
		cboLoaiPhong.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		cboLoaiPhong.addItem("Tất cả");
		String[] dsdv = "Nước,Đồ ăn nhanh,Lẩu".split(",");
		for (String loaiDichVu : dsdv) {
			cboLoaiPhong.addItem(loaiDichVu);
		}

		pnChucNang.add(cboLoaiPhong, "cell 3 0,grow");

		lblKhoangGia = new JLabel("Giá phòng:");
		lblKhoangGia.setHorizontalAlignment(SwingConstants.TRAILING);
		lblKhoangGia.setFont(new Font("Dialog", Font.BOLD, 12));
		pnChucNang.add(lblKhoangGia, "cell 2 1,grow");

		btnTimKiem = new JButton("Tìm kiếm");
		btnTimKiem.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnTimKiem.addActionListener(this);
		pnChucNang.add(btnTimKiem, "cell 6 0,grow");

		btnLamMoi = new JButton("Làm mới");
		btnLamMoi.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnLamMoi.addActionListener(this);
		pnChucNang.add(btnLamMoi, "cell 6 1,grow");
		spnGiaBD = new JSpinner();
		spnGiaKT = new JSpinner();

		// Set the maximum value of spnGiaBD to the current value of spnGiaKT
		spnGiaBD.setModel(new SpinnerNumberModel(0L, 0L, null, 10000L));
		spnGiaKT.setModel(new SpinnerNumberModel(200000L, 0L, null, 10000L));

		spnGiaBD.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		spnGiaBD.setForeground(Color.WHITE);

		pnChucNang.add(spnGiaBD, "cell 3 1,grow");

		// Set the minimum value of spnGiaKT to the current value of spnGiaBD
		spnGiaKT.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		spnGiaKT.setForeground(Color.WHITE);
		pnChucNang.add(spnGiaKT, "cell 5 1,grow");

		JLabel lblKiTu = new JLabel("~");
		lblKiTu.setHorizontalAlignment(SwingConstants.CENTER);
		lblKiTu.setFont(new Font("Dialog", Font.BOLD, 22));
		pnChucNang.add(lblKiTu, "cell 4 1,growx,aligny center");

		lblDanhSachDV = new JLabel("Danh Sách phòng");
		lblDanhSachDV.setOpaque(true);
		lblDanhSachDV.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDanhSachDV.setHorizontalAlignment(SwingConstants.CENTER);
		lblDanhSachDV.setForeground(Color.WHITE);
		lblDanhSachDV.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblDanhSachDV.setBackground(new Color(72, 209, 204));

		pnDanhSach = new JPanel();
		pnDanhSach.setOpaque(false);
		add(pnDanhSach);
		pnDanhSach.setBorder(new TitledBorder(null, "Danh s\u00E1ch ph\u00F2ng:", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		pnDanhSach.setLayout(new BorderLayout(0, 0));

		pnChiTietPhong = new JPanel(new GridBagLayout());
		pnChiTietPhong.setOpaque(false);
		scrollPane = new JScrollPane();
		scrollPane.setOpaque(false);
		pnDanhSach.add(scrollPane);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);

		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setViewportView(pnChiTietPhong);
		scrollPane.getViewport().setOpaque(false);
		Vector<String> dsLoaiPhong = new Vector<String>();
		dsLoaiPhong.add("Tất cả");
		for (LoaiPhong loaiPhong : LoaiPhong.values()) {
			dsLoaiPhong.add(loaiPhong.getLoaiPhong());
		}
		cboLoaiPhong.setModel(new DefaultComboBoxModel<String>(dsLoaiPhong));

		Vector<String> dsTrangThaiPhong = new Vector<String>();
		dsTrangThaiPhong.add("Tất cả");
		for (TrangThaiPhong trangThaiPhong : TrangThaiPhong.values()) {
			dsTrangThaiPhong.add(trangThaiPhong.getTrangThaiPhong());
		}
		cboTrangThai.setModel(new DefaultComboBoxModel<String>(dsTrangThaiPhong));
		lamMoi();
	}

	private final int NUMBEROFCOLUMN = 5;
	private JLabel lblTrangThai;
	private JComboBox<String> cboTrangThai;

	public void hienThiDanhSachVoiDieuKien(Object... objectSearch) throws RemoteException {
		
		pnChiTietPhong.removeAll(); // Xóa các thành phần hiển thị cũ
		List<Phong> dsPhong = new Vector<>(phongIDAO.timKiemNhieuTruong(objectSearch));
		new Thread(() -> { 
		if (dsPhong.isEmpty() || dsPhong == null) {
			MyNotification panel = new MyNotification(chinhDeskstop, MyNotification.Type.INFO,
					MyNotification.Location.CENTER, "Không tìm thấy kết quả nào!");
			panel.showNotification();
		} else {
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
							FormManager.getInstance().showForm("Thông tin chi tiết Phòng",
									new ThongTinChiTietPhongPanel(phong), 500, 700);
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

	private void lamMoi() {
		new Thread(() -> {
			try {
				cboMaPhong.setModel(new DefaultComboBoxModel<String>(phongIDAO.layTatCaTheoMa()));
				cboTenPhong.setModel(new DefaultComboBoxModel<String>(phongIDAO.layTatCaTheoTen()));
				cboLoaiPhong.setSelectedIndex(0);
				cboTrangThai.setSelectedIndex(0);
				spnGiaBD.setValue(0L);
				spnGiaKT.setValue(200000L);
				hienThiDanhSachVoiDieuKien(null);
			} catch (RemoteException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}).start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnTimKiem) || o.equals(cboMaPhong)) {
				try {
					hienThiDanhSachVoiDieuKien(cboMaPhong.getSelectedItem(),
							cboTenPhong.getSelectedItem().toString().trim(), cboLoaiPhong.getSelectedItem().toString(),
							cboTrangThai.getSelectedItem().toString(), (long) spnGiaBD.getValue(),
							(long) spnGiaKT.getValue());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
		if (o.equals(btnLamMoi))
			lamMoi();
	}
}
