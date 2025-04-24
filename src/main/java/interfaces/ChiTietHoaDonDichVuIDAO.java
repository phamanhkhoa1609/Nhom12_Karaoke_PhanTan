package interfaces;

import entities.ChiTietHoaDonDichVu;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ChiTietHoaDonDichVuIDAO extends Remote{
	public ChiTietHoaDonDichVu themMot(ChiTietHoaDonDichVu chiTietHoaDonDichVu) throws RemoteException;
	public void themNhieu(List<ChiTietHoaDonDichVu> dsChiTietHoaDonDichVu) throws RemoteException;
//	public ChiTietHoaDonDichVu suaMot(ChiTietHoaDonDichVu doiTuongSua) throws RemoteException;
//	public void suaNhieu(List<ChiTietHoaDonDichVu> dsDoiTuongSua) throws RemoteException;
//	public List<ChiTietHoaDonDichVu> layTatCa() throws RemoteException;
//	public ChiTietHoaDonDichVu layTheoMa(long ma) throws RemoteException;
//	public List<ChiTietHoaDonDichVu> timKiemMotTruong(String where, Object... objectSearch) throws RemoteException;
//	public List<ChiTietHoaDonDichVu> timKiemNhieuTruong(Object... objectSearch) throws RemoteException;
//	public Vector<String> layTatCaTheoTen() throws RemoteException;
//	public Vector<String> layTatCaTheoMa() throws RemoteException;
}
