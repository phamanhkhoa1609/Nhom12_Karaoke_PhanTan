package interfaces;

import entities.Phong;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Vector;

public interface PhongIDAO extends Remote{
	public Phong themMot(Phong phong) throws RemoteException;
	public void themNhieu(List<Phong> dsPhong) throws RemoteException;
	public Phong suaMot(Phong phong) throws RemoteException;
	public void suaNhieu(List<Phong> dsPhong) throws RemoteException;
	public List<Phong> layTatCa() throws RemoteException;
	public Phong layTheoMa(long maPhong) throws RemoteException;
	public List<Phong> timKiemMotTruong(String where, Object... objectSearch) throws RemoteException;
	public List<Phong> timKiemNhieuTruong(Object... objectSearch) throws RemoteException;
	public Vector<String> layTatCaTheoTen() throws RemoteException;
	public Vector<String> layTatCaTheoMa() throws RemoteException;
}
