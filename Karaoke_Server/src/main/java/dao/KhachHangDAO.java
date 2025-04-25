package dao;

import entities.KhachHang;
import interfaces.KhachHangIDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Vector;
import java.util.stream.Collectors;

public class KhachHangDAO extends UnicastRemoteObject implements KhachHangIDAO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EntityManager entityManager;
	public KhachHangDAO(EntityManager entityManager)  throws RemoteException{
		// TODO Auto-generated constructor stub
		this.entityManager = entityManager;
	}
	@Override
	public KhachHang themMot(KhachHang khachHang)  throws RemoteException{
		entityManager.getTransaction().begin();
		try {
			entityManager.persist(khachHang);
			entityManager.getTransaction().commit();
			return khachHang;
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
	public void themNhieu(List<KhachHang> dsKhachHang) throws RemoteException{
		dsKhachHang.stream().forEach(khachHang -> {
			try {
				themMot(khachHang);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	@Override
	public KhachHang suaMot(KhachHang khachHang)  throws RemoteException{
		entityManager.getTransaction().begin();
		try {
			entityManager.merge(khachHang);
			entityManager.getTransaction().commit();
			return khachHang;
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
	public void suaNhieu(List<KhachHang> dsKhachHang) throws RemoteException {
		// TODO Auto-generated method stub
		dsKhachHang.stream().forEach(khachHang -> {
			try {
				suaMot(khachHang);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	@Override
	public List<KhachHang> layTatCa()  throws RemoteException{
		return entityManager.createNativeQuery("Select * from khachhang", KhachHang.class).getResultList();
	}
	@Override
	public KhachHang layTheoMa(long maND) throws RemoteException {
		return (KhachHang) entityManager
				.createNativeQuery("Select * from khachhang where mand = :mand", KhachHang.class)
				.setParameter("mand", maND).getSingleResult();
	}
	@Override
	public KhachHang layTheoSDT(String sdt) throws RemoteException {
		return (KhachHang) entityManager.createNativeQuery("Select * from khachhang where sdt = :sdt", KhachHang.class)
				.setParameter("sdt", sdt).getSingleResult();
	}

	@Override
	public List<KhachHang> timKiemMotTruong(String where, Object... objectSearch) throws RemoteException {
		Query query = entityManager.createNativeQuery("select * from khachhang " + where, KhachHang.class);
		for (int i = 0; i < objectSearch.length; i++) {
			query.setParameter(i + 1, objectSearch[i]);
		}
		return query.getResultList();
	}

	@Override
	public List<KhachHang> timKiemNhieuTruong(Object... objectSearch)  throws RemoteException{
		
		if (objectSearch == null ) {
	        return layTatCa();
	    }
		boolean phanTuDeuLaTatCa = true;
	    for (Object obj : objectSearch) {
	        if (!Objects.equals(obj, "Tất cả")) {
	        	phanTuDeuLaTatCa = false;
	            break;
	        }
	    }

	    if (phanTuDeuLaTatCa) {
	        return layTatCa();
	    }
		if (!objectSearch[0].equals("Tất cả")) // neu them vao la chuoi rong va ma co ton tai{
		{
			List<KhachHang> dsKH = new ArrayList<KhachHang>();
			dsKH.add(layTheoMa(Long.parseLong(objectSearch[0].toString())));
			return dsKH;
		}
		StringBuilder queryString = new StringBuilder("select * from khachhang where");

		boolean honMotTruong = false;
		if (!((String) objectSearch[1]).trim().equals("Tất cả")) {
			queryString.append(" hoten like ?");
			honMotTruong = true;
		}
		if (!((String) objectSearch[2]).trim().equals("Tất cả")) {
			if (honMotTruong)
				queryString.append(" and");
			queryString.append(" gioitinh like ?");
			honMotTruong = true;
		}
		if (!((String) objectSearch[3]).trim().equals("Tất cả")) {
			if (honMotTruong)
				queryString.append(" and");
			queryString.append(" sdt like ?");
			honMotTruong = true;
		}
		if (!((String) objectSearch[4]).trim().equals("Tất cả")) {
			if (honMotTruong)
				queryString.append(" and");
			queryString.append(" hoivien like ?");
			honMotTruong = true;
		}
		
		Query query = entityManager.createNativeQuery(queryString.toString(), KhachHang.class);
		int parameter = 0;
		if (!((String) objectSearch[1]).trim().equals("Tất cả")) {
			query.setParameter(parameter + 1, objectSearch[1]); // tenphong
			parameter++;
		}
		if (!((String) objectSearch[2]).trim().equals("Tất cả")) {
			query.setParameter(parameter + 1, ((String) objectSearch[2]).replaceAll(" ", "").toUpperCase()); 
			parameter++;
		}
		if (!((String) objectSearch[3]).trim().equals("Tất cả")) {
			query.setParameter(parameter + 1, ((String) objectSearch[3]).replaceAll(" ", "").toUpperCase());
			parameter++;
		}
		if (!((String) objectSearch[4]).trim().equals("Tất cả")) {
			query.setParameter(parameter + 1, ((String) objectSearch[4]).replaceAll(" ", "").toUpperCase()); 
			parameter++;
		}
		return query.getResultList();
	}
	@Override
	public Vector<String> layTatCaTheoTen()  throws RemoteException{
		Vector<String> dsHoTen = new Vector<>();
		dsHoTen.add("Tất cả");
		dsHoTen.addAll(layTatCa().parallelStream().map(KhachHang::getHoTen).collect(Collectors.toList()));
		return dsHoTen;
	}
	@Override
	public Vector<String> layTatCaTheoMa()  throws RemoteException{
		Vector<String> dsMaND = new Vector<>();
		dsMaND.add("Tất cả");
		dsMaND.addAll(
				layTatCa().parallelStream().map(khachHang -> Long.toString(khachHang.getMaND())).collect(Collectors.toList()));
		return dsMaND;
	}
	@Override
	public Vector<String> layTatCaTheoSDT()  throws RemoteException{
		Vector<String> dsSDT = new Vector<>();
		dsSDT.add("Tất cả");
		dsSDT.addAll(layTatCa().parallelStream().map(KhachHang::getSdt).collect(Collectors.toList()));
		return dsSDT;
	}
	@Override
	public boolean tonTaiSDT(String sdt)  throws RemoteException{
		return layTatCa().parallelStream().anyMatch(khachHang -> khachHang.getSdt().equals(sdt));
	}
}
