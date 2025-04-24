package dao;

import entities.DichVu;
import interfaces.DichVuIDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class DichVuDAO extends UnicastRemoteObject implements DichVuIDAO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EntityManager entityManager;
	public DichVuDAO(EntityManager entityManager)  throws RemoteException{
		// TODO Auto-generated constructor stub
		this.entityManager = entityManager;
	}
	@Override
	public DichVu themMot(DichVu dichVu)  throws RemoteException{
		if (dichVu.getTenDV().trim().equals("") ) return null;
		entityManager.getTransaction().begin();
		try {
			entityManager.persist(dichVu);
			entityManager.getTransaction().commit();
			return dichVu;
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
	public void themNhieu(List<DichVu> dsDichVu)  throws RemoteException{
		dsDichVu.stream().forEach(dichVu -> {
			try {
				themMot(dichVu);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	@Override
	public DichVu suaMot(DichVu dichVu)  throws RemoteException{
		if (dichVu.getTenDV().trim().equals("") ) return null;
		entityManager.getTransaction().begin();
		try {
			entityManager.merge(dichVu);
			entityManager.getTransaction().commit();
			return dichVu;
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
	public void suaNhieu(List<DichVu> dsDichVu) throws RemoteException {
		// TODO Auto-generated method stub
		dsDichVu.stream().forEach(dichVu -> {
			try {
				suaNhieu(dsDichVu);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	@Override
	public List<DichVu> layTatCa()  throws RemoteException{
		return entityManager.createNativeQuery("Select * from dichvu",DichVu.class).getResultList();
	}
	@Override
	public DichVu layTheoMa(long maDV) {
		return (DichVu) entityManager.createNativeQuery("Select * from dichvu where madv = :madv",DichVu.class).setParameter("madv", maDV).getSingleResult();
	}
	@Override
	public List<DichVu> timKiemMotTruong(String where, Object...objectSearch) {
		Query query = entityManager.createNativeQuery("select * from dichvu "+where,DichVu.class);
		for (int i = 0; i < objectSearch.length; i++) {
			query.setParameter(i+1, objectSearch[i]);
		}
		return query.getResultList();
	}
	@Override
	public List<DichVu> timKiemNhieuTruong(Object...objectSearch)  throws RemoteException{
		if(objectSearch==null) {
			System.out.println("Phat hien null");
			return layTatCa();
		}
			
		if(!objectSearch[0].equals("Tất cả")) // neu them vao la chuoi rong va ma co ton tai{
		{
			List<DichVu> dsDV = new ArrayList<DichVu>();
			dsDV.add(layTheoMa(Long.parseLong(objectSearch[0].toString()) ));
			return  dsDV;
		}
		StringBuilder queryString = new StringBuilder("select * from dichvu where") ;
		
		boolean honMotTruong = false;
		if (!((String)objectSearch[1]).trim().equals("Tất cả")) {
			queryString.append(" tendv like ?") ;
			honMotTruong = true;
		}
		if (!((String)objectSearch[2]).trim().equals("Tất cả")) {
			if(honMotTruong) queryString.append(" and");
			queryString.append(" loaidv like ?" ) ;
			honMotTruong = true;
		}
		if (!((String)objectSearch[3]).trim().equals("Tất cả")) {
			if(honMotTruong) queryString.append(" and");
			queryString.append(" trangthai like ?" ) ;
			honMotTruong = true;
		}
		if(honMotTruong) queryString.append(" and");
		queryString.append(" giadv >= ? and giadv <= ?");
		Query query = entityManager.createNativeQuery(queryString.toString(),DichVu.class);
		int parameter = 0;
		if (!((String)objectSearch[1]).trim().equals("Tất cả")) {
			System.out.println(queryString);
			query.setParameter(parameter+1, objectSearch[1]); // tenphong
			parameter++;
		}
		if (!((String)objectSearch[2]).trim().equals("Tất cả")) {
			System.out.println(queryString);
			System.out.println(parameter+":"+((String)objectSearch[2]).replaceAll(" ", "").toUpperCase());
			query.setParameter(parameter+1, ((String)objectSearch[2]).replaceAll(" ", "").toUpperCase()); // loaiphong
			parameter++;
		}
		if (!((String)objectSearch[3]).trim().equals("Tất cả")) {
			System.out.println(parameter+":"+((String)objectSearch[3]).replaceAll(" ", "").toUpperCase());
			query.setParameter(parameter+1, ((String)objectSearch[3]).replaceAll(" ", "").toUpperCase()); // tinhtrang
			parameter++;
		}
		query.setParameter(parameter+1, objectSearch[4]); // giaBD
		query.setParameter(parameter+2, objectSearch[5]); // giaKT
		return query.getResultList();
	}
	@Override
	public Vector<String> layTatCaTheoTen()  throws RemoteException{
	    Vector<String> dsTenDV = new Vector<>();
	    dsTenDV.add("Tất cả");
	    dsTenDV.addAll(layTatCa().parallelStream()
	                                .map(DichVu::getTenDV)
	                                .collect(Collectors.toList()));
	    return dsTenDV;
	}
	@Override
	public Vector<String> layTatCaTheoMa()  throws RemoteException{
	    Vector<String> dsMaDV = new Vector<>();
	    dsMaDV.add("Tất cả");
	    dsMaDV.addAll(layTatCa().parallelStream()
	                               .map(dichVu -> Long.toString(dichVu.getMaDV()))
	                               .collect(Collectors.toList()));
	    return dsMaDV;
	}
	
	@Override
	public int getSoLuongDichVuDaBanTheoNgay(long maDV, LocalDate localDate) throws RemoteException {
	    try {
	        Query query = entityManager.createNativeQuery(
	            "SELECT COALESCE(SUM(cthd.soluong), 0) " +
	            "FROM HoaDon hd " +
	            "JOIN ChiTietHoaDonDichVu cthd ON hd.mahd = cthd.mahd " +
	            "WHERE cthd.madv = :maDV AND DATE(hd.thoigianlap) = :ngay " +
	            "GROUP BY cthd.madv"
	        );
	        query.setParameter("maDV", maDV);
	        query.setParameter("ngay", java.sql.Date.valueOf(localDate));

	        // Lấy kết quả từ câu truy vấn
	        Object result = query.getSingleResult();

	        // Chuyển đổi kết quả từ BigDecimal sang int
	        if (result instanceof BigDecimal) {
	            BigDecimal bdResult = (BigDecimal) result;
	            return bdResult.intValue(); // Chuyển đổi BigDecimal sang int
	        } else {
	            // Trả về 0 nếu không có kết quả
	            return 0;
	        }
	    } catch (NoResultException e) {
	        // Xử lý ngoại lệ khi không có kết quả
	        return 0;
	    }
	}
	
	@Override
	public Double getTongTienDichVuTheoNgay(long maDV, LocalDate localDate) throws RemoteException {
	    try {
	        Query query = entityManager.createNativeQuery(
	            "SELECT COALESCE(SUM(cthd.soluong * cthd.dongia), 0) " +
	            "FROM HoaDon hd " +
	            "JOIN ChiTietHoaDonDichVu cthd ON hd.mahd = cthd.mahd " +
	            "WHERE cthd.madv = :maDV AND DATE(hd.thoigianlap) = :ngay " +
	            "GROUP BY cthd.madv"
	        );
	        query.setParameter("maDV", maDV);
	        query.setParameter("ngay", java.sql.Date.valueOf(localDate));

	        // Lấy kết quả từ câu truy vấn
	        Object result = query.getSingleResult();

	        // Chuyển đổi kết quả từ BigDecimal sang Double
	        if (result instanceof BigDecimal) {
	            BigDecimal bdResult = (BigDecimal) result;
	            return bdResult.doubleValue(); // Chuyển đổi BigDecimal sang Double
	        } else {
	            // Trả về 0.0 nếu không có kết quả
	            return 0.0;
	        }
	    } catch (NoResultException e) {
	        // Xử lý ngoại lệ khi không có kết quả
	        return 0.0;
	    }
	}
	
}
