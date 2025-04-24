package dao;

import entities.HoaDon;
import entities.enums.TrangThaiHoaDon;
import entities.enums.TrangThaiPhong;
import interfaces.HoaDonIDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class HoaDonDAO extends UnicastRemoteObject implements HoaDonIDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EntityManager entityManager;

	public HoaDonDAO(EntityManager entityManager) throws RemoteException {
		// TODO Auto-generated constructor stub
		this.entityManager = entityManager;
	}

	@Override
	public HoaDon themMot(HoaDon hoaDon) throws RemoteException {
		// TODO Auto-generated method stub
		entityManager.getTransaction().begin();
		try {
			entityManager.persist(hoaDon);
			entityManager.getTransaction().commit();
			return hoaDon;
		} catch (Exception e) {
			// TODO: handle exception
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void themNhieu(List<HoaDon> dsHoaDon) throws RemoteException {
		// TODO Auto-generated method stub
		dsHoaDon.stream().forEach(hoaDon -> {
			try {
				themMot(hoaDon);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	@Override
	public HoaDon suaMot(HoaDon hoaDon) throws RemoteException {
		// TODO Auto-generated method stub
		entityManager.getTransaction().begin();
		try {
			entityManager.merge(hoaDon);
			entityManager.getTransaction().commit();
			return hoaDon;
		} catch (Exception e) {
			// TODO: handle exception
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void suaNhieu(List<HoaDon> dsHoaDon) throws RemoteException {
		// TODO Auto-generated method stub
		dsHoaDon.stream().forEach(hoaDon -> {
			try {
				suaMot(hoaDon);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	@Override
	public List<HoaDon> layTatCa() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HoaDon layTheoMa(long maHD) throws RemoteException {
		// TODO Auto-generated method stub
		return (HoaDon) entityManager.createNativeQuery("Select * from hoadon where mahd = :mahd", HoaDon.class)
				.setParameter("mahd", maHD).getSingleResult();
	}
	
	@Override
	public List<HoaDon> layTheoMaKH(long maKH) throws RemoteException {
		// TODO Auto-generated method stub
		return  entityManager.createNativeQuery("Select * from hoadon where makh = :makh", HoaDon.class)
				.setParameter("makh", maKH).getResultList();
	}

	@Override
	public List<HoaDon> timKiemMotTruong(String where, Object... objectSearch) throws RemoteException {
		// TODO Auto-generated method stub
		Query query = entityManager.createNativeQuery("select * from hoadon " + where, HoaDon.class);
		for (int i = 0; i < objectSearch.length; i++) {
			query.setParameter(i + 1, objectSearch[i]);
		}
		if (query.getResultList().isEmpty()) {
			return null;
		} else {
			return query.getResultList();
		}
	}

	@Override
	public List<HoaDon> timKiemNhieuTruong(Object... objectSearch) throws RemoteException {
		return null;
	}

	public boolean xuLyHetHan() {
		// Xử lý hóa đơn hết hạn:
		// Lấy danh sách Hóa đơn có thoihan < bây giờ và trangthai != ĐÃTHANHTOÁN thì
		// đổi trangthai = ĐÃHẾTHẠN
		// sau đó cập nhật số lượng những dịch vụ trả về kho và phòng có trạng thái = ĐANBẢOTRÌ trong danh sách đó
		entityManager.getTransaction().begin();
		try {
			List<HoaDon> dsHoaDon = entityManager
					.createNativeQuery("select * from hoadon where trangthai != 'ĐÃTHANHTOÁN' and thoihan < now()",
							HoaDon.class)
					.getResultList();
			dsHoaDon.stream().forEach(hoaDon -> {
				hoaDon.setTrangThai(TrangThaiHoaDon.ĐÃHẾTHẠN);
				entityManager.merge(hoaDon);
				hoaDon.getDsCTHDDV().stream().forEach(ccthddv -> {
					ccthddv.getDichVu().setSoLuong(ccthddv.getDichVu().getSoLuong() + ccthddv.getSoLuong());
					entityManager.merge(ccthddv.getDichVu());
				});
				hoaDon.getDsCTHDP().stream().forEach(ccthdp -> {
					ccthdp.getPhong().setTrangThai(TrangThaiPhong.ĐANGBẢOTRÌ);
					entityManager.merge(ccthdp.getPhong());
				});
			});
			entityManager.getTransaction().commit();
			return true;
		} catch (Exception e) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			e.printStackTrace();
			return false;
		}
	}
	public boolean xuLyPhongSuDungXong() {
		// Xử lý phòng sử dụng xong:
		// Lấy danh sách Hóa đơn (độc nhất về mã phòng sau đó là thoigianlap lớn nhất) có trangthai = ĐÃTHANHTOÁN và tất cả các chi tiết hóa
		// đơn phòng có trạng thái = ĐANGSỬDỤNG thì
		// đổi trangthai = ĐANGBẢOTRÌ
		entityManager.getTransaction().begin();
		try {
			List<HoaDon> dsHoaDon = entityManager.createNativeQuery(
					"select * from hoadon where trangthai = 'ĐÃTHANHTOÁN' and mahd in (select mahd from chitiethoadonphong where trangthai = 'ĐANGSỬDỤNG' group by mahd order by thoigianlap desc)",
					HoaDon.class).getResultList();
			dsHoaDon.stream().forEach(hoaDon -> {
				hoaDon.getDsCTHDP().stream().forEach(ccthdp -> {
					ccthdp.getPhong().setTrangThai(TrangThaiPhong.ĐANGBẢOTRÌ);
					entityManager.merge(ccthdp.getPhong());
				});
			});
			entityManager.getTransaction().commit();
			return true;
		} catch (Exception e) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			e.printStackTrace();
			return false;
		}
		
	}
}
