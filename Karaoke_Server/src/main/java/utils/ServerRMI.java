package utils;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import dao.ChiTietHoaDonDichVuDAO;
import dao.ChiTietHoaDonPhongDAO;
import dao.DichVuDAO;
import dao.HoaDonDAO;
import dao.KhachHangDAO;
import dao.NhanVienDAO;
import dao.PhongDAO;
import dao.TaiKhoanDAO;
import gui.ChinhFrame;
import interfaces.ChiTietHoaDonDichVuIDAO;
import interfaces.ChiTietHoaDonPhongIDAO;
import interfaces.DichVuIDAO;
import interfaces.HoaDonIDAO;
import interfaces.KhachHangIDAO;
import interfaces.NhanVienIDAO;
import interfaces.PhongIDAO;
import interfaces.TaiKhoanIDAO;
import jakarta.persistence.EntityManager;
import utils.guicomponents.table.MyTable;

public class ServerRMI {
	private HoaDonDAO hoaDonIDAO;
	private EntityManagerFactoryUltil entityManagerFactoryUltil;
	private EntityManager entityManager;

	public ServerRMI(MyTable tblDanhSachTruyCap) throws RemoteException, AlreadyBoundException {
		Registry registry = null;
		try {
			registry = LocateRegistry.createRegistry(ChinhFrame.PORT);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Port đã tồn tại nhưng vẫn sẽ chạy tiếp");
		}

		entityManagerFactoryUltil = new EntityManagerFactoryUltil("jpa-mariadb-karaGOke");
		 entityManager = entityManagerFactoryUltil.getEntityManager();
		ChiTietHoaDonDichVuIDAO chiTietHoaDonDichVuIDAO = new ChiTietHoaDonDichVuDAO(entityManager);
		ChiTietHoaDonPhongIDAO chiTietHoaDonPhongIDAO = new ChiTietHoaDonPhongDAO(entityManager); // Remote Object
		DichVuIDAO dichVuIDAO = new DichVuDAO(entityManager);
		hoaDonIDAO = new HoaDonDAO(entityManager);
		KhachHangIDAO khachHangIDAO = new KhachHangDAO(entityManager);
		NhanVienIDAO nhanVienIDAO = new NhanVienDAO(entityManager);
		PhongIDAO phongIDAO = new PhongDAO(entityManager);
		TaiKhoanIDAO taiKhoanIDAO = new TaiKhoanDAO(entityManager, tblDanhSachTruyCap);
		
		registry.bind("chiTietHoaDonDichVuIDAO", chiTietHoaDonDichVuIDAO);
		registry.bind("chiTietHoaDonPhongIDAO", chiTietHoaDonPhongIDAO);
		registry.bind("dichVuIDAO", dichVuIDAO);
		registry.bind("hoaDonIDAO", hoaDonIDAO);
		registry.bind("khachHangIDAO", khachHangIDAO);
		registry.bind("nhanVienIDAO", nhanVienIDAO);
		registry.bind("phongIDAO", phongIDAO);
		registry.bind("taiKhoanIDAO", taiKhoanIDAO);
		}

	public HoaDonDAO getHoaDonIDAO() {
		return hoaDonIDAO;
	}

	public void ketThuc() {
		try {
            Registry registry = LocateRegistry.getRegistry(ChinhFrame.PORT);
            registry.unbind("hoaDonIDAO");
            registry.unbind("chiTietHoaDonDichVuIDAO");
            registry.unbind("chiTietHoaDonPhongIDAO");
            registry.unbind("dichVuIDAO");
            registry.unbind("khachHangIDAO");
            registry.unbind("nhanVienIDAO");
            registry.unbind("phongIDAO");
            registry.unbind("taiKhoanIDAO");
            entityManagerFactoryUltil.closeEntityManager();
            entityManagerFactoryUltil.closeEntityManagerFactory();
        } catch (Exception e) {};
		entityManagerFactoryUltil.closeEntityManager();
		entityManagerFactoryUltil.closeEntityManagerFactory();
		System.exit(0);
	}
}
