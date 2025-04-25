package dao;

import entities.KhachHang;
import entities.NguoiDung;
import entities.NhanVien;
import entities.enums.ChucVu;
import interfaces.TaiKhoanIDAO;
import jakarta.persistence.EntityManager;
import utils.guicomponents.table.MyTable;

import javax.swing.table.DefaultTableModel;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class TaiKhoanDAO extends UnicastRemoteObject implements TaiKhoanIDAO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EntityManager entityManager;
	private MyTable tblDanhSachTruyCap;

	public TaiKhoanDAO(EntityManager entityManager, MyTable tblDanhSachTruyCap) throws RemoteException {
		// TODO Auto-generated constructor stub
		this.entityManager = entityManager;
		this.tblDanhSachTruyCap = tblDanhSachTruyCap;
	}

	@Override
	public NguoiDung layTheoSDT(String sdt) throws RemoteException {
		return (NguoiDung) entityManager.createQuery("Select nd from NguoiDung nd where sdt = :sdt", NguoiDung.class)
				.setParameter("sdt", sdt).getSingleResult();
	}

	@Override
	public NguoiDung suaMatKhau(NguoiDung nguoiDung, String matKhau) throws RemoteException {
		entityManager.getTransaction().begin();
		try {
			nguoiDung.getTaiKhoan().setMatKhau(matKhau);
			entityManager.merge(nguoiDung);
			entityManager.merge(nguoiDung.getTaiKhoan());
			entityManager.getTransaction().commit();
			return nguoiDung;
		} catch (Exception e) {
			// TODO: handle exception
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void truyCap(NguoiDung nguoiDung, String ip) throws RemoteException {
		// TODO Auto-generated method stub
		DefaultTableModel model = (DefaultTableModel) tblDanhSachTruyCap.getModel();
		String vaiTro = "";
		if (nguoiDung == null) {
			model.addRow(new Object[] { ip, "Không xác định", "Không xác định", "Khách hàng" });
		} else {
			if (nguoiDung instanceof KhachHang) {
				vaiTro = "Khách hàng";
			} else if (nguoiDung instanceof NhanVien) {
				if (((NhanVien) nguoiDung).getChucVu()==ChucVu.QUẢNLÝ)
					vaiTro = "Quản lý";
				else if (((NhanVien) nguoiDung).getChucVu()==ChucVu.PHỤCVỤ)
					vaiTro = "Nhân viên phục vụ";
				else if (((NhanVien) nguoiDung).getChucVu()==ChucVu.TIẾPTÂN)
					vaiTro = "Nhân viên tiếp tân";
			}
			model.addRow(new Object[] { ip, nguoiDung.getMaND(), nguoiDung.getHoTen(), vaiTro });
		}
		
	}
	@Override
	public void ngatKetNoi(long maND, String ip) throws RemoteException {
		// TODO Auto-generated method stub
		DefaultTableModel model = (DefaultTableModel) tblDanhSachTruyCap.getModel();
		// nếu maND không xác định, hãy xóa dòng có cột ip là ip và cột maND là "Không xác định"
		if (maND == -1) {
			for (int i = 0; i < model.getRowCount(); i++) {
				if (model.getValueAt(i, 0).equals(ip) && model.getValueAt(i, 1).equals("Không xác định")) {
					model.removeRow(i);
					break;
				}
			}
		} else {
			for (int i = 0; i < model.getRowCount(); i++) {
				if (model.getValueAt(i, 1).equals(maND)) {
					model.removeRow(i);
					break;
				}
			}
		}
		
	}
}
