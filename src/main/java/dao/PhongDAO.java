package dao;

import entities.Phong;
import interfaces.PhongIDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class PhongDAO extends UnicastRemoteObject implements PhongIDAO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EntityManager entityManager;
	public PhongDAO(EntityManager entityManager)  throws RemoteException{
		// TODO Auto-generated constructor stub
		this.entityManager = entityManager;
	}
	@Override
	public Phong themMot(Phong phong) throws RemoteException {
		entityManager.getTransaction().begin();
		try {
			entityManager.persist(phong);
			entityManager.getTransaction().commit();
			return phong;
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
	public void themNhieu(List<Phong> dsPhong)  throws RemoteException{
		dsPhong.stream().forEach(phong ->{
			try {
				themMot(phong);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	@Override
	public Phong suaMot(Phong phong)  throws RemoteException{
		entityManager.getTransaction().begin();
		try {
			entityManager.merge(phong);
			entityManager.getTransaction().commit();
			return phong;
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
	public void suaNhieu(List<Phong> dsPhong)  throws RemoteException{
		// TODO Auto-generated method stub
		dsPhong.stream().forEach(phong ->{
			try {
				suaMot(phong);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	@Override
	public List<Phong> layTatCa()  throws RemoteException{
		return entityManager.createNativeQuery("Select * from phong",Phong.class).getResultList();
	}
	@Override
	public Phong layTheoMa(long maPhong)  throws RemoteException{
		return (Phong) entityManager.createNativeQuery("Select * from phong where maphong = :maphong",Phong.class).setParameter("maphong", maPhong).getSingleResult();
	}
	@Override
	public List<Phong> timKiemMotTruong(String where, Object...objectSearch)  throws RemoteException{
		Query query = entityManager.createNativeQuery("select * from phong "+where,Phong.class);
		for (int i = 0; i < objectSearch.length; i++) {
			query.setParameter(i+1, objectSearch[i]);
		}
		return query.getResultList();
	}
	@Override
	public List<Phong> timKiemNhieuTruong(Object...objectSearch) throws RemoteException {
		if(objectSearch==null) {
			return layTatCa();
		}
		if(!objectSearch[0].equals("Tất cả")) // neu them vao la chuoi rong va ma co ton tai{
		{
			List<Phong> dsPhong = new ArrayList<Phong>();
			dsPhong.add(layTheoMa(Long.parseLong(objectSearch[0].toString()) ));
			return  dsPhong;
		}
		StringBuilder queryString = new StringBuilder("select * from phong where") ;
		
		boolean honMotTruong = false;
		if (!((String)objectSearch[1]).trim().equals("Tất cả")) {
			queryString.append(" tenphong like ?") ;
			honMotTruong = true;
		}
		if (!((String)objectSearch[2]).trim().equals("Tất cả")) {
			if(honMotTruong) queryString.append(" and");
			queryString.append(" loaiphong like ?" ) ;
			honMotTruong = true;
		}
		if (!((String)objectSearch[3]).trim().equals("Tất cả")) {
			if(honMotTruong) queryString.append(" and");
			queryString.append(" trangthai like ?" ) ;
			honMotTruong = true;
		}
		if(honMotTruong) queryString.append(" and");
		queryString.append(" giaphong >= ? and giaphong <= ?");
		Query query = entityManager.createNativeQuery(queryString.toString(),Phong.class);
		int parameter = 0;
		if (!((String)objectSearch[1]).trim().equals("Tất cả")) {
			query.setParameter(parameter+1, "%"+ objectSearch[1] + "%"); // tenphong
			parameter++;
		}
		if (!((String)objectSearch[2]).trim().equals("Tất cả")) {
			query.setParameter(parameter+1, "%"+ ((String)objectSearch[2]).replaceAll(" ", "").toUpperCase() + "%"); // loaiphong
			parameter++;
		}
		if (!((String)objectSearch[3]).trim().equals("Tất cả")) {
			query.setParameter(parameter+1,"%"+ ((String)objectSearch[3]).replaceAll(" ", "").toUpperCase()+ "%"); // tinhtrang
			parameter++;
		}
		query.setParameter(parameter+1, objectSearch[4]); // giaBD
		query.setParameter(parameter+2, objectSearch[5]); // giaKT
		return query.getResultList();
	}
	@Override
	public Vector<String> layTatCaTheoTen()  throws RemoteException{
	    Vector<String> dsTenPhong = new Vector<>();
	    dsTenPhong.add("Tất cả");
	    dsTenPhong.addAll(layTatCa().parallelStream()
	                                .map(Phong::getTenPhong)
	                                .collect(Collectors.toList()));
	    return dsTenPhong;
	}
	@Override
	public Vector<String> layTatCaTheoMa()  throws RemoteException{
	    Vector<String> dsMaPhong = new Vector<>();
	    dsMaPhong.add("Tất cả");
	    dsMaPhong.addAll(layTatCa().parallelStream()
	                               .map(phong -> Long.toString(phong.getMaPhong()))
	                               .collect(Collectors.toList()));
	    return dsMaPhong;
	}
}
