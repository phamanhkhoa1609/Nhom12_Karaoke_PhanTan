package interfaces;

import entities.DichVu;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.List;
import java.util.Vector;

public interface DichVuIDAO extends Remote{
	public DichVu themMot(DichVu dichVu) throws RemoteException;
	public void themNhieu(List<DichVu> dsDichVu) throws RemoteException;
	public DichVu suaMot(DichVu dichVu) throws RemoteException;
	public void suaNhieu(List<DichVu> dsDichVu) throws RemoteException;
	public List<DichVu> layTatCa() throws RemoteException;
	public DichVu layTheoMa(long maDV) throws RemoteException;
	public List<DichVu> timKiemMotTruong(String where, Object... objectSearch) throws RemoteException;
	public List<DichVu> timKiemNhieuTruong(Object... objectSearch) throws RemoteException;
	public Vector<String> layTatCaTheoTen() throws RemoteException;
	public Vector<String> layTatCaTheoMa() throws RemoteException;
	public int getSoLuongDichVuDaBanTheoNgay(long maDV, LocalDate localDate) throws RemoteException;
	public Double getTongTienDichVuTheoNgay(long maDV, LocalDate localDate) throws RemoteException;
}
