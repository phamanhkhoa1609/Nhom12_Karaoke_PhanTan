package interfaces;

import entities.HoaDon;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface HoaDonIDAO extends Remote{
	public HoaDon themMot(HoaDon hoaDon) throws RemoteException;
	public void themNhieu(List<HoaDon> dsHoaDon) throws RemoteException;
	public HoaDon suaMot(HoaDon hoaDon) throws RemoteException;
	public void suaNhieu(List<HoaDon> dsHoaDon) throws RemoteException;
	public List<HoaDon> layTatCa() throws RemoteException;
	public HoaDon layTheoMa(long maHD) throws RemoteException;
	public List<HoaDon> timKiemMotTruong(String where, Object... objectSearch) throws RemoteException;
	public List<HoaDon> timKiemNhieuTruong(Object... objectSearch) throws RemoteException;
	public boolean xuLyHetHan() throws RemoteException;
	public boolean xuLyPhongSuDungXong() throws	RemoteException;
	public List<HoaDon> layTheoMaKH(long maKH) throws RemoteException;
}
