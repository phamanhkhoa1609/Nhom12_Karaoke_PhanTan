package gui;

import com.formdev.flatlaf.FlatClientProperties;

import dao.ChiTietHoaDonDichVuDAO;
import dao.ChiTietHoaDonPhongDAO;
import dao.DichVuDAO;
import dao.HoaDonDAO;
import dao.KhachHangDAO;
import dao.NhanVienDAO;
import dao.PhongDAO;
import dao.TaiKhoanDAO;
import interfaces.ChiTietHoaDonDichVuIDAO;
import interfaces.ChiTietHoaDonPhongIDAO;
import interfaces.DichVuIDAO;
import interfaces.HoaDonIDAO;
import interfaces.KhachHangIDAO;
import interfaces.NhanVienIDAO;
import interfaces.PhongIDAO;
import interfaces.TaiKhoanIDAO;
import jakarta.persistence.EntityManager;
import net.miginfocom.swing.MigLayout;
import raven.chart.ChartLegendRenderer;
import raven.chart.data.category.DefaultCategoryDataset;
import raven.chart.line.LineChart;
import raven.swing.blur.BlurBackground;
import raven.swing.blur.BlurChild;
import raven.swing.blur.style.StyleOverlay;
import utils.EntityManagerFactoryUltil;
import utils.ServerRMI;
import utils.guicomponents.FlatRobotoInit;
import utils.guicomponents.MyImageIcon;
import utils.guicomponents.table.MyTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ChinhFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	public static final int PORT = 2000;
	private static ServerRMI serverRMI;	
	private HoaDonDAO hoaDonIDAO;
	/**
	 * Launch the application.
	 */
	
	public static void ketThuc() {
		try {
			serverRMI.ketThuc();
		} catch (Exception e1) {
			// TODO Auto-generated catchs block
			e1.printStackTrace();
		}
	}

	public ChinhFrame() {
		try {
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					ketThuc();
				}
				
			});
			khoiTaoGiaoDien();
			KhoiTaoKetNoi();
//			luongCapNhatLanCuoi();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void KhoiTaoKetNoi() {
		try {
			serverRMI = new ServerRMI(gettblDanhSach());
			hoaDonIDAO = serverRMI.getHoaDonIDAO();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void luongCapNhatLanCuoi() {
		new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(1000);
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					Date date = Calendar.getInstance().getTime();
					String strDate = sdf.format(date);
					lblThoiGianCapNhat.setText(strDate);
					hoaDonIDAO.xuLyHetHan();
					hoaDonIDAO.xuLyPhongSuDungXong();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public MyTable gettblDanhSach() {
		return tblDanhSach;
	}

	KhachHangIDAO khachHangIDAO;
	NhanVienIDAO nhanVienIDAO;
	public MyTable tblDanhSach;
	private JLabel lblSoLuong = new JLabel("0");
	private static JLabel lblThoiGianCapNhat;

	private void khoiTaoGiaoDien() {
		setSize(new Dimension(720, 500));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getRootPane().putClientProperty(FlatClientProperties.FULL_WINDOW_CONTENT, true);
		setTitle("KaraGOke");
		setIconImage(new MyImageIcon("src/main/resources/images/icons/applogo.png", 500, 500, 999).getImage());
		setLocationRelativeTo(null);

		BlurBackground blurBackground = new BlurBackground();
		blurBackground.setImage(new MyImageIcon(
				"src/main/resources/images/karaoke_background_" + (new Random().nextInt(10) + 1) + ".jpg").getImage()
				.getScaledInstance((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
						(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight(), Image.SCALE_SMOOTH));
		blurBackground.setOverlay(new StyleOverlay(new Color(50, 50, 50), 0.4f));
		JPanel pnBang = new JPanel();
		pnBang.setLayout(new BorderLayout());
		JScrollPane scrDanhSachTruyCap = new JScrollPane();
		scrDanhSachTruyCap.putClientProperty(FlatClientProperties.STYLE, "" + "border:0,0,0,0");
		tblDanhSach = new MyTable();
		tblDanhSach.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "IP truy cập", "Mã người dùng", "Họ tên", "Vai trò" }) {
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false;
			}
		});
		Map<Integer, Integer> setCellAlignMap = new HashMap<Integer, Integer>();
		setCellAlignMap.put(0, SwingConstants.RIGHT);
		setCellAlignMap.put(1, SwingConstants.CENTER);
		setCellAlignMap.put(2, SwingConstants.LEFT);
		setCellAlignMap.put(3, SwingConstants.LEFT);
		tblDanhSach.setColumnCenterAndCellAlign(setCellAlignMap);
		tblDanhSach.getModel().addTableModelListener(e -> {
			int count = tblDanhSach.getModel().getRowCount();
			lblSoLuong.setText(count + "");
		});
		scrDanhSachTruyCap.setViewportView(tblDanhSach);
		getContentPane().add(scrDanhSachTruyCap, BorderLayout.CENTER);

		JPanel pnTinhTrang = new JPanel();
		getContentPane().add(pnTinhTrang, BorderLayout.NORTH);
		pnTinhTrang.setLayout(new MigLayout("", "[438px,grow]", "[30px][30px:40px:40px][30px:40px:40px]"));

		JLabel lblTieuDe = new JLabel("Server khởi động thành công ở port " + PORT);
		lblTieuDe.setForeground(Color.WHITE);
		lblTieuDe.setFont(new Font("Times New Roman", Font.BOLD, 25));
		pnTinhTrang.add(lblTieuDe, "cell 0 0,alignx center,growy");

		JLabel lblSoLuongTruyCap = new JLabel("Số lượng người truy cập:");
		lblSoLuongTruyCap.setFont(new Font("Times New Roman", Font.BOLD, 15));
		pnTinhTrang.add(lblSoLuongTruyCap, "flowx,cell 0 1,alignx left,growy");

		lblSoLuong.setForeground(Color.WHITE);
		lblSoLuong.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnTinhTrang.add(lblSoLuong, "cell 0 1,grow");

		JLabel lblCapNhatLanCuoi = new JLabel("Cập nhật lần cuối");
		lblCapNhatLanCuoi.setFont(new Font("Times New Roman", Font.BOLD, 15));
		pnTinhTrang.add(lblCapNhatLanCuoi, "flowx,cell 0 2,growy");

		lblThoiGianCapNhat = new JLabel("");
		lblThoiGianCapNhat.setForeground(Color.WHITE);
		lblThoiGianCapNhat.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnTinhTrang.add(lblThoiGianCapNhat, "cell 0 2,grow");
	}

}