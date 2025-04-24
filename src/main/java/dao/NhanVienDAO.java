package dao;

import entities.NhanVien;
import interfaces.NhanVienIDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Vector;
import java.util.stream.Collectors;

public class NhanVienDAO extends UnicastRemoteObject implements NhanVienIDAO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EntityManager entityManager;
	public NhanVienDAO(EntityManager entityManager)  throws RemoteException{
		// TODO Auto-generated constructor stub
		this.entityManager = entityManager;
	}
	public NhanVien themMot(NhanVien nhanVien)  throws RemoteException{
		if (nhanVien.getHoTen().trim().equals("") ) return null;
		entityManager.getTransaction().begin();
		try {
			entityManager.persist(nhanVien);
			entityManager.getTransaction().commit();
			return nhanVien;
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
	public void themNhieu(List<NhanVien> dsNhanVien)  throws RemoteException{
		dsNhanVien.stream().forEach(nhanVien -> {
			try {
				themMot(nhanVien);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	@Override
	public NhanVien suaMot(NhanVien nhanVien)  throws RemoteException{
		if (nhanVien.getHoTen().trim().equals("") ) return null;
		entityManager.getTransaction().begin();
		try {
			entityManager.merge(nhanVien);
			entityManager.getTransaction().commit();
			return nhanVien;
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
	public void suaNhieu(List<NhanVien> dsNhanVien)  throws RemoteException{
		// TODO Auto-generated method stub
		dsNhanVien.stream().forEach(nhanVien -> {
			try {
				suaMot(nhanVien);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	@Override
	public List<NhanVien> layTatCa()  throws RemoteException{
		return entityManager.createNativeQuery("Select * from nhanvien",NhanVien.class).getResultList();
	}
	@Override
	public NhanVien layTheoMa(long maND)  throws RemoteException{
		return (NhanVien) entityManager.createNativeQuery("Select * from nhanvien where mand = :mand",NhanVien.class).setParameter("mand", maND).getSingleResult();
	}
	@Override
	public List<NhanVien> timKiemMotTruong(String where, Object...objectSearch) throws RemoteException {
		Query query = entityManager.createNativeQuery("select * from nhanvien "+where,NhanVien.class);
		for (int i = 0; i < objectSearch.length; i++) {
			query.setParameter(i+1, objectSearch[i]);
		}
		return query.getResultList();
	}
	@Override
	public List<NhanVien> timKiemNhieuTruong(Object...objectSearch) throws RemoteException {
		if(objectSearch==null) {
			return layTatCa();
		}
		
		boolean phanTuDeuLaTatCa = true;
	    for (Object obj : objectSearch) {
	        if (!(Objects.equals(obj, "Tất cả")||Objects.equals(obj, 0))) {
	        	phanTuDeuLaTatCa = false;
	            break;
	        }
	    }
	    if(phanTuDeuLaTatCa) {
			return layTatCa();
		}
			
		if(!objectSearch[0].equals("Tất cả")) // neu them vao la chuoi rong va ma co ton tai{
		{
			List<NhanVien> dsNV = new ArrayList<NhanVien>();
			dsNV.add(layTheoMa(Long.parseLong(objectSearch[0].toString()) ));
			return  dsNV;
		}
		StringBuilder queryString = new StringBuilder("select * from nhanvien where") ;
		
		boolean honMotTruong = false;
		if (!((String)objectSearch[1]).trim().equals("Tất cả")) {
			queryString.append(" hoten like ?") ;
			honMotTruong = true;
		}
		if (!((String) objectSearch[2]).trim().equals("Tất cả")) {
			if (honMotTruong)
				queryString.append(" and");
			queryString.append(" gioitinh = ?");
			honMotTruong = true;
		}
		if (!((String) objectSearch[3]).trim().equals("Tất cả")) {
			if (honMotTruong)
				queryString.append(" and");
			queryString.append(" trangthai = ?");
			honMotTruong = true;
		}
		if (!((String) objectSearch[4]).trim().equals("Tất cả")) {
			if (honMotTruong)
				queryString.append(" and");
			queryString.append(" sdt like ?");
			honMotTruong = true;
		}
		if (!((String)objectSearch[5]).trim().equals("Tất cả")) {
			if(honMotTruong) queryString.append(" and");
			queryString.append(" cccd like ?" ) ;
			honMotTruong = true;
		}
		if (!((String)objectSearch[6]).trim().equals("Tất cả")) {
			if(honMotTruong) queryString.append(" and");
			queryString.append(" chucvu = ?" ) ;
			honMotTruong = true;
		}
		if (!((String)objectSearch[7]).trim().equals("Tất cả")) {
			if(honMotTruong) queryString.append(" and");
			queryString.append(" diachi like ?" ) ;
			honMotTruong = true;
		}
		if (!objectSearch[8].equals(0)) {
			if(honMotTruong) queryString.append(" and");
			queryString.append(" day(ngaysinh) = ?" ) ;
			honMotTruong = true;
		}
		if (!objectSearch[9].equals(0)) {
			if(honMotTruong) queryString.append(" and");
			queryString.append(" month(ngaysinh) = ?" ) ;
			honMotTruong = true;
		}
		if (!objectSearch[10].equals(0)) {
			if(honMotTruong) queryString.append(" and");
			queryString.append(" year(ngaysinh) = ?" ) ;
			honMotTruong = true;
		}
		Query query = entityManager.createNativeQuery(queryString.toString(),NhanVien.class);
		int parameter = 0;
		if (!((String)objectSearch[1]).trim().equals("Tất cả")) {
			query.setParameter(parameter+1, "%"+ objectSearch[1] + "%"); // hoten
			parameter++;
		}
		if (!((String)objectSearch[2]).trim().equals("Tất cả")) {
			query.setParameter(parameter+1, ((String)objectSearch[2]).replaceAll(" ", "").toUpperCase()); // gioitinh
			parameter++;
		}
		if (!((String)objectSearch[3]).trim().equals("Tất cả")) {
			query.setParameter(parameter+1, ((String)objectSearch[3]).replaceAll(" ", "").toUpperCase()); // gioitinh
			parameter++;
		}
		if (!((String)objectSearch[4]).trim().equals("Tất cả")) {
			query.setParameter(parameter+1,"%" + objectSearch[4] + "%"); // sdt
			parameter++;
		}
		if (!((String)objectSearch[5]).trim().equals("Tất cả")) {
			query.setParameter(parameter+1,"%" + objectSearch[5] + "%"); // cccd
			parameter++;
		}
		if (!((String)objectSearch[6]).trim().equals("Tất cả")) {
			query.setParameter(parameter+1, ((String)objectSearch[6]).replaceAll(" ", "").toUpperCase()); // chucvu
			parameter++;
		}
		if (!((String)objectSearch[7]).trim().equals("Tất cả")) {
			query.setParameter(parameter+1,"%" + objectSearch[7] + "%"); // diachi
			parameter++;
		}
		if (!objectSearch[8].equals(0)) {
			query.setParameter(parameter+1, objectSearch[8] ); // ngay
			parameter++;
		} 
		if (!objectSearch[9].equals(0)) {
			query.setParameter(parameter+1, objectSearch[9] ); // thang
			parameter++;
		} 
		if (!objectSearch[10].equals(0)) {
			query.setParameter(parameter+1,  objectSearch[10] ); // nam
			parameter++;
		} 
		return query.getResultList();
	}
	@Override
	public Vector<String> layTatCaTheoTen()  throws RemoteException{
	    Vector<String> dsHoTen = new Vector<>();
	    dsHoTen.add("Tất cả");
	    dsHoTen.addAll(layTatCa().parallelStream()
	                                .map(NhanVien::getHoTen)
	                                .collect(Collectors.toList()));
	    return dsHoTen;
	}
	@Override
	public Vector<String> layTatCaTheoMa() throws RemoteException {
	    Vector<String> dsMaND = new Vector<>();
	    dsMaND.add("Tất cả");
	    dsMaND.addAll(layTatCa().parallelStream()
	                               .map(nhanVien -> Long.toString(nhanVien.getMaND()))
	                               .collect(Collectors.toList()));
	    return dsMaND;
	}
	@Override
	public Vector<String> layTatCaTheoSDT() throws RemoteException {
	    Vector<String> dsSDT = new Vector<>();
	    dsSDT.add("Tất cả");
	    dsSDT.addAll(layTatCa().parallelStream()
	                                .map(NhanVien::getSdt)
	                                .collect(Collectors.toList()));
	    return dsSDT;
	}
	@Override
	public Vector<String> layTatCaTheoCCCD() throws RemoteException {
	    Vector<String> dsSDT = new Vector<>();
	    dsSDT.add("Tất cả");
	    dsSDT.addAll(layTatCa().parallelStream()
	                                .map(NhanVien::getCccd)
	                                .collect(Collectors.toList()));
	    return dsSDT;
	}
	@Override
	public boolean tonTaiSDT(String sdt) throws RemoteException {
	    return layTatCa().parallelStream()
	        .anyMatch(khachHang -> khachHang.getSdt().equals(sdt));
	}
	@Override
	public NhanVien layTheoSDT(String sdt) throws RemoteException {
		return (NhanVien) entityManager.createNativeQuery("Select * from nhanvien where sdt = :sdt", NhanVien.class)
				.setParameter("sdt", sdt).getSingleResult();
	}
}
