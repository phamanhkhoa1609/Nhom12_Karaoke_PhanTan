package gui.subgui;

import net.miginfocom.swing.MigLayout;
import raven.popup.component.GlassPaneChild;
import raven.swing.blur.BlurBackground;
import raven.swing.blur.BlurChild;
import raven.swing.blur.style.*;
import utils.ClientIDAO;
import utils.guicomponents.MyImageIcon;
import utils.guicomponents.MyPanel;
import utils.guicomponents.notification.MyNotification;
import utils.guicomponents.optionmessage.GlassPanePopup;
import utils.guicomponents.optionmessage.MyMessage;
import gui.DangKyTaiKhoanKhachHang;
import gui.DangNhapFrame;
import gui.ThayDoiMatKhauPanel;
import gui.ThongTinChiTietKhachHang;
import gui.TimKiemDichVuPanel;
import gui.TimKiemHoaDonPanel;
import gui.TimKiemPhongPanel;
import interfaces.TaiKhoanIDAO;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import entities.KhachHang;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

public class Menu extends MyPanel implements ActionListener {

	private MyDeskstopPane myDeskstopPane;
	private ClientIDAO clientIDAO;
	private JMenuItem thayDoiMatKhauItem;
	private JMenuItem dangXuatItem;
	private AbstractButton xemTaiKhoan;

	public Menu(MyDeskstopPane myDeskstopPane) {
		this.myDeskstopPane = myDeskstopPane;
		clientIDAO = myDeskstopPane.getClientIDAO();
		try {
			TaiKhoanIDAO taiKhoanIDAO = clientIDAO.getTaiKhoanIDAO();
			taiKhoanIDAO.truyCap(null, clientIDAO.getMyIP());
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void KhoiTaoGiaoDien() {
		background = new BlurBackground();
		background.setImage(new MyImageIcon("src/main/resources/images/karaokebackground.jpg").getImage()
				.getScaledInstance((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
						(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight(), Image.SCALE_SMOOTH));

		background.setLayout(new MigLayout("al center center"));
		blurChild = new BlurChild(getStyle());
		blurChild.setLayout(new MigLayout("insets 50,wrap 5,gap 20", "[sg,fill]", "[sg,fill]"));
		background.add(blurChild);

		setLayout(new BorderLayout());

		add(background);

		createMenu();
		
		chinhFrame.addWindowListener(new WindowAdapter() {
			@Override
				public void windowClosing(WindowEvent e) {
                    try {
                        System.out.println("Ngắt kết nối");
                        if(nguoiDung == null)clientIDAO.getTaiKhoanIDAO().ngatKetNoi(-1, clientIDAO.getMyIP());
                        else clientIDAO.getTaiKhoanIDAO().ngatKetNoi(nguoiDung.getMaND(), clientIDAO.getMyIP());
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    }
			}
		});

	}

	private void createMenu() {
		Item itemHoaDon = new Item("Hoá đơn", "images/icons/order.svg");
		Item itemPhong = new Item("Phòng", "images/icons/room.svg");
		Item itemDV = new Item("Dịch vụ", "images/icons/service.svg");
		Item itemTaiKhoan = new Item("Tài khoản", "images/icons/security.svg");

		itemHoaDon.addActionListener(e -> {
			// Nếu người dùng đã đăng nhập thì mới hiển thị thông tin hóa đơn của khách
			// hàng;
			if (nguoiDung != null) {
				TimKiemHoaDonPanel timKiemHoaDonPanel = new TimKiemHoaDonPanel();
				timKiemHoaDonPanel.setNguoiDung(nguoiDung);
				timKiemHoaDonPanel.setClientIDAO(clientIDAO);
				timKiemHoaDonPanel.setChinhFrame(chinhFrame);
				FormManager.getInstance().showForm("Hoá đơn", timKiemHoaDonPanel);
			} else {
				MyNotification jPanel2 = new MyNotification(chinhFrame, MyNotification.Type.WARNING,
						MyNotification.Location.CENTER, "Bạn cần đăng nhập để xem thông tin hóa đơn.");
				jPanel2.showNotification();
			}
		});
		itemPhong.addActionListener(e -> {
			TimKiemPhongPanel timKiemPhongPanel = new TimKiemPhongPanel();
			timKiemPhongPanel.setClientIDAO(clientIDAO);
			timKiemPhongPanel.setChinhFrame(chinhFrame);
			FormManager.getInstance().showForm("Phòng", timKiemPhongPanel);
		});
		itemDV.addActionListener(e -> {
			TimKiemDichVuPanel timKiemDichVuPanel = new TimKiemDichVuPanel();
			timKiemDichVuPanel.setClientIDAO(clientIDAO);
			timKiemDichVuPanel.setChinhFrame(chinhFrame);
			FormManager.getInstance().showForm("Dịch vụ", timKiemDichVuPanel);
		});

		xemTaiKhoan = new JMenuItem("Xem tài khoản");
		xemTaiKhoan.setActionCommand("Xem tài khoản");
		dangXuatItem = new JMenuItem("Đăng xuất");
		dangXuatItem.setActionCommand("Đăng xuất");
		thayDoiMatKhauItem = new JMenuItem("Thay đổi mật khẩu");
		thayDoiMatKhauItem.setActionCommand("Thay đổi mật khẩu");
		xemTaiKhoan.addActionListener(this);
		dangXuatItem.addActionListener(this);
		thayDoiMatKhauItem.addActionListener(this);
		luaChonMenu.add(xemTaiKhoan);
		luaChonMenu.add(dangXuatItem);
		luaChonMenu.add(thayDoiMatKhauItem);

		itemTaiKhoan.addActionListener(e -> {
			if (nguoiDung != null) {
				add(luaChonMenu);

				itemTaiKhoan.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						Point p = MouseInfo.getPointerInfo().getLocation();
						luaChonMenu.show(chinhFrame, (int) p.getX(), (int) p.getY());
					}
				});

			} else {
				DangNhapFrame dangNhapFrame = new DangNhapFrame();
				dangNhapFrame.setClientIDAO(clientIDAO);
				dangNhapFrame.setChinhFrame(chinhFrame);
				FormManager.getInstance().showForm("Đăng nhập", dangNhapFrame);
				dangNhapFrame.getManHinhChua().addInternalFrameListener(new InternalFrameAdapter() {

	@Override
	public void internalFrameClosed(InternalFrameEvent e) {
		setNguoiDung(dangNhapFrame.getNguoiDung());
	}});}});

	blurChild.add(itemHoaDon);blurChild.add(itemPhong);blurChild.add(itemDV);blurChild.add(itemTaiKhoan);}

	private JPopupMenu luaChonMenu = new JPopupMenu("Lựa chọn");

	private Style getStyle() {
		return new Style().setBlur(30)
				.setBorder(new StyleBorder(30).setBorderWidth(1.5f).setOpacity(0.25f)
						.setBorderColor(new GradientColor(new Color(150, 150, 150), new Color(230, 230, 230),
								new Point2D.Float(0, 0), new Point2D.Float(0, 1)))
						.setDropShadow(new StyleDropShadow(new Color(0, 0, 0), 0.2f, new Insets(12, 12, 20, 20))))
				.setOverlay(new StyleOverlay(new Color(255, 255, 255), 0.1f));
	}

	private BlurChild blurChild;
	private BlurBackground background;
	private final String ĐƯỜNGDẪNFILETAIKHOANSER = "taikhoan.ser";

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o instanceof JMenuItem) {
			luaChonMenu.setVisible(false);
			o = (JMenuItem) o;
			System.out.println("Đã có sự kiện ở đây: ");
			if (o.equals(xemTaiKhoan)) {
				ThongTinChiTietKhachHang thongTinChiTietKhachHang = new ThongTinChiTietKhachHang((KhachHang) nguoiDung);
				thongTinChiTietKhachHang.setClientIDAO(clientIDAO);
				thongTinChiTietKhachHang.setNguoiDung(nguoiDung);
				FormManager.getInstance().showForm("Thông tin khách hàng", thongTinChiTietKhachHang);
			} else if (o.equals(dangXuatItem)) {
				MyMessage thongBaoDangXuat = new MyMessage("Bạn có muốn đăng xuất?",
						"Hãy nghỉ ngơi sau một thời gian làm việc mệt mỏi", true);
				thongBaoDangXuat.eventOK(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// nếu file tồn tại, xóa file
						if (new File(ĐƯỜNGDẪNFILETAIKHOANSER).exists()) {
							new File(ĐƯỜNGDẪNFILETAIKHOANSER).delete();
						}
						chinhFrame.dispose();
					}
				});
				GlassPanePopup.showPopup(thongBaoDangXuat);
			} else if (o.equals(thayDoiMatKhauItem)) {
				ThayDoiMatKhauPanel thayDoiMatKhauPanel = new ThayDoiMatKhauPanel();
				thayDoiMatKhauPanel.setClientIDAO(clientIDAO);
				thayDoiMatKhauPanel.setChinhFrame(chinhFrame);
				thayDoiMatKhauPanel.setNguoiDung(nguoiDung);
				FormManager.getInstance().showForm("Thay đổi mật khẩu", thayDoiMatKhauPanel, 600, 600);
			}
		}

	}
}
