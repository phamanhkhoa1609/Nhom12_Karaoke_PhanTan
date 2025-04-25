package interfaces;

import entities.NguoiDung;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TaiKhoanIDAO extends Remote{
	public NguoiDung layTheoSDT(String sdt)  throws RemoteException;
	public NguoiDung suaMatKhau(NguoiDung nguoiDung, String matKhau) throws RemoteException;
	public void truyCap(NguoiDung nguoiDung, String ip) throws RemoteException;
	public void ngatKetNoi(long maND, String ip) throws RemoteException;
}
