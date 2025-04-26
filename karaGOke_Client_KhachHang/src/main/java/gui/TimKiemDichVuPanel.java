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
import entities.enums.LoaiDichVu;
import entities.enums.TrangThaiDichVu;
import gui.subgui.FormManager;
import gui.subgui.RoomCardPanel;
import gui.subgui.ServiceCardPanel;

import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import net.miginfocom.swing.MigLayout;
import raven.alerts.MessageAlerts;
import utils.guicomponents.MyImageIcon;
import utils.guicomponents.MyPanel;
import utils.guicomponents.notification.MyNotification;
import utils.guicomponents.optionmessage.GlassPanePopup;
import utils.guicomponents.optionmessage.MyMessage;

import java.awt.BorderLayout;
import com.jidesoft.swing.AutoCompletionComboBox;
import interfaces.DichVuIDAO;

import javax.swing.SpinnerNumberModel;
import javax.swing.border.EtchedBorder;

public class TimKiemDichVuPanel extends MyPanel implements ActionListener {
	private AutoCompletionComboBox cboMaDV, cboTenDV;
	private JComboBox<String> cboLoaiDV;
	private JSpinner spnGiaBD, spnGiaKT;
	private JButton btnTimKiem, btnLamMoi, btnThemDV;
	private JPanel pnChucNang, pnDanhSach, pnChiTietDV;
	private JLabel lblMaDV, lblTenDV, lblLoaiDV, lblKhoangGia, lblDanhSachDV, lblImageDV, lblGiaDV, lblSoLuong;
	private JScrollPane scrollPane;
	private DichVuIDAO dichVuIDAO;

	public TimKiemDichVuPanel() {}
	@Override
	public void KhoiTaoGiaoDien() {
		dichVuIDAO = clientIDAO.getDichVuIDAO();
		setLayout(new BorderLayout(0, 0));
		setOpaque(false);
		pnChucNang = new JPanel();
		pnChucNang.setOpaque(false);
		add(pnChucNang, BorderLayout.NORTH);
		pnChucNang.setLayout(new MigLayout("", "[100px:n][grow][100px:n][grow][100px:n][grow][120px:120px]",
				"[30px:40px:40px][30px:40px:40px]"));

		lblMaDV = new JLabel("Mã dịch vụ:");
		lblMaDV.setHorizontalAlignment(SwingConstants.TRAILING);
		lblMaDV.setFont(new Font("Dialog", Font.BOLD, 12));
		pnChucNang.add(lblMaDV, "cell 0 0,grow");

		cboMaDV = new AutoCompletionComboBox();
		cboMaDV.setForeground(Color.WHITE);
		cboMaDV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnChucNang.add(cboMaDV, "cell 1 0,grow");
		cboMaDV.addActionListener(this);

		lblTrangThai = new JLabel("Trạng thái");
		lblTrangThai.setFont(new Font("Dialog", Font.BOLD, 12));
		pnChucNang.add(lblTrangThai, "cell 4 0,alignx trailing");

		cboTrangThai = new JComboBox<String>();
		cboTrangThai.setForeground(Color.WHITE);
		cboTrangThai.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnChucNang.add(cboTrangThai, "cell 5 0,grow");

		lblTenDV = new JLabel("Tên dịch vụ");
		lblTenDV.setHorizontalAlignment(SwingConstants.TRAILING);
		lblTenDV.setFont(new Font("Dialog", Font.BOLD, 12));
		pnChucNang.add(lblTenDV, "cell 0 1,grow");

		cboTenDV = new AutoCompletionComboBox();
		cboTenDV.setForeground(Color.WHITE);
		cboTenDV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnChucNang.add(cboTenDV, "cell 1 1,grow");

		lblLoaiDV = new JLabel("Loại dịch vụ:");
		lblLoaiDV.setHorizontalAlignment(SwingConstants.TRAILING);
		lblLoaiDV.setFont(new Font("Dialog", Font.BOLD, 12));
		pnChucNang.add(lblLoaiDV, "cell 2 0,grow");

		cboLoaiDV = new JComboBox<String>();
		cboLoaiDV.setForeground(Color.WHITE);
		cboLoaiDV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		cboLoaiDV.addItem("Tất cả");

		pnChucNang.add(cboLoaiDV, "cell 3 0,grow");

		lblKhoangGia = new JLabel("Giá dịch vụ:");
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

		lblDanhSachDV = new JLabel("Danh Sách Dịch Vụ");
		lblDanhSachDV.setOpaque(true);
		lblDanhSachDV.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDanhSachDV.setHorizontalAlignment(SwingConstants.CENTER);
		lblDanhSachDV.setForeground(Color.WHITE);
		lblDanhSachDV.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblDanhSachDV.setBackground(new Color(72, 209, 204));

		pnDanhSach = new JPanel();
		pnDanhSach.setOpaque(false);
		add(pnDanhSach);
		pnDanhSach.setBorder(
				new TitledBorder(null, "Danh sách dịch vụ:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnDanhSach.setLayout(new BorderLayout(0, 0));

		pnChiTietDV = new JPanel(new GridBagLayout());
		pnChiTietDV.setOpaque(false);
		scrollPane = new JScrollPane();
		scrollPane.setOpaque(false);
		scrollPane.add(pnChiTietDV);
		pnDanhSach.add(scrollPane);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);

		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setViewportView(pnChiTietDV);
		scrollPane.getViewport().setOpaque(false);
		Vector<String> dsLoaiDV = new Vector<String>();
		dsLoaiDV.add("Tất cả");
		for (LoaiDichVu loaiDichVu : LoaiDichVu.values()) {
			dsLoaiDV.add(loaiDichVu.getLoaiDichVu());
		}
		cboLoaiDV.setModel(new DefaultComboBoxModel<String>(dsLoaiDV));

		Vector<String> dsTrangThaiDV = new Vector<String>();
		dsTrangThaiDV.add("Tất cả");
		for (TrangThaiDichVu trangThaiDichVu : TrangThaiDichVu.values()) {
			dsTrangThaiDV.add(trangThaiDichVu.getTrangThaiDichVu());
		}
		cboTrangThai.setModel(new DefaultComboBoxModel<String>(dsTrangThaiDV));
		lamMoi();
	}

	private final int NUMBEROFCOLUMN = 5;
	private JLabel lblTrangThai;
	private JComboBox<String> cboTrangThai;

	public void hienThiDanhSachVoiDieuKien(Object... objectSearch) throws RemoteException {
		pnChiTietDV.removeAll(); // Xóa các thành phần hiển thị cũ

		List<DichVu> dsDV = new Vector<>(dichVuIDAO.timKiemNhieuTruong(objectSearch));
		new Thread(() -> {
			if (dsDV.isEmpty() || dsDV == null) {
				MyNotification panel = new MyNotification(chinhFrame, MyNotification.Type.INFO,
						MyNotification.Location.CENTER, "Không tìm thấy kết quả nào!");
				panel.showNotification();
			} else {
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.insets = new Insets(5, 5, 5, 5);
				synchronized (dsDV) {
					dsDV.stream().forEach(dichVu -> {
						ServiceCardPanel pnChild = new ServiceCardPanel(dichVu);
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
								FormManager.getInstance().showForm("Thông tin chi tiết Dịch vụ",
										new ThongTinChiTietDichVuPanel(dichVu), 500, 700);

							}
						});

						pnChiTietDV.add(pnChild, gbc);
						pnChiTietDV.revalidate();
						pnChiTietDV.repaint();
					});
				}
			}
		}).start();
	}

	private void lamMoi() {
		new Thread(() -> {
			try {
				cboMaDV.setModel(new DefaultComboBoxModel<String>(dichVuIDAO.layTatCaTheoMa()));
				cboTenDV.setModel(new DefaultComboBoxModel<String>(dichVuIDAO.layTatCaTheoTen()));
				cboLoaiDV.setSelectedIndex(0);
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
		if (o.equals(btnTimKiem) || o.equals(cboMaDV)) {
			try {
				hienThiDanhSachVoiDieuKien(cboMaDV.getSelectedItem(), cboTenDV.getSelectedItem().toString().trim(),
						cboLoaiDV.getSelectedItem().toString(), cboTrangThai.getSelectedItem().toString(),
						(long) spnGiaBD.getValue(), (long) spnGiaKT.getValue());
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (o.equals(btnLamMoi))
			lamMoi();
	}
}
