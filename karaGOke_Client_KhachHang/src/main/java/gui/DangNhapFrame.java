package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.formdev.flatlaf.FlatClientProperties;

import entities.KhachHang;
import entities.NguoiDung;
import entities.TaiKhoan;
import gui.subgui.MyDeskstopPane;
import gui.subgui.FormManager;
import interfaces.TaiKhoanIDAO;
import jakarta.persistence.NoResultException;
import net.miginfocom.swing.MigLayout;
import raven.swing.blur.BlurBackground;
import raven.swing.blur.style.StyleOverlay;
import utils.ClientIDAO;
import utils.guicomponents.FlatRobotoInit;
import utils.guicomponents.MyImageIcon;
import utils.guicomponents.MyPanel;
import utils.guicomponents.notification.MyNotification;

import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.JButton;
import javax.swing.JCheckBox;

public class DangNhapFrame extends MyPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JDesktopPane desktopPane;
	private JTextField txtSDT;
	private JPasswordField txtMatKhau;
	private JButton btnDangNhap;
	private JCheckBox ckbHienMatKhau;
	private JCheckBox ckbNhoTaiKhoan;
	private final String ĐƯỜNGDẪNFILETAIKHOANSER = "taikhoan.ser";
	private ClientIDAO clientRMIDAO;
	
	public DangNhapFrame() {
		clientRMIDAO = new ClientIDAO();
		taiKhoanIDAO = clientRMIDAO.getTaiKhoanIDAO();
	}
	@Override
	public void KhoiTaoGiaoDien() {
		TaiKhoan taiKhoan = null;
		ckbNhoTaiKhoan = new JCheckBox("Nhớ tài khoản");
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ĐƯỜNGDẪNFILETAIKHOANSER))) {
			taiKhoan = (TaiKhoan) ois.readObject();
			ckbNhoTaiKhoan.setSelected(true);
		} catch (Exception e) {
			KhachHang khachHang = new KhachHang();
			khachHang.setSdt("");
			taiKhoan = new TaiKhoan(khachHang.getSdt(), "");
		}



		setLayout(new MigLayout("", "[30px:n][400.00px,grow][30px:n]",
				"[30px:n][30px:n][][:30px:40px,grow][][:30px:40px,grow][][:30px:40px,grow][]"));

		JLabel lblTieuDe = new JLabel();
		lblTieuDe.setHorizontalAlignment(SwingConstants.CENTER);
		lblTieuDe.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTieuDe.setForeground(Color.WHITE);
		lblTieuDe.setText("Đăng nhập");
		add(lblTieuDe, "cell 1 1,alignx center");

		JLabel lblSDT = new JLabel("Số điện thoại");
		lblSDT.setHorizontalAlignment(SwingConstants.CENTER);
		lblSDT.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblSDT, "cell 1 2,alignx left");

		txtSDT = new JTextField(taiKhoan.getSdt());
		txtSDT.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(txtSDT, "cell 1 3,grow");

		JLabel lblMatKhau = new JLabel("Mật khẩu");
		lblMatKhau.setHorizontalAlignment(SwingConstants.CENTER);
		lblMatKhau.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(lblMatKhau, "cell 1 4,alignx left");

		txtMatKhau = new JPasswordField(taiKhoan.getMatKhau());
		txtMatKhau.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(txtMatKhau, "cell 1 5,grow");
		txtMatKhau.setEchoChar('*');
		ckbHienMatKhau = new JCheckBox("Hiện mật khẩu");
		ckbHienMatKhau.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		ckbHienMatKhau.addActionListener(this);

		ckbNhoTaiKhoan.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		add(ckbNhoTaiKhoan, "flowx,cell 1 6");

		JLabel lblRong = new JLabel("");
		add(lblRong, "cell 1 6,growx");
		add(ckbHienMatKhau, "cell 1 6,alignx right");

		btnDangNhap = new JButton("Đăng nhập");
		btnDangNhap.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		btnDangNhap.setForeground(Color.WHITE);
		btnDangNhap.setBackground(Color.LIGHT_GRAY);
		btnDangNhap.addActionListener(this);
		add(btnDangNhap, "cell 1 7,grow");

		btnDangKy = new JButton("Chưa có tài khoản? Click vào đây");
		btnDangKy.setBackground(new Color(0, 0, 0, 0)); // set background color to be transparent
		btnDangKy.setBorder(null);
		btnDangKy.setForeground(Color.lightGray);
		btnDangKy.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		btnDangKy.addActionListener(this);
		add(btnDangKy, "cell 1 8,alignx center");
	}

	private JButton btnDangKy;
	private NguoiDung nguoiDung;
	public NguoiDung getNguoiDung() {
		return nguoiDung;
	};
	private TaiKhoanIDAO taiKhoanIDAO;

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnDangNhap)) {
			try {
				nguoiDung = clientRMIDAO.getTaiKhoanIDAO().layTheoSDT(txtSDT.getText());
				if (!nguoiDung.getTaiKhoan().getMatKhau().equals(txtMatKhau.getText())) {
					MyNotification jPanel = new MyNotification(chinhFrame, MyNotification.Type.WARNING,
							MyNotification.Location.CENTER, "Mật khẩu sai");
					jPanel.showNotification();
				} else if(!(nguoiDung instanceof KhachHang)) {
					// nếu người dùng là khách hàng thì sẽ không được đăng nhập
					MyNotification jPanel = new MyNotification(chinhFrame, MyNotification.Type.WARNING,
							MyNotification.Location.CENTER, "Chỉ có khách hàng đăng nhập vào ứng dụng này");
					jPanel.showNotification();
				} else {
					if (ckbNhoTaiKhoan.isSelected()) {
						try (ObjectOutputStream oos = new ObjectOutputStream(
								new FileOutputStream(ĐƯỜNGDẪNFILETAIKHOANSER, false))) { // Sử dụng tham số false để ghi
																							// đè
							oos.writeObject(new TaiKhoan(nguoiDung.getSdt(), new String(txtMatKhau.getPassword())));
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					} else {
						try (ObjectOutputStream oos = new ObjectOutputStream(
								new FileOutputStream(ĐƯỜNGDẪNFILETAIKHOANSER, false))) { // Sử dụng tham số false để ghi
																							// đè
							oos.writeObject(null);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}

					FlatRobotoInit.init();
					try {
						// ngat ket noi cu
						taiKhoanIDAO.ngatKetNoi(-1, clientIDAO.getMyIP());
						taiKhoanIDAO.truyCap(nguoiDung, InetAddress.getLocalHost().getHostAddress());
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					manHinhChua.dispose();
				}
			} catch (NullPointerException e1) {
				
				MyNotification jPanel = new MyNotification(chinhFrame, MyNotification.Type.WARNING,
						MyNotification.Location.CENTER, "Không thể kết nối đến server");
				jPanel.showNotification();
				e1.printStackTrace();
			} catch ( NoResultException e1 ) {
				MyNotification jPanel = new MyNotification(chinhFrame, MyNotification.Type.WARNING,
						MyNotification.Location.CENTER, "Số điện thoại không tồn tại");
				jPanel.showNotification();
			} catch (RemoteException e2) {
				// TODO Auto-generated catch block
				MyNotification jPanel = new MyNotification(chinhFrame, MyNotification.Type.WARNING,
						MyNotification.Location.CENTER, "Không thể kết nối đến server, vui lòng mở lại ứng dụng");
				jPanel.showNotification();
			} 	
		}
		if (o.equals(ckbHienMatKhau)) {
			if (ckbHienMatKhau.isSelected()) {
				txtMatKhau.setEchoChar((char) 0); // set echo character to be empty
			} else {
				txtMatKhau.setEchoChar('*'); // set echo character to be empty
			}
		}
		if (o.equals(btnDangKy)) {
			this.setEnabled(false);
			FlatRobotoInit.init();
			DangKyTaiKhoanKhachHang dangKyTaiKhoanKhachHang = new DangKyTaiKhoanKhachHang();
			dangKyTaiKhoanKhachHang.setClientIDAO(clientRMIDAO);
			dangKyTaiKhoanKhachHang.setChinhFrame(chinhFrame);
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int x = (int) ((screenSize.getWidth() - dangKyTaiKhoanKhachHang.getWidth()) / 2) + 30;
			int y = (int) ((screenSize.getHeight() - dangKyTaiKhoanKhachHang.getHeight()) / 2);
			dangKyTaiKhoanKhachHang.setLocation(x, y);
			dangKyTaiKhoanKhachHang.setVisible(true);
			FormManager.getInstance().showForm("Đăng ký", dangKyTaiKhoanKhachHang);
			dangKyTaiKhoanKhachHang.getManHinhChua().addInternalFrameListener(new InternalFrameAdapter() {
				@Override
			    public void internalFrameClosed(InternalFrameEvent e) {
					manHinhChua.setEnabled(true);
					manHinhChua.setVisible(true);
				}
			});
		}

	}
}
