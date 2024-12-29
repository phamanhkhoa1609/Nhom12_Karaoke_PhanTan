package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class TrangChuNVTiepTan_GUI extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JPanel pnContent;
	private JMenuBar menuBar;
	private JMenu menuTaiKhoan, menuPhong, menuKhachHang, menuDichVu, menuHoaDon,
	menuTraCuu, menuThoat;
	private JMenuItem itemDangXuat, itemQLPhong, itemDatPhong, itemQL_TTKH, itemDatDichVu, itemQL_TTDV,
	itemLapHoaDon, itemTraCuuPhong, itemTraCuuDV, itemTraCuuHD, itemTraCuuKH, itemTraCuuNV;
	
	public TrangChuNVTiepTan_GUI() throws Exception {
		buildGUI();
	}
	public void buildGUI() throws Exception {
		setTitle("Trang chủ");
		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);

		createGUI();
		createMenuGUI();
	}
	public void createGUI() throws IOException {
		pnContent = new JPanel();
		pnContent.setLayout(new FlowLayout());
		Color mauNen = new Color(205, 38, 38);
		

		JLabel lblTitle = new JLabel("Chào mừng nhân viên tiếp tân");
		pnContent.add(lblTitle);
		lblTitle.setFont(new Font("Arial", Font.BOLD, 30));
		lblTitle.setForeground(Color.white);

		BufferedImage image = ImageIO.read(new File("src/images/IMG_KARAOKE_NICE.png"));
		JLabel picLabel = new JLabel(new ImageIcon(image));
		pnContent.add(picLabel);
		pnContent.setBackground(mauNen);

		Container container = getContentPane();
		container.add(pnContent);

	}
	public void createMenuGUI() {
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		// menu tài khoản
		menuTaiKhoan = new JMenu("Tài khoản");
		menuBar.add(menuTaiKhoan);
		itemDangXuat = new JMenuItem("Đăng Xuất");
		menuTaiKhoan.add(itemDangXuat);

		ImageIcon iconUser = new ImageIcon(ClassLoader.getSystemResource("icons/users.png"));
		Image imageUser = iconUser.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
		ImageIcon iconUser1 = new ImageIcon(imageUser);
		menuTaiKhoan.setIcon(iconUser1);
		
		ImageIcon iconDangXuat = new ImageIcon(ClassLoader.getSystemResource("icons/logout.png"));
		Image imageDangXuat = iconDangXuat.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
		ImageIcon iconDangXuat1 = new ImageIcon(imageDangXuat);
		itemDangXuat.setIcon(iconDangXuat1);
		
		
//		Menu phong
		menuPhong = new JMenu("Quản lý phòng");
		menuBar.add(menuPhong);
		itemDatPhong = new JMenuItem("Đặt phòng");
		itemQLPhong = new JMenuItem("Quản lý thông tin phòng");
		menuPhong.add(itemDatPhong);
		menuPhong.add(itemQLPhong);

		ImageIcon iconPhong = new ImageIcon(ClassLoader.getSystemResource("icons/phongKaraoke.jpeg"));
		Image imagePhong = iconPhong.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
		ImageIcon iconPhong1 = new ImageIcon(imagePhong);
		menuPhong.setIcon(iconPhong1);
		itemQLPhong.setIcon(iconPhong1);

		ImageIcon iconDatPhong = new ImageIcon(ClassLoader.getSystemResource("icons/actor_karaoke.png"));
		Image imageDatPhong = iconDatPhong.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
		ImageIcon iconDatPhong1 = new ImageIcon(imageDatPhong);
		itemDatPhong.setIcon(iconDatPhong1);
		
		
//		Menu Khach Hang
		menuKhachHang = new JMenu("Khách hàng");
		menuBar.add(menuKhachHang);
		itemQL_TTKH = new JMenuItem("Quản lý thông tin khách hàng");
		menuKhachHang.add(itemQL_TTKH);

		ImageIcon iconKH = new ImageIcon(ClassLoader.getSystemResource("icons/customer.png"));
		Image imageKH = iconKH.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
		ImageIcon iconKH1 = new ImageIcon(imageKH);
		menuKhachHang.setIcon(iconKH1);

		ImageIcon iconQuanLy = new ImageIcon(ClassLoader.getSystemResource("icons/updated.png"));
		Image imageQuanly = iconQuanLy.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
		ImageIcon iconQuanLy1 = new ImageIcon(imageQuanly);
		itemQL_TTKH.setIcon(iconQuanLy1);
		
		
//		Menu Dich Vu
		menuDichVu = new JMenu("Dịch vụ");
		menuBar.add(menuDichVu);
		itemDatDichVu = new JMenuItem("Đặt dịch vụ");
		itemQL_TTDV = new JMenuItem("Quản lý thông tin dịch vụ");
		menuDichVu.add(itemDatDichVu);
		menuDichVu.add(itemQL_TTDV);

		ImageIcon iconDichVu = new ImageIcon(ClassLoader.getSystemResource("icons/customer-service.png"));
		Image imageDichVu = iconDichVu.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
		ImageIcon iconDichVu1 = new ImageIcon(imageDichVu);
		menuDichVu.setIcon(iconDichVu1);
		
		ImageIcon iconDatDichVu = new ImageIcon(ClassLoader.getSystemResource("icons/receipt.png"));
		Image imageDatDichVu = iconDatDichVu.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
		ImageIcon iconDatDichVu1 = new ImageIcon(imageDatDichVu);
		itemDatDichVu.setIcon(iconDatDichVu1);
		itemQL_TTDV.setIcon(iconQuanLy1);
		

		
//		Menu Lap Hoa Don
		menuHoaDon = new JMenu("Hóa đơn");
		menuBar.add(menuHoaDon);
		itemLapHoaDon = new JMenuItem("Lập hóa đơn");
		menuHoaDon.add(itemLapHoaDon);

		ImageIcon iconLapHD = new ImageIcon(ClassLoader.getSystemResource("icons/bills.png"));
		Image imageLapHD = iconLapHD.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
		ImageIcon iconLapHD1 = new ImageIcon(imageLapHD);
		menuHoaDon.setIcon(iconLapHD1);
		itemLapHoaDon.setIcon(iconLapHD1);

//		ImageIcon icon33 = new ImageIcon(ClassLoader.getSystemResource("icons/bill.png"));
//		Image image17 = icon33.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
//		ImageIcon icon34 = new ImageIcon(image17);
		
		
		
//		Menu Tra Cuu
		menuTraCuu = new JMenu("Tra cứu");
//		menuBar.add(menuTraCuu);
		itemTraCuuPhong = new JMenuItem("Tra cứu thông tin phòng");
		itemTraCuuDV = new JMenuItem("Tra cứu thông tin dịch vụ");
		itemTraCuuHD = new JMenuItem("Tra cứu thông tin hóa đơn");
		itemTraCuuKH = new JMenuItem("Tra cứu thông tin khách hàng");
		itemTraCuuNV = new JMenuItem("Tra cứu thông tin nhân viên");
		menuPhong.add(itemTraCuuPhong);
		menuDichVu.add(itemTraCuuDV);
		menuHoaDon.add(itemTraCuuHD);
		menuKhachHang.add(itemTraCuuKH);
		
		ImageIcon iconTraCuu = new ImageIcon(ClassLoader.getSystemResource("icons/seo.png"));
		Image imageTraCuu = iconTraCuu.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
		ImageIcon iconTraCuu1 = new ImageIcon(imageTraCuu);
//		menuTraCuu.setIcon(iconTraCuu1);
		itemTraCuuPhong.setIcon(iconTraCuu1);
		itemTraCuuDV.setIcon(iconTraCuu1);
		itemTraCuuHD.setIcon(iconTraCuu1);
		itemTraCuuKH.setIcon(iconTraCuu1);
		
		
//		Menu Thoat
		menuThoat = new JMenu("Thoát");
//		menuBar.add(menuThoat);
		ImageIcon iconThoat = new ImageIcon(ClassLoader.getSystemResource("icons/thoatCT.jpeg"));
		Image imageThoat = iconThoat.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
		ImageIcon iconThoat1 = new ImageIcon(imageThoat);
		menuThoat.setIcon(iconThoat1);
		
		itemDangXuat.addActionListener(this);
		itemQLPhong.addActionListener(this);
		itemDatPhong.addActionListener(this);
		itemQL_TTDV.addActionListener(this);
		itemDatDichVu.addActionListener(this);
		itemQL_TTKH.addActionListener(this);
		itemLapHoaDon.addActionListener(this);
		itemTraCuuDV.addActionListener(this);
		itemTraCuuHD.addActionListener(this);
		itemTraCuuKH.addActionListener(this);
		itemTraCuuNV.addActionListener(this);
		itemTraCuuPhong.addActionListener(this);

	}
	public static void main(String[] args) throws Exception {
		new TrangChuNVTiepTan_GUI().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Object o = e.getSource();
			if (o.equals(itemDangXuat)) {
				new DangNhap_GUI().setVisible(true);
				this.dispose();
			}
			else if (o.equals(itemQLPhong))
				new QLPhong_GUI().setVisible(true);
			else if (o.equals(itemDatPhong))
				new DatPhong_GUI().setVisible(true);
			else if (o.equals(itemQL_TTDV))
				new DichVu_GUI().setVisible(true);
			else if (o.equals(itemDatDichVu))
				new DatDichVu_GUI().setVisible(true);
			else if (o.equals(itemQL_TTKH))
				new KhachHang_GUI().setVisible(true);
			else if (o.equals(itemLapHoaDon))
				new LapHoaDon_GUI().setVisible(true);
			else if (o.equals(itemTraCuuPhong))
				new TraCuuPhong_GUI().setVisible(true);
			else if (o.equals(itemTraCuuDV))
				new TraCuuDichVu_GUI().setVisible(true);
			else if (o.equals(itemTraCuuHD))
				new TraCuuHoaDon_GUI().setVisible(true);
			else if (o.equals(itemTraCuuKH))
				new TraCuuKhachHang_GUI().setVisible(true);
			else if (o.equals(itemTraCuuNV))
				new TraCuuNhanVien_GUI().setVisible(true);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

}
