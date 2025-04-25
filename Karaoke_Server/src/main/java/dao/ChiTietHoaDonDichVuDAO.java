package dao;

import entities.ChiTietHoaDonDichVu;
import interfaces.ChiTietHoaDonDichVuIDAO;
import jakarta.persistence.EntityManager;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ChiTietHoaDonDichVuDAO extends UnicastRemoteObject implements ChiTietHoaDonDichVuIDAO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EntityManager entityManager;
	public ChiTietHoaDonDichVuDAO(EntityManager entityManager) throws RemoteException{
		// TODO Auto-generated constructor stub
		this.entityManager = entityManager;
	}
	@Override
	public ChiTietHoaDonDichVu themMot(ChiTietHoaDonDichVu chiTietHoaDonDichVu)  throws RemoteException{
		// TODO Auto-generated method stub
		entityManager.getTransaction().begin();
		try {
			entityManager.persist(chiTietHoaDonDichVu);
			entityManager.getTransaction().commit();
			return chiTietHoaDonDichVu;
		} catch (Exception e) {
			// TODO: handle exception
			if(entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void themNhieu(List<ChiTietHoaDonDichVu> dsChiTietHoaDonDichVu)  throws RemoteException{
		// TODO Auto-generated method stub
		dsChiTietHoaDonDichVu.stream().forEach(chiTietHoaDonDV -> {
			try {
				themMot(chiTietHoaDonDV);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
}
