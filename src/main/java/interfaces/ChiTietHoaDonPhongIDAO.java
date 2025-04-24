package interfaces;

import entities.ChiTietHoaDonPhong;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ChiTietHoaDonPhongIDAO extends Remote{
	public ChiTietHoaDonPhong themMot(ChiTietHoaDonPhong chiTietHoaDonPhong) throws RemoteException;
	public void themNhieu(List<ChiTietHoaDonPhong> dsChiTietHoaDonPhong) throws RemoteException;
}
