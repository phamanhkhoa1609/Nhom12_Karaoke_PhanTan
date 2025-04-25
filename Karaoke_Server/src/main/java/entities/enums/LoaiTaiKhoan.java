package entities.enums;

public enum LoaiTaiKhoan {
	QUẢNLÝ("Quản lý"),
	NHÂNVIÊN("Nhân viên"),
	KHÁCHHÀNG("Khách hàng");
	private String loaiTaiKhoan;
	LoaiTaiKhoan(String loaiTaiKhoan) {
		// TODO Auto-generated constructor stub
		this.loaiTaiKhoan = loaiTaiKhoan;
	}
	public String getLoaiTaiKhoan() {
		return loaiTaiKhoan;
	}
	public void setLoaiTaiKhoan(String loaiTaiKhoan) {
		this.loaiTaiKhoan = loaiTaiKhoan;
	}
	
}
