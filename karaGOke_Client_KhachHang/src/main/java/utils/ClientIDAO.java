package utils;

import java.net.InetAddress;
import java.rmi.Naming;
import interfaces.ChiTietHoaDonDichVuIDAO;
import interfaces.ChiTietHoaDonPhongIDAO;
import interfaces.DichVuIDAO;
import interfaces.HoaDonIDAO;
import interfaces.KhachHangIDAO;
import interfaces.NhanVienIDAO;
import interfaces.PhongIDAO;
import interfaces.TaiKhoanIDAO;
public class ClientIDAO {
    private final String urlRMI = "rmi://127.0.0.1:2000/";
    private String myIP ;
    private ChiTietHoaDonDichVuIDAO chiTietHoaDonDichVuIDAO;
    private ChiTietHoaDonPhongIDAO chiTietHoaDonPhongIDAO;
    private DichVuIDAO dichVuIDAO;
    private HoaDonIDAO hoaDonIDAO;
    private KhachHangIDAO khachHangIDAO;
    private NhanVienIDAO nhanVienIDAO;
    private PhongIDAO phongIDAO;
    private TaiKhoanIDAO taiKhoanIDAO;
    public ChiTietHoaDonDichVuIDAO getChiTietHoaDonDichVuIDAO() {
    	return chiTietHoaDonDichVuIDAO;
    }
	public ChiTietHoaDonPhongIDAO getChiTietHoaDonPhongIDAO() {
		return chiTietHoaDonPhongIDAO;
	}
	public DichVuIDAO getDichVuIDAO() {
		return dichVuIDAO;
	}
	public HoaDonIDAO getHoaDonIDAO() {
		return hoaDonIDAO;
	}
	public KhachHangIDAO getKhachHangIDAO() {
		return khachHangIDAO;
	}
	public NhanVienIDAO getNhanVienIDAO() {
		return nhanVienIDAO;
	}
	public PhongIDAO getPhongIDAO() {
		return phongIDAO;
	}
	public TaiKhoanIDAO getTaiKhoanIDAO() {
		return taiKhoanIDAO;
	}
	
    public String getMyIP() {
		return myIP;
	}
	public ClientIDAO() {
        try {
        	myIP = InetAddress.getLocalHost().getHostAddress();
        	chiTietHoaDonDichVuIDAO = (ChiTietHoaDonDichVuIDAO) Naming.lookup(urlRMI + "chiTietHoaDonDichVuIDAO");
        	chiTietHoaDonPhongIDAO =  (ChiTietHoaDonPhongIDAO) Naming.lookup(urlRMI + "chiTietHoaDonPhongIDAO");
        	dichVuIDAO = (DichVuIDAO) Naming.lookup(urlRMI + "dichVuIDAO");
        	hoaDonIDAO =  (HoaDonIDAO) Naming.lookup(urlRMI + "hoaDonIDAO");
        	khachHangIDAO =  (KhachHangIDAO) Naming.lookup(urlRMI + "khachHangIDAO");
        	nhanVienIDAO =  (NhanVienIDAO) Naming.lookup(urlRMI + "nhanVienIDAO");
        	phongIDAO =  (PhongIDAO) Naming.lookup(urlRMI + "phongIDAO");
        	taiKhoanIDAO =  (TaiKhoanIDAO) Naming.lookup(urlRMI + "taiKhoanIDAO");
        } catch (Exception e) {
            System.out.println("Không thể kết nối đến máy chủ");
        }
    }

	public String getUrlRMI() {
		return urlRMI;
	}
}