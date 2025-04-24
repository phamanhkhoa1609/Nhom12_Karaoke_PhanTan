package interfaces;

import entities.NhanVien;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Vector;

public interface NhanVienIDAO extends Remote{
	public NhanVien themMot(NhanVien nhanVien) throws RemoteException;
	public void themNhieu(List<NhanVien> dsNhanVien) throws RemoteException;
	public NhanVien suaMot(NhanVien nhanVien) throws RemoteException;
	public void suaNhieu(List<NhanVien> dsNhanVien) throws RemoteException;
	public List<NhanVien> layTatCa() throws RemoteException;
	public NhanVien layTheoMa(long maNV) throws RemoteException;
	public List<NhanVien> timKiemMotTruong(String where, Object... objectSearch) throws RemoteException;
	public List<NhanVien> timKiemNhieuTruong(Object... objectSearch) throws RemoteException;
	public Vector<String> layTatCaTheoTen() throws RemoteException;
	public Vector<String> layTatCaTheoMa() throws RemoteException;
	public Vector<String> layTatCaTheoSDT() throws RemoteException;
	public Vector<String> layTatCaTheoCCCD() throws RemoteException;
	public boolean tonTaiSDT(String sdt) throws RemoteException;
	public NhanVien layTheoSDT(String sdt) throws RemoteException;
}
