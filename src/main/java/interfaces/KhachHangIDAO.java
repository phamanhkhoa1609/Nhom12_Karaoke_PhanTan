package interfaces;

import entities.KhachHang;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Vector;

public interface KhachHangIDAO extends Remote{
	public KhachHang themMot(KhachHang khachHang) throws RemoteException;
	public void themNhieu(List<KhachHang> dsKhachHang) throws RemoteException;
	public KhachHang suaMot(KhachHang khachHang) throws RemoteException;
	public void suaNhieu(List<KhachHang> dsKhachHang) throws RemoteException;
	public List<KhachHang> layTatCa() throws RemoteException;
	public KhachHang layTheoSDT(String sdt) throws RemoteException;
	public KhachHang layTheoMa(long maKH) throws RemoteException;
	public List<KhachHang> timKiemMotTruong(String where, Object... objectSearch) throws RemoteException;
	public List<KhachHang> timKiemNhieuTruong(Object... objectSearch) throws RemoteException;
	public Vector<String> layTatCaTheoTen() throws RemoteException;
	public Vector<String> layTatCaTheoMa() throws RemoteException;
	public Vector<String> layTatCaTheoSDT()  throws RemoteException;
	public boolean tonTaiSDT(String sdt)  throws RemoteException;
}
