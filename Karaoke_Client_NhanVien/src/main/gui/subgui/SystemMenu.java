package gui.subgui;

import com.formdev.flatlaf.FlatClientProperties;

import entities.NguoiDung;
import entities.NhanVien;
import entities.enums.ChucVu;
import gui.*;
import net.miginfocom.swing.MigLayout;
import raven.drawer.component.header.SimpleHeader;
import raven.drawer.component.header.SimpleHeaderData;
import raven.drawer.component.menu.*;
import raven.drawer.component.menu.data.Item;
import raven.popup.GlassPopup;
import raven.swing.AvatarIcon;
import raven.swing.blur.BlurChild;
import raven.swing.blur.style.GradientColor;
import raven.swing.blur.style.Style;
import raven.swing.blur.style.StyleBorder;
import raven.swing.blur.style.StyleOverlay;
import utils.ClientIDAO;
import utils.guicomponents.optionmessage.GlassPanePopup;
import utils.guicomponents.optionmessage.MyMessage;

import javax.swing.*;
import javax.xml.namespace.QName;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.io.File;

public class SystemMenu extends BlurChild {
	private ChinhDeskstop chinhDeskstop;
	private ClientIDAO clientIDAO;
	private NguoiDung nguoiDung;
	private final String ĐƯỜNGDẪNFILETAIKHOANSER = "taikhoan.ser";

	public SystemMenu(ChinhDeskstop chinhDeskstop, NguoiDung nguoiDung, ClientIDAO clientIDAO) {
		super(new Style().setBlur(30)
				.setBorder(new StyleBorder(10).setOpacity(0.15f).setBorderWidth(1.2f)
						.setBorderColor(new GradientColor(new Color(200, 200, 200), new Color(150, 150, 150),
								new Point2D.Float(0, 0), new Point2D.Float(1f, 0))))
				.setOverlay(new StyleOverlay(new Color(0, 0, 0), 0.2f)));
		this.nguoiDung = nguoiDung;
		this.chinhDeskstop = chinhDeskstop;
		this.clientIDAO = clientIDAO;
		setLayout(new MigLayout("wrap,fill", "[fill]", "[grow 0][fill]"));
		simpleMenu = new SimpleMenu(getMenuOption());

		simpleMenu.setOpaque(false);
		JScrollPane scrollPane = new JScrollPane(simpleMenu);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.getVerticalScrollBar().setOpaque(false);
		scrollPane.getVerticalScrollBar().setUnitIncrement(10);
		scrollPane.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE,
				"" + "trackArc:999;" + "width:5;" + "thumbInsets:0,0,0,0");

		// header
		AppHeaderPanel header = new AppHeaderPanel();
		UserCardPanel userCard = new UserCardPanel(nguoiDung);
		add(header);
		add(scrollPane);
		add(userCard);
	}

	private SimpleMenuOption getMenuOption() {
		NhanVien nhanVien = (NhanVien) nguoiDung;
		if(nhanVien.getChucVu() == ChucVu.QUẢNLÝ){
			raven.drawer.component.menu.data.MenuItem items[] = new raven.drawer.component.menu.data.MenuItem[] {
					new Item.Label("TRANG CHÍNH"), new Item("Trang chủ", "home.svg"), new Item.Label("NGHIỆP VỤ"),
					new Item("Đặt và thanh toán", "receptionist.svg"), new Item("Thống kê doanh thu", "statistic.svg"),
					new Item.Label("QUẢN LÝ TÀI NGUYÊN VÀ NHÂN LỰC"), new Item("Hóa đơn", "order.svg"),
					new Item("Phòng", "room.svg").subMenu("Tìm kiếm phòng").subMenu("Quản lý phòng"),
					new Item("Dịch vụ", "service.svg").subMenu("Tìm kiếm dịch vụ").subMenu("Quản lý dịch vụ"),
					new Item("Khách hàng", "customer.svg").subMenu("Tìm kiếm khách hàng").subMenu("Quản lý khách hàng"),
					new Item("Nhân viên", "employee.svg").subMenu("Tìm kiếm nhân viên").subMenu("Quản lý nhân viên")
							.subMenu("Tạo tài khoản nhân viên"),
					new Item.Label("CÀI ĐẶT"),
					new Item("Bảo mật", "security.svg").subMenu("Thay đổi mật khẩu").subMenu("Đăng xuất"),
					new Item.Label("TRÒ CHƠI"), new Item("Cờ caro", "tictactoe.svg"), new Item("Dò mìn", "bomb.svg") };
			return new SimpleMenuOption().setBaseIconPath("images/icons").setIconScale(0.05f).setMenus(items)
					.setMenuStyle(new SimpleMenuStyle() {
						@Override
						public void styleMenuPanel(JPanel panel, int[] index) {
							panel.setOpaque(false);
						}

						@Override
						public void styleMenuItem(JButton menu, int[] index) {
							menu.setContentAreaFilled(false);
						}
					}).addMenuEvent(new MenuEvent() {
						@Override
						public void selected(MenuAction menuAction, int[] ints) {
							if (ints.length == 1) {
								int index = ints[0];
								if (index == 0) {
								}
								if (index == 1) {
									DatVaThanhToanPanel datVaThanhToanPanel = new DatVaThanhToanPanel(clientIDAO,
											nguoiDung);
									datVaThanhToanPanel.setChinhDeskstop(chinhDeskstop);
									FormManager.getInstance().showForm("Đặt và thanh toán phòng và dịch vụ",
											datVaThanhToanPanel);
								}
								if (index == 2) {
									ThongKeDichVuPanel thongKeDichVuPanel = new ThongKeDichVuPanel();
									thongKeDichVuPanel.setClientIDAO(clientIDAO);
									FormManager.getInstance().showForm("Thống kê dịch vụ", thongKeDichVuPanel);
									
								}
								if (index == 3) {
									TimKiemHoaDonPanel timKiemHoaDonPanel = new TimKiemHoaDonPanel();
									timKiemHoaDonPanel.setClientIDAO(clientIDAO);
									timKiemHoaDonPanel.setChinhDeskstop(chinhDeskstop);
									FormManager.getInstance().showForm("Tìm kiếm hóa đơn", timKiemHoaDonPanel);
								}
								if (index == 9) {
									FormManager.getInstance().showForm("Trò caro", new TroChoiCaroPanel());

								}
								if (index == 10) {
									FormManager.getInstance().showForm("Trò Dò mìn", new TroChoiDoMinPanel());

								}
							} else if (ints.length == 2) {
								int index = ints[0];
								int subIndex = ints[1];
								if (index == 4) {
									if (subIndex == 0) {
										TimKiemPhongPanel timKiemPhongPanel = new TimKiemPhongPanel();
										timKiemPhongPanel.setClientIDAO(clientIDAO);
										timKiemPhongPanel.setChinhDeskstop(chinhDeskstop);
										FormManager.getInstance().showForm("Tìm kiếm phòng", timKiemPhongPanel);
									} else if (subIndex == 1) {
										QuanLyPhongPanel quanLyPhongPanel = new QuanLyPhongPanel();
										quanLyPhongPanel.setClientIDAO(clientIDAO);
										quanLyPhongPanel.setChinhDeskstop(chinhDeskstop);
										FormManager.getInstance().showForm("Quản lý phòng", quanLyPhongPanel);
									}
								}
								if (index == 5) {
									if (subIndex == 0) {
										TimKiemDichVuPanel timKiemDichVuPanel = new TimKiemDichVuPanel();
										timKiemDichVuPanel.setClientIDAO(clientIDAO);
										timKiemDichVuPanel.setChinhDeskstop(chinhDeskstop);
										FormManager.getInstance().showForm("Tìm kiếm dịch vụ", timKiemDichVuPanel);
									} else if (subIndex == 1) {
										QuanLyDichVuPanel quanLyDichVuPanel = new QuanLyDichVuPanel();
										quanLyDichVuPanel.setClientIDAO(clientIDAO);
										quanLyDichVuPanel.setChinhDeskstop(chinhDeskstop);
										FormManager.getInstance().showForm("Quản lý dịch vụ", quanLyDichVuPanel);
									}
								}
								if (index == 6) {
									if (subIndex == 0) {
										TimKiemKhachHangPanel timKiemKhachHangPanel = new TimKiemKhachHangPanel();
										timKiemKhachHangPanel.setClientIDAO(clientIDAO);
										timKiemKhachHangPanel.setChinhDeskstop(chinhDeskstop);
										FormManager.getInstance().showForm("Tìm kiếm khách hàng",
												timKiemKhachHangPanel);
									} else if (subIndex == 1) {
										QuanLyKhachHangPanel quanLyKhachHangPanel = new QuanLyKhachHangPanel();
										quanLyKhachHangPanel.setClientIDAO(clientIDAO);
										quanLyKhachHangPanel.setChinhDeskstop(chinhDeskstop);
										FormManager.getInstance().showForm("Quản lý khách hàng", quanLyKhachHangPanel);
									}
								}
								if (index == 7) {
									if (subIndex == 0) {
										TimKiemNhanVienPanel timKiemNhanVienPanel = new TimKiemNhanVienPanel();
										timKiemNhanVienPanel.setClientIDAO(clientIDAO);
										timKiemNhanVienPanel.setChinhDeskstop(chinhDeskstop);
										FormManager.getInstance().showForm("Tìm kiếm nhân viên", timKiemNhanVienPanel);
									} else if (subIndex == 1) {
										QuanLyNhanVienPanel quanLyNhanVienPanel = new QuanLyNhanVienPanel();
										quanLyNhanVienPanel.setClientIDAO(clientIDAO);
										quanLyNhanVienPanel.setChinhDeskstop(chinhDeskstop);
										FormManager.getInstance().showForm("Quản lý nhân viên", quanLyNhanVienPanel);
									} else if (subIndex == 2) {
										TaoTaiKhoanNhanVienPanel taoTaiKhoanNhanVienPanel = new TaoTaiKhoanNhanVienPanel();
										taoTaiKhoanNhanVienPanel.setClientIDAO(clientIDAO);
										taoTaiKhoanNhanVienPanel.setChinhDeskstop(chinhDeskstop);
										FormManager.getInstance().showForm("Tạo tài khoản nhân viên",
												taoTaiKhoanNhanVienPanel);
									}
								}
								if (index == 8) {
									if (subIndex == 0) {
										ThayDoiMatKhauPanel thayDoiMatKhauPanel = new ThayDoiMatKhauPanel();
										thayDoiMatKhauPanel.setClientIDAO(clientIDAO);
										thayDoiMatKhauPanel.setChinhDeskstop(chinhDeskstop);
										thayDoiMatKhauPanel.setNguoiDung(nguoiDung);
										FormManager.getInstance().showForm("Thay đổi mật khẩu", thayDoiMatKhauPanel,
												600, 600);
									} else if (subIndex == 1) {
										MyMessage thongBaoDangXuat = new MyMessage("Bạn có muốn đăng xuất?",
												"Hãy nghỉ ngơi sau một thời gian làm việc mệt mỏi", true);
										thongBaoDangXuat.eventOK(new ActionListener() {

											@Override
											public void actionPerformed(ActionEvent e) {
												// nếu file tồn tại, xóa file
												if (new File(ĐƯỜNGDẪNFILETAIKHOANSER).exists()) {
													new File(ĐƯỜNGDẪNFILETAIKHOANSER).delete();
												}
												chinhDeskstop.dispose();
											}
										});
										GlassPanePopup.showPopup(thongBaoDangXuat);
									}
								}

							}
						}
					});}
		else if(nhanVien.getChucVu()==ChucVu.TIẾPTÂN) {
			raven.drawer.component.menu.data.MenuItem items[] = new raven.drawer.component.menu.data.MenuItem[] {
					new Item.Label("TRANG CHÍNH"), new Item("Trang chủ", "home.svg"), new Item.Label("NGHIỆP VỤ"),
					new Item("Đặt và thanh toán", "receptionist.svg"),
					new Item.Label("QUẢN LÝ TÀI NGUYÊN VÀ NHÂN LỰC"), new Item("Hóa đơn", "order.svg"),
					new Item("Phòng", "room.svg").subMenu("Tìm kiếm phòng").subMenu("Quản lý phòng"),
					new Item("Dịch vụ", "service.svg").subMenu("Tìm kiếm dịch vụ"),
					new Item("Khách hàng", "customer.svg").subMenu("Tìm kiếm khách hàng").subMenu("Quản lý khách hàng"),
					new Item.Label("CÀI ĐẶT"),
					new Item("Bảo mật", "security.svg").subMenu("Thay đổi mật khẩu").subMenu("Đăng xuất"),
					new Item.Label("TRÒ CHƠI"), new Item("Cờ caro", "tictactoe.svg"), new Item("Dò mìn", "bomb.svg") };
			return new SimpleMenuOption().setBaseIconPath("images/icons").setIconScale(0.05f).setMenus(items)
					.setMenuStyle(new SimpleMenuStyle() {
						@Override
						public void styleMenuPanel(JPanel panel, int[] index) {
							panel.setOpaque(false);
						}

						@Override
						public void styleMenuItem(JButton menu, int[] index) {
							menu.setContentAreaFilled(false);
						}
					}).addMenuEvent(new MenuEvent() {
						@Override
						public void selected(MenuAction menuAction, int[] ints) {
							if (ints.length == 1) {
								int index = ints[0];
								if (index == 0) {
								}
								if (index == 1) {
									DatVaThanhToanPanel datVaThanhToanPanel = new DatVaThanhToanPanel(clientIDAO,
											nguoiDung);
									datVaThanhToanPanel.setChinhDeskstop(chinhDeskstop);
									FormManager.getInstance().showForm("Đặt và thanh toán phòng và dịch vụ",
											datVaThanhToanPanel);
								}
								if (index == 2) {
									TimKiemHoaDonPanel timKiemHoaDonPanel = new TimKiemHoaDonPanel();
									timKiemHoaDonPanel.setClientIDAO(clientIDAO);
									timKiemHoaDonPanel.setChinhDeskstop(chinhDeskstop);
									FormManager.getInstance().showForm("Tìm kiếm hóa đơn", timKiemHoaDonPanel);
								}
								if (index == 7) {
									FormManager.getInstance().showForm("Trò caro", new TroChoiCaroPanel());

								}
								if (index == 8) {
									FormManager.getInstance().showForm("Trò Dò mìn", new TroChoiDoMinPanel());

								}
							} else if (ints.length == 2) {
								int index = ints[0];
								int subIndex = ints[1];
								if (index == 3) {
									if (subIndex == 0) {
										TimKiemPhongPanel timKiemPhongPanel = new TimKiemPhongPanel();
										timKiemPhongPanel.setClientIDAO(clientIDAO);
										timKiemPhongPanel.setChinhDeskstop(chinhDeskstop);
										FormManager.getInstance().showForm("Tìm kiếm phòng", timKiemPhongPanel);
									} else if (subIndex == 1) {
										QuanLyPhongPanel quanLyPhongPanel = new QuanLyPhongPanel();
										quanLyPhongPanel.setClientIDAO(clientIDAO);
										quanLyPhongPanel.setChinhDeskstop(chinhDeskstop);
										quanLyPhongPanel.setNguoiDung(nhanVien);
										FormManager.getInstance().showForm("Quản lý phòng", quanLyPhongPanel);
									}
								}
								if (index == 4)
								{
									if (subIndex == 0) {
										TimKiemDichVuPanel timKiemDichVuPanel = new TimKiemDichVuPanel();
										timKiemDichVuPanel.setClientIDAO(clientIDAO);
										timKiemDichVuPanel.setChinhDeskstop(chinhDeskstop);
										FormManager.getInstance().showForm("Tìm kiếm dịch vụ", timKiemDichVuPanel);
									}
								}
								if (index == 5) {
									if (subIndex == 0) {
										TimKiemKhachHangPanel timKiemKhachHangPanel = new TimKiemKhachHangPanel();
										timKiemKhachHangPanel.setClientIDAO(clientIDAO);
										timKiemKhachHangPanel.setChinhDeskstop(chinhDeskstop);
										FormManager.getInstance().showForm("Tìm kiếm khách hàng",
												timKiemKhachHangPanel);
									} else if (subIndex == 1) {
										QuanLyKhachHangPanel quanLyKhachHangPanel = new QuanLyKhachHangPanel();
										quanLyKhachHangPanel.setClientIDAO(clientIDAO);
										quanLyKhachHangPanel.setChinhDeskstop(chinhDeskstop);
										FormManager.getInstance().showForm("Quản lý khách hàng", quanLyKhachHangPanel);
									}
								}
					
								if (index == 6) {
									if (subIndex == 0) {
										ThayDoiMatKhauPanel thayDoiMatKhauPanel = new ThayDoiMatKhauPanel();
										thayDoiMatKhauPanel.setClientIDAO(clientIDAO);
										thayDoiMatKhauPanel.setChinhDeskstop(chinhDeskstop);
										thayDoiMatKhauPanel.setNguoiDung(nguoiDung);
										FormManager.getInstance().showForm("Thay đổi mật khẩu", thayDoiMatKhauPanel,
												600, 600);
									} else if (subIndex == 1) {
										MyMessage thongBaoDangXuat = new MyMessage("Bạn có muốn đăng xuất?",
												"Hãy nghỉ ngơi sau một thời gian làm việc mệt mỏi", true);
										thongBaoDangXuat.eventOK(new ActionListener() {
											@Override
											public void actionPerformed(ActionEvent e) {
												// nếu file tồn tại, xóa file
												if (new File(ĐƯỜNGDẪNFILETAIKHOANSER).exists()) {
													new File(ĐƯỜNGDẪNFILETAIKHOANSER).delete();
												}
												chinhDeskstop.dispose();
											}
										});
										GlassPanePopup.showPopup(thongBaoDangXuat);
									}
								}

							}
						}
					});
		}
		return null;
	}

	private SimpleMenu simpleMenu;
}
