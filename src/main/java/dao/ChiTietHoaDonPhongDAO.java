package dao;

import entities.ChiTietHoaDonPhong;
import interfaces.ChiTietHoaDonPhongIDAO;
import jakarta.persistence.EntityManager;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ChiTietHoaDonPhongDAO extends UnicastRemoteObject implements ChiTietHoaDonPhongIDAO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EntityManager entityManager;
	public ChiTietHoaDonPhongDAO(EntityManager entityManager) throws RemoteException{
		// TODO Auto-generated constructor stub
		this.entityManager = entityManager;
	}
	@Override
	public ChiTietHoaDonPhong themMot(ChiTietHoaDonPhong chiTietHoaDonPhong)  throws RemoteException{
		// TODO Auto-generated method stub
		entityManager.getTransaction().begin();
		try {
			entityManager.persist(chiTietHoaDonPhong);
			entityManager.getTransaction().commit();
			return chiTietHoaDonPhong;
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
	public void themNhieu(List<ChiTietHoaDonPhong> dsChiTietHoaDonPhong)  throws RemoteException{
		// TODO Auto-generated method stub
		dsChiTietHoaDonPhong.stream().forEach(chiTietHoaDonPhong -> {
			try {
				themMot(chiTietHoaDonPhong);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
}
